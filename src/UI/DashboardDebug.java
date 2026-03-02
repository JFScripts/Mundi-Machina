package UI;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Ferramentas.GerenciadorDeMapas;
import Ferramentas.PainelMapa;
import Mundo.Mundo;

public class DashboardDebug {
    
    private int indiceAtual;
    private BufferedImage[] mapas;
    private PainelMapa monitor;
    private Mundo mundo;
    private JTextArea painelInfo;
    private GerenciadorDeMapas gerenciadorDeMapas;
    private JPanel painelBotoes;
    
    public DashboardDebug(BufferedImage[] mapas, Mundo mundo, GerenciadorDeMapas gerenciadorDeMapas) {
        this.mapas = mapas;
        this.mundo = mundo;
        this.gerenciadorDeMapas = gerenciadorDeMapas;
        this.indiceAtual = 0;
        this.monitor = new PainelMapa();
        this.painelInfo = new JTextArea();
        this.painelInfo.setEditable(false);
        this.painelInfo.setLineWrap(true);
        this.painelInfo.setPreferredSize(new java.awt.Dimension(400, 0));
        this.painelInfo.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 40));
        this.painelInfo.setText("Semente do Mundo: " + this.mundo.getSeedMundo());

        JFrame janela = new JFrame("Tela de Debug");
        janela.setLayout(new BorderLayout());
        monitor.setImage(mapas[0]);
        janela.add(monitor, BorderLayout.CENTER);

        janela.setSize(800, 600);
        janela.setVisible(true);

        this.painelBotoes = new javax.swing.JPanel();
        janela.add(this.painelBotoes, BorderLayout.SOUTH);

        this.monitor.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e){
                // 1. Pega a coordenada bruta do clique na tela
                int pixelX = e.getX();
                int pixelY = e.getY();

                // 2. Busca o estado atual do zoom e da movimentação da câmera
                double escala = monitor.getEscalaAtual();
                int margemX = monitor.getOffsetX();
                int margemY = monitor.getOffsetY();

                // 3. Traduz o pixel da tela para o índice exato da matriz 100x100
                int indiceX = (int) ((pixelX - margemX) / escala);
                int indiceY = (int) ((pixelY - margemY) / escala);
                
                // 4. Valida se o clique ocorreu dentro dos limites do mundo
                if(indiceX >= 0 && indiceX < 100 && indiceY >= 0 && indiceY < 100){
                    var chunckClicado = mundo.getMatrizMundo()[indiceY][indiceX];
                    
                    // A nova arquitetura em ação: o Chunk gera o próprio relatório
                    String textoRelatorio = chunckClicado.gerarRelatorio();
                    painelInfo.setText(textoRelatorio);
                    
                } else {
                    painelInfo.setText("Semente do Mundo: " + mundo.getSeedMundo());
                }
            }
        });

        adicionarBotao("Próximo >", e -> {
            this.indiceAtual++;
            if (this.indiceAtual > this.mapas.length - 1) {
                this.indiceAtual = 0; 
            }
            this.monitor.setImage(this.mapas[this.indiceAtual]);
        });
        
        adicionarBotao("Novo Mundo com Seed", e -> {
            String input = JOptionPane.showInputDialog("Digite uma Seed");
            Long seed = Long.parseLong(input);
            this.mundo = new Mundo(100, 100, seed);
            this.mundo.criarMundo(0.6, 5, 2, 5);
            

            this.mapas = this.gerenciadorDeMapas.GerarMapas(this.mundo);
            
            this.monitor.setImage(this.mapas[this.indiceAtual]);
            this.painelInfo.setText("Seed: " + this.mundo.getSeedMundo());
        });
        
        adicionarBotao("Novo Mundo", e -> {
            
            this.mundo = new Mundo(100, 100);
            this.mundo.criarMundo(0.6, 5, 2, 5);
            
            this.mapas = this.gerenciadorDeMapas.GerarMapas(this.mundo);
            
            this.monitor.setImage(this.mapas[this.indiceAtual]);
            this.painelInfo.setText("Seed: " + this.mundo.getSeedMundo());
        });

        adicionarBotao("Exportar Imagens", e -> {
            long seedMundo = this.mundo.getSeedMundo();
            String caminhoPasta = "Imagens/Mundo_" + seedMundo;
            java.io.File pasta = new java.io.File(caminhoPasta);
            
            if (!pasta.exists()) {
                pasta.mkdirs(); 
            }
            
            for(int i = 0; i < this.mapas.length; i++){
                String nomeMapa = this.gerenciadorDeMapas.getGeradores().get(i).getNomeString();
                String caminhoFinal = caminhoPasta + "/Mapa_" + nomeMapa + "_" + seedMundo;
                exportarMapa(this.mapas[i], caminhoFinal);
            }
            
            this.painelInfo.setText("Exportação concluída com sucesso!\nPasta: " + caminhoPasta);
        });

        janela.add(this.painelInfo, BorderLayout.WEST);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    private void adicionarBotao(String texto, java.awt.event.ActionListener acao) {
        javax.swing.JButton botao = new javax.swing.JButton(texto);
        botao.addActionListener(acao);
        this.painelBotoes.add(botao);
    }

    private void exportarMapa(BufferedImage imagem, String nomeArquivo) {
        String caminho = nomeArquivo + ".png";
        java.io.File arquivoDeSaida = new java.io.File(caminho);
        try {
            javax.imageio.ImageIO.write(imagem, "png", arquivoDeSaida);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            this.painelInfo.setText("Erro ao exportar imagem: " + e.getMessage());
        }
    }
}
