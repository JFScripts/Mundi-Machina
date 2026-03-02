package Interface;

import java.awt.image.BufferedImage;

import Mundo.Mundo;

public interface gerarImagem {

    public BufferedImage gerarMapaMundo(Mundo mundo);
    public String getNomeString();
    
}
