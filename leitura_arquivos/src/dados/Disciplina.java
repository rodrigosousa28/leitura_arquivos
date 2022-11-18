package dados;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import sistema.Menu;

public class Disciplina {

	private String nome;
	private String caminhoAbsoluto;
	private File respostasAlunos;
	private File caminhoDiretorio;
	private File gabarito;
	private File arquivosResultadosAlunos;
	private File resultadosOrdemAlfabetica;
	private File resultadosOrdemNumerica;
	private List<Aluno> alunos;
	private boolean disciplinaExiste;
	
	public Disciplina(String nome) {
		this.nome = nome;
		this.caminhoAbsoluto = "C:/BancoDados/";
		boolean arquivoNaoExiste = !(new File(caminhoAbsoluto.concat(nome))).exists();
		this.caminhoDiretorio = new File(caminhoAbsoluto.concat(nome));
		if(arquivoNaoExiste) {
			this.caminhoDiretorio.mkdir(); //Cria o diretorio, caso nao exista
			Menu.esperar();
			System.out.printf("Disciplina %s criada com sucesso!%n", this.nome);
		}
		disciplinaExiste = !arquivoNaoExiste;
		respostasAlunos = new File(this.caminhoDiretorio, "Respostas dos alunos.txt");
		resultadosOrdemNumerica = new File(this.caminhoDiretorio, "Resultados em ordem numerica.txt");
		resultadosOrdemAlfabetica = new File(this.caminhoDiretorio, "Resultados em ordem alfabetica.txt");
		
		alunos = new ArrayList<>();
	}
	
	//Caso o usuário queira passar o caminhoAbsoluto onde o arquivo irá se localizar
	public Disciplina(String nome, String caminho) {
		this.nome = nome;
		this.caminhoAbsoluto = caminho;
		boolean arquivoNaoExiste = !(new File(caminho.concat(nome))).exists();
		this.caminhoDiretorio = new File(caminhoAbsoluto.concat(nome));
		if(arquivoNaoExiste) {
			this.caminhoDiretorio.mkdir(); //Cria o diretorio no caminhoAbsoluto especificado
			Menu.esperar();
			System.out.printf("Disciplina %s criada com sucesso!%n", this.nome);
		}
		disciplinaExiste = !arquivoNaoExiste;
		respostasAlunos = new File(this.caminhoDiretorio, "Respostas dos alunos.txt");
		resultadosOrdemNumerica = new File(this.caminhoDiretorio, "Resultados em ordem numerica.txt");
		resultadosOrdemAlfabetica = new File(this.caminhoDiretorio, "Resultados em ordem alfabetica.txt");
		
		alunos = new ArrayList<>();	
	}
	
	//Usado quando o usuário informar o gabarito, que será passado como parâmetro
	public void gerarGabarito(String gab) throws IOException {
		File fileGab = new File(caminhoDiretorio, "Gabarito.txt"); //Cria um arquivo da disciplinas informada com o gabarito
		FileWriter gabarito = new FileWriter(fileGab); //permite escrever o gabarito em arquivo txt
		gabarito.write(gab); //escreve o gabarito
		gabarito.close();
		this.gabarito = fileGab;
	}
	
	public File getGabarito() {
		return gabarito;
	}
	
	//precisa ser usado, caso a disciplina já exista
	public String getRespostas() throws IOException {
		File fileGab = new File(caminhoDiretorio, "Gabarito.txt");
		boolean arquivoExiste = fileGab.exists();
		String respostaCorreta = "";
		
		if(arquivoExiste) {
			FileReader gabarito = new FileReader(fileGab);
			BufferedReader br = new BufferedReader(gabarito);
			respostaCorreta = br.readLine();
			br.close();
			gabarito.close();
			gerarGabarito(respostaCorreta);
			
		}else {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			System.out.print("Informe o gabarito: ");
			String gabarito = scanner.next().toUpperCase();
			gerarGabarito(gabarito);
			return gabarito;
		}
		return respostaCorreta;
	}
	
	private void createArquivosResultadosAlunos() {
		File resultadosSemOrdenacao = new File(caminhoDiretorio, "Resultados.txt");
		arquivosResultadosAlunos = resultadosSemOrdenacao;
	}
	
	public File getArquivosResultadosAlunos() {
		createArquivosResultadosAlunos();
		return arquivosResultadosAlunos;
	}
	
	public File getRespostasAlunos() {
		return respostasAlunos;
	}
	
	public String consultarResultados() throws IOException {
		File fileResults = new File(caminhoDiretorio, "Respostas dos alunos.txt");
		FileReader fr = new FileReader(fileResults);
		BufferedReader leitorResultados = new BufferedReader(fr);
		String linha = leitorResultados.readLine();
		String retorno = "";
		while(linha != null) {
			retorno += linha.concat("\n");
			linha = leitorResultados.readLine();
		}
		leitorResultados.close();
		fr.close();
		return retorno;
	}
	
	public void gerarResultadosOrdemAlfabetica() throws IOException {
		FileReader fr = new FileReader(getArquivosResultadosAlunos());
		BufferedReader leitorNotas = new BufferedReader(fr);
		
		String linha = leitorNotas.readLine();
		List<String> linhas = new ArrayList<>(); //Irá armazenar cada linha em uma posição na lista
		
		while(linha != null) {
			linhas.add(linha);
			linha = leitorNotas.readLine();
		}
		
		leitorNotas.close();
		
		Map<String, Integer> alunosNotas = new HashMap<>(); // Vai guardar cada linha em estrutura chave/valor
		
		List<String> nomes = new ArrayList<>();
		
		for(int i = 0; i < linhas.size(); i++) {
			String[] l = linhas.get(i).split("    "); //transformado cada linha em um array de strings de 2 posições
			String chave = l[0];
			Integer valor = Integer.parseInt(l[1]);
			alunosNotas.put(chave, valor); //Guardando a chave e valor no HashMap
			nomes.add(chave); // guardando o nome em uma lista de Strings
		}
		
		Collections.sort(nomes); //Ordenando a lista de Strings
		
		List<String> preenchimentoFinal = new ArrayList<>(); //Lista que terá o nome e nota na mesma posição, mas ordenados
		
		for(String nome: nomes) {
			for(Entry<String, Integer> entrada: alunosNotas.entrySet()) {
				if(nome.equalsIgnoreCase(entrada.getKey())) {
					preenchimentoFinal.add(nome.concat("    ").concat(Integer.toString(entrada.getValue())));
					break;
				}
			}
		}
		
		
		FileWriter orAlfa = new FileWriter(resultadosOrdemAlfabetica);
		BufferedWriter escritorNotas = new BufferedWriter(orAlfa);
		
		for(String nome: preenchimentoFinal) {
			escritorNotas.write(nome);
			escritorNotas.newLine();
		}
		        
		escritorNotas.close();
		orAlfa.close();
	}
	
	
	public void gerarResultadosOrdemNumerica() throws IOException {
		FileReader fr = new FileReader(getArquivosResultadosAlunos());
		BufferedReader leitorNotas = new BufferedReader(fr);
		
		String linha = leitorNotas.readLine();
		List<String> linhas = new ArrayList<>(); //Irá armazenar cada linha em uma posição na lista
		
		while(linha != null) {
			linhas.add(linha);
			linha = leitorNotas.readLine();
		}
		
		leitorNotas.close();
		
		Map<String, Integer> alunosNotas = new HashMap<>(); // Vai guardar cada linha em estrutura chave/valor
		
		for(int i = 0; i < linhas.size(); i++) {
			String[] l = linhas.get(i).split("    "); //transformado cada linha em um array de strings de 2 posições
			String chave = l[0];
			Integer valor = Integer.parseInt(l[1]);
			alunosNotas.put(chave, valor); //Guardando a chave e valor no HashMap
		}
				
		
		FileWriter orNum = new FileWriter(resultadosOrdemNumerica);
		BufferedWriter escritorNotas = new BufferedWriter(orNum);
		
		alunosNotas
		.entrySet()
		.stream()
		.sorted(Map.Entry.<String, Integer>comparingByValue())
		.forEach(a -> {
			try {
				escritorNotas.write(a.getKey() + "       " + a.getValue());
				escritorNotas.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		
		escritorNotas.close();
		orNum.close();
	}
	
	public String getResultadosOrdemCrescente() throws IOException {
		gerarResultadosOrdemNumerica();
		FileReader fw = new FileReader(resultadosOrdemNumerica);
		BufferedReader leitorNotas = new BufferedReader(fw);
		String linha = leitorNotas.readLine();
		String retorno = "";
		while(linha != null) {
			retorno += linha.concat("\n");
			linha = leitorNotas.readLine();
		}
		leitorNotas.close();
		fw.close();
		return retorno;
	}
	
	public String getResultadosOrdemAlfabetica() throws IOException {
		gerarResultadosOrdemAlfabetica();
		FileReader fw = new FileReader(resultadosOrdemAlfabetica);
		BufferedReader leitorNotas = new BufferedReader(fw);
		String linha = leitorNotas.readLine();
		String retorno = "";
		while(linha != null) {
			retorno += linha.concat("\n");
			linha = leitorNotas.readLine();
		}
		leitorNotas.close();
		fw.close();
		return retorno;
	}
		
	public void adicionarAluno(Aluno aluno) {
		alunos.add(aluno);
	}
	
	public String getNome() {
		return nome;
	}
	
	public boolean existe() {
		return disciplinaExiste;
	}
	
	public File getCaminhoDiretorio() {
		return caminhoDiretorio;
	}
	
	public List<Aluno> getAlunos(){
		return alunos;
	}
}
