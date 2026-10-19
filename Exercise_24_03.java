// Look for WRITE YOUR CODE to write your code
import java.util.Iterator;
import java.util.ListIterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Collection;

public class Exercise_24_03 {
  public static void main(String[] args) {
    new Exercise_24_03();
  }
  
  public Exercise_24_03() {
    TwoWayLinkedList<Double> list = new TwoWayLinkedList<>();
    System.out.print("Enter five numbers: ");
    Scanner input = new Scanner(System.in);
    double[] v = new double[5];
    for (int i = 0; i < 5; i++) 
      v[i] = input.nextDouble();

    list.add(v[1]);
    list.add(v[2]);
    list.add(v[3]);
    list.add(v[4]);
    list.add(0, v[0]);
    list.add(2, 10.55);
    list.remove(3);

    java.util.ListIterator<Double> iterator1 = list.listIterator();
    System.out.print("The list in forward order: ");
    while (iterator1.hasNext())
      System.out.print(iterator1.next() + " ");

    java.util.ListIterator<Double> iterator2 = list.listIterator(list.size() - 1);
    System.out.print("\nThe list in backward order: ");
    while (iterator2.hasPrevious())
      System.out.print(iterator2.previous() + " ");
  }
}

interface MyList<E> extends java.util.Collection<E> {
  /** Add a new element at the specified index in this list */
  public void add(int index, E e);

  /** Return the element from this list at the specified index */
  public E get(int index);

  /** Return the index of the first matching element in this list.
   *  Return -1 if no match. */
  public int indexOf(Object e);

  /** Return the index of the last matching element in this list
   *  Return -1 if no match. */
  public int lastIndexOf(E e);

  /** Remove the element at the specified position in this list
   *  Shift any subsequent elements to the left.
   *  Return the element that was removed from the list. */
  public E remove(int index);

  /** Replace the element at the specified position in this list
   *  with the specified element and returns the new set. */
  public E set(int index, E e);
  
  @Override /** Add a new element at the end of this list */
  public default boolean add(E e) {
    add(size(), e);
    return true;
  }

  @Override /** Return true if this list contains no elements */
  public default boolean isEmpty() {
    return size() == 0;
  }

  @Override /** Remove the first occurrence of the element e 
   *  from this list. Shift any subsequent elements to the left.
   *  Return true if the element is removed. */
  public default boolean remove(Object e) {
    if (indexOf(e) >= 0) {
      remove(indexOf(e));
      return true;
    }
    else
      return false;
  }

  @Override
  public default boolean containsAll(Collection<?> c) {
    // Left as an exercise
    return true;
  }

  @Override
  public default boolean addAll(Collection<? extends E> c) {
    // Left as an exercise
    return true;
  }

  @Override
  public default boolean removeAll(Collection<?> c) {
    // Left as an exercise
    return true;
  }

  @Override
  public default boolean retainAll(Collection<?> c) {
    // Left as an exercise
    return true;
  }

  @Override
  public default Object[] toArray() {
    // Left as an exercise
    return null;
  }

  @Override
  public default <T> T[] toArray(T[] array) {
    // Left as an exercise
    return null;
  }
}

class TwoWayLinkedList<E> implements MyList<E> {
  private Node<E> head, tail;
  private int size;

  /** Create a default list */
  public TwoWayLinkedList() {
  }

  /** Create a list from an array of objects */
  public TwoWayLinkedList(E[] objects) {
    size = objects.length;
    for (E e : objects)
      add(e);
  }

  /** Return the head element in the list */
  public E getFirst() {
    if (size == 0) {
      return null;
    } else {
      return head.element;
    }
  }

  /** Return the last element in the list */
  public E getLast() {
    if (size == 0) {
      return null;
    } else {
      return tail.element;
    }
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder("[");

    Node<E> current = head;
    for (int i = 0; i < size; i++) {
      result.append(current.element);
      current = current.next;
      if (current != null) {
        result.append(", "); // Separate two elements with a comma
      } else {
        result.append("]"); // Insert the closing ] in the string
      }
    }

    return result.toString();
  }

  /** Clear the list */
  @Override
  public void clear() {
    head = tail = null;
    
    // set size to 0 now that list is empty
    size = 0;
  }

  /** Return true if this list contains the element o */
  @Override
  public boolean contains(Object e) {
    // If doesn't exist, indexOf(e) = -1. Else, indexOf(e) >= 0.
    return indexOf(e) >= 0;
  }

  /** Return the element from this list at the specified index */
  @Override
  public E get(int index) {
    if (index < 0 || index >= size ) {
        // Return null if index is out of range
        return null;
    }
    Node<E> currentNode = head;
    for (int i = 0; i < index; i++) {
        currentNode = currentNode.next;
    }
    return currentNode.element;
  }

  /**
   * Return the index of the head matching element in this list. Return -1 if
   * no match.
   */
  @Override
  public int indexOf(Object e) {
    Node<E> currentNode = head;
    for (int i = 0; i < size; i++) {
        /* If element is null, then check if current node is null as well, as 
        e.equals() throws an error when comapring null */
        if (e == null ? currentNode.element == null : e.equals(currentNode.element)) {
            return i;
        }
        currentNode = currentNode.next;
    }
    // If can't find an index, return -1
    return -1;
  }

  /**
   * Return the index of the last matching element in this list Return -1 if
   * no match.
   */
  @Override
  public int lastIndexOf(Object e) {
    int lastIndex = -1;
    Node<E> currentNode = head;
    for (int i = 0; i < size; i++) {
        if (e == null ? currentNode.element == null : e.equals(currentNode.element)) {
            lastIndex = i;
        }
        currentNode = currentNode.next;
    }
    return lastIndex;
  }

  /**
   * Replace the element at the specified position in this list with the
   * specified element.
   */
  @Override
  public E set(int index, E e) {
    if (index < 0 || index >= size) {
        return null;
    }
    Node<E> currentNode = head;
    for (int i = 0; i < index; i++) {
        currentNode = currentNode.next;
    }
    E oldElement = currentNode.element;
    currentNode.element = e;
    return oldElement;
  }

  private class LinkedListIterator implements java.util.ListIterator<E> {
    private Node<E> current = head; // Current index

    public LinkedListIterator() {
    }
    
    public LinkedListIterator(int index) {
      if (index < 0 || index >= size)
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: "
          + size);
      for (int nextIndex = 0; nextIndex < index; nextIndex++)
        current = current.next;
    }
    
    public void setLast() {
  	current = tail;
    }
    
    @Override
    public boolean hasNext() {
      return (current != null);
    }

    @Override
    public E next() {
      E e = current.element;
      current = current.next;
      return e;
    }

    @Override
    public void remove() {
      if (current.equals(head)) {
          removeFirst();
      } else if (current.equals(tail)) {
          removeLast();
      }
      current.previous.next = current.next;
      current.next.previous = current.previous;
    }

    @Override
    public void add(E e) {
        Node<E> newNode = new Node<>(e);
        current.next.previous = newNode;
        current.next = newNode;
    }

    @Override
    public boolean hasPrevious() {
      return current != null;
    }

    @Override
    public int nextIndex() {
      int index = indexOf(current.next);
      return index;
    }

    @Override
    public E previous() {
      E e = current.element;
      current = current.previous;
      return e;
    }

    @Override
    public int previousIndex() {
      int index = indexOf(current.previous);
      return index;
    }

    @Override
    public void set(E e) {
      current.element = e;
    }
  }

  private class Node<E> {
    E element;
    Node<E> next;
    Node<E> previous;

    public Node(E o) {
      element = o;
    }
  }

  @Override
  public int size() {
    return size;
  }

  public ListIterator<E> listIterator() {
    return new LinkedListIterator(); 
  }
  
  public ListIterator<E> listIterator(int index) {
    return new LinkedListIterator(index); 
  }

  @Override
  public Iterator<E> iterator() {
    // TODO Auto-generated method stub
    return null;
  }

  /** Add an element to the beginning of the list */
  public void addFirst(E e) {    
    
    Node<E> newHead = new Node<>(e);
    
    if (size == 0) {
        tail = head = newHead;
    } else {
        newHead.next = head;
        head.previous = newHead;
        head = newHead;
    }
    size++;
  }

  /** Add an element to the end of the list */
  public void addLast(E e) {
    
    Node<E> newTail = new Node<>(e);
    
    if (size == 0) {
        tail = head = newTail;
    } else {
        tail.next = newTail;
        newTail.previous = tail;
        tail = newTail;
    }
    size++;
  }

  /**
   * Add a new element at the specified index in this list The index of the
   * head element is 0
   */
  @Override
  public void add(int index, E e) {
    
    if (index < 0 || index > size) return;
    
    if (index == 0) {
        addFirst(e);
        return;
    } else if (index == size) {
        addLast(e);
        return;
    }
    
    Node<E> current = head;
    for (int i = 0; i < index; i++) {
        current = current.next;
    }
    
    
    Node<E> newNode = new Node<>(e);
    
    current.previous.next = newNode;
    newNode.previous = current.previous;
    newNode.next = current;
    current.previous = newNode;
    size++;
  }

  /**
   * Remove the head node and return the object that is contained in the
   * removed node.
   */
  public E removeFirst() {
    Node<E> oldHead = head;
    
    if (size == 0) return null;
    if (size == 1) {
        head = tail = null;
        size--;
        return oldHead.element;
    }
    
    oldHead.next.previous = null;
    head = oldHead.next;
    oldHead.next = null;
    
    size--;
    
    return oldHead.element;
  }

  /**
   * Remove the last node and return the object that is contained in the
   * removed node.
   */
  public E removeLast() {
    Node<E> oldTail = tail;
    
    if (size == 0) return null;
    if (size == 1) {
        head = tail = null;
        size--;
        return oldTail.element;
    }
    
    oldTail.previous.next = null;
    tail = oldTail.previous;
    oldTail.previous = null;
    
    size--;
    
    return oldTail.element;
  }

  /**
   * Remove the element at the specified position in this list. Return the
   * element that was removed from the list.
   */
  @Override
  public E remove(int index) {
      
    if (index < 0 || index >= size) return null;
    
    if (index == 0) return removeFirst();
    if (index == size - 1) return removeLast();
    
    Node<E> current = head;
    for (int i = 0; i < index; i++) {
        current = current.next;
    }
    
    Node<E> oldNode = current;
    
    oldNode.next.previous = oldNode.previous;
    oldNode.previous.next = oldNode.next;
    
    size--;
    return oldNode.element;
  }
}