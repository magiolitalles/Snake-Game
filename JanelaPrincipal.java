import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class JanelaPrincipal extends JFrame {
    private CardLayout cardLayout;
    private JPanel painelPrincipal;
    private Tabuleiro tabuleiro;
    private JPanel painelRanking; 
    private JLabel fundoLabel;    

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
        fundoLabel = new JLabel(fundoIcon); // agora é atributo
        fundoLabel.setLayout(new GridBagLayout());

        JButton btnIniciar = new JButton("Iniciar Jogo");
        btnIniciar.setPreferredSize(new Dimension(200, 50));
        btnIniciar.setFont(new Font("Arial", Font.BOLD, 22));
        btnIniciar.setFocusPainted(false);

        fundoLabel.add(btnIniciar, new GridBagConstraints());

        // Painel de ranking
        painelRanking = new JPanel(); // agora é atributo
        painelRanking.setOpaque(false);
        painelRanking.setLayout(new BoxLayout(painelRanking, BoxLayout.Y_AXIS));
        painelRanking.add(Box.createVerticalStrut(20));

        atualizarRanking(); // Preenche o ranking inicialmente

        // Adiciona o painelRanking ao fundo
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        fundoLabel.add(painelRanking, gbc);

        // Tabuleiro do jogo
        tabuleiro = new Tabuleiro(600, 400);

        painelPrincipal.add(fundoLabel, "telaInicial");
        painelPrincipal.add(tabuleiro, "tabuleiro");

        add(painelPrincipal);
        pack();
        setLocationRelativeTo(null);

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

    // Método para atualizar o ranking dinamicamente
    private void atualizarRanking() {
        painelRanking.removeAll();

        painelRanking.add(Box.createVerticalStrut(20));
        JLabel lblTitulo = new JLabel("TOP 5 Pontuações");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelRanking.add(lblTitulo);

        List<String[]> scores = Ranking.lerRanking();
        for (int i = 0; i < scores.size(); i++) {
            String nome = scores.get(i)[0];
            String pontos = scores.get(i)[1];
            JLabel lblScore = new JLabel((i+1) + "º - " + nome + " : " + pontos);
            lblScore.setFont(new Font("Arial", Font.PLAIN, 16));
            lblScore.setForeground(Color.WHITE);
            lblScore.setAlignmentX(Component.CENTER_ALIGNMENT);
            painelRanking.add(lblScore);
        }
        painelRanking.revalidate();
        painelRanking.repaint();
    }

    public void mostrarTelaInicial() {
        atualizarRanking(); // Atualiza o ranking sempre que voltar ao menu
        cardLayout.show(painelPrincipal, "telaInicial");
    }

    public static void main(String[] args) {
        new JanelaPrincipal();
    }
}
