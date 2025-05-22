import javax.swing.JFrame;

public class JanelaPrincipal extends JFrame {

   public JanelaPrincipal() {
      super("Jogo da Cobrinha");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setResizable(false);
      Tabuleiro tabuleiro = new Tabuleiro(600, 400);
      this.add(tabuleiro);
      pack();
      setLocationRelativeTo(null);
      setVisible(true);
      tabuleiro.iniciarJogo();
   }

   public static void main(String[] args) {
      new JanelaPrincipal();
   }
}

