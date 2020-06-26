import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import textbook.LinkedBinaryTree;

public class TestAssignment {
	
	// Set up JUnit to be able to check for expected exceptions
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	// Some simple testing of prefix2tree
	@Test(timeout = 100)
	public void testPrefix2tree() {
		
		LinkedBinaryTree<String> tree;

		tree = Assignment.prefix2tree("hi");
		assertEquals(1, tree.size());
		assertEquals("hi", tree.root().getElement());

		tree = Assignment.prefix2tree("+ 5 10");
		assertEquals(3, tree.size());
		assertEquals("+", tree.root().getElement());
		assertEquals("5", tree.left(tree.root()).getElement());
		assertEquals("10", tree.right(tree.root()).getElement());
		
		tree = Assignment.prefix2tree("- 5 10");
		assertEquals(3, tree.size());
		assertEquals("-", tree.root().getElement());
		assertEquals("5", tree.left(tree.root()).getElement());
		assertEquals("10", tree.right(tree.root()).getElement());
		
		tree = Assignment.prefix2tree("* 5 10");
		assertEquals(3, tree.size());
		assertEquals("*", tree.root().getElement());
		assertEquals("5", tree.left(tree.root()).getElement());
		assertEquals("10", tree.right(tree.root()).getElement());
				
		tree = Assignment.prefix2tree("+ 5 - 4 3");
		assertEquals(5, tree.size());
		assertEquals("+", tree.root().getElement());
		assertEquals("5", tree.left(tree.root()).getElement());
		assertEquals("-", tree.right(tree.root()).getElement());
		assertEquals("4", tree.left(tree.right(tree.root())).getElement());
		assertEquals("3", tree.right(tree.right(tree.root())).getElement());
		
		thrown.expect(IllegalArgumentException.class);
		tree = Assignment.prefix2tree("+ 5 - 4");
	}
	
	// example of using the Assignment.equals method to check that "- x + 1 2" simplifies to "- x 3"
	@Test(timeout = 100)
	public void testSimplify1() {
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("- x + 1 2");
		tree = Assignment.simplify(tree);
		LinkedBinaryTree<String> expected = Assignment.prefix2tree("- x 3");
		assertTrue(Assignment.equals(tree, expected));
	}

	/*********************************Tests**********************************************/
	// Some testing of tree2prefix
	@Test(timeout = 100)
	public void testTree2prefix() {
		LinkedBinaryTree<String> tree;
		
		tree = Assignment.prefix2tree("1");
		assertEquals("1", Assignment.tree2prefix(tree));
		
		tree = Assignment.prefix2tree("x");
		assertEquals("x", Assignment.tree2prefix(tree));
		
		tree = Assignment.prefix2tree("+ 1 2");
		assertEquals("+ 1 2", Assignment.tree2prefix(tree));
		
		tree = Assignment.prefix2tree("+ 1 - 2 3");
		assertEquals("+ 1 - 2 3", Assignment.tree2prefix(tree));
		
		tree = Assignment.prefix2tree("* - 1 + b 3 d");
		assertEquals("* - 1 + b 3 d", Assignment.tree2prefix(tree));
	}
	
	// Some testing of tree2prefix
		@Test(timeout = 100)
		public void testTree2prefixComplicated() {
			LinkedBinaryTree<String> tree;
			
			tree = Assignment.prefix2tree("100");
			assertEquals("100", Assignment.tree2prefix(tree));
			
			thrown.expect(IllegalArgumentException.class);
			tree = Assignment.prefix2tree("+ x");
			
			thrown.expect(IllegalArgumentException.class);
			tree = Assignment.prefix2tree("+ 1 2 * x");
			
			thrown.expect(IllegalArgumentException.class);
			tree = Assignment.prefix2tree("+ 1 2 * x 1 - 2");
			
			thrown.expect(IllegalArgumentException.class);
			tree = Assignment.prefix2tree("* 1  ");
			
		}
	
	// Some testing of isArithmeticExpression
	@Test(timeout = 100)
		public void testIsArithmeticExpression() {
		LinkedBinaryTree<String> tree;
			
		tree = Assignment.prefix2tree("1");
		assertTrue(Assignment.isArithmeticExpression(tree));
			
		tree = Assignment.prefix2tree("x");
		assertTrue(Assignment.isArithmeticExpression(tree));
			
		tree = Assignment.prefix2tree("+ 1 2");
		assertTrue(Assignment.isArithmeticExpression(tree));
			
		tree = Assignment.prefix2tree("+ 1 - 2 3");
		assertTrue(Assignment.isArithmeticExpression(tree));
			
		tree = Assignment.prefix2tree("* - 1 + b 3 d");
		assertTrue(Assignment.isArithmeticExpression(tree));
	}
		
	// Some testing of tree2infix
	@Test(timeout = 100)
	public void testTree2infix() {
		LinkedBinaryTree<String> tree;
			
		tree = Assignment.prefix2tree("1");
		assertEquals("1", Assignment.tree2infix(tree));
			
		tree = Assignment.prefix2tree("x");
		assertEquals("x", Assignment.tree2infix(tree));
		
		tree = Assignment.prefix2tree("+ 1 2");
		assertEquals("(1+2)", Assignment.tree2infix(tree));
			
		tree = Assignment.prefix2tree("+ 1 - 2 3");
		assertEquals("(1+(2-3))", Assignment.tree2infix(tree));
			
		tree = Assignment.prefix2tree("* - 1 + b 3 d");
		assertEquals("((1-(b+3))*d)", Assignment.tree2infix(tree));
	}
	
	// Some testing of tree2infix
		@Test(timeout = 100)
		public void testTree2infixComplicated() {
			LinkedBinaryTree<String> tree;
				
			tree = Assignment.prefix2tree("1");
			assertEquals("1", Assignment.tree2infix(tree));
				
			tree = Assignment.prefix2tree("* x 3");
			assertEquals("(x*3)", Assignment.tree2infix(tree));
			
			tree = Assignment.prefix2tree("+ * x 3 - b 3");
			assertEquals("((x*3)+(b-3))", Assignment.tree2infix(tree));
				
			tree = Assignment.prefix2tree("+ * x a - b c");
			assertEquals("((x*a)+(b-c))", Assignment.tree2infix(tree));
		}
		
	//Some testing of Simplify
	@Test(timeout = 100)
	public void testSimplifyExample() {
		LinkedBinaryTree<String> tree;
			
		tree = Assignment.prefix2tree("- + 2 15 4");
		assertEquals("13", Assignment.tree2prefix(Assignment.simplify(tree)));
			
		tree = Assignment.prefix2tree("- + 2 15 c");
		assertEquals("- 17 c", Assignment.tree2prefix(Assignment.simplify(tree)));
			
		tree = Assignment.prefix2tree("- - 2 2 c");
		assertEquals("- 0 c", Assignment.tree2prefix(Assignment.simplify(tree)));
	}
	
	//Some testing of Simplify
		@Test(timeout = 100)
		public void testSimplifyComplicated() {
			LinkedBinaryTree<String> tree;
				
			tree = Assignment.prefix2tree("- + 2 15 - 4 3");
			assertEquals("16", Assignment.tree2prefix(Assignment.simplify(tree)));
				
			tree = Assignment.prefix2tree("- + 2 15 - c 0");
			assertEquals("- 17 c", Assignment.tree2prefix(Assignment.simplify(tree)));
		}
	
	//Some testing of SimplifyFancy
	@Test(timeout = 100)
	public void testSimplifyFancyRules() {
		LinkedBinaryTree<String> tree;
		
		tree = Assignment.prefix2tree("* x 1");
		assertEquals("x", Assignment.tree2prefix(Assignment.simplifyFancy(tree)));
		
		tree = Assignment.prefix2tree("* x 0");
		assertEquals("0", Assignment.tree2prefix(Assignment.simplifyFancy(tree)));
		
		tree = Assignment.prefix2tree("+ x 0");
		assertEquals("x", Assignment.tree2prefix(Assignment.simplifyFancy(tree)));
		
		tree = Assignment.prefix2tree("- x 0");
		assertEquals("x", Assignment.tree2prefix(Assignment.simplifyFancy(tree)));
		
		tree = Assignment.prefix2tree("- x x");
		assertEquals("0", Assignment.tree2prefix(Assignment.simplifyFancy(tree)));
		
		tree = Assignment.prefix2tree("* 1 x");
		assertEquals("x", Assignment.tree2prefix(Assignment.simplifyFancy(tree)));
		
		tree = Assignment.prefix2tree("* 0 x");
		assertEquals("0", Assignment.tree2prefix(Assignment.simplifyFancy(tree)));
		
		tree = Assignment.prefix2tree("+ 0 x");
		assertEquals("x", Assignment.tree2prefix(Assignment.simplifyFancy(tree)));
		
		tree = Assignment.prefix2tree("- 0 x");
		assertEquals("- 0 x", Assignment.tree2prefix(Assignment.simplifyFancy(tree)));
	}
	
	//Some testing of SimplifyFancyExample
	@Test(timeout = 100)
	public void testSimplifyFancyExample() {
		LinkedBinaryTree<String> tree;
		
		tree = Assignment.prefix2tree("* 1 a");
		assertEquals("a", Assignment.tree2prefix(Assignment.simplifyFancy(tree)));
		
		tree = Assignment.prefix2tree("+ - 2 2 c");
		assertEquals("c", Assignment.tree2prefix(Assignment.simplifyFancy(tree)));
		
		tree = Assignment.prefix2tree("+ - + 1 1 2 c");
		assertEquals("c", Assignment.tree2prefix(Assignment.simplifyFancy(tree)));
		
		tree = Assignment.prefix2tree("- * 1 c + c 0");
		assertEquals("0", Assignment.tree2prefix(Assignment.simplifyFancy(tree)));
		
		tree = Assignment.prefix2tree("- * 1 c + c 1");
		assertEquals("- c + c 1", Assignment.tree2prefix(Assignment.simplifyFancy(tree)));
	}
		
	//Some testing of Substitute
	@Test(timeout = 100)
	public void testSubstitute() {
		LinkedBinaryTree<String> tree;
					
		tree = Assignment.prefix2tree("- 1 c");
		assertEquals("- 1 5", Assignment.tree2prefix(Assignment.substitute(tree, "c", 5)));
					
		tree = Assignment.prefix2tree("- 1 b");
		assertEquals("- 1 b", Assignment.tree2prefix(Assignment.substitute(tree, "c", 5)));
					
		tree = Assignment.prefix2tree("+ c - c c");
		assertEquals("+ 5 - 5 5", Assignment.tree2prefix(Assignment.substitute(tree, "c", 5)));
		}
	
	//Some testing of Substitute
		@Test(timeout = 100)
		public void testSubstituteComplicated () {
			LinkedBinaryTree<String> tree;
						
			tree = Assignment.prefix2tree("* - 1 c + c a");
			assertEquals("* - 1 5 + 5 a", Assignment.tree2prefix(Assignment.substitute(tree, "c", 5)));
			
			tree = Assignment.prefix2tree("* - 1 c + c a");
			assertEquals("* - 1 c + c 7", Assignment.tree2prefix(Assignment.substitute(tree, "a", 7)));			
			
			}
		
	//Some testing of Substitute with HashMap
	@Test(timeout = 100)
	public void testSubstituteHashMap() {
		LinkedBinaryTree<String> tree;
		
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		hm.put("a", 1);
		hm.put("b", 5);
		hm.put("c", 3);
							
		tree = Assignment.prefix2tree("+ c - a b");
		assertEquals("+ 3 - 1 5", Assignment.tree2prefix(Assignment.substitute(tree, hm)));
			
		tree = Assignment.prefix2tree("+ + a b - c b");
		assertEquals("+ + 1 5 - 3 5", Assignment.tree2prefix(Assignment.substitute(tree, hm)));
		
		tree = Assignment.prefix2tree("* + c c - a b");
		assertEquals("* + 3 3 - 1 5", Assignment.tree2prefix(Assignment.substitute(tree, hm)));
	}
	
	//Some testing of Substitute with HashMap
		@Test(timeout = 100)
		public void testSubstituteHashMapComplicated() {
			LinkedBinaryTree<String> tree;
			
			HashMap<String, Integer> hm2 = new HashMap<String, Integer>();
			hm2.put("a", 1);
			hm2.put("b", 5);
			hm2.put("c", 3);
			hm2.put("d", 4);
								
			tree = Assignment.prefix2tree("* + c d - a b");
			assertEquals("* + 3 4 - 1 5", Assignment.tree2prefix(Assignment.substitute(tree, hm2)));
				
			tree = Assignment.prefix2tree("+ + a b - c b");
			assertEquals("+ + 1 5 - 3 5", Assignment.tree2prefix(Assignment.substitute(tree, hm2)));
			
			tree = Assignment.prefix2tree("* + c c - a b");
			assertEquals("* + 3 3 - 1 5", Assignment.tree2prefix(Assignment.substitute(tree, hm2)));
		}
	
}