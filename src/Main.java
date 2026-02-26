import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Debug.GerarImagemMundo;
import Ferramentas.PainelMapa;
import Mundo.Mundo;

public class Main {

    static int indiceAtual = 0;
    static BufferedImage[] mapas = new BufferedImage[4];
    static PainelMapa monitor = new PainelMapa();
    static Mundo mundo;
    static JLabel seed = new JLabel("");
    static JLabel altura = new JLabel("");
    static JLabel temperatura = new JLabel("");
    static JLabel umidade = new JLabel("");
    static JLabel bioma = new JLabel("");
    static JLabel seedChunk = new JLabel("");

    

    public static void main(String[] args) {
        mundo = new Mundo(100, 100);
        mundo.criarMundo(0.6, 5, 2, 5);

        mapas[0] = GerarImagemMundo.gerarBiomeMap(mundo);
        mapas[1] = GerarImagemMundo.gerarHeightMap(mundo);
        mapas[2] = GerarImagemMundo.gerarHumidityMap(mundo);
        mapas[3] = GerarImagemMundo.gerarTemperatureMap(mundo);

        JFrame janela = new JFrame("Tela de Debug");
        janela.setLayout(new BorderLayout());
        monitor.setImage(mapas[0]);
        janela.add(monitor, BorderLayout.CENTER);
        JPanel botoes = new JPanel();
        JPanel monitoramento = new JPanel();

        seed.setText("Seed Atual: " + mundo.getSeedMundo());

        JButton proximaImagem = new JButton("Próximo >");
        proximaImagem.addActionListener(e -> {
            
            indiceAtual++;
            if (indiceAtual > 3) {
                indiceAtual = 0; 
            }
            
            monitor.setImage(mapas[indiceAtual]);
        }); 

        JButton gerarNovoMundo = new JButton("Novo Mundo");
        gerarNovoMundo.addActionListener(e ->{
            mundo = new Mundo(100, 100);
            mundo.criarMundo(0.6, 5, 2, 5);
            mapas[0] = GerarImagemMundo.gerarBiomeMap(mundo);
            mapas[1] = GerarImagemMundo.gerarHeightMap(mundo);
            mapas[2] = GerarImagemMundo.gerarHumidityMap(mundo);
            mapas[3] = GerarImagemMundo.gerarTemperatureMap(mundo);
            monitor.setImage(mapas[indiceAtual]);
            seed.setText("Seed Atual: " + mundo.getSeedMundo());
        });
        
        JButton criarImagem = new JButton("Exportar Imagem");
        criarImagem.addActionListener(e ->{
            long seedMundo = mundo.getSeedMundo();
            String caminhoPasta = "Imagens/Mundo_" + seedMundo;
            File pasta = new File(caminhoPasta);
            if (!pasta.exists()) {
                pasta.mkdirs(); 
            }
            exportarMapa(mapas[0], caminhoPasta + "/Mapa_Bioma_"+seedMundo);
            exportarMapa(mapas[1], caminhoPasta + "/Mapa_Altura_"+seedMundo);
            exportarMapa(mapas[2], caminhoPasta + "/Mapa_Umidade_"+seedMundo);
            exportarMapa(mapas[3], caminhoPasta + "/Mapa_Temperatura_"+seedMundo);
        });


        botoes.add(proximaImagem);
        botoes.add(gerarNovoMundo);
        botoes.add(criarImagem);

        janela.add(botoes, BorderLayout.SOUTH);

        monitoramento.setLayout(new java.awt.GridLayout(5,2, 0,15));
        monitoramento.setPreferredSize(new java.awt.Dimension(600, 0));
        monitoramento.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        monitoramento.add(seed);
        monitoramento.add(altura);
        monitoramento.add(temperatura);
        monitoramento.add(umidade);
        monitoramento.add(bioma);
        monitoramento.add(seedChunk);

        janela.add(monitoramento, BorderLayout.WEST);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(800, 600);

        monitor.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e){
                int pixelX = e.getX();
                int pixelY = e.getY();

                double escala = monitor.getEscalaAtual();
                int margemX = monitor.getOffsetX();
                int margemY = monitor.getOffsetY();

                int indiceX = (int) ((pixelX - margemX) / escala);
                int indiceY = (int) ((pixelY - margemY) / escala);
                
                if(indiceX >= 0 && indiceX < 100 && indiceY >= 0 && indiceY < 100){
                    var chunckClicado = mundo.getMatrizMundo()[indiceY][indiceX];
                    
                    seedChunk.setText("Semente do Chunck: " + chunckClicado.getSemente());
                    altura.setText(String.format("Altura do Chunck: %.2f", chunckClicado.getAltura()));
                    temperatura.setText(String.format("Temperatura do Chunck: %.2f", chunckClicado.getTemperatura()));
                    umidade.setText(String.format("Umidade do Chunck: %.2f", chunckClicado.getUmidade()));
                    bioma.setText("Bioma do Chunck: " + chunckClicado.getBioma());
                    
                } else {
                    seedChunk.setText("");
                    altura.setText("");
                    temperatura.setText("");
                    umidade.setText("");
                    bioma.setText("");
                }
            }
        });
        janela.setVisible(true);
    }

    private static void exportarMapa(BufferedImage imagem, String nomeArquivo){
        String caminho = nomeArquivo + ".png";
        File arquivoDeSaida = new File(caminho);
        try {
            ImageIO.write(imagem, "png", arquivoDeSaida);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
