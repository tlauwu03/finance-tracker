import java.util.*;

public class Main {
    public static void main(String[] args){
        ArrayList<Transaction> transactions = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while(running) {
            System.out.println("\n--- Finance Tracker ---");
            System.out.println("1. Add transaction");
            System.out.println("2. View all transactions");
            System.out.println("3. View total");
            System.out.println("4. Exit");
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

            } else if (choice == 4) {
                running = false;
                System.out.println("Have a good day!");

            } else {
                System.out.println("Invalid option, try again.");
            }
        }
    }
}