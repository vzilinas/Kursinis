USE [Kursinis]

DECLARE @Id INT
SET @Id = 1

WHILE @Id < 2000001
BEGIN
	INSERT INTO [dbo].[Product]
			   ([Id]
			   ,[Price]
			   ,[Tax]
			   ,[ShopId]
			   ,[Name])
		 VALUES
			   (@Id
			   ,Round(RAND()*(100-1+1)+1, 3)
			   ,FLOOR(RAND()*(20-1+1)+1)
			   ,FLOOR(RAND()*(2000-1+1)+1)
			   ,CONVERT(varchar(255), NEWID()))
	
	SET @Id += 1
END


