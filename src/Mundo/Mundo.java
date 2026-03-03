package Mundo;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Enums.Biomas;

public class Mundo {
    private int widht;
    private int height;
    private int linhaEquador;
    private MacroChunk[][] matrizMundo;
    private long seedMundo;
    private long cicloMundial;

    private Random geraracaoAleatoria;

    public Mundo(int widht, int height, long seed) {
        this.seedMundo = seed;
        this.cicloMundial = 0;
        this.geraracaoAleatoria = new Random(this.seedMundo);
        this.widht = widht;
        this.height = height;
        this.matrizMundo = new MacroChunk[widht][height];
        this.linhaEquador = geraracaoAleatoria.nextInt((height) + 1);

    }

    public Mundo(int widht, int height){
        this(widht, height, System.currentTimeMillis());
    }

    public void criarMundo(double valorTerra, int qntSuavizacao, int qntErosao, int qntUmidade){
 
        for(int x = 0; x < this.matrizMundo.length; x ++){
            for(int y = 0; y < this.matrizMundo[x].length; y ++){
                double numero = this.geraracaoAleatoria.nextDouble();
                int valorCelula = 0;
                if(numero < valorTerra){
                    valorCelula = 1;
                }
                matrizMundo[x][y] = new MacroChunk(valorCelula, this.seedMundo, x, y);
            }
        }

        for(int i = 0; i < qntSuavizacao; i++){
            suavizarMundo();
        }

        gerarAlturas(qntErosao);
        adicionarTemperatura();
        adicionarUmidade(qntUmidade);

        for(int x = 0; x < this.matrizMundo.length; x ++){
            for(int y = 0; y < this.matrizMundo[x].length; y ++){
                this.matrizMundo[x][y].setBioma(distribuirBiomas(x, y));
                calcularPressao(x, y);
            }
        }
        calcularVentos();
    }

    private void suavizarMundo(){

        double taxaMutacao = 0.05;
        int[][] novoMundo = new int[this.widht][this.height];
        for(int x = 0; x < this.matrizMundo.length; x ++){
            for(int y = 0; y < this.matrizMundo[x].length; y ++){
                
                int contagemTerra = contarTerra(x, y);
                int contagemMar = 8 - contagemTerra;
                if(contagemTerra > contagemMar){
                    novoMundo[x][y] = 1;
                } else {
                    novoMundo[x][y] = 0;
                }
                double chanceMutacao = this.geraracaoAleatoria.nextDouble();
                if(chanceMutacao < taxaMutacao && contagemTerra >= 3 && contagemTerra <= 5){
                    if(novoMundo[x][y] == 1){
                        novoMundo[x][y] = 0;
                    } else {
                        novoMundo[x][y] = 1;
                    }
                }
            }
        }
        for(int x = 0; x < this.matrizMundo.length; x ++){
            for(int y = 0; y < this.matrizMundo[x].length; y ++){
                matrizMundo[x][y].setAltura(novoMundo[x][y]);
            }
        }
        
    }

    private int contarTerra(int i, int j){
        int contagemTerra = 0;
        for(int x = -1; x <= 1; x ++){
            for(int y = -1; y <= 1; y ++){
                int vizinhoColuna = x + i;
                int vizinhoLinha = y + j;
                if(x == 0 && y == 0){
                    continue;
                } else if(vizinhoColuna >= 0 && vizinhoColuna < this.widht && vizinhoLinha >= 0 && vizinhoLinha < this.height){
                    if(this.matrizMundo[vizinhoColuna][vizinhoLinha].getAltura() == 1){
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

        for(int x = 0; x < this.matrizMundo.length; x ++){
            for(int y = 0; y < this.matrizMundo[x].length; y ++){
                if (matrizMundo[x][y].getAltura() != 0) {
                    double valorBase = this.geraracaoAleatoria.nextDouble();
                    valorBase = Math.pow(valorBase, 3);
                    double novaAltura = (valorBase * 8) + 1;
                    matrizMundo[x][y].setAltura(novaAltura);
                }
            }
        }
    
        for (int a = 0; a < qntErosao; a++) {
            double[][] bufferAnoAtual = new double[this.widht][this.height];
    
            for(int x = 0; x < this.matrizMundo.length; x ++){
                for(int y = 0; y < this.matrizMundo[x].length; y ++){
                    if (matrizMundo[x][y].getAltura() == 0) {
                        bufferAnoAtual[x][y] = 0; 
                        
                    } else {
                        bufferAnoAtual[x][y] = suavizarAltura(x, y);
                    }
                }
            }
    
            for(int x = 0; x < this.matrizMundo.length; x ++){
                for(int y = 0; y < this.matrizMundo[x].length; y ++){
                    this.matrizMundo[x][y].setAltura(bufferAnoAtual[x][y]);
                }
            }
        }
    }

    private double suavizarAltura(int i, int j){
        double somaTotal = 0;
        int qntCelulas = 0;
        for(int x = -1; x <= 1; x ++){
            for(int y = -1; y <= 1; y ++){
                int vizinhoColuna = x + i;
                int vizinhoLinha = y + j;
                if(vizinhoColuna >= 0 && vizinhoColuna < this.widht && vizinhoLinha >= 0 && vizinhoLinha < this.height){
                    somaTotal += this.matrizMundo[vizinhoColuna][vizinhoLinha].getAltura();
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

        for(int x = 0; x < this.matrizMundo.length; x ++){
            for(int y = 0; y < this.matrizMundo[x].length; y++){
                int distaciaEquador = Math.abs(this.linhaEquador - y);
                double temperaturaBase = 1 - ((double) distaciaEquador/maiorDistancia);
                double temperaturaFinal = temperaturaBase - (this.matrizMundo[x][y].getAltura() * 0.05);
                this.matrizMundo[x][y].setTemperaturaBase(temperaturaFinal);
            }
        }
    }

    private void adicionarUmidade(int cicloUmidade){
        double[][] novaUmidade = new double[this.widht][this.height];

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
                int vizinhoColuna = x + i;
                int vizinhoLinha = y + j;
                
                if(x == 0 && y == 0){
                    continue;
                }
                if (vizinhoColuna >= 0 && vizinhoColuna < this.widht && vizinhoLinha >= 0 && vizinhoLinha < this.height) {
                    if (this.matrizMundo[vizinhoColuna][vizinhoLinha].getUmidade() > maiorUmidadeVizinho) {
                        maiorUmidadeVizinho = this.matrizMundo[vizinhoColuna][vizinhoLinha].getUmidade();
                    }
                }
           }
        }
        double umidadePenalidade = maiorUmidadeVizinho - ((0.05 * this.matrizMundo[i][j].getAltura()) + (0.1 * this.matrizMundo[i][j].getTemperaturaLocal()));
        
        return Math.max(0, umidadePenalidade);
    }

    private Biomas distribuirBiomas(int x, int y){
        List<Biomas> candidatos = new ArrayList<>();
        double temperaturaLocal = this.matrizMundo[x][y].getTemperaturaBase();
        double umidadeLocal = this.matrizMundo[x][y].getUmidade();
        double alturaLocal = this.matrizMundo[x][y].getAltura();
        if(alturaLocal  <= 0){
            return Biomas.OCEANO;
        }
        for(Biomas bioma : Biomas.values()){
            if(bioma == Biomas.OCEANO){
                continue;
            }
            if(temperaturaLocal >= bioma.getTempMin() && temperaturaLocal <= bioma.getTempMax()){
                if(umidadeLocal >= bioma.getUmidMin() && umidadeLocal <= bioma.getUmidMax()){
                    candidatos.add(bioma);
                }
            }
        }

        if(candidatos.isEmpty()){
            return Biomas.PLANICIE;
        }
        if(candidatos.size() < 2){
            return candidatos.get(0);
        } else {
            Random randomLocal = new Random(this.matrizMundo[x][y].getSemente());
            return candidatos.get(randomLocal.nextInt(candidatos.size()));
        }
    }

    private void calcularPressao(int x, int y){
        double temperaturalLocal = this.matrizMundo[x][y].getTemperaturaLocal();
        double pesoTemperatura = 0.6;
        double alturaLocal = this.matrizMundo[x][y].getAltura();
        double pesoAltura = 0.4;
        double alturaMaxima = 9;
        double pressao = 1 - (pesoTemperatura * temperaturalLocal) - (pesoAltura * (alturaLocal/alturaMaxima));
        this.matrizMundo[x][y].setPressaoAr(pressao);
    }

    private void calcularVentos(){
        double pressaoCima = 0;
        double pressaoBaixo = 0;
        double pressaoEsquerda = 0;
        double pressaoDireita = 0;

        for(int x = 0; x < this.matrizMundo.length; x ++){
            for(int y = 0; y < this.matrizMundo[x].length; y ++){
                if (y - 1 >= 0) {
                    pressaoCima = this.matrizMundo[x][y - 1].getPressaoAr();
                } else {
                    pressaoCima = this.matrizMundo[x][y].getPressaoAr();
                }

                if (y + 1 < this.height) {
                    pressaoBaixo = this.matrizMundo[x][y + 1].getPressaoAr();
                } else {
                    pressaoBaixo = this.matrizMundo[x][y].getPressaoAr();
                }

                if (x - 1 >= 0) {
                    pressaoEsquerda = this.matrizMundo[x - 1][y].getPressaoAr();
                } else {
                    pressaoEsquerda = this.matrizMundo[x][y].getPressaoAr();
                }

                if (x + 1 < this.widht) {
                    pressaoDireita = this.matrizMundo[x + 1][y].getPressaoAr();
                } else {
                    pressaoDireita = this.matrizMundo[x][y].getPressaoAr();
                }

                double pressaoResultanteX = pressaoEsquerda - pressaoDireita;
                double pressaoResultanteY = pressaoCima - pressaoBaixo; 
                this.matrizMundo[x][y].setPressaoX(pressaoResultanteX);
                this.matrizMundo[x][y].setPressaoY(pressaoResultanteY);
            }
        }
    }

    public int getWidht() {
        return widht;
    }

    public int getHeight() {
        return height;
    }

    public MacroChunk[][] getMatrizMundo() {
        return matrizMundo;
    }

    public MacroChunk getXYMacroChunk(int x, int y){
        return matrizMundo[x][y];
    }

    public long getSeedMundo() {
        return seedMundo;
    }

    public long getCicloMundial() {
        return cicloMundial;
    }
    
    public void setCicloMundial(long cicloMundial) {
        this.cicloMundial = cicloMundial;
    }
    
}