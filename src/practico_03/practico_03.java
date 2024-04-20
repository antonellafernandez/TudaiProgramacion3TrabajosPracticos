package practico_03;

import java.util.List;

public class practico_03 {
    public static void main(String[] args) {
        // Ejercicio 2
        BinaryTree bt = new BinaryTree();

        // Insertar algunos valores en el árbol
        bt.insert(6);
        bt.insert(2);
        bt.insert(10);
        bt.insert(1);
        bt.insert(4);
        bt.insert(8);
        bt.insert(11);
        bt.insert(7);
        bt.insert(9);

        // Imprimir la suma de los nodos internos
        System.out.println("Suma de los nodos internos: " + bt.sumInternalNodes());

        // Ejercicio 3
        int k = 8;
        List<Integer> leaves = bt.findLeavesGreaterThanK(k);
        System.out.println("Valores de las hojas mayores que " + k + ": " + leaves);
    }
}