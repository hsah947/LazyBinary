/* Name: Hitesh Sah
 * NetID: hks200000
 * Course: 3345.005
 * Project Description: Binary Search Tree with Lazy Deletion
 */

public class LazyBinarySearchTree {

    private static final int DOES_NOT_EXIST = -1;

    private TreeNode root; //instance variable for Binary Search Tree

    //inserting node in the tree
    public boolean insert(int key) {
        throwExceptionIfInvalid(key);
        TreeNode node = root;
        TreeNode parent = node;

        //Using while loop for checking and updating
        while (node != null) {
            parent = node;
            if (key < node.key) {
                node = node.leftChild;
            } else if (key > node.key) {
                node = node.rightChild;
            } else if (node.deleted) { // if equal but deleted, updated deleted flag and return true
                node.deleted = false;
                return true;
            } else { // if equal and not deleted, do nothing and return false
                return false;
            }
        }

        // if not returned yet create new node and attach to parent
        node = new TreeNode(key);
        if (parent == null) {
            this.root = node;
        } else if (key > parent.key) {
            parent.rightChild = node;
        } else {
            parent.leftChild = node;
        }

        return true;
    }

    //Deleting key from tree
    public boolean delete(int key) {
        throwExceptionIfInvalid(key);
        TreeNode found = find(key);

        // if found but not yet deleted, then delete logically and return true
        if (found != null && !found.deleted) {
            found.deleted = true;
            return true;
        }

        return false;
    }

    //finding minimum element
    public int findMin() {
        int min = DOES_NOT_EXIST;

        TreeNode node = root;
        while (node != null) {
            // if node is not deleted, assign to min
            if (!node.deleted) {
                min = node.key;
            }
            // go to left child for next iteration
            node = node.leftChild;
        }

        return min;
    }
    
    //finding max element
    public int findMax() {
        int max = DOES_NOT_EXIST;
        TreeNode node = root;

        while (node != null) {
            // if node is not deleted, assign to max
            if (!node.deleted) {
                max = node.key;
            }
            // go to right child for next iteration
            node = node.rightChild;
        }

        return max;
    }
    
   
    //Checking weather a key exist in the tree and is not deleted
    public boolean contains(int key) {
        throwExceptionIfInvalid(key);
        TreeNode found = find(key);

        return found != null && !found.deleted;
    }

    //returning height
    public int height() {
        int height = height(root);
        return height;
    }

    //returning size
    public int size() {
        int size = size(root);
        return size;
    }

    @Override
    public String toString() {
        return preOrderTraversal(root);
    }

    /* ------------Helper method---------------*/
    private TreeNode find(int key) {
        TreeNode found = null;

        TreeNode node = root;
        while (node != null) {
            if (key < node.key) {           // go left if key is less than current node
                node = node.leftChild;
            } else if (key > node.key) {    // go right, if key is greater than current node
                node = node.rightChild;
            } else {                       // if equal, check if node is deleted or not
                found = node;
                break;
            }
        }
        return found;
    }

    // finds height of tree starting from given node recursively
    private int height(TreeNode node) {
        // first base case
        if (node == null) {
            return 0;
        }

        // base case, as we are counting edges. i.e. tree with single node is considered of height 0
        if (node.leftChild == null && node.rightChild == null) {
            return 0;
        }

        return 1 + Math.max(height(node.leftChild), height(node.rightChild));
    }

    // finds size of tree starting from given node recursively
    private int size(TreeNode node) {
        // first base case
        if (node == null) {
            return 0;
        }

        return 1 + size(node.leftChild) + size(node.rightChild);
    }

    // throws IllegalArgumentException if the key is out of range
    private void throwExceptionIfInvalid(int key) {
        if (key < 1 || key > 99) {
            throw new IllegalArgumentException("Invalid key. Key should be in the range [1,99]");
        }
    }

    // concatenates all keys while performing pre-order traversal
    private String preOrderTraversal(TreeNode node) {
        String value = "";

        // base case
        if (node == null) {
            return value;
        }

        if (node.deleted) {
            value = "*";
        }
        value = value + node.key + " ";

        return value + preOrderTraversal(node.leftChild) + preOrderTraversal(node.rightChild);
    }

    // private nested class
    private static class TreeNode {
    	
    	//instance variable
        final int key;
        TreeNode leftChild;
        TreeNode rightChild;
        boolean deleted;

        // constructor
        TreeNode(int key) {
            this.key = key;
        }
    }
}
