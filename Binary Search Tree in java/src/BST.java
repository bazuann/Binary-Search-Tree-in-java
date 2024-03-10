import javax.swing.*;
import java.awt.*;

class TreeNode {
    int data;
    TreeNode left;
    TreeNode right;

    public TreeNode(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

public class BST {
    private TreeNode root;

    public BST() {
        this.root = null;
    }

    public void insert(int x) {
        root = insertNode(root, x);
    }

    private TreeNode insertNode(TreeNode node, int x) {
        if (node == null) {
            return new TreeNode(x);
        }

        if (x < node.data) {
            node.left = insertNode(node.left, x);
        } else if (x > node.data) {
            node.right = insertNode(node.right, x);
        }

        return node;
    }

    public void delete(int x) {
        root = deletebuble(root, x);
    }

    private TreeNode deletebuble(TreeNode root, int x) {
        if (root == null) {
            return root;
        }

        if (x < root.data) {
            root.left = deletebuble(root.left, x);
        } else if (x > root.data) {
            root.right = deletebuble(root.right, x);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            root.data = minValue(root.right);

            root.right = deletebuble(root.right, root.data);
        }

        return root;
    }

    private int minValue(TreeNode root) {
        int minValue = root.data;
        while (root.left != null) {
            minValue = root.left.data;
            root = root.left;
        }
        return minValue;
    }

    public void draw() {
        JFrame frame = new JFrame("Binary Search Tree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TreePanel treePanel = new TreePanel(root);
        frame.getContentPane().add(treePanel);

        int treeWidth = treePanel.calculateTreeWidth();
        int treeHeight = treePanel.calculateTreeHeight();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - treeWidth) / 2;
        int y = (screenSize.height - treeHeight) / 2;

        frame.setBounds(x, y, treeWidth + 400, treeHeight + 400);
        frame.setVisible(true);
    }

    private static class TreePanel extends JPanel {
        private static final int NODE_RADIUS = 20;

        private TreeNode root;

        public TreePanel(TreeNode root) {
            this.root = root;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int treeWidth = calculateTreeWidth();
            int startX = (getWidth() - treeWidth) / 2;

            drawTree(g, startX + treeWidth / 20, 30, root, 2 * treeWidth / 5);
        }

        private void drawTree(Graphics g, int x, int y, TreeNode node, int xOffset) {
            if (node == null) {
                return;
            }

            g.drawOval(x - NODE_RADIUS, y - NODE_RADIUS, 2 * NODE_RADIUS, 2 * NODE_RADIUS);
            g.drawString(Integer.toString(node.data), x, y);

            if (node.left != null) {
                int nextX = x -  xOffset;
                int nextY = y + 150;
                g.drawLine(x, y + NODE_RADIUS, nextX + NODE_RADIUS, nextY - NODE_RADIUS);
                drawTree(g, nextX, nextY, node.left, xOffset );
            }

            if (node.right != null) {
                int nextX = x +  xOffset;
                int nextY = y + 150;
                g.drawLine(x + 1 * NODE_RADIUS, y + NODE_RADIUS, nextX - NODE_RADIUS, nextY - NODE_RADIUS);
                drawTree(g, nextX, nextY, node.right, xOffset );
            }
        }

        public int calculateTreeWidth() {
            return calculateSubtreeWidth(root);
        }

        private int calculateSubtreeWidth(TreeNode node) {
            if (node == null) {
                return 0;
            }

            int leftWidth = calculateSubtreeWidth(node.left);
            int rightWidth = calculateSubtreeWidth(node.right);

            return leftWidth + 2 * NODE_RADIUS + rightWidth;
        }

        public int calculateTreeHeight() {
            return calculateSubtreeHeight(root);
        }

        private int calculateSubtreeHeight(TreeNode node) {
            if (node == null) {
                return 0;
            }

            int leftHeight = calculateSubtreeHeight(node.left);
            int rightHeight = calculateSubtreeHeight(node.right);

            return Math.max(leftHeight, rightHeight) + 2 * NODE_RADIUS;
        }
    }

    public static void main(String[] args) {
        BST BST = new BST();
        BST.insert(3);
        BST.insert(56);
        BST.insert(40);
        BST.insert(7);
        BST.insert(68);
        BST.insert(20);
        BST.insert(70);
        
        BST.delete(20);
        BST.draw();
    }
}