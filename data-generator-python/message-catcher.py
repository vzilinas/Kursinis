from kafka import KafkaConsumer

# To consume latest messages and auto-commit offsets
consumer = KafkaConsumer('generate-report',
                         bootstrap_servers=['localhost:9092'],
                         auto_offset_reset='earliest')
KafkaConsumer(value_deserializer=lambda m: json.loads(m.decode('ascii')))
for message in consumer:
    # message value and key are raw bytes -- decode if necessary!
    # e.g., for unicode: `message.value.decode('utf-8')`
    print ("%s:%d:%d: key=%s value=%s" % (message.topic, message.partition,
                                          message.offset, message.key,
                                          message.value))
