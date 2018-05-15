using Confluent.Kafka;
using Confluent.Kafka.Serialization;
using System;
using System.Collections.Generic;
using System.Text;

namespace ReportGenerator
{
    class Program
    {
        static void Main(string[] args)
        {
            var conf = new Dictionary<string, object>
            {
              { "group.id", "generate-report-group" },
              { "bootstrap.servers", "localhost:9092" },
              { "auto.commit.interval.ms", 5000 },
              { "auto.offset.reset", "earliest" }
            };

            using (var consumer = new Consumer<Null, string>(conf, null, new StringDeserializer(Encoding.UTF8)))
            {
                consumer.OnMessage += (_, msg) =>
                {
                    GenerateReport.Generate(msg.Value);
                };
                consumer.Subscribe("ReportGenerator");

                while (true)
                {
                    consumer.Poll(TimeSpan.FromMilliseconds(100));
                }
            }
        }
    }
}
