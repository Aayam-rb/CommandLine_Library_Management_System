import java.util.*;
import java.io.*;
import java.sql.SQLOutput;

public class lms {
    public static void menu() {
        System.out.println("************* MENU LIST *************");
        System.out.println("1. Add new Book");
        System.out.println("2. Display all Books");
        System.out.println("3. Borrow Book");
        System.out.println("4. Return Book");
        System.out.println("0. EXIT");
        System.out.println("*************************************");
    }

    public static void main(String[] args) throws Exception {
        Scanner inum = new Scanner(System.in);
        Scanner inStr = new Scanner(System.in);

        ArrayList<Library> bookList = new ArrayList<>();
        File file = new File("lms.txt");

        // Deserialize file if it exists
        if (file.isFile()) {
            try (ObjectInputStream rData = new ObjectInputStream(new FileInputStream(file))) {
                bookList = (ArrayList<Library>) rData.readObject();
            } catch (Exception e) {
                System.out.println("Error reading file. Starting with an empty library.");
            }
        }

        int choice;
        do {
            menu();
            System.out.print("Enter your choice: ");
            choice = inum.nextInt();

            switch (choice) {
                case 1: { // Add a new book
                    System.out.print("Enter Book ID: ");
                    int id = inum.nextInt();
                    System.out.print("Enter Book Name: ");
                    String name = inStr.nextLine();
                    System.out.print("Enter Author Name: ");
                    String author = inStr.nextLine();
                    System.out.print("Enter Quantity: ");
                    int quantity = inum.nextInt();

                    bookList.add(new Library(id, name, author, quantity));

                    // Save to file
                    try (ObjectOutputStream wData = new ObjectOutputStream(new FileOutputStream(file))) {
                        wData.writeObject(bookList);
                    }
                    System.out.println("Book added successfully!");
                    break;
                }
                case 2: { // Display all books
                    if (bookList.isEmpty()) {
                        System.out.println("No books available.");
                    } else {
                        System.out
                                .println("                            LIST OF BOOKS IN LIBRARY                       ");
                        System.out.println(
                                "------------------------------------------------------------------------------------");
                        System.out.printf("%-15s%-20s%-25s%-15s%-15s\n", "Book ID", "Book Name", "Author Name",
                                "Quantity", "Status");
                        System.out.println(
                                "------------------------------------------------------------------------------------");
                        for (Library lib : bookList) {
                            String status = (lib.getQua() > 0) ? "Available" : "Unavailable";
                            System.out.printf("%-15d%-20s%-25s%-15d%-15s\n", lib.getBid(), lib.getBname(),
                                    lib.getAname(), lib.getQua(), status);
                        }
                        System.out.println(
                                "------------------------------------------------------------------------------------");
                    }
                    break;
                }
                case 3: { // Borrow a book
                    System.out.print("Enter the Book ID to borrow: ");
                    int id = inum.nextInt();

                    boolean found = false;
                    for (Library lib : bookList) {
                        if (lib.getBid() == id) {
                            if (lib.getQua() > 0) {
                                lib.setQua(lib.getQua() - 1);
                                System.out.println("Book borrowed successfully!");
                            } else {
                                System.out.println("Book is unavailable.");
                            }
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Book ID not found.");
                    }

                    // Save to file
                    try (ObjectOutputStream wData = new ObjectOutputStream(new FileOutputStream(file))) {
                        wData.writeObject(bookList);
                    }
                    break;
                }
                case 4: { // Return a book
                    System.out.print("Enter the Book ID to return: ");
                    int id = inum.nextInt();

                    boolean found = false;
                    for (Library lib : bookList) {
                        if (lib.getBid() == id) {
                            lib.setQua(lib.getQua() + 1);
                            System.out.println("Book returned successfully!");
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Book ID not found.");
                    }

                    // Save to file
                    try (ObjectOutputStream wData = new ObjectOutputStream(new FileOutputStream(file))) {
                        wData.writeObject(bookList);
                    }
                    break;
                }
                case 0: {
                    System.out.println("Exiting...");
                    break;
                }
                default: {
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        } while (choice != 0);
    }
}
