package practico_04_grafos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/*
Ejercicio 1
Implemente en JAVA las clases GrafoDirigido y GrafoNoDirigido.
*/

/*
Ejercicio 8
Se dispone de un conjunto de tareas, donde cada tarea tiene un nombre, una descripción y
una duración (medida en horas). Se sabe también que hay una dependencia en el orden
posible en el cual se pueden ejecutar estas tareas y un tiempo de espera entre dos tareas
consecutivas (también medido en horas). Por ejemplo, si la tarea B depende de la tarea A y
tiene un tiempo de espera de 5 horas; significa que:

• B no puede ejecutarse antes que A y,
• B debe ejecutarse 5 horas después de haber finalizado la ejecución de A.

Objetivo

Implementar un algoritmo que obtenga la secuencia de ejecución crítica de estas tareas, es
decir, la secuencia de tareas que resulta en el máximo tiempo empleado para su ejecución.

Por ejemplo: si partimos de la siguiente configuración podemos encontrar el camino crítico en
la secuencia de tareas [0, 2, 5, 6, 10], ya que su tiempo de ejecución es la duración de cada
tarea más el tiempo de espera entre cada par de tareas: 70 horas.
*/

public class GrafoDirigido<T> implements Grafo<T> {
    private HashMap<Integer, HashSet<Integer>> grafo;
    private HashMap<Integer, HashMap<Integer, T>> etiquetas;

    public GrafoDirigido() {
        this.grafo = new HashMap();
        this.etiquetas = new HashMap();
    }
    @Override
    public void agregarVertice(int verticeId) {
        if (grafo.containsKey(verticeId)) {
            System.out.println("El vértice " + verticeId + " ya se encuentra agregado.");
        } else {
            grafo.put(verticeId, new HashSet<Integer>());
        }
    }

    @Override
    public void borrarVertice(int verticeId) {
        // Obtener el conjunto de adyacentes del vértice a eliminar
        HashSet<Integer> vecinos = grafo.get(verticeId);

        // Salir del método si el vértice no existe
        if (vecinos == null) {
            return;
        }

        // Eliminar el vértice de los conjuntos de adyacentes de los demás vértices
        for (int vecino : vecinos) {
            HashSet<Integer> conjunto = grafo.get(vecino);
            conjunto.remove(verticeId);
        }

        // Eliminar el vértice del grafo
        grafo.remove(verticeId);
    }

    @Override
    public void agregarArco(int verticeId1, int verticeId2, T etiqueta) {
        if (!grafo.containsKey(verticeId1) || !grafo.containsKey(verticeId2)) {
            System.out.println("El grafo no contiene al menos uno de los vértices.");
        } else {
            grafo.get(verticeId1).add(verticeId2);
        }

        if (!etiquetas.containsKey(verticeId1)) {
            etiquetas.put(verticeId1, new HashMap<Integer, T>());
            etiquetas.get(verticeId1).put(verticeId2, etiqueta);
        }
    }

    @Override
    public void borrarArco(int verticeId1, int verticeId2) {
        if (!grafo.containsKey(verticeId1) || !grafo.containsKey(verticeId2)) {
            System.out.println("El grafo no contiene al menos uno de los vértices.");
        } else {
            // Eliminar verticeId2 de la lista de adyacencia de verticeId1
            grafo.get(verticeId1).remove(verticeId2);

            // Eliminar etiqueta correspondiente, si existe
            if (etiquetas.containsKey(verticeId1) && etiquetas.get(verticeId2).containsKey(verticeId2)) {
                etiquetas.get(verticeId1).remove(verticeId2);
            }
        }
    }

    @Override
    public boolean contieneVertice(int verticeId) {
        return grafo.containsKey(verticeId);
    }

    @Override
    public boolean existeArco(int verticeId1, int verticeId2) {
        if (!grafo.containsKey(verticeId1) || !grafo.containsKey(verticeId2)) {
            System.out.println("El grafo no contiene al menos uno de los vértices.");
        } else {
            HashSet<Integer> vecinosVerticeId1 = grafo.get(verticeId1);

            if (vecinosVerticeId1.contains(verticeId2)) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    @Override
    public Arco<T> obtenerArco(int verticeId1, int verticeId2) {
        if (!grafo.containsKey(verticeId1) || !grafo.containsKey(verticeId2)) {
            return null;
        } else if (existeArco(verticeId1, verticeId2)) {
            T etiqueta = etiquetas.get(verticeId1).get(verticeId2);
            Arco<T> arco = new Arco<T>(verticeId1, verticeId2, etiqueta);

            return arco;
        }

        return null;
    }

    @Override
    public int cantidadVertices() {
        return 0;
    }

    @Override
    public int cantidadArcos() {
        return grafo.size();
    }

    @Override
    public Iterator<Integer> obtenerVertices() {
        Iterator<Integer> iterador = grafo.keySet().iterator();
        return iterador;
    }

    @Override
    public Iterator<Integer> obtenerAdyacentes(int verticeId) {
        HashSet<Integer> vecinos = grafo.get(verticeId);
        Iterator<Integer> iteradorVecinos = vecinos.iterator();
        return iteradorVecinos;
    }

    @Override
    public Iterator<Arco<T>> obtenerArcos() {
        HashSet<Arco<T>> arcos = new HashSet<Arco<T>>();

        for (Integer origen : grafo.keySet()) {
            for (Integer destino : grafo.get(origen)) { // keySet devuelve las claves del grafo
                if (etiquetas.containsKey(origen) && etiquetas.get(origen).containsKey(destino)) {
                    T etiqueta = etiquetas.get(origen).get(destino);
                    arcos.add(new Arco<T>(origen, destino, etiqueta));
                }
            }
        }

        return arcos.iterator();
    }

    @Override
    public Iterator<Arco<T>> obtenerArcos(int verticeId) {
        HashSet<Arco<T>> arcosVertice = new HashSet<Arco<T>>();

        for(Integer destino : grafo.get(verticeId)) {
            if(etiquetas.containsKey(verticeId) && etiquetas.get(verticeId).containsKey(destino)) {
                T etiqueta = etiquetas.get(verticeId).get(destino);
                arcosVertice.add(new Arco<T>(verticeId,destino,etiqueta));
            }
        }

        return arcosVertice.iterator();
    }
}
