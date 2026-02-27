package Mundo;

import Enums.Biomas;
import Ferramentas.GerenciadorDeSemente;

public class MacroChunk {
   
    private long semente;
    private int coordenadaMundialX;
    private int coordenadaMundialY;
    private double altura;
    private double temperatura;
    private double umidade;
    private Biomas bioma;

    private double pressaoAr;
    private double pressaoX;
    private double pressaoY;
    
    public MacroChunk(double altura, long sementeMundo, int xMundo, int yMundo) {
        this.altura = altura;
        this.temperatura = 0;
        this.umidade = 0;
        this.coordenadaMundialX = xMundo;
        this.coordenadaMundialY = yMundo;
        this.semente = GerenciadorDeSemente.gerarSementeMacroChunk(sementeMundo, xMundo, yMundo);
        this.bioma = null;
        this.pressaoAr = 0;
        this.pressaoX = 0;
        this.pressaoY = 0;
    }
    public MacroChunk(double altura, double temperatura, double umidade,long sementeMundo, int xMundo, int yMundo, Biomas bioma) {
        this.altura = altura;
        this.temperatura = temperatura;
        this.umidade = umidade;
        this.coordenadaMundialX = xMundo;
        this.coordenadaMundialY = yMundo;
        this.semente = GerenciadorDeSemente.gerarSementeMacroChunk(sementeMundo, xMundo, yMundo);
        this.bioma = bioma;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }
    public double getUmidade() {
        return umidade;
    }
    public void setUmidade(double umidade) {
        this.umidade = umidade;
    }
    public void setSemente(long semente) {
        this.semente = semente;
    }
    public long getSemente() {
        return semente;
    }
    public int getCoordenadaMundialX() {
        return coordenadaMundialX;
    }
    public int getCoordenadaMundialY() {
        return coordenadaMundialY;
    }
    public void setCoordenadaMundialX(int coordenadaMundialX) {
        this.coordenadaMundialX = coordenadaMundialX;
    }
    public void setCoordenadaMundialY(int coordenadaMundialY) {
        this.coordenadaMundialY = coordenadaMundialY;
    }
    public Biomas getBioma() {
        return bioma;
    }
    public void setBioma(Biomas bioma) {
        this.bioma = bioma;
    }
    public double getPressaoAr() {
        return pressaoAr;
    }
    public void setPressaoAr(double pressaoAr) {
        this.pressaoAr = pressaoAr;
    }
    public double getPressaoX() {
        return pressaoX;
    }
    public void setPressaoX(double pressaoX) {
        this.pressaoX = pressaoX;
    }
    public double getPressaoY() {
        return pressaoY;
    }
    public void setPressaoY(double pressaoY) {
        this.pressaoY = pressaoY;
    }

}
