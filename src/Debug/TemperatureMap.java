package Debug;

import java.awt.Color;
import java.awt.image.BufferedImage;

import Interface.gerarImagem;
import Mundo.MacroChunk;
import Mundo.Mundo;

public class TemperatureMap implements gerarImagem{

    @Override
    public BufferedImage gerarMapaMundo(Mundo mundo) {
        int widht = mundo.getWidht();
        int height = mundo.getHeight();
        MacroChunk[][] matrizMundo = mundo.getMatrizMundo();
        BufferedImage temperatureMap = new BufferedImage(widht, height, BufferedImage.TYPE_INT_RGB);

        for(int x = 0; x < widht; x ++){
            for(int y = 0; y < height; y ++){
                double temperaturaAtual = matrizMundo[x][y].getTemperaturaLocal();
                Color corAtual = getMapColor(temperaturaAtual);
                temperatureMap.setRGB(x, y, corAtual.getRGB());
            }
        }

        return temperatureMap;
    }

    public Color getMapColor(double dados) {
        double temperatura = dados;

        if (temperatura <= 0.0) return new Color(148, 0, 211);
        if (temperatura <= 0.1) return new Color(0, 0, 255);    
        if (temperatura <= 0.2) return new Color(0, 128, 255);  
        if (temperatura <= 0.3) return new Color(0, 255, 255);  
        if (temperatura <= 0.4) return new Color(0, 255, 128);  
        if (temperatura <= 0.5) return new Color(0, 255, 0);
        if (temperatura <= 0.6) return new Color(173, 255, 47); 
        if (temperatura <= 0.7) return new Color(255, 255, 0); 
        if (temperatura <= 0.8) return new Color(255, 165, 0);  
        if (temperatura <= 0.9) return new Color(255, 69, 0);   
            
        return new Color(255, 0, 0);                            
    }

    @Override
    public String getNomeString() {
        return "Temperatura";
    }
    
}
