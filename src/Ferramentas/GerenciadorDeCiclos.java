package Ferramentas;

import Mundo.Mundo;
import Simulação.Clima;

public class GerenciadorDeCiclos {

    public static void atualizarUmCiclo(Mundo mundo){
        long curCiclo = mundo.getCicloMundial();
        mundo.setCicloMundial(curCiclo + 1);
        Clima.simularClima(mundo);
    }

}
