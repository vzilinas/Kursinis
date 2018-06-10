using System;
using System.Collections.Generic;
using System.Text;

namespace Core
{
    public class StoredReport
    {
        public DateTime Date { get; set; }
        public List<Report> Reports { get; set; }
    }
}
