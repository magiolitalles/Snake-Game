import java.awt.Color;
import java.util.LinkedList;

public class Cobra {
    public enum Direcao {
        CIMA, BAIXO, ESQUERDA, DIREITA
    }

    private LinkedList<Ponto> segmentos;
    private Direcao direcaoAtual;
    private Color cor;

    public Cobra(int larguraTabuleiro, int alturaTabuleiro) {
        segmentos = new LinkedList<>();
        // Posição inicial: centro do tabuleiro
        int x = larguraTabuleiro / 2;
        int y = alturaTabuleiro / 2;
        // Direção inicial: DIREITA
        direcaoAtual = Direcao.DIREITA;
        // Cor padrão
        cor = Color.GREEN;
        // Tamanho inicial: 3 segmentos
        segmentos.add(new Ponto(x, y));
        segmentos.add(new Ponto(x - 1, y));
        segmentos.add(new Ponto(x - 2, y));
    }

    public LinkedList<Ponto> getSegmentos() {
        return segmentos;
    }

    public Direcao getDirecaoAtual() {
        return direcaoAtual;
    }

    public void setDirecaoAtual(Direcao novaDirecao) {
        // Impede inversão direta de direção
        if ((this.direcaoAtual == Direcao.DIREITA && novaDirecao == Direcao.ESQUERDA) ||
            (this.direcaoAtual == Direcao.ESQUERDA && novaDirecao == Direcao.DIREITA) ||
            (this.direcaoAtual == Direcao.CIMA && novaDirecao == Direcao.BAIXO) ||
            (this.direcaoAtual == Direcao.BAIXO && novaDirecao == Direcao.CIMA)) {
            return;
        }
        this.direcaoAtual = novaDirecao;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }

    public Ponto getCabeca() {
        return segmentos.getFirst();
    }

    public void mover(boolean crescendo) {
        Ponto cabeca = segmentos.getFirst();
        int x = cabeca.getX();
        int y = cabeca.getY();
        switch (direcaoAtual) {
            case DIREITA:
                x += 1;
                break;
            case ESQUERDA:
                x -= 1;
                break;
            case CIMA:
                y -= 1;
                break;
            case BAIXO:
                y += 1;
                break;
        }
        Ponto novaCabeca = new Ponto(x, y);
        // Se não for crescer, remova a cauda antes de verificar colisão
        if (!crescendo) {
            segmentos.removeLast();
        }
        segmentos.addFirst(novaCabeca);
    }
}
