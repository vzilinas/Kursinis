# Import spout type from heronpy
from heronpy.api.spout.spout import Spout
from kafka import KafkaConsumer

# KafkaInputSpout class that inherits from heron Spout
class KafkaInputSpout(Spout):
    # Important : Define output field tags for the Spout
    outputs = ['raw_buy']
    #{
    #   price: number
    #   tax_percent: number
    #   amount: number
    #   shop_name: string
    #}

    # Spout initialization
    def initialize(self, config, context):
        # A log context is provided in the context of the spout
        self.log("Initializing KafkaInputSpout...")
        consumer = KafkaConsumer('generate_report', bootstrap_servers="localhost:9092",auto_offset_reset='earliest')
        KafkaConsumer(value_deserializer=lambda m: json.loads(m.decode('ascii')))


    # Generate next tuple sequence for this spout
    def next_tuple(self):
        for msg in consumer:
            message = msg.value
            self.log("Caught message ({})".format(message))
            self.emit([message]) # Emit message to go to next phase in the topology