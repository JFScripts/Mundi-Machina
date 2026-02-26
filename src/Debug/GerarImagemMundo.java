package Debug;
import java.awt.image.BufferedImage;

import Enums.Biomas;
import Mundo.MacroChunk;
import Mundo.Mundo;

import java.awt.Color;

public class GerarImagemMundo {


    public static BufferedImage gerarHeightMap(Mundo mundo){
        int widht = mundo.getWidht();
        int height = mundo.getHeight();
        MacroChunk[][] matrizMundo = mundo.getMatrizMundo();
        BufferedImage heightMap = new BufferedImage(widht, height, BufferedImage.TYPE_INT_RGB);
        
        for(int i = 0; i < widht; i ++){
            for(int j = 0; j < height; j ++){
                double alturaAtual = matrizMundo[j][i].getAltura();
                
                Color corAtual = obterCorHeightMap(alturaAtual);
                heightMap.setRGB(i, j, corAtual.getRGB());
            }
        }

        return heightMap;
        
    }

    public static BufferedImage gerarTemperatureMap(Mundo mundo){
        int widht = mundo.getWidht();
        int height = mundo.getHeight();
        MacroChunk[][] matrizMundo = mundo.getMatrizMundo();
        BufferedImage temperatureMap = new BufferedImage(widht, height, BufferedImage.TYPE_INT_RGB);

        for(int i = 0; i < widht; i ++){
            for(int j = 0; j < height; j ++){
                double temperaturaAtual = matrizMundo[j][i].getTemperatura();
                Color corAtual = obterCorTemperatureMap(temperaturaAtual);
                temperatureMap.setRGB(i, j, corAtual.getRGB());
            }
        }

        return temperatureMap;
    }

    public static BufferedImage gerarHumidityMap(Mundo mundo){
        int widht = mundo.getWidht();
        int height = mundo.getHeight();
        MacroChunk[][] matrizMundo = mundo.getMatrizMundo();
        BufferedImage humidityMap = new BufferedImage(widht, height, BufferedImage.TYPE_INT_RGB);
        
        for(int i = 0; i < widht; i ++){
            for(int j = 0; j < height; j ++){
                double umidadeAtual = matrizMundo[j][i].getUmidade();
                Color corAtual = obterCorHumidityMap(umidadeAtual);
                humidityMap.setRGB(i, j, corAtual.getRGB());
            }
        }

        return humidityMap;
    }
    
    public static BufferedImage gerarBiomeMap(Mundo mundo){
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

    private static Color obterCorHeightMap(double altura) {
        double VALORMINIMO = 0;
        double VALORMAXIMO = 9;

        double alturaNormalizada = (altura - VALORMINIMO) / (VALORMAXIMO - VALORMINIMO);
        int corCinza = (int) Math.round(alturaNormalizada * 255);
        return new Color(corCinza, corCinza, corCinza);
        
    }

    private static Color obterCorTemperatureMap(double temperatura) {
        // Temperaturas negativas ou zero (Topo de montanhas nos polos)
        if (temperatura <= 0.0) return new Color(148, 0, 211);  // Roxo Escuro (Frio Extremo)
        
        // Gradiente do Frio ao Quente
        if (temperatura <= 0.1) return new Color(0, 0, 255);    // Azul Escuro
        if (temperatura <= 0.2) return new Color(0, 128, 255);  // Azul Claro
        if (temperatura <= 0.3) return new Color(0, 255, 255);  // Ciano
        if (temperatura <= 0.4) return new Color(0, 255, 128);  // Verde Azulado
        if (temperatura <= 0.5) return new Color(0, 255, 0);    // Verde (Temperado Neutro)
        if (temperatura <= 0.6) return new Color(173, 255, 47); // Amarelo Esverdeado
        if (temperatura <= 0.7) return new Color(255, 255, 0);  // Amarelo
        if (temperatura <= 0.8) return new Color(255, 165, 0);  // Laranja
        if (temperatura <= 0.9) return new Color(255, 69, 0);   // Laranja Escuro
        
        // Temperatura máxima (Equador ao nível do mar)
        return new Color(255, 0, 0);                            // Vermelho (Fervendo)
    }

    private static Color obterCorHumidityMap(double umidade) {
        // Escala de umidade de 1.0 (Molhado) até 0.0 (Seco)
        if (umidade >= 0.9) return new Color(0, 0, 200);      // Azul Escuro (Oceanos e litorais encharcados)
        if (umidade >= 0.8) return new Color(30, 80, 220);    // Azul Forte
        if (umidade >= 0.7) return new Color(60, 130, 240);   // Azul Médio
        if (umidade >= 0.6) return new Color(100, 170, 255);  // Azul Claro
        if (umidade >= 0.5) return new Color(140, 200, 255);  // Azul Celeste
        if (umidade >= 0.4) return new Color(170, 220, 255);  // Azul Pálido
        if (umidade >= 0.3) return new Color(200, 235, 255);  // Branco Azulado
        if (umidade >= 0.2) return new Color(225, 245, 255);  // Quase Branco
        if (umidade >= 0.1) return new Color(245, 250, 255);  // Branco com traço de azul
        
        // Seca extrema (Sombra de chuva / Deserto)
        return new Color(255, 255, 255);                      // Branco Puro
    }
}
