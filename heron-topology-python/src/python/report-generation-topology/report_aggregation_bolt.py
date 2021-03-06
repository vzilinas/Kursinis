# Import Bolt type from heronpy
from heronpy.api.bolt.bolt import Bolt

# Word Count Bolt that inherits from heron Bolt
class ReportAggregationBolt(Bolt):
    # Important : Define output field tags for the Bolt
    outputs = ['report']
    #[
    #   shop_name(string) : all_income(number)
    #]

    def initialize(self, config, context):
        # A log context is provided in the context of the spout
        self.log("Initializing ReportAggregationBolt...")
        # We initialize a python collections Counter for recording hte word count
        self.reports = {}

    # # Process Periodic tick tuple to log and emit output
    # def process_tick(self, tup):
    #     # If tuple type is tick type a special periodic interval entry generate logs and emit sequence
    #     for value, count in self.counter.most_common():
    #         self.log("Emitting a count of ({}) for word ({})".format(value, count))
    #         self.emit([value, count])

    # Process Word stream to aggregate to word count
    def process(self, tup):
        income = tup.values[0]
        self.log("Caught calculated buy from bolt:" + income)
        #tup:
        #{
        #   calculated_price: number
        #   shop_name: string
        #}
        if tup["shop_name"] in self.reports:
            self.reports[income["shop_name"]] += income["calculated_price"]
        else:
            self.reports[income["shop_name"]] = income["calculated_price"]
