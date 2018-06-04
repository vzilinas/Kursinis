# Import Bolt type from heronpy
from heronpy.api.bolt.bolt import Bolt

# PriceCalculationBolt class that inherits from heron Bolt
class PriceCalculationBolt(Bolt):
    # Important : Define output field tags for the Bolt
    outputs = ['calculated_buy']
    #{
    #   calculated_price: number
    #   shop_name: string
    #}

    def initialize(self, config, context):
        # A log context is provided in the context of the spout
        self.log("Initializing PriceCalculationBolt...")

    # Process incoming tuple and emit output
    def process(self, tup):
        #tup:
        #{
        #   price: number
        #   tax_percent: number
        #   amount: number
        #   shop_name: string
        #}
        # Accept a sentence string and spit into words via space delimiter
        price = (tup["price"] + (tup["price"] * tup["tax_percent"] / 100)) * tup["amount"]
        dict = {"calculated_price" : price, "shop_name" : tup["shop_name"]}
        self.emit(dict)
