package least_common_multiple_with_prime_factors_as_binary_tree;

public class Node {
    public int value;
    public Node parent;
    public Node left;
    public Node right;

    public Node(int value, Node parent, Node left, Node right) {
        this.value = value;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }
}
