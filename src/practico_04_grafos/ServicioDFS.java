package practico_04_grafos;

import java.util.*;

/*
Ejercicio 2
Implemente los recorridos Depth-First-Search y Breadth-First-Search.
*/

public class ServicioDFS<T, G extends Grafo<T>> {
    private G grafo;
    private static final int BLANCO = 0;
    private static final int AMARILLO = 1;
    private static final int NEGRO = 2;
    private final HashMap<Integer, Integer> distancias = new HashMap<>();
    private int tiempo;

    public ServicioDFS(G grafo) {
        this.grafo = grafo;
    }

    // Complejidad Computacional O(|V| + |A|) Con Listas de Adyacencia
    public void DFS() {
        HashMap<Integer, Integer> color = new HashMap<Integer, Integer>();
        Iterator<Integer> verticesIterator = grafo.obtenerVertices();

        while (verticesIterator.hasNext()) {
            color.put(verticesIterator.next(), BLANCO);
        }

        tiempo = 0;
        verticesIterator = grafo.obtenerVertices();

        while (verticesIterator.hasNext()) {
            Integer vertice = verticesIterator.next();
            if (color.get(vertice) == BLANCO) {
                DFS_Visit(vertice, color);
            }
        }
    }

    public void DFS_Visit(int vertice, HashMap<Integer, Integer> color) {
        color.put(vertice, AMARILLO);
        tiempo++;

        Iterator<Integer> vecinosIterator = grafo.obtenerAdyacentes(vertice);

        while (vecinosIterator.hasNext()) {
            Integer adyacente = vecinosIterator.next();
            if (color.get(adyacente) == BLANCO) {
                DFS_Visit(adyacente, color);
            } else {
                /*
                Ejercicio 3
                Implemente un algoritmo que determine si un grafo dirigido tiene algún ciclo.
                */
                if (color.get(adyacente) == AMARILLO) {
                    System.out.println("Existe un ciclo.");
                }
            }
        }

        color.put(vertice, NEGRO);
        distancias.put(vertice, tiempo);
    }

    /*
    Ejercicio 4
    Escribir un algoritmo que, dado un grafo dirigido y dos vértices i, j de este grafo, devuelva el
    camino simple (sin ciclos) de mayor longitud del vértice i al vértice j. Puede suponerse que el
    grafo de entrada es acíclico.
    */

    public List<Integer> caminoMasLargo(int inicio, int fin) {
        DFS();
        List<Integer> camino = new ArrayList<>();
        int actual = fin;
        camino.add(actual);

        while (actual != inicio) {
            int mejorVecino = -1;
            int mejorDistancia = -1;
            Iterator<Integer> adyacentesIterator = grafo.obtenerAdyacentes(actual);

            while (adyacentesIterator.hasNext()) {
                int vecino = adyacentesIterator.next();

                if (distancias.containsKey(vecino) && distancias.get(vecino) < distancias.getOrDefault(actual, 0)) {
                    if (distancias.get(vecino) > mejorDistancia) {
                        mejorVecino = vecino;
                        mejorDistancia = distancias.get(vecino);
                    }
                }
            }

            actual = mejorVecino;
            camino.add(actual);
        }

        Collections.reverse(camino);
        return camino;
    }

    public List<Integer> caminoMasLargoEntre(int inicio, int fin) {
        return caminoMasLargo(inicio, fin);
    }

    /*
    Ejercicio 5
    Escriba un algoritmo que dado un grafo G y un vértice v de dicho grafo, devuelva una lista
    con todos los vértices a partir de los cuales exista un camino en G que termine en v.
    */

    public List<Integer> verticesConCaminoHasta(int vertice) {
        DFS();
        List<Integer> verticesConCamino = new ArrayList<>();
        boolean[] visitado = new boolean[grafo.cantidadVertices()];

        DFS(vertice, visitado, verticesConCamino);

        return verticesConCamino;
    }

    private void DFS(int vertice, boolean[] visitado, List<Integer> verticesConCamino) {
        visitado[vertice] = true;

        Iterator<Integer> verticesIterator = grafo.obtenerVertices();
        while (verticesIterator.hasNext()) {
            int adyacente = verticesIterator.next();
            if (!visitado[adyacente]) {
                DFS(adyacente, visitado, verticesConCamino);
                verticesConCamino.add(adyacente);
            }
        }
    }
}
