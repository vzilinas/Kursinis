from kafka import KafkaProducer
import json
import random
import string
import time

producer = KafkaProducer(bootstrap_servers='localhost:9092')

throttle_amount = 0.0005 #defines the amount of throttle required to reach optimal throughput
for x in range(0, 100000):
	data = {}
	data['price'] = random.random() * 1000
	data['product_id'] = random.randint(1, 2000000)
	data['tax_percent'] = random.randint(1, 20)
	data['amount'] = random.randint(1, 5)
	data['shop_name'] = "".join([random.choice(string.letters[:5]) for i in xrange(5)])
	json_data = json.dumps(data).encode('utf-8')
	producer.send('generate_report',value=json_data)
	time.sleep(throttle_amount)
