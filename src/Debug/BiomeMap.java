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

        
        for(int i = 0; i < widht; i ++){
            for(int j = 0; j < height; j ++){
                Biomas biomaAtual = matrizMundo[j][i].getBioma();
                Color corAtual = biomaAtual.getBiomaCor();
                biomeMap.setRGB(i, j, corAtual.getRGB());
            }
        }

        return biomeMap;
    }

    @Override
    public String getNomeString() {
        return "Bioma";
    }
    
}
