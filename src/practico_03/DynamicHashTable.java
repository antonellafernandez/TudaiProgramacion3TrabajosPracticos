package practico_03;

import java.util.ArrayList;
import java.util.LinkedList;

public class DynamicHashTable {
    private int M;
    private int threshold;
    private ArrayList<LinkedList<Integer>> table;

    public DynamicHashTable(int M, double loadFactor) {
        this.M = M;
        this.threshold = (int) (M * loadFactor);
        table = new ArrayList<>(M);
        for (int i = 0; i < M; i++) {
            table.add(new LinkedList<>());
        }
    }

    private int hash(int key) {
        return key % M;
    }

    public void insert(int key) {
        int index = hash(key);
        table.get(index).add(key);

        if (table.get(index).size() > threshold) {
            resizeTable();
        }
    }

    private void resizeTable() {
        M *= 2;
        threshold = (int) (M * 0.9);

        ArrayList<LinkedList<Integer>> newTable = new ArrayList<>(M);

        for (int i = 0; i < M; i++) {
            newTable.add(new LinkedList<>());
        }

        for (LinkedList<Integer> list : table) {
            for (int num : list) {
                int index = num % M;
                newTable.get(index).add(num);
            }
        }

        table = newTable;
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
