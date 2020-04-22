package Arvore;

public class Main {

	public static void main(String[] args) {
		
		ArvoreB b = new ArvoreB(4);
		b.setOrdem(3);
		
		b.insere(3);
		b.insere(4);
		b.insere(7);
		
		b.percorreNivel(b);
		
	}
}
