package Arvore;

public class Main {

	public static void main(String[] args) {
		
		ArvoreB b = new ArvoreB(4);
		b.setOrdem(3);
		
		No n1 = new No(3);
		n1.setN(5);
		
		No n2 = new No(4);
		n2.setN(8);
		
		b.insere(3);
		b.insere(4);
		
		System.out.println(b.getnElementos());
	}
}
