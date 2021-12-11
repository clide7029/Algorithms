package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Pair;

/**
 * A priority queue class implemented using a min heap.
 * Priorities cannot be negative.
 * 
 * @author Your Tristan Gaeta
 * @version Date 09-23-2021
 *
 */
public class PriorityQueue {

	protected Map<Integer, Integer> location;
	protected List<Pair<Integer, Integer>> heap;

	/**
	 * Constructs an empty priority queue
	 */
	public PriorityQueue() {
		this.location = new HashMap<>();
		this.heap = new ArrayList<>();
	}

	/**
	 * Insert a new element into the queue with the
	 * given priority.
	 *
	 * @param priority priority of element to be inserted
	 * @param element  element to be inserted
	 *                 <br>
	 *                 <br>
	 *                 <b>Preconditions:</b>
	 *                 <ul>
	 *                 <li>The element does not already appear in the priority
	 *                 queue.</li>
	 *                 <li>The priority is non-negative.</li>
	 *                 </ul>
	 * 
	 */
	public void push(int priority, int element) {
		if (location.get(element) != null) {
			throw new AssertionError("Element already in queue.");
		}
		if (priority < 0) {
			throw new AssertionError("Priorities must be positive.");
		}

		this.location.put(element, this.size());
		this.heap.add(new Pair<>(priority, element));
		this.percolateUpLeaf();
	}

	/**
	 * Remove the highest priority element
	 * <br>
	 * <br>
	 * <b>Preconditions:</b>
	 * <ul>
	 * <li>The priority queue is non-empty.</li>
	 * </ul>
	 * 
	 */
	public int pop() {
		if (this.isEmpty()) {
			throw new AssertionError("Cannot remove from empty list.");
		}

		this.swap(0, this.size() - 1);
		Pair<Integer, Integer> out = this.heap.remove(this.size() - 1);
		this.location.remove(out.element);
		this.pushDownRoot();
		return out.element;
	}

	/**
	 * Returns the highest priority in the queue
	 * 
	 * @return highest priority value
	 *         <br>
	 *         <br>
	 *         <b>Preconditions:</b>
	 *         <ul>
	 *         <li>The priority queue is non-empty.</li>
	 *         </ul>
	 */
	public int topPriority() {
		if (this.isEmpty()) {
			throw new AssertionError("Cannot peek into empty list.");
		}
		return this.heap.get(0).priority;
	}

	/**
	 * Returns the element with the highest priority
	 * 
	 * @return element with highest priority
	 *         <br>
	 *         <br>
	 *         <b>Preconditions:</b>
	 *         <ul>
	 *         <li>The priority queue is non-empty.</li>
	 *         </ul>
	 */
	public int topElement() {
		if (this.isEmpty()) {
			throw new AssertionError("Cannot peek into empty list.");
		}
		return this.heap.get(0).element;
	}

	/**
	 * Change the priority of an element already in the
	 * priority queue.
	 * 
	 * @param newpriority the new priority
	 * @param element     element whose priority is to be changed
	 *                    <br>
	 *                    <br>
	 *                    <b>Preconditions:</b>
	 *                    <ul>
	 *                    <li>The element exists in the priority queue</li>
	 *                    <li>The new priority is non-negative</li>
	 *                    </ul>
	 */
	public void changePriority(int newpriority, int element) {
		if (location.get(element) == null) {
			throw new AssertionError("Element not in queue.");
		}
		if (newpriority < 0) {
			throw new AssertionError("Priorities must be positive.");
		}

		int elementIndex = this.location.get(element);
		int prevPriority = this.heap.get(elementIndex).priority;
		this.heap.get(elementIndex).priority = newpriority;
		if (newpriority > prevPriority) {
			this.pushDown(elementIndex);
		} else {
			this.percolateUp(elementIndex);
		}
	}

	/**
	 * Gets the priority of the element
	 * 
	 * @param element the element whose priority is returned
	 * @return the priority value
	 *         <br>
	 *         <br>
	 *         <b>Preconditions:</b>
	 *         <ul>
	 *         <li>The element exists in the priority queue</li>
	 *         </ul>
	 */
	public int getPriority(int element) {
		if (this.location.get(element) == null) {
			throw new AssertionError("Element does not exist in queue.");
		}
		return this.heap.get(this.location.get(element)).priority;
	}

	/**
	 * Returns true if the priority queue contains no elements
	 * 
	 * @return true if the queue contains no elements, false otherwise
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}

	/**
	 * Returns true if the element exists in the priority queue.
	 * 
	 * @return true if the element exists, false otherwise
	 */
	public boolean isPresent(int element) {
		return this.location.get(element) != null;
	}

	/**
	 * Removes all elements from the priority queue
	 */
	public void clear() {
		this.heap.clear();
		this.location.clear();
	}

	/**
	 * Returns the number of elements in the priority queue
	 * 
	 * @return number of elements in the priority queue
	 */
	public int size() {
		return this.heap.size();
	}

	/*********************************************************
	 * Private helper methods
	 *********************************************************/

	/**
	 * Push down the element at the given position in the heap
	 * 
	 * @param start_index the index of the element to be pushed down
	 * @return the index in the list where the element is finally stored
	 */
	private int pushDown(int start_index) {
		if (this.isLeaf(start_index))
			return start_index;

		int leftChildIndex = this.left(start_index);
		int minChildIndex = leftChildIndex;
		if (this.hasTwoChildren(start_index)) {
			int rightChildIndex = this.right(start_index);
			if (this.heap.get(leftChildIndex).priority > this.heap.get(rightChildIndex).priority) {
				minChildIndex = rightChildIndex;
			}
		}

		if (this.heap.get(start_index).priority > this.heap.get(minChildIndex).priority) {
			this.swap(start_index, minChildIndex);
			this.pushDown(minChildIndex);
		}
		return start_index;
	}

	/**
	 * Percolate up the element at the given position in the heap
	 * 
	 * @param start_index the index of the element to be percolated up
	 * @return the index in the list where the element is finally stored
	 */
	private int percolateUp(int start_index) {
		if (start_index == 0)
			return start_index;

		int parentIndex = this.parent(start_index);

		if (this.heap.get(start_index).priority < this.heap.get(parentIndex).priority) {
			this.swap(start_index, this.parent(start_index));
			return this.percolateUp(parentIndex);
		}
		return start_index;
	}

	/**
	 * Swaps two elements in the priority queue by updating BOTH
	 * the list representing the heap AND the map
	 * 
	 * @param i The index of the element to be swapped
	 * @param j The index of the element to be swapped
	 */
	private void swap(int i, int j) {
		location.put(heap.get(i).element, j);
		location.put(heap.get(j).element, i);

		Pair<Integer, Integer> temp = heap.get(i);
		heap.set(i, heap.get(j));
		heap.set(j, temp);
	}

	/**
	 * Computes the index of the element's left child
	 * 
	 * @param parent index of element in list
	 * @return index of element's left child in list
	 */
	private int left(int parent) {
		return 2 * parent + 1;
	}

	/**
	 * Computes the index of the element's right child
	 * 
	 * @param parent index of element in list
	 * @return index of element's right child in list
	 */
	private int right(int parent) {
		return 2 * parent + 2;
	}

	/**
	 * Computes the index of the element's parent
	 * 
	 * @param child index of element in list
	 * @return index of element's parent in list
	 */

	private int parent(int child) {
		return (child - 1) / 2;
	}

	/*********************************************************
	 * These are optional private methods that may be useful
	 *********************************************************/

	/**
	 * Push down the root element
	 * 
	 * @return the index in the list where the element is finally stored
	 */
	private int pushDownRoot() {
		return this.pushDown(0);
	}

	/**
	 * Percolate up the last leaf in the heap, i.e. the most recently
	 * added element which is stored in the last slot in the list
	 * 
	 * @return the index in the list where the element is finally stored
	 */
	private int percolateUpLeaf() {
		return this.percolateUp(this.size() - 1);
	}

	/**
	 * Returns true if element is a leaf in the heap
	 * 
	 * @param i index of element in heap
	 * @return true if element is a leaf
	 */
	private boolean isLeaf(int i) {
		return this.left(i) >= this.size();
	}

	/**
	 * Returns true if element has two children in the heap
	 * 
	 * @param i index of element in the heap
	 * @return true if element in heap has two children
	 */
	private boolean hasTwoChildren(int i) {
		return !this.isLeaf(i) && this.right(i) < this.size();
	}

	/**
	 * Print the underlying list representation
	 */
	public void printHeap() {
		System.out.println(this.heap);
	}

	/**
	 * Print the entries in the location map
	 */
	public void printMap() {
		System.out.println(this.location);
	}
}