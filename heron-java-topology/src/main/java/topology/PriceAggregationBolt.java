package topology;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.heron.api.bolt.BaseBasicBolt;
import com.twitter.heron.api.bolt.BasicOutputCollector;
import com.twitter.heron.api.bolt.OutputCollector;
import com.twitter.heron.api.topology.OutputFieldsDeclarer;
import com.twitter.heron.api.topology.TopologyContext;
import com.twitter.heron.api.tuple.Fields;
import com.twitter.heron.api.tuple.Tuple;
import com.twitter.heron.api.tuple.Values;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import topology.models.CalculatedBuy;
import topology.models.RawBuy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This Bolt splits a sentence into words
 */
public class PriceAggregationBolt extends BaseBasicBolt {
    private Map<String, Double> hashMap = new HashMap<String, Double>();
    private static final Logger logger = LogManager.getLogger(PriceCalculationBolt.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    //Execute is called to process tuples
    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        //Get the sentence content from the tuple
        String calculatedBuy = tuple.getString(0);

        CalculatedBuy cb = null;

        try {
            cb = objectMapper.readValue(calculatedBuy, CalculatedBuy.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(hashMap.containsKey(cb.getShop_name()))
        {
            hashMap.put(cb.getShop_name(), hashMap.get(cb.getShop_name()) + cb.getCalculated_price());
        }
        else
        {
            hashMap.put(cb.getShop_name(), cb.getCalculated_price());
        }
        try {
            logger.info("Current report" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(hashMap));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        collector.emit(new Values(hashMap));

    }

    //Declare that emitted tuples contain a word field
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("report"));
    }
}
