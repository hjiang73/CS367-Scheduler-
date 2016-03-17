///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  IntervalBST.java
// File:             SchedulerDB.java
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
import java.util.Iterator;

/**
 * An IntervalBST is a modified Binary Search Tree that has an Event object 
 * as its key and uses the start time of the Event to sort its nodes.
 * <p>Bugs: None known
 * @author Han Jiang, You Wu
 */

public class IntervalBST<K extends Interval> implements SortedListADT<K> {
	//data field
	private IntervalBSTnode<K> root;
	//size of the tree
	private int N;

	public IntervalBST() {
		//constructor
		root = null;
		N = 0;
	}

	/**
	 * Inserts the given key into the Sorted List.
	 * @param K key
	 */
	public void insert(K key){

		//if the tree is empty,create a new one
		if(root==null){
			root = new IntervalBSTnode<K>(key);
			N++;
			return;
		}
		insert(root, key);
		update(root);
		N++;
	}
	/**
	 * the helper passed a pointer to a possibly empty tree. 
	 * Its responsibility is to add the indicated key to that tree 
	 * and return a pointer to the root of the modified tree. 
	 * If the original tree is empty, the result is a one-node tree. 
	 * Otherwise, the result is a pointer to the same node that was passed as an argument.
	 * @param IntervalBSTnode<K>n
	 * @param K key
	 */
	private void insert(IntervalBSTnode<K> n, K key) {


		if (key.compareTo(n.getKey()) < 0) { 
			if (n.getLeft()==null ) {
				n.setLeft(new IntervalBSTnode<K>(key));
			}
			else insert(n.getLeft(), key);     
		}
		else {
			if (n.getRight()==null ) {
				n.setRight(new IntervalBSTnode<K>(key));
			}// key > this node's key; look in right subtree
			else insert(n.getRight(), key);      
		}

	}

	/**
	 * Deletes the given key from the Sorted List. 
	 * If the key is in the Sorted List, the key is deleted and true is returned. 
	 * If the key is not in the Sorted List, the Sorted List is unchanged and false is returned.
	 * @param K key
	 */
	public boolean delete(K key) {
		root = delete(root, key);
		if(lookup(root,key)!=null){
			return false;
		}//if the tree is same as the old one
		//return false,otherwise size-1 and return true
		else N--;
		return true;
	}

	/**
	 * The auxiliary delete method returns a value (a pointer to the updated tree). 
	 * @param IntervalBSTnode<K> n
	 * @param K key
	 */
	private IntervalBSTnode<K> delete(IntervalBSTnode<K> n, K key) {
		if (n == null) {
			return null;
		}

		if (n.getData().getStart()==key.getStart()) {
			// n is the node to be removed
			// n has no child
			if (n.getLeft() == null && n.getRight() == null) {
				update(n);
				return null;
			}
			// n has right child

			if (n.getLeft() == null) {
				update(n);
				return n.getRight();
			}
			// n has left child
			if (n.getRight() == null) {
				update(n);
				return n.getLeft();
			}

			// if we get here, then n has 2 children
			K smallVal = smallest(n.getRight());
			n.setKey(smallVal);
			n.setRight( delete(n.getRight(), smallVal) );
			update(n);
			return n; 
		}

		else if (key.compareTo(n.getKey()) < 0) {
			n.setLeft( delete(n.getLeft(), key) );
			return n;
		}

		else {
			n.setRight( delete(n.getRight(), key) );
			return n;
		}
	}

	/**
	 * The auxiliary method returns the smallest value of a node's right subtree
	 * (in predecessor) 
	 * @param IntervalBSTnode<K> n
	 * @return K key
	 */
	private K smallest(IntervalBSTnode<K> n)


	{
		if (n.getLeft() == null) {
			return n.getKey();
		} else {
			return smallest(n.getLeft());
		}
	}


	/**
	 * the helper method is to update the maxend of each node when inserting and deleting.
	 * @param IntervalBSTnode<K>n
	 */
	private void update(IntervalBSTnode<K> n){
		if (n==null) return;
		// leaf-node
		if (n.getLeft()==null&&n.getRight()==null){
			n.setMaxEnd(n.getEnd());
		}
		// only left child
		else if (n.getLeft()!=null && n.getRight()==null){
			update(n.getLeft());
			if (n.getEnd()>n.getLeft().getEnd())
				n.setMaxEnd(n.getEnd());
			else{
				n.setMaxEnd(n.getLeft().getEnd());
			}


		}
		//only right child
		else if (n.getRight()!=null && n.getLeft()==null){
			update(n.getRight());
			if (n.getEnd()>n.getRight().getEnd())
				n.setMaxEnd(n.getEnd());
			else{
				n.setMaxEnd(n.getRight().getEnd());
			}	

		}
		else{
			// both children
			update(n.getLeft());
			update(n.getRight());
			if (n.getEnd()>n.getLeft().getMaxEnd() 
					&& n.getEnd()>n.getRight().getMaxEnd())
				n.setMaxEnd(n.getEnd());
			else if (n.getEnd()<n.getLeft().getMaxEnd()
					&& n.getLeft().getMaxEnd()>n.getRight().getMaxEnd())
				n.setMaxEnd(n.getLeft().getMaxEnd());
			else if (n.getRight().getMaxEnd()>n.getEnd()
					&&n.getRight().getMaxEnd()>n.getLeft().getMaxEnd()){
				n.setMaxEnd(n.getRight().getMaxEnd());
			}
		}
	}





	/**
	 * Searches for the given key in the Sorted List and returns it. 
	 * If the key is not in the Sorted List, null is returned.
	 * @param K key
	 * @return K
	 */
	public K lookup(K key) {
		return lookup(root, key);
	}

	/**
	 * an auxiliary, recursive lookup method 
	 * @param K key
	 * @return K
	 */
	private K lookup(IntervalBSTnode<K> n, K key) {
		if (n == null) {
			return null;
		}

		if (n.getKey().equals(key)) {
			return key;
		}

		if (key.compareTo(n.getKey()) < 0) {
			// key < this node's key; look in left subtree
			return lookup(n.getLeft(), key);
		}

		else {
			// key > this node's key; look in right subtree
			return lookup(n.getRight(), key);
		}
	}

	/**
	 * Returns the number of items in the tree. 
	 * @return int N
	 */
	public int size() {
		return this.N;
	}

	/**
	 * Returns if the tree is empty 
	 * @return boolean
	 */
	public boolean isEmpty() {
		return N==0;
	}

	/**
	 * Returns an iterator over the tree 
	 * that iterates over the items in 
	 * the tree from smallest to largest.
	 * @return Iterator<K>
	 */
	public Iterator<K> iterator() {
		return new IntervalBSTIterator<K>(root);
	}

}