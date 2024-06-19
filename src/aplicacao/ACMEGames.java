package aplicacao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import dados.Categoria;
import dados.Jogo;
import dados.JogoEletronico;
import dados.JogoTabuleiro;
import dados.Ludoteca;

public class ACMEGames {

    private Scanner sc;
    private BufferedReader streamEntrada;

    private Ludoteca ludoteca;
    
    public ACMEGames() {
        try {
            sc = new Scanner((streamEntrada = new BufferedReader(new FileReader("dadosin.txt"))));
            System.setOut(new PrintStream(new File("dadosout.txt"), Charset.defaultCharset()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Locale.setDefault(Locale.ENGLISH);
        sc.useLocale(Locale.ENGLISH);

        ludoteca = new Ludoteca();
        executa();
    }

    public void executa() {
        cadastraJogosEletronico();
        cadastraJogosTabuleiro();
        lerDadosJogoNome();
        lerDadosAno();
        dadosJogoEletronico();
        somatorioJogos();
        jogoTabuleiroMaisCaro();
        jogoMaisProximoPrecoBase();
        mostrarDadosJogoTabuleiroAntigo();
    }

    private void cadastraJogosEletronico() {
        String linha;
        try {
            while ((linha = streamEntrada.readLine()) != null) {
                if (linha.equals("-1")) break;

                Scanner scanner = new Scanner(linha).useDelimiter(";");
                JogoEletronico j = new JogoEletronico(scanner.next(), scanner.nextInt(), scanner.nextDouble(), scanner.next(), Categoria.valor(scanner.next()));

                if (ludoteca.addJogo(j)) System.out.println(String.format("1:%s,R$ %.2f", j.getNome(), j.calculaPrecoFinal()));
                else System.out.println(String.format("1:Erro-jogo com nome repetido: %s", j.getNome()));

                scanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cadastraJogosTabuleiro() {
        String linha;
        try {
            while ((linha = streamEntrada.readLine()) != null) {
                if (linha.equals("-1")) break;

                Scanner scanner = new Scanner(linha).useDelimiter(";");
                JogoTabuleiro j = new JogoTabuleiro(scanner.next(), scanner.nextInt(), scanner.nextDouble(), scanner.nextInt());

                if (ludoteca.addJogo(j)) System.out.println(String.format("2:%s,R$ %.2f", j.getNome(), j.calculaPrecoFinal()));
                else System.out.println(String.format("2:Erro-jogo com nome repetido: %s", j.getNome()));
                
                scanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void lerDadosJogoNome() {
        Jogo jogo = ludoteca.consultaPorNome(sc.nextLine());
        if(!(jogo == null)) System.out.println("3:" + jogo.getDescricao());
        else System.out.println("3:Nome inexistente.");
    }

    private void lerDadosAno() {
        List<Jogo> listaJogosAno = ludoteca.consultaPorAno(sc.nextInt());
        
        if (!(listaJogosAno.isEmpty())) listaJogosAno.forEach(jogo -> System.out.println("4:" + jogo.getDescricao()));
        else System.out.println("4:Nenhum jogo encontrado.");
            
        sc.nextLine();
    }

    private void dadosJogoEletronico() {
        List<Jogo> listaJogoEletronicos = ludoteca.consultaPorCategoria(Categoria.valor(sc.nextLine()));
        if (!(listaJogoEletronicos.isEmpty())) listaJogoEletronicos.forEach(e -> System.out.println("5:" + e.getDescricao()));
        else System.out.println("5:Nenhum jogo encontrado.");
    }

    private void somatorioJogos() {
        if (!ludoteca.isEmpty()) System.out.println(String.format("6:R$ %.2f", ludoteca.somatorioJogos()));
        else System.out.println("6:Nenhum jogo encontrado.");
    }

    private void jogoTabuleiroMaisCaro() {
        JogoTabuleiro j = ludoteca.jogoTabuleiroMaisCaro();
        if(!(j == null)) System.out.println(String.format("7:%s,R$ %.2f", j.getNome(), j.calculaPrecoFinal()));
        else System.out.println("7:Nenhum jogo encontrado.");
    }

    private void jogoMaisProximoPrecoBase() {
        Jogo j = ludoteca.jogoMaisProximoPrecoBase();
        if (j != null) System.out.println(String.format("8:R$ %.2f,%s", ludoteca.mediaPrecoBase(), j.getDescricao()));
        else System.out.println("8:Nenhum jogo encontrado.");
    }

    private void mostrarDadosJogoTabuleiroAntigo() {
        JogoTabuleiro jogoMaisAntigo = ludoteca.jogoTabuleiroMaisAntigo();
        if(jogoMaisAntigo!=null) System.out.println("9:" + jogoMaisAntigo.getNome() + "," + jogoMaisAntigo.getAno());
        else System.out.println("9:Nenhum jogo encontrado.");
    }
}
