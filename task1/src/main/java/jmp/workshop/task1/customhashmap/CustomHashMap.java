package jmp.workshop.task1.customhashmap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Author: Bakhodirjon_Marupov
 * Date: 22/06/2022
 */
public class CustomHashMap<K, V> {

    private int capacity = 16; //Initial default capacity

    private Entry<K, V>[] table; //Array of Entry object

    private final Object lock = new Object();

    public CustomHashMap() {
        table = new Entry[capacity];
    }

    public CustomHashMap(int capacity) {
        this.capacity = capacity;
        table = new Entry[capacity];
    }

    public void put(K key, V value) {
        synchronized (lock) {
            int index = index(key);
            Entry newEntry = new Entry(key, value, null);
            if (table[index] == null) {
                table[index] = newEntry;
            } else {
                Entry<K, V> previousNode = null;
                Entry<K, V> currentNode = table[index];
                while (currentNode != null) {
                    if (currentNode.getKey().equals(key)) {
                        currentNode.setValue(value);
                        break;
                    }
                    previousNode = currentNode;
                    currentNode = currentNode.getNext();
                }
                if (previousNode != null)
                    previousNode.setNext(newEntry);
            }
        }
    }

    public V get(K key) {
        synchronized (lock) {
            V value = null;
            int index = index(key);
            Entry<K, V> entry = table[index];
            while (entry != null) {
                if (entry.getKey().equals(key)) {
                    value = entry.getValue();
                    break;
                }
                entry = entry.getNext();
            }
            return value;
        }
    }

    public void remove(K key) {
        synchronized (lock) {
            int index = index(key);
            Entry previous = null;
            Entry entry = table[index];
            while (entry != null) {
                if (entry.getKey().equals(key)) {
                    if (previous == null) {
                        entry = entry.getNext();
                        table[index] = entry;
                        return;
                    } else {
                        previous.setNext(entry.getNext());
                        return;
                    }
                }
                previous = entry;
                entry = entry.getNext();
            }
        }
    }

    public List<V> values() {
        synchronized (lock) {
            List<V> values = new ArrayList<>();
            for (Entry<K, V> kvEntry : table) {
                Optional.ofNullable(kvEntry)
                        .ifPresent(e -> values.add(e.getValue()));
            }
            return values;
        }
    }

    public void display() {
        synchronized (lock) {
            for (int i = 0; i < capacity; i++) {
                if (table[i] != null) {
                    Entry<K, V> currentNode = table[i];
                    while (currentNode != null) {
                        System.out.println(String.format("Key is %s and value is %s",
                                currentNode.getKey(),
                                currentNode.getValue()));
                        currentNode = currentNode.getNext();
                    }
                }
            }
        }
    }

    private int index(K key) {
        if (key == null) {
            return 0;
        }
        return Math.abs(key.hashCode() % capacity);
    }
}
