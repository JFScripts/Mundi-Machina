package Debug;

import java.awt.Color;
import java.awt.image.BufferedImage;

import Interface.gerarImagem;
import Mundo.MacroChunk;
import Mundo.Mundo;

public class WindMap implements gerarImagem{

    @Override
    public BufferedImage gerarMapaMundo(Mundo mundo) {
        int widht = mundo.getWidht();
        int height = mundo.getHeight();
        MacroChunk[][] matrizMundo = mundo.getMatrizMundo();
        BufferedImage windMap = new BufferedImage(widht, height, BufferedImage.TYPE_INT_RGB);

        
        for(int i = 0; i < widht; i ++){
            for(int j = 0; j < height; j ++){
                Color corAtual = getMapColor(matrizMundo[j][i].getPressaoX(), matrizMundo[j][i].getPressaoY());
                windMap.setRGB(i, j, corAtual.getRGB());
            }
        }

        return windMap;
    }

    public Color getMapColor(double x, double y) {
        double xx = x * x;
        double yy = y * y;
        double intensidade = Math.sqrt(yy + xx) * 5;
        intensidade = Math.min(intensidade, 1);
        double direcao = ((Math.atan2(x, y)) / (2 * Math.PI)) + 0.5;

        return Color.getHSBColor((float) direcao, 1, (float) intensidade);
    }

    @Override
    public String getNomeString() {
        return "Vento";
    }
    
}
