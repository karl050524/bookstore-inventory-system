import classes.Book;
import classes.InputValidation;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Inventory {
    private static final Scanner mainScanner = new Scanner(System.in);
    private static final LinkedList<Book> bookStoreInventory = new LinkedList<>();
    private static final Queue<String> orderQueue = new LinkedList<>();

    public static void main(String[] args) {
        int selectionResult;

        do {
            displayMenu();
            selectionResult = handleSelection(mainScanner);
        }
        while (selectionResult != 7);

        mainScanner.close();
    }

    private static void displayMenu() {
        System.out.println("\033cWelcome to the Bookstore Inventory Management System!");
        System.out.printf("Please choose from any of the following options\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n\n\t >>  ",
                "[1] Add a New Book",
                "[2] Display All Books",
                "[3] Sort books by Title",
                "[4] Search for a book by Title",
                "[5] Add a customer order to a Queue",
                "[6] Process the next customer order",
                "[7] Exit the Program");
    }

    public static int handleSelection(Scanner handleSelectionScanner){
        int returnValue = 0; // returnValue is 0 (continue) by default, 7 (exit) if option 7 is selected.
        int choice = InputValidation.integer(handleSelectionScanner);

        System.out.println("\033c");
        switch (choice){
            // Add Book to LinkedList
            case 1:
                bookStoreInventory.add(addBook(handleSelectionScanner));
                System.out.printf("Book was added successfully!\n%s[Press Enter to go back to main menu]%s","\u001B[31m","\u001B[0m");
                handleSelectionScanner.nextLine();
                break;

            // Displays all Books in Inventory
            case 2:
                System.out.printf("%s  [All Books in Inventory]  %s\n%s\n%s[Press Enter to go back to main menu]%s",
                        "~~~~~~~~".repeat(3),"~~~~~~~~".repeat(3),displayAllBooks(),"\u001B[31m","\u001B[0m");
                handleSelectionScanner.nextLine();
                break;

            // Sorts Books by Title
            case 3:
                if (bookStoreInventory.isEmpty()) System.out.println("There are no books in the Inventory");

                else {
                    sortBooksByTitle(bookStoreInventory, bookStoreInventory.size());
                    System.out.println("Sorting books by title...\nBooks sorted successfully");
                }
                System.out.printf("\n%s[Press Enter to go back to main menu]%s","\u001B[31m","\u001B[0m");
                handleSelectionScanner.nextLine();
                break;

            // Search Book by Title
            case 4:
                searchBookByTitle(handleSelectionScanner);
                System.out.printf("\n%s[Press Enter to go back to main menu]%s","\u001B[31m","\u001B[0m");
                handleSelectionScanner.nextLine();
                break;

            // Add Customer Order to Queue
            case 5:
                addOrderToQueue(handleSelectionScanner);
                System.out.printf("\n%s[Press Enter to go back to main menu]%s","\u001B[31m","\u001B[0m");
                handleSelectionScanner.nextLine();
                break;

            // Processes Customer Order from Queue
            case 6:
                processNextOrder();
                System.out.printf("\n%s[Press Enter to go back to main menu]%s","\u001B[31m","\u001B[0m");
                handleSelectionScanner.nextLine();
                break;

            // Exits Program
            case 7:
                System.out.println("Terminating Program... Goodbye and Thank you for using the Bookstore Inventory Management System!");
                returnValue = 7;
                break;

            // Invalid Selection
            default:
                System.out.println("\nInvalid selection. Please enter a number between 1 and 7.");
        }
        return returnValue;
    }

    // Add Book Method
    public static Book addBook(Scanner bookScanner){
        System.out.printf("%s  <<Adding Book to Inventory System>>  %s\nBook Title: ",
                "__".repeat(10),"__".repeat(10));
        String bookTitle = bookScanner.nextLine();

        System.out.print("Author: ");
        String author = bookScanner.nextLine();

        System.out.print("ISBN: ");
        String isbn = InputValidation.string(bookScanner,"^((978|979)-\\d{10})$");

        System.out.print("Price: ");
        float price = InputValidation.floatNumber(bookScanner);

        return new Book(bookTitle,author,isbn,price);
    }

    // Displays All Books in bookInventory
    public static String displayAllBooks(){
        // Initializes String Builder
        StringBuilder stringBuilder = new StringBuilder();
        if (bookStoreInventory.isEmpty()){
            stringBuilder.append("No Books in the Inventory");
        }
        else{
            // If the list is NOT empty, append the first element of the linked list to the string builder parameter
            for (Book book : bookStoreInventory){
                stringBuilder.append(book.toString()).append("\n");
            }
        }

        return stringBuilder.toString();
    }

    // Sorts Books by Title Recursively
    private static void sortBooksByTitle(LinkedList<Book> bookLinkedList, int listSize){
            if (listSize <= 1) return;
            for (int index = 0; index < listSize - 1; index++){
                Book book1 = bookLinkedList.get(index);
                Book book2 = bookLinkedList.get(index+1);

                if (book1.getTitle().compareToIgnoreCase(book2.getTitle()) > 0){
                    // Swaps books
                    bookLinkedList.set(index, book2);
                    bookLinkedList.set(index + 1, book1);
                }
            }

            // Recursively bubble sort the remaining (n-1) elements
            sortBooksByTitle(bookLinkedList, listSize - 1);
    }

    // Search Book by Title Method
    private static void searchBookByTitle(Scanner searcherScanner){
        if (!bookStoreInventory.isEmpty()){
            System.out.print("Enter the title of the book to search for: ");
            String searchKey = searcherScanner.nextLine().trim().toLowerCase();
            boolean found = false;

            // Enhanced for-loop
            for (Book book : bookStoreInventory) {
                if (isBookInInventory(searchKey)) {
                    System.out.printf("\nBook Found!\n%s", book);
                    found = true;
                    break;
                }
            }

            if (!found) System.out.println("Oh no! The book you're trying to search for was not found!");
        }
        else System.out.println("There are no books in the Inventory");
    }

    private static boolean isBookInInventory(String title){
        String searchKey = title.trim().toLowerCase();
        for (Book book : bookStoreInventory){
            if (book.getTitle().toLowerCase().contains(searchKey)){
                return true;
            }
        }
        return false;
    }

    // Add Customer Order to Queue
    private static void addOrderToQueue(Scanner queueScanner){
        System.out.print("Enter the title (or partial title) of the book to order: ");
        String searchKey = queueScanner.nextLine().trim().toLowerCase();

        // 1. Find all partial matches
        LinkedList<String> matchingTitles = new LinkedList<>();
        for (Book book : bookStoreInventory) {
            if (book.getTitle().toLowerCase().contains(searchKey)) {
                matchingTitles.add(book.getTitle());
            }
        }

        // 2. Handle matches
        if (matchingTitles.isEmpty()) {
            System.out.printf("Error: No book matching '%s' was found in the inventory.", searchKey);
        } else if (matchingTitles.size() == 1) {
            // Found a unique match
            String bookToOrder = matchingTitles.getFirst();
            orderQueue.offer(bookToOrder);
            System.out.printf("Order for unique book '%s' successfully added to the queue.", bookToOrder);
        } else {
            // Multiple matches found, require clarification
            System.out.println("\nMultiple books matched your input. Please select the correct book:");
            for (int i = 0; i < matchingTitles.size(); i++) {
                System.out.printf("  [%d] %s\n", i + 1, matchingTitles.get(i));
            }
            // Ask for selection by index
            System.out.print("Enter the number of the book you wish to order, or press Enter to cancel: ");
            String selectionInput = queueScanner.nextLine().trim();

            if (!selectionInput.isEmpty()) {
                try {
                    int selectionIndex = Integer.parseInt(selectionInput);
                    if (selectionIndex > 0 && selectionIndex <= matchingTitles.size()) {
                        String bookToOrder = matchingTitles.get(selectionIndex - 1);
                        orderQueue.offer(bookToOrder);
                        System.out.printf("Order for '%s' successfully added to the queue.", bookToOrder);
                    } else {
                        System.out.println("Invalid selection number. Order cancelled.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input (must be a number). Order cancelled.");
                }
            } else {
                System.out.println("Order cancelled by user.");
            }
        }
    }

    // Processes Customer Order from front of the order queue
    private static void processNextOrder(){
        if (orderQueue.isEmpty()){
            System.out.println("The order queue is empty!");
        }
        else {
            String removedElement = orderQueue.remove();
            System.out.printf("Processing next order....\nProcessed Order for: %s", removedElement);
        }
    }
}