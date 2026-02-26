package Ferramentas;

public class GerenciadorDeSemente {
    
    public static long gerarSementeMacroChunk(long sementeMundo, int coordendaX, int coordenadaY){
        long sementeMacroChunk = sementeMundo ^ (coordendaX * 89513L) ^ (coordenadaY * 15485863L);
        return sementeMacroChunk;
    }
}
