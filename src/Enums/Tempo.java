package Enums;

public enum Tempo {
    MINUTO(1),
    HORA(60),
    DIA(1440),
    SEMANA(10080),
    MES(40320),
    ANO( 483840);

    private final int qntCiclos;

    private Tempo(int qntCiclos) {
        this.qntCiclos = qntCiclos;
    }

    public int getQntCiclos() {
        return qntCiclos;
    }
}
