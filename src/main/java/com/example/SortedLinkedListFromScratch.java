package com.example;

import java.util.*;

/**
 * Implementation of {@link SortedLinkedList} using a custom singly linked list structure.
 * <p>
 * This class maintains elements in sorted order at all times, using either the natural order
 * of elements or a provided {@link Comparator}. Elements are inserted at the correct position
 * to preserve sorting, and duplicate values are allowed and kept in order of insertion.
 * <ul>
 *   <li>Efficient insertion by traversing the list to the correct position.</li>
 *   <li>Null elements are not supported (throws {@link NullPointerException}).</li>
 *   <li>Sorting order is defined once at construction and cannot be changed later.</li>
 *   <li>Provides static factory methods for Integer and String types.</li>
 * </ul>
 *
 * @param <E> the type of elements, must implement {@link Comparable}
 */
public final class SortedLinkedListFromScratch<E extends Comparable<E>> implements SortedLinkedList<E> {

    private transient int size = 0;
    private transient Node<E> first;
    private final Comparator<E> comparator;

    private SortedLinkedListFromScratch() {
        this(Comparator.naturalOrder());
    }

    public SortedLinkedListFromScratch(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public static SortedLinkedListFromScratch<Integer> createForIntegers() {
        return new SortedLinkedListFromScratch<>();
    }

    public static SortedLinkedListFromScratch<Integer> createForIntegers(Comparator<Integer> comparator) {
        return new SortedLinkedListFromScratch<>(comparator);
    }

    public static SortedLinkedListFromScratch<String> createForStrings() {
        return new SortedLinkedListFromScratch<>();
    }

    public static SortedLinkedListFromScratch<String> createForStrings(Comparator<String> comparator) {
        return new SortedLinkedListFromScratch<>(comparator);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(E e) {
        Objects.requireNonNull(e);
        Node<E> newNode = new Node<>(e);
        if (first == null) {
            first = newNode;
            size++;
            return true;
        }
        Node<E> current = first;
        Node<E> previous = null;

        // Find the correct position to insert the new node
        // Skip nodes that are less than the new element
        while (current != null && comparator.compare(current.data, e) < 0) {
            previous = current;
            current = current.next;
        }

        if (previous == null) {
            // Insert at the beginning
            newNode.next = first;
            first = newNode;
            size++;
            return true;
        }

        // Insert in the middle or end
        newNode.next = current;
        previous.next = newNode;
        size++;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Objects.requireNonNull(c);
        if (c.isEmpty()) {
            return false;
        }

        // Can not use c.contains(null), the check itself could throw a NullPointerException
        c.forEach(Objects::requireNonNull);

        boolean modified = false;
        for (E element : c) {
            // Maybe more sophisticated adding could be used - iterating only once through the collections,
            // but there is overhead of sorting the input collection first, probably using helper collection.
            // Not sure if it is worth it.
            if (add(element)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean remove(Object o) {
        if (size == 0 || o == null || !(o instanceof Comparable)) {
            return false;
        }

        Node<E> current = first;
        Node<E> previous = null;
        @SuppressWarnings("unchecked")
        Comparable<E> comparable = (Comparable<E>) o;

        while (current != null) {
            int cmp = comparable.compareTo(current.data);
            if (cmp == 0) {
                // Remove the current node
                if (previous == null) {
                    first = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return true;
            } else if (cmp < 0) {
                // If we reach a node with a greater value, the element is not in the list
                return false;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        if (size == 0 || o == null || !(o instanceof Comparable)) {
            return false;
        }

        Node<E> current = first;
        @SuppressWarnings("unchecked")
        Comparable<E> comparable = (Comparable<E>) o;
        while (current != null) {
            int cmp = comparable.compareTo(current.data);
            if (cmp == 0) {
                // Found the element
                return true;
            } else if (cmp < 0) {
                // Element is not in the list, jump out early
                return false;
            }
            current = current.next;
        }

        // If we reach here, the element was not found
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private Node<E> current = first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                E data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        Node<E> current = first;
        int index = 0;
        while (current != null) {
            result[index++] = current.data;
            current = current.next;
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            // If the provided array is too small, create a new one
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }
        Node<E> current = first;
        int index = 0;
        while (current != null) {
            a[index++] = (T) current.data;
            current = current.next;
        }
        if (a.length > size) {
            a[size] = null; // Null-terminate the array
        }
        return a;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null || c.isEmpty()) {
            return true; // An empty collection is always contained
        }
        for (Object element : c) {
            if (!contains(element)) {
                return false; // If any element is not contained, return false
            }
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        if (c.isEmpty()) {
            return false; // Nothing to remove
        }
        boolean modified = false;
        for (Object element : c) {
            if (remove(element)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        if (c.isEmpty()) {
            clear(); // If the collection is empty, clear the list
            return true;
        }
        boolean modified = false;
        Node<E> current = first;
        Node<E> previous = null;

        while (current != null) {
            if (!c.contains(current.data)) {
                // Remove the current node
                if (previous == null) {
                    first = current.next; // Remove from the head
                } else {
                    previous.next = current.next; // Remove from the middle or end
                }
                size--;
                modified = true;
            } else {
                previous = current; // Move to the next node
            }
            current = current.next;
        }
        return modified;
    }

    @Override
    public void clear() {
        first = null;
        size = 0;
    }

    @Override
    public SequencedCollection<E> reversed() {
        if (isEmpty()) {
            return this; // Return the same instance if empty
        }
        SortedLinkedListFromScratch<E> reversedList = new SortedLinkedListFromScratch<>(comparator.reversed());

        reversedList.addAll(this);
        return reversedList;
    }

    private static class Node<E extends Comparable<E>> {
        E data;
        Node<E> next;

        private Node(E data) {
            this.data = data;
            this.next = null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof List)) {
            return false;
        }

        Iterator<E> e1 = this.iterator();
        Iterator<?> e2 = ((List<?>) o).listIterator();
        while (e1.hasNext() && e2.hasNext()) {
            E o1 = e1.next();
            Object o2 = e2.next();
            if (!(Objects.equals(o1, o2))) {
                return false;
            }
        }
        return !(e1.hasNext() || e2.hasNext());
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        for (E e : this)
            hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
        return hashCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<E> it = this.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}

