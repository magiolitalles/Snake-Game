import java.awt.Color;
import java.util.LinkedList;
import java.util.Random;

public class Comida {
    private Ponto ponto;
    private Color cor;
    private Random random;

    public Comida() {
        this.cor = Color.RED;
        this.random = new Random();
        this.ponto = new Ponto(0, 0); // posição inicial será definida depois
    }

    public Ponto getPonto() {
        return ponto;
    }

    public Color getCor() {
        return cor;
    }

    public void gerarNovaPosicao(int maxX, int maxY, LinkedList<Ponto> corpoCobra) {
        int x, y;
        do {
            x = random.nextInt(maxX);
            y = random.nextInt(maxY);
            ponto = new Ponto(x, y);
        } while (corpoCobra.contains(ponto));
    }
}
