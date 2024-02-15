# Collections API Lab Discussion
### Jason Qiu (jq48), Judy He (yh381), Jeremyah Flowers (jtf45), Jordan Haytaian (jeh120)
### Team 3

## In your experience using these collections, are they hard or easy to use?
Our group has collectively used sets, lists, and maps.  We have generally found them easy to use.  In the past, we have have some difficulty understanding the inheritance relationships and dealing with debugging lists. 

Linked lists are the least intuitive and least used.

## In your experience using these collections, do you feel mistakes are easy to avoid?
Removing elements as you are iterating through the list is not very inuitive since you have to make a iterator or gather them in a separate collection first and then remove them.

Otherwise, mistakes are very easy to avoid and the functions are very straightforward.


## What methods are common to all collections (except Maps)?
add, remove, iterator, contains, isEmpty, size, addAll, removeAll, sort, clear, toArray, removeIf, etc..

## What methods are common to all Deques?
add/remove/get/offer/poll/peek + first/last

as well as all of the collections methods

## What is the purpose of each interface implemented by LinkedList?

List
- so that you can use linked list as a regular list to add/remove from

Deque
- so that you can use a doubly-linked list as a deque you can add/remove to/from either end

Cloneable
- to clone the linked list

Serializable
- the purpose is to make the linked list serializable

Queue

Collection

## How many different implementations are there for a Set?
AbstractSet, ConcurrentHashMap.KeySetView, ConcurrentSkipListSet, CopyOnWriteArraySet, EnumSet, HashSet, JobStateReasons, LinkedHashSet, TreeSet


## What is the purpose of each superclass of PriorityQueue?
>java.lang.Object
>java.util.AbstractCollection<E>
>java.util.AbstractQueue<E>
>java.util.PriorityQueue<E>
AbstractQueue describes the functionality that any queue would have.
AbstractCollection describes any funcitonality that a collection would have

## What is the purpose of the Collections utility class?
"Array Utilities
Arrays - Contains static methods to sort, search, compare, hash, copy, resize, convert to String, and fill arrays of primitives and objects."
    - Do stuff on arrays


## API Characterics applied to Collections API

 * Easy to learn
    - The Collections API is relatively easy to learn as each the methods are intuitive and accessible.
    - One potential learning curve is understanding the different interfaces it contains (ex: List, Map, Set, Deque...etc), how they are diffferent and when to use thme. 

 * Encourages extension
    
    - Provides interfaces describing different behaviors any potential collection could have.
    - Provides abstract classes for types of widely used collections that can be extended for more specific use cases
    - A lot of documentation for how to extend collections in all sorts of ways

 * Leads to readable code
    - The methods are well named and describe the functionality being performed
    - Provided abstractions: complicated algorithms (such as sorting) are done behind the scenes

 * Hard to misuse
    - For the most part, the Collections API are hard to misuse in that they do not allow for unneccessary intimacy or 
        for conflicting value to be added or important data to be modified outside the interface.
