using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace W2W.Model
{
    public class SendFriendlyClothes
    {
        public byte[] Image;
        public int ImageHeight;
        public int ImageWidth;
        public int Type;

        public SendFriendlyClothes(Cloth c)
        {
            Type = c.Type;
            
            ImageHeight = c.ClothingImages.First(o => o.ClothingID == c.ClothingID).ImageHeight.Value;
            ImageWidth = c.ClothingImages.First(o => o.ClothingID == c.ClothingID).ImageWidth.Value;
            if (c.ClothingImages.First(o => o.ClothingID == c.ClothingID).Image != null)
            {
                Image = Encoding.ASCII.GetBytes(c.ClothingImages.First(o => o.ClothingID == c.ClothingID).Image);
            }
            else
            {
                int x, y;
                int size = 0;
                
                    y = ImageHeight;
                    x = ImageWidth;
                    size =  x * y * 4;
                
               
                Image = new byte[size];
            }
        }
    }
}
