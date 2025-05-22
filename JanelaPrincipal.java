import javax.swing.JFrame;
import javax.swing.ImageIcon; // Adicionado para ícone personalizado

public class JanelaPrincipal extends JFrame {
    public JanelaPrincipal() {
        super("Shark Game - Projeto A3 - Grupo 2"); // Alterado nome da janela
        setDefaultLookAndFeelDecorated(true);       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        Tabuleiro tabuleiro = new Tabuleiro(600, 400);
        this.add(tabuleiro);
        pack();
        setLocationRelativeTo(null);

        // Define um ícone personalizado para a janela (opcional)
        setIconImage(new ImageIcon("tubarao.png").getImage());

        setVisible(true);
        tabuleiro.iniciarJogo();
    }

    public static void main(String[] args) {
        new JanelaPrincipal();
    }
}
