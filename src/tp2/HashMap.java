package tp2;

public class HashMap<KeyType, DataType> {

    private static final int DEFAULT_CAPACITY = 20;
    private static final float DEFAULT_LOAD_FACTOR = 0.5f;
    private static final int CAPACITY_INCREASE_FACTOR = 2;

    private Node<KeyType, DataType>[] map;
    private int size = 0;
    private int capacity;
    private final float loadFactor; // Compression factor

    /**
     * Constructeur par défaut
     */
    public HashMap() { this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR); }

    /**
     * Constructeur par parametre
     * @param initialCapacity
     */
    public HashMap(int initialCapacity) {
        this(initialCapacity > 0 ? initialCapacity : DEFAULT_CAPACITY,
                DEFAULT_LOAD_FACTOR);
    }

    /**
     * Constructeur par parametres
     * @param initialCapacity
     * @param loadFactor
     */
    public HashMap(int initialCapacity, float loadFactor) {
        capacity = initialCapacity;
        this.loadFactor = 1 / loadFactor;
        map = new Node[capacity];
    }

    /**
     * Finds the index attached to a particular key
     * This is the hashing function ("Fonction de dispersement")
     * @param key Value used to access to a particular instance of a DataType within map
     * @return Index value where this key should be placed in attribute map
     */
    private int hash(KeyType key){
        int keyHash = key.hashCode() % capacity;
        return Math.abs(keyHash);
    }

    /**
     * @return if map should be rehashed
     */
    private boolean needRehash() { return size * loadFactor > capacity; }

    /**
     * @return Number of elements currently in the map
     */
    public int size() { return size; }

    /**
     * @return Current reserved space for the map
     */
    public int capacity(){ return capacity; }

    /**
     * @return if map does not contain any element
     */
    public boolean isEmpty() { return size == 0; }

    /** TODO
     * Increases capacity by CAPACITY_INCREASE_FACTOR (multiplication) and
     * reassigns all contained values within the new map
     */
    private void rehash() {
        capacity = capacity * CAPACITY_INCREASE_FACTOR;
        Node<KeyType, DataType>[] oldMap = map;
        clear();
        for(int i = 0; i < oldMap.length; ++i){
            if(oldMap[i] != null){
                put(oldMap[i].key, oldMap[i].data);
                if(oldMap[i].next != null){
                    Node<KeyType, DataType> nextNode = oldMap[i].next;
                    while(nextNode != null){
                        put(nextNode.key, nextNode.data);
                        nextNode = nextNode.next;
                    }
                }
            }
        }

    }

    /** TODO
     * Finds if map contains a key
     * @param key Key which we want to know if exists within map
     * @return if key is already used in map
     */
    public boolean containsKey(KeyType key) {
        int hashVal = hash(key);
        if(map[hashVal] != null){
            Node<KeyType, DataType> node = map[hashVal];
            while(!node.key.equals(key) && node.next != null)
                node = node.next;
            if(node.key.equals(key))
                return true;
            if(node.next == null)
                return false;
        }

        return false;
    }

    /** TODO
     * Finds the value attached to a key
     * @param key Key which we want to have its value
     * @return DataType instance attached to key (null if not found)
     */
    public DataType get(KeyType key) {
        int hashVal = hash(key);
        if(map[hashVal] == null)
            return null;
        if(map[hashVal].key.equals(key))
            return map[hashVal].data;
        if(!(map[hashVal].key.equals(key)) && map[hashVal].next != null){
            Node<KeyType, DataType> node = map[hashVal].next;
            while (!(node.key.equals(key)) && node.next != null)
                node = node.next;
            if(node.key.equals(key))
                return node.data;
            if(node.next == null)
                return null;
        }
        return null;
    }

    /**TODO
     * Assigns a value to a key
     * @param key Key which will have its value assigned or reassigned
     * @return Old DataType instance at key (null if none existed)
     */
    public DataType put(KeyType key, DataType value) {
        DataType removedData = null;
        if(containsKey(key))
            removedData = remove(key);
        int hashVal = hash(key);
        Node<KeyType, DataType> newNode = new Node(key, value);
        if(map[hashVal] == null)
            map[hashVal] = newNode;
        else{
            Node<KeyType, DataType> node = map[hashVal];
            while(node.next != null)
                node = node.next;
            node.next = newNode;
        }
        ++size;
        if(needRehash())
            rehash();
        return removedData;
    }

    /**TODO
     * Removes the node attached to a key
     * @param key Key which is contained in the node to remove
     * @return Old DataType instance at key (null if none existed)
     */
    public DataType remove(KeyType key) {
        DataType removedData = null;
        int hashVal = hash(key);
        if(map[hashVal] != null && map[hashVal].key.equals(key)){
            removedData = map[hashVal].data;
            if(map[hashVal].next != null)
                map[hashVal] = map[hashVal].next;
            else
                map[hashVal] = null;
            --size;
            return removedData;

        }
        else if(map[hashVal] != null && !(map[hashVal].key.equals(key)) && map[hashVal].next != null){
            Node<KeyType, DataType> node = map[hashVal].next;
            // Find the node to remove
            while (node.next != null && !(node.key.equals(key)))
                node = node.next;

            if(!(node.key.equals(key)))
                return null;
            removedData = node.data;

            // Remove the node while taking in consideration that the node may have a previous and a next node
            Node<KeyType, DataType> previousNode = map[hashVal];
            while(previousNode.next != node)
                previousNode = previousNode.next;
            previousNode.next = node.next;

            --size;
            return removedData;
        }

        return removedData;
    }

    /**TODO
     * Removes all nodes contained within the map
     */
    public void clear() {
        map = new Node[capacity];
        size = 0;
    }

    /**
     * Definition et implementation de la classe Node
     */
    static class Node<KeyType, DataType> {
        final KeyType key;
        DataType data;
        Node<KeyType, DataType> next; // Pointer to the next node within a Linked List

        Node(KeyType key, DataType data)
        {
            this.key = key;
            this.data = data;
            next = null;
        }
    }
}