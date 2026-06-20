import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Stock implements CsvHandler {
    protected String stockId;
    protected String symbol;
    protected String name;
    protected double price;
    protected double openPrice;
    protected String sector;
    protected List<Double> priceHistory;

    public Stock() {
        this.stockId = generateRandomId();
        this.symbol = "N/A";
        this.name = "Unknown";
        this.sector = "General";
        this.priceHistory = new ArrayList<>();
    }

    public Stock(String symbol, String name, String sector, double price) {
        this.stockId = generateRandomId();
        this.symbol = symbol.toUpperCase();
        this.name = name;
        this.sector = sector;
        this.price = price;
        this.openPrice = price;
        this.priceHistory = new ArrayList<>();
        this.priceHistory.add(price);
    }

    public static String generateRandomId() {
        String pool = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        Random rand = new Random();
        String result = new String();
        for (int i = 0; i < 6; i++) {
            char c = pool.charAt(rand.nextInt(pool.length()));
            if (!result.contains(String.valueOf(c)))
                result += c;
            else {
                c--;
            }
        }
        return result;
    }

    public void simulatePriceChange() {
        Random rand = new Random();
        double changePercent = (rand.nextDouble() * 10) - 5;
        double change = this.price * (changePercent / 100);
        this.price = Math.max(0.01, this.price + change);
        this.price = Double.parseDouble(String.format("%.2f", this.price));
        this.priceHistory.add(this.price);
    }

    public double getPriceChange() {
        return price - openPrice;
    }

    public double getPriceChangePercent() {
        if (openPrice == 0)
            return 0;

        return ((price - openPrice) / openPrice) * 100;
    }

    public boolean isUp() {
        return price >= openPrice;
    }

    public boolean isDown() {
        return price < openPrice;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol.toUpperCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double p) {
        this.openPrice = p;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public List<Double> getPriceHistory() {
        return priceHistory;
    }

    @Override
    public String getCSVHeader() {
        return "stockId,symbol,name,sector,price,openPrice";
    }

    //make the csv like the final project
    @Override
    public String toCSV() {
        return stockId + "," + symbol + "," + name + "," + sector + ","
                + String.format("%.2f", price) + "," + String.format("%.2f", openPrice);
    }

    @Override
    public void fromCSV(String data) {
        String[] part = data.split(",");
        if (part.length < 6)
            return;
        this.stockId = part[0].trim();
        this.symbol = part[1].trim();
        this.name = part[2].trim();
        this.sector = part[3].trim();
        this.price = Double.parseDouble(part[4].trim());
        this.openPrice = Double.parseDouble(part[5].trim());
        if (this.priceHistory == null)
            this.priceHistory = new ArrayList<>();
        this.priceHistory.add(this.price);
    }

    @Override
    public String toString() {
        String arrow = isUp() ? "▲" : "▼";
        return String.format("%-6s | %-22s | $%-9.2f | %s %+.2f (%+.2f%%) | Sector: %s",
                symbol, name, price, arrow,
                getPriceChange(), getPriceChangePercent(), sector);
    }
}
