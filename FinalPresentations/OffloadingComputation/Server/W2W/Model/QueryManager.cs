using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Data.Linq;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace W2W.Model
{
    public class QueryManager
    {
        public Cloth Template;

        private Collection<Cloth> _Wardrobe;
        private Dictionary<int, Collection<Cloth>> _SelectedClothes;
        private Dictionary<int, Collection<Cloth>> _TargetedClothes;
        private DBModel _DB;

        public QueryManager()
        {
            _DB = new DBModel();
            _TargetedClothes = new Dictionary<int, Collection<Cloth>>();
            _SelectedClothes = new Dictionary<int, Collection<Cloth>>();
        }

        public Dictionary<int, Collection<Cloth>> GetSelected()
        {
            return _SelectedClothes;
        }

        public void GetMatchingClothes(Cloth request)
        {
            // drop any old targets and selecteds
            _TargetedClothes = new Dictionary<int, Collection<Cloth>>();
            _SelectedClothes = new Dictionary<int, Collection<Cloth>>();
            Template = request;
            if (_Wardrobe == null)
            {
                _Wardrobe = new Collection<Cloth>(_DB.GetClothes());
            }
            // in anycase order wardrobe by type
            _Wardrobe = new Collection<Cloth>(_Wardrobe.OrderBy(o => o.Type).ToList());

            // go through and match for what was selected at best of 10 of 13 matches;
            foreach(Cloth c in _Wardrobe)
            {
                if (!c.IsDirty)
                {
                    int matches = 0;
                    // Colors
                    if (request.CanBlack == c.IsBlack)
                    {
                        matches += 1;
                    }
                    if (request.CanBlue == c.IsBlue)
                    {
                        matches += 1;
                    }
                    if (request.CanBrown == c.IsBrown)
                    {
                        matches += 1;
                    }
                    if (request.CanGreen == c.IsGreen)
                    {
                        matches += 1;
                    }
                    if (request.CanGrey == c.IsGrey)
                    {
                        matches += 1;
                    }
                    if (request.CanOrange == c.IsOrange)
                    {
                        matches += 1;
                    }
                    if (request.CanPink == c.IsPink)
                    {
                        matches += 1;
                    }
                    if (request.CanPurple == c.IsPurple)
                    {
                        matches += 1;
                    }
                    if (request.CanRed == c.IsRed)
                    {
                        matches += 1;
                    }
                    if (request.CanWhite == c.IsWhite)
                    {
                        matches += 1;
                    }
                    if (request.CanYellow == c.IsYellow)
                    {
                        matches += 1;
                    }
                    // Setting
                    if (request.IsCasual == c.IsCasual)
                    {
                        matches += 1;
                    }
                    if (request.IsRelaxed == c.IsRelaxed)
                    {
                        matches += 1;
                    }
                    if (request.IsBusiness == c.IsBusiness)
                    {
                        matches += 1;
                    }
                    // Temp
                    if (request.IdealTemp != null && request.IdealTemp != 0)
                    {
                        if (request.IdealTemp >= (c.IdealTemp + 15) || (request.IdealTemp <= (c.IdealTemp - 15)))
                        {
                            matches += 1;
                        }
                    }

                    if (matches >= 8)
                    {
                        if (!_TargetedClothes.ContainsKey(c.Type))
                        {
                            _TargetedClothes[c.Type] = new Collection<Cloth>();
                        }
                        _TargetedClothes[c.Type].Add(c);
                    }
                }
            }

            // now make selected articles
            foreach (KeyValuePair<int, Collection<Cloth>> pair in _TargetedClothes)
            {
                // watch nulls
                if (!_SelectedClothes.ContainsKey(pair.Key))
                {
                    _SelectedClothes[pair.Key] = new Collection<Cloth>();
                }
                // check if full (5 at most)
                int i = 0;
                while (_SelectedClothes[pair.Key].Count < 5 && i < pair.Value.Count)
                {
                    // add
                    _SelectedClothes[pair.Key].Add(pair.Value[i]);
                    // shift iterator
                    i += 1;
                }
            }
        }

        public void RotateRackLeft()
        {
            // pop first and get new at end
            foreach(KeyValuePair<int, Collection<Cloth>> pair in _TargetedClothes)
            {
                // can only shift if more than 5 options
                if (pair.Value.Count > 5)
                {
                    // find last
                    int index = _TargetedClothes[pair.Key].ToList().IndexOf(_SelectedClothes[pair.Key].Last());
                    
                    //if last in list, wrap
                    if (index == _TargetedClothes[pair.Key].Count -1)
                    {
                        index = 0;
                    }

                    // get next
                    Cloth insert = _TargetedClothes[pair.Key][index + 1];

                    // drop first
                    _SelectedClothes[pair.Key].Remove(_SelectedClothes[pair.Key].First());

                    // add last
                    _SelectedClothes[pair.Key].Add(insert);
                }
            }
        }

        public void RotateRackRight()
        {
            // pop last and get new at front
            foreach (KeyValuePair<int, Collection<Cloth>> pair in _TargetedClothes)
            {
                // can only shift if more than 5 options
                if (pair.Value.Count > 5)
                {
                    // find last
                    int index = _TargetedClothes[pair.Key].ToList().IndexOf(_SelectedClothes[pair.Key].First());

                    //if first in list, wrap
                    if (index == 0)
                    {
                        index = _TargetedClothes[pair.Key].Count - 1;
                    }

                    // get next
                    Cloth insert = _TargetedClothes[pair.Key][index + 1];

                    // drop first
                    _SelectedClothes[pair.Key].Remove(_SelectedClothes[pair.Key].Last());

                    // add last
                    _SelectedClothes[pair.Key].Insert(0,insert);
                }
            }
        }

        public void GetClothesByDesc(Cloth request)
        {
            if (_Wardrobe == null)
            {
                _Wardrobe = new Collection<Cloth>(_DB.GetClothes());
            }
            // try perfect match
            Template = _Wardrobe.Where(o => o.Description == request.Description).FirstOrDefault();
            // if null try a like for first few words
            if (Template == null)
            {
                // get words
                string[] targets = request.Description.Split(null);
                foreach(string s in targets)
                {
                    Template = _Wardrobe.Where(o => o.Description.Contains(s)).FirstOrDefault();
                    // if ever not null
                    if(Template != null)
                    {
                        break;
                    }
                }                
            }
            // check one last time for null template
            if (Template == null)
            {
                // pass back error
            }
            GetMatchingClothes(Template);
        }

        public void GetClothesByID(Cloth request)
        {
            if (_Wardrobe == null)
            {
                _Wardrobe = new Collection<Cloth>(_DB.GetClothes());
            }
            Template = _Wardrobe.Where(o => o.ClothingID == request.ClothingID).FirstOrDefault();
            if (Template == null)
            {
                // bad request... pass back error
            }
            GetMatchingClothes(Template);
        }
    }
}
