import java.util.ArrayList;
import java.util.List;

public class User implements CsvHandler {
    protected String userId;
    protected String username;
    protected String password;
    protected double balance;
    protected Portfolio portfolio;
    protected List<Transaction> transactionHistory;

    public User() {
        this.userId = generateRandomId();
        this.username = "Unknown";
        this.balance = 0.0;
        this.portfolio = new Portfolio(this.userId);
        this.transactionHistory = new ArrayList<>();
    }

    public User(String username, String password, double startingBalance) {
        this.userId = generateRandomId();
        this.username = username;
        this.password = password;
        this.balance = startingBalance;
        this.portfolio = new Portfolio(this.userId);
        this.transactionHistory = new ArrayList<>();
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

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public boolean canAfford(double amount) {
        return balance >= amount;
    }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit must be positive");
        this.balance += amount;
    }

    public void withdraw(double amount) {
        if (amount > balance) throw new IllegalArgumentException("Insufficient balance");
        this.balance -= amount;
    }

    public void addTransaction(Transaction t) {
        transactionHistory.add(t);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    @Override
    public String getCSVHeader() {
        return "userId,username,password,balance";
    }

    @Override
    public String toCSV() {
        return userId + "," + username + "," + password + "," + String.format("%.2f", balance);
    }

    @Override
    public void fromCSV(String data) {
        String[] part = data.split(",", 4);
        if (part.length < 4) return;
        this.userId = part[0].trim();
        this.username = part[1].trim();
        this.password = part[2].trim();
        this.balance = Double.parseDouble(part[3].trim());
        if (this.portfolio == null) this.portfolio = new Portfolio(this.userId);
        if (this.transactionHistory == null) this.transactionHistory = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "User ID: " + userId + " | Username: " + username
                + " | Balance: $" + String.format("%.2f", balance);
    }
}
