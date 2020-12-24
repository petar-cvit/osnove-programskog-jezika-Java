package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * 
 * Class UniqueNumbers is used to form a binary tree with distinct values.
 * 
 * @author Petar Cvitanović
 *
 */
public class UniqueNumbers {
	
	/**
	 * 
	 * Class TreeNode represents one node in a binary tree. It has it's integer value
	 * and has two children, left and right.
	 * 
	 * @author Petar Cvitanović
	 *
	 */
	public static class TreeNode {
		
		/**
		 * Integer value of a tree node.
		 */
		int value;
		
		/**
		 * Tree node's left child.
		 */
		TreeNode left;
		
		/**
		 * Right node's right child.
		 */
		TreeNode right;
	}
	
	/**
	 * Method addNode adds one node to the tree. If the value that has to be added 
	 * is already in the tree it does nothing. If the value is greater than current node's
	 * value, the value is forwarded to right subtree, else it is forwarded to left subtree.
	 * Method recursively goes down the tree.
	 * 
	 * @param current node recursion is in
	 * @param integer value that needs to be added
	 * @return updated tree node that was given in parameters
	 */
	static TreeNode addNode(TreeNode node, int value) {
		if(node == null) {
			TreeNode newNode = new TreeNode();
			newNode.value = value;
			return newNode;
		} else {
			if(node.value > value) {
				node.left = addNode(node.left, value);
			} else if(node.value < value) {
				node.right = addNode(node.right, value);
			}
			return node;
		}
	}
	
	/**
	 * Method treeSize recursively goes through tree and counts its nodes.
	 * 
	 * @param current node recursion is in
	 * @return tree's size
	 */
	static int treeSize(TreeNode node) {
		if(node == null) {
			return 0;
		} else return treeSize(node.left) + treeSize(node.right) + 1;
	}
	
	/**
	 * Method containsValue returns boolean value whether a given value is in a tree or not.
	 * 
	 * @param current node recursion is in
	 * @param value that needs to be checked
	 * @return boolean value
	 */
	static boolean containsValue(TreeNode node, int value) {
		if(node == null) {
			return false;
		} else {
			return containsValue(node.left, value) || containsValue(node.right, value) 
					|| node.value == value;
		}
	}
	
	/**
	 * Method inorderMin prints out tree from left to right.
	 * 
	 * @param current node recursion is in 
	 */
	static void inorderMin(TreeNode node) {
		if(node != null) {
			inorderMin(node.left);
			System.out.printf("%d ", node.value);
			inorderMin(node.right);
		}
	}
	
	/**
	 * Method inorderMax prints out tree from right to left.
	 * 
	 * @param current node recursion is in
	 */
	static void inorderMax(TreeNode node) {
		if(node != null) {
			inorderMax(node.right);
			System.out.printf("%d ", node.value);
			inorderMax(node.left);
		}
	}
	
	/**
	 * Main method is called once the program starts running.
	 * It gets numbers from keyboard as input and forms a binary tree.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		TreeNode glava = null;
		
		while(true) {
			System.out.printf("Unesite broj > ");
			if(sc.hasNextInt()) {
				int element = sc.nextInt();
				if(containsValue(glava, element)) {
					System.out.println("Broj već postoji. Preskačem.");
				} else {
					glava = addNode(glava, element);
					System.out.println("Dodano.");
				}
			} else {
				String element = sc.next();
				if(element.equals("kraj")) {
					break;
				}
				System.out.printf("'%s' nije cijeli broj.\n", element);
			}
		}
		
		System.out.print("Ispis od najmanjeg: ");
		inorderMin(glava);
		System.out.print("\nIspis od najvećeg: ");
		inorderMax(glava);
		
		sc.close();
	}
}

	










