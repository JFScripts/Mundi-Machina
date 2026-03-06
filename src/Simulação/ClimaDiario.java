package Simulação;

import Enums.Tempo;
import Mundo.MacroChunk;
import Mundo.Mundo;

public class ClimaDiario {
    
    private static int fatorTempo = Tempo.HORA.getQntCiclos();
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
                escoarAgua(x, y, mundo, proximaUmidade);
            }
        }

        for(int y = 0; y < mundo.getWidht(); y ++){
            for(int x = 0; x < mundo.getHeight(); x ++){
                double novaUmidade = macroChunkAtual[x][y].getUmidade();
                double taxaEvaporacao = 0.0008;
                double aguaEvaporada = Math.max(0, macroChunkAtual[x][y].getTemperaturaLocal()) * taxaEvaporacao;
                novaUmidade += proximaUmidade[x][y] - aguaEvaporada;
                novaUmidade = Math.max(0, novaUmidade);
                macroChunkAtual[x][y].setUmidade(novaUmidade);
                if(macroChunkAtual[x][y].getAltura() <= 0){
                    macroChunkAtual[x][y].setUmidade(1);
                }
            }
        }
    }

    private static void atualizarUmidade(int x, int y, MacroChunk curMacroChunk, double[][] proximaUmidade, Mundo mundo){
        double umidadeAtual = curMacroChunk.getUmidade();
        double taxaTrasferencia = 0.45;
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

    private static void escoarAgua(int x, int y, Mundo mundo, double[][] proximaUmidade){
        MacroChunk curMChunk = mundo.getXYMacroChunk(x, y);
        int mundoSizeX = mundo.getWidht();
        int mundoSizeY = mundo.getHeight();
        double alturaAtual = curMChunk.getAltura();
        double umidadeAtual = curMChunk.getUmidade();
        if(alturaAtual <= 0){
            return;
        }
        double saturacaoMaxima = 1;
        if(umidadeAtual <= saturacaoMaxima){
            return;
        }
        int vizinhoMaisBaixoX = x;
        int vizinhoMaisBaixoY = y;
        double menorAlturaEncontrada = alturaAtual;

        if (y - 1 >= 0) { 
            if(mundo.getXYMacroChunk(x, y-1).getAltura() < menorAlturaEncontrada){
                vizinhoMaisBaixoX = x;
                vizinhoMaisBaixoY = y - 1;
                menorAlturaEncontrada = mundo.getXYMacroChunk(x, y-1).getAltura();
            }
        }

        if (y + 1 < mundoSizeY) {
            if(mundo.getXYMacroChunk(x, y+1).getAltura() < menorAlturaEncontrada){
                vizinhoMaisBaixoX = x;
                vizinhoMaisBaixoY = y + 1;
                menorAlturaEncontrada = mundo.getXYMacroChunk(x, y+1).getAltura();
            }
        }

        if (x - 1 >= 0) {
            if(mundo.getXYMacroChunk(x - 1, y).getAltura() < menorAlturaEncontrada){
                vizinhoMaisBaixoX = x - 1;
                vizinhoMaisBaixoY = y;
                menorAlturaEncontrada = mundo.getXYMacroChunk(x - 1, y).getAltura();
            }
        } 

        if (x + 1 < mundoSizeX) {
            if(mundo.getXYMacroChunk(x + 1, y).getAltura() < menorAlturaEncontrada){
                vizinhoMaisBaixoX = x + 1;
                vizinhoMaisBaixoY = y;
                menorAlturaEncontrada = mundo.getXYMacroChunk(x + 1, y).getAltura();
            }
        } 

        if(menorAlturaEncontrada == alturaAtual){
            return;
        }
        double taxaTrasferencia = 1;
        double umidadeTransferir = (umidadeAtual - saturacaoMaxima) * taxaTrasferencia;
        proximaUmidade[x][y] -= umidadeTransferir;
        proximaUmidade[vizinhoMaisBaixoX][vizinhoMaisBaixoY] += umidadeTransferir;
        
    }

    private static void atualizarTemperaturaGlobal(Mundo mundo, double duracaoDia){
        double fracaoDia = (mundo.getCicloMundial() % duracaoDia) / duracaoDia;
        double tempoOnda = fracaoDia * (2 * Math.PI);
        double duracaoAno = Tempo.ANO.getQntCiclos();
        double fracaoAno = (mundo.getCicloMundial() % duracaoAno) / duracaoAno;
        double ondaSazonal = Math.sin(fracaoAno * 2 * Math.PI);

        int equador = mundo.getLinhaEquador();
        int maiorDistancia = Math.max(equador, mundo.getHeight() - equador);

        int width = mundo.getWidht();
        int height = mundo.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                MacroChunk curMChunk = mundo.getXYMacroChunk(x, y);
                double fatorHemisferico =  (equador - y)/(double) maiorDistancia;
                double macroChunkPosicao =  (((double) x / width) * (2*Math.PI));
                double incidenciaSolar = Math.sin(tempoOnda + macroChunkPosicao);
                double fatorSolar = 0.2;
                double impactoSazonal = ondaSazonal * fatorHemisferico * 0.3;
                double temperaturaDiaria = curMChunk.getTemperaturaBase() + (incidenciaSolar * fatorSolar) + impactoSazonal;
                double temperaturaAtual = curMChunk.getTemperaturaLocal();
                double inerciaTermica = 0.0005;
                if(curMChunk.getAltura() <= 0){
                    inerciaTermica = 0.0001;
                }
                double novaTemperatura = temperaturaAtual + (temperaturaDiaria - temperaturaAtual) * inerciaTermica;
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
}
