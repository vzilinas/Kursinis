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

            using (SqlConnection conn = new SqlConnection("Server=.\\SQLEXPRESS;DataBase=SMM;Integrated Security=SSPI"))
            {
                SqlCommand myCommand = new SqlCommand(query, conn);
                myCommand.Parameters.AddWithValue("@Kodas", request.Kodas);
                myCommand.Parameters.AddWithValue("@Pavadinimas", request.Pavadinimas);
                myCommand.Parameters.AddWithValue("@Nuo", request.Nuo);
                myCommand.Parameters.AddWithValue("@Iki", request.Iki);
                myCommand.Parameters.AddWithValue("@BlobaiId", request.BlodaiId);
                myCommand.Parameters.AddWithValue("@Galioja", request.Galioja);
                myCommand.Parameters.AddWithValue("@SablonaiId", request.SablonaiId);
                myCommand.Parameters.AddWithValue("@IstaigosId", request.IstaigosId);
                myCommand.Parameters.AddWithValue("@FunkcijosId", request.FunkcijosId);
                myCommand.Parameters.AddWithValue("@ProgramosId", request.ProgramosId);
                myCommand.Parameters.AddWithValue("@AktyvusPeriodaiId", request.AktyvusPeriodaiId);
                myCommand.Parameters.AddWithValue("@Processed", request.Processed);
                myCommand.Parameters.AddWithValue("@Processor", request.Processor);

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
