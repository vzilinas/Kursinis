using Core;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Threading.Tasks;

namespace Kursinis.OnDemand
{
    public class GenerateReport
    {
        const int SablonaiId = 138;
        const int AktyvusPeriodaiId = 22;
        public static StoredReport GetReport()
        {
            var storedReport = new StoredReport
            {
                Date = DateTime.Now
            };
            var reports = new List<Report>();
            using (SqlConnection conn = new SqlConnection("Server=.\\SQLEXPRESS;DataBase=SMM;Integrated Security=SSPI"))
            {
                conn.Open();

                // create a command object identifying the stored procedure
                SqlCommand cmd = new SqlCommand("KonsolidavimasPlatyn", conn);

                // set the command object so it knows to execute a stored procedure
                cmd.CommandType = CommandType.StoredProcedure;

                // add parameter to command, which will be passed to the stored procedure
                cmd.Parameters.Add(new SqlParameter("@SablonaiId", SablonaiId));
                cmd.Parameters.Add(new SqlParameter("@AktyvusPeriodaiId", AktyvusPeriodaiId));
                cmd.CommandTimeout = 0;

                // execute the command
                using (SqlDataReader rdr = cmd.ExecuteReader())
                {
                    // iterate through results, printing each to console
                    while (rdr.Read())
                    {
                        var report = new Report
                        {
                            Eil = Convert.ToInt32(rdr["Eil"]),
                            Eilute = rdr["Eilute"].ToString(),
                            IstaigosId = Convert.IsDBNull(rdr["IstaigosId"]) ? 0 : Convert.ToInt32(rdr["IstaigosId"]),
                            Kodas = rdr["Kodas"].ToString(),
                            Kol = rdr["Kol"].ToString(),
                            Kolonele = rdr["Kolonele"].ToString(),
                            Pavadinimas = rdr["Pavadinimas"].ToString(),
                            Suma = Convert.IsDBNull(rdr["Suma"]) ? 0 : (decimal?)rdr["Suma"],
                            Tekstas = rdr["Tekstas"].ToString(),
                            Width = rdr["Width"].ToString(),
                            Zona = rdr["Zona"].ToString()
                        };
                        reports.Add(report);
                    }
                }
            }
            storedReport.Reports = reports;
            return storedReport;
        }
    }
}
