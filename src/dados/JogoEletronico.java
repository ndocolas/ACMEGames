package dados;

public class JogoEletronico extends Jogo {

	private String nome, plataforma;
	private int ano;
	private Categoria categoria;
	private double precoBase;

	public JogoEletronico(String nome, int ano, double precoBase, String plataforma, Categoria categoria) {
		super(nome, ano, precoBase);
		this.nome = nome;
		this.ano = ano;
		this.precoBase = precoBase;
		this.plataforma = plataforma;
		this.categoria = categoria;
	}

	@Override
	public double calculaPrecoFinal() {
		switch (categoria) {
			case ACT -> {return (precoBase * 1.1);}
			case SIM -> {return (precoBase * 1.3);}
			case STR -> {return (precoBase * 1.7);}
			default -> {return precoBase;}
		}
	}
	
	@Override
	public String getDescricao() {
		return String.format("%s,%d,R$ %.2f,%s,%s,R$ %.2f", nome, ano, precoBase, plataforma, categoria.getNome(), calculaPrecoFinal());
	}

	public String getPlataforma() {return plataforma;}
	public Categoria getCategoria() {return categoria;}

}
