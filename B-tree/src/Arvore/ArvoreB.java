package Arvore;

import java.util.Collection;
import java.util.LinkedList;

public class ArvoreB {

	//Atributos da Classe ArvoreB
    private No raiz; //Atributo do Nó raiz;
    private int ordem; //Ordem da Arvore-B;
    private int nElementos; //Contador para a quantidade de elementos na arvore B;

    //Construtor
    public ArvoreB(int n) {
        this.raiz = new No(n);
        this.ordem = n;
        nElementos = 0;
    }  

    //Set e get
    public int getnElementos() {
        return nElementos;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public int getOrdem() {
        return ordem;
    }

    public No getRaiz() {
        return raiz;
    }

    //Metodo de Inserção
    //parametros: k - chave a ser inserida
    public void insere(int k) {
        if (BuscaChave(raiz, k) == null) { 
            if (raiz.getN() == 0) {
                raiz.getChave().set(0, k);
                raiz.setN(raiz.getN() + 1);
            } else { 
                No r = raiz;
                if (r.getN() == ordem - 1) {
                    No s = new No(ordem);
                    raiz = s;
                    s.setFolha(false);
                    s.setN(0);
                    s.getFilho().set(0, r);
                    divideNo(s, 0, r);
                    insereNoNaoCheio(s, k);

                } else {
                    insereNoNaoCheio(r, k);
                }
            }
            nElementos++;
        }
    }

    //Método de busca de uma chave
    //Parâmetros: X - nó por onde começar a busca, k - chave a ser buscada
    public No BuscaChave(No X, int k) {
        int i = 1;
        while ((i <= X.getN()) && (k > X.getChave().get(i - 1))) { 
            i++;
        }
        if ((i <= X.getN()) && (k == X.getChave().get(i - 1))) {
            return X;
        }
        if (X.isFolha()) {
            return null;
        } else {
            return (BuscaChave(X.getFilho().get(i - 1), k));
        }
    }

    //Método de divisão de nó
    //Parâmetros: x - nó Pai, y - nó Filho e i - índice i que indica que y é o i-ésimo filho de x.
    public void divideNo(No x, int i, No y) {
        int t = (int) Math.floor((ordem - 1) / 2);
        No z = new No(ordem);
        z.setFolha(y.isFolha());
        z.setN(t);

        for (int j = 0; j < t; j++) {
            if ((ordem - 1) % 2 == 0) {
                z.getChave().set(j, y.getChave().get(j + t));
            } else {
                z.getChave().set(j, y.getChave().get(j + t + 1));
            }
            y.setN(y.getN() - 1);
        }

        if (!y.isFolha()) {
            for (int j = 0; j < t + 1; j++) {
                if ((ordem - 1) % 2 == 0) {
                    z.getFilho().set(j, y.getFilho().get(j + t));
                } else {
                    z.getFilho().set(j, y.getFilho().get(j + t + 1));
                }

            }
        }

        y.setN(t);

        for (int j = x.getN(); j > i; j--) { 
            x.getFilho().set(j + 1, x.getFilho().get(j));
        }

        x.getFilho().set(i + 1, z);

        for (int j = x.getN(); j > i; j--) {
            x.getChave().set(j, x.getChave().get(j - 1));
        }

        if ((ordem - 1) % 2 == 0) {
            x.getChave().set(i, y.getChave().get(t - 1));
            y.setN(y.getN() - 1);
            
        } else {
            x.getChave().set(i, y.getChave().get(t));
        }

        x.setN(x.getN() + 1);
    }

    //Método para inserir uma chave em um nó não cheio
    //Paâmetros: x - nó a ser inserido, k - chave a ser inserida no nó x
    public void insereNoNaoCheio(No x, int k) {
        int i = x.getN() - 1;
        if (x.isFolha()) {
            while (i >= 0 && k < x.getChave().get(i)) {
                x.getChave().set(i + 1, x.getChave().get(i));
                i--;
            }
            i++;
            x.getChave().set(i, k);
            x.setN(x.getN() + 1);

        } else {
            while ((i >= 0 && k < x.getChave().get(i))) {
                i--;
            }
            i++;
            if ((x.getFilho().get(i)).getN() == ordem - 1) {
                divideNo(x, i, x.getFilho().get(i));
                if (k > x.getChave().get(i)) {
                    i++;
                }
            }
            insereNoNaoCheio(x.getFilho().get(i), k);
        }

    }

    //Método de Remoção de uma determinada chave da arvoreB
    public void Remove(int k) {
        if (BuscaChave(this.raiz, k) != null) {
            No N = BuscaChave(this.raiz, k);
            int i = 1;

            while (N.getChave().get(i - 1) < k) {
                i++;
            }

            if (N.isFolha()) {
                for (int j = i + 1; j <= N.getN(); j++) {
                    N.getChave().set(j - 2, N.getChave().get(j - 1));
                }
                N.setN(N.getN() - 1);
                if (N != this.raiz) {
                    Balanceia_Folha(N);
                }
            } else {
                No S = Antecessor(this.raiz, k);
                int y = S.getChave().get(S.getN() - 1);
                S.setN(S.getN() - 1);
                N.getChave().set(i - 1, y);
                Balanceia_Folha(S);
            }
            nElementos--;
        }
    }

    //Métode de Balancear um nó folha
    //Parâmetros: F - nó Folha a ser balanceada
    private void Balanceia_Folha(No F) {
        if (F.getN() < Math.floor((ordem - 1) / 2)) {
            No P = getPai(raiz, F);
            int j = 1;

            while (P.getFilho().get(j - 1) != F) {
                j++;
            }

            if (j == 1 || (P.getFilho().get(j - 2)).getN() == Math.floor((ordem - 1) / 2)) {
                if (j == P.getN() + 1 || (P.getFilho().get(j).getN() == Math.floor((ordem - 1) / 2))) {
                    Diminui_Altura(F);
                } else {
                    Balanceia_Dir_Esq(P, j - 1, P.getFilho().get(j), F);
                }
            } else {
                Balanceia_Esq_Dir(P, j - 2, P.getFilho().get(j - 2), F);
            }
        }
    }

    //Método para diminuir a altura
    //Parâmetros: X - nó onde vai ser diminuido a altura
    private void Diminui_Altura(No X) {
        int j;
        No P = new No(ordem);

        if (X == this.raiz) {
            if (X.getN() == 0) {
                this.raiz = X.getFilho().get(0);
                X.getFilho().set(0, null);
            }
        } else {
            int t = (int) Math.floor((ordem - 1) / 2);
            if (X.getN() < t) {
                P = getPai(raiz, X);
                j = 1;

                while (P.getFilho().get(j - 1) != X) {
                    j++;
                }

                if (j > 1) {
                    Juncao_No(getPai(raiz, X), j - 1);
                } else {
                    Juncao_No(getPai(raiz, X), j);
                }
                Diminui_Altura(getPai(raiz, X));
            }
        }
    }

    //Mótodo de Balancear da esquerda para a direita
    //Parâmetros: P - Nó pai, e - indica que Esq é o e-ésimo filho de P, Esq - Nó da esquerda, Dir - Nó da direita
    private void Balanceia_Esq_Dir(No P, int e, No Esq, No Dir) {
        for (int i = 0; i < Dir.getN(); i++) {
            Dir.getChave().set(i + 1, Dir.getChave().get(i));
        }

        if (!Dir.isFolha()) {
            for (int i = 0; i > Dir.getN(); i++) {
                Dir.getFilho().set(i + 1, Dir.getFilho().get(i));
            }
        }
        Dir.setN(Dir.getN() + 1);
        Dir.getChave().set(0, P.getChave().get(e));
        P.getChave().set(e, Esq.getChave().get(Esq.getN() - 1));
        Dir.getFilho().set(0, Esq.getFilho().get(Esq.getN()));
        Esq.setN(Esq.getN() - 1);
    }
    
    //Método de Balancear da direita para a esquerda
    //Parâmetros: P - Nó pai, e - indica que Dir é o e-ésimo filho de P, Dir - Nó da direita, Esq - Nó da esquerda
    private void Balanceia_Dir_Esq(No P, int e, No Dir, No Esq) {

        Esq.setN(Esq.getN() + 1);
        Esq.getChave().set(Esq.getN() - 1, P.getChave().get(e));
        P.getChave().set(e, Dir.getChave().get(0));
        Esq.getFilho().set(Esq.getN(), Dir.getFilho().get(0));

        for (int j = 1; j < Dir.getN(); j++) {
            Dir.getChave().set(j - 1, Dir.getChave().get(j));
        }

        if (!Dir.isFolha()) {
            for (int i = 1; i < Dir.getN()+1 ; i++) {
                Dir.getFilho().set(i - 1, Dir.getFilho().get(i));
            }
        }

        Dir.setN(Dir.getN() - 1);
    }

     //Método para junção do nó
    //Parâmetros: X - No pai, i - posicao do filho de X onde vai ser juntado
    private void Juncao_No(No X, int i) {
        No Y = X.getFilho().get(i - 1); 
        No Z = X.getFilho().get(i);

        int k = Y.getN();
        Y.getChave().set(k, X.getChave().get(i - 1));

        for (int j = 1; j <= Z.getN(); j++) {
            Y.getChave().set(j + k, Z.getChave().get(j - 1));
        }

        if (!Z.isFolha()) {
            for (int j = 1; j <= Z.getN(); j++) {
                Y.getFilho().set(j + k, Z.getFilho().get(j - 1));
            }
        }

        Y.setN(Y.getN() + Z.getN() + 1);
        
        X.getFilho().set(i, null);

        for (int j = i; j <= X.getN() - 1; j++) {
            X.getChave().set(j - 1, X.getChave().get(j));
            X.getFilho().set(j, X.getFilho().get(j + 1));
        }
        X.setN(X.getN() - 1);
    }

    private No Antecessor(No N, int k) {
        int i = 1;
        while (i <= N.getN() && N.getChave().get(i - 1) < k) {
            i++;
        }
        if (N.isFolha()) {
            return N;
        } else {
            return Antecessor(N.getFilho().get(i - 1), k);
        }
    }
    
    //Metodo que retorna o nó pai de N
    //Parâmetros: T - Nó onde começa a busca, N - nó que deve se buscar o pai
    private No getPai(No T, No N) {
        if (this.raiz == N) {
            return null;
        }
        for (int j = 0; j <= T.getN(); j++) {
            if (T.getFilho().get(j) == N) {
                return T;
            }
            if (!T.getFilho().get(j).isFolha()) {
                No X = getPai(T.getFilho().get(j), N);
                if (X != null) {
                    return X;
                }
            }
        }
        return null;
    }
    
    //método para percorrer a árvore por nível
  	public void percorreNivel(ArvoreB aux) {
  		LinkedList<ArvoreB> fila = new LinkedList<ArvoreB>();
  		fila.add(aux);
  		
  		int cont = 0;
  		while(fila.size() != 0) {
  			cont++;
  			ArvoreB r = fila.remove();
  			System.out.println(" " + r.getRaiz().getChave());
  			if(r.getRaiz().getFilho() != null)
  				System.out.println(" " + r.getRaiz().getFilho().get(cont).getChave());
  		}
  	}
}
