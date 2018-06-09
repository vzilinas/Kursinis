package topology;

import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.Config;
import org.apache.storm.tuple.Fields;


public class ReportGenerationTopology {

    public static void main(String[] args) throws Exception {

        // Instantiate a topology builder to build the tag
        TopologyBuilder builder = new TopologyBuilder();


        // Define the parallelism hint for the topololgy
        int parallelism = 1;

        // Build the topology to have a 'word' spout and 'exclaim' bolt
        // also, set the 'word' spout bolt to have two instances
        builder.setSpout("kafka-spout", new KafkaSpout(), parallelism);

        // Specify that 'exclaim1' bolt should consume from 'word' spout using
        // Shuffle grouping running in four instances
        builder.setBolt("calculate-price-bolt", new PriceCalculationBolt(), parallelism)
                .fieldsGrouping("kafka-spout", new Fields("rawbuy"));

        builder.setBolt("aggregate-price-bolt", new PriceAggregationBolt(), parallelism)
                .fieldsGrouping("calculate-price-bolt", new Fields("calculatedbuy"));

        Config topologyConfig = new Config();

        StormSubmitter.submitTopology("ReportGenerationTopology", topologyConfig, builder.createTopology());
    }
}
