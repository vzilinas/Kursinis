USE [Kursinis]
GO

DECLARE @Id INT
SET @Id = 1

WHILE @Id < 2000001
BEGIN
	INSERT INTO [dbo].[Product]
			   ([Id]
			   ,[Price]
			   ,[Tax]
			   ,[Discount]
			   ,[ShopId]
			   ,[Name])
		 VALUES
			   (@Id
			   ,Round(RAND()*(1000-1+1)+1, 2)
			   ,FLOOR(RAND()*(20-1+1)+1)
			   ,NULL
			   ,FLOOR(RAND()*(2000-1+1)+1)
			   ,CONVERT(varchar(255), NEWID()))
	
	SET @Id += 1
END


