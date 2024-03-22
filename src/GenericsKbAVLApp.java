
/**
 * Student Name: Kaamil Saib
 * Student Number: SBXKAA001
 * CSC2001F Assignment 2
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class GenericsKbAVLApp {
    /**
     * main method
     */
    public static void main(String[] args) {
        AVLTree<String, String> kbAVL = new AVLTree<>();

        // storing data from experiment
        String fileName = "ExperimentData.txt";
        try {
            FileWriter file = new FileWriter(fileName);
            // for (int n = 5; n < 50000; n *= 2) {
            // load data from GenericsKB.txt into AVL tree
            loadKnowledgeBase("GenericsKB.txt", kbAVL, 50000);
            // perform searches based on terms from GenericsKB-queries.txt
            performSearches("GenericsKB-queries.txt", kbAVL);
            // Instrumentation...
            // file.write(kbAVL.totalSearchOps() + "\t" + n + "\n");
            // }
            file.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * load KB
     *
     * @param fileName   name of the file to load data from
     * @param kbAVL      AVL tree to load KB into
     * @param sampleSize size of sample we are taking from KB
     */
    private static void loadKnowledgeBase(String fileName, AVLTree<String, String> kbAVL, int sampleSize) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            int lineCount = 0;
            int totalLines = 50000;
            Random random = new Random();

            if (sampleSize == 50000) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split("\t");
                    String term = parts[0].trim();
                    String sentence = parts[1].trim();
                    String confidenceScore = parts[2].trim();
                    kbAVL.insert(term, sentence + "\t" + confidenceScore);
                    lineCount++;
                }
            } else {
                while (scanner.hasNextLine() && lineCount < sampleSize) {
                    // skip to random line
                    int randomLine = random.nextInt(totalLines);
                    for (int j = 0; j < randomLine && scanner.hasNextLine(); j++) {
                        scanner.nextLine();
                    }
                    if (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] parts = line.split("\t");
                        String term = parts[0].trim();
                        String sentence = parts[1].trim();
                        String confidenceScore = parts[2].trim();
                        kbAVL.insert(term, sentence + "\t" + confidenceScore);
                        lineCount++;
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            e.printStackTrace();
        }
    }

    /**
     * perform searches and print the results
     *
     * @param fileName name of the file containing search terms
     * @param kbAVL    AVL tree to search in
     */
    private static void performSearches(String fileName, AVLTree<String, String> kbAVL) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String searchTerm = scanner.nextLine().trim();
                String result = kbAVL.search(searchTerm);
                if (result != null) {
                    // Part 1 //System.out.println(searchTerm + ": " + result);
                } else {
                    // Part 1 //System.out.println("Term not found: " + searchTerm);
                }
            }
            // printing out total number of ops
            System.out.println(kbAVL.totalInsertOps());

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            e.printStackTrace();
        }
    }
}