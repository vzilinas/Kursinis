from kafka import KafkaProducer
from kafka.errors import KafkaError
import json


def on_send_success(record_metadata):
    print(record_metadata.topic)
    print(record_metadata.partition)
    print(record_metadata.offset)

def on_send_error(excp):
    print('I am an errback' + excp)
    # handle exception


data = {}
data['price'] = "600.10"
data['tax_percent'] = "10"
data['amount'] = "1"
data['shop_name'] = "maximalce"
json_data = json.dumps(data).encode('utf-8')
print(json_data)
producer = KafkaProducer(bootstrap_servers='localhost:9092')
producer.send('generate_report',value=json_data).add_callback(on_send_success).add_errback(on_send_error)
print(producer)
producer.flush()
