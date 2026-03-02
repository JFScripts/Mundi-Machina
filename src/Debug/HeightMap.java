package Debug;

import java.awt.Color;
import java.awt.image.BufferedImage;

import Interface.gerarImagem;
import Mundo.MacroChunk;
import Mundo.Mundo;

public class HeightMap implements gerarImagem{

    @Override
    public BufferedImage gerarMapaMundo(Mundo mundo) {
        int widht = mundo.getWidht();
        int height = mundo.getHeight();
        MacroChunk[][] matrizMundo = mundo.getMatrizMundo();
        BufferedImage heightMap = new BufferedImage(widht, height, BufferedImage.TYPE_INT_RGB);
        
        for(int i = 0; i < widht; i ++){
            for(int j = 0; j < height; j ++){
                double alturaAtual = matrizMundo[j][i].getAltura();
                
                Color corAtual = getMapColor(alturaAtual);
                heightMap.setRGB(i, j, corAtual.getRGB());
            }
        }

        return heightMap;
    }

    public Color getMapColor(double dados) {
        double VALORMINIMO = 0;
        double VALORMAXIMO = 9;
        double altura = dados;

        double alturaNormalizada = (altura - VALORMINIMO) / (VALORMAXIMO - VALORMINIMO);
        int corCinza = (int) Math.round(alturaNormalizada * 255);
        return new Color(corCinza, corCinza, corCinza);
    }

    @Override
    public String getNomeString() {
        return "Altura";
    }
    
}
