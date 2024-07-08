package dados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Ludoteca {

	private List<Jogo> listaJogos;

	public Ludoteca() {listaJogos = new ArrayList<>();}

	public boolean addJogo(Jogo jogo) {
		return (listaJogos.stream().anyMatch(x -> jogo.getNome().equals(x.getNome()))) ? false : listaJogos.add(jogo);
	}
	
	public Jogo consultaPorNome(String nome) {
		return listaJogos.stream()
		.filter(jogo -> nome.equals(jogo.getNome()))
		.findFirst()
		.orElse(null);
	}
	
	public List<Jogo> consultaPorAno(int ano) {
		return listaJogos.stream()
		.filter(e -> e.getAno() == ano)
		.collect(Collectors.toList());
	}

	public List<Jogo> consultaPorCategoria(Categoria categoria) {
		return listaJogos.stream()
		.filter(JogoEletronico.class::isInstance)
		.map(JogoEletronico.class::cast)
		.filter(e -> e.getCategoria().equals(categoria))
		.collect(Collectors.toList());
	}

	public JogoTabuleiro jogoTabuleiroMaisCaro() {
   		return listaJogos.stream()
		.filter(JogoTabuleiro.class::isInstance)
		.map(jogo -> (JogoTabuleiro) jogo)
		.max(Comparator.comparingDouble(JogoTabuleiro::calculaPrecoFinal))
		.orElse(null);
	}

	public JogoTabuleiro jogoTabuleiroMaisAntigo() {
		return  listaJogos.stream()
		.filter(jogo -> jogo instanceof JogoTabuleiro)
		.map(jogo -> (JogoTabuleiro) jogo)
		.min(Comparator.comparingInt(JogoTabuleiro::getAno))
		.orElse(null);
	}

	public double somatorioJogos() {
		return listaJogos.stream()
		.mapToDouble(Jogo::calculaPrecoFinal)
		.sum();
	}

	public double mediaPrecoBase() {
		return listaJogos.stream()
		.mapToDouble(Jogo::getPrecoBase)
		.sum()/listaJogos.size();
	}

	public Jogo jogoMaisProximoPrecoBase() {
		return listaJogos.stream()
		.min(Comparator.comparingDouble(jogo -> Math.abs(jogo.getPrecoBase() - mediaPrecoBase())))
		.orElse(null);
	}

	public boolean isEmpty() {return listaJogos.isEmpty();}
}
