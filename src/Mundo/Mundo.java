package Mundo;
import java.text.DecimalFormat;
import java.util.Random;

public class Mundo {
    private int widht;
    private int height;
    private int linhaEquador;
    private Celula[][] matrizMundo;
    private long seedMundo;
    private Random geraracaoAleatoria;

    public Mundo(int widht, int height, long seed) {
        this.seedMundo = seed;
        this.geraracaoAleatoria = new Random(this.seedMundo);
        this.widht = widht;
        this.height = height;
        this.matrizMundo = new Celula[height][widht];
        this.linhaEquador = geraracaoAleatoria.nextInt((height) + 1);

    }

    public Mundo(int widht, int height){
        this(widht, height, System.currentTimeMillis());
    }

    public void criarMundo(double valorTerra, int qntSuavizacao, int qntErosao, int qntUmidade){
 
        for(int i = 0; i < this.matrizMundo.length; i ++){
            for(int j = 0; j < this.matrizMundo[i].length; j ++){
                double numero = this.geraracaoAleatoria.nextDouble();
                int valorCelula = 0;
                if(numero < valorTerra){
                    valorCelula = 1;
                }
                matrizMundo[i][j] = new Celula(valorCelula);
            }
        }

        for(int i = 0; i < qntSuavizacao; i++){
            suavizarMundo();
        }
        gerarAlturas(qntErosao);
        adicionarTemperatura();
        adicionarUmidade(qntUmidade);
    }

    private void suavizarMundo(){

        double taxaMutacao = 0.05;
        int[][] novoMundo = new int[this.height][this.widht];
        for(int i = 0; i < this.matrizMundo.length; i ++){
            for(int j = 0; j < this.matrizMundo[i].length; j ++){
                
                int contagemTerra = contarTerra(i, j);
                int contagemMar = 8 - contagemTerra;
                if(contagemTerra > contagemMar){
                    novoMundo[i][j] = 1;
                } else {
                    novoMundo[i][j] = 0;
                }
                double chanceMutacao = this.geraracaoAleatoria.nextDouble();
                if(chanceMutacao < taxaMutacao && contagemTerra >= 3 && contagemTerra <= 5){
                    if(novoMundo[i][j] == 1){
                        novoMundo[i][j] = 0;
                    } else {
                        novoMundo[i][j] = 1;
                    }
                }
            }
        }
        for(int i = 0; i < this.matrizMundo.length; i ++){
            for(int j = 0; j < this.matrizMundo[i].length; j ++){
                matrizMundo[i][j].setAltura(novoMundo[i][j]);
            }
        }
        
    }

    private int contarTerra(int i, int j){
        int contagemTerra = 0;
        for(int x = -1; x <= 1; x ++){
            for(int y = -1; y <= 1; y ++){
                int vizinhoLinha = x + i;
                int vizinhoColuna = y + j;
                if(x == 0 && y == 0){
                    continue;
                } else if(vizinhoLinha >= 0 && vizinhoLinha < this.height && vizinhoColuna >= 0 && vizinhoColuna < this.widht){
                    if(this.matrizMundo[vizinhoLinha][vizinhoColuna].getAltura() == 1){
                        contagemTerra ++;
                    } 
                } else {
                    continue;
                }
            }
        }
        return contagemTerra;
    }

    private void gerarAlturas(int qntErosao) {

        for (int i = 0; i < this.matrizMundo.length; i++) {
            for (int j = 0; j < this.matrizMundo[i].length; j++) {
                if (matrizMundo[i][j].getAltura() != 0) {
                    double valorBase = this.geraracaoAleatoria.nextDouble();
                    valorBase = Math.pow(valorBase, 3);
                    double novaAltura = (valorBase * 8) + 1;
                    matrizMundo[i][j].setAltura(novaAltura);
                }
            }
        }
    
        for (int a = 0; a < qntErosao; a++) {
            double[][] bufferAnoAtual = new double[this.height][this.widht];
    
            for (int i = 0; i < this.height; i++) {
                for (int j = 0; j < this.widht; j++) {
                    if (matrizMundo[i][j].getAltura() == 0) {
                        bufferAnoAtual[i][j] = 0; 
                        
                    } else {
                        bufferAnoAtual[i][j] = suavizarAltura(i, j);
                    }
                }
            }
    
            for (int i = 0; i < this.height; i++) {
                for (int j = 0; j < this.widht; j++) {
                    this.matrizMundo[i][j].setAltura(bufferAnoAtual[i][j]);
                }
            }
        }
    }

    private double suavizarAltura(int i, int j){
        double somaTotal = 0;
        int qntCelulas = 0;
        for(int x = -1; x <= 1; x ++){
            for(int y = -1; y <= 1; y ++){
                int vizinhoLinha = x + i;
                int vizinhoColuna = y + j;
                if(vizinhoLinha >= 0 && vizinhoLinha < this.height && vizinhoColuna >= 0 && vizinhoColuna < this.widht){
                    somaTotal += this.matrizMundo[vizinhoLinha][vizinhoColuna].getAltura();
                    qntCelulas ++;
                }
                
            }
        }
        double mediaVizinhos = somaTotal/qntCelulas;
        double alturaAtual = matrizMundo[i][j].getAltura();
        double resistencia = alturaAtual/10;
        double alturaFinal = (alturaAtual * resistencia) + (mediaVizinhos * (1 - resistencia));
        return alturaFinal;
    }

    private void adicionarTemperatura(){
        int maiorDistancia = this.linhaEquador - 0;
        if(maiorDistancia < this.height - this.linhaEquador){
            maiorDistancia = this.height - this.linhaEquador;
        }

        for(int i = 0; i < this.matrizMundo.length; i ++){
            int distaciaEquador = Math.abs(this.linhaEquador - i);
            double temperaturaBase = 1 - ((double) distaciaEquador/maiorDistancia);
            for(int j = 0; j < this.matrizMundo[i].length; j++){
                double temperaturaFinal = temperaturaBase - (this.matrizMundo[i][j].getAltura() * 0.05);
                this.matrizMundo[i][j].setTemperatura(temperaturaFinal);
            }
        }
    }

    private void adicionarUmidade(int cicloUmidade){
        double[][] novaUmidade = new double[this.height][this.widht];

        for(int x = 0; x < this.matrizMundo.length; x ++){
            for(int y = 0; y < this.matrizMundo[x].length; y ++){
                if(matrizMundo[x][y].getAltura() == 0){
                    matrizMundo[x][y].setUmidade(1);
                } else {
                    matrizMundo[x][y].setUmidade(0);
                }
            }
        }

        for(int i = 0; i < cicloUmidade; i ++){
            for(int x = 0; x < this.matrizMundo.length; x ++){
                for(int y = 0; y < this.matrizMundo[x].length; y ++){
                    novaUmidade[x][y] = moverUmidade(x, y);
                }
            }
            for(int x = 0; x < this.matrizMundo.length; x ++){
                for(int y = 0; y < this.matrizMundo[x].length; y ++){
                    this.matrizMundo[x][y].setUmidade(novaUmidade[x][y]);
                }
            }
        }
    }

    private double moverUmidade(int i, int j){
        if(this.matrizMundo[i][j].getAltura() == 0){
            return 1;
        } 
        double maiorUmidadeVizinho = 0;
        for(int x = -1; x <= 1; x ++){
            for(int y = -1; y <= 1; y ++){
                int vizinhoLinha = x + i;
                int vizinhoColuna = y + j;
                
                if(x == 0 && y == 0){
                    continue;
                }
                if (vizinhoLinha >= 0 && vizinhoLinha < this.height && vizinhoColuna >= 0 && vizinhoColuna < this.widht) {
                    if (this.matrizMundo[vizinhoLinha][vizinhoColuna].getUmidade() > maiorUmidadeVizinho) {
                        maiorUmidadeVizinho = this.matrizMundo[vizinhoLinha][vizinhoColuna].getUmidade();
                    }
                }
           }
        }
        double umidadePenalidade = maiorUmidadeVizinho - ((0.05 * this.matrizMundo[i][j].getAltura()) + (0.1 * this.matrizMundo[i][j].getTemperatura()));
        
        return Math.max(0, umidadePenalidade);
    }
    public String exportarMundo(){
        StringBuilder construirString = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#");
        for(int i = 0; i < this.matrizMundo.length; i ++){
            for(int j = 0; j < this.matrizMundo[i].length; j ++){
                construirString.append(df.format(matrizMundo[i][j].getAltura()));
            }
            construirString.append("\n");
        }
        return construirString.toString();
    }

    public int getWidht() {
        return widht;
    }

    public int getHeight() {
        return height;
    }

    public Celula[][] getMatrizMundo() {
        return matrizMundo;
    }

    public long getSeedMundo() {
        return seedMundo;
    }

    
}
