package Debug;

import java.awt.Color;
import java.awt.image.BufferedImage;

import Interface.gerarImagem;
import Mundo.MacroChunk;
import Mundo.Mundo;

public class MagicMap implements gerarImagem{

    @Override
    public BufferedImage gerarMapaMundo(Mundo mundo) {
        int widht = mundo.getWidht();
        int height = mundo.getHeight();
        MacroChunk[][] matrizMundo = mundo.getMatrizMundo();
        BufferedImage magicMap = new BufferedImage(widht, height, BufferedImage.TYPE_INT_RGB);

        for(int x = 0; x < widht; x ++){
            for(int y = 0; y < height; y ++){
                double magiaAtual = matrizMundo[x][y].getMagia();
                Color corAtual = gerarCor(magiaAtual);
                magicMap.setRGB(x, y, corAtual.getRGB());
            }
        }

        return magicMap;
    }

    private Color gerarCor(double valor){
        int red = (int) (valor * 255);
        return new Color(red, 0, 0);
    } 

    @Override
    public String getNomeString() {
        return "Magia";
    }
    
}
