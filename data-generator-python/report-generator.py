from datetime import datetime
import time
import pyodbc

conn = pyodbc.connect(
    r'DRIVER={SQL Server};'
    r'SERVER=localhost\SQLEXPRESS;'
    r'DATABASE=Kursinis;'
    r'Trusted_Connection=yes;')

cursor = conn.cursor()

while True:
    print("Started: " + str(datetime.now()))
    rows = cursor.execute("{CALL dbo.GetReports}")
    print("Ended: " + str(datetime.now()))
    time.sleep(5)
