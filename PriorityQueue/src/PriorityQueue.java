import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A priority queue class implemented using a min heap.
 * Priorities cannot be negative.
 * 
 * @author Skyler Benjamin
 * @version 9/26/21
 *
 */
public class PriorityQueue {

	protected Map<Integer, Integer> location;
	protected List<Pair<Integer, Integer>> heap;

	/**
	 *  Constructs an empty priority queue
	 */
	public PriorityQueue() {
		location = new HashMap<Integer,Integer>();
		heap = new ArrayList<Pair<Integer,Integer>>();
	}
	/**
	 *  Insert a new element into the queue with the
	 *  given priority.
	 *
	 *	@param priority priority of element to be inserted
	 *	@param element element to be inserted
	 *	<br><br>
	 *	<b>Preconditions:</b>
	 *	<ul>
	 *	<li> The element does not already appear in the priority queue.</li>
	 *	<li> The priority is non-negative.</li>
	 *	</ul>
	 *  
	 */
	public void push(int priority, int element) {
		if(location.containsKey(element)){
			new AssertionError("Element already exists in Priority Queue");
		}else if(priority < 0){
			new AssertionError("Priority must be non-negative");
		}

		//construct pair
		Pair<Integer,Integer> pair = new Pair<Integer,Integer>(priority,element);
		//put pair in heap
		heap.add(pair);
		printHeap();
		//exchange with parents till it fits
		int idx = percolateUp(size()-1);

		//put element in the location map
		location.put(element,idx);
	}

	/**
	 *  Remove the highest priority element
	 *  <br><br>
	 *	<b>Preconditions:</b>
	 *	<ul>
	 *	<li> The priority queue is non-empty.</li>
	 *	</ul>
	 *  
	 */
	public void pop(){
		// check heap is non-empty
		if(heap.isEmpty()){
			new AssertionError("Priority Queue is Empty.");
		}

		int last_idx = size()-1;
		// swap root and last element
		swap(0,last_idx);
		// remove "old root"
		location.remove(heap.get(last_idx).element);
		heap.remove(last_idx);
		// percolate new root down by checking children
		pushDown(0);
	}


	/**
	 *  Returns the highest priority in the queue
	 *  @return highest priority value
	 *  <br><br>
	 *	<b>Preconditions:</b>
	 *	<ul>
	 *	<li> The priority queue is non-empty.</li>
	 *	</ul>
	 */
	public int topPriority() {	
		// check heap is non-empty
		if (heap.isEmpty()) {
			new AssertionError("Priority Queue is Empty.");
		}	
		return heap.get(0).priority;
	}


	/**
	 *  Returns the element with the highest priority
	 *  @return element with highest priority
	 *  <br><br>
	 *	<b>Preconditions:</b>
	 *	<ul>
	 *	<li> The priority queue is non-empty.</li>
	 *	</ul>
	 */
	public int topElement() {
		// check heap is non-empty
		if (heap.isEmpty()) {
			new AssertionError("Priority Queue is Empty.");
		}
		return heap.get(0).element;
	}


	/**
	 *  Change the priority of an element already in the
	 *  priority queue.
	 *  
	 *  @param newpriority the new priority	  
	 *  @param element element whose priority is to be changed
	 *  <br><br>
	 *	<b>Preconditions:</b>
	 *	<ul>
	 *	<li> The element exists in the priority queue</li>
	 *	<li> The new priority is non-negative </li>
	 *	</ul>
	 */
	public void changePriority(int newpriority, int element) {
		if(!location.containsKey(element)){
			new AssertionError("Given element cannot be found in the Priority Queue.");
		}else if(newpriority < 0){
			new AssertionError("Priority must be non-negative");
		}

		location.replace(element,newpriority);
	}


	/**
	 *  Gets the priority of the element
	 *  
	 *  @param element the element whose priority is returned
	 *  @return the priority value
	 *  <br><br>
	 *	<b>Preconditions:</b>
	 *	<ul>
	 *	<li> The element exists in the priority queue</li>
	 *	</ul>
	 */
	public int getPriority(int element) {
		if(!location.containsKey(element)){
			new AssertionError("Given element cannot be found in the Priority Queue.");
		}

		int idx = location.get(element);
		return heap.get(idx).priority;
	}

	/**
	 *  Returns true if the priority queue contains no elements
	 *  @return true if the queue contains no elements, false otherwise
	 */
	public boolean isEmpty() {
		return heap.isEmpty();
	}

	/**
	 *  Returns true if the element exists in the priority queue.
	 *  @return true if the element exists, false otherwise
	 */
	public boolean isPresent(int element) {
		if(location.containsKey(element)){
			return true;
		}

		return false;
	}

	/**
	 *  Removes all elements from the priority queue
	 */
	public void clear() {
		heap.removeAll(heap);
		location.clear();
	}

	/**
	 *  Returns the number of elements in the priority queue
	 *  @return number of elements in the priority queue
	 */
	public int size() {
		return heap.size();
	}


	
	/*********************************************************
	 * 				Private helper methods
	 *********************************************************/
	

	/**
	 * Push down the element at the given position in the heap 
	 * @param start_index the index of the element to be pushed down
	 * @return the index in the list where the element is finally stored
	 */
	private int pushDown(int start_index) {	
		int idx = start_index;
		while (left(idx) < heap.size()) {
			// check if left child has higher priority
			if(heap.get(idx).priority > heap.get(left(idx)).priority){
				swap(idx, left(idx));
				idx = left(idx);
			// check if right child has higher priority
			}else if(heap.get(idx).priority > heap.get(right(idx)).priority) {
				swap(idx, right(idx));
				idx = right(idx);
			}
		}
		return idx;
	}

	/**
	 * Percolate up the element at the given position in the heap 
	 * @param start_index the index of the element to be percolated up
	 * @return the index in the list where the element is finally stored
	 */
	private int percolateUp(int start_index) {
		int idx = start_index;
		// check if parent has lower priority
		while(heap.get(idx).priority < heap.get(parent(idx)).priority) {
			swap(idx, parent(idx));
			idx = parent(idx);
		}
		return idx;
	}


	/**
	 * Swaps two elements in the priority queue by updating BOTH
	 * the list representing the heap AND the map
	 * @param i The index of the element to be swapped
	 * @param j The index of the element to be swapped
	 */
	private void swap(int i, int j) {
		int el1 = heap.get(i).element;
		int pr1 = heap.get(i).priority;
		int el2 = heap.get(j).element;

		//remove elements from the location map
		location.remove(el1);
		location.remove(el2);

		//swap list elements
		Pair<Integer,Integer> temp = new Pair<Integer,Integer>(pr1, el1);
		heap.set(i,heap.get(j));
		heap.set(j,temp);

		// put new elements back into location map
		location.put(el1, j);
		location.put(el2, i);
	}

	/**
	 * Computes the index of the element's left child
	 * @param parent index of element in list
	 * @return index of element's left child in list
	 */
	private int left(int parent) {
		return ((2*parent) + 1);
	}

	/**
	 * Computes the index of the element's right child
	 * @param parent index of element in list
	 * @return index of element's right child in list
	 */
	private int right(int parent) {
		return ((2*parent) + 2);
	}

	/**
	 * Computes the index of the element's parent
	 * @param child index of element in list
	 * @return index of element's parent in list
	 */

	private int parent(int child) {
		return ((child-1)/2);
	}
	

	/*********************************************************
	 * 	These are optional private methods that may be useful
	 *********************************************************/
	

	/**
	 * Returns true if element is a leaf in the heap
	 * @param i index of element in heap
	 * @return true if element is a leaf
	 */
	// private boolean isLeaf(int i){
	// 	int len = heap.size();
	// 	if(i > (len / 2) && i < len){
	// 		return true;
	// 	}else {
	// 		return false;
	// 	}
	// }

	/**
	 * Returns true if element has two children in the heap
	 * @param i index of element in the heap
	 * @return true if element in heap has two children
	 */
	// private boolean hasTwoChildren(int i) {
	// 	// TODO: Fill in
	// }
	
	/**
	 * Print the underlying list representation
	 */
	public void printHeap() {
		for(int i = 0; i<heap.size(); i++){
			
			int p = heap.get(i).priority;
			System.out.println(p);
		}
	}

	/**
	 * Print the entries in the location map
	 */
	public void printMap() {
		for(Map.Entry<Integer,Integer> element : location.entrySet()){
			System.out.println("\nElement = " + element.getKey() +
						", idx = " + element.getValue() +
						", priority = " + getPriority(element.getKey()));
		}
	}

	
}
