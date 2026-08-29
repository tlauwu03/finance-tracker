import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args){
        ArrayList<Transaction> transactions = loadTransactions();
        HashMap<String, Double> budgets = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while(running) {
            System.out.println("\n--- Finance Tracker ---");
            System.out.println("1. Add transaction");
            System.out.println("2. View all transactions");
            System.out.println("3. View total");
            System.out.println("4. View by category");
            System.out.println("5. Delete a transaction");
            System.out.println("6. Set budget for category");
            System.out.println("7. Reset all transactions");
            System.out.println("8. Exit");

            int choice = getValidInt(scanner, "Choose an option: ");

            if (choice == 1) {

                String description = getValidString(scanner, "Description: ");

                double amount = getValidDouble(scanner, "Amount: ");

                String category = getValidString(scanner, "Category: ");

                transactions.add(new Transaction(description, amount, category));
                saveTransactions(transactions);
                System.out.println("Transaction added!");

                if(budgets.containsKey(category)){
                    double spent = 0;
                    for(Transaction t : transactions){
                        if(t.getCategory().equalsIgnoreCase(category)){
                            spent += t.getAmount();
                        }
                    }
                    double budget = budgets.get(category);
                    System.out.println("Spent so far in " + category + ": $" + spent + " / $" + budget);

                    if(spent > budget){
                        System.out.println("⚠ You've gone over budget for " + category + "!");
                    }
                }


            } else if (choice == 2) {
                if (transactions.isEmpty()) {
                    System.out.println("No transactions yet.");
                } else {
                    for (Transaction t : transactions) {
                        System.out.println(t);
                    }
                }

            } else if (choice == 3) {
                double total = 0;
                for (Transaction t : transactions) {
                    total += t.getAmount();
                }
                System.out.println("Total: $" + total);

            } else if (choice == 4){
                String searchCategory = getValidString(scanner, "Enter category to view: ");

                boolean found = false;
                for(Transaction t : transactions){
                    if(t.getCategory().equalsIgnoreCase(searchCategory)){
                        System.out.println(t);
                        found = true;
                    }
                }
                if(!found){
                    System.out.println("No transactions found in that category.");
                }

            } else if (choice == 5){
                if(transactions.isEmpty()){
                    System.out.println("No transactions to delete.");
                } else{
                    for(int i = 0; i < transactions.size(); i++){
                        System.out.println(i + ": " + transactions.get(i));
                    }
                    int index = getValidInt(scanner, "Enter the number of transaction to delete: ");

                    if(index >= 0 && index < transactions.size()){
                        transactions.remove(index);
                        saveTransactions(transactions);
                        System.out.println("Transaction deleted!");
                    } else{
                        System.out.println("Invalid number, nothing deleted.");
                    }
                }

            } else if (choice == 6) {
                String category = getValidString(scanner, "Category to set budget for: ");
                double amount = getValidDouble(scanner, "Budget amount: ");
                budgets.put(category, amount);
                System.out.println("Budget set for " + category + ": $" + amount);

            } else if (choice == 7){
                transactions.clear();
                saveTransactions(transactions);
                System.out.println("All transactions cleared!");

            } else if (choice == 8){
                running = false;
                System.out.println("Have a good day!");

            } else {
                System.out.println("Invalid option, try again.");
            }
        }
        scanner.close();
    }

    public static void saveTransactions(ArrayList<Transaction> transactions){
        try{
            PrintWriter writer = new PrintWriter(new FileWriter("transactions.txt"));
            for(Transaction t : transactions){
                writer.println(t.getDescription()+","+t.getAmount()+","+t.getCategory());
            }
            writer.close();
        }catch (IOException e){
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }

    public static ArrayList<Transaction> loadTransactions(){
        ArrayList<Transaction>transactions = new ArrayList<>();
        File file = new File("transactions.txt");

        if(!file.exists()){
            return transactions;
        }

        try{
            Scanner fileScanner = new Scanner(file);
            while(fileScanner.hasNextLine()){
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                String description = parts[0];
                double amount = Double.parseDouble(parts[1]);
                String category = parts[2];
                transactions.add(new Transaction(description, amount, category));
            }
            fileScanner.close();
        }catch (IOException e){
            System.out.println("Error loading transtions: " + e.getMessage());
        }
        return transactions;
    }

    public static int getValidInt(Scanner scanner, String prompt){
        while (true){
            System.out.print(prompt);
            try{
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch(InputMismatchException e){
                System.out.println("That's not a valid number, try again.");
                scanner.nextLine();
            }
        }
    }

    public static double getValidDouble(Scanner scanner, String prompt){
        while (true){
            System.out.print(prompt);
            try{
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } catch(InputMismatchException e){
                System.out.println("That's not a valid number, try again.");
                scanner.nextLine();
            }
        }
    }

    public static String getValidString(Scanner scanner, String prompt){
        String value;
        while(true){
            System.out.print(prompt);
            value = scanner.nextLine();
            if(!value.trim().isEmpty()){
                return value;
            }
            System.out.println("This can't be blank, try again.");
        }
    }
}