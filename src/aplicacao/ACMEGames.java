package aplicacao;

import static java.lang.StringTemplate.STR;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import dados.Categoria;
import dados.Jogo;
import dados.JogoEletronico;
import dados.JogoTabuleiro;
import dados.Ludoteca;

@SuppressWarnings("unused")
public class ACMEGames {

    private Scanner sc;
    private BufferedReader streamEntrada;

    private Ludoteca ludoteca;
    
    public ACMEGames() {
        try {
            sc = new Scanner((streamEntrada = new BufferedReader(new FileReader("dadosin.txt"))));
            System.setOut(new PrintStream(new File("dadosout.txt"), Charset.defaultCharset()));
        } catch (Exception _) {}
        
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

                var scanner = new Scanner(linha).useDelimiter(";");
                var j = new JogoEletronico(scanner.next(), scanner.nextInt(), scanner.nextDouble(), scanner.next(), Categoria.valor(scanner.next()));

                System.out.println(ludoteca.addJogo(j) ? 
                STR."1:\{j.getNome()},\{NumberFormat.getCurrencyInstance(Locale.of("pt", "BR")).format(j.calculaPrecoFinal())}" 
                : 
                STR."1:Erro-jogo com nome repetido: \{j.getNome()}");

                scanner.close();
            }
        } catch (Exception _) {}
    }

    private void cadastraJogosTabuleiro() {
        String linha;
        try {
            while ((linha = streamEntrada.readLine()) != null) {
                if (linha.equals("-1")) break;

                var scanner = new Scanner(linha).useDelimiter(";");
                var j = new JogoTabuleiro(scanner.next(), scanner.nextInt(), scanner.nextDouble(), scanner.nextInt());

                System.out.println((ludoteca.addJogo(j)) ? 
                STR."2:\{j.getNome()},\{NumberFormat.getCurrencyInstance(Locale.of("pt", "BR")).format(j.calculaPrecoFinal())}" 
                : 
                STR."2:Erro-jogo com nome repetido: \{j.getNome()}");

                scanner.close();
            }
        } catch (Exception _) {}
    }

    private void lerDadosJogoNome() {
        var jogo = ludoteca.consultaPorNome(sc.nextLine());
        System.out.println(!(jogo == null) ? STR."3:\{jogo.getDescricao()}" : "3:Nome inexistente.");
    }

    private void lerDadosAno() {
        List<Jogo> listaJogosAno = ludoteca.consultaPorAno(Integer.parseInt(sc.nextLine()));
        
        if (!(listaJogosAno.isEmpty())) listaJogosAno.forEach(jogo -> System.out.println("4:" + jogo.getDescricao()));
        else System.out.println("4:Nenhum jogo encontrado.");
    }

    private void dadosJogoEletronico() {
        List<Jogo> listaJogoEletronicos = ludoteca.consultaPorCategoria(Categoria.valor(sc.nextLine()));
        if (!(listaJogoEletronicos.isEmpty())) listaJogoEletronicos.forEach(e -> System.out.println("5:" + e.getDescricao()));
        else System.out.println("5:Nenhum jogo encontrado.");
    }

    private void somatorioJogos() {
        System.out.println(!ludoteca.isEmpty() ? STR."6:\{NumberFormat.getCurrencyInstance(Locale.of("pt", "BR")).format(ludoteca.somatorioJogos())}" : "6:Nenhum jogo encontrado.");
    }

    private void jogoTabuleiroMaisCaro() {
        var j = ludoteca.jogoTabuleiroMaisCaro();
        System.out.println((j != null) ? STR."7:\{j.getNome()},\{NumberFormat.getCurrencyInstance(Locale.of("pt", "BR")).format(j.calculaPrecoFinal())}" : "7:Nenhum jogo encontrado.");
    }

    private void jogoMaisProximoPrecoBase() {
        var j = ludoteca.jogoMaisProximoPrecoBase();
        System.out.println(j != null ? STR."8:\{NumberFormat.getCurrencyInstance(Locale.of("pt", "BR")).format(ludoteca.mediaPrecoBase())},\{j.getDescricao()}" : "8:Nenhum jogo encontrado.");
    }

    private void mostrarDadosJogoTabuleiroAntigo() {
        var jogoMaisAntigo = ludoteca.jogoTabuleiroMaisAntigo();
        System.out.println(jogoMaisAntigo!=null ? STR."9:\{jogoMaisAntigo.getNome()},\{jogoMaisAntigo.getAno()}" : "9:Nenhum jogo encontrado.");
    }
}
