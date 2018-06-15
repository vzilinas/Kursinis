from kafka import KafkaConsumer
from os import getenv
import json
import pyodbc
from datetime import datetime


consumer = KafkaConsumer('generate_report',
                         bootstrap_servers=['localhost:9092'])
                         #auto_offset_reset='earliest')

KafkaConsumer(value_deserializer=lambda m: json.loads(m.decode('utf-8')))
conn = pyodbc.connect(
    r'DRIVER={SQL Server};'
    r'SERVER=localhost\SQLEXPRESS;'
    r'DATABASE=Kursinis;'
    r'Trusted_Connection=yes;'
    )

cursor = conn.cursor()
sqlinsert = """
        INSERT INTO [dbo].[Buys]
                ([Amount]
                ,[ProductId])
            VALUES
                ({amount}
                ,{product_id})
    """
count = 0
for message in consumer:
    data = json.loads(message.value)
    insertquery= sqlinsert.format(amount=data['amount'], product_id=data["product_id"])
    cursor.execute(insertquery)
    conn.commit()
    if(count % 100000 == 0):
        print(str(datetime.now()) + ' ' + str(count))
    count += 1

