package Debug;

import java.awt.Color;
import java.awt.image.BufferedImage;

import Interface.gerarImagem;
import Mundo.MacroChunk;
import Mundo.Mundo;

public class HumidityMap implements gerarImagem{

    @Override
    public BufferedImage gerarMapaMundo(Mundo mundo) {
        int widht = mundo.getWidht();
        int height = mundo.getHeight();
        MacroChunk[][] matrizMundo = mundo.getMatrizMundo();
        BufferedImage humidityMap = new BufferedImage(widht, height, BufferedImage.TYPE_INT_RGB);
        
        for(int x = 0; x < widht; x ++){
            for(int y = 0; y < height; y ++){
                double umidadeAtual = matrizMundo[x][y].getUmidade();
                Color corAtual = getMapColor(umidadeAtual);
                humidityMap.setRGB(x, y, corAtual.getRGB());
            }
        }

        return humidityMap;
    }

    public Color getMapColor(double dados) {
        double umidade = dados;
        int blue = Math.max(0, Math.min(255, (int) (umidade * 255)));
        return new Color(0, 0, blue);
    }

    @Override
    public String getNomeString() {
        return "Umidade";
    }
    
}
