using System;
using System.Collections.Generic;
using System.Text;

namespace Core
{
    public class InsertRequest
    {
        public string Kodas { get; set; }
        public string Pavadinimas { get; set; }
        public DateTime Nuo { get; set; }
        public DateTime Iki { get; set; }
        public int? BlodaiId { get; set; }
        public bool Galioja { get; set; }
        public int SablonaiId { get; set; }
        public int IstaigosId { get; set; }
        public int? FunkcijosId { get; set; }
        public int? ProgramosId { get; set; }
        public int? AktyvusPeriodaiId { get; set; }
        public DateTime? Processed { get; set; }
        public string Processor { get; set; }
    }
}
