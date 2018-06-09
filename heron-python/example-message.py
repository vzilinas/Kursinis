from kafka import KafkaProducer
from kafka.errors import KafkaError
import json
import random
import string


def on_send_success(record_metadata):
    print(record_metadata.topic)
    print(record_metadata.partition)
    print(record_metadata.offset)

def on_send_error(excp):
    print('I am an errback' + excp)
    # handle exception


producer = KafkaProducer(bootstrap_servers='localhost:9092')
for x in range(0, 100000):
	data = {}
	data['price'] = 100
	data['tax_percent'] = 10
	data['amount'] = 1
	data['shop_name'] = "".join( [random.choice(string.letters[:5]) for i in xrange(5)] )
	json_data = json.dumps(data).encode('utf-8')
	producer.send('generate_report',value=json_data)
print(producer)
producer.flush()
