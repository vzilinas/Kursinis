import pyodbc
from datetime import datetime

conn = pyodbc.connect(
    r'DRIVER={SQL Server};'
    r'SERVER=localhost\SQLEXPRESS;'
    r'DATABASE=Kursinis;'
    r'Trusted_Connection=yes;'
    )

cursor = conn.cursor()

print("Started: " + str(datetime.now()))
rows = cursor.execute("{CALL dbo.GetReports}")
print("Ended: " + str(datetime.now()))
