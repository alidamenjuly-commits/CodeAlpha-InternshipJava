import java.io.*;
import java.util.*;

public class FileManager {
    private static String DATA_DIR = "data/";
    private static String USERS_FILE = DATA_DIR + "users.csv";
    private static String PORTFOLIOS_FILE = DATA_DIR + "portfolios.csv";
    private static String TRANSACTIONS_FILE = DATA_DIR + "transactions.csv";
    private static String STOCKS_FILE = DATA_DIR + "stocks.csv";

    public FileManager() {
        ensureDataDirectory();
    }

    private void ensureDataDirectory() {
        File dir = new File(DATA_DIR);
        if (!dir.exists())
            dir.mkdirs();
    }


    public void saveUsers(List<User> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {
            if (!users.isEmpty())
                writer.println(users.get(0).getCSVHeader());
            for (User u : users)
                writer.println(u.toCSV());
        } catch (IOException e) {
            System.out.println("  [ERROR] Could not save users: " + e.getMessage());
        }
    }

    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(USERS_FILE);
        if (!file.exists()) return users;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                if (line.trim().isEmpty()) continue;
                User u = new User();
                u.fromCSV(line);
                users.add(u);
            }
        } catch (IOException e) {
            System.out.println("  [ERROR] Could not load users: " + e.getMessage());
        }
        return users;
    }


    public void savePortfolios(List<Portfolio> portfolios) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PORTFOLIOS_FILE))) {
            if (!portfolios.isEmpty()) writer.println(portfolios.get(0).getCSVHeader());
            for (Portfolio p : portfolios) writer.println(p.toCSV());
        } catch (IOException e) {
            System.out.println("  [ERROR] Could not save portfolios: " + e.getMessage());
        }
    }
//similar  to the final bas add hashmaps
    public Map<String, Portfolio> loadPortfolios() {
        Map<String, Portfolio> map = new HashMap<>();
        File file = new File(PORTFOLIOS_FILE);
        if (!file.exists())
            return map;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                if (line.trim().isEmpty()) continue;
                Portfolio p = new Portfolio();
                p.fromCSV(line);
                map.put(p.getUserId(), p);
            }
        } catch (IOException e) {
            System.out.println("  [ERROR] Could not load portfolios: " + e.getMessage());
        }
        return map;
    }


    public void saveTransactions(List<Transaction> transactions) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TRANSACTIONS_FILE))) {
            if (!transactions.isEmpty()) writer.println(transactions.get(0).getCSVHeader());
            for (Transaction t : transactions) writer.println(t.toCSV());
        } catch (IOException e) {
            System.out.println("  [ERROR] Could not save transactions: " + e.getMessage());
        }
    }

    public List<Transaction> loadTransactions(String userId) {
        List<Transaction> result = new ArrayList<>();
        File file = new File(TRANSACTIONS_FILE);
        if (!file.exists()) return result;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                if (line.trim().isEmpty()) continue;
                Transaction t = new Transaction();
                t.fromCSV(line);
                if (t.getUserId().equals(userId)) result.add(t);
            }
        } catch (IOException e) {
            System.out.println("  [ERROR] Could not load transactions: " + e.getMessage());
        }
        return result;
    }

    public void appendTransaction(Transaction t) {
        File file = new File(TRANSACTIONS_FILE);
        boolean writeHeader = !file.exists() || file.length() == 0;
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            if (writeHeader) writer.println(t.getCSVHeader());
            writer.println(t.toCSV());
        } catch (IOException e) {
            System.out.println("  [ERROR] Could not save transaction: " + e.getMessage());
        }
    }


    public void saveStocks(List<Stock> stocks) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STOCKS_FILE))) {
            if (!stocks.isEmpty()) writer.println(stocks.get(0).getCSVHeader());
            for (Stock s : stocks) writer.println(s.toCSV());
        } catch (IOException e) {
            System.out.println("  [ERROR] Could not save stocks: " + e.getMessage());
        }
    }

    public List<Stock> loadStocks() {
        List<Stock> stocks = new ArrayList<>();
        File file = new File(STOCKS_FILE);
        if (!file.exists()) return stocks;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                if (line.trim().isEmpty()) continue;
                Stock s = new Stock();
                s.fromCSV(line);
                stocks.add(s);
            }
        } catch (IOException e) {
            System.out.println("  [ERROR] Could not load stocks: " + e.getMessage());
        }
        return stocks;
    }
}
