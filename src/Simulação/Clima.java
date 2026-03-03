package Simulação;

import Mundo.MacroChunk;
import Mundo.Mundo;

public class Clima {
    
    
    public static void simularClima(Mundo mundo){
        double[][] proximaUmidade = new double[mundo.getWidht()][mundo.getHeight()];
        MacroChunk[][] macroChunkAtual = mundo.getMatrizMundo();
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
}
