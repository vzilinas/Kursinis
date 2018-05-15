using Core;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;

namespace Kursinis.OnInsert
{
    public class GenerateReport
    {
        public static async Task<StoredReport> GetReportAsync()
        {
            var uri = "http://localhost:52320/api/report";

            var client = new HttpClient();

            var reportsTask = await client.GetAsync(uri);

            var result = JsonConvert.DeserializeObject<StoredReport>(await reportsTask.Content.ReadAsStringAsync());

            return result;
        }
    }
}
