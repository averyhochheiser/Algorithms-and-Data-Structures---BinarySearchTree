package csu22011_a3;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

//-------------------------------------------------------------------------
/**
 *  Test class for Doubly Linked List
 *
 *  @version 29/10/24 11:20:38
 *
 *  @author  Avery Hochheiser
 */

@RunWith(JUnit4.class)
public class BSTTest
{
  
  //TODO write more tests here.

  
  /** <p>Test {@link BST#prettyPrintKeys()}.</p> */
      
 @Test
 public void testPrettyPrint() {
     BST<Integer, Integer> bst = new BST<Integer, Integer>();
     assertEquals("Checking pretty printing of empty tree",
             "-null\n", bst.prettyPrintKeys());
      
                          //  -7
                          //   |-3
                          //   | |-1
                          //   | | |-null
     bst.put(7, 7);       //   | |  -2
     bst.put(8, 8);       //   | |   |-null
     bst.put(3, 3);       //   | |    -null
     bst.put(1, 1);       //   |  -6
     bst.put(2, 2);       //   |   |-4
     bst.put(6, 6);       //   |   | |-null
     bst.put(4, 4);       //   |   |  -5
     bst.put(5, 5);       //   |   |   |-null
                          //   |   |    -null
                          //   |    -null
                          //    -8
                          //     |-null
                          //      -null
     
     String result = 
      "-7\n" +
      " |-3\n" + 
      " | |-1\n" +
      " | | |-null\n" + 
      " | |  -2\n" +
      " | |   |-null\n" +
      " | |    -null\n" +
      " |  -6\n" +
      " |   |-4\n" +
      " |   | |-null\n" +
      " |   |  -5\n" +
      " |   |   |-null\n" +
      " |   |    -null\n" +
      " |    -null\n" +
      "  -8\n" +
      "   |-null\n" +
      "    -null\n";
     assertEquals("Checking pretty printing of non-empty tree", result, bst.prettyPrintKeys());
     }

  
     /** <p>Test {@link BST#delete(Comparable)}.</p> */
     @Test
     public void testDelete() {
         BST<Integer, Integer> bst = new BST<Integer, Integer>();
         bst.delete(1);
         assertEquals("Deleting from empty tree", "()", bst.printKeysPreOrder());
         
         bst.put(7, 7);   //        _7_
         bst.put(8, 8);   //      /     \
         bst.put(3, 3);   //    _3_      8
         bst.put(0, 0);   //  /     \
         bst.put(2, 2);   // 0       6
         bst.put(6, 6);   //  \     /
         bst.put(4, 4);   //   2   4
         bst.put(5, 5);   //  /     \
         bst.put(1, 1);   // 1       5
         
         assertEquals("Checking order of constructed tree",
                 "(7(3(0()(2(1()())()))(6(4()(5()()))()))(8()()))", bst.printKeysPreOrder());
         
         bst.delete(9);
         assertEquals("Deleting non-existent key",
                 "(7(3(0()(2(1()())()))(6(4()(5()()))()))(8()()))", bst.printKeysPreOrder());
 
         bst.delete(8);
         assertEquals("Deleting leaf", "(7(3(0()(2(1()())()))(6(4()(5()()))()))())", bst.printKeysPreOrder());
 
         bst.delete(6);
         assertEquals("Deleting node with single child",
                 "(7(3(0()(2(1()())()))(4()(5()())))())", bst.printKeysPreOrder());
 
         bst.delete(3);
         assertEquals("Deleting node with two children",
                 "(7(2(0()(1()()))(4()(5()())))())", bst.printKeysPreOrder());
     }


     @Test
     public void testisBSTPreOrder() {
       BST<Integer, Integer> bst = new BST<Integer, Integer>();

       Integer[] keys = new Integer[0];
       assertTrue("Empty array should be considered a valid pre-order traversal", bst.isBSTPreOrder(keys));

       keys = new Integer[] { 1 };
       assertTrue("Array with one element should be considered a valid pre-order traversal", bst.isBSTPreOrder(keys));

       keys = new Integer[] { 10, 5, 1, 7, 40, 60 };
       assertTrue("This array represents a valid pre-order traversal of a BST", bst.isBSTPreOrder(keys));

       keys = new Integer[] { 10, 50, 1, 7, 40, 60 };
       assertFalse("This array does not represent a valid pre-order traversal of a BST", bst.isBSTPreOrder(keys));
    }

    @ Test 
    public void testPrintKeysPreOrder() {
        BST<Integer, Integer> bst0 = new BST<Integer, Integer>();
        
        bst0.put(7, 7);   //        _7_
        bst0.put(8, 8);   //      /     \
        bst0.put(3, 3);   //    _3_      8
        bst0.put(0, 0);   //  /     \
        bst0.put(2, 2);   // 0       6
        bst0.put(6, 6);   //  \     /
        bst0.put(4, 4);   //   2   4
        bst0.put(5, 5);   //  /     \
        bst0.put(1, 1);   // 1       5    

        assertEquals("(7(3(0()(2(1()())()))(6(4()(5()()))()))(8()()))", bst0.printKeysPreOrder());

        BST<Integer, Integer> bst1 = new BST<Integer, Integer>();
        bst1.put(7, 7);   //        _7_
        assertEquals("(7()())", bst1.printKeysPreOrder());

        BST<Integer, Integer> bst2 = new BST<Integer, Integer>();
        assertEquals("()", bst2.printKeysPreOrder());

    }

    @ Test
    public void testHeight() {
        BST<Integer, Integer> bst = new BST<Integer, Integer>();
        
        bst.put(7, 7);   //        _7_
        bst.put(8, 8);   //      /     \
        bst.put(3, 3);   //    _3_      8
        bst.put(0, 0);   //  /     \
        bst.put(2, 2);   // 0       6
        bst.put(6, 6);   //  \     /
        bst.put(4, 4);   //   2   4
        bst.put(5, 5);   //  /     \
        bst.put(1, 1);   // 1       5

        assertEquals(4, bst.height());

        BST<Integer, Integer> bst1 = new BST<Integer, Integer>();
        bst1.put(7, 7);   //        _7_
        assertEquals(0, bst1.height());

        BST<Integer, Integer> bst2 = new BST<Integer, Integer>();
        assertEquals(-1, bst2.height());
    }
     

    @ Test 
    public void testMedian() {
        BST<Integer, Integer> bst0 = new BST<Integer, Integer>();
        
        bst0.put(7, 7);   //        _7_
        bst0.put(8, 8);   //      /     \
        bst0.put(3, 3);   //    _3_      8
        bst0.put(0, 0);   //  /     
                                 // 0   

        assertEquals("3", bst0.median().toString());
        
        BST<Integer, Integer> bst1 = new BST<Integer, Integer>();
        assertEquals(null, bst1.median());
    }

    @ Test
    public void testEmpty() {
        BST<Integer, Integer> bst0 = new BST<Integer, Integer>();
        
        bst0.put(7, 7);   //        _7_
        bst0.put(8, 8);   //      /     \
        bst0.put(3, 3);   //    _3_      8
        bst0.put(0, 0);   //  /     
                                 // 0   
        assertFalse(bst0.isEmpty());

        BST<Integer, Integer> bst1 = new BST<Integer, Integer>();
        assertTrue(bst1.isEmpty());
    }

    @ Test
    public void testPut() {
        BST<Integer, Integer> bst0 = new BST<Integer, Integer>();
        
        bst0.put(7, 7);   //        _7_

        assertEquals("(7()())", bst0.printKeysPreOrder());
    }
}

