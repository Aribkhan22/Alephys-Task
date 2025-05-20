package com.example.ExpenseTracker;



import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;


/**
 * A simple console-based expense tracker application.
 *
 * This program allows users to:
 * - Add income or expense entries
 * - Categorize transactions (e.g., Salary, Rent, Food)
 * - View a summary of income and expenses for the current month
 * - Load and save transaction data from/to a file
 *
 * Transactions are stored in memory as a list and serialized to files as comma-separated values.
 *
 * Author: Aribumer Khan
 * Date: 2025-05-20
 */

public class ExpenseTracker {
    private static List<Transaction> transactions = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. View Monthly Summary");
            System.out.println("4. Load from File");
            System.out.println("5. Save to File");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> addTransaction("income");
                case 2 -> addTransaction("expense");
                case 3 -> viewMonthlySummary();
                case 4 -> loadFromFile();
                case 5 -> saveToFile();
                case 6 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    /**
     * Adds a transaction (income or expense) based on user input.
     *
     * @param type "income" or "expense"
     */

    private static void addTransaction(String type) {
        scanner.nextLine(); // consume newline
        System.out.print("Enter category (" +
                (type.equals("income") ? "Salary/Business" : "Food/Rent/Travel") + "): ");
        String category = scanner.nextLine();

        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();

        transactions.add(new Transaction(type, category, amount, LocalDate.now()));
        System.out.println("Transaction added.");
    }

    /**
     * Generates and displays a summary of all transactions
     * for the current month, including totals per category.
     */

    private static void viewMonthlySummary() {
        Map<String, Double> incomeMap = new HashMap<>();
        Map<String, Double> expenseMap = new HashMap<>();
        double totalIncome = 0, totalExpense = 0;
        YearMonth currentMonth = YearMonth.now();

        for (Transaction t : transactions) {
            if (YearMonth.from(t.getDate()).equals(currentMonth)) {
                if (t.getType().equals("income")) {
                    incomeMap.put(t.getCategory(),
                            incomeMap.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
                    totalIncome += t.getAmount();
                } else {
                    expenseMap.put(t.getCategory(),
                            expenseMap.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
                    totalExpense += t.getAmount();
                }
            }
        }

        System.out.println("\n--- Monthly Summary (" + currentMonth + ") ---");
        System.out.println("Income:");
        incomeMap.forEach((k, v) -> System.out.println(k + ": $" + v));
        System.out.println("Total Income: $" + totalIncome);

        System.out.println("\nExpenses:");
        expenseMap.forEach((k, v) -> System.out.println(k + ": $" + v));
        System.out.println("Total Expenses: $" + totalExpense);

        System.out.println("\nNet Savings: $" + (totalIncome - totalExpense));
    }

    /**
     * Loads transactions from a file provided by the user.
     * Each line in the file should follow the format:
     * type,category,amount,date (e.g., income,Salary,5000.0,2025-05-01)
     */

    private static void loadFromFile() {
        scanner.nextLine(); // consume newline
        System.out.print("Enter file path to load: ");
        String path = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                transactions.add(Transaction.fromString(line));
            }
            System.out.println("Data loaded successfully.");
        } catch (IOException e) {
            System.out.println("Failed to load file: " + e.getMessage());
        }
    }

    /**
     * Saves all current transactions to a file.
     * Each transaction is written as a CSV line.
     */

    private static void saveToFile() {
        scanner.nextLine(); // consume newline
        System.out.print("Enter file path to save: ");
        String path = scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (Transaction t : transactions) {
                writer.write(t.toString());
                writer.newLine();
            }
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save file: " + e.getMessage());
        }
    }
}
