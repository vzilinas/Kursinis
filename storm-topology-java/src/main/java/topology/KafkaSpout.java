package topology;


import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;


import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * This spout randomly emits sentences
 */
public class KafkaSpout extends BaseRichSpout {
    private static final long serialVersionUID = 3328511128150069047L;

    private static final Logger logger = Logger.getLogger(KafkaSpout.class.getName());

    // Collector used to emit output
    private SpoutOutputCollector collector;

    private Consumer<String, String> consumer;
    private int counter = 1;

    // Used to generate a random number
    // Open is called when an instance of the class is created
    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector collector) {
        logger.info("testing logger1");

        // Set the instance collector to the one passed in
        this.collector = collector; 
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "kafka-group");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList("generate_report"));
        logger.info("testing logger2");
    }

    //Emit data to the stream
    @Override
    public void nextTuple() {
        // Emit the sentence
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                collector.emit(new Values(record.value()));
                if (counter % 10000 == 0) {
                    logger.info("Current count in kafka spout: " + counter);
                }
                counter++;
            }
        }


    }

    // Declare the output fields. In this case, an sentence
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("rawbuy"));
    }

}
