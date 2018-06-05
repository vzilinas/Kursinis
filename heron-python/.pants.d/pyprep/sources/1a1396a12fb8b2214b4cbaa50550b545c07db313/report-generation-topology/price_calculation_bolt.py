# Import Bolt type from heronpy
from heronpy.api.bolt.bolt import Bolt

# PriceCalculationBolt class that inherits from heron Bolt
class PriceCalculationBolt(Bolt):
    # Important : Define output field tags for the Bolt
    outputs = ['calculatedbuy']
    #{
    #   calculated_price: number
    #   shop_name: string
    #}

    def initialize(self, config, context):
        # A log context is provided in the context of the spout
        self.log("Initializing PriceCalculationBolt...")

    # Process incoming tuple and emit output
    def process(self, tup):
        self.ack(tup)
        self.logger.info("basic tuple" + tup)
        buy = tup.values[0]
        self.logger.info("Caught raw data from spout:" + buy)
        #buy:
        #{
        #   price: number
        #   tax_percent: number
        #   amount: number
        #   shop_name: string
        #}
        # Accept a sentence string and spit into words via space delimiter
        price = (buy["price"] + (buy["price"] * buy["tax_percent"] / 100)) * buy["amount"]
        dict = {"calculated_price" : price, "shop_name" : buy["shop_name"]}
        self.logger.info("dict:" + dict)
        self.emit([dict])
