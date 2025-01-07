/*************************************************************************
 *  Binary Search Tree class.
 *  Adapted from Sedgewick and Wayne.
 *
 *  @version 29/10/24 11:20:54
 *
 *  @author Avery Hochheiser
 *
 *************************************************************************/
package csu22011_a3;

import java.util.NoSuchElementException;
import java.util.Objects;

public class BST<Key extends Comparable<Key>, Value> {
    private Node root;             // root of BST

    /**
     * Private node class.
     */
    private class Node {
        private Key key;           // sorted by key
        private Value val;         // associated data
        private Node left, right;  // left and right subtrees
        private int N;             // number of nodes in subtree

        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    // is the symbol table empty?
    public boolean isEmpty() { return size() == 0; }

    // return number of key-value pairs in BST
    public int size() { return size(root); }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

    /**
     *  Search BST for given key.
     *  Does there exist a key-value pair with given key?
     *
     *  @param key the search key
     *  @return true if key is found and false otherwise
     */
    public boolean contains(Key key) {
        return get(key) != null;
    }

    /**
     *  Search BST for given key.
     *  What is the value associated with given key?
     *
     *  @param key the search key
     *  @return value associated with the given key if found, or null if no such key exists.
     */
    public Value get(Key key) { return get(root, key); }

    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else              return x.val;
    }

    /**
     *  Insert key-value pair into BST.
     *  If key already exists, update with new value.
     *
     *  @param key the key to insert
     *  @param val the value associated with key
     */
    public void put(Key key, Value val) {
        if (val == null) { delete(key); return; }
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = put(x.left,  key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else              x.val   = val;
        x.N = 1 + size(x.left) + size(x.right);
        return x;
    }

    /**
     * Tree height.
     *
     * Asymptotic worst-case running time using Theta notation: TODO
     *
     * @return the number of links from the root to the deepest leaf.
     *
     * Example 1: for an empty tree this should return -1.
     * Example 2: for a tree with only one node it should return 0.
     * Example 3: for the following tree it should return 2.
     *   B
     *  / \
     * A   C
     *      \
     *       D
     */
    public int height() { 
      if (isEmpty() == true) return -1; // constant time operation Theta(1)
      else if (root.left == null && root.right == null) return 0; // constant time operation Theta(1)
      return height(root); } // Theta(N)

    private int height(Node x) {
      if (x == null) return -1; // constant time operation Theta(1)
      int lH = height(x.left); int rH = height(x.right); // number of recursive calls grows proportionaltly to size of BST, Theta(N)
      int max = 0; // constant time operation Theta(1)
      if ( lH >= rH ) max = lH; else max = rH; // constant time operation Theta(1)
      return max + 1; // constant time operation Theta(1)
    }

    /**
     * Median key.
     * If the tree has N keys k1 < k2 < k3 < ... < kN, then their median key 
     * is the element at position (N+1)/2 (where "/" here is integer division)
     *
     * @return the median key, or null if the tree is empty.
     */
    public Key median() {
      if (isEmpty()) return null; // constant time operation Theta(1)
      int medKey = (size() - 1) / 2; // constant time operation Theta(1)
      return median(root, medKey); // Theta(N) b/c even with worst case, there will always be a median which will not be the last
                                    // element in right subtree, meaning it goes through <N times -> <N(constant) * N in theta = N
    }

    private Key median(Node x, int medKey) { 
      if (x == null) return null; // constant time operation Theta(1)
      Key left = median(x.left, medKey);
      if (left != null) return left; // constant time operation Theta(1)
      if (rank(x.key) == medKey) return x.key; // rank runs in Theta(N), else is constant, so Theta(N)
      return median(x.right, medKey);
    }

    // public Key select(int n) {
    //   if (n < 0 || n >= size()) return null;
    //   Node x = select(root, n);
    //   return x.key;
    //   }

    // private Node select(Node x, int n) {
    //   if (x == null) return null;
    //   int t = size(x.left);
    //   if (t > n) return select(x.left, n);
    //   else if (t < n) return select(x.right, n-t-1);
    //   else return x;
    // }

    public int rank(Key key) { return rank(key, root); } 

    private int rank(Key key, Node x) {
      if (x == null) return 0;
      int cmp = key.compareTo(x.key);
      if (cmp < 0) return rank(key, x.left);
      else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
      else return size(x.left);
    } 

    /**
     * Deletes a key from a tree (if the key is in the tree).
     * Note that this method works symmetrically from the Hibbard deletion:
     * If the node to be deleted has two child nodes, then it needs to be
     * replaced with its predecessor (not its successor) node.
     *
     * @param key the key to delete
     */
    public void delete(Key key) {
      if (isEmpty() || !contains(key)) return;
      //if (root.left == null && root.right == null && key != root.key) return;
      root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
      if (x == null) return null;
      if (key.compareTo(x.key) < 0) x.left = delete(x.left, key);
      else if (key.compareTo(x.key) > 0) x.right = delete(x.right, key);
      else { if (x.left == null && x.right == null) return null;
        if (x.right == null) return x.left;
        if (x.left == null) return x.right;
        else { Node pred = x;
          x = max(pred.left);
          x.left = deleteMax(pred.left);
          x.right = pred.right;
        }
      }
      return x;
    }

    // public void deleteMax() {
    //   root = deleteMax(root);
    // }

    private Node deleteMax(Node x) {
      if (x.right == null) return x.left;
      x.right = deleteMax(x.right);
      return x;
    }

    private Node max(Node x) {
      while (x.right != null) x = x.right;
      return x;
    }

    /**
     * Print all keys of the tree in a sequence in pre-order.
     * That is, for each node, the key of the node is printed first, then the keys in the left subtree are printed, and then the keys in the right subtree are printed.
     * For each subtree, its keys should appear within a parenthesis. For Null nodes, a pair of empty parentheses "()" should be printed.
     *
     * Example 1: Empty tree -- output: "()"
     * Example 2: Tree containing only "A" -- output: "(()A())"
     * Example 3: Tree:
     *   B
     *  / \
     * A   C
     *      \
     *       D
     *
     * output: "(B(A()())(C()(D()())))"
     *
     * See assignment for a longer example.
     *
     * @return a String with all keys in the tree, in pre-order, parenthesized.
     */
    public String printKeysPreOrder() {
      if (isEmpty()) return "()";
      //else if (root.left == null && root.right == null) return "(()" + root.key + "())";
      return "(" + printKeysPreOrder(root) + ")";
    }

    private String printKeysPreOrder(Node root) {
      if (root == null) return "";
      String string = root.key.toString();
      String lT = printKeysPreOrder(root.left);
      String rT = printKeysPreOrder(root.right); 

      return string += "(" + lT + ")(" + rT + ")";
    }
    
    /**
     * Pretty Printing the tree. Each node is on one line -- see assignment for details.
     *
     * @return a multi-line string with the pretty ascii picture of the tree.
     */
    public String prettyPrintKeys() {
      if (isEmpty()) return "-null\n";
      return prettyPrintKeys(root, "");
    }

    private String prettyPrintKeys(Node root, String prefix) {
      if (root == null) return prefix + "-null\n";

      String string = prefix + "-" + root.key.toString() + "\n";
      string += prettyPrintKeys(root.left, prefix +" |");
      string += prettyPrintKeys(root.right, prefix + "  ");

      return string;
    }

     /**
     * This method takes an array of Key objects and determines whether the
     * given sequence can represent the pre-order traversal of a BST.
     *
     * @param keys an array of keys
     */
    // public boolean isBSTPreOrder(Key[] keys){
    //   if (keys.length == 1 || keys == null) return true;
      
    //   //BST<Key, Value> bst = new BST<>();
    //   for ( int i = 0; i < keys.length; i ++ ) {
    //     put(keys[i], null);
    //   }
      
    //   Key[] testKeys = (Key[]) new Comparable[keys.length]; 
    //   int i = 0;
    //   isBSTPreOrder(root, testKeys, i);

    //   for ( int j = 0; j < keys.length; j ++ ) {
    //     if (!Objects.equals(testKeys[j], keys[j])) {
    //       return false;
    //     }
    //   }
    //   return true;
    // }

    // private int isBSTPreOrder(Node x, Key[] testKeys, int i) {
    //   if (x == null) return i;
    //   testKeys[i++]= x.key;
    //   i = isBSTPreOrder(x.left, testKeys, i);
    //   return isBSTPreOrder(x.right, testKeys, i);
    // }

    public boolean isBSTPreOrder(Key[] keys){
      if (keys == null) return true;
      return isBSTPreOrderRec(keys, 0, keys.length - 1);
    }

    private boolean isBSTPreOrderRec(Key[] keys, int startIndex, int endIndex) {
      if (startIndex >= endIndex) return true;
      
      int mid = startIndex + 1;

      while (mid <= endIndex && keys[mid].compareTo(keys[startIndex]) < 0) {
        mid++;
      }

      for (int i = mid; i <= endIndex; i ++) {
        if (keys[i].compareTo(keys[startIndex]) <= 0) return false;
      }

      boolean left = isBSTPreOrderRec(keys, startIndex + 1, mid - 1);
      boolean right = isBSTPreOrderRec(keys, mid + 1, endIndex);

      return left & right;
    }

}
