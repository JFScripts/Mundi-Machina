package Ferramentas;

import java.util.Random;

public class GeradorRuido2d {
    private long semente;
    private int[] tabelaPermutacao;

    public GeradorRuido2d(long semente) {
        this.semente = semente;
        Random rand = new Random(this.semente);
        int[] arrayTemp = new int[256];
        this.tabelaPermutacao = new int[arrayTemp.length * 2];
        for(int i = 0; i < arrayTemp.length; i++){
            arrayTemp[i] = i;
        }
        
        for(int i = arrayTemp.length - 1; i > 0; i--){
            int posicaoSorteada = rand.nextInt(i + 1);
            int tempA = arrayTemp[i];
            int tempB = arrayTemp[posicaoSorteada];
            arrayTemp[posicaoSorteada] = tempA;
            arrayTemp[i] = tempB;
        }

        for(int i = 0; i < arrayTemp.length; i ++){
            this.tabelaPermutacao[i] = arrayTemp[i];
            this.tabelaPermutacao[i + arrayTemp.length] = arrayTemp[i];
        }          
    }

    public double gerarRuidoFractal(double x, double y, int oitavas, double persistencia, double lacunaridade){
        double ruidoTotal = 0;
        double amplitudeMaxima = 0;
        double amplitudeAtual = 1;
        double frequenciaAtual = 1;
        
        for(int i = 0; i < oitavas; i ++){
            ruidoTotal += (perlinBasico(x * frequenciaAtual, y * frequenciaAtual) * amplitudeAtual);
            amplitudeMaxima += amplitudeAtual;
            amplitudeAtual *= persistencia;
            frequenciaAtual *= lacunaridade;
        }

        return ruidoTotal / amplitudeMaxima;

    }

    private double perlinBasico(double x, double y){
        int X = (int) Math.floor(x) & 255;
        int Y = (int) Math.floor(y) & 255;
        double xf = x - Math.floor(x);
        double yf = y - Math.floor(y);
        double u = polinomioPerlin(xf);
        double v = polinomioPerlin(yf);
        int aa = this.tabelaPermutacao[this.tabelaPermutacao[X] + Y];
        int ab = this.tabelaPermutacao[this.tabelaPermutacao[X] + Y + 1];
        int ba = this.tabelaPermutacao[this.tabelaPermutacao[X + 1] + Y];
        int bb = this.tabelaPermutacao[this.tabelaPermutacao[X + 1] + Y + 1];

        double interpolacaoX1 = interpolacaoLinear(u, gradiente(aa, xf, yf), gradiente(ba, xf - 1, yf));
        double interpolacaoX2 = interpolacaoLinear(u, gradiente(ab, xf, yf - 1), gradiente(bb, xf - 1, yf - 1));
        
        return interpolacaoLinear(v, interpolacaoX1, interpolacaoX2);
    }

    private double polinomioPerlin(double t){
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private double gradiente(int hash, double x, double y) {
        switch(hash & 3) {
            case 0: return x + y;
            case 1: return -x + y;
            case 2: return x - y;
            case 3: return -x - y;
            default: return 0; 
        }
    }

    private double interpolacaoLinear(double t, double a, double b) {
        return a + t * (b - a);
    }
    
}
