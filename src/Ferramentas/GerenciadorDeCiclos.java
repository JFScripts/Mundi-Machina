package Ferramentas;

import Enums.Tempo;
import Mundo.Mundo;
import Simulação.ClimaDiario;

public class GerenciadorDeCiclos {

    public static void simularTickMundo(Mundo mundo) {
        long cicloAtual = mundo.getCicloMundial();
        
        mundo.setCicloMundial(cicloAtual + 1);
        if (cicloAtual == 0) return;

        eventoDoMinuto(mundo);
        if (cicloAtual % Tempo.HORA.getQntCiclos() == 0L) {
            eventosDaHora(mundo);
        }

        if (cicloAtual % Tempo.DIA.getQntCiclos() == 0L) {
            eventosDoDia(mundo);
        }

        if (cicloAtual % Tempo.MES.getQntCiclos() == 0L) {
            eventosDoMes(mundo);
        }
    }

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