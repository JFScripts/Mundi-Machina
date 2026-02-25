package Debug;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Mundo.Celula;
import Mundo.Mundo;

import java.awt.Color;

public class GerarImagemMundo {
    public static void gerarHeightMap(Mundo mundo, String caminho){
        int widht = mundo.getWidht();
        int height = mundo.getHeight();
        Celula[][] matrizMundo = mundo.getMatrizMundo();
        BufferedImage heightMap = new BufferedImage(widht, height, BufferedImage.TYPE_INT_RGB);
        File arquivoDeSaida = new File(caminho);
        
        for(int i = 0; i < widht; i ++){
            for(int j = 0; j < height; j ++){
                double alturaAtual = matrizMundo[j][i].getAltura();
                int alturaArredondada = (int) Math.round(alturaAtual);
                Color corAtual = obterCorHeightMap(alturaArredondada);
                heightMap.setRGB(i, j, corAtual.getRGB());
            }
        }

        try {
            ImageIO.write(heightMap, "png", arquivoDeSaida);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void gerarTemperatureMap(Mundo mundo, String caminho){
        int widht = mundo.getWidht();
        int height = mundo.getHeight();
        Celula[][] matrizMundo = mundo.getMatrizMundo();
        BufferedImage heightMap = new BufferedImage(widht, height, BufferedImage.TYPE_INT_RGB);
        File arquivoDeSaida = new File(caminho);
        
        for(int i = 0; i < widht; i ++){
            for(int j = 0; j < height; j ++){
                double temperaturaAtual = matrizMundo[j][i].getTemperatura();
                Color corAtual = obterCorTemperatureMap(temperaturaAtual);
                heightMap.setRGB(i, j, corAtual.getRGB());
            }
        }

        try {
            ImageIO.write(heightMap, "png", arquivoDeSaida);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void gerarHumidityMap(Mundo mundo, String caminho){
        int widht = mundo.getWidht();
        int height = mundo.getHeight();
        Celula[][] matrizMundo = mundo.getMatrizMundo();
        BufferedImage heightMap = new BufferedImage(widht, height, BufferedImage.TYPE_INT_RGB);
        File arquivoDeSaida = new File(caminho);
        
        for(int i = 0; i < widht; i ++){
            for(int j = 0; j < height; j ++){
                double umidadeAtual = matrizMundo[j][i].getUmidade();
                Color corAtual = obterCorHumidityMap(umidadeAtual);
                heightMap.setRGB(i, j, corAtual.getRGB());
            }
        }

        try {
            ImageIO.write(heightMap, "png", arquivoDeSaida);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private static Color obterCorHeightMap(int altura) {
        switch (altura) {
            case 0: return new Color(15, 94, 156);   // Oceano (Azul Profundo)
            case 1: return new Color(235, 225, 175); // Areia da Praia
            case 2: return new Color(153, 204, 113); // Planície Clara
            case 3: return new Color(104, 165, 85);  // Campo Verde
            case 4: return new Color(63, 122, 58);   // Floresta
            case 5: return new Color(125, 105, 84);  // Colina de Terra (Marrom)
            case 6: return new Color(140, 132, 125); // Montanha Base (Cinza Escuro)
            case 7: return new Color(166, 161, 156); // Montanha Média (Cinza)
            case 8: return new Color(199, 196, 193); // Pico Rochoso (Cinza Claro)
            case 9: return new Color(255, 255, 255); // Neve (Branco)
            default: return new Color(0, 0, 0);      // Cor de fallback (Preto) em caso de erro
        }
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
