# sorted-linked-list

## Task Description

Implement a library providing SortedLinkedList
(linked list that keeps values sorted). It should be
able to hold string or int values, but not both. Try to
think about what you'd expect from such library as a
user in terms of usability and best practices, and
apply those.

## Notes

- `Try to think about what you'd expect from such library as a user in terms of usability and best practices:`
    - Documentation
    - Implementation is tested
    - Minimal Maven dependencies
    - Implementing common/expected interfaces (List, Iterable, etc ?)
        - Problem with some methods, that works with indexes, like `get(int index)` or `set(int index, E element)`. Does not make sense for sorted list.
    - Not be able to insert non supported types - compile time check - generics somehow? Problem that I need to handle two types
        - Private constructor, factory methods only for enabled types?
        - Multiple classes (2 Integer and String) extending Abstract Class for different types?
        - Avoid Runtime Exceptions caused by wrong input type if possible
    - There can be duplicate values, but they should be kept in order
    - I can change sorting order - comparator. Due to immutability, make it possible to set it only once, at the beginning.
    - Would be nice to get first and last element
- Not mentioned if single or double linked list.
- According to the naming convention of classes, the implementation should not care about thread safety.
    - If I implement Collection, then I can use for thred safety if needed: `java.util.Collections.synchronizedCollection(java.util.Collection<T>)`

## Interfaces

- List: Can not use. Has methods that work with indexes do not make sense for sorted list. Get by index is not that bad, but inserting at some index is not ok, I wouldn't be able to handle contract
- SequencedCollection
    - It would be handy to be able to use `getFirst` and `getLast` for getting the min and max
    - but uses also `addFirst`, `addLast`, does not make sense for sorted list, by default are not implemented
    - Probably will implement it, but not sure if it is expected
- Collection: ok
- Iterable: ok, inside Collection
- Queue/Dequeue: does not make sense, no FIFO, no LIFO

## Possible solutions:

- Implement by extending LinkedList and overriding methods that can change order (add and addAll) and call sort method after each change
    - Will not use. I decided to not to implement List interface, as it does not make sense for sorted list (methods working with indexes)
- Implement by composing LinkedList and handling methods that can change order and call this.sort() or Collections.sort()
    - Easy to implement
    - I would need to handle all methods that can change order - sort the elements after each add and addAll
    - This would leverage existing proved implementation
    - Too easy as an interview task? Is it expected to implement it from scratch?
- Implement from scratch using Node class and handle sorting by default
    - More complex
    - Code could be probably more efficient if done right.
    - More logic to implement, bigger chances for bugs

## Decision

- Create interface SortedLinkedList extending SequencedCollection (including Collection and Iterable inside)
- SortedLinkedList will not support null elements
- Write tests
- Implement both versions: Using composition and from scratch

## Result

- Implemented two versions of SortedLinkedList:
    - Using composition with LinkedList
    - From scratch using Node class
- In real life I would use the composition one if no special requirements, as it is easier to implement and maintain.