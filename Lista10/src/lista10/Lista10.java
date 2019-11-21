//package lista10;

import java.io.*;
import java.util.*;

public class Lista10{
    public static void imprimir(String arq,int inicial,int finals)throws FileNotFoundException, IOException{
      //Declaração de variáveis referentes a abertura de documentos e leitura dos dados
      
      FileInputStream stream = new FileInputStream(arq);
      InputStreamReader reader = new InputStreamReader(stream);
      BufferedReader br = new BufferedReader(reader);        
      String linha = br.readLine();
      
      int vertices = Integer.parseInt(linha.substring(0, linha.indexOf(' ')));
      int arestas = Integer.parseInt(linha.substring(linha.lastIndexOf(' ') + 1, linha.length()));
      linha = br.readLine();
      Grafo grafo = new Grafo(vertices);

      //Leitura dos dados
      while (linha != null) {
        int vert1 = Integer.parseInt(linha.substring(0, linha.indexOf(' ')));
        int vert2 = Integer.parseInt(linha.substring(linha.indexOf(' ') + 1, linha.lastIndexOf(' ')));
        int peso = Integer.parseInt(linha.substring(linha.lastIndexOf(' ') + 1, linha.length()));
        linha = br.readLine();

      grafo.insereAresta(vert1, vert2, peso);  
      // grafo.insereArestaNaoOrientada(vertice1, vertice2, peso);

      }
      if(finals==0){
        finals=vertices-1;
      }
      //Criacao do grafo
      AlgoritmosEmGrafos alg = new AlgoritmosEmGrafos(vertices, grafo);
      
      System.out.println("Fluxo máximo = "+alg.iniciaFluxoMaximoEmRedes(inicial, finals));
      
      ArrayList<String> agenda = new ArrayList();
      
      ArrayList<ArrayList<Integer>> caminhos = alg.getCaminhosFluxoRedes();
      ArrayList<Integer> capacidade = alg.getArestaMenorPeso();
      
      for(ArrayList<Integer> a:caminhos){
       System.out.print("Caminho = "); 
        for(int b:a){
          System.out.print(b+" ");
        }
       System.out.println("\nCapacidade residual = "+capacidade.get(caminhos.indexOf(a))+"\n");
      }
  

}
    // static BufferedReader in = new BufferedReader(
            // new InputStreamReader(System.in));

    //Procedimento main inicia a execuçao do programa - chamamos o metodo de impressao dos experimentos
    public static void main(String[] args) throws IOException{
    System.out.println("**Experimento1");
    System.out.println("Grafo 1:");
    imprimir("grafofluxo1.txt",0,0);
    System.out.println("Grafo 2:");
    imprimir("grafofluxo2.txt",0,0);

    System.out.println("**Experimento2");
    System.out.println("Grafo 1:");
    imprimir("grafofluxo1.txt",1,0);
    System.out.println("Grafo 2:");
    imprimir("grafofluxo2.txt",2,0);
  }
}
