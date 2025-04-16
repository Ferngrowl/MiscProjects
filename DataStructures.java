/**
 * DataStructures.java
 * A comprehensive collection of commonly used data structures for coding assessments.
 * This class provides template implementations that can be easily adapted to specific problems.
 */

 import java.util.*;

 public class DataStructures {
 
     /**
      * ARRAY-BASED DATA STRUCTURES
      * ----------------------------
      */
 
     /**
      * Implementation of a dynamic array (similar to ArrayList).
      * Time Complexity:
      * - Access: O(1)
      * - Search: O(n)
      * - Insertion: O(n) (worst case when resizing is needed)
      * - Deletion: O(n)
      */
     public static class DynamicArray<T> {
         private Object[] array;
         private int size;
         private int capacity;
         
         public DynamicArray() {
             this.capacity = 10;
             this.size = 0;
             this.array = new Object[capacity];
         }
         
         public int size() {
             return size;
         }
         
         public boolean isEmpty() {
             return size == 0;
         }
         
         @SuppressWarnings("unchecked")
         public T get(int index) {
             if (index < 0 || index >= size) {
                 throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
             }
             return (T) array[index];
         }
         
         public void set(int index, T element) {
             if (index < 0 || index >= size) {
                 throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
             }
             array[index] = element;
         }
         
         public void add(T element) {
             if (size == capacity) {
                 resize(2 * capacity);
             }
             array[size++] = element;
         }
         
         public void remove(int index) {
             if (index < 0 || index >= size) {
                 throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
             }
             
             // Shift elements to the left
             for (int i = index; i < size - 1; i++) {
                 array[i] = array[i + 1];
             }
             
             array[--size] = null; // Clear the last element
             
             // Shrink if necessary
             if (size > 0 && size == capacity / 4) {
                 resize(capacity / 2);
             }
         }
         
         private void resize(int newCapacity) {
             Object[] newArray = new Object[newCapacity];
             for (int i = 0; i < size; i++) {
                 newArray[i] = array[i];
             }
             array = newArray;
             capacity = newCapacity;
         }
     }
 
     /**
      * LINKED LIST-BASED DATA STRUCTURES
      * --------------------------------
      */
 
     /**
      * Basic node for singly linked lists.
      */
     public static class ListNode<T> {
         T val;
         ListNode<T> next;
         
         public ListNode(T val) {
             this.val = val;
             this.next = null;
         }
     }
     
     /**
      * Basic node for doubly linked lists.
      */
     public static class DoublyListNode<T> {
         T val;
         DoublyListNode<T> prev;
         DoublyListNode<T> next;
         
         public DoublyListNode(T val) {
             this.val = val;
             this.prev = null;
             this.next = null;
         }
     }
     
     /**
      * Implementation of a singly linked list.
      * Time Complexity:
      * - Access: O(n)
      * - Search: O(n)
      * - Insertion: O(1) (at head), O(n) (at specific position)
      * - Deletion: O(1) (at head), O(n) (at specific position)
      */
     public static class SinglyLinkedList<T> {
         private ListNode<T> head;
         private int size;
         
         public SinglyLinkedList() {
             this.head = null;
             this.size = 0;
         }
         
         public int size() {
             return size;
         }
         
         public boolean isEmpty() {
             return size == 0;
         }
         
         public T get(int index) {
             if (index < 0 || index >= size) {
                 throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
             }
             
             ListNode<T> curr = head;
             for (int i = 0; i < index; i++) {
                 curr = curr.next;
             }
             return curr.val;
         }
         
         public void addFirst(T val) {
             ListNode<T> newNode = new ListNode<>(val);
             newNode.next = head;
             head = newNode;
             size++;
         }
         
         public void addLast(T val) {
             ListNode<T> newNode = new ListNode<>(val);
             if (head == null) {
                 head = newNode;
             } else {
                 ListNode<T> curr = head;
                 while (curr.next != null) {
                     curr = curr.next;
                 }
                 curr.next = newNode;
             }
             size++;
         }
         
         public void add(int index, T val) {
             if (index < 0 || index > size) {
                 throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
             }
             
             if (index == 0) {
                 addFirst(val);
             } else {
                 ListNode<T> curr = head;
                 for (int i = 0; i < index - 1; i++) {
                     curr = curr.next;
                 }
                 ListNode<T> newNode = new ListNode<>(val);
                 newNode.next = curr.next;
                 curr.next = newNode;
                 size++;
             }
         }
         
         public T removeFirst() {
             if (isEmpty()) {
                 throw new NoSuchElementException("List is empty");
             }
             
             T val = head.val;
             head = head.next;
             size--;
             return val;
         }
         
         public T removeLast() {
             if (isEmpty()) {
                 throw new NoSuchElementException("List is empty");
             }
             
             if (size == 1) {
                 T val = head.val;
                 head = null;
                 size = 0;
                 return val;
             }
             
             ListNode<T> curr = head;
             while (curr.next.next != null) {
                 curr = curr.next;
             }
             
             T val = curr.next.val;
             curr.next = null;
             size--;
             return val;
         }
         
         public T remove(int index) {
             if (index < 0 || index >= size) {
                 throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
             }
             
             if (index == 0) {
                 return removeFirst();
             }
             
             ListNode<T> curr = head;
             for (int i = 0; i < index - 1; i++) {
                 curr = curr.next;
             }
             
             T val = curr.next.val;
             curr.next = curr.next.next;
             size--;
             return val;
         }
         
         public void reverse() {
             if (head == null || head.next == null) {
                 return;
             }
             
             ListNode<T> prev = null;
             ListNode<T> curr = head;
             ListNode<T> next = null;
             
             while (curr != null) {
                 next = curr.next;
                 curr.next = prev;
                 prev = curr;
                 curr = next;
             }
             
             head = prev;
         }
     }
     
     /**
      * Implementation of a doubly linked list.
      * Time Complexity:
      * - Access: O(n)
      * - Search: O(n)
      * - Insertion: O(1) (at head/tail), O(n) (at specific position)
      * - Deletion: O(1) (at head/tail), O(n) (at specific position)
      */
     public static class DoublyLinkedList<T> {
         private DoublyListNode<T> head;
         private DoublyListNode<T> tail;
         private int size;
         
         public DoublyLinkedList() {
             this.head = null;
             this.tail = null;
             this.size = 0;
         }
         
         public int size() {
             return size;
         }
         
         public boolean isEmpty() {
             return size == 0;
         }
         
         public T get(int index) {
             if (index < 0 || index >= size) {
                 throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
             }
             
             DoublyListNode<T> curr;
             // Optimize traversal by starting from head or tail based on index
             if (index < size / 2) {
                 curr = head;
                 for (int i = 0; i < index; i++) {
                     curr = curr.next;
                 }
             } else {
                 curr = tail;
                 for (int i = size - 1; i > index; i--) {
                     curr = curr.prev;
                 }
             }
             return curr.val;
         }
         
         public void addFirst(T val) {
             DoublyListNode<T> newNode = new DoublyListNode<>(val);
             if (isEmpty()) {
                 head = newNode;
                 tail = newNode;
             } else {
                 newNode.next = head;
                 head.prev = newNode;
                 head = newNode;
             }
             size++;
         }
         
         public void addLast(T val) {
             DoublyListNode<T> newNode = new DoublyListNode<>(val);
             if (isEmpty()) {
                 head = newNode;
                 tail = newNode;
             } else {
                 newNode.prev = tail;
                 tail.next = newNode;
                 tail = newNode;
             }
             size++;
         }
         
         public void add(int index, T val) {
             if (index < 0 || index > size) {
                 throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
             }
             
             if (index == 0) {
                 addFirst(val);
             } else if (index == size) {
                 addLast(val);
             } else {
                 DoublyListNode<T> curr;
                 // Optimize traversal
                 if (index < size / 2) {
                     curr = head;
                     for (int i = 0; i < index - 1; i++) {
                         curr = curr.next;
                     }
                 } else {
                     curr = tail;
                     for (int i = size - 1; i > index; i--) {
                         curr = curr.prev;
                     }
                     curr = curr.prev;
                 }
                 
                 DoublyListNode<T> newNode = new DoublyListNode<>(val);
                 newNode.prev = curr;
                 newNode.next = curr.next;
                 curr.next.prev = newNode;
                 curr.next = newNode;
                 size++;
             }
         }
         
         public T removeFirst() {
             if (isEmpty()) {
                 throw new NoSuchElementException("List is empty");
             }
             
             T val = head.val;
             if (size == 1) {
                 head = null;
                 tail = null;
             } else {
                 head = head.next;
                 head.prev = null;
             }
             size--;
             return val;
         }
         
         public T removeLast() {
             if (isEmpty()) {
                 throw new NoSuchElementException("List is empty");
             }
             
             T val = tail.val;
             if (size == 1) {
                 head = null;
                 tail = null;
             } else {
                 tail = tail.prev;
                 tail.next = null;
             }
             size--;
             return val;
         }
         
         public T remove(int index) {
             if (index < 0 || index >= size) {
                 throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
             }
             
             if (index == 0) {
                 return removeFirst();
             } else if (index == size - 1) {
                 return removeLast();
             } else {
                 DoublyListNode<T> curr;
                 // Optimize traversal
                 if (index < size / 2) {
                     curr = head;
                     for (int i = 0; i < index; i++) {
                         curr = curr.next;
                     }
                 } else {
                     curr = tail;
                     for (int i = size - 1; i > index; i--) {
                         curr = curr.prev;
                     }
                 }
                 
                 T val = curr.val;
                 curr.prev.next = curr.next;
                 curr.next.prev = curr.prev;
                 size--;
                 return val;
             }
         }
     }
 
     /**
      * STACK AND QUEUE IMPLEMENTATIONS
      * -------------------------------
      */
 
     /**
      * Implementation of a stack using a linked list.
      * Time Complexity:
      * - Push: O(1)
      * - Pop: O(1)
      * - Peek: O(1)
      */
     public static class Stack<T> {
         private ListNode<T> top;
         private int size;
         
         public Stack() {
             this.top = null;
             this.size = 0;
         }
         
         public int size() {
             return size;
         }
         
         public boolean isEmpty() {
             return size == 0;
         }
         
         public void push(T val) {
             ListNode<T> newNode = new ListNode<>(val);
             newNode.next = top;
             top = newNode;
             size++;
         }
         
         public T pop() {
             if (isEmpty()) {
                 throw new NoSuchElementException("Stack is empty");
             }
             
             T val = top.val;
             top = top.next;
             size--;
             return val;
         }
         
         public T peek() {
             if (isEmpty()) {
                 throw new NoSuchElementException("Stack is empty");
             }
             
             return top.val;
         }
     }
     
     /**
      * Implementation of a queue using a linked list.
      * Time Complexity:
      * - Enqueue: O(1)
      * - Dequeue: O(1)
      * - Peek: O(1)
      */
     public static class Queue<T> {
         private ListNode<T> front;
         private ListNode<T> rear;
         private int size;
         
         public Queue() {
             this.front = null;
             this.rear = null;
             this.size = 0;
         }
         
         public int size() {
             return size;
         }
         
         public boolean isEmpty() {
             return size == 0;
         }
         
         public void enqueue(T val) {
             ListNode<T> newNode = new ListNode<>(val);
             
             if (isEmpty()) {
                 front = newNode;
                 rear = newNode;
             } else {
                 rear.next = newNode;
                 rear = newNode;
             }
             size++;
         }
         
         public T dequeue() {
             if (isEmpty()) {
                 throw new NoSuchElementException("Queue is empty");
             }
             
             T val = front.val;
             front = front.next;
             
             if (front == null) {
                 rear = null;
             }
             
             size--;
             return val;
         }
         
         public T peek() {
             if (isEmpty()) {
                 throw new NoSuchElementException("Queue is empty");
             }
             
             return front.val;
         }
     }
     
     /**
      * Implementation of a deque (double-ended queue).
      * Time Complexity:
      * - Add/Remove from either end: O(1)
      * - Peek from either end: O(1)
      */
     public static class Deque<T> {
         private DoublyListNode<T> front;
         private DoublyListNode<T> rear;
         private int size;
         
         public Deque() {
             this.front = null;
             this.rear = null;
             this.size = 0;
         }
         
         public int size() {
             return size;
         }
         
         public boolean isEmpty() {
             return size == 0;
         }
         
         public void addFirst(T val) {
             DoublyListNode<T> newNode = new DoublyListNode<>(val);
             
             if (isEmpty()) {
                 front = newNode;
                 rear = newNode;
             } else {
                 newNode.next = front;
                 front.prev = newNode;
                 front = newNode;
             }
             size++;
         }
         
         public void addLast(T val) {
             DoublyListNode<T> newNode = new DoublyListNode<>(val);
             
             if (isEmpty()) {
                 front = newNode;
                 rear = newNode;
             } else {
                 newNode.prev = rear;
                 rear.next = newNode;
                 rear = newNode;
             }
             size++;
         }
         
         public T removeFirst() {
             if (isEmpty()) {
                 throw new NoSuchElementException("Deque is empty");
             }
             
             T val = front.val;
             
             if (size == 1) {
                 front = null;
                 rear = null;
             } else {
                 front = front.next;
                 front.prev = null;
             }
             
             size--;
             return val;
         }
         
         public T removeLast() {
             if (isEmpty()) {
                 throw new NoSuchElementException("Deque is empty");
             }
             
             T val = rear.val;
             
             if (size == 1) {
                 front = null;
                 rear = null;
             } else {
                 rear = rear.prev;
                 rear.next = null;
             }
             
             size--;
             return val;
         }
         
         public T peekFirst() {
             if (isEmpty()) {
                 throw new NoSuchElementException("Deque is empty");
             }
             
             return front.val;
         }
         
         public T peekLast() {
             if (isEmpty()) {
                 throw new NoSuchElementException("Deque is empty");
             }
             
             return rear.val;
         }
     }
     
     /**
      * Implementation of a priority queue using a binary heap.
      * Time Complexity:
      * - Insertion: O(log n)
      * - Extraction of min/max element: O(log n)
      * - Access to min/max element: O(1)
      */
     public static class PriorityQueue<T extends Comparable<T>> {
         private List<T> heap;
         private boolean isMinHeap; // true for min heap, false for max heap
         
         public PriorityQueue() {
             this(true); // Default to min heap
         }
         
         public PriorityQueue(boolean isMinHeap) {
             this.heap = new ArrayList<>();
             this.isMinHeap = isMinHeap;
         }
         
         public int size() {
             return heap.size();
         }
         
         public boolean isEmpty() {
             return heap.isEmpty();
         }
         
         public void add(T val) {
             heap.add(val);
             heapifyUp(heap.size() - 1);
         }
         
         public T peek() {
             if (isEmpty()) {
                 throw new NoSuchElementException("Priority queue is empty");
             }
             
             return heap.get(0);
         }
         
         public T poll() {
             if (isEmpty()) {
                 throw new NoSuchElementException("Priority queue is empty");
             }
             
             T root = heap.get(0);
             T lastElement = heap.remove(heap.size() - 1);
             
             if (!isEmpty()) {
                 heap.set(0, lastElement);
                 heapifyDown(0);
             }
             
             return root;
         }
         
         private void heapifyUp(int index) {
             if (index == 0) {
                 return;
             }
             
             int parentIndex = (index - 1) / 2;
             
             if (compare(heap.get(index), heap.get(parentIndex)) < 0) {
                 swap(index, parentIndex);
                 heapifyUp(parentIndex);
             }
         }
         
         private void heapifyDown(int index) {
             int leftChildIndex = 2 * index + 1;
             int rightChildIndex = 2 * index + 2;
             int smallest = index;
             
             if (leftChildIndex < heap.size() && compare(heap.get(leftChildIndex), heap.get(smallest)) < 0) {
                 smallest = leftChildIndex;
             }
             
             if (rightChildIndex < heap.size() && compare(heap.get(rightChildIndex), heap.get(smallest)) < 0) {
                 smallest = rightChildIndex;
             }
             
             if (smallest != index) {
                 swap(index, smallest);
                 heapifyDown(smallest);
             }
         }
         
         private void swap(int i, int j) {
             T temp = heap.get(i);
             heap.set(i, heap.get(j));
             heap.set(j, temp);
         }
         
         private int compare(T a, T b) {
             int result = a.compareTo(b);
             return isMinHeap ? result : -result;
         }
     }
 
     /**
      * TREE-BASED DATA STRUCTURES
      * --------------------------
      */
 
     /**
      * Basic node for a binary tree.
      */
     public static class TreeNode<T> {
         T val;
         TreeNode<T> left;
         TreeNode<T> right;
         
         public TreeNode(T val) {
             this.val = val;
             this.left = null;
             this.right = null;
         }
     }
     
     /**
      * Implementation of a binary search tree.
      * Time Complexity (average case):
      * - Search: O(log n)
      * - Insertion: O(log n)
      * - Deletion: O(log n)
      * Note: Worst case is O(n) for unbalanced trees
      */
     public static class BinarySearchTree<T extends Comparable<T>> {
         private TreeNode<T> root;
         
         public BinarySearchTree() {
             this.root = null;
         }
         
         public boolean isEmpty() {
             return root == null;
         }
         
         public void insert(T val) {
             root = insertRecursive(root, val);
         }
         
         private TreeNode<T> insertRecursive(TreeNode<T> node, T val) {
             if (node == null) {
                 return new TreeNode<>(val);
             }
             
             if (val.compareTo(node.val) < 0) {
                 node.left = insertRecursive(node.left, val);
             } else if (val.compareTo(node.val) > 0) {
                 node.right = insertRecursive(node.right, val);
             }
             
             return node; // Return unchanged node pointer if val already exists
         }
         
         public boolean search(T val) {
             return searchRecursive(root, val);
         }
         
         private boolean searchRecursive(TreeNode<T> node, T val) {
             if (node == null) {
                 return false;
             }
             
             if (val.equals(node.val)) {
                 return true;
             }
             
             if (val.compareTo(node.val) < 0) {
                 return searchRecursive(node.left, val);
             } else {
                 return searchRecursive(node.right, val);
             }
         }
         
         public void delete(T val) {
             root = deleteRecursive(root, val);
         }
         
         private TreeNode<T> deleteRecursive(TreeNode<T> node, T val) {
             if (node == null) {
                 return null;
             }
             
             if (val.compareTo(node.val) < 0) {
                 node.left = deleteRecursive(node.left, val);
             } else if (val.compareTo(node.val) > 0) {
                 node.right = deleteRecursive(node.right, val);
             } else {
                 // Node with only one child or no child
                 if (node.left == null) {
                     return node.right;
                 } else if (node.right == null) {
                     return node.left;
                 }
                 
                 // Node with two children: Get the inorder successor (smallest
                 // in the right subtree)
                 node.val = minValue(node.right);
                 
                 // Delete the inorder successor
                 node.right = deleteRecursive(node.right, node.val);
             }
             
             return node;
         }
         
         private T minValue(TreeNode<T> node) {
             T minVal = node.val;
             while (node.left != null) {
                 minVal = node.left.val;
                 node = node.left;
             }
             return minVal;
         }
         
         // Tree traversals
         public List<T> inorderTraversal() {
             List<T> result = new ArrayList<>();
             inorderTraversal(root, result);
             return result;
         }
         
         private void inorderTraversal(TreeNode<T> node, List<T> result) {
             if (node != null) {
                 inorderTraversal(node.left, result);
                 result.add(node.val);
                 inorderTraversal(node.right, result);
             }
         }
         
         public List<T> preorderTraversal() {
             List<T> result = new ArrayList<>();
             preorderTraversal(root, result);
             return result;
         }
         
         private void preorderTraversal(TreeNode<T> node, List<T> result) {
             if (node != null) {
                 result.add(node.val);
                 preorderTraversal(node.left, result);
                 preorderTraversal(node.right, result);
             }
         }
         
         public List<T> postorderTraversal() {
             List<T> result = new ArrayList<>();
             postorderTraversal(root, result);
             return result;
         }
         
         private void postorderTraversal(TreeNode<T> node, List<T> result) {
             if (node != null) {
                 postorderTraversal(node.left, result);
                 postorderTraversal(node.right, result);
                 result.add(node.val);
             }
         }
         
         public List<T> levelOrderTraversal() {
             List<T> result = new ArrayList<>();
             if (root == null) {
                 return result;
             }
             
             Queue<TreeNode<T>> queue = new LinkedList<>();
             queue.add(root);
             
             while (!queue.isEmpty()) {
                 TreeNode<T> node = queue.poll();
                 result.add(node.val);
                 
                 if (node.left != null) {
                     queue.add(node.left);
                 }
                 
                 if (node.right != null) {
                     queue.add(node.right);
                 }
             }
             
             return result;
         }
     }
     
     /**
      * GRAPH DATA STRUCTURES
      * --------------------
      */
     
     /**
      * Implementation of a graph using adjacency list.
      * Supports both directed and undirected graphs.
      * Time Complexity:
      * - Add vertex: O(1)
      * - Add edge: O(1)
      * - Remove vertex: O(V + E) where V is vertices and E is edges
      * - Remove edge: O(E) where E is the number of edges
      */
     public static class Graph<T> {
         private Map<T, List<T>> adjacencyList;
         private boolean isDirected;
         
         public Graph(boolean isDirected) {
             this.adjacencyList = new HashMap<>();
             this.isDirected = isDirected;
         }
         
         public void addVertex(T vertex) {
             adjacencyList.putIfAbsent(vertex, new ArrayList<>());
         }
         
         public void addEdge(T source, T destination) {
             // Ensure vertices exist
             addVertex(source);
             addVertex(destination);
             
             // Add edge from source to destination
             adjacencyList.get(source).add(destination);
             
             // If undirected, add edge from destination to source as well
             if (!isDirected) {
                 adjacencyList.get(destination).add(source);
             }
         }
         
         public void removeVertex(T vertex) {
             // Remove all edges pointing to this vertex
             adjacencyList.values().forEach(e -> e.remove(vertex));
             
             // Remove the vertex and its adjacency list
             adjacencyList.remove(vertex);
         }
         
         public void removeEdge(T source, T destination) {
             if (adjacencyList.containsKey(source) && adjacencyList.containsKey(destination)) {
                 adjacencyList.get(source).remove(destination);
                 
                 if (!isDirected) {
                     adjacencyList.get(destination).remove(source);
                 }
             }
         }
         
         public List<T> getNeighbors(T vertex) {
             return adjacencyList.getOrDefault(vertex, new ArrayList<>());
         }
         
         public Set<T> getVertices() {
             return adjacencyList.keySet();
         }
         
         public List<T> breadthFirstTraversal(T start) {
             List<T> result = new ArrayList<>();
             Set<T> visited = new HashSet<>();
             Queue<T> queue = new LinkedList<>();
             
             visited.add(start);
             queue.add(start);
             
             while (!queue.isEmpty()) {
                 T vertex = queue.poll();
                 result.add(vertex);
                 
                 for (T neighbor : getNeighbors(vertex)) {
                     if (!visited.contains(neighbor)) {
                         visited.add(neighbor);
                         queue.add(neighbor);
                     }
                 }
             }
             
             return result;
         }
         
         public List<T> depthFirstTraversal(T start) {
             List<T> result = new ArrayList<>();
             Set<T> visited = new HashSet<>();
             
             depthFirstUtil(start, visited, result);
             
             return result;
         }
         
         private void depthFirstUtil(T vertex, Set<T> visited, List<T> result) {
             visited.add(vertex);
             result.add(vertex);
             
             for (T neighbor : getNeighbors(vertex)) {
                 if (!visited.contains(neighbor)) {
                     depthFirstUtil(neighbor, visited, result);
                 }
             }
         }
         
         // Check if path exists from source to destination
         public boolean hasPath(T source, T destination) {
             Set<T> visited = new HashSet<>();
             return hasPathDFS(source, destination, visited);
         }
         
         private boolean hasPathDFS(T current, T destination, Set<T> visited) {
             if (current.equals(destination)) {
                 return true;
             }
             
             visited.add(current);
             
             for (T neighbor : getNeighbors(current)) {
                 if (!visited.contains(neighbor)) {
                     if (hasPathDFS(neighbor, destination, visited)) {
                         return true;
                     }
                 }
             }
             
             return false;
         }
         
         // Find shortest path using BFS (for unweighted graph)
         public List<T> shortestPath(T source, T destination) {
             Map<T, T> parentMap = new HashMap<>();
             Set<T> visited = new HashSet<>();
             Queue<T> queue = new LinkedList<>();
             
             visited.add(source);
             queue.add(source);
             
             while (!queue.isEmpty()) {
                 T current = queue.poll();
                 
                 if (current.equals(destination)) {
                     break;
                 }
                 
                 for (T neighbor : getNeighbors(current)) {
                     if (!visited.contains(neighbor)) {
                         visited.add(neighbor);
                         parentMap.put(neighbor, current);
                         queue.add(neighbor);
                     }
                 }
             }
             
             // If no path found
             if (!parentMap.containsKey(destination) && !source.equals(destination)) {
                 return new ArrayList<>();
             }
             
             // Reconstruct path
             List<T> path = new ArrayList<>();
             T current = destination;
             
             while (!current.equals(source)) {
                 path.add(0, current);
                 current = parentMap.get(current);
             }
             
             path.add(0, source);
             return path;
         }
     }
     
     /**
      * Implementation of a weighted graph using adjacency list.
      * Supports both directed and undirected graphs.
      */
     public static class WeightedGraph<T> {
         // Edge class to represent a weighted edge
         public static class Edge<T> {
             private T source;
             private T destination;
             private int weight;
             
             public Edge(T source, T destination, int weight) {
                 this.source = source;
                 this.destination = destination;
                 this.weight = weight;
             }
             
             public T getSource() {
                 return source;
             }
             
             public T getDestination() {
                 return destination;
             }
             
             public int getWeight() {
                 return weight;
             }
         }
         
         private Map<T, List<Edge<T>>> adjacencyList;
         private boolean isDirected;
         
         public WeightedGraph(boolean isDirected) {
             this.adjacencyList = new HashMap<>();
             this.isDirected = isDirected;
         }
         
         public void addVertex(T vertex) {
             adjacencyList.putIfAbsent(vertex, new ArrayList<>());
         }
         
         public void addEdge(T source, T destination, int weight) {
             // Ensure vertices exist
             addVertex(source);
             addVertex(destination);
             
             // Add edge from source to destination
             adjacencyList.get(source).add(new Edge<>(source, destination, weight));
             
             // If undirected, add edge from destination to source as well
             if (!isDirected) {
                 adjacencyList.get(destination).add(new Edge<>(destination, source, weight));
             }
         }
         
         public void removeVertex(T vertex) {
             // Remove edges pointing to this vertex
             adjacencyList.values().forEach(edges -> 
                 edges.removeIf(edge -> edge.getDestination().equals(vertex)));
             
             // Remove the vertex and its adjacency list
             adjacencyList.remove(vertex);
         }
         
         public void removeEdge(T source, T destination) {
             if (adjacencyList.containsKey(source)) {
                 adjacencyList.get(source).removeIf(edge -> edge.getDestination().equals(destination));
             }
             
             if (!isDirected && adjacencyList.containsKey(destination)) {
                 adjacencyList.get(destination).removeIf(edge -> edge.getDestination().equals(source));
             }
         }
         
         public List<Edge<T>> getEdges(T vertex) {
             return adjacencyList.getOrDefault(vertex, new ArrayList<>());
         }
         
         public Set<T> getVertices() {
             return adjacencyList.keySet();
         }
         
         // Implementation of Dijkstra's algorithm for finding shortest paths
         public Map<T, Integer> dijkstra(T start) {
             Map<T, Integer> distances = new HashMap<>();
             PriorityQueue<Map.Entry<T, Integer>> pq = new PriorityQueue<>(
                 Comparator.comparing(Map.Entry::getValue));
             Set<T> settled = new HashSet<>();
             
             // Initialize distances
             for (T vertex : getVertices()) {
                 distances.put(vertex, Integer.MAX_VALUE);
             }
             distances.put(start, 0);
             
             pq.add(new AbstractMap.SimpleEntry<>(start, 0));
             
             while (!pq.isEmpty()) {
                 T current = pq.poll().getKey();
                 
                 if (settled.contains(current)) {
                     continue;
                 }
                 
                 settled.add(current);
                 
                 for (Edge<T> edge : getEdges(current)) {
                     T neighbor = edge.getDestination();
                     
                     if (!settled.contains(neighbor)) {
                         int newDistance = distances.get(current) + edge.getWeight();
                         
                         if (newDistance < distances.get(neighbor)) {
                             distances.put(neighbor, newDistance);
                             pq.add(new AbstractMap.SimpleEntry<>(neighbor, newDistance));
                         }
                     }
                 }
             }
             
             return distances;
         }
         
         // Implementation of Bellman-Ford algorithm for shortest paths (handles negative weights)
         public Map<T, Integer> bellmanFord(T start) {
             Map<T, Integer> distances = new HashMap<>();
             List<Edge<T>> allEdges = new ArrayList<>();
             
             // Initialize distances
             for (T vertex : getVertices()) {
                 distances.put(vertex, Integer.MAX_VALUE);
                 allEdges.addAll(getEdges(vertex));
             }
             distances.put(start, 0);
             
             // Relax edges |V| - 1 times
             int vertexCount = getVertices().size();
             
             for (int i = 0; i < vertexCount - 1; i++) {
                 for (Edge<T> edge : allEdges) {
                     T source = edge.getSource();
                     T destination = edge.getDestination();
                     int weight = edge.getWeight();
                     
                     if (distances.get(source) != Integer.MAX_VALUE && 
                         distances.get(source) + weight < distances.get(destination)) {
                         distances.put(destination, distances.get(source) + weight);
                     }
                 }
             }
             
             // Check for negative-weight cycles
             for (Edge<T> edge : allEdges) {
                 T source = edge.getSource();
                 T destination = edge.getDestination();
                 int weight = edge.getWeight();
                 
                 if (distances.get(source) != Integer.MAX_VALUE && 
                     distances.get(source) + weight < distances.get(destination)) {
                     // Negative cycle exists
                     throw new IllegalStateException("Graph contains a negative-weight cycle");
                 }
             }
             
             return distances;
         }
         
         // Implementation of Kruskal's algorithm for Minimum Spanning Tree
         public List<Edge<T>> kruskalMST() {
             List<Edge<T>> mst = new ArrayList<>();
             List<Edge<T>> allEdges = new ArrayList<>();
             
             // Get all edges
             for (T vertex : getVertices()) {
                 for (Edge<T> edge : getEdges(vertex)) {
                     // For undirected graphs, avoid duplicates
                     if (isDirected || edge.getSource().hashCode() <= edge.getDestination().hashCode()) {
                         allEdges.add(edge);
                     }
                 }
             }
             
             // Sort edges by weight
             allEdges.sort(Comparator.comparing(Edge::getWeight));
             
             // Create disjoint set
             Map<T, T> parent = new HashMap<>();
             for (T vertex : getVertices()) {
                 parent.put(vertex, vertex);
             }
             
             for (Edge<T> edge : allEdges) {
                 T sourceRoot = find(parent, edge.getSource());
                 T destRoot = find(parent, edge.getDestination());
                 
                 // If including this edge doesn't form a cycle, include it
                 if (!sourceRoot.equals(destRoot)) {
                     mst.add(edge);
                     union(parent, sourceRoot, destRoot);
                 }
             }
             
             return mst;
         }
         
         private T find(Map<T, T> parent, T item) {
             if (!parent.get(item).equals(item)) {
                 parent.put(item, find(parent, parent.get(item)));
             }
             return parent.get(item);
         }
         
         private void union(Map<T, T> parent, T x, T y) {
             parent.put(x, y);
         }
     }
     
     /**
      * HASH-BASED DATA STRUCTURES
      * ------------------------
      */
     
     /**
      * Implementation of a HashTable with linear probing for collision handling.
      * Time Complexity (average case):
      * - Put: O(1)
      * - Get: O(1)
      * - Remove: O(1)
      * Note: Worst case is O(n) for highly colliding hash functions
      */
     public static class HashTable<K, V> {
         private static class Entry<K, V> {
             K key;
             V value;
             boolean isDeleted;
             
             public Entry(K key, V value) {
                 this.key = key;
                 this.value = value;
                 this.isDeleted = false;
             }
         }
         
         private Entry<K, V>[] table;
         private int size;
         private int capacity;
         private float loadFactor;
         
         @SuppressWarnings("unchecked")
         public HashTable() {
             this(16, 0.75f);
         }
         
         @SuppressWarnings("unchecked")
         public HashTable(int initialCapacity, float loadFactor) {
             this.capacity = initialCapacity;
             this.loadFactor = loadFactor;
             this.size = 0;
             this.table = (Entry<K, V>[]) new Entry[capacity];
         }
         
         public int size() {
             return size;
         }
         
         public boolean isEmpty() {
             return size == 0;
         }
         
         public V get(K key) {
             int index = findKey(key);
             
             if (index != -1) {
                 return table[index].value;
             }
             
             return null;
         }
         
         public void put(K key, V value) {
             if (key == null) {
                 throw new IllegalArgumentException("Key cannot be null");
             }
             
             // Check load factor and resize if necessary
             if ((float) size / capacity >= loadFactor) {
                 resize(2 * capacity);
             }
             
             int index = getIndex(key);
             int originalIndex = index;
             int step = 0;
             
             // Linear probing
             while (table[index] != null) {
                 if (table[index].isDeleted || 
                     (table[index].key != null && table[index].key.equals(key))) {
                     break;
                 }
                 
                 step++;
                 index = (originalIndex + step) % capacity;
             }
             
             if (table[index] != null && !table[index].isDeleted && 
                 table[index].key != null && table[index].key.equals(key)) {
                 // Update existing entry
                 table[index].value = value;
             } else {
                 // Create new entry
                 table[index] = new Entry<>(key, value);
                 size++;
             }
         }
         
         public V remove(K key) {
             int index = findKey(key);
             
             if (index != -1) {
                 V value = table[index].value;
                 table[index].isDeleted = true;
                 size--;
                 return value;
             }
             
             return null;
         }
         
         private int getIndex(K key) {
             return (key.hashCode() & 0x7FFFFFFF) % capacity;
         }
         
         private int findKey(K key) {
             if (key == null) {
                 return -1;
             }
             
             int index = getIndex(key);
             int originalIndex = index;
             int step = 0;
             
             // Linear probing
             while (table[index] != null) {
                 if (!table[index].isDeleted && 
                     table[index].key != null && table[index].key.equals(key)) {
                     return index;
                 }
                 
                 step++;
                 index = (originalIndex + step) % capacity;
                 
                 // If we've gone through the entire table
                 if (index == originalIndex) {
                     break;
                 }
             }
             
             return -1;
         }
         
         @SuppressWarnings("unchecked")
         private void resize(int newCapacity) {
             Entry<K, V>[] oldTable = table;
             table = (Entry<K, V>[]) new Entry[newCapacity];
             
             size = 0;
             int oldCapacity = capacity;
             capacity = newCapacity;
             
             for (int i = 0; i < oldCapacity; i++) {
                 if (oldTable[i] != null && !oldTable[i].isDeleted) {
                     put(oldTable[i].key, oldTable[i].value);
                 }
             }
         }
         
         public boolean containsKey(K key) {
             return findKey(key) != -1;
         }
         
         public Set<K> keySet() {
             Set<K> keySet = new HashSet<>();
             
             for (Entry<K, V> entry : table) {
                 if (entry != null && !entry.isDeleted) {
                     keySet.add(entry.key);
                 }
             }
             
             return keySet;
         }
         
         public Collection<V> values() {
             List<V> values = new ArrayList<>();
             
             for (Entry<K, V> entry : table) {
                 if (entry != null && !entry.isDeleted) {
                     values.add(entry.value);
                 }
             }
             
             return values;
         }
     }
     
     /**
      * Implementation of a LRU (Least Recently Used) Cache.
      * Time Complexity:
      * - Get: O(1)
      * - Put: O(1)
      */
     public static class LRUCache<K, V> {
         private class Node {
             K key;
             V value;
             Node prev;
             Node next;
             
             public Node(K key, V value) {
                 this.key = key;
                 this.value = value;
             }
         }
         
         private int capacity;
         private Map<K, Node> cache;
         private Node head;  // Most recently used
         private Node tail;  // Least recently used
         
         public LRUCache(int capacity) {
             this.capacity = capacity;
             this.cache = new HashMap<>();
             this.head = new Node(null, null);
             this.tail = new Node(null, null);
             
             head.next = tail;
             tail.prev = head;
         }
         
         public V get(K key) {
             Node node = cache.get(key);
             if (node == null) {
                 return null;
             }
             
             // Move to front (most recently used)
             moveToHead(node);
             
             return node.value;
         }
         
         public void put(K key, V value) {
             Node node = cache.get(key);
             
             if (node == null) {
                 // Create new node
                 Node newNode = new Node(key, value);
                 cache.put(key, newNode);
                 addToHead(newNode);
                 
                 // Check capacity
                 if (cache.size() > capacity) {
                     // Remove least recently used
                     Node tail = removeTail();
                     cache.remove(tail.key);
                 }
             } else {
                 // Update existing node
                 node.value = value;
                 moveToHead(node);
             }
         }
         
         private void addToHead(Node node) {
             node.prev = head;
             node.next = head.next;
             
             head.next.prev = node;
             head.next = node;
         }
         
         private void removeNode(Node node) {
             node.prev.next = node.next;
             node.next.prev = node.prev;
         }
         
         private void moveToHead(Node node) {
             removeNode(node);
             addToHead(node);
         }
         
         private Node removeTail() {
             Node res = tail.prev;
             removeNode(res);
             return res;
         }
     }
     
     /**
      * DISJOINT SET (UNION-FIND)
      * -------------------------
      */
     
     /**
      * Implementation of a Disjoint Set (Union-Find) data structure.
      * Time Complexity (with path compression and union by rank):
      * - Find: O(α(n)), where α is the inverse Ackermann function (nearly constant)
      * - Union: O(α(n))
      */
     public static class DisjointSet {
         private int[] parent;
         private int[] rank;
         private int count;  // Number of disjoint sets
         
         public DisjointSet(int n) {
             parent = new int[n];
             rank = new int[n];
             count = n;
             
             // Initialize each element as a separate set
             for (int i = 0; i < n; i++) {
                 parent[i] = i;
                 rank[i] = 0;
             }
         }
         
         // Find with path compression
         public int find(int x) {
             if (parent[x] != x) {
                 parent[x] = find(parent[x]);  // Path compression
             }
             return parent[x];
         }
         
         // Union by rank
         public void union(int x, int y) {
             int rootX = find(x);
             int rootY = find(y);
             
             if (rootX != rootY) {
                 if (rank[rootX] < rank[rootY]) {
                     parent[rootX] = rootY;
                 } else if (rank[rootX] > rank[rootY]) {
                     parent[rootY] = rootX;
                 } else {
                     parent[rootY] = rootX;
                     rank[rootX]++;
                 }
                 count--;  // Decrease count when two sets are merged
             }
         }
         
         public boolean isConnected(int x, int y) {
             return find(x) == find(y);
         }
         
         public int getCount() {
             return count;
         }
     }
     
     /**
      * SEGMENT TREE
      * -----------
      */
     
     /**
      * Implementation of a Segment Tree for range queries and updates.
      * Time Complexity:
      * - Build: O(n)
      * - Query: O(log n)
      * - Update: O(log n)
      */
     public static class SegmentTree {
         private int[] tree;
         private int n;
         
         public SegmentTree(int[] nums) {
             if (nums.length > 0) {
                 n = nums.length;
                 // Height of segment tree = ceiling of log2(n)
                 int height = (int) Math.ceil(Math.log(n) / Math.log(2));
                 // Max size of segment tree
                 int maxSize = 2 * (int) Math.pow(2, height) - 1;
                 tree = new int[maxSize];
                 buildTree(nums, 0, 0, n - 1);
             }
         }
         
         // Build segment tree from array
         private void buildTree(int[] nums, int treeIndex, int lo, int hi) {
             if (lo == hi) {
                 // Leaf node
                 tree[treeIndex] = nums[lo];
                 return;
             }
             
             int mid = lo + (hi - lo) / 2;
             // Build left subtree
             buildTree(nums, 2 * treeIndex + 1, lo, mid);
             // Build right subtree
             buildTree(nums, 2 * treeIndex + 2, mid + 1, hi);
             
             // Internal node will have the sum of both of its children
             tree[treeIndex] = tree[2 * treeIndex + 1] + tree[2 * treeIndex + 2];
         }
         
         // Query sum in range [qlo, qhi]
         public int sumRange(int qlo, int qhi) {
             if (qlo < 0 || qhi >= n || qlo > qhi) {
                 throw new IndexOutOfBoundsException("Invalid range");
             }
             
             return sumRangeHelper(0, 0, n - 1, qlo, qhi);
         }
         
         private int sumRangeHelper(int treeIndex, int lo, int hi, int qlo, int qhi) {
             // Total overlap
             if (qlo <= lo && qhi >= hi) {
                 return tree[treeIndex];
             }
             
             // No overlap
             if (hi < qlo || lo > qhi) {
                 return 0;
             }
             
             // Partial overlap - recurse to children
             int mid = lo + (hi - lo) / 2;
             return sumRangeHelper(2 * treeIndex + 1, lo, mid, qlo, qhi) +
                    sumRangeHelper(2 * treeIndex + 2, mid + 1, hi, qlo, qhi);
         }
         
         // Update value at index
         public void update(int index, int val) {
             if (index < 0 || index >= n) {
                 throw new IndexOutOfBoundsException("Invalid index");
             }
             
             updateHelper(0, 0, n - 1, index, val);
         }
         
         private void updateHelper(int treeIndex, int lo, int hi, int index, int val) {
             // Found the index
             if (lo == hi) {
                 tree[treeIndex] = val;
                 return;
             }
             
             int mid = lo + (hi - lo) / 2;
             
             if (index <= mid) {
                 // Update left subtree
                 updateHelper(2 * treeIndex + 1, lo, mid, index, val);
             } else {
                 // Update right subtree
                 updateHelper(2 * treeIndex + 2, mid + 1, hi, index, val);
             }
             
             // Update current node
             tree[treeIndex] = tree[2 * treeIndex + 1] + tree[2 * treeIndex + 2];
         }
     }
     
     /**
      * FENWICK TREE (BINARY INDEXED TREE)
      * ---------------------------------
      */
     
     /**
      * Implementation of a Fenwick Tree (Binary Indexed Tree) for efficient prefix sums.
      * Time Complexity:
      * - Update: O(log n)
      * - Query: O(log n)
      */
     public static class FenwickTree {
         private int[] tree;
         private int n;
         
         public FenwickTree(int size) {
             this.n = size + 1;
             this.tree = new int[n];
         }
         
         // Update value at index (1-based indexing)
         public void update(int index, int delta) {
             index++; // Convert to 1-based indexing
             
             while (index < n) {
                 tree[index] += delta;
                 index += (index & -index); // Add least significant bit
             }
         }
         
         // Get sum of elements from 0 to index (inclusive, 0-based indexing)
         public int prefixSum(int index) {
             index++; // Convert to 1-based indexing
             int sum = 0;
             
             while (index > 0) {
                 sum += tree[index];
                 index -= (index & -index); // Remove least significant bit
             }
             
             return sum;
         }
         
         // Get sum of elements in range [left, right] (inclusive, 0-based indexing)
         public int rangeSum(int left, int right) {
             return prefixSum(right) - (left > 0 ? prefixSum(left - 1) : 0);
         }
     }
     
     /**
      * UTILITY METHODS
      * --------------
      */
     
     /**
      * Utility method to find the maximum element in an array.
      */
     public static <T extends Comparable<T>> T findMax(T[] array) {
         if (array == null || array.length == 0) {
             throw new IllegalArgumentException("Array cannot be empty");
         }
         
         T max = array[0];
         for (int i = 1; i < array.length; i++) {
             if (array[i].compareTo(max) > 0) {
                 max = array[i];
             }
         }
         
         return max;
     }
     
     /**
      * Utility method to find the minimum element in an array.
      */
     public static <T extends Comparable<T>> T findMin(T[] array) {
         if (array == null || array.length == 0) {
             throw new IllegalArgumentException("Array cannot be empty");
         }
         
         T min = array[0];
         for (int i = 1; i < array.length; i++) {
             if (array[i].compareTo(min) < 0) {
                 min = array[i];
             }
         }
         
         return min;
     }
     
     /**
      * Utility method to reverse an array.
      */
     public static <T> void reverseArray(T[] array) {
         if (array == null) {
             return;
         }
         
         int left = 0;
         int right = array.length - 1;
         
         while (left < right) {
             T temp = array[left];
             array[left] = array[right];
             array[right] = temp;
             left++;
             right--;
         }
     }
     
     /**
      * Utility method to check if a string is a palindrome.
      */
     public static boolean isPalindrome(String s) {
         if (s == null) {
             return false;
         }
         
         int left = 0;
         int right = s.length() - 1;
         
         while (left < right) {
             if (s.charAt(left) != s.charAt(right)) {
                 return false;
             }
             left++;
             right--;
         }
         
         return true;
     }
 }