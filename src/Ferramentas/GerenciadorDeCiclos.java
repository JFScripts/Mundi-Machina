package Ferramentas;

import Mundo.Mundo;
import Simulação.ClimaDiario;

public class GerenciadorDeCiclos {

    public static void atualizarUmCiclo(Mundo mundo){
        long curCiclo = mundo.getCicloMundial();
        mundo.setCicloMundial(curCiclo + 1);
    private static void eventoDoMinuto(Mundo mundo){
    }

    private static void eventosDaHora(Mundo mundo) {
        ClimaDiario.simularClima(mundo);
    }

    private static void eventosDoDia(Mundo mundo) {
        
    }

    private static void eventosDoMes(Mundo mundo) {
        
    }
    
}