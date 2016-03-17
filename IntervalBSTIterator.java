///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Scheduler.java
// File:             IntervalBSTIterator.java
// Semester:         CS367 Fall 2015
//
// Author:           Han Jiang
// CS Login:         hjiang
// Lecturer's Name:  James Skretney
// Lab Section:      Lec-002
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     You Wu
// Email:            wu278@wisc.edu
// CS Login:         ywu
// Lecturer's Name:  James Skretney
// Lab Section:      Lec-001
//
////////////////////////////////////////////////////////////////////////////////

/**
 * The resource class represents a iterator of the tree which implement an
 * in-order traverse of the tree.
 *
 * @author Han Jiang, You Wu
 */
import java.util.*;

public class IntervalBSTIterator<K extends Interval> implements Iterator<K> {
	//data field
	private Stack<IntervalBSTnode<K>> stack; //for keeping track of nodes


	public IntervalBSTIterator(IntervalBSTnode<K> root){
		//constructor
		stack = new Stack<IntervalBSTnode<K>>();
		while (root != null) {
			stack.push(root);
			root = root.getLeft();
		}
	}

	/**
	 * Returns true if there are more elements to iterate over, otherwise returns false.
	 *
	 * @return true or false
	 */
	public boolean hasNext(){
		return !stack.isEmpty() ;
	}

	/**
	 * Returns the next element, while iterating over the nodes of the IntervalBST tree.
	 *
	 * @return result
	 */
	public K next(){
		IntervalBSTnode<K> node = stack.pop();
		K result = node.getData();
		if (node.getRight() != null) {
			node = node.getRight();
			while (node != null) {
				stack.push(node);
				node = node.getLeft();
			}
		}
		return result;
	}
}


