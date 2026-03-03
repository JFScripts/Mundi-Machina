package Ferramentas;

import Mundo.Mundo;

public class GerenciadorDeCiclos {

    public static void atualizarUmCiclo(Mundo mundo){
        long curCiclo = mundo.getCicloMundial();
        mundo.setCicloMundial(curCiclo + 1);
    }

}
