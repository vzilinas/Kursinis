using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Core;
using Microsoft.AspNetCore.Mvc;
using Nest;

namespace ReportSearch.Controllers
{
    [Route("api/[controller]")]
    public class ReportController : Controller
    {
        // GET api/values
        [HttpGet]
        public StoredReport GetLatest()
        {
            var reports = new StoredReport();
            var node = new Uri("http://localhost:9200");
            var settings = new ConnectionSettings(node);
            var client = new ElasticClient(settings);

            var indices = client.CatIndices();
            var latestIndice = indices.Records.OrderByDescending(x => x.Index).First();

            reports.Reports = new List<Report>();
            reports.Date = DateTime.ParseExact(latestIndice.Index.Remove(0, ("reportindex_").Count()), "yyyyMMdd_HHmmssfff", System.Globalization.CultureInfo.InvariantCulture);

            if (latestIndice == null)
            {
                return reports;
            }
            var search = client.Search<Report>(s => s
                .Index(latestIndice.Index)
                .Size(10000)
                .Scroll("1m"));

            while (search.Documents.Any())
            {
                reports.Reports.AddRange(search.Documents);

                search = client.Scroll<Report>("1m", search.ScrollId);
            }

            client.ClearScroll(c => c.ScrollId(search.ScrollId));

            return reports;
        }
    }
}
