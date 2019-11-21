// package lista10;

import java.util.*;

public class AlgoritmosEmGrafos {

    private final int[] distanciaProfundidade;
    private final int[] verticePredecessor;  // Vetor que armazena os vértices predecessores
    private final int[] distanciasCMC;     // Vetor de distâncias caminho mais curto
    private final int[] verticeAntecessorCMC;
    private ArrayList<Integer> Nvisitados = new ArrayList();  //ArrayList que auxilia vértices visitados
    private ArrayList<Integer> Aux = new ArrayList();
    private final int vertices;
    private final int NumeroVertices;
    private int tempo;
    private Grafo Grafo;
    private int indice;

    // private final ArrayList<Pair<Integer,Integer>> arestasArvoreGeradoraMinima; //armazena as arestas que estao na arvore geradora miınima do grafo.
    private final int[] verticeAntecessorAGM;   // guarda o vertice antecessor de cada no pertencente a arvore geradora mınima (AGM)

    //Determina estado do vértice, assemelha-se ao vetor cor[]
    private final int estado[]; //0 = nao visitado, 1 = visitado, 2 = todas adjacencias visitadas 

    // armazena todos os caminhos de aumento que foram encontrados durante a execução do algoritmo de Ford-Fulkeson
    private ArrayList < ArrayList < Integer >> caminhosDeAumentoFR; 
    
    //Guarda o valor da aresta de menor peso em cada caminho de aumento.
    private ArrayList < Integer > capacidadeResidual;
    
    //Construtor da classe
    public AlgoritmosEmGrafos(int vertices, Grafo grafo) {
        // this.arestasArvoreGeradoraMinima = new ArrayList<Pair<Integer, Integer>>();
        this.capacidadeResidual = new ArrayList<>();
        this.caminhosDeAumentoFR = new ArrayList<>();
        this.verticeAntecessorAGM = new int[vertices];
        this.NumeroVertices = vertices;
        this.distanciaProfundidade = new int[vertices];
        this.verticePredecessor = new int[vertices];
        this.Grafo = grafo;
        this.estado = new int[NumeroVertices];
        this.distanciasCMC = new int[NumeroVertices];
        this.verticeAntecessorCMC = new int[NumeroVertices];
        this.tempo = 0;
        this.vertices = vertices;
    }

    //Inicia o algoritmo que calcula o fluxo máximo entre dois vértices de uma rede.
    public int iniciaFluxoMaximoEmRedes(int verticeInicial, int verticeFinal){        
      return fluxoMaximoEmRedes(verticeInicial, verticeFinal);
    }

    // Implementação do algoritmo de Ford-Fulkerson o qual retorna o fluxo máximo entre um vértice inicial e outro final
    private int fluxoMaximoEmRedes(int verticeInicial, int verticeFinal){
        int fluxoMaximo = 0;
        // int chegouFinal;
        int verticeAtual=verticeInicial;
    //   do{
    //     chegouFinal=0;
        ArrayList<Integer>Adjacentes = Grafo.listaDeAdjacencia(verticeAtual);
        int maiorPeso = -1;
        int maiorIndice = 0;
        // System.out.println("\nVertice inicial = "+verticeAtual+" VerticeFinal = "+verticeFinal);
        
        while(Adjacentes.isEmpty()!=true){
         maiorPeso = -1;  
         Aux.add(verticeInicial);
         for(int a:Adjacentes){
        //   System.out.println("Adjacente = "+a+" Peso = "+(Grafo.getPeso(verticeInicial, a))); 
          if(Grafo.getPeso(verticeInicial, a)>maiorPeso)
             maiorIndice = a;
             maiorPeso = Grafo.getPeso(verticeInicial, a);
             }
            
            // System.out.println("Maior indice = "+maiorIndice);
            
            Aux.add(maiorIndice); 
            if(FordFulkersen(verticeAtual,maiorIndice,verticeFinal)==0||Grafo.getPeso(verticeInicial, maiorIndice)==0){
              Adjacentes.remove((Object)maiorIndice);
              Aux.remove((Object)maiorIndice);
            }

            else{
              
            
              this.caminhosDeAumentoFR.add(new ArrayList<Integer>(Aux));  
              AtualizaPesos(Aux);  
            }
            Aux.clear();                        
        }

        for(int a:capacidadeResidual){
            fluxoMaximo+=a;
        }
      return fluxoMaximo;
    }

    private int FordFulkersen(int verticeAnterior,int verticeAtual,int verticeFinal){
        // System.out.println("\n\nFord-Fulkerson\nVertice anterior = "+verticeAnterior+"\nVertice atual = "+verticeAtual);
        // Scanner b = new Scanner(System.in);
        indice = verticeAtual;
        int maiorPeso;
        int maiorIndice = 0;
        
        int vertAux = verticeAtual;
        ArrayList<Integer>Adjacentes = Grafo.listaDeAdjacencia(verticeAtual);
        
        if(verticeAtual == verticeFinal){           
            // System.out.println("Entrei aqui!");
            return 1;
        }
        
        while(!Adjacentes.isEmpty()){
          maiorPeso = -1;
          
          if(verticeAtual == verticeAnterior){
            verticeAtual+=1;  
          }  
        
          for(int a : Adjacentes){
            // System.out.println("Adjacente = "+a+" Peso Vertice("+verticeAtual+","+a+") = "+(Grafo.getPeso(verticeAtual, a)));
            
            if(Grafo.getPeso(verticeAtual, a)==0){
                Adjacentes.remove(a);   
            }            
            // b.nextInt();
            if(Grafo.getPeso(verticeAtual, a) > maiorPeso && Grafo.getPeso(verticeAtual, a)!=0){             
             if(!Aux.contains(a)){
                maiorIndice = a;
                maiorPeso = Grafo.getPeso(verticeAtual, a);
               }               
              }              
            }
            
            if(maiorPeso ==-1){
                return 0;
            }

            if(Aux.contains(maiorIndice)){
                Adjacentes.remove((Object)maiorIndice);
            }
            
            if(!Aux.contains(maiorIndice)){
            //   System.out.println("Maior indice = "+maiorIndice +" peso = "+maiorPeso);
              Aux.add(maiorIndice);
              if(FordFulkersen(verticeAtual,maiorIndice,verticeFinal)==0){
                Adjacentes.remove((Object)maiorIndice);
                Aux.remove((Object)maiorIndice);
              }
              else
                return 1;
            }            
        }
        return 0;
    }

    public void AtualizaPesos(ArrayList<Integer>Aux){
    //   System.out.println("Estamos atualizando");
      int menor = Integer.MAX_VALUE;
      for(int a:Aux){        
        if(a!=Aux.get(Aux.size()-1)){
        // System.out.println("A = " +a+" peso = "+Grafo.getPeso(a, Aux.get(Aux.indexOf((Object)a)+1)));    
        int indice2 = Aux.get(Aux.indexOf((Object)a)+1);  
           if(menor>Grafo.getPeso(a, indice2)&&Grafo.getPeso(a, indice2)!=0){
              menor=Grafo.getPeso(a, indice2);  
            }
        }        
      }
    //   System.out.println("Menor = " + menor);
      capacidadeResidual.add(menor);

      for(int a:Aux){
        if(a!=Aux.get(Aux.size()-1)){
            
          int indice2 = Aux.get(Aux.indexOf((Object)a)+1);
          if(Grafo.getPeso(a, indice2)!=0){
           int peso = Grafo.getPeso(a, indice2)-menor; 
        //    System.out.println("Peso antes"+ Grafo.getPeso(a, indice2)+" Peso depois"+peso);
           Grafo.setPeso(a,indice2,peso);
          }          
        }
      }

      for(int a:Aux){
        if(a!=Aux.get(Aux.size()-1)){
          int indice2 = Aux.get(Aux.indexOf((Object)a)+1);
          
        //   System.out.println("\nAresta("+a+","+indice2+") Peso = "+ Grafo.getPeso(a, indice2));
          
        }
      }
    //   System.out.println("\n");
    }

    // Retorna o caminho do fluxo de redes
    public ArrayList < ArrayList < Integer >> getCaminhosFluxoRedes(){
        return this.caminhosDeAumentoFR;
    }

    // Retorna o ArrayList de Arestas de menor peso
    public ArrayList < Integer > getArestaMenorPeso(){
        return capacidadeResidual;
    }
   
    //Algoritmo caminho mais curto
    public int[] iniciaCaminhoMaisCurto(int verticeInicial) {
        for (int i = 0; i < NumeroVertices; i++) {
            distanciasCMC[i] = Integer.MAX_VALUE / 2;
            verticeAntecessorCMC[i] = -1;
            Nvisitados.add(i);
        }

        distanciasCMC[verticeInicial] = 0;
        dijkstra(verticeInicial);
        return distanciasCMC;
    }

    //Algoritmo busca em profundidade
    public void iniciaBuscaEmProfundidade(int vertice) {
        for (int i = 0; i < NumeroVertices; i++) {
            this.estado[i] = 0;
            this.distanciaProfundidade[i] = NumeroVertices + 1;
            verticePredecessor[i] = -1;
        }
        distanciaProfundidade[vertice] = 0;
        tempo = 0;

        buscaProfundidade(vertice);
        for (int i = 0; i < NumeroVertices; i++) {
            if (estado[i] == 0) {
                buscaProfundidade(i);
            }
        }
        calculaDistancias(vertice);
    }

    //Algoritmo de busca em profundidade
    private void buscaProfundidade(int vertice) {
        estado[vertice] = 2;
        tempo += 1;

        for (int i = 0; i < NumeroVertices; i++) {
            if (Grafo.existeAresta(vertice, i)) {
                if (estado[i] == 0) {
                    verticePredecessor[i] = vertice;
                    buscaProfundidade(i);
                }
            }
        }
        this.estado[vertice] = 3;
        tempo += 1;
    }

    //Método que calcula distancias entre um dado vértice e os demais
    private void calculaDistancias(int vertice) {
        for (int i = 0; i < NumeroVertices; i++) {
            distanciaProfundidade[i] = 0;
            int atual = i;
            while (atual != vertice) {
                if (verticePredecessor[atual] == -1) {
                    distanciaProfundidade[i] += NumeroVertices;
                    break;
                }

                distanciaProfundidade[i] += Grafo.getPeso(verticePredecessor[atual], atual);
                atual = verticePredecessor[atual];
            }
        }
    }

    //Método getter que retorna o vetor de distancias de profundidade
    public int[] getDistanciaProfundidade() {
        return distanciaProfundidade;
    }

    //Método getter que retorna o vetor de vertices
    public int[] getVerticePai() {
        return verticePredecessor;
    }

    //Método auxiliar do algoritmo dijkstra que relaxa as arestas
    private void relaxa(int verticeInicial) {
        ArrayList<Integer> aux = Grafo.listaDeAdjacencia(verticeInicial);
        int i = 0;

        while (i < aux.size()) {

            if (i < aux.size()) {
                if (distanciasCMC[aux.get(i)] > distanciasCMC[verticeInicial] + Grafo.getPeso(verticeInicial, aux.get(i))) {
                    distanciasCMC[aux.get(i)] = distanciasCMC[verticeInicial] + Grafo.getPeso(verticeInicial, aux.get(i));
                    verticeAntecessorCMC[aux.get(i)] = verticeInicial;
                }
            }
            i++;
        }
    }

    //Implementação do algoritmo de Dijkstra
    private void dijkstra(int verticeInicial) {
        int menor = 0;
        while (!Nvisitados.isEmpty()) {
            menor = Nvisitados.get(0);

            for (int i = 0; i < NumeroVertices; i++) {
                if (distanciasCMC[i] < distanciasCMC[menor] && Nvisitados.contains((Object) i)) {
                    menor = i;
                }
            }

            Nvisitados.remove((Object) menor);
            ArrayList<Integer> aux = Grafo.listaDeAdjacencia(menor);
            relaxa(menor);
        }
    }

    //Retorna vertices antecessores algoritmo caminho mais curto
    public int[] getVerticeAntecessorCMC() {
        return verticeAntecessorCMC;
    }

    //Retorna os vertices antecessores arvore geradora minima
    public int[] getVerticeAntecessorAGM() {
        return verticeAntecessorAGM;
    }

    /*
    ______________________________________________________________________________________________*
    
     Partes referentes ao algoritmo de Kruskal foram comentadas devido a uma incompatibilidade
     com a classe Pairs a qual nao conseguimos solucionar e impossibilitou a sua utilização
    ______________________________________________________________________________________________*

    //inicia o algoritmo que ira encontrar a arvore geradora mınima do grafo
    public int iniciaArvoreGeradoraMinima(int vertice) {
        for (int i = 0; i < verticeAntecessorAGM.length; i++) {
            verticeAntecessorAGM[i] = -1;
        }
        verticeAntecessorAGM[vertice] = vertice;
        return arvoreGeradoraMinima(vertice);
    }

    //Implementação do algoritmo de Kruskal
    private int arvoreGeradoraMinima(int vertice) {
        //Pairs auxiliares para armazenar arestas ordenadas
        ArrayList<Pair< Integer, Integer>> arestasCrescente = new ArrayList<Pair<Integer, Integer>>();
        ArrayList<Pair< Integer, Integer>> arestas = new ArrayList<Pair<Integer, Integer>>();

        //Adicionando arestas
        for (int j = 0; j < vertices; j++) {
            for (int i = 0; i < vertices; i++) {
                if (Grafo.existeAresta(j, i) && i != j) {
                    Pair<Integer, Integer> e = new Pair<>(j, i);
                    arestas.add(e);
                }
            }
        }
        int peso = 0;
        int menor = 0;
        while (!arestas.isEmpty() || menor == Integer.MAX_VALUE) {
            menor = Integer.MAX_VALUE;
            int indice = 0;
            for (int i = 0; i < arestas.size(); i++) {
                Pair<Integer, Integer> x = arestas.get(i);
                if (Grafo.getPeso(x.getKey(), x.getValue()) < menor) {
                    indice = i;
                    menor = Grafo.getPeso(x.getKey(), x.getValue());
                }
            }
            Pair<Integer, Integer> e = new Pair(arestas.get(indice).getValue(), arestas.get(indice).getKey());
            if (menor != Integer.MAX_VALUE) {
                peso += menor / 2;
            }
            //arestasCrescente.add(arestas.get(indice));
            arestasCrescente.add(e);
            arestas.remove(e);
            arestas.remove(indice);
        }

        int i = 0;
        while (arestasArvoreGeradoraMinima.size() < (vertices)) {
            Pair<Integer, Integer> x = arestasCrescente.get(i);
            int indice1 = x.getKey();
            int indice2 = x.getValue();
            int cont = 0;

            for (int j = 0; j < arestasArvoreGeradoraMinima.size(); j++) {
                if (arestasArvoreGeradoraMinima.get(j).getKey() == x.getKey() || arestasArvoreGeradoraMinima.get(j).getKey() == x.getValue()) {
                    cont++;
                }
            }

            if (cont < 2) {
                arestasArvoreGeradoraMinima.add(x);
            }
            i++;
        }

        paisEfilhos(vertice);
        return peso;
    }

    //Método que atualiza os pais recursivamente
    public void paisEfilhos(int vertice) {

       for (int i = 0; i < arestasArvoreGeradoraMinima.size(); i++) {
            if (arestasArvoreGeradoraMinima.get(i).getValue() == vertice && verticeAntecessorAGM[arestasArvoreGeradoraMinima.get(i).getKey()] == -1) {
                verticeAntecessorAGM[arestasArvoreGeradoraMinima.get(i).getKey()] = vertice;
                paisEfilhos(arestasArvoreGeradoraMinima.get(i).getKey());
            }

            if (arestasArvoreGeradoraMinima.get(i).getKey() == vertice && verticeAntecessorAGM[arestasArvoreGeradoraMinima.get(i).getValue()] == -1) {
                verticeAntecessorAGM[arestasArvoreGeradoraMinima.get(i).getValue()] = vertice;
                paisEfilhos(arestasArvoreGeradoraMinima.get(i).getValue());
            }
        }
    }

    //Retorna os pares de arestas da arvore geradora minima
    public ArrayList<Pair<Integer, Integer>> getArestasArvoreGeradoraMinima() {
        return arestasArvoreGeradoraMinima;
    }

    */
}
