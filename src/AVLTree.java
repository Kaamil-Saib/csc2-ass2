/**
 * Student Name: Kaamil Saib
 * Student Number: SBXKAA001
 * CSC2001F Assignment 2
 */

/**
 * @param <K> keys stored in the AVL tree
 * @param <V> value with keys
 */
public class AVLTree<K extends Comparable<K>, V> {
    private Node root;
    private int InsertOpCount = 0;
    private int SearchOpCount = 0;

    /**
     * checks if AVL tree is empty
     *
     * @return true if tree is empty, false otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * gets total number of ops done by search
     * 
     * @param node
     * @return total number of ops done by insert
     */
    public int totalInsertOps() {
        return InsertOpCount;
    }

    /**
     * gets total number of ops done by search
     * 
     * @param node
     * @return total number of ops done by search
     */
    public int totalSearchOps() {
        return SearchOpCount;
    }

    /**
     * gets height of AVL tree
     * 
     * @param node
     * @return height of node
     */
    private int height(Node node) {
        if (node == null)
            return 0;
        return node.height;
    }

    /**
     * gets the balance factor
     * 
     * @param node
     * @return balance factor of the node
     */
    private int getBalance(Node node) {
        if (node == null)
            return 0;
        return height(node.left) - height(node.right);
    }

    /**
     * right rotates subtree rooted with y
     * 
     * @param y root node of the subtree to be rotated
     * @return new root of the subtree
     */
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    /**
     * left rotates subtree rooted with x
     * 
     * @param x root node of the subtree to be rotated
     * @return new root of the subtree
     */
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    /**
     * inserts a key value pair into AVL tree
     * 
     * @param key   key to insert
     * @param value value associated with the key
     */
    public void insert(K key, V value) {
        root = insertHelper(root, key, value);
    }

    /**
     * helper funtion for insert
     * recursively insert a key value pair into AVL tree
     * 
     * @param node  node being processed
     * @param key   key to insert
     * @param value value with key
     * @return updated root node of the subtree
     */
    private Node insertHelper(Node node, K key, V value) {
        // normal BST insert
        if (node == null)
            return new Node(key, value);

        if (key.compareTo(node.key) < 0) {
            InsertOpCount++; // counting ops
            node.left = insertHelper(node.left, key, value);
        } else if (key.compareTo(node.key) > 0) {
            InsertOpCount++; // counting ops
            node.right = insertHelper(node.right, key, value);
        } else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        // get balance factor
        int balance = getBalance(node);

        // if unbalanced

        // left left
        if (balance > 1 && key.compareTo(node.left.key) < 0) {
            InsertOpCount++; // counting ops
            return rightRotate(node);
        }

        // right rigth
        if (balance < -1 && key.compareTo(node.right.key) > 0) {
            InsertOpCount++; // counting ops
            return leftRotate(node);
        }

        // left right
        if (balance > 1 && key.compareTo(node.left.key) > 0) {
            InsertOpCount++; // counting ops
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // rigth left
        if (balance < -1 && key.compareTo(node.right.key) < 0) {
            InsertOpCount++; // counting ops
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    /**
     * search for key in AVL tree and return its value
     * 
     * @param key key to search for
     * @return value with key or null if the key is not found
     */
    public V search(K key) {
        return searchRec(root, key);
    }

    /**
     * recursively searches for a key in the AVL tree and returns itsassociated
     * value
     * 
     * @param node         node being processed
     * @param key          key to search for
     * @param compareCount counts the number of comparisons
     * @return value with the key, or null if the key is not found
     */
    private V searchRec(Node node, K key) {
        if (node == null) {
            return null;
        }
        if (key.equals(node.key)) {
            SearchOpCount++; // counting ops
            return node.value;
        } else if (key.compareTo(node.key) < 0) {
            SearchOpCount++; // counting ops
            return searchRec(node.left, key);
        } else
            return searchRec(node.right, key);
    }

    /**
     * prints AVL tree with in-order traversal
     */
    public void printInOrder() {
        printInOrderRec(root);
    }

    /**
     * recursively prints AVL tree with in-order traversal
     * 
     * @param node node being processed
     */
    private void printInOrderRec(Node node) {
        if (node != null) {
            printInOrderRec(node.left);
            System.out.println(node.key + ": " + node.value);
            printInOrderRec(node.right);
        }
    }

    /**
     * Node in AVL tree
     */
    private class Node {
        K key;
        V value;
        Node left, right;
        int height;

        /**
         * constructs a new node with given key and value
         * 
         * @param key   key of the node
         * @param value value of key
         */
        Node(K key, V value) {
            this.key = key;
            this.value = value;
            height = 1;
        }
    }
}