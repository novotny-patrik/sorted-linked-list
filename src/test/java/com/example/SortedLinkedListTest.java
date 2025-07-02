package com.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.SequencedCollection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"SuspiciousMethodCalls", "DataFlowIssue", "ConstantValue"})
class SortedLinkedListTest {

    static Stream<SortedLinkedList<Integer>> integerListProvider() {
        return Stream.of(
                SortedLinkedListUsingComposition.createForIntegers(),
                SortedLinkedListFromScratch.createForIntegers()
        );
    }

    static Stream<SortedLinkedList<String>> stringListProvider() {
        return Stream.of(
                SortedLinkedListUsingComposition.createForStrings(),
                SortedLinkedListFromScratch.createForStrings()
        );
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void add_singleInteger_shouldContainElement(SortedLinkedList<Integer> list) {
        assertTrue(list.add(5));
        assertEquals(1, list.size());
        assertArrayEquals(new Object[]{5}, list.toArray());
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void add_multipleIntegers_shouldBeSorted(SortedLinkedList<Integer> list) {
        list.add(5);
        list.add(3);
        list.add(8);
        assertEquals(3, list.size());
        assertArrayEquals(new Object[]{3, 5, 8}, list.toArray());
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void add_duplicateIntegers_shouldAllowDuplicates(SortedLinkedList<Integer> list) {
        list.add(5);
        list.add(3);
        list.add(8);
        list.add(3);
        assertEquals(4, list.size());
        assertArrayEquals(new Object[]{3, 3, 5, 8}, list.toArray());
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void addAll_multipleIntegers_shouldBeSorted(SortedLinkedList<Integer> list) {
        assertTrue(list.addAll(List.of(5, 3, 8, 3)));
        assertEquals(4, list.size());
        assertArrayEquals(new Object[]{3, 3, 5, 8}, list.toArray());
    }

    @ParameterizedTest
    @MethodSource("stringListProvider")
    void add_singleString_shouldContainElement(SortedLinkedList<String> list) {
        assertTrue(list.add("b"));
        assertEquals(1, list.size());
        assertArrayEquals(new Object[]{"b"}, list.toArray());
    }

    @ParameterizedTest
    @MethodSource("stringListProvider")
    void add_multipleStrings_shouldBeSorted(SortedLinkedList<String> list) {
        list.add("b");
        list.add("a");
        list.add("c");
        assertEquals(3, list.size());
        assertArrayEquals(new Object[]{"a", "b", "c"}, list.toArray());
    }

    @ParameterizedTest
    @MethodSource("stringListProvider")
    void add_duplicateStrings_shouldAllowDuplicates(SortedLinkedList<String> list) {
        list.add("b");
        list.add("a");
        list.add("c");
        list.add("a");
        assertEquals(4, list.size());
        assertArrayEquals(new Object[]{"a", "a", "b", "c"}, list.toArray());
    }

    @ParameterizedTest
    @MethodSource("stringListProvider")
    void addAll_multipleStrings_shouldBeSorted(SortedLinkedList<String> list) {
        assertTrue(list.addAll(List.of("b", "a", "c", "a")));
        assertEquals(4, list.size());
        assertArrayEquals(new Object[]{"a", "a", "b", "c"}, list.toArray());
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void add_nullInteger_shouldThrowException(SortedLinkedList<Integer> list) {
        assertThrows(NullPointerException.class, () -> list.add(null));
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void addAll_nullCollection_shouldThrowException(SortedLinkedList<Integer> list) {
        assertThrows(NullPointerException.class, () -> list.addAll(null));
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void remove_nullInteger_shouldReturnFalse(SortedLinkedList<Integer> list) {
        assertFalse(list.remove(null));
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void contains_nullInteger_shouldReturnFalse(SortedLinkedList<Integer> list) {
        assertFalse(list.contains(null));
    }

    @ParameterizedTest
    @MethodSource("stringListProvider")
    void add_nullString_shouldThrowException(SortedLinkedList<String> list) {
        assertThrows(NullPointerException.class, () -> list.add(null));
    }

    @ParameterizedTest
    @MethodSource("stringListProvider")
    void remove_nullString_shouldReturnFalse(SortedLinkedList<String> list) {
        assertFalse(list.remove(null));
    }

    @ParameterizedTest
    @MethodSource("stringListProvider")
    void contains_nullString_shouldReturnFalse(SortedLinkedList<String> list) {
        assertFalse(list.contains(null));
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void addAll_collectionWithNull_shouldThrowException(SortedLinkedList<Integer> list) {
        List<Integer> input = new ArrayList<>();
        input.add(1);
        input.add(null);
        input.add(2);
        assertThrows(NullPointerException.class, () -> list.addAll(input));
    }

    @ParameterizedTest
    @MethodSource("stringListProvider")
    void addAll_collectionWithNull_shouldThrowException_String(SortedLinkedList<String> list) {
        List<String> input = new ArrayList<>();
        input.add("a");
        input.add(null);
        input.add("b");
        assertThrows(NullPointerException.class, () -> list.addAll(input));
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void remove_existingElement_shouldRemoveAndReturnTrue(SortedLinkedList<Integer> list) {
        list.addAll(List.of(1, 2, 3));
        assertTrue(list.remove(2));
        assertFalse(list.contains(2));
        assertEquals(2, list.size());
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void remove_nonExistingElement_shouldReturnFalse(SortedLinkedList<Integer> list) {
        list.addAll(List.of(1, 2, 3));
        assertFalse(list.remove(99));
        assertEquals(3, list.size());
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void contains_existingElement_shouldReturnTrue(SortedLinkedList<Integer> list) {
        list.addAll(List.of(1, 2, 3));
        assertTrue(list.contains(2));
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void contains_nonExistingElement_shouldReturnFalse(SortedLinkedList<Integer> list) {
        list.addAll(List.of(1, 2, 3));
        assertFalse(list.contains(99));
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void removeAll_someElements_shouldRemoveThem(SortedLinkedList<Integer> list) {
        list.addAll(List.of(1, 2, 3, 4));
        assertTrue(list.removeAll(List.of(2, 4)));
        assertFalse(list.contains(2));
        assertFalse(list.contains(4));
        assertEquals(2, list.size());
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void removeAll_noElements_shouldReturnFalse(SortedLinkedList<Integer> list) {
        list.addAll(List.of(1, 2, 3));
        assertFalse(list.removeAll(List.of(99, 100)));
        assertEquals(3, list.size());
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void containsAll_trueIfAllPresent(SortedLinkedList<Integer> list) {
        list.addAll(List.of(1, 2, 3));
        assertTrue(list.containsAll(List.of(1, 2)));
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void containsAll_falseIfAnyMissing(SortedLinkedList<Integer> list) {
        list.addAll(List.of(1, 2, 3));
        assertFalse(list.containsAll(List.of(1, 4)));
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void containsAll_emptyCollection_shouldReturnTrue(SortedLinkedList<Integer> list) {
        list.addAll(List.of(1, 2, 3));
        assertTrue(list.containsAll(List.of()));
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void removeAll_emptyCollection_shouldReturnFalse(SortedLinkedList<Integer> list) {
        list.addAll(List.of(1, 2, 3));
        assertFalse(list.removeAll(List.of()));
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void removeAll_nullCollection_shouldThrowException(SortedLinkedList<Integer> list) {
        assertThrows(NullPointerException.class, () -> list.removeAll(null));
    }

    @ParameterizedTest
    @MethodSource("stringListProvider")
    void remove_existingString_shouldRemoveAndReturnTrue(SortedLinkedList<String> list) {
        list.addAll(List.of("a", "b", "c"));
        assertTrue(list.remove("b"));
        assertFalse(list.contains("b"));
        assertEquals(2, list.size());
    }

    @ParameterizedTest
    @MethodSource("stringListProvider")
    void remove_nonExistingString_shouldReturnFalse(SortedLinkedList<String> list) {
        list.addAll(List.of("a", "b", "c"));
        assertFalse(list.remove("z"));
        assertEquals(3, list.size());
    }

    @ParameterizedTest
    @MethodSource("stringListProvider")
    void contains_existingString_shouldReturnTrue(SortedLinkedList<String> list) {
        list.addAll(List.of("a", "b", "c"));
        assertTrue(list.contains("b"));
    }

    @ParameterizedTest
    @MethodSource("stringListProvider")
    void contains_nonExistingString_shouldReturnFalse(SortedLinkedList<String> list) {
        list.addAll(List.of("a", "b", "c"));
        assertFalse(list.contains("z"));
    }

    @ParameterizedTest
    @MethodSource("stringListProvider")
    void removeAll_someStrings_shouldRemoveThem(SortedLinkedList<String> list) {
        list.addAll(List.of("a", "b", "c", "d"));
        assertTrue(list.removeAll(List.of("b", "d")));
        assertFalse(list.contains("b"));
        assertFalse(list.contains("d"));
        assertEquals(2, list.size());
    }

    @ParameterizedTest
    @MethodSource("stringListProvider")
    void removeAll_noElements_shouldReturnFalse_String(SortedLinkedList<String> list) {
        list.addAll(List.of("a", "b", "c"));
        assertFalse(list.removeAll(List.of("z", "y")));
        assertEquals(3, list.size());
    }

    @ParameterizedTest
    @MethodSource("stringListProvider")
    void containsAll_trueIfAllPresent_String(SortedLinkedList<String> list) {
        list.addAll(List.of("a", "b", "c"));
        assertTrue(list.containsAll(List.of("a", "b")));
    }

    @ParameterizedTest
    @MethodSource("stringListProvider")
    void containsAll_falseIfAnyMissing_String(SortedLinkedList<String> list) {
        list.addAll(List.of("a", "b", "c"));
        assertFalse(list.containsAll(List.of("a", "d")));
    }

    @ParameterizedTest
    @MethodSource("stringListProvider")
    void containsAll_emptyCollection_shouldReturnTrue_String(SortedLinkedList<String> list) {
        list.addAll(List.of("a", "b", "c"));
        assertTrue(list.containsAll(List.of()));
    }

    @ParameterizedTest
    @MethodSource("stringListProvider")
    void removeAll_emptyCollection_shouldReturnFalse_String(SortedLinkedList<String> list) {
        list.addAll(List.of("a", "b", "c"));
        assertFalse(list.removeAll(List.of()));
    }

    @ParameterizedTest
    @MethodSource("stringListProvider")
    void removeAll_nullCollection_shouldThrowException_String(SortedLinkedList<String> list) {
        assertThrows(NullPointerException.class, () -> list.removeAll(null));
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void retainAll_someElements_shouldRetainOnlyThose(SortedLinkedList<Integer> list) {
        list.addAll(List.of(1, 2, 3, 4, 5));
        boolean modified = list.retainAll(List.of(2, 4, 6));
        assertTrue(modified);
        assertEquals(2, list.size());
        assertArrayEquals(new Object[]{2, 4}, list.toArray());
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void retainAll_allElements_shouldChangeNothing(SortedLinkedList<Integer> list) {
        list.addAll(List.of(1, 2, 3));
        boolean modified = list.retainAll(List.of(1, 2, 3));
        assertFalse(modified);
        assertEquals(3, list.size());
        assertArrayEquals(new Object[]{1, 2, 3}, list.toArray());
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void retainAll_emptyCollection_shouldClearList(SortedLinkedList<Integer> list) {
        list.addAll(List.of(1, 2, 3));
        boolean modified = list.retainAll(List.of());
        assertTrue(modified);
        assertEquals(0, list.size());
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void retainAll_nullCollection_shouldThrowException(SortedLinkedList<Integer> list) {
        list.addAll(List.of(1, 2, 3));
        assertThrows(NullPointerException.class, () -> list.retainAll(null));
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void toArray_shouldReturnCorrectArray(SortedLinkedList<Integer> list) {
        list.addAll(List.of(4, 2, 5));
        Object[] arr = list.toArray();
        assertArrayEquals(new Object[]{2, 4, 5}, arr);
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void toArray_withTypedArray_shouldReturnCorrectArray(SortedLinkedList<Integer> list) {
        list.addAll(List.of(4, 2, 5));
        Integer[] arr = list.toArray(new Integer[0]);
        assertArrayEquals(new Integer[]{2, 4, 5}, arr);
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void iterator_shouldIterateAllElementsInOrder(SortedLinkedList<Integer> list) {
        list.addAll(List.of(4, 2, 5));
        var it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals(2, it.next());
        assertTrue(it.hasNext());
        assertEquals(4, it.next());
        assertTrue(it.hasNext());
        assertEquals(5, it.next());
        assertFalse(it.hasNext());
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void iterator_onEmptyList_shouldHaveNoElements(SortedLinkedList<Integer> list) {
        var it = list.iterator();
        assertFalse(it.hasNext());
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void iterator_nextOnEmptyList_shouldThrowException(SortedLinkedList<Integer> list) {
        var it = list.iterator();
        assertThrows(java.util.NoSuchElementException.class, it::next);
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void clear_shouldRemoveAllElements(SortedLinkedList<Integer> list) {
        list.addAll(List.of(1, 2, 3));
        list.clear();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
        assertArrayEquals(new Object[]{}, list.toArray());
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void isEmpty_onNewList_shouldReturnTrue(SortedLinkedList<Integer> list) {
        assertTrue(list.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void isEmpty_onNonEmptyList_shouldReturnFalse(SortedLinkedList<Integer> list) {
        list.add(1);
        assertFalse(list.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void addAll_emptyCollection_shouldReturnFalseAndNotChangeList(SortedLinkedList<Integer> list) {
        list.addAll(List.of(1, 2));
        boolean result = list.addAll(List.of());
        assertFalse(result);
        assertEquals(2, list.size());
        assertArrayEquals(new Object[]{1, 2}, list.toArray());
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void reversed_shouldReturnElementsInReverseOrder(SortedLinkedList<Integer> list) {
        list.addAll(List.of(1, 2, 3, 4));
        SequencedCollection<Integer> reversed = list.reversed();
        assertIterableEquals(List.of(4, 3, 2, 1), reversed);
    }

    @ParameterizedTest
    @MethodSource("integerListProvider")
    void reversed_onEmptyList_shouldReturnEmptyList(SortedLinkedList<Integer> list) {
        SequencedCollection<Integer> reversed = list.reversed();
        assertTrue(reversed.isEmpty());
    }
}