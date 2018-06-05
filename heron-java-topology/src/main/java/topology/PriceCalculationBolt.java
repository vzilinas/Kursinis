package topology;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.heron.api.bolt.BaseBasicBolt;
import com.twitter.heron.api.bolt.BasicOutputCollector;
import com.twitter.heron.api.topology.OutputFieldsDeclarer;

import com.twitter.heron.api.tuple.Fields;
import com.twitter.heron.api.tuple.Tuple;
import com.twitter.heron.api.tuple.Values;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import topology.models.CalculatedBuy;
import topology.models.RawBuy;

import java.io.IOException;

/**
 * This Bolt splits a sentence into words
 */
public class PriceCalculationBolt extends BaseBasicBolt {
    private static final Logger logger = LogManager.getLogger(PriceCalculationBolt.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    //Execute is called to process tuples
    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        logger.info(String.format("Cought (%s)", tuple));
        //Get the sentence content from the tuple
        String rawbuy = tuple.getString(0);

        RawBuy raw = null;

        try {
            raw = objectMapper.readValue(rawbuy, RawBuy.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        CalculatedBuy cb = new CalculatedBuy();

        cb.setShop_name(raw.getShop_name());
        double price = (raw.getPrice() + (raw.getPrice() * raw.getTax_percent() / 100)) * raw.getAmount();
        cb.setCalculated_price(price);
        logger.info("calculated price: " + cb);
        //An iterator to get each word
        String value = null;
        try {
            value = objectMapper.writer().writeValueAsString(cb);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        collector.emit(new Values(value));
    }

    //Declare that emitted tuples contain a word field
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("calculatedbuy"));
    }
}
