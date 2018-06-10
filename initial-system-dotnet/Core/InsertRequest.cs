using System;
using System.Collections.Generic;
using System.Text;

namespace Core
{
    public class InsertRequest
    {
        public int Id { get; set; }
        public int ProductId { get; set; }
        public int Amount { get; set; }
        public string ShopName { get; set; }
        public decimal Price { get; set; }
        public int Tax { get; set; }
    }
}
