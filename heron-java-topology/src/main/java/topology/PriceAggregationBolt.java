package topology;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import topology.models.CalculatedBuy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This Bolt splits a sentence into words
 */
public class PriceAggregationBolt extends BaseBasicBolt {
    private static final long serialVersionUID = 3328511628150069047L;
    private Map<String, Double> hashMap = new HashMap<String, Double>();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PriceAggregationBolt.class.getName());
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
