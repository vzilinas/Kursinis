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
import topology.models.RawBuy;

import java.io.IOException;

/**
 * This Bolt splits a sentence into words
 */
public class PriceCalculationBolt extends BaseBasicBolt {
    private static final long serialVersionUID = 3328511428150069047L;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PriceCalculationBolt.class.getName());
    private ObjectMapper objectMapper = new ObjectMapper();

    //Execute is called to process tuples
    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        //Get the sentence content from the tuple
        String rawbuy = tuple.getString(0);

        RawBuy raw = null;

        try {
            raw = objectMapper.readValue(rawbuy, RawBuy.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        CalculatedBuy cb = new CalculatedBuy();
		String shopName = raw.getShop_name();
        cb.setShop_name(shopName);
        double price = (raw.getPrice() - (raw.getPrice() * raw.getTax_percent() / 100)) * raw.getAmount();
        cb.setCalculated_price(price);
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
