package dados;

import static java.lang.StringTemplate.STR;

import java.text.NumberFormat;
import java.util.Locale;

@SuppressWarnings("unused")
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
		return STR."\{nome},\{ano},\{NumberFormat.getCurrencyInstance(Locale.of("pt", "BR")).format(precoBase)},\{plataforma},\{categoria.getNome()},\{NumberFormat.getCurrencyInstance(Locale.of("pt", "BR")).format(calculaPrecoFinal())}";
	}

	public String getPlataforma() {return plataforma;}
	public Categoria getCategoria() {return categoria;}

}
