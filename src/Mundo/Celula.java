package Mundo;

public class Celula {
   
    private double altura;
    private double temperatura;
    private double umidade;
    
    public Celula(double altura) {
        this.altura = altura;
        this.temperatura = 0;
        this.umidade = 0;
    }
    public Celula(double altura, double temperatura) {
        this.altura = altura;
        this.temperatura = temperatura;
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

}
