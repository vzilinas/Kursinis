using System;
using System.Collections.Generic;
using System.Text;

namespace Core
{
    public class SqlQueries
    {
        public static string InsertQuery()
        {
            return @"INSERT INTO [dbo].[Buys]
                               ([Id]
                               ,[ProductId]
                               ,[Amount])
                         VALUES
                               (@Id
                               ,@ProductId
                               ,@Amount)
                    GO";
        }
    }
}
