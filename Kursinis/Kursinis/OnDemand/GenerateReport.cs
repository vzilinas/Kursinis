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
        public static StoredReport GetReport()
        {
            var storedReport = new StoredReport
            {
                Date = DateTime.Now
            };
            var reports = new List<Report>();
            using (SqlConnection conn = new SqlConnection("Server=.\\SQLEXPRESS;DataBase=Kursinis;Integrated Security=SSPI"))
            {
                conn.Open();

                // create a command object identifying the stored procedure
                SqlCommand cmd = new SqlCommand("GetReports", conn)
                {

                    // set the command object so it knows to execute a stored procedure
                    CommandType = CommandType.StoredProcedure
                };

                // execute the command
                using (SqlDataReader rdr = cmd.ExecuteReader())
                {
                    // iterate through results, printing each to console
                    while (rdr.Read())
                    {
                        var report = new Report
                        {
                            Shop_Name = rdr[0].ToString(),
                            Income = Convert.ToInt32(rdr[1])
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
