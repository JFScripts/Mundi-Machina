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
        
        for(int i = 0; i < widht; i ++){
            for(int j = 0; j < height; j ++){
                double umidadeAtual = matrizMundo[j][i].getUmidade();
                Color corAtual = getMapColor(umidadeAtual);
                humidityMap.setRGB(i, j, corAtual.getRGB());
            }
        }

        return humidityMap;
    }

    public Color getMapColor(double dados) {
        double umidade = dados;
        int blue = (int) (umidade * 255);
        return new Color(0, 0, blue);
    }

    @Override
    public String getNomeString() {
        return "Umidade";
    }
    
}
