package practico_03_hashing;

import java.util.ArrayList;
import java.util.LinkedList;

public class HashTable {
    private int M;
    private ArrayList<LinkedList<Integer>> table;

    public HashTable(int M) {
        this.M = M;
        this.table = new ArrayList<>(M);

        for (int i = 0; i < M; i++) {
            this.table.add(new LinkedList<>());
        }
    }

    private int hash(int key) {
        return key % M;
    }

    public void insert(int key) {
        int index = hash(key);
        this.table.get(index).add(key);
    }

    public void printTable() {
        for (int i = 0; i < M; i++) {
            System.out.print(i + ": ");
            for (int num : table.get(i)) {
                System.out.print(num + " -> ");
            }
            System.out.println("null");
        }
    }
}
