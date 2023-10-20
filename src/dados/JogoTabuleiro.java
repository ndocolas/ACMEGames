package dados;

public class JogoTabuleiro extends Jogo {

	private String nome;
	private int ano, numeroPecas;
	private double precoBase;
	
	public JogoTabuleiro(String nome, int ano, double precoBase, int numeroPecas) {
		super(nome, ano, precoBase);
		this.nome = nome;
		this.ano = ano;
		this.precoBase = precoBase;
		this.numeroPecas = numeroPecas;
	}

	@Override
	public double calculaPrecoFinal() {
		double precoPeca = 0.01*precoBase;
		precoPeca*=numeroPecas;
		return precoBase+precoPeca;
	}
	
	@Override
	public String getDescricao() {
		return String.format("%s,%d,R$ %.2f,%d,R$ %.2f", nome, ano, precoBase, numeroPecas, calculaPrecoFinal());
	}

	public int getNumeroPecas() {return numeroPecas;}

}
