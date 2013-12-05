using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.Linq;
using System.Linq;
using System.Collections.ObjectModel;

namespace W2W.Model
{
    class DBModel
    {
        private static string _ConnectionString = "Data Source=HAPEX;Initial Catalog=W2W;Integrated Security=True";
        private W2W.Model.DataClasses1DataContext _DB;

        public DBModel()
        {
            _DB = new DataClasses1DataContext(_ConnectionString);
        }

        public void Update()
        {
            _DB.SubmitChanges();
        }

        public Collection<Cloth> GetClothes()
        {
            return new Collection<Cloth>(_DB.Cloths.ToList());
        }

        public Collection<ClothingImage> GetImages()
        {
            return new Collection<ClothingImage>(_DB.ClothingImages.ToList());
        }
    }
}
