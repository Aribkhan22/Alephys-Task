package com.example.ExpenseTracker;

import java.time.LocalDate;

/**
 * Represents a financial transaction, either income or expense.
 * Each transaction includes a type, category, amount, and date.
 * Used by the Expense Tracker to store and process user financial records.
 */

public class Transaction {
    private String type; // income or expense
    private String category;
    private double amount;
    private LocalDate date;

    public Transaction(String type, String category, double amount, LocalDate date) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return type + "," + category + "," + amount + "," + date;
    }

    /**
     * Parses a transaction string (from file) and returns a Transaction object.
     *
     * @param line A line in the format "type,category,amount,date"
     * @return Transaction object constructed from the string
     */

    public static Transaction fromString(String line) {
        String[] parts = line.split(",");
        return new Transaction(parts[0], parts[1], Double.parseDouble(parts[2]), LocalDate.parse(parts[3]));
    }
}
