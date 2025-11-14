import classes.Book;
import classes.InputValidation;

import java.util.LinkedList;
import java.util.Scanner;

public class Inventory {
    private static final Scanner mainScanner = new Scanner(System.in);
    private static final LinkedList<Book> bookStoreInventory = new LinkedList<>();

    public static void main(String[] args) {
        int selectionResult;

        do {
            displayMenu();

            int userChoice = InputValidation.integer(mainScanner);

            selectionResult = handleSelection(userChoice);
        } while (selectionResult != -1);

    }

    private static void displayMenu() {
        System.out.println("\033cWelcome to the Bookstore Inventory Management System!");
        System.out.printf("Please choose from any of the following options\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n\n\t >>  ",
                "[0] Add a New Book",
                "[1] Display All Books",
                "[2] Sort books by Title",
                "[3] Search for a book by Title",
                "[4] Add a customer order to a Queue",
                "[5] Process the next customer order",
                "[6] Exit the Program");
    }

    public static int handleSelection(int choice){
        Scanner handleSelectionScanner = new Scanner(System.in);
        int returnValue;

        switch (choice){
            case 0:
                bookStoreInventory.add(addBook(handleSelectionScanner));
                System.out.printf("Book was added successfully!\n%s[Press Enter to go back to main menu]%s","\u001B[31m","\u001B[0m");
                handleSelectionScanner.nextLine();
            case 1:
                System.out.printf("%sAll Books in Inventory%s\n%s\n%s[Press Enter to go back to main menu]%s",
                        "~~~~~~~~".repeat(3),"~~~~~~~~".repeat(3),displayAllBooks(),"\u001B[31m","\u001B[0m");
                handleSelectionScanner.nextLine();
            case 6:
            default:
                returnValue = -1;
                break;
        }
        return returnValue;
    }


    public static Book addBook(Scanner bookScanner){
        System.out.printf("\n%sAdding Book to Inventory System%s\nBook Title: ",
                "__".repeat(10),"__".repeat(10));
        String bookTitle = bookScanner.nextLine();

        System.out.print("\nAuthor: ");
        String author = bookScanner.nextLine();

        System.out.print("\nISBN: ");
        String isbn = InputValidation.string(bookScanner,"^((978|979)-\\d{10})$");

        System.out.print("\nPrice: ");
        float price = bookScanner.nextFloat();

        return new Book(bookTitle,author,isbn,price);
    }

    public static String displayAllBooks(){
        // Initializes String Builder
        StringBuilder stringBuilder = new StringBuilder();
        try {
            readInventoryRecursive(stringBuilder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return stringBuilder.toString();
    }

    private static void readInventoryRecursive(StringBuilder sb){
        if (!bookStoreInventory.isEmpty()){
            sb.append(bookStoreInventory.getFirst().toString()).append("\n");
        }
        else {
            sb.append("No Books in the Inventory!");
        }
    }
}
