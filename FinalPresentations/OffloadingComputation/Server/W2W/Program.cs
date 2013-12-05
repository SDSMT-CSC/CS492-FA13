using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using W2W.Model;
using System.Drawing;
using System.Collections.ObjectModel;


namespace W2W
{
    class Program
    {
        static void Main(string[] args)
        {
            SocketServer.StartListening();
        }
        //    string path = @"C:\Users\Alex\Desktop\Clothes";
        //    DBModel db = new DBModel();
        //    ObservableCollection<ClothingImage> images = new ObservableCollection<ClothingImage>(db.GetImages());

        //    int count = 0;
        //    string[] files = Directory.GetFiles(path);
        //    foreach (string s in files)
        //    {
        //        if (!s.Contains(".txt") && !s.Contains(".db"))
        //        {
        //            Image img = Image.FromFile(s);
        //            byte[] bytes = imageToByteArray(s, img);
        //            images[count].Image = Encoding.UTF8.GetString(bytes, 0, bytes.Length);
        //            count += 1;
        //        }
        //    }
        //    db.Update();
        //}
        //public static byte[] imageToByteArray(string file, Image imageIn)
        //{
        //    MemoryStream ms = new MemoryStream();
        //    imageIn.Save(ms, System.Drawing.Imaging.ImageFormat.Png);
        //    return ms.ToArray();
        //}
    }
}
