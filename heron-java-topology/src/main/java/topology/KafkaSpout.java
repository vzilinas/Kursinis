package topology;

import com.twitter.heron.api.spout.BaseRichSpout;
import com.twitter.heron.api.spout.SpoutOutputCollector;
import com.twitter.heron.api.topology.OutputFieldsDeclarer;
import com.twitter.heron.api.topology.TopologyContext;
import com.twitter.heron.api.tuple.Fields;
import com.twitter.heron.api.tuple.Values;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 * This spout randomly emits sentences
 */
public class KafkaSpout extends BaseRichSpout {
    private static final Logger logger = LogManager.getLogger(KafkaSpout.class);

    // Collector used to emit output
    private SpoutOutputCollector collector;

    private Consumer<String, String> consumer;

    // Used to generate a random number
    // Open is called when an instance of the class is created
    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector collector) {
        // Set the instance collector to the one passed in
        this.collector = collector;
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("generate-report"));
    }

    //Emit data to the stream
    @Override
    public void nextTuple() {
        // Emit the sentence
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
            for (ConsumerRecord<String, String> record : records) {
                collector.emit(new Values(record.value()));
                logger.info("emited: " + record.value());
            }
        }

    }

    // Declare the output fields. In this case, an sentence
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("rawbuy"));
    }

}
