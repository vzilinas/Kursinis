from kafka import KafkaProducer
from datetime import datetime
import json
import random
import string

producer = KafkaProducer(bootstrap_servers='localhost:9092')

messages = []
for x in range(0, 1500000):
	data = {}
	data['price'] = round(random.random() * 100, 3)
	data['product_id'] = random.randint(1, 2000000)
	data['tax_percent'] = random.randint(1, 20)
	data['amount'] = random.randint(1, 5)
	data['shop_name'] = "".join([random.choice(string.letters[:13]) for i in xrange(3)])
	json_data = json.dumps(data).encode('utf-8')
	messages.append(json_data)
	
print(str(datetime.now()) + " started sending")	

for msg in messages:
	time.sleep(throttle_amount)
	producer.send('generate_report', msg)

print(str(datetime.now()) + " sent")