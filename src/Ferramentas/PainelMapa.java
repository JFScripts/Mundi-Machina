package Ferramentas;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class PainelMapa  extends JPanel{
    private BufferedImage imagemAtual;

    private double escalaAtual = 1.0;
    private int offsetX = 0;
    private int offsetY = 0;

    public void setImage(BufferedImage novImage){
        this.imagemAtual = novImage;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(imagemAtual != null){
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, java.awt.RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            int larguraPainel = getWidth();
            int alturaPainel = getHeight();
            int larguraImagem = imagemAtual.getWidth();
            int alturaImagem = imagemAtual.getHeight();

            double escalaX = (double) larguraPainel / larguraImagem;
            double escalaY = (double) alturaPainel / alturaImagem;

            double escalaFinal = Math.min(escalaX, escalaY);
            this.escalaAtual = escalaFinal;

            int novaLargura = (int) (larguraImagem * escalaFinal);
            int novaAltura = (int) (alturaImagem * escalaFinal);

            int eixoX = (larguraPainel - novaLargura) / 2;
            int eixoY = (alturaPainel - novaAltura) / 2;
            this.offsetX = eixoX;
            this.offsetY = eixoY;

            g2d.drawImage(imagemAtual, eixoX, eixoY, novaLargura, novaAltura, null);
            
        }
    }

    public BufferedImage getImagemAtual() {
        return imagemAtual;
    }

    public double getEscalaAtual() {
        return escalaAtual;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }
    


}
