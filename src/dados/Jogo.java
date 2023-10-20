package dados;

public abstract class Jogo {

	private String nome;
	private int ano;
	private double precoBase;

	public Jogo(String nome, int ano, double precoBase) {
		this.nome = nome;
		this.ano = ano;
		this.precoBase = precoBase;
	}

	public String getNome() {return nome;}
	public int getAno() {return ano;}
	public double getPrecoBase() {return precoBase;}

	public abstract double calculaPrecoFinal();
	public abstract String getDescricao();
}
