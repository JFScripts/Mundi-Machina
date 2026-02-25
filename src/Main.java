import Debug.GerarImagemMundo;
import Mundo.Mundo;

public class Main {
    public static void main(String[] args) {
        
        for(int i = 0; i < 1; i ++){
            Mundo mundo = new Mundo(100, 100);
            mundo.criarMundo(0.6, 5, 2, 5);
            String altura = "Imagens/MapaAltura_"+i+".png";
            String temperatura = "Imagens/MapaTemperatura_"+i+".png";
            String umidade = "Imagens/MapaUmidade_"+i+".png";
            //System.out.println(mundo.exportarMundo());
            GerarImagemMundo.gerarHeightMap(mundo, altura);
            GerarImagemMundo.gerarTemperatureMap(mundo, temperatura);
            GerarImagemMundo.gerarHumidityMap(mundo, umidade);
        }
    }
}
