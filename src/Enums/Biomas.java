package Enums;
import java.awt.Color;


public enum Biomas {
    // ZONAS FRIAS (Temp < 0.4)
    TUNDRA(3.0, 0.1, 0.3, 0.1, 0.9, new Color(200, 200, 200), true), 
    TAIGA(2.0, 0.2, 0.7, 0.1, 0.9, new Color(189,236,182), true),   
    PICO_NEVADO(8.5, 0.05, 0.5, 0.1, 1.0, new Color(255, 255, 255), true), 
    MATAGAL_GELIDO(2.0, 0.2, 0.1, 0.1, 0.8, new Color(170, 190, 170), true), // Ponte: Muito frio, mas sem neve

    // ZONAS TEMPERADAS (Temp entre 0.4 e 0.7)
    PLANICIE(1.0, 0.5, 0.4, 0.1, 0.8, new Color(0, 255, 0), true), 
    FLORESTA_TEMPERADA(2.0, 0.5, 0.7, 0.1, 0.8, new Color(255, 204, 55), true), 
    BOSQUE_ARIDO(1.5, 0.6, 0.2, 0.1, 0.8, new Color(200, 220, 100), true), // Ponte: Transição para o deserto
    PANTANO(0.5, 0.6, 0.9, 0.1, 0.8, new Color(126, 140, 84), true), 
    VALE_ALPINO(5.0, 0.4, 0.6, 0.1, 0.9, new Color(100, 200, 150), true), // Ponte: Altitude média, antes da neve

    // ZONAS QUENTES (Temp > 0.7)
    ESTEPE(2.0, 0.8, 0.3, 0.1, 0.8, new Color(255, 255, 224), true), 
    SAVANA(1.5, 0.85, 0.5, 0.1, 0.8, new Color(255, 215, 0), true), // Ponte: Quente e umidade média
    DESERTO(3.0, 0.95, 0.1, 0.1, 0.9, new Color(237, 201, 175), true), 
    SELVA(2.0, 0.9, 0.9, 0.1, 0.8, new Color(0, 105, 23), true),  

    // ZONAS ESPECIAIS & MAGIA (Eixo Magia > 0.6)
    FLORESTA_FEERICA(3.0, 0.5, 0.6, 0.9, 0.8, new Color(255, 105, 180), true), 
    PANTANO_MALDITO(0.5, 0.7, 0.9, 0.9, 0.8, new Color(75, 0, 130), true), // Magia corrompendo zonas úmidas
    TERRA_DESOLADA(2.0, 0.8, 0.1, 0.9, 0.8, new Color(115, 71, 0), true), 
    
    // ZONAS GEOLÓGICAS (Dominadas por Altitude)
    PRAIA(0.1, 0.6, 0.5, 0.1, 0.8, new Color(255, 165, 0), true), 
    MONTANHA_ROCHOSA(6.0, 0.4, 0.4, 0.1, 1.0, new Color(145, 145, 145), true), 

    // ZONAS INORGÂNICAS (Hardcoded de sobrevivência)
    OCEANO(0.0, 0.5, 1.0, 0.1, 1.0, new Color(15, 94, 156), true) {
        @Override
        public boolean consegueSobreviver(double altLocal, double tempLocal, double umidLocal, double magiaLocal) {
            return true;
        }
    },
    TERRA_MORTA(0.0, 0.0, 0.0, 0.0, 0.0, new Color(0, 0, 0), false) {
        @Override
        public boolean consegueSobreviver(double altLocal, double tempLocal, double umidLocal, double magiaLocal) {
            return true;
        }
    };

    
    private final double altIdeal;
    private final double tempIdeal;
    private final double umidIdeal;
    private final double magiaIdeal;
    private final double raioTolerancia;
    private final Color biomaCor;
    private final boolean podeGerarNaturalmente;
    
    private Biomas(double altIdeal, double tempIdeal, double umidIdeal, double magiaIdeal, double raioTolerancia,Color biomaCor, boolean podeGerarNaturalmente) {
        this.altIdeal = altIdeal;
        this.tempIdeal = tempIdeal;
        this.umidIdeal = umidIdeal;
        this.magiaIdeal = magiaIdeal;
        this.raioTolerancia = raioTolerancia;
        this.biomaCor = biomaCor;
        this.podeGerarNaturalmente = podeGerarNaturalmente;
    }

    public boolean consegueSobreviver(double altLocal, double tempLocal, double umidLocal, double magiaLocal){
        double vetorAltura = ((altLocal - this.altIdeal)/9) * ((altLocal - this.altIdeal)/9);
        double vetorTemperatura = (tempLocal - this.tempIdeal) * (tempLocal - this.tempIdeal);
        double vetorUmidade = (umidLocal - this.umidIdeal) * (umidLocal - this.umidIdeal);
        double vetorMagia = (magiaLocal - this.magiaIdeal) * (magiaLocal - this.magiaIdeal);

        double vetorSomado = vetorAltura + vetorTemperatura + vetorUmidade + vetorMagia;

        double vetorFinal = Math.sqrt(vetorSomado);

        if(vetorFinal <= this.raioTolerancia){
            return true;
        }

        return false;

    }

    public double getAltIdeal() {
        return altIdeal;
    }

    public double getTempIdeal() {
        return tempIdeal;
    }

    public double getUmidIdeal() {
        return umidIdeal;
    }

    public double getMagiaIdeal() {
        return magiaIdeal;
    }

    public Color getBiomaCor() {
        return biomaCor;
    }

    public double getRaioTolerancia() {
        return raioTolerancia;
    }

    public boolean isPodeGerarNaturalmente() {
        return podeGerarNaturalmente;
    }

}
