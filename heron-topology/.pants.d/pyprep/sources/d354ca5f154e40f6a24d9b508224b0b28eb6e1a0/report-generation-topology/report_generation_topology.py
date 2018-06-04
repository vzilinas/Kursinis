# Import Grouping and TopologyBuilder from heronpy
from heronpy.api.stream import Grouping
from heronpy.api.topology import TopologyBuilder
# import heronpy.api.api_constants

# Import the defined Bolts and Spouts
from report_aggregation_bolt import ReportAggregationBolt
from price_calculation_bolt import PriceCalculationBolt
from kafka_input_spout import KafkaInputSpout

if __name__ == '__main__':
    # Define the topology name
    builder = TopologyBuilder("Report_Generation_Topology")

    # Define the topology dag

    # Start with the random sentence generator, create a reference and define a parallelism hint with par attribute
    kafka_input_spout = builder.add_spout("kafka_input_spout", KafkaInputSpout, par=1)

    # Link the output of the random sentence generator to the split sentence bolt, the input grouping is
    # done on the field `sentence`
    price_calculation_bolt_inputs = {
        kafka_input_spout: Grouping.fields('raw_buy')
    }
    # Define the split sentence bolt with the input context defined above and a parallelism hint.
    price_calculation_bolt = builder.add_bolt("price_calculation_bolt", PriceCalculationBolt, par=1,
                                           inputs=price_calculation_bolt_inputs)

    # Link the output of the split sentence bolt to the word count bolt, the input grouping is
    # done on the field `word`
    report_aggregation_bolt_inputs = {
        price_calculation_bolt: Grouping.fields('calculated_buy')
    }

    # # Define emit frequency in seconds, this is used throttle word_count_bolt, log and emit output frequency
    # emit_frequency = 3;
    #
    # config = {
    #     constants.TOPOLOGY_TICK_TUPLE_FREQ_SECS: emit_frequency
    # }

    # Define the word count bolt with the input context defined above and a parallelism hint and emit frequency config
    # and
    report_aggregation_bolt = builder.add_bolt("report_aggregation_bolt", ReportAggregationBolt, par=1,
                                       inputs=report_aggregation_bolt_inputs)

    # Finalize the topology graph
    builder.build_and_submit()
