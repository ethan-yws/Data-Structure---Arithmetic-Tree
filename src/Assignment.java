import java.util.ArrayList;
import java.util.HashMap;

import textbook.LinkedBinaryTree;
import textbook.LinkedQueue;
import textbook.Position;

public class Assignment {

	/**
	 * Convert an arithmetic expression (in prefix notation), to a binary tree
	 * 
	 * Binary operators are +, -, * (i.e. addition, subtraction, multiplication)
	 * Anything else is assumed to be a variable or numeric value
	 * 
	 * Example: "+ 2 15" will be a tree with root "+", left child "2" and right
	 * child "15" i.e. + 2 15
	 * 
	 * Example: "+ 2 - 4 5" will be a tree with root "+", left child "2", right
	 * child a subtree representing "- 4 5" i.e. + 2 - 4 5
	 * 
	 * This method runs in O(n) time
	 * 
	 * @param expression
	 *            - an arithmetic expression in prefix notation
	 * @return BinaryTree representing an expression expressed in prefix
	 *         notation
	 * @throws IllegalArgumentException
	 *             if expression was not a valid expression
	 */
	public static LinkedBinaryTree<String> prefix2tree(String expression) throws IllegalArgumentException {
		if (expression == null) {
			throw new IllegalArgumentException("Expression string was null");
		}
		// break up the expression string using spaces, into a queue
		LinkedQueue<String> tokens = new LinkedQueue<String>();
		for (String token : expression.split(" ")) {
			tokens.enqueue(token);
		}
		// recursively build the tree
		return prefix2tree(tokens);
	}
	
	/**
	 * Recursive helper method to build an tree representing an arithmetic
	 * expression in prefix notation, where the expression has already been
	 * broken up into a queue of tokens
	 * 
	 * @param tokens
	 * @return
	 * @throws IllegalArgumentException
	 *             if expression was not a valid expression
	 */
	private static LinkedBinaryTree<String> prefix2tree(LinkedQueue<String> tokens) throws IllegalArgumentException {
		LinkedBinaryTree<String> tree = new LinkedBinaryTree<String>();

		// use the next element of the queue to build the root
		if (tokens.isEmpty()) {
			throw new IllegalArgumentException("String was not a valid arithmetic expression in prefix notation");
		}
		String element = tokens.dequeue();
		tree.addRoot(element);

		// if the element is a binary operation, we need to build the left and
		// right subtrees
		if (element.equals("+") || element.equals("-") || element.equals("*")) {
			LinkedBinaryTree<String> left = prefix2tree(tokens);
			LinkedBinaryTree<String> right = prefix2tree(tokens);
			tree.attach(tree.root(), left, right);
		}
		// otherwise, assume it's a variable or a value, so it's a leaf (i.e.
		// nothing more to do)

		return tree;
	}
	
	/**
	 * Test to see if two trees are identical (every position in the tree stores the same value)
	 * 
	 * e.g. two trees representing "+ 1 2" are identical to each other, but not to a tree representing "+ 2 1"
	 * @param a
	 * @param b
	 * @return true if the trees have the same structure and values, false otherwise
	 */
	public static boolean equals(LinkedBinaryTree<String> a, LinkedBinaryTree<String> b) {
		return equals(a, b, a.root(), b.root());
	}

	/**
	 * Recursive helper method to compare two trees
	 * @param aTree one of the trees to compare
	 * @param bTree the other tree to compare
	 * @param aRoot a position in the first tree
	 * @param bRoot a position in the second tree (corresponding to a position in the first)
	 * @return true if the subtrees rooted at the given positions are identical
	 */
	private static boolean equals(LinkedBinaryTree<String> aTree, LinkedBinaryTree<String> bTree, Position<String> aRoot, Position<String> bRoot) {
		//if either of the positions is null, then they are the same only if they are both null
		if(aRoot == null || bRoot == null) {
			return (aRoot == null) && (bRoot == null);
		}
		//first check that the elements stored in the current positions are the same
		String a = aRoot.getElement();
		String b = bRoot.getElement();
		if((a==null && b==null) || a.equals(b)) {
			//then recursively check if the left subtrees are the same...
			boolean left = equals(aTree, bTree, aTree.left(aRoot), bTree.left(bRoot));
			//...and if the right subtrees are the same
			boolean right = equals(aTree, bTree, aTree.right(aRoot), bTree.right(bRoot));
			//return true if they both matched
			return left && right;
		}
		else {
			return false;
		}
	}

	
	/**
	 * Given a tree, this method should output a string for the corresponding
	 * arithmetic expression in prefix notation, without (parenthesis) (also
	 * known as Polish notation)
	 * 
	 * Example: A tree with root "+", left child "2" and right child "15" would
	 * be "+ 2 15" Example: A tree with root "-", left child a subtree
	 * representing "(2+15)" and right child "4" would be "- + 2 15 4"
	 * 
	 * Ideally, this method should run in O(n) time
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @return prefix notation expression of the tree
	 * @throws IllegalArgumentException
	 *             if tree was not a valid expression
	 */
	public static String tree2prefix(LinkedBinaryTree<String> tree) throws IllegalArgumentException {
		// TODO: Implement this method
		
	//Loading the given tree into the isArithmeticExpression method to test
	//it is a valid expression or not, if false, throws IllegalArgumentException, vice verse. 
		if(!isArithmeticExpression(tree)) {
			throw new IllegalArgumentException();
		}
		
		String prefix = tree2prefix(tree, tree.root());
		if(tree.size() == 1) {
			return prefix;
		}
		//for tree size > 1, string returned will add 1 extra space at last
		//thus, return the string except the last one character which is the extra space
		return prefix.substring(0, prefix.length()-1);
	}
	
	/**Recursive helper method to visit the tree in Preorder*/
	private static String tree2prefix(LinkedBinaryTree<String> Tree, Position<String> root){
		String rawPrefix ="";
		// Case 1: 1 sized tree, No space required
		if(root != null && Tree.size() == 1) {
			rawPrefix += root.getElement();
		}
		// Case 2: more than 1 node, spaces required
		else if(root != null && Tree.size() > 1){
			rawPrefix += root.getElement();
			rawPrefix += " ";
			rawPrefix += tree2prefix(Tree, Tree.left(root));
			
			rawPrefix += tree2prefix(Tree, Tree.right(root));
		}
		return rawPrefix;
	}
	
	/**
	 * Given a tree, this method should output a string for the corresponding
	 * arithmetic expression in infix notation with parenthesis (i.e. the most
	 * commonly used notation).
	 * 
	 * Example: A tree with root "+", left child "2" and right child "15" would
	 * be "(2+15)"
	 * 
	 * Example: A tree with root "-", left child a subtree representing "(2+15)"
	 * and right child "4" would be "((2+15)-4)"
	 * 
	 * Optionally, you may leave out the outermost parenthesis, but it's fine to
	 * leave them on. (i.e. "2+15" and "(2+15)-4" would also be acceptable
	 * output for the examples above)
	 * 
	 * Ideally, this method should run in O(n) time
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @return infix notation expression of the tree
	 * @throws IllegalArgumentException
	 *             if tree was not a valid expression
	 */
	public static String tree2infix(LinkedBinaryTree<String> tree) throws IllegalArgumentException {
		// TODO: Implement this method
		
	//Loading the given tree into the isArithmeticExpression method to test
	//it is a valid expression or not, if false, throws IllegalArgumentException, vice verse.
		if(!isArithmeticExpression(tree)) {
			throw new IllegalArgumentException();
		}
		
		return inorder(tree, tree.root());
	}
	
	/**Recursive Helper method to visit the given tree in Inorder*/
	private static String inorder(LinkedBinaryTree<String> tree, Position<String> root) {
		String rawInfix = "";
		if(root != null) {
			//Case 1: a single node tree, No parenthesis required
			if(tree.left(root) == null && tree.right(root) == null) {
				rawInfix += root.getElement();
			}
			//Case 2: more than a node, parenthesis required
			if(tree.left(root) != null) {
				rawInfix += "(" + inorder(tree, tree.left(root));
				rawInfix += root.getElement();
			}
			if(tree.right(root) != null) {
				rawInfix += inorder(tree, tree.right(root)) + ")";
			}
		}
		return rawInfix;
	}

	/**
	 * Given a tree, this method should simplify any subtrees which can be
	 * evaluated to a single integer value.
	 * 
	 * Ideally, this method should run in O(n) time
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @return resulting binary tree after evaluating as many of the subtrees as
	 *         possible
	 * @throws IllegalArgumentException
	 *             if tree was not a valid expression
	 */
	public static LinkedBinaryTree<String> simplify(LinkedBinaryTree<String> tree) throws IllegalArgumentException {
		// TODO: Implement this method
		
	//Loading the given tree into the isArithmeticExpression method to test
	//it is a valid expression or not, if false, throws IllegalArgumentException, vice verse.
		if(!isArithmeticExpression(tree)) {
			throw new IllegalArgumentException();
		}
	
		return simplify(tree, tree.root());
	}
	
	/**Recursive Helper method to simplify the subtrees.*/
	private static LinkedBinaryTree<String> simplify(LinkedBinaryTree<String> tree, Position<String> root){
		//Checking each positions' elements, if its operators, checking the two children' elements
		//if both are Integers, doing the corresponding calculation and replace the root's value
		//otherwise, keep recursions.
		if(root.getElement().equals("+") || root.getElement().equals("-") || root.getElement().equals("*")) {
			
			if(!isNumber(tree.left(root).getElement()) || !isNumber(tree.right(root).getElement())) {
				simplify(tree, tree.left(root));
				simplify(tree, tree.right(root));
			}
			
			if(isNumber(tree.left(root).getElement()) && isNumber(tree.right(root).getElement())) {
				if(root.getElement().equals("+")) {
					int result = Integer.parseInt(tree.left(root).getElement()) + Integer.parseInt(tree.right(root).getElement());
					tree.remove(tree.left(root));
					tree.remove(tree.right(root));
					tree.set(root, String.valueOf(result));
				}
				if(root.getElement().equals("-")) {
					int result = Integer.parseInt(tree.left(root).getElement()) - Integer.parseInt(tree.right(root).getElement());
					tree.remove(tree.left(root));
					tree.remove(tree.right(root));
					tree.set(root, String.valueOf(result));
				}
				if(root.getElement().equals("*")) {
					int result = Integer.parseInt(tree.left(root).getElement()) * Integer.parseInt(tree.right(root).getElement());
					tree.remove(tree.left(root));
					tree.remove(tree.right(root));
					tree.set(root, String.valueOf(result));
				}
				
			}
		}
		
		return tree;
	}
	
	/**Helper method to determine a String is numeric or not*/
	private static boolean isNumber(String str) {
		try {
			Integer.parseInt(str);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	/**
	 * This should do everything the simplify method does AND also apply the following rules:
	 *  * 1 x == x  i.e.  (1*x)==x
	 *  * x 1 == x        (x*1)==x
	 *  * 0 x == 0        (0*x)==0
	 *  * x 0 == 0        (x*0)==0
	 *  + 0 x == x        (0+x)==x
	 *  + x 0 == 0        (x+0)==x
	 *  - x 0 == x        (x-0)==x
	 *  - x x == 0        (x-x)==0
	 *  
	 *  Example: - * 1 x x == 0, in infix notation: ((1*x)-x) = (x-x) = 0
	 *  
	 * Ideally, this method should run in O(n) time (harder to achieve than for other methods!)
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @return resulting binary tree after applying the simplifications
	 * @throws IllegalArgumentException
	 *             if tree was not a valid expression
	 */
	public static LinkedBinaryTree<String> simplifyFancy(LinkedBinaryTree<String> tree) throws IllegalArgumentException {
		// TODO: Implement this method
		
	//Loading the given tree into the isArithmeticExpression method to test
	//it is a valid expression or not, if false, throws IllegalArgumentException, vice verse.
		if(!isArithmeticExpression(tree)) {
			throw new IllegalArgumentException();
		}
		
		return simplifyFancy(tree, tree.root());
	}
	
	/**Recursive Helper method to simplifyFancy the given tree*/
	private static LinkedBinaryTree<String> simplifyFancy(LinkedBinaryTree<String> tree, Position<String> root){
		LinkedBinaryTree<String> simplifiedtree = simplify(tree);
		Position<String> Root = simplifiedtree.root();
		//Checking each position' element, if it's operator, checking the left and right child' elements
		//if they matches [a-z0-1] do the corresponding 
		//otherwise, keep recursion
		if(Root.getElement().equals("+") || Root.getElement().equals("-") || Root.getElement().equals("*")) {
			if(simplifiedtree.left(Root).getElement().matches("[a-z]") && !simplifiedtree.right(Root).getElement().matches("[a-z0-1]")){
				simplifyFancy(simplifiedtree, simplifiedtree.left(Root));
				simplifyFancy(simplifiedtree, simplifiedtree.right(Root));
			}
			if(simplifiedtree.right(Root).getElement().matches("[a-z]") && !simplifiedtree.left(Root).getElement().matches("[a-z0-1]")) {
				simplifyFancy(simplifiedtree, simplifiedtree.left(Root));
				simplifyFancy(simplifiedtree, simplifiedtree.right(Root));
			}
			//SimplifyFancy Rules
			//Case 1: left child is letter and right child is letter or number 0 and 1
			if(simplifiedtree.left(Root).getElement().matches("[a-z]") && simplifiedtree.right(Root).getElement().matches("[a-z0-1]")) {
				if(Root.getElement().equals("*")) {
					//eg. * x 1 = x
					if(simplifiedtree.right(Root).getElement().equals("1")) {
						String result = simplifiedtree.left(Root).getElement();
						simplifiedtree.remove(simplifiedtree.left(Root));
						simplifiedtree.remove(simplifiedtree.right(Root));
						simplifiedtree.set(Root, result);
					}
					//eg. * x 0 = 0
					else if(simplifiedtree.right(Root).getElement().equals("0")) {
						simplifiedtree.remove(simplifiedtree.left(Root));
						simplifiedtree.remove(simplifiedtree.right(Root));
						simplifiedtree.set(Root, "0");
					}
				}

				if(Root.getElement().equals("+")) {
					//eg. + x 0 = x
					if(simplifiedtree.right(Root).getElement().equals("0")) {
						String result = simplifiedtree.left(Root).getElement();
						simplifiedtree.remove(simplifiedtree.left(Root));
						simplifiedtree.remove(simplifiedtree.right(Root));
						simplifiedtree.set(Root, result);
					}
				}

				if(Root.getElement().equals("-")) {
					//eg. - x 0 = x
					if(simplifiedtree.right(Root).getElement().equals("0")) {
						String result = simplifiedtree.left(Root).getElement();
						simplifiedtree.remove(simplifiedtree.left(Root));
						simplifiedtree.remove(simplifiedtree.right(Root));
						simplifiedtree.set(Root, result);
					}
					//eg. - x x = 0
					else if(simplifiedtree.right(Root).getElement().equals(simplifiedtree.left(Root).getElement())) {
						simplifiedtree.remove(simplifiedtree.left(Root));
						simplifiedtree.remove(simplifiedtree.right(Root));
						simplifiedtree.set(Root, "0");
					}
				}
			}

			//Case 2: right child is letter and left child is letter or number 0 and 1
			else if(simplifiedtree.right(Root).getElement().matches("[a-z]") && simplifiedtree.left(Root).getElement().matches("[a-z0-1]")) {
				if(Root.getElement().equals("*")) {
					//eg. * 1 x = x
					if(simplifiedtree.left(Root).getElement().equals("1")) {
						String result = simplifiedtree.right(Root).getElement();
						simplifiedtree.remove(simplifiedtree.left(Root));
						simplifiedtree.remove(simplifiedtree.right(Root));
						simplifiedtree.set(Root, result);
					}
					//eg. * 0 x = 0
					else if(simplifiedtree.left(Root).getElement().equals("0")) {
						simplifiedtree.remove(simplifiedtree.left(Root));
						simplifiedtree.remove(simplifiedtree.right(Root));
						simplifiedtree.set(Root, "0");
					}
				}

				if(Root.getElement().equals("+")) {
					//eg. + 0 x = x
					if(simplifiedtree.left(Root).getElement().equals("0")) {
						String result = simplifiedtree.right(Root).getElement();
						simplifiedtree.remove(simplifiedtree.left(Root));
						simplifiedtree.remove(simplifiedtree.right(Root));
						simplifiedtree.set(Root, result);
					}
				}
			}
		}
		return simplifiedtree;
	}

	
	/**
	 * Given a tree, a variable label and a value, this should replace all
	 * instances of that variable in the tree with the given value
	 * 
	 * Ideally, this method should run in O(n) time (quite hard! O(n^2) is easier.)
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @param variable
	 *            - a variable label that might exist in the tree
	 * @param value
	 *            - an integer value that the variable represents
	 * @return Tree after replacing all instances of the specified variable with
	 *         it's numeric value
	 * @throws IllegalArgumentException
	 *             if tree was not a valid expression, or either of the other
	 *             arguments are null
	 */
	public static LinkedBinaryTree<String> substitute(LinkedBinaryTree<String> tree, String variable, int value)
			throws IllegalArgumentException {
		// TODO: Implement this method
		
	//Loading the given tree into the isArithmeticExpression method to test
	//it is a valid expression or not, if false, throws IllegalArgumentException, vice verse.
		if(!isArithmeticExpression(tree) || variable == null || (Integer)value == null) {
			throw new IllegalArgumentException();
		}
		//Retrieving the prefix notation from tree2prefix method,
		//adding the string into the arraylist char by char
		ArrayList<String> al = new ArrayList<String>();
		String prefix = tree2prefix(tree);
		for(int i=0;i<prefix.length();i++) {
			al.add(String.valueOf(prefix.charAt(i)));
		}
		//go through the arraylist, replcaing the targets by the values.
		for(int i=0;i<al.size();i++) {
			if(al.get(i).equals(variable)) {
				al.set(i, String.valueOf(value));
			}
		}
		//Retrieving the new prefix notation from the ArrayList
		String aftersub ="";
		for(int i=0;i<al.size();i++) {
			aftersub += al.get(i);
		}
		//Retrieving the new tree from prefix2tree method.
		LinkedBinaryTree<String> treeAfterSub = prefix2tree(aftersub);
		
		return treeAfterSub;
	}

	/**
	 * Given a tree and a a map of variable labels to values, this should
	 * replace all instances of those variables in the tree with the
	 * corresponding given values
	 * 
	 * Ideally, this method should run in O(n) expected time
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @param map
	 *            - a map of variable labels to integer values
	 * @return Tree after replacing all instances of variables which are keys in
	 *         the map, with their numeric values
	 * @throws IllegalArgumentException
	 *             if tree was not a valid expression, or map is null, or tries
	 *             to substitute a null into the tree
	 */
	public static LinkedBinaryTree<String> substitute(LinkedBinaryTree<String> tree, HashMap<String, Integer> map)
			throws IllegalArgumentException {
		// TODO: Implement this method
		
	//Loading the given tree into the isArithmeticExpression method to test
	//it is a valid expression or not, if false, throws IllegalArgumentException, vice verse.
		if(!isArithmeticExpression(tree) || map == null) {
			throw new IllegalArgumentException();
		}
		for(String k : map.keySet()) {
			if(map.get(k) == null) {
				throw new IllegalArgumentException();
			}
		}
		//adding all the keys (target strings) into a arraylist
		LinkedBinaryTree<String> treeAfterSubMap = tree;
		ArrayList<String> keys = new ArrayList<String>();
		for(String s : map.keySet()) {
			keys.add(s);
		}
		//Calling the substitte(tree, string, int) method to parse each elements inside the
		//arraylist and using map.get retrieve corresponding values
		for(int i=0;i<keys.size();i++) {
			treeAfterSubMap = substitute(treeAfterSubMap, keys.get(i), map.get(keys.get(i)));
		}
		
		return treeAfterSubMap;
	}

	/**
	 * Given a tree, identify if that tree represents a valid arithmetic
	 * expression (possibly with variables)
	 * 
	 * Ideally, this method should run in O(n) expected time
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @return true if the tree is not null and it obeys the structure of an
	 *              arithmetic expression. Otherwise, it returns false
	 */
	public static boolean isArithmeticExpression(LinkedBinaryTree<String> tree) {
		// TODO: Implement this method
		if(tree == null) {
			return false;
		}
		
		if(tree.size() % 2 == 0) {
			return false;
		}
		//Retrieving the infix notation from helper method
		String equation = ArithmeticExpression(tree, tree.root());
		int eqlength = equation.length();
		//if the last char is operator, return fasle;
		if(equation.charAt(eqlength-1) == '+' || equation.charAt(eqlength-1) == '-' || equation.charAt(eqlength-1) == '*') {
			return false;
		}
		//checking the even number positions character is number or not, if is operator, fasle;
		//eg. "1+2*3" even number char is number and odd number char is operator.
		for(int i=0;(i+2)<eqlength;i+=2) {
			if(equation.charAt(i) == '+' || equation.charAt(i) == '-' || equation.charAt(i) == '*') {
				return false;
			}
		}
		
		return true;
	}
	
	/**Recursive Helper method to visit the given tree in Inorder*/
	private static String ArithmeticExpression(LinkedBinaryTree<String> tree, Position<String> root) {
		String infix = "";
		if(root != null) {
			if(tree.left(root) == null && tree.right(root) == null) {
				infix += root.getElement();
			}
			if(tree.left(root) != null) {
				infix += ArithmeticExpression(tree, tree.left(root));
				infix += root.getElement();
			}
			if(tree.right(root) != null) {
				infix += ArithmeticExpression(tree, tree.right(root));
			}
		}
		return infix;
	}
}

