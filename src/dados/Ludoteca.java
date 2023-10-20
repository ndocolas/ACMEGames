package dados;

import java.util.ArrayList;

public class Ludoteca implements Iterador {

	private int contador;
	private ArrayList<Jogo> listaJogos;

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

	public Jogo consultaPorNome(String nome) {
		for (Jogo jogo : listaJogos) {
			if (nome.equals(jogo.getNome())) {
				return jogo;
			}
		}
		return null;
	}

	public ArrayList<Jogo> consultaPorAno(int ano) {
		ArrayList<Jogo> listaJogosAno = new ArrayList<>();
		for (Jogo jogo : listaJogos) {
			if (jogo.getAno() == ano) {
				listaJogosAno.add(jogo);
			}
		}
		return listaJogosAno;
	}

	public ArrayList<JogoEletronico> consultaPorCategoria(Categoria categoria) {
		ArrayList<JogoEletronico> listaJogosEletronicos = new ArrayList<>();
		for (Jogo jogo : listaJogos) {
			if (jogo instanceof JogoEletronico j) {
				if (j.getCategoria().equals(categoria)) {
					listaJogosEletronicos.add(j);
				}
			}
		}
		return listaJogosEletronicos;
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
