package topology.models;

public class RawBuy {
    private String shop_name;
    private double price;
    private int tax_percent;
    private int amount;

    public String getShop_name() {
      return shop_name;
    }
    public void setShop_name(String shop_name) {
      this.shop_name = shop_name;
    }

    public double getPrice() {
      return price;
    }
    public void setPrice(double price) {
      this.price = price;
    }

    public int getTax_percent() {
      return tax_percent;
    }
    public void setTax_percent(int tax_percent) {
      this.tax_percent = tax_percent;
    }

    public int getAmount() {
      return amount;
    }
    public void setAmount(int amount) {
      this.amount = amount;
    }
}
