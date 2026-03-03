package Simulação;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Enums.Biomas;
import Mundo.MacroChunk;
import Mundo.Mundo;

public class Clima {
    
    
    public static void simularClima(Mundo mundo){
        double[][] proximaUmidade = new double[mundo.getWidht()][mundo.getHeight()];
        MacroChunk[][] macroChunkAtual = mundo.getMatrizMundo();

        atualizarTemperaturaGlobal(mundo, 1440);

        for(int y = 0; y < mundo.getWidht(); y ++){
            for(int x = 0; x < mundo.getHeight(); x ++){
                atualizarPressaoGlobal(mundo, x, y);
            }
        }
        atualizarVento(mundo);

        for(int y = 0; y < mundo.getWidht(); y ++){
            for(int x = 0; x < mundo.getHeight(); x ++){
                atualizarUmidade(x, y, macroChunkAtual[x][y], proximaUmidade, mundo);
            }
        }

        for(int y = 0; y < mundo.getWidht(); y ++){
            for(int x = 0; x < mundo.getHeight(); x ++){
                double novaUmidade = macroChunkAtual[x][y].getUmidade();
                novaUmidade += proximaUmidade[x][y];
                macroChunkAtual[x][y].setUmidade(Math.min(novaUmidade, 1));
                if(macroChunkAtual[x][y].getAltura() <= 0){
                    macroChunkAtual[x][y].setUmidade(1);
                }
            }
        }
        for(int y = 0; y < mundo.getWidht(); y ++){
            for(int x = 0; x < mundo.getHeight(); x ++){
                macroChunkAtual[x][y].setBioma(atualizarBioma(mundo, x, y));
            }
        }
        
    }

    private static void atualizarUmidade(int x, int y, MacroChunk curMacroChunk, double[][] proximaUmidade, Mundo mundo){
        double umidadeAtual = curMacroChunk.getUmidade();
        double taxaTrasferencia = 0.1;
        double pressaoX = curMacroChunk.getPressaoX();
        double pressaoY = curMacroChunk.getPressaoY();
        double umidadeX = Math.abs(umidadeAtual * pressaoX * taxaTrasferencia);
        double umidadeY = Math.abs(umidadeAtual * pressaoY * taxaTrasferencia);

        if((umidadeX + umidadeY) > umidadeAtual){
            double fatorReducao = umidadeAtual/(umidadeX + umidadeY);
            umidadeX *= fatorReducao;
            umidadeY *= fatorReducao;
        }
        int alvoX = x;
        int alvoY = y;
        if(pressaoX < 0){
            alvoX = x - 1;
        } else {
            alvoX = x + 1;
        }

        if(pressaoY < 0){
            alvoY = y - 1;
        } else {
            alvoY = y + 1;
        }
        if(alvoX >= 0 && alvoX < mundo.getWidht()){
            proximaUmidade[alvoX][y] += umidadeX;
        }
        if(alvoY >= 0 && alvoY < mundo.getHeight()){
            proximaUmidade[x][alvoY] += umidadeY;
        }
        proximaUmidade[x][y] -= umidadeX;
        proximaUmidade[x][y] -= umidadeY;

    }

    private static void atualizarTemperaturaGlobal(Mundo mundo, double duracaoDia){
        double fracaoDia = (mundo.getCicloMundial() % duracaoDia) / duracaoDia;
        double tempoOnda = fracaoDia * (2 * Math.PI);
        double duracaoAno = 1440 * 28 * 12;
        double fracaoAno = (mundo.getCicloMundial() % duracaoAno) / duracaoAno;
        double ondaSazonal = Math.sin(fracaoAno * 2 * Math.PI);

        int equador = mundo.getLinhaEquador();
        int maiorDistancia = Math.max(equador, mundo.getHeight() - equador);

        int width = mundo.getWidht();
        int height = mundo.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double fatorHemisferico =  (equador - y)/(double) maiorDistancia;
                double macroChunkPosicao =  (((double) x / width) * (2*Math.PI));
                double incidenciaSolar = Math.sin(tempoOnda + macroChunkPosicao);
                double fatorSolar = 0.2;
                double impactoSazonal = ondaSazonal * fatorHemisferico * 0.3;
                double novaTemperatura = mundo.getXYMacroChunk(x, y).getTemperaturaBase() + (incidenciaSolar * fatorSolar) + impactoSazonal;
                mundo.getXYMacroChunk(x, y).setTemperaturaLocal(novaTemperatura);
            }
        }
    }

    private static void atualizarPressaoGlobal(Mundo mundo, int x, int y){
        double temperaturalLocal = mundo.getXYMacroChunk(x, y).getTemperaturaLocal();
        double pesoTemperatura = 0.6;
        double alturaLocal = mundo.getXYMacroChunk(x, y).getAltura();
        double pesoAltura = 0.4;
        double alturaMaxima = 9;
        double pressao = 1 - (pesoTemperatura * temperaturalLocal) - (pesoAltura * (alturaLocal/alturaMaxima));
        mundo.getXYMacroChunk(x, y).setPressaoAr(pressao);
    }

    private static void atualizarVento(Mundo mundo){
        double pressaoCima = 0;
        double pressaoBaixo = 0;
        double pressaoEsquerda = 0;
        double pressaoDireita = 0;
        int matrizX = mundo.getWidht();
        int matrizY = mundo.getHeight();

        for(int x = 0; x < matrizX; x ++){
            for(int y = 0; y < matrizY; y ++){
                if (y - 1 >= 0) { 
                    pressaoCima = mundo.getXYMacroChunk(x, y-1).getPressaoAr();
                } else {
                    pressaoCima = mundo.getXYMacroChunk(x, y).getPressaoAr();
                }

                if (y + 1 < matrizY) {
                    pressaoBaixo = mundo.getXYMacroChunk(x, y+1).getPressaoAr();
                } else {
                    pressaoBaixo = mundo.getXYMacroChunk(x, y).getPressaoAr();
                }

                if (x - 1 >= 0) {
                    pressaoEsquerda = mundo.getXYMacroChunk(x - 1, y).getPressaoAr();
                } else {
                    pressaoEsquerda = mundo.getXYMacroChunk(x, y).getPressaoAr();
                }

                if (x + 1 < matrizX) {
                    pressaoDireita = mundo.getXYMacroChunk(x + 1, y).getPressaoAr();
                } else {
                    pressaoDireita = mundo.getXYMacroChunk(x, y).getPressaoAr();
                }

                double pressaoResultanteX = pressaoEsquerda - pressaoDireita;
                double pressaoResultanteY = pressaoCima - pressaoBaixo; 
                mundo.getXYMacroChunk(x, y).setPressaoX(pressaoResultanteX);
                mundo.getXYMacroChunk(x, y).setPressaoY(pressaoResultanteY);
            }
        }
    }

    private static Biomas atualizarBioma(Mundo mundo, int x, int y){
        List<Biomas> candidatos = new ArrayList<>();
        double temperaturaLocal = mundo.getXYMacroChunk(x, y).getTemperaturaBase();
        double umidadeLocal = mundo.getXYMacroChunk(x, y).getUmidade();
        double alturaLocal = mundo.getXYMacroChunk(x, y).getAltura();
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
            Random randomLocal = new Random(mundo.getXYMacroChunk(x, y).getSemente());
            return candidatos.get(randomLocal.nextInt(candidatos.size()));
        }
    }
}
