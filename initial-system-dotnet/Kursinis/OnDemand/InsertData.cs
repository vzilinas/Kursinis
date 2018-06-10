using Core;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Threading.Tasks;

namespace Kursinis.OnDemand
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
            return true;
        }
    }
}
