package Enums;
import java.awt.Color;


public enum Biomas {
    TUNDRA(0, 0.35, 0, 0.5, false, new Color(255, 255, 255)),
    DESERTO(0.65, 1, 0, 0.3, false, new Color(237, 201, 175)),
    PANTANO(0.5, 1, 0.7, 1, false,new Color(47, 79, 79)),
    FLORESTA_TEMPERADA(0.3, 0.65, 0.4, 1, false, new Color(34, 139, 34)),
    PLANICIE(0.2, 0.8, 0.1, 0.6, false, new Color(154, 205, 50)),
    OCEANO(0, 1, 0, 1, true, new Color(15, 94, 156));

    private final double tempMin;
    private final double tempMax;
    private final double umidMin;
    private final double umidMax;
    private final boolean surgirAgua;
    private final Color biomaCor;

    private Biomas(double tempMin, double tempMax, double umidMin, double umidMax, boolean surgirAgua, Color cor) {
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.umidMin = umidMin;
        this.umidMax = umidMax;
        this.surgirAgua = surgirAgua;
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

    public boolean isSurgirAgua() {
        return surgirAgua;
    }

    public Color getBiomaCor() {
        return biomaCor;
    }
}
