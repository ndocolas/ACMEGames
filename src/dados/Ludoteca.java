package dados;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Ludoteca implements Iterador {

	private int contador;
	private List<Jogo> listaJogos;

	public Ludoteca() {
		listaJogos = new ArrayList<>();
		contador = 0;
	}

	public boolean addJogo(Jogo jogo) {
		for (Jogo x : listaJogos) {
			if (jogo.getNome().equals(x.getNome())) {
				return false;
			}
		}
		return listaJogos.add(jogo);
	}
	//CONTINUAR DAQYU
	public Jogo consultaPorNome(String nome) {
		for (Jogo jogo : listaJogos) {
			if (nome.equals(jogo.getNome())) {
				return jogo;
			}
		}
		return null;
	}
	
	public List<Jogo> consultaPorAno(int ano) {
		List<Jogo> listaJogosAno = listaJogos.stream()
		.filter(e -> e.getAno() == ano)
		.collect(Collectors.toList());
		return listaJogosAno;
	}

	public List<Jogo> consultaPorCategoria(Categoria categoria) {
		return listaJogos.stream()
		.filter(JogoEletronico.class::isInstance)
		.map(JogoEletronico.class::cast)
		.filter(e -> e.getCategoria().equals(categoria))
		.collect(Collectors.toList());
	}

	public JogoTabuleiro jogoMaisCaro() {
		double numero = 0.0;
		JogoTabuleiro jogoMaior = null;
		for (Jogo jogo : listaJogos) {
			if (jogo instanceof JogoTabuleiro j) {
				if (j.calculaPrecoFinal() > numero) {
					numero = j.calculaPrecoFinal();
					jogoMaior = j;
				}
			}
		}
		return jogoMaior;
	}

	public JogoTabuleiro jogoMaisAntigo() {
		int ano = 10000;
		JogoTabuleiro jogoMaisAntigo = null;
		for (Jogo jogo : listaJogos) {
			if(jogo instanceof JogoTabuleiro j) {
				if(j.getAno()<ano) {
					ano = j.getAno();
					jogoMaisAntigo = j;
				}
			}
		}
		return jogoMaisAntigo;
	}

	public double somatorioJogos() {
		double contador = 0.0;
		for (Jogo jogo : listaJogos) {
			contador += jogo.calculaPrecoFinal();
		}
		return contador;
	}

	public double mediaPrecoBase() {
		double contador = 0.0;
		for (Jogo jogo : listaJogos) {
			contador += jogo.getPrecoBase();
		}
		contador /= listaJogos.size();
		return contador;
	}

	public Jogo jogoMaisProximoPrecoBase() {
		Jogo maiorJogo = null;
		double diferencaMinima = Double.MAX_VALUE;
		for (Jogo jogo : listaJogos) {
			double diferenca = Math.abs(jogo.getPrecoBase() - mediaPrecoBase());
			if (diferenca < diferencaMinima) {
				diferencaMinima = diferenca;
				maiorJogo = jogo;
			}
		}
		return maiorJogo;
	}

	public boolean isEmpty() {
		return listaJogos.isEmpty();
	}

	@Override
	public void reset() {
		contador = 0;
	}

	@Override
	public boolean hasNext() {
		if (contador == listaJogos.size()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Object next() {
		if (contador < listaJogos.size()) {
			Object o = listaJogos.get(contador);
			contador++;
			return o;
		}
		return null;
	}
}
