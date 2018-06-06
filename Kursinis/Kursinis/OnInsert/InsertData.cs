using Confluent.Kafka;
using Confluent.Kafka.Serialization;
using Core;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace Kursinis.OnInsert
{
    public class InsertData
    {
        public static bool Insert(InsertRequest request)
        {
            string query = SqlQueries.InsertQuery();

            using (SqlConnection conn = new SqlConnection("Server=.\\SQLEXPRESS;DataBase=Kursinis;Integrated Security=SSPI"))
            {
                SqlCommand myCommand = new SqlCommand(query, conn);
                myCommand.Parameters.AddWithValue("@Id", request.Id);
                myCommand.Parameters.AddWithValue("@ProductId", request.ProductId);
                myCommand.Parameters.AddWithValue("@Amount", request.Amount);

                myCommand.ExecuteNonQuery();
            }

            var config = new Dictionary<string, object>
            {
                { "bootstrap.servers", "localhost:9092" }
            };
            using (var producer = new Producer<Null, string>(config, null, new StringSerializer(Encoding.UTF8)))
            {
                var dr = producer.ProduceAsync("ReportGenerator", null, DateTime.Now.ToString("yyyyMMdd_HHmmssfff")).Result;
            }
            return true;
        }
    }
}
