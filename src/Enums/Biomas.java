package Enums;
import java.awt.Color;


public enum Biomas {
    TUNDRA(3.0, 0.1, 0.2, 0.1, new Color(200, 200, 200)),
    TAIGA(2, 0.2, 0.8, 0.1, new Color(189,236,182)),
    ESTEPE(2, 0.7, 0.3, 0.1, new Color(255,255,224)),
    FLORESTA_TEMPERADA(2, 0.5, 0.7, 0.1, new Color(255, 204, 55)),
    SELVA(2, 0.9, 0.9, 0.1, new Color(0, 105, 23)),
    DESERTO(3.0, 0.9, 0.1, 0.1, new Color(237, 201, 175)),
    FLORESTA_FEERICA(3.0, 0.5, 0.6, 0.9, new Color(255, 105, 180)),
    TERRA_DESOLADA(2, 0.8, 0.1, 0.9, new Color(115, 71, 0)), 
    OCEANO(0.0, 0.5, 1.0, 0.1, new Color(15, 94, 156)),
    PRAIA(0.1, 0.5, 0.3, 0.1, new Color(255, 165, 0)),
    PANTANO(0.5, 0.6, 0.9, 0.1, new Color(126, 140, 84)),
    PLANICIE(1, 0.5, 0.2, 0.1, new Color(0, 255, 0)),
    MONTANHA_ROCHOSA(6, 0.4, 0.4, 0.1, new Color(145, 145, 145)),
    PICO_NEVADO(8.5, 0.1, 0.5, 0.1, new Color(255, 255, 255));

    private final double altIdeal;
    private final double tempIdeal;
    private final double umidIdeal;
    private final double magiaIdeal;
    private final Color biomaCor;
    
    private Biomas(double altIdeal, double tempIdeal, double umidIdeal, double magiaIdeal, Color biomaCor) {
        this.altIdeal = altIdeal;
        this.tempIdeal = tempIdeal;
        this.umidIdeal = umidIdeal;
        this.magiaIdeal = magiaIdeal;
        this.biomaCor = biomaCor;
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

}
