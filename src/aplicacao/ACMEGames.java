package aplicacao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
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
                if (linha.equals("-1")) {
                    break;
                }

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
        String nome, linha;
        int ano;
        double precoBase;
        int numeroPecas;
        try {
            while ((linha = streamEntrada.readLine()) != null) {
                if (linha.equals("-1")) {
                    break;
                }
                Scanner scanner = new Scanner(linha).useDelimiter(";");
                nome = scanner.next();
                ano = scanner.nextInt();
                precoBase = scanner.nextDouble();
                numeroPecas = scanner.nextInt();

                JogoTabuleiro j = new JogoTabuleiro(nome, ano, precoBase, numeroPecas);
                if (ludoteca.addJogo(j)) {
                    System.out.println(String.format("2:%s,R$ %.2f", j.getNome(), j.calculaPrecoFinal()));
                } else {
                    System.out.println(String.format("2:Erro-jogo com nome repetido: %s", j.getNome()));
                }
                scanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void lerDadosJogoNome() {
        try {
            Jogo jogo = ludoteca.consultaPorNome(sc.nextLine());
            if (jogo == null) {
                throw new NullPointerException();
            }
            System.out.println("3:" + jogo.getDescricao());
        } catch (NullPointerException e) {
            System.out.println("3:Nome inexistente.");
        }
    }

    private void lerDadosAno() {
        try {
            ArrayList<Jogo> listaJogosAno = ludoteca.consultaPorAno(sc.nextInt());
            if (listaJogosAno == null) {
                throw new NoSuchElementException();
            }
            if (!(listaJogosAno.isEmpty())) {
                for (Jogo jogo : listaJogosAno) {
                    System.out.println("4:" + jogo.getDescricao());
                }
            } else {
                System.out.println("4:Nenhum jogo encontrado.");
            }
            sc.nextLine();
        } catch (NoSuchElementException e) {
            System.out.println("4:Ano nao encontrado.");
        } catch (Exception e) {
        }
    }

    private void dadosJogoEletronico() {
        String categoria = sc.nextLine();
        try {
            if (Categoria.isValid(Categoria.valor(categoria)) == false) {
                throw new NullPointerException();
            }

            ArrayList<JogoEletronico> listaJogoEletronicos = ludoteca.consultaPorCategoria(Categoria.valor(categoria));
            if (!(listaJogoEletronicos.isEmpty())) {
                for (JogoEletronico jogoEletronico : listaJogoEletronicos) {
                    System.out.println("5:" + jogoEletronico.getDescricao());
                }
            } else {
                System.out.println("5:Nenhum jogo encontrado.");
            }
        } catch (NullPointerException e) {
            System.out.println("5:Categoria inexistente.");
        }
    }

    private void somatorioJogos() {
        if (!ludoteca.isEmpty()) {
            System.out.println(String.format("6:R$ %.2f", ludoteca.somatorioJogos()));
        } else {
            System.out.println("6:Nenhum jogo encontrado.");
        }
    }

    private void jogoTabuleiroMaisCaro() {
        try {
            JogoTabuleiro j = ludoteca.jogoMaisCaro();
            if (j == null) {
                throw new NullPointerException();
            }
            System.out.println(String.format("7:%s,R$ %.2f", j.getNome(), j.calculaPrecoFinal()));
        } catch (NullPointerException e) {
            System.out.println("7:Nenhum jogo encontrado.");
        }
    }

    private void jogoMaisProximoPrecoBase() {
        Jogo j = ludoteca.jogoMaisProximoPrecoBase();
        if (j != null) {
            System.out.println(String.format("8:R$ %.2f,%s", ludoteca.mediaPrecoBase(), j.getDescricao()));
        } else {
            System.out.println("8:Nenhum jogo encontrado.");
        }
    }

    private void mostrarDadosJogoTabuleiroAntigo() {
        JogoTabuleiro jogoMaisAntigo = ludoteca.jogoMaisAntigo();
        if(jogoMaisAntigo!=null) {
            System.out.println("9:" + jogoMaisAntigo.getNome() + "," + jogoMaisAntigo.getAno());
        } else {
            System.out.println("9:Nenhum jogo encontrado.");
        }
    }
}