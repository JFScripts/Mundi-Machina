package Debug;

import java.awt.Color;
import java.awt.image.BufferedImage;

import Interface.gerarImagem;
import Mundo.MacroChunk;
import Mundo.Mundo;

public class AirPressureMap implements gerarImagem{

    @Override
    public BufferedImage gerarMapaMundo(Mundo mundo) {
        int widht = mundo.getWidht();
        int height = mundo.getHeight();
        MacroChunk[][] matrizMundo = mundo.getMatrizMundo();
        BufferedImage airPressureMap = new BufferedImage(widht, height, BufferedImage.TYPE_INT_RGB);

        
        for(int x = 0; x < widht; x ++){
            for(int y = 0; y < height; y ++){
                double pressaoAtual = matrizMundo[x][y].getPressaoAr();
                Color corAtual = getMapColor(pressaoAtual);
                airPressureMap.setRGB(x, y, corAtual.getRGB());
            }
        }

        return airPressureMap;
    }

    public Color getMapColor(double dados) {
        double pressao = dados;
        double pressaoNormalizada = Math.max(0.0, Math.min(1, pressao));
        int vermelho, verde, azul;

        if (pressaoNormalizada >= 0.5) {
            double intensidade = (pressaoNormalizada - 0.5) * 2; 
            
            vermelho = (int) (255 * (1 - intensidade)); 
            verde = (int) (255 * (1 - intensidade)); 
            azul = 255;                               
        } else {
            
            double intensidade = pressaoNormalizada * 2; 
            
            vermelho = 255;                               
            verde = (int) (255 * intensidade);         
            azul = (int) (255 * intensidade);         
        }
        
        return new Color(vermelho, verde, azul);
    }

    @Override
    public String getNomeString() {
        return "PressaoDoAr";
    }
    
}
