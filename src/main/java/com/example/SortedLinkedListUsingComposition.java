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

    @Override
    public boolean add(E e) {
        Objects.requireNonNull(e);
        boolean result = linkedList.add(e);
        if (result) {
            linkedList.sort(comparator);
        }
        return result;
    }

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

    @Override
    public int size() {
        return linkedList.size();
    }

    @Override
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return linkedList.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return linkedList.iterator();
    }

    @Override
    public Object[] toArray() {
        return linkedList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return linkedList.toArray(a);
    }

    @Override
    public boolean remove(Object o) {
        return linkedList.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return linkedList.containsAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return linkedList.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return linkedList.retainAll(c);
    }

    @Override
    public void clear() {
        linkedList.clear();
    }

    @Override
    public SequencedCollection<E> reversed() {
        return linkedList.reversed();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        SortedLinkedListUsingComposition<?> that = (SortedLinkedListUsingComposition<?>) o;
        return linkedList.equals(that.linkedList);
    }

    @Override
    public int hashCode() {
        return linkedList.hashCode();
    }

    @Override
    public String toString() {
        return linkedList.toString();
    }
}
