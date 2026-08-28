import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args){
        ArrayList<Transaction> transactions = loadTransactions();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while(running) {
            System.out.println("\n--- Finance Tracker ---");
            System.out.println("1. Add transaction");
            System.out.println("2. View all transactions");
            System.out.println("3. View total");
            System.out.println("4. View by category");
            System.out.println("5. Exit");
            System.out.println("6. Reset all transactions");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("Description: ");
                String description = scanner.nextLine();

                System.out.println("Amount: ");
                double amount = scanner.nextDouble();
                scanner.nextLine();

                System.out.print("Category: ");
                String category = scanner.nextLine();

                transactions.add(new Transaction(description, amount, category));
                saveTransactions(transactions);
                System.out.println("Transaction added!");


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
                System.out.println("Enter category to view: ");
                String searchCategory = scanner.nextLine();

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

            } else if (choice == 5) {
                running = false;
                System.out.println("Have a good day!");

            } else if (choice == 6){
                transactions.clear();
                saveTransactions(transactions);
                System.out.println("All transactions cleared!");

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
}