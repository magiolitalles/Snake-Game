import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

public class Tabuleiro extends JPanel implements ActionListener, KeyListener {
    private static final int TAMANHO_BLOCO = 40;
    private int largura;
    private int altura;
    private Cobra cobra;
    private Comida comida;
    private Timer timer;
    private final int DELAY = 150;
    private boolean deveCrescer = false;
    private boolean emJogo = true;
    private int pontuacao;
    private Image imgTubarao;
    private Image imgCorpoTubarao;
    private Image imgPeixe;
    private Image imgFundo;

    public Tabuleiro(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        this.cobra = new Cobra(largura / TAMANHO_BLOCO, altura / TAMANHO_BLOCO);
        setPreferredSize(new Dimension(largura, altura));
        setBackground(Color.BLACK);
        timer = new Timer(DELAY, this);
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        comida = new Comida();
        posicionarNovaComida();
        imgTubarao = new ImageIcon("tubarao.png").getImage();
        imgCorpoTubarao = new ImageIcon("corpo_tubarao.png").getImage(); // nova imagem para o corpo
        imgPeixe = new ImageIcon("peixe.png").getImage();
        imgFundo = new ImageIcon("fundo_mar.png").getImage(); // imagem do fundo do mar
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Desenhar fundo do mar
        g.drawImage(imgFundo, 0, 0, getWidth(), getHeight(), this);

        java.util.List<Ponto> segmentos = cobra.getSegmentos();
        for (int i = 0; i < segmentos.size(); i++) {
            Ponto p = segmentos.get(i);
            int x = p.getX() * TAMANHO_BLOCO;
            int y = p.getY() * TAMANHO_BLOCO;
            if (i == 0) {
                // Cabeça do tubarão
                g.drawImage(imgTubarao, x, y, TAMANHO_BLOCO, TAMANHO_BLOCO, this);
            } else {
                // Corpo do tubarão com imagem
                g.drawImage(imgCorpoTubarao, x, y, TAMANHO_BLOCO, TAMANHO_BLOCO, this);
            }
        }
        // Desenhar peixe (comida)
        Ponto pc = comida.getPonto();
        int x = pc.getX() * TAMANHO_BLOCO;
        int y = pc.getY() * TAMANHO_BLOCO;
        g.drawImage(imgPeixe, x, y, TAMANHO_BLOCO, TAMANHO_BLOCO, this);

        desenharPontuacao(g);
        if (!emJogo) {
            desenharTelaGameOver(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!emJogo) return;
        verificarComida();
        cobra.mover(deveCrescer);
        deveCrescer = false;
        verificarColisoesComBordas();
        verificarColisaoComProprioCorpo();
        repaint();
    }

    public void iniciarJogo() {
        pontuacao = 0;
        cobra = new Cobra(largura / TAMANHO_BLOCO, altura / TAMANHO_BLOCO);
        comida = new Comida();
        posicionarNovaComida();
        deveCrescer = false;
        emJogo = true;
        timer.start();
        requestFocusInWindow();
        repaint();
    }

    public Cobra getCobra() {
        return cobra;
    }

    private void posicionarNovaComida() {
        int maxX = largura / TAMANHO_BLOCO;
        int maxY = altura / TAMANHO_BLOCO;
        comida.gerarNovaPosicao(maxX, maxY, cobra.getSegmentos());
    }

    private void verificarComida() {
        if (cobra.getCabeca().equals(comida.getPonto())) {
            deveCrescer = true;
            posicionarNovaComida();
            pontuacao++;
        }
    }

    private void verificarColisoesComBordas() {
        Ponto cabeca = cobra.getCabeca();
        int maxX = largura / TAMANHO_BLOCO;
        int maxY = altura / TAMANHO_BLOCO;
        if (cabeca.getX() < 0 || cabeca.getX() >= maxX || cabeca.getY() < 0 || cabeca.getY() >= maxY) {
            emJogo = false;
            timer.stop();
            salvarPontuacaoSeNecessario();
            // Opcional: mostrar mensagem de Game Over
        }
    }

    private void verificarColisaoComProprioCorpo() {
        Ponto cabeca = cobra.getCabeca();
        java.util.LinkedList<Ponto> corpo = cobra.getSegmentos();
        for (int i = 1; i < corpo.size(); i++) {
            if (cabeca.equals(corpo.get(i))) {
                emJogo = false;
                timer.stop();
                salvarPontuacaoSeNecessario();
                // Opcional: mostrar mensagem de Game Over
                break;
            }
        }
    }

    private void salvarPontuacaoSeNecessario() {
        String nome = JOptionPane.showInputDialog(this, "Digite seu nome para o ranking:", "Game Over", JOptionPane.PLAIN_MESSAGE);
        if (nome == null || nome.trim().isEmpty()) {
            nome = "Anônimo";
        }
        Ranking.salvarScore(nome, pontuacao);
    }

    private void desenharPontuacao(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        String textoPontuacao = "Pontuação: " + pontuacao;
        g.drawString(textoPontuacao, 10, 20);
    }

    private void desenharTelaGameOver(Graphics g) {
        String msg = "Game Over";
        String scoreMsg = "Pontuação final: " + pontuacao;
        String restartMsg = "Pressione ENTER para reiniciar";
        String menuMsg = "Pressione ESC para voltar ao menu inicial";
        g.setColor(Color.RED);
        g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 36));
        int msgWidth = g.getFontMetrics().stringWidth(msg);
        int x = (getWidth() - msgWidth) / 2;
        int y = getHeight() / 2 - 20;
        g.drawString(msg, x, y);
        g.setColor(Color.WHITE);
        g.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 20));
        int scoreWidth = g.getFontMetrics().stringWidth(scoreMsg);
        g.drawString(scoreMsg, (getWidth() - scoreWidth) / 2, y + 40);
        int restartWidth = g.getFontMetrics().stringWidth(restartMsg);
        g.drawString(restartMsg, (getWidth() - restartWidth) / 2, y + 80);
        int menuWidth = g.getFontMetrics().stringWidth(menuMsg);
        g.drawString(menuMsg, (getWidth() - menuWidth) / 2, y + 120);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (!emJogo) {
            if (keyCode == KeyEvent.VK_ENTER) {
                iniciarJogo();
                return;
            } else if (keyCode == KeyEvent.VK_ESCAPE) {
                // Voltar ao menu inicial
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                if (frame instanceof JanelaPrincipal) {
                    ((JanelaPrincipal) frame).mostrarTelaInicial();
                }
                return;
            }
        }
        Cobra.Direcao atual = cobra.getDirecaoAtual();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                if (atual != Cobra.Direcao.BAIXO) cobra.setDirecaoAtual(Cobra.Direcao.CIMA);
                break;
            case KeyEvent.VK_DOWN:
                if (atual != Cobra.Direcao.CIMA) cobra.setDirecaoAtual(Cobra.Direcao.BAIXO);
                break;
            case KeyEvent.VK_LEFT:
                if (atual != Cobra.Direcao.DIREITA) cobra.setDirecaoAtual(Cobra.Direcao.ESQUERDA);
                break;
            case KeyEvent.VK_RIGHT:
                if (atual != Cobra.Direcao.ESQUERDA) cobra.setDirecaoAtual(Cobra.Direcao.DIREITA);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
