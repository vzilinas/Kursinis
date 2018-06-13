from kafka import KafkaProducer
from kafka.errors import KafkaError
import json
import random
import string
import time
from datetime import datetime


producer = KafkaProducer(bootstrap_servers='localhost:9092', max_block_ms=1)

messages = []
#throttle_amount = 0.0009 #defines the amount of throttle required to reach optimal throughput
for x in range(0, 500000):
	data = {}
	data['price'] = round(random.random() * 100, 3)
	#data['product_id'] = random.randint(1, 2000000)
	data['tax_percent'] = random.randint(1, 20)
	data['amount'] = random.randint(1, 5)
	data['shop_name'] = "".join([random.choice(string.letters[:5]) for i in xrange(6)])
	json_data = json.dumps(data).encode('utf-8')
	messages.append(json_data)
	
print(str(datetime.now()))
print("sending 1")	
for msg in messages:
	#time.sleep(throttle_amount)
	producer.send('generate_report', msg)
print("sent 1")
print(str(datetime.now()))
print("sending 2")	
for msg in messages:
	#time.sleep(throttle_amount)
	producer.send('generate_report', msg)

print("sent 2")
print(str(datetime.now()))
print("sending 3")	
for msg in messages:
	#time.sleep(throttle_amount)
	producer.send('generate_report', msg)
print("sent 3")
print(str(datetime.now()))
producer.flush()
