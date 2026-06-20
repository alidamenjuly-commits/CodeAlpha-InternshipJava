import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio implements CsvHandler {
    protected String portfolioId;
    protected String userId;
    protected Map<String, Integer> holdings;
    protected Map<String, Double> avgCost;

    public Portfolio() {
        this.portfolioId = generateRandomId();
        this.holdings = new HashMap<>();
        this.avgCost = new HashMap<>();
    }

    public Portfolio(String userId) {
        this.portfolioId = generateRandomId();
        this.userId = userId;
        this.holdings = new HashMap<>();
        this.avgCost = new HashMap<>();
    }

    public static String generateRandomId() {
        String pool = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        java.util.Random rand = new java.util.Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            char c = pool.charAt(rand.nextInt(pool.length()));
            if (!result.toString().contains(String.valueOf(c)))
                result.append(c);
        }
        return result.toString();
    }

    public void addShares(String symbol, int shares, double price) {
        symbol = symbol.toUpperCase();
        int currentShares = holdings.getOrDefault(symbol, 0);
        double currentCost = avgCost.getOrDefault(symbol, 0.0);
        double newAvgCost = ((currentShares * currentCost) + (shares * price))
                / (currentShares + shares);
        holdings.put(symbol, currentShares + shares);
        avgCost.put(symbol, newAvgCost);
    }

    public boolean removeShares(String symbol, int shares) {
        symbol = symbol.toUpperCase();
        int currentShares = holdings.getOrDefault(symbol, 0);
        if (shares > currentShares) return false;
        if (shares == currentShares) {
            holdings.remove(symbol);
            avgCost.remove(symbol);
        } else {
            holdings.put(symbol, currentShares - shares);
        }
        return true;
    }

    public boolean hasShares(String symbol, int amount) {
        return holdings.getOrDefault(symbol.toUpperCase(), 0) >= amount;
    }

    public int getShares(String symbol) {
        return holdings.getOrDefault(symbol.toUpperCase(), 0);
    }

    public double getAvgCost(String symbol) {
        return avgCost.getOrDefault(symbol.toUpperCase(), 0.0);
    }

    public boolean isEmpty() {
        return holdings.isEmpty();
    }

    public Map<String, Integer> getHoldings() {
        return holdings;
    }

    public double getTotalInvested() {
        double total = 0;
        for (Map.Entry<String, Integer> e : holdings.entrySet())
            total += e.getValue() * avgCost.getOrDefault(e.getKey(), 0.0);
        return total;
    }

    public double getCurrentValue(Map<String, Stock> marketStocks) {
        double total = 0;
        for (Map.Entry<String, Integer> e : holdings.entrySet()) {
            Stock s = marketStocks.get(e.getKey());
            if (s != null) total += e.getValue() * s.getPrice();
        }
        return total;
    }

    public double getProfitLoss(Map<String, Stock> marketStocks) {
        return getCurrentValue(marketStocks) - getTotalInvested();
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getCSVHeader() {
        return "portfolioId,userId,holdings,avgCost";
    }

    @Override
    public String toCSV() {
        StringBuilder h = new StringBuilder();
        StringBuilder a = new StringBuilder();
        for (Map.Entry<String, Integer> e : holdings.entrySet()) {
            if (h.length() > 0) {
                h.append(";");
                a.append(";");
            }
            h.append(e.getKey()).append(":").append(e.getValue());
            a.append(e.getKey()).append(":").append(String.format("%.2f", avgCost.get(e.getKey())));
        }
        return portfolioId + "," + userId + "," + h + "," + a;
    }

    @Override
    public void fromCSV(String data) {
        String[] part = data.split(",", 4);
        if (part.length < 4) return;
        this.portfolioId = part[0].trim();
        this.userId = part[1].trim();
        this.holdings = new HashMap<>();
        this.avgCost = new HashMap<>();

        String holdingsData = part[2].trim();
        String avgCostData = part[3].trim();

        if (!holdingsData.isEmpty()) {
            for (String entry : holdingsData.split(";")) {
                String[] kv = entry.split(":");
                if (kv.length == 2) holdings.put(kv[0].trim(), Integer.parseInt(kv[1].trim()));
            }
        }
        if (!avgCostData.isEmpty()) {
            for (String entry : avgCostData.split(";")) {
                String[] kv = entry.split(":");
                if (kv.length == 2) avgCost.put(kv[0].trim(), Double.parseDouble(kv[1].trim()));
            }
        }
    }

    public void printSummary(Map<String, Stock> marketStocks) {
        if (isEmpty()) {
            System.out.println("  No holdings yet.");
            return;
        }
        System.out.println("  " + "-".repeat(72));
        System.out.printf("  %-6s | %6s | %10s | %10s | %10s | %s%n",
                "SYMBOL", "SHARES", "AVG COST", "CUR PRICE", "VALUE", "P/L");
        System.out.println("  " + "-".repeat(72));
        for (Map.Entry<String, Integer> e : holdings.entrySet()) {
            String sym = e.getKey();
            int shares = e.getValue();
            double avg = avgCost.getOrDefault(sym, 0.0);
            Stock stock = marketStocks.get(sym);
            double cur = (stock != null) ? stock.getPrice() : 0.0;
            double value = shares * cur;
            double pl = value - (shares * avg);
            System.out.printf("  %-6s | %6d | $%9.2f | $%9.2f | $%9.2f | %s$%.2f%n",
                    sym, shares, avg, cur, value, pl >= 0 ? "+" : "", pl);
        }
        System.out.println("  " + "-".repeat(72));
    }
}
