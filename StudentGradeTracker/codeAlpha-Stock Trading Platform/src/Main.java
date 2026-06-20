import java.util.*;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static Market market = new Market();
    private static FileManager fileMgr = new FileManager();

    private static List<User> users = new ArrayList<>();
    private static User loggedInUser = null;

    public static void main(String[] args) {
        loadAllData();
        printBanner();
        authMenu();
    }

    private static void printBanner() {
        System.out.println("\n===== STOCK TRADING PLATFORM =====");
    }

    private static void authMenu() {

        while (true) {

            System.out.println("\n1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");

            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    login();
                    break;

                case "2":
                    register();
                    break;

                case "3":
                    exit();
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void login() {

        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        for (User user : users) {

            if (user.getUsername().equals(username)
                    && user.authenticate(password)) {

                loggedInUser = user;

                loggedInUser.getTransactionHistory().clear();

                loggedInUser.getTransactionHistory().addAll(
                        fileMgr.loadTransactions(user.getUserId())
                );

                System.out.println("\nWelcome " + username + "!");
                mainMenu();
                return;
            }
        }

        System.out.println("Invalid credentials.");
    }

    private static void register() {

        System.out.print("Choose username: ");
        String username = scanner.nextLine().trim();

        for (User user : users) {

            if (user.getUsername().equals(username)) {
                System.out.println("Username already exists.");
                return;
            }
        }

        System.out.print("Choose password: ");
        String password = scanner.nextLine().trim();

        System.out.print("Starting balance: ");

        double balance;

        try {

            balance = Double.parseDouble(scanner.nextLine());

            if (balance < 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {

            System.out.println("Invalid balance.");
            return;
        }

        User newUser = new User(username, password, balance);

        users.add(newUser);

        saveAllData();

        System.out.println("Account created!");
    }

    private static void mainMenu() {

        while (loggedInUser != null) {

            System.out.println("\n===== MAIN MENU =====");
            System.out.printf("Balance: $%.2f%n", loggedInUser.getBalance());

            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Transaction History");
            System.out.println("6. Simulate Market");
            System.out.println("7. Deposit Funds");
            System.out.println("8. Logout");

            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    market.displayMarket();
                    break;

                case "2":
                    buyStock();
                    break;

                case "3":
                    sellStock();
                    break;

                case "4":
                    viewPortfolio();
                    break;

                case "5":
                    viewTransactionHistory();
                    break;

                case "6":
                    simulateMarket();
                    break;

                case "7":
                    depositFunds();
                    break;

                case "8":
                    logout();
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void buyStock() {

        market.displayMarket();

        System.out.print("\nEnter stock symbol: ");
        String symbol = scanner.nextLine().trim().toUpperCase();

        if (!market.hasStock(symbol)) {
            System.out.println("Stock not found.");
            return;
        }

        Stock stock = market.getStock(symbol);

        System.out.println(stock);

        System.out.print("Number of shares: ");

        int shares;

        try {

            shares = Integer.parseInt(scanner.nextLine());

            if (shares <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {

            System.out.println("Invalid number.");
            return;
        }

        double totalCost = shares * stock.getPrice();

        if (!loggedInUser.canAfford(totalCost)) {
            System.out.println("Insufficient balance.");
            return;
        }

        loggedInUser.withdraw(totalCost);

        loggedInUser.getPortfolio().addShares(
                symbol,
                shares,
                stock.getPrice()
        );

        Transaction t = new Transaction(
                loggedInUser.getUserId(),
                symbol,
                "BUY",
                shares,
                stock.getPrice()
        );

        loggedInUser.addTransaction(t);

        fileMgr.appendTransaction(t);

        saveAllData();

        System.out.println("Stock purchased successfully.");
    }

    private static void sellStock() {

        Portfolio portfolio = loggedInUser.getPortfolio();

        if (portfolio.isEmpty()) {
            System.out.println("Portfolio is empty.");
            return;
        }

        portfolio.printSummary(market.getStocks());

        System.out.print("\nEnter stock symbol: ");
        String symbol = scanner.nextLine().trim().toUpperCase();

        if (!portfolio.hasShares(symbol, 1)) {
            System.out.println("You do not own this stock.");
            return;
        }

        int ownedShares = portfolio.getShares(symbol);

        System.out.println("Owned shares: " + ownedShares);

        System.out.print("Shares to sell: ");

        int shares;

        try {

            shares = Integer.parseInt(scanner.nextLine());

            if (shares <= 0 || shares > ownedShares) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {

            System.out.println("Invalid number.");
            return;
        }

        Stock stock = market.getStock(symbol);

        double totalValue = shares * stock.getPrice();

        portfolio.removeShares(symbol, shares);

        loggedInUser.deposit(totalValue);

        Transaction t = new Transaction(
                loggedInUser.getUserId(),
                symbol,
                "SELL",
                shares,
                stock.getPrice()
        );

        loggedInUser.addTransaction(t);

        fileMgr.appendTransaction(t);

        saveAllData();

        System.out.println("Stock sold successfully.");
    }

    private static void viewPortfolio() {

        Portfolio portfolio = loggedInUser.getPortfolio();

        System.out.println("\n===== PORTFOLIO =====");

        portfolio.printSummary(market.getStocks());

        System.out.printf("Cash Balance: $%.2f%n",
                loggedInUser.getBalance());
    }

    private static void viewTransactionHistory() {

        List<Transaction> history =
                loggedInUser.getTransactionHistory();

        if (history.isEmpty()) {

            System.out.println("No transactions found.");
            return;
        }

        System.out.println("\n===== TRANSACTIONS =====");

        for (Transaction t : history) {
            System.out.println(t);
        }
    }

    private static void simulateMarket() {

        market.simulateMarket();

        saveAllData();

        System.out.println("Market updated.");
    }

    private static void depositFunds() {

        System.out.print("Amount to deposit: ");

        try {

            double amount =
                    Double.parseDouble(scanner.nextLine());

            if (amount <= 0) {
                throw new NumberFormatException();
            }

            loggedInUser.deposit(amount);

            saveAllData();

            System.out.printf(
                    "New Balance: $%.2f%n",
                    loggedInUser.getBalance()
            );

        } catch (NumberFormatException e) {

            System.out.println("Invalid amount.");
        }
    }

    private static void logout() {

        System.out.println("Logged out.");

        loggedInUser = null;
    }

    private static void exit() {

        saveAllData();

        System.out.println("Goodbye!");

        System.exit(0);
    }

    private static void loadAllData() {

        List<User> loadedUsers = fileMgr.loadUsers();

        Map<String, Portfolio> portfolioMap =
                fileMgr.loadPortfolios();

        for (User user : loadedUsers) {

            Portfolio portfolio =
                    portfolioMap.get(user.getUserId());

            if (portfolio != null) {
                user.setPortfolio(portfolio);
            }

            users.add(user);
        }

        List<Stock> savedStocks = fileMgr.loadStocks();

        for (Stock stock : savedStocks) {
            market.addStock(stock);
        }
    }

    private static void saveAllData() {

        fileMgr.saveUsers(users);

        List<Portfolio> portfolios =
                new ArrayList<>();

        for (User user : users) {
            portfolios.add(user.getPortfolio());
        }

        fileMgr.savePortfolios(portfolios);

        fileMgr.saveStocks(
                new ArrayList<>(market.getStocks().values())
        );
    }
}
