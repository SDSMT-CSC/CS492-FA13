package edu.sdsmt.ThuryWulff.sentireaerissimia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;

import com.google.gson.Gson;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
/***
 * Class for asynchronously making requests to the server 
 * 
 * @author Alex Wulff
 */
public class ClientSender extends AsyncTask<String, Void, Socket> {
	private static String SERVER_IP = "smfo.no-ip.org";
    private Socket socket;
    private String answer;
    private Context context;
    private BufferedWriter out;
    private BufferedReader in;
    public String Reply;

    /***
     * Constructor
     * 
     * @author Alex Wulff
     * 
     * @param context the context to be used
     */
    public ClientSender(Context context) {
        this.context = context;
        socket = null;
        out = null;
        in = null;
    }

    /***
     * task to be done in the background for this asyncTask
     * 
     * @author Alex Wulff
     * 
     * @return Socket returns the socket of the connection
     */
    @Override
    protected Socket doInBackground(String... params) {
        try {
            if (socket == null) {
                socket = new Socket(SERVER_IP, 11000);
		// this is the out stream to the server
                out = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream()));
                // this then is our response
                in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
            }

            out.write(params[0]);  // write out the message
            out.flush();  // and force it to send

            // Reply is kept as a public access to the answer... should probably be done better
            Reply = answer = in.readLine() + System.getProperty("line.separator");

            return socket;
        } catch (IOException e) {
            e.printStackTrace();
        }

        // returned for toast and notifications
        return socket;
    }

    /***
     * task to be after the task is executed for this asyncTask
     * 
     * @author Alex Wulff
     * 
     * @param none
     * @return none
     */
    protected void onPostExecute(Socket socket) {
        if (socket != null) {
        // Commented out as this is a huge string
            //Toast.makeText(context, answer, Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(context, "Can't connect to server!",
                    Toast.LENGTH_LONG).show();
        }

    }
}