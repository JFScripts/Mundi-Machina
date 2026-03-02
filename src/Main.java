import java.awt.image.BufferedImage;

import Ferramentas.GerenciadorDeMapas;
import Mundo.Mundo;
import UI.DashboardDebug;

public class Main {

    public static void main(String[] args) {
        Mundo mundo = new Mundo(100, 100);
        mundo.criarMundo(0.6, 5, 2, 5);
        GerenciadorDeMapas gerenciadorDeMapas = new GerenciadorDeMapas(); 
        BufferedImage[] mapas = gerenciadorDeMapas.GerarMapas(mundo);
        
        new DashboardDebug(mapas, mundo, gerenciadorDeMapas);
        
    }

}
