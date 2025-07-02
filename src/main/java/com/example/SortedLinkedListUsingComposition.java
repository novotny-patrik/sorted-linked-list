package com.example;

import java.util.*;

/**
 * Implementation of {@link SortedLinkedList} using composition with Java's {@link LinkedList}.
 * <p>
 * This class maintains elements in sorted order by sorting the underlying LinkedList after each
 * modification that can change order. It supports custom sorting order via a {@link Comparator}
 * provided at construction time, or uses natural order by default.
 * <ul>
 *   <li>Leverages proven LinkedList implementation for storage and iteration.</li>
 *   <li>Null elements are not supported (throws {@link NullPointerException}).</li>
 *   <li>Sorting order is defined once at construction and cannot be changed later.</li>
 *   <li>Provides static factory methods for Integer and String types.</li>
 * </ul>
 *
 * @param <E> the type of elements, must implement {@link Comparable}
 */
public final class SortedLinkedListUsingComposition<E extends Comparable<E>> implements SortedLinkedList<E> {

    private final LinkedList<E> linkedList;
    private final Comparator<E> comparator;

    SortedLinkedListUsingComposition() {
        this(Comparator.naturalOrder());
    }

    private SortedLinkedListUsingComposition(Comparator<E> comparator) {
        this.linkedList = new LinkedList<>();
        this.comparator = comparator;
    }

    public static SortedLinkedListUsingComposition<Integer> createForIntegers() {
        return new SortedLinkedListUsingComposition<>();
    }

    public static SortedLinkedListUsingComposition<Integer> createForIntegers(Comparator<Integer> comparator) {
        return new SortedLinkedListUsingComposition<>(comparator);
    }

    public static SortedLinkedListUsingComposition<String> createForStrings() {
        return new SortedLinkedListUsingComposition<>();
    }

    public static SortedLinkedListUsingComposition<String> createForStrings(Comparator<String> comparator) {
        return new SortedLinkedListUsingComposition<>(comparator);
    }

    /**
     * Inserts the specified element into the list and sorts the list to maintain sorted order.
     * Duplicate values are allowed and will be kept in order of insertion among equals.
     *
     * @param e element to be inserted (must not be null)
     * @return true if the list was modified as a result of this call
     * @throws NullPointerException if the specified element is null
     */
    @Override
    public boolean add(E e) {
        Objects.requireNonNull(e);
        boolean result = linkedList.add(e);
        if (result) {
            linkedList.sort(comparator);
        }
        return result;
    }

    /**
     * Inserts all elements from the specified collection into the list, then sorts the list to maintain sorted order.
     * The input collection may contain duplicate values, which will be preserved.
     *
     * @param c collection containing elements to be added (must not be null or contain nulls)
     * @return true if the list was modified as a result of this call
     * @throws NullPointerException if the collection or any of its elements is null
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }

        // Can not use c.contains(null), the check itself could throw a NullPointerException
        c.forEach(Objects::requireNonNull);

        boolean result = linkedList.addAll(c);
        if (result) {
            linkedList.sort(comparator);
        }
        return result;
    }

    /**
     * @see LinkedList#size()
     */
    @Override
    public int size() {
        return linkedList.size();
    }

    /**
     * @see LinkedList#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    /**
     * @see LinkedList#contains(Object)
     */
    @Override
    public boolean contains(Object o) {
        return linkedList.contains(o);
    }

    /**
     * @see LinkedList#iterator()
     */
    @Override
    public Iterator<E> iterator() {
        return linkedList.iterator();
    }

    /**
     * @see LinkedList#toArray()
     */
    @Override
    public Object[] toArray() {
        return linkedList.toArray();
    }

    /**
     * @see LinkedList#toArray(Object[])
     */
    @Override
    public <T> T[] toArray(T[] a) {
        return linkedList.toArray(a);
    }

    /**
     * @see LinkedList#remove(Object)
     */
    @Override
    public boolean remove(Object o) {
        return linkedList.remove(o);
    }

    /**
     * @see LinkedList#containsAll(Collection)
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        return linkedList.containsAll(c);
    }

    /**
     * @see LinkedList#removeAll(Collection)
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        return linkedList.removeAll(c);
    }

    /**
     * @see LinkedList#retainAll(Collection)
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        return linkedList.retainAll(c);
    }

    /**
     * @see LinkedList#clear()
     */
    @Override
    public void clear() {
        linkedList.clear();
    }

    /**
     * @see LinkedList#reversed()
     */
    @Override
    public SequencedCollection<E> reversed() {
        return linkedList.reversed();
    }

    /**
     * @see LinkedList#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SortedLinkedListUsingComposition<?> that = (SortedLinkedListUsingComposition<?>) o;
        return linkedList.equals(that.linkedList);
    }

    /**
     * @see LinkedList#hashCode()
     */
    @Override
    public int hashCode() {
        return linkedList.hashCode();
    }

    /**
     * @see LinkedList#toString()
     */
    @Override
    public String toString() {
        return linkedList.toString();
    }
}
