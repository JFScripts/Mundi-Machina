package Mundo;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

import Enums.Biomas;
import Enums.Tempo;
import Ferramentas.GerenciadorDeSemente;

public class MacroChunk {

    private long semente;
    private Random geracaoAleatoria;

    private int coordenadaMundialX;
    private int coordenadaMundialY;
    private double altura;
    private double temperaturaBase;
    private double umidade;

    private Biomas bioma;
    private double magia;

    private double pressaoAr;
    private double pressaoX;
    private double pressaoY;

    private double temperaturaLocal;
    
    public MacroChunk(double altura, long sementeMundo, int yMundo, int xMundo) {
        this.coordenadaMundialX = xMundo;
        this.coordenadaMundialY = yMundo;
        this.semente = GerenciadorDeSemente.gerarSementeMacroChunk(sementeMundo, xMundo, yMundo);
        this.geracaoAleatoria = new Random(semente);

        this.altura = altura;
        this.temperaturaBase = 0;
        this.umidade = 0;
        this.magia = 0;
        this.temperaturaLocal = temperaturaBase;
        
        
        this.bioma = null;
        this.pressaoAr = 0;
        this.pressaoX = 0;
        this.pressaoY = 0;
    }

    public String gerarRelatorio() {
        return String.format(
            "Semente do Chunk: %d | X: %d | Y: %d%n" +
            "Altura: %.2f%n" +
            "temperaturaBase: %.2f%n" +
            "Umidade: %.2f%n" +
            "Pressão do Ar: %.2f%n" +
            "Ar X: %.2f | Ar Y %.2f%n"+
            "Bioma: %s%n"+
            "Magia: %.2f", 
            this.semente,
            this.coordenadaMundialX,
            this.coordenadaMundialY, 
            this.altura, 
            this.temperaturaBase, 
            this.umidade,
            this.pressaoAr,
            this.pressaoX,
            this.pressaoY,
            this.bioma,
            this.magia
        );
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getTemperaturaBase() {
        return temperaturaBase;
    }

    public void setTemperaturaBase(double temperaturaBase) {
        this.temperaturaBase = temperaturaBase;
        setTemperaturaLocal(temperaturaBase);
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

    public double getTemperaturaLocal() {
        return temperaturaLocal;
    }

    public void setTemperaturaLocal(double temperaturaLocal) {
        this.temperaturaLocal = temperaturaLocal;
    }

    public double getMagia() {
        return magia;
    }

    public void setMagia(double magia) {
        this.magia = magia;
    }

    public Random getGeracaoAleatoria() {
        return geracaoAleatoria;
    }

    public void setGeracaoAleatoria(Random geracaoAleatoria) {
        this.geracaoAleatoria = geracaoAleatoria;
    }

}
