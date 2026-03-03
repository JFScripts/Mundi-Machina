package Debug;

import java.awt.Color;
import java.awt.image.BufferedImage;

import Enums.Biomas;
import Interface.gerarImagem;
import Mundo.MacroChunk;
import Mundo.Mundo;

public class BiomeMap implements gerarImagem{

    @Override
    public BufferedImage gerarMapaMundo(Mundo mundo) {
        int widht = mundo.getWidht();
        int height = mundo.getHeight();
        MacroChunk[][] matrizMundo = mundo.getMatrizMundo();
        BufferedImage biomeMap = new BufferedImage(widht, height, BufferedImage.TYPE_INT_RGB);

        
        for(int x = 0; x < widht; x ++){
            for(int y = 0; y < height; y ++){
                Biomas biomaAtual = matrizMundo[x][y].getBioma();
                Color corAtual = biomaAtual.getBiomaCor();
                biomeMap.setRGB(x, y, corAtual.getRGB());
            }
        }

        return biomeMap;
    }

    @Override
    public String getNomeString() {
        return "Bioma";
    }
    
}
