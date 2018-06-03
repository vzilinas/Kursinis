USE [Kursinis]
GO

DECLARE @Id INT
SET @Id = 1

WHILE @Id < 20000001
BEGIN
	INSERT INTO [dbo].[Buys]
			   ([Id]
			   ,[ProductId]
			   ,[Amount])
		 VALUES
			   (@Id
			   ,FLOOR(RAND()*(2000000-1+1)+1)
			   ,FLOOR(RAND()*(5-1+1)+1))
	
	SET @Id += 1
END


