import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction implements CsvHandler {
    protected String transactionId;
    protected String userId;
    protected String stockSymbol;
    protected String type; // BUY or SELL
    protected int shares;
    protected double pricePerShare;
    protected String timestamp;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Transaction() {
        this.transactionId = generateRandomId();
        this.timestamp = LocalDateTime.now().format(FORMATTER);
    }

    public Transaction(String userId, String stockSymbol, String type, int shares, double pricePerShare) {
        this.transactionId = generateRandomId();
        this.userId = userId;
        this.stockSymbol = stockSymbol.toUpperCase();
        this.type = type.toUpperCase();
        this.shares = shares;
        this.pricePerShare = pricePerShare;
        this.timestamp = LocalDateTime.now().format(FORMATTER);
    }

    public static String generateRandomId() {
        String pool = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        java.util.Random rand = new java.util.Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            char c = pool.charAt(rand.nextInt(pool.length()));
            if (!result.toString().contains(String.valueOf(c)))
                result.append(c);
        }
        return result.toString();
    }

    public double getTotalValue() {
        return shares * pricePerShare;
    }

    public boolean isBuy() {
        return "BUY".equals(type);
    }

    public boolean isSell() {
        return "SELL".equals(type);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String s) {
        this.stockSymbol = s.toUpperCase();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type.toUpperCase();
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public double getPricePerShare() {
        return pricePerShare;
    }

    public void setPricePerShare(double p) {
        this.pricePerShare = p;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String getCSVHeader() {
        return "transactionId,userId,stockSymbol,type,shares,pricePerShare,timestamp";
    }

    @Override
    public String toCSV() {
        return transactionId + "," + userId + "," + stockSymbol + "," + type + ","
                + shares + "," + String.format("%.2f", pricePerShare) + "," + timestamp;
    }

    @Override
    public void fromCSV(String data) {
        String[] part = data.split(",", 7);
        if (part.length < 7) return;
        this.transactionId = part[0].trim();
        this.userId = part[1].trim();
        this.stockSymbol = part[2].trim();
        this.type = part[3].trim();
        this.shares = Integer.parseInt(part[4].trim());
        this.pricePerShare = Double.parseDouble(part[5].trim());
        this.timestamp = part[6].trim();
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | %-4s | %-6s | %3d shares @ $%.2f = $%.2f",
                timestamp, transactionId, type, stockSymbol,
                shares, pricePerShare, getTotalValue());
    }
}
