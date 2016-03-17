///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Scheduler.java
// File:             IntervalBSTnode.java
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
 * The IntervalBSTnode<K> class represents a node in the tree.
 *
 * @author Han Jiang, You Wu
 */

class IntervalBSTnode<K> {
	//data field
	private K keyValue;
	private IntervalBSTnode<K> left, right;
	private long maxEnd;
	private long start, end;

	public IntervalBSTnode(K keyValue) {//constructor
		this.keyValue = keyValue;
		this.left = null;
		this.right = null;
	}

	public IntervalBSTnode(K keyValue, IntervalBSTnode<K> leftChild, IntervalBSTnode<K> rightChild, long maxEnd) {//constructor
		this.keyValue = keyValue;
		this.left = leftChild;
		this.right = rightChild;
		this.maxEnd = maxEnd;
	}

	/**
	 * Returns the key value.
	 *
	 * @return this.keyValue
	 */
	public K getKey() {
		return this.keyValue;
	}

	/**
	 * Returns the left child.
	 *
	 * @return this.left
	 */
	public IntervalBSTnode<K> getLeft() {
		return this.left;
	}

	/**
	 * Returns the right child.
	 *
	 * @return this.right
	 */
	public IntervalBSTnode<K> getRight() {
		return this.right;
	}

	/**
	 * Returns the maximum end value of the intervals in this node's subtree.
	 *
	 * @return this.maxEnd
	 */
	public long getMaxEnd(){
		return this.maxEnd;
	}

	/**
	 * Changes the key value for this node to the one given.
	 *
	 * @param K newK
	 */
	public void setKey(K newK) { 
		this.keyValue = newK;
	}

	/**
	 * Changes the left child for this node to the one given.
	 *
	 * @param IntervalBSTnode<K> newL
	 */
	public void setLeft(IntervalBSTnode<K> newL) { 
		this.left = newL;

	}

	/**
	 * Changes the right child for this node to the one given.
	 *
	 * @param IntervalBSTnode<K> newR
	 */
	public void setRight(IntervalBSTnode<K> newR) { 
		this.right = newR;
	}

	/**
	 * Changes the maxEnd to the updated maximum end in the subtree.
	 *
	 * @param IntervalBSTnode<K> newEnd
	 */
	public void setMaxEnd(long newEnd) { 
		this.maxEnd = newEnd;
	}

	/**
	 * Returns the start timestamp of this interval.
	 *
	 * @return this.start
	 */
	public long getStart(){ 
		return this.start;
	}

	/**
	 * Returns the end timestamp of this interval.
	 *
	 * @return this.end
	 */
	public long getEnd(){
		return this.end;
	}

	/**
	 * Returns the key value of this BST node.
	 *
	 * @return this.keyValue
	 */
	public K getData(){
		return this.keyValue;
	}

}
