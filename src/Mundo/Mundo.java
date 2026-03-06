package Mundo;
import java.util.Random;

import Enums.Biomas;
import Ferramentas.GeradorRuido2d;

public class Mundo {

    private GeradorRuido2d geradorAltitude;
    private GeradorRuido2d geradorUmidade;
    private GeradorRuido2d geradorMagia;

    private int widht;
    private int height;
    private int linhaEquador;
    private MacroChunk[][] matrizMundo;
    private long seedMundo;
    private long cicloMundial;
    private Random geraracaoAleatoria;

    public Mundo(int widht, int height, long seed) {
        this.seedMundo = seed;
        this.geradorAltitude = new GeradorRuido2d(seed);
        this.geradorUmidade = new GeradorRuido2d(seed * 73);
        this.geradorMagia = new GeradorRuido2d(seed * 37);
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
        for(int x = 0; x < this.matrizMundo.length; x++){
            for(int y = 0; y < this.matrizMundo[x].length; y ++){
                double escala = 0.05;
                double alturaAtual = geradorAltitude.gerarRuidoFractal(x * escala, y * escala, 4, 0.5, 2);
                alturaAtual = (alturaAtual / 0.6) * 9; 
                alturaAtual = Math.max(-9, Math.min(9, alturaAtual));
                this.matrizMundo[x][y] = new MacroChunk(alturaAtual, this.seedMundo, x, y);

                escala = 0.05;
                double magiaAtual = geradorMagia.gerarRuidoFractal(x * escala, y * escala, 4, 0.5, 2);
                magiaAtual = Math.abs(magiaAtual);
                if(magiaAtual < 0.35){
                    magiaAtual = 0;
                }
                this.matrizMundo[x][y].setMagia(magiaAtual);
            }
        }
        adicionarTemperatura();
        
        for(int x = 0; x < this.matrizMundo.length; x++){
            for(int y = 0; y < this.matrizMundo[x].length; y ++){
                if(matrizMundo[x][y].getAltura() > 0){
                    double escala = 0.02;
                    double umidadeAtual = geradorUmidade.gerarRuidoFractal(x * escala, y * escala, 2, 0.5, 2);
                    umidadeAtual /= 0.6;
                    umidadeAtual =(umidadeAtual + 1) / 2;
                    umidadeAtual = Math.max(0, Math.min(1, umidadeAtual));
                    this.matrizMundo[x][y].setUmidade(umidadeAtual);
                } else {
                    this.matrizMundo[x][y].setUmidade(1);
                }
            }
        }

        for(int x = 0; x < this.matrizMundo.length; x ++){
            for(int y = 0; y < this.matrizMundo[x].length; y ++){
                this.matrizMundo[x][y].setBioma(distribuirBiomas(x, y));
                calcularPressao(x, y);
            }
        }
        calcularVentos();
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

    private Biomas distribuirBiomas(int x, int y){
        MacroChunk local = matrizMundo[x][y];
        double altLocal = local.getAltura();
        double tempLocal = local.getTemperaturaLocal();
        double umidLocal = local.getUmidade();
        double magiaLocal = local.getMagia();
        Biomas biomaAtual = Biomas.DESERTO;
        double vetorAtual = 999;

        if(altLocal <= 0){
            return Biomas.OCEANO;
        }

        for(Biomas bioma : Biomas.values()){
            if(!bioma.isPodeGerarNaturalmente()){
                continue;
            }
            double altIdeal = bioma.getAltIdeal();
            double tempIdeal = bioma.getTempIdeal();
            double umidIdeal = bioma.getUmidIdeal();
            double magiaIdeal = bioma.getMagiaIdeal();

            double vetorAltura = ((altLocal - altIdeal)/9) * ((altLocal - altIdeal)/9);
            double vetorTemperatura = (tempLocal - tempIdeal) * (tempLocal - tempIdeal);
            double vetorUmidade = (umidLocal - umidIdeal) * (umidLocal - umidIdeal);
            double vetorMagia = (magiaLocal - magiaIdeal) * (magiaLocal - magiaIdeal);

            double vetorSomado = vetorAltura + vetorTemperatura + vetorUmidade + vetorMagia;

            double vetorTemp = Math.sqrt(vetorSomado);
            if(vetorTemp < vetorAtual){
                vetorAtual = vetorTemp;
                biomaAtual = bioma;
            }
        }

        return biomaAtual;
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

    public int getLinhaEquador() {
        return linhaEquador;
    }

    public Random getGeraracaoAleatoria() {
        return geraracaoAleatoria;
    }
    
}