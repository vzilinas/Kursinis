package topology;

import com.twitter.heron.api.Config;
import com.twitter.heron.api.HeronSubmitter;
import com.twitter.heron.api.bolt.IRichBolt;
import com.twitter.heron.api.topology.TopologyBuilder;
import com.twitter.heron.api.tuple.Fields;

public final class ReportGenerationTopology {

    private ReportGenerationTopology() {}

    public static void main(String[] args) throws Exception {

        // Instantiate a topology builder to build the tag
        TopologyBuilder builder = new TopologyBuilder();


        // Define the parallelism hint for the topololgy
        final int parallelism = 1;

        // Build the topology to have a 'word' spout and 'exclaim' bolt
        // also, set the 'word' spout bolt to have two instances
        builder.setSpout("kafka-spout", new KafkaSpout(), parallelism);

        // Specify that 'exclaim1' bolt should consume from 'word' spout using
        // Shuffle grouping running in four instances
        builder.setBolt("calculate-price-bolt", (IRichBolt) new PriceCalculationBolt())
                .fieldsGrouping("kafka-spout", new Fields("rawbuy"));

        builder.setBolt("aggregate-price-bolt", new PriceAggregationBolt(), parallelism)
                .fieldsGrouping("calculate-price-bolt", new Fields("calculatedbuy"));

        Config topologyConfig = new Config();

        // Apply effectively-once semantics and set the checkpoint interval to 10 seconds
        topologyConfig.setTopologyReliabilityMode(Config.TopologyReliabilityMode.EFFECTIVELY_ONCE);
        topologyConfig.setTopologyStatefulCheckpointIntervalSecs(10);

        HeronSubmitter.submitTopology("ReportGenerationTopology", topologyConfig, builder.createTopology());
    }
}
