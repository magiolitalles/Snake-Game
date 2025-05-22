import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaPrincipal extends JFrame {
    private CardLayout cardLayout;
    private JPanel painelPrincipal;
    private Tabuleiro tabuleiro;

    public JanelaPrincipal() {
        super("Shark Game - Projeto A3 - Grupo 2");
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setIconImage(new ImageIcon("tubarao.png").getImage());

        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);

        // Tela inicial com fundo do mar
        ImageIcon fundoIcon = new ImageIcon("fundo_mar.png");
        JLabel fundoLabel = new JLabel(fundoIcon);
        fundoLabel.setLayout(new GridBagLayout()); // Permite centralizar o botão

        JButton btnIniciar = new JButton("Iniciar Jogo");
        btnIniciar.setPreferredSize(new Dimension(200, 50));
        btnIniciar.setFont(new Font("Arial", Font.BOLD, 22));
        btnIniciar.setFocusPainted(false);

        // Centraliza o botão no fundo
        fundoLabel.add(btnIniciar, new GridBagConstraints());

        // Tabuleiro do jogo
        tabuleiro = new Tabuleiro(600, 400);

        painelPrincipal.add(fundoLabel, "telaInicial");
        painelPrincipal.add(tabuleiro, "tabuleiro");

        add(painelPrincipal);
        pack();
        setLocationRelativeTo(null);

        // Ação do botão
        btnIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(painelPrincipal, "tabuleiro");
                tabuleiro.iniciarJogo();
                tabuleiro.requestFocusInWindow();
            }
        });

        setVisible(true);
        cardLayout.show(painelPrincipal, "telaInicial");
    }

    public void mostrarTelaInicial() {
        cardLayout.show(painelPrincipal, "telaInicial");
    }

    public static void main(String[] args) {
        new JanelaPrincipal();
    }
}
