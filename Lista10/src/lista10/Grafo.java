// package lista10;

import java.util.*;

public class Grafo {

    private final int numeroVertices;
    private final int[][] matrizAdjacencia;

    public Grafo(int vertices) {         //Inicializa todas as posições da matriz de adjacência com valores iguais a 0.
        this.numeroVertices = vertices;
        this.matrizAdjacencia = new int[vertices][vertices];
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                this.matrizAdjacencia[j][i] = 0;
            }
        }
    }

    public void insereArestaNaoOrientada(int vertice1, int vertice2, int peso) {

        this.matrizAdjacencia[vertice1][vertice2] = peso;
        this.matrizAdjacencia[vertice2][vertice1] = peso;
    }

    //Insere uma aresta no grafo - a partir de dois vertices 
    public void insereAresta(int vertice1, int vertice2, int peso) { //insere uma aresta no grafo.
        this.matrizAdjacencia[vertice1][vertice2] = peso;
    }

    //a partir de dois vertices, verifica se existe uma aresta entre tais
    public boolean existeAresta(int vertice1, int vertice2) { //verifica se uma aresta existe no grafo
        return (matrizAdjacencia[vertice1][vertice2] > 0);
    }

    public ArrayList<Integer> listaDeAdjacencia(int vertice1) {  //retorna a lista de vértices adjacentes a um determinado vértice v. Ou seja, 
        //todos os vértices u cujos quais existe uma aresta que sai de v e chega em u.
        ArrayList<Integer> aux = new ArrayList();
        for (int i = 0; i < this.numeroVertices; i++) {
            if (matrizAdjacencia[vertice1][i] > 0) {
                aux.add(i);
            }
        }
        return aux;
    }

    public int getPeso(int vertice1, int vertice2) {  //Retorna o peso de uma aresta dada nos argumentos.
        return matrizAdjacencia[vertice1][vertice2];
    }

    public void setPeso(int vertice1, int vertice2, int peso){ //Altera o peso de uma aresta do grafo
      matrizAdjacencia[vertice1][vertice2] = peso;
    }

    //retorna o numero de vertices presentes no grafo
    public int getNumeroVertices() {
        return numeroVertices;
    }

    //imprime a matriz de adjacencia formatada
    public void imprime() {
        System.out.print("   ");
        for (int i = 0; i < this.numeroVertices; i++) {
            System.out.print(i + "   ");
        }
        System.out.println();
        for (int i = 0; i < this.numeroVertices; i++) {
            System.out.print(i + "  ");
            for (int j = 0; j < this.numeroVertices; j++) {
                System.out.print(this.matrizAdjacencia[i][j] + "   ");
            }
            System.out.println();
        }
    }
}
