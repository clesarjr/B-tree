import java.util.Vector;

public class No {

    private int n; //Atributo que guarda a quantidade de chaves no nó
    private Vector<Integer> chave; //vetor das chaves
    private Vector<No> filho;//vetor dos filhos
    private boolean folha;//Atributo que indica se a nó eh folha ou nao
    private int X;//Atributo que guarda a posicao X onde o Nó deve aparecer na tela
    private int Y;//Atributo que guarda a posicao Y onde o Nó deve aparecer na tela
    private int larguraFilho;            
    final int DIFERENCA_ALTURA = 30;
    final int DIFERENCA_IRMAOS = 5;

    public No(int n) {
        this.chave = new Vector<Integer>(n - 1);
        for (int i = 0; i < n - 1; i++) {
            this.chave.add(null);
        }
        this.filho = new Vector<No>(n);
        for (int i = 0; i < n; i++) {
            this.filho.add(null);
        }
        this.folha = true;
        this.n = 0;
    }