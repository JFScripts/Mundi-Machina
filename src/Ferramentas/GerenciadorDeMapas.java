package Ferramentas;

import java.util.List;

import Debug.AirPressureMap;
import Debug.BiomeMap;
import Debug.HeightMap;
import Debug.HumidityMap;
import Debug.TemperatureMap;
import Debug.WindMap;
import Interface.gerarImagem;
import Mundo.Mundo;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GerenciadorDeMapas {
    private List<gerarImagem> geradores = new ArrayList<>();

    public GerenciadorDeMapas() {
        this.geradores.add(new BiomeMap());
        this.geradores.add(new HeightMap());
        this.geradores.add(new HumidityMap());
        this.geradores.add(new TemperatureMap());
        this.geradores.add(new AirPressureMap());
        this.geradores.add(new WindMap());
    }

    public BufferedImage[] GerarMapas(Mundo mundo){
        BufferedImage[] mapas = new BufferedImage[geradores.size()];
        for(int i = 0; i < this.geradores.size(); i ++){
            mapas[i] = geradores.get(i).gerarMapaMundo(mundo);
        }

        return mapas;
    }

    public List<gerarImagem> getGeradores() {
        return geradores;
    }
    
}
