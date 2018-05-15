using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Core;
using Microsoft.AspNetCore.Mvc;

namespace Kursinis.Controllers
{
    [Route("api/[controller]")]
    public class ReportController : Controller
    {
        [HttpGet("[action]")]
        public StoredReport GenerateReportA()
        {
            var result = OnDemand.GenerateReport.GetReport();
            return result;
        }

        [HttpPost("[action]")]
        public bool InsertDataA([FromBody]InsertRequest request)
        {
            var result = OnDemand.InsertData.Insert(request);
            return result;
        }
        [HttpGet("[action]")]
        public async Task<StoredReport> GenerateReportB()
        {
            var result = OnInsert.GenerateReport.GetReportAsync();
            return await result;
        }

        [HttpPost("[action]")]
        public bool InsertDataB([FromBody]InsertRequest request)
        {
            var result = OnInsert.InsertData.Insert(request);
            return result;
        }
    }
}
