package practico_03;

import java.util.ArrayList;
import java.util.List;

/*
Ejercicio 1.
Implemente la estructura de Árbol Binario para búsquedas.
Métodos:
    • Integer getRoot(), boolean hasElem(Integer), boolean isEmpty(), void insert(Integer),
    boolean delete(Integer), int getHeight(), void printPosOrder(), void printPreOrder(), void
    printInOrder(), List getLongestBranch(), List getFrontera(), Integer getMaxElem(), List
    getElemAtLevel(int)

    1. ¿Cuál es la complejidad de cada uno de estos métodos?
 */

public class BinaryTree {
    private TreeNode root;

    public BinaryTree() {
        this.root = null;
    }

    // Complejidad O(1), simplemente devuelve el valor de la raíz del árbol.
    public Integer getRoot() {
        if (this.root == null) {
            return null;
        }

        return this.root.getValue();
    }

    //  En el peor caso, la complejidad es O(h), donde h es la altura del árbol.
    //  En un árbol balanceado, esto sería O(log n), pero en un árbol desbalanceado podría ser O(n).
    public boolean hasElem(Integer value) {
        return hasElem(this.root, value);
    }

    private boolean hasElem(TreeNode node, Integer value) {
        if (node == null) {
            return false;
        }

        if (node.getValue().equals(value)) {
            return true;
        }

        if (value < node.getValue()) {
            return hasElem(node.getLeft(), value);
        } else {
            return hasElem(node.getRight(), value);
        }
    }

    // Complejidad O(1), simplemente verifica si la raíz es nula.
    public boolean isEmpty() {
        return this.root == null;
    }

    // En el peor caso, la complejidad es O(n).
    public void insert(Integer value) {
        if (this.root == null)
            this.root = new TreeNode(value);
        else
            this.insert(this.root, value);
    }

    private void insert(TreeNode node, Integer value) {
        if (node.getValue() > value) {
            if (node.getLeft() == null) {
                TreeNode temp = new TreeNode(value);
                node.setLeft(temp);
            } else {
                insert(node.getLeft(),value);
            }
        } else if (node.getValue() < value) {
            if (node.getRight() == null) {
                TreeNode temp = new TreeNode(value);
                node.setRight(temp);
            } else {
                insert(node.getRight(),value);
            }
        }
    }

    // En el peor caso, la complejidad es O(n).
    public boolean delete(Integer value) {
        if (value != null) {
            this.root = delete(this.root, value);
            return true;
        }

        return false;
    }

    private TreeNode delete(TreeNode node, Integer value) {
        if (value < node.getValue()) {
            node.setLeft(delete(node.getLeft(), value));
        } else if (value > node.getValue()) {
            node.setRight(delete(node.getRight(), value));
        } else {
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            }

            TreeNode minValueNode = findMinValueNode(node.getRight());
            node.setValue(minValueNode.getValue());
            node.setRight(delete(node.getRight(), minValueNode.getValue()));
        }

        return node;
    }

    private TreeNode findMinValueNode(TreeNode node) {
        TreeNode current = node;

        while (current.getLeft() != null) {
            current = current.getLeft();
        }

        return current;
    }

    // La complejidad es O(n), ya que recorre todos los nodos del árbol.
    public int getHeight() {
        return getHeight(this.root);
    }

    private int getHeight(TreeNode node) {
        if (node == null)
            return 0;
        else {
            int leftHeight = getHeight(node.getLeft());
            int rightHeight = getHeight(node.getRight());

            return Math.max(leftHeight, rightHeight) + 1;
        }
    }

    // La complejidad es O(n), ya que recorre todos los nodos del árbol.
    public void printPosOrder() {
        printPosOrder(this.root);
        System.out.println();
    }

    private void printPosOrder(TreeNode node) {
        if (node != null) {
            printPosOrder(node.getLeft());
            printPosOrder(node.getRight());
            System.out.print(node.getValue() + " ");
        }
    }

    // La complejidad es O(n), ya que recorre todos los nodos del árbol.
    public void printPreOrder() {
        printPreOrder(this.root);
        System.out.println();
    }

    private void printPreOrder(TreeNode node) {
        if (node != null) {
            System.out.print(node.getValue() + " ");
            printPreOrder(node.getLeft());
            printPreOrder(node.getRight());
        }
    }

    // La complejidad es O(n), ya que recorre todos los nodos del árbol.
    public void printInOrder() {
        printInOrder(this.root);
        System.out.println();
    }

    private void printInOrder(TreeNode node) {
        if (node != null) {
            printInOrder(node.getLeft());
            System.out.print(node.getValue() + " ");
            printInOrder(node.getRight());
        }
    }

    // La complejidad es O(n), ya que recorre todos los nodos del árbol.
    public List<Integer> getLongestBranch() {
        List<Integer> longestBranch = new ArrayList<>();
        getLongestBranch(root, longestBranch);
        return longestBranch;
    }

    private void getLongestBranch(TreeNode node, List<Integer> longestBranch) {
        if (node != null) {
            longestBranch.add(node.getValue());

            int leftHeight = getHeight(node.getLeft());
            int rightHeight = getHeight(node.getRight());

            if (leftHeight >= rightHeight) {
                getLongestBranch(node.getLeft(), longestBranch);
            } else {
                getLongestBranch(node.getRight(), longestBranch);
            }
        }
    }

    // La complejidad es O(n), ya que recorre todos los nodos del árbol.
    public List<Integer> getFrontera() {
        List<Integer> frontera = new ArrayList<>();
        getFrontera(this.root, frontera);
        return frontera;
    }

    private void getFrontera(TreeNode node, List<Integer> frontera) {
        if (node != null) {
            if (node.getLeft() == null && node.getRight() == null) {
                frontera.add(node.getValue());
            }

            getFrontera(node.getLeft(), frontera);
            getFrontera(node.getRight(), frontera);
        }
    }

    // La complejidad es O(h) en el peor caso, ya que desciende por la rama derecha del árbol hasta
    // llegar al nodo más a la derecha.
    public Integer getMaxElem() {
        if (this.root == null) {
            return null;
        }

        TreeNode current = this.root;

        while (current.getRight() != null) {
            current = current.getRight();
        }

        return current.getValue();
    }

    // En el peor de los casos, la complejidad es O(n), ya que recorre todos los nodos del árbol
    // hasta encontrar el nivel dado.
    public List<Integer> getElemAtLevel(int level) {
        List<Integer> elements = new ArrayList<>();
        getElemAtLevel(this.root, level, elements, 1);
        return elements;
    }

    private void getElemAtLevel(TreeNode node, int level, List<Integer> elements, int currentLevel) {
        if (node != null) {
            if (currentLevel == level) {
                elements.add(node.getValue());
            } else {
                getElemAtLevel(node.getLeft(), level, elements, currentLevel + 1);
                getElemAtLevel(node.getRight(), level, elements, currentLevel + 1);
            }
        }
    }
}
