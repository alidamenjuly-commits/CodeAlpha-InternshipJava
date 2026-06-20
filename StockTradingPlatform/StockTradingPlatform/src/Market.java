import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Market {
    private Map<String, Stock> stocks;

    public Market() {
        this.stocks = new HashMap<>();
        seedDefaultStocks();
    }

    private void seedDefaultStocks() {
        addStock(new Stock("AAPL", "Apple Inc.", "Technology", 182.50));
        addStock(new Stock("GOOGL", "Alphabet Inc.", "Technology", 141.80));
        addStock(new Stock("MSFT", "Microsoft Corp.", "Technology", 378.90));
        addStock(new Stock("AMZN", "Amazon.com Inc.", "Consumer", 185.20));
        addStock(new Stock("TSLA", "Tesla Inc.", "Automotive", 248.50));
        addStock(new Stock("META", "Meta Platforms Inc.", "Technology", 502.30));
        addStock(new Stock("NVDA", "NVIDIA Corp.", "Technology", 875.40));
        addStock(new Stock("JPM", "JPMorgan Chase", "Finance", 198.70));
        addStock(new Stock("BAC", "Bank of America", "Finance", 38.20));
        addStock(new Stock("KO", "The Coca-Cola Co.", "Consumer", 60.10));
    }

    public void addStock(Stock stock) {
        stocks.put(stock.getSymbol(), stock);
    }

    public Stock getStock(String symbol) {
        return stocks.get(symbol.toUpperCase());
    }

    public boolean hasStock(String symbol) {
        return stocks.containsKey(symbol.toUpperCase());
    }

    public void simulateMarket() {
        for (Stock s : stocks.values())
            s.simulatePriceChange();
    }

    public Map<String, Stock> getStocks() {
        return stocks;
    }

    public void displayMarket() {
        System.out.println("\n  " + "=".repeat(72));
        System.out.println("  LIVE MARKET DATA");
        System.out.println("  " + "=".repeat(72));
        System.out.printf("  %-6s | %-22s | %-10s | %-16s | %s%n",
                "SYMBOL", "NAME", "PRICE", "CHANGE", "SECTOR");
        System.out.println("  " + "-".repeat(72));
        for (Stock s : stocks.values()) System.out.println("  " + s);
        System.out.println("  " + "=".repeat(72));
    }
}
