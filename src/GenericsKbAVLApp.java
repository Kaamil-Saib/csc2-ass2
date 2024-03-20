import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Application to read in GenericsKB.txt and store data items in an AVL Tree
 */
public class GenericsKbAVLApp {
    /**
     * main method
     */
    public static void main(String[] args) {
        AVLTree<String, String> kbAVL = new AVLTree<>();

        Scanner scanner = new Scanner(System.in);
        int user_input = 0;

        while (user_input != 5) {
            System.out.println("Choose an action from the menu:\n" +
                    "1. Load a knowledge base from a file\n" +
                    "2. Add a new statement to the knowledge base\n" +
                    "3. Search for an item in the knowledge base by term\n" +
                    "4. Search for an item in the knowledge base by term and sentence\n" +
                    "5. Quit\n" +
                    "Enter your choice:");

            user_input = scanner.nextInt();
            scanner.nextLine();

            switch (user_input) {
                case 1:
                    loadKnowledgeBase(kbAVL);
                    break;

                case 2:
                    if (!kbAVL.isEmpty()) {
                        addStatement(kbAVL, scanner);
                    } else {
                        System.out.println("\nKnowledge base has not been loaded.\n");
                    }
                    break;

                case 3:
                    if (!kbAVL.isEmpty()) {
                        searchByTerm(kbAVL, scanner);
                    } else {
                        System.out.println("\nKnowledge base has not been loaded.\n");
                    }
                    break;

                case 4:
                    if (!kbAVL.isEmpty()) {
                        searchByTermAndSentence(kbAVL, scanner);
                    } else {
                        System.out.println("\nKnowledge base has not been loaded.\n");
                    }
                    break;

                case 5:
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }

        scanner.close();
    }

    /**
     * load KB
     *
     * @param kbAVL AVL tree to load KB into
     */
    private static void loadKnowledgeBase(AVLTree<String, String> kbAVL) {
        try {
            File file = new File("GenericsKB.txt");
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\t");
                if (parts.length == 3) {
                    String term = parts[0].trim();
                    String statement = parts[1].trim();
                    double confidenceScore = Double.parseDouble(parts[2].trim());
                    kbAVL.insert(term, statement + " (Confidence: " + confidenceScore + ")");
                } else {
                    System.out.println("Invalid format in line: " + line);
                }
            }

            fileScanner.close();
            System.out.println("\nKnowledge base loaded successfully.\n");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: GenericsKB.txt");
        } catch (NumberFormatException e) {
            System.out.println("Invalid confidence score format.");
        }
    }

    /**
     * add statement to KB
     *
     * @param kbAVL   knowledge base AVL tree
     * @param scanner read input
     */
    private static void addStatement(AVLTree<String, String> kbAVL, Scanner scanner) {
        System.out.println("Enter the term: ");
        String term = scanner.nextLine().trim();

        System.out.println("Enter the statement: ");
        String statement = scanner.nextLine().trim();

        System.out.println("Enter the confidence interval (0 to 1): ");
        double confidenceInterval = scanner.nextDouble();

        scanner.nextLine();

        kbAVL.insert(term, statement + " (Confidence: " + confidenceInterval + ")");
        System.out.println("\nStatement for term " + term + " has been added with confidence interval "
                + confidenceInterval + ".\n");
    }

    /**
     * search for item in KB by term
     *
     * @param kbAVL   knowledge baseAVL tree
     * @param scanner read input
     */
    private static void searchByTerm(AVLTree<String, String> kbAVL, Scanner scanner) {
        System.out.println("Enter the term to search: ");
        String searchTerm = scanner.nextLine().trim();
        String result = kbAVL.search(searchTerm);
        if (result != null) {
            System.out.println("\nResult: " + result + "\n");
        } else {
            System.out.println("\nTerm not found in the knowledge base.\n");
        }
    }

    /**
     * search for item in KB by term and sentence
     *
     * @param kbAVL   KB AVL tree
     * @param scanner read input
     */
    private static void searchByTermAndSentence(AVLTree<String, String> kbAVL, Scanner scanner) {
        System.out.println("Enter the term to search: ");
        String searchTerm = scanner.nextLine().trim();

        System.out.println("Enter the statement to search for: ");
        String searchStatement = scanner.nextLine().trim();

        boolean found = false;
        String result = kbAVL.search(searchTerm);
        if (result != null && result.contains(searchStatement)) {
            System.out.println("\nResult: " + result + "\n");
            found = true;
        }

        if (!found) {
            System.out.println("\nTerm and statement not found in knowledge base.\n");
        }
    }
}
