package com.example;

import java.util.SequencedCollection;

/**
 * The {@code SortedLinkedList} interface represents a linked list that always maintains its elements in sorted order.
 * Elements are sorted either by their natural ordering or by a provided {@link java.util.Comparator}.
 * <p>
 * Features:
 * <ul>
 *   <li>Elements are always kept sorted; insertion at a specific index is not supported.</li>
 *   <li>Duplicate values are allowed, and their insertion order is preserved among equals.</li>
 *   <li>Sorting order is defined at construction and cannot be changed later.</li>
 *   <li>Null elements are not supported (insertion throws {@link NullPointerException}).</li>
 *   <li>Extends {@link SequencedCollection} to provide access to the first and last element.</li>
 * </ul>
 * <p>
 * Implementations:
 * <ul>
 *   <li>{@link SortedLinkedListFromScratch} - custom singly linked list implementation</li>
 *   <li>{@link SortedLinkedListUsingComposition} - uses Java's {@link java.util.LinkedList} for storage</li>
 * </ul>
 *
 * @param <E> the type of elements, must implement {@link Comparable}
 */
public sealed interface SortedLinkedList<E extends Comparable<E>>
        extends SequencedCollection<E>
        permits SortedLinkedListFromScratch, SortedLinkedListUsingComposition {
}
