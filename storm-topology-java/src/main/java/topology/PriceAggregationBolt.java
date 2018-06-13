package topology;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Fields;

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
    private int counter = 1;
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
        String name = cb.getShop_name();
        double price = cb.getCalculated_price();
        if (hashMap.containsKey(name)) {
            hashMap.put(name, hashMap.get(name) + price);
        } else {
            hashMap.put(name, price);
        }
        //logger.info("Current report: " + name + " " + price);
        if (counter % 10000 == 0) {
            logger.info("Current count in reporting part: " + counter);
        }
        counter++;
    }

    //Declare that emitted tuples contain a word field
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("report"));
    }
}
