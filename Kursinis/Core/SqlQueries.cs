using System;
using System.Collections.Generic;
using System.Text;

namespace Core
{
    public class SqlQueries
    {
        public static string InsertQuery()
        {
            return @"USE [SMM]
                            GO

                            INSERT INTO [dbo].[Ataskaitos]
                                        ([AtaskaitosId]
                                        ,[Kodas]
                                        ,[Pavadinimas]
                                        ,[Nuo]
                                        ,[Iki]
                                        ,[BlobaiId]
                                        ,[Galioja]
                                        ,[SablonaiId]
                                        ,[IstaigosId]
                                        ,[FunkcijosId]
                                        ,[ProgramosId]
                                        ,[AktyvusPeriodaiId]
                                        ,[Processed]
                                        ,[Processor])
                                    VALUES
                                        (@Kodas,
                                         @Pavadinimas,
                                         @Nuo,
                                         @Iki,
                                         @BlobaiId,
                                         @Galioja,
                                         @SablonaiId,
                                         @IstaigosId,
                                         @FunkcijosId,
                                         @ProgramosId,
                                         @AktyvusPeriodaiId, 
                                         @Processed, 
                                         @Processor)
                            GO";
        }
    }
}
