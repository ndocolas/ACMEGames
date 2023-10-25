package aplicacao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import dados.Categoria;
import dados.Jogo;
import dados.JogoEletronico;
import dados.JogoTabuleiro;
import dados.Ludoteca;

public class ACMEGames {

    private Scanner sc;
    private Ludoteca ludoteca;
    private BufferedReader streamEntrada;

    public ACMEGames() {
        try {
            streamEntrada = new BufferedReader(new FileReader("dadosin.txt"));
            sc = new Scanner(streamEntrada);
            PrintStream streamSaida = new PrintStream(new File("dadosout.txt"), Charset.defaultCharset());
            System.setOut(streamSaida);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ludoteca = new Ludoteca();
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
        String linha, nome;
        int ano;
        double precoBase;
        String plataforma;
        Categoria categoria;
        try {
            while ((linha = streamEntrada.readLine()) != null) {
                if (linha.equals("-1")) break;
                

                Scanner scanner = new Scanner(linha).useDelimiter(";");

                nome = scanner.next();
                ano = scanner.nextInt();
                precoBase = scanner.nextDouble();
                plataforma = scanner.next();
                categoria = Categoria.valor(scanner.next());

                JogoEletronico j = new JogoEletronico(nome, ano, precoBase, plataforma, categoria);

                if (ludoteca.addJogo(j)) {
                    System.out.println(String.format("1:%s,R$ %.2f", j.getNome(), j.calculaPrecoFinal()));
                } else {
                    System.out.println(String.format("1:Erro-jogo com nome repetido: %s", j.getNome()));
                }

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
                String nome = scanner.next();
                int ano = scanner.nextInt();
                double precoBase = scanner.nextDouble();
                int numeroPecas = scanner.nextInt();

                JogoTabuleiro j = new JogoTabuleiro(nome, ano, precoBase, numeroPecas);

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
        String categoria = sc.nextLine();
        List<Jogo> listaJogoEletronicos = ludoteca.consultaPorCategoria(Categoria.valor(categoria));
        if (!(listaJogoEletronicos.isEmpty())) listaJogoEletronicos.forEach(e -> System.out.println("5:" + e.getDescricao()));
        else System.out.println("5:Nenhum jogo encontrado.");

    }

    private void somatorioJogos() {
        if (!ludoteca.isEmpty())  System.out.println(String.format("6:R$ %.2f", ludoteca.somatorioJogos()));
        else System.out.println("6:Nenhum jogo encontrado.");
        
    }

    private void jogoTabuleiroMaisCaro() {
        JogoTabuleiro j = ludoteca.jogoMaisAntigo();
        if(!(j == null)) System.out.println(String.format("7:%s,R$ %.2f", j.getNome(), j.calculaPrecoFinal()));
        else System.out.println("7:Nenhum jogo encontrado.");
    }

    private void jogoMaisProximoPrecoBase() {
        Jogo j = ludoteca.jogoMaisProximoPrecoBase();
        if (j != null) System.out.println(String.format("8:R$ %.2f,%s", ludoteca.mediaPrecoBase(), j.getDescricao()));
        else System.out.println("8:Nenhum jogo encontrado.");
    }

    private void mostrarDadosJogoTabuleiroAntigo() {
        JogoTabuleiro jogoMaisAntigo = ludoteca.jogoMaisAntigo();
        if(jogoMaisAntigo!=null) System.out.println("9:" + jogoMaisAntigo.getNome() + "," + jogoMaisAntigo.getAno());
        else System.out.println("9:Nenhum jogo encontrado.");
    }
}