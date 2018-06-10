USE [Kursinis]
GO

DECLARE @Id INT
SET @Id = 1

WHILE @Id < 2001
BEGIN
	INSERT INTO [dbo].[Shop]
			   ([Id]
			   ,[Name]
			   ,[Address])
		 VALUES
			   (@Id
			   ,CONVERT(varchar(255), NEWID())
			   ,CONVERT(varchar(255), NEWID()))
			   
	SET @Id += 1
END
