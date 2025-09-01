/* 
Time Complexity: 
  - get: O(1) average (hash map + O(1) DLL operations)
  - put: O(1) average (hash map + O(1) DLL operations)

Space Complexity: O(capacity) for the map + doubly linked list nodes

Did this code successfully run on Leetcode: Yes
*/

// Approach:
// - Combine a HashMap (key -> node) with a doubly linked list ordered by recency.
// - Most-recently used (MRU) is near the head; least-recently used (LRU) near the tail.
// - get(key): if present, move the node to head and return value; else return -1.
// - put(key, val): if key exists, update value and move node to head;
//   otherwise create a new node, add to head, and if size exceeds capacity, evict tail.prev (LRU).
// - All list ops (remove/add/move) are O(1).


import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    class Node{
        int key, value;
        Node prev, next;

        Node(int key, int value){
            this.key = key;
            this.value = value;
        }
    }

    private int capacity;
    private Map<Integer, Node> cache;
    private Node head, tail;

    public LRUCache(int capacity){
        this.capacity = capacity;
        cache = new HashMap<>();

        head = new Node(-1, -1);
        tail = new Node(-1, -1);

        head.next = tail;
        tail.prev = head;
    }

    public int get(int key){
        if(!cache.containsKey(key)){
            return -1;
        }

        Node node = cache.get(key);
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value){
        if(cache.containsKey(key)){
            Node node = cache.get(key);
            node.value = value;
            moveToHead(node);
        }else{
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            addToHeadNode(newNode);

            if(cache.size() > capacity){
                removeLRU();
            }
        }
    }

    private void moveToHead(Node node){
        removeNode(node);
        addToHeadNode(node);
    }

    private void removeNode(Node node){
        node.next.prev = node.prev;
        node.prev.next = node.next;
    }

    private void addToHeadNode(Node node){
        node.next = head.next;
        node.prev = head;
        node.next.prev = node;
        head.next = node;
    }

    private void removeLRU(){
        Node lru = tail.prev;
        removeNode(lru);
        cache.remove(lru.key);
    }
}
