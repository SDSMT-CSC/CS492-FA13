using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace W2W.Model
{
    using Newtonsoft.Json;
    using System;
    using System.Collections.ObjectModel;
    using System.Net;
    using System.Net.Sockets;
    using System.Text;
    using System.Threading;

    // State object for reading client data asynchronously
    public class StateObject
    {
        // Client  socket.
        public Socket workSocket = null;
        // Size of receive buffer.
        public const int BufferSize = 5120000;
        // Receive buffer.
        public byte[] buffer = new byte[BufferSize];
        // Received data string.
        public StringBuilder sb = new StringBuilder();
    }

    public class SocketServer
    {
        // Thread signal.
        public static ManualResetEvent allDone = new ManualResetEvent(false);
        public static Collection<Cloth> Current;


        public SocketServer()
        {
        }

        public static void StartListening()
        {
            // Data buffer for incoming data.
            byte[] bytes = new Byte[51200];

            Current = new Collection<Cloth>();
            // Establish the local endpoint for the socket.
            // The DNS name of the computer
            // running the listener is "host.contoso.com".
            IPHostEntry ipHostInfo = Dns.Resolve(Dns.GetHostName());
            IPAddress ipAddress = ipHostInfo.AddressList[0];
            IPEndPoint localEndPoint = new IPEndPoint(ipAddress, 11000);

            // Create a TCP/IP socket.
            Socket listener = new Socket(AddressFamily.InterNetwork,
                SocketType.Stream, ProtocolType.Tcp);

            // Bind the socket to the local endpoint and listen for incoming connections.
            try
            {
                listener.Bind(localEndPoint);
                listener.Listen(100);

                while (true)
                {
                    // Set the event to nonsignaled state.
                    allDone.Reset();

                    // Start an asynchronous socket to listen for connections.
                    Console.WriteLine("Waiting for a connection...");
                    listener.BeginAccept(
                        new AsyncCallback(AcceptCallback),
                        listener);

                    // Wait until a connection is made before continuing.
                    allDone.WaitOne();
                }

            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }

            Console.WriteLine("\nPress ENTER to continue...");
            Console.Read();

        }

        public static void AcceptCallback(IAsyncResult ar)
        {
            // Signal the main thread to continue.
            allDone.Set();

            // Get the socket that handles the client request.
            Socket listener = (Socket)ar.AsyncState;
            Socket handler = listener.EndAccept(ar);

            // Create the state object.
            StateObject state = new StateObject();
            state.workSocket = handler;
            handler.BeginReceive(state.buffer, 0, StateObject.BufferSize, 0,
                new AsyncCallback(ReadCallback), state);
        }

        public static void ReadCallback(IAsyncResult ar)
        {
            QueryManager qm = new QueryManager();
            
            int num;

            String content = String.Empty;

            // Retrieve the state object and the handler socket
            // from the asynchronous state object.
            StateObject state = (StateObject)ar.AsyncState;
            Socket handler = state.workSocket;

            // Read data from the client socket. 
            int bytesRead = handler.EndReceive(ar);

            if (bytesRead > 0)
            {
                // There  might be more data, so store the data received so far.
                state.sb.Append(Encoding.ASCII.GetString(
                    state.buffer, 0, bytesRead));

                // Check for end-of-file tag. If it is not there, read 
                // more data.
                content = state.sb.ToString();

                // Test for end flag
                if (content.IndexOf("<EOF>") > -1)
                {
                    // All the data has been read from the 
                    // client. Display it on the console.
                    Console.WriteLine("Read {0} bytes from socket. \n Data : {1}",
                        content.Length, content);
                    // Now we want to push the received data to the JSonParser, database, filter then back
                    Cloth received;

                    try
                    {
                         received = JsonConvert.DeserializeObject<Cloth>(content.Substring(0,content.Length - 5));
                    }
                    catch(Exception e)
                    {
                        received = null;
                    }
                    // now we search if not null
                    if (received != null)
                    {
                        if (received.Description != null)
                        {
                            qm.GetClothesByDesc(received);
                        }
                        else if (received.ClothingID >= 1)
                        {
                            qm.GetClothesByID(received);
                        }
                        else
                        {
                            qm.GetMatchingClothes(received);
                        }

                        // get matches
                        Dictionary<int, Collection<Cloth>> found = qm.GetSelected();
                        // Save results
                        // use number to get index

                        foreach(var index in found.Keys)
                        {
                            foreach(var c in found[index])
                            {
                                Current.Add(c);
                            }
                        }
                        int clothesCount = found.Values.Sum(o => o.Count);

                        //// send count
                        //Send(handler, clothesCount.ToString());

                        //foreach (KeyValuePair<int,Collection<Cloth>> item in found)
                        //{
                        //   foreach (Cloth c in item.Value)
                        //   {
                        //       // call over to get big json reply
                        //       string reply = JSONBuilder(c);

                        //       // send back
                        //       Send(handler, reply);
                        //   }
                        //}
                        Send(handler, clothesCount.ToString());

                        // call over to get big json reply
                        
                    }
                    // if received was null, we need to check out the content
                    else if (content.Contains("rr"))
                    {
                        // rotate right
                        qm.RotateRackRight();

                        //get clothes and make sub list
                        Dictionary<int, Collection<Cloth>> list = qm.GetSelected();
                        Dictionary<int, Collection<Cloth>> sublist = new Dictionary<int, Collection<Cloth>>();
                        
                        // only get what was added
                        foreach(KeyValuePair<int,Collection<Cloth>> pair in list)
                        {
                            sublist[pair.Key] = new Collection<Cloth>();
                            sublist[pair.Key].Add(pair.Value.First());
                        }

                        // call over to get big json reply
                        string reply = JSONBuilder(sublist);

                        // send back
                        Send(handler, reply);
                    }
                    else if (content.Contains("lr"))
                    {
                        // rotate left
                        qm.RotateRackLeft();

                        //get clothes and make sub list
                        Dictionary<int, Collection<Cloth>> list = qm.GetSelected();
                        Dictionary<int, Collection<Cloth>> sublist = new Dictionary<int, Collection<Cloth>>();

                        // only get what was added
                        foreach (KeyValuePair<int, Collection<Cloth>> pair in list)
                        {
                            sublist[pair.Key] = new Collection<Cloth>();
                            sublist[pair.Key].Add(pair.Value.Last());
                        }

                        // call over to get big json reply
                        string reply = JSONBuilder(sublist);

                        // send back
                        Send(handler, reply);
                    }

                    else
                    {
                        if (int.TryParse(content.Substring(0, content.Length - 5), out num))
                        {
                            if (Current.Count <= num)
                            {
                                Send(handler, "UP YOURS");
                            }
                            else
                            {
                                // call over to get big json reply
                                string reply = JSONBuilder(Current[num]);

                                // send back
                                Send(handler, reply);
                            }
                        }
                    }
                    
                }
                else
                {
                    // Bad data, send reply
                    Send(handler, "Incorrect information format");
                }
            }
        }

        private static void Send(Socket handler, String data)
        {
            // Convert the string data to byte data using ASCII encoding.
            byte[] byteData = Encoding.ASCII.GetBytes(data);


            // Begin sending the data to the remote device.
            handler.BeginSend(byteData, 0, byteData.Length, 0,
                new AsyncCallback(SendCallback), handler);
        }

        private static void SendCallback(IAsyncResult ar)
        {
            try
            {
                // Retrieve the socket from the state object.
                Socket handler = (Socket)ar.AsyncState;

                // Complete sending the data to the remote device.
                int bytesSent = handler.EndSend(ar);
                Console.WriteLine("Sent {0} bytes to client.", bytesSent);

                handler.Shutdown(SocketShutdown.Both);
                handler.Close();

            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }
        private static void ShutDown(Socket ar)
        {
            try
            {
                Socket handler = ar;
                handler.Shutdown(SocketShutdown.Both);
                handler.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        // WILL NEED TESTING WITH APP I NEED A HEADDER WHICH I HAVEN'T A CLUE YET
        private static string JSONBuilder( Dictionary<int, Collection<Cloth>> c)
        {
            string clothes = string.Empty;
            foreach(KeyValuePair<int,Collection<Cloth>> pair in c)
            {
                foreach (Cloth item in pair.Value)
                {
                    try
                    {
                        SendFriendlyClothes sfc = new SendFriendlyClothes(item);
                        clothes = string.Format("{0}{1}", clothes, JsonConvert.SerializeObject(sfc));
                    }
                    catch (Exception e)
                    {
                        Console.WriteLine(e.ToString());
                    }
                }
            }
            return clothes;
        }
        private static string JSONBuilder(Cloth c)
        {
            string clothes = string.Empty;
            try
            {
                SendFriendlyClothes sfc = new SendFriendlyClothes(c);
                clothes = string.Format("{0}{1}", clothes, JsonConvert.SerializeObject(sfc));
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
             
            return clothes;
        }
    }
}