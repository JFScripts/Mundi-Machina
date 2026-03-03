package Enums;
import java.awt.Color;


public enum Biomas {
    TUNDRA(0, 0.35, 0, 0.5, 5.49, 0.01, new Color(255, 255, 255)),
    DESERTO(0.65, 1, 0, 0.3, 5.0, 0.01, new Color(237, 201, 175)),
    PANTANO(0.5, 1, 0.7, 1, 1.5, 0.01, new Color(47, 79, 79)),
    FLORESTA_TEMPERADA(0.3, 0.65, 0.4, 1, 5.49, 0.01, new Color(34, 139, 34)),
    PLANICIE(0.2, 0.8, 0.1, 0.6, 5.49, 0.51, new Color(154, 205, 50)),
    OCEANO(0, 1, 0, 1, 0.0, -10.0, new Color(15, 94, 156));
    /*PICO_NEVADO(0.0, 1.0, 0.0, 1.0, 9.0, 8, new Color(255, 250, 250)),
    MONTANHA_ROCHOSA(0.0, 1.0, 0.0, 1.0, 8, 6, new Color(139, 137, 137)),
    PRAIA(0.4, 1.0, 0.2, 1.0, 1, 0.01, new Color(244, 164, 96));*/

    private final double tempMin;
    private final double tempMax;
    private final double umidMin;
    private final double umidMax;
    private final double altMax;
    private final double altMin;
    private final Color biomaCor;

    private Biomas(double tempMin, double tempMax, double umidMin, double umidMax, double altMax, double altMin, Color cor) {
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.umidMin = umidMin;
        this.umidMax = umidMax;
        this.altMax = altMax;
        this.altMin = altMin;
        this.biomaCor = cor;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public double getUmidMin() {
        return umidMin;
    }

    public double getUmidMax() {
        return umidMax;
    }

    public Color getBiomaCor() {
        return biomaCor;
    }

    public double getAltMax() {
        return altMax;
    }

    public double getAltMin() {
        return altMin;
    }

}
