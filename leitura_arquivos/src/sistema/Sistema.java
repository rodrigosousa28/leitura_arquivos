package sistema;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import dados.Aluno;
import dados.Disciplina;
import excecao.GabaritoInvalidoException;
import excecao.NenhumaDisciplinaCadastradaException;
import excecao.Validar;

public class Sistema {

	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		
		boolean bancoDeDadosNaoExiste = !(new File("C:/BancoDados/")).exists();
		
		if(bancoDeDadosNaoExiste) {
			File bancoDeDados = new File("C:/BancoDados/");
			bancoDeDados.mkdir();
		}
		String continuar = "S";
		
		
		while(continuar.equalsIgnoreCase("S")) {
			Menu.exibirMenu();
			int escolha = scan.nextInt();
			
			switch(escolha) {
			case 1: //Vai cadastrar as respostas dos alunos para correção automática
				
				System.out.print("Informe a disciplina: "); //Vai estar tudo em pastas, dentro de BancoDados, no Disco C
				String novoDiretorio = scan.next().toLowerCase();
				
				Disciplina teste = new Disciplina(novoDiretorio);
				
				boolean condicaoGabaritoOk = false;
				String gabarito = "";
				
				//Se a disciplina não existir, vai obrigar o usuário a digitar um gabarito válido
				if(!teste.existe()) {
					while(!condicaoGabaritoOk) {
						System.out.printf("%nInforme o gabarito de %s: ", teste.getNome());
						gabarito = scan.next().toUpperCase();
						
						while(gabarito.equalsIgnoreCase("VVVVVVVVVV") || gabarito.equalsIgnoreCase("FFFFFFFFFF")) {
							System.out.println("O gabarito não pode ter todas as respostas iguais");
							System.out.print("Informe um gabarito válido: ");
							gabarito = scan.next().toUpperCase();
						}
						try {
							Validar.gabarito(gabarito);
							condicaoGabaritoOk = true;
						}catch (GabaritoInvalidoException e) {
							System.out.println(e.getMessage());
						}
					}
					teste.gerarGabarito(gabarito); //Gabarito gerado apenas após a validação
					
				
				//Se já existir, o gabarito será apenas o que já existe
					condicaoGabaritoOk = false;
				}else {
					gabarito = teste.getRespostas();
						
						while(gabarito.equalsIgnoreCase("VVVVVVVVVV") || gabarito.equalsIgnoreCase("FFFFFFFFFF")) {
							System.out.println("O gabarito não pode ter todas as respostas iguais");
							System.out.print("Informe um gabarito válido: ");
							gabarito = scan.next().toUpperCase();
						}
						try {
							Validar.gabarito(gabarito);
							condicaoGabaritoOk = true;
						}catch (GabaritoInvalidoException e) {
							System.out.println(e.getMessage());
						}
						
						while(!condicaoGabaritoOk) {
							System.out.printf("%nInforme o gabarito de %s: ", teste.getNome());
							gabarito = scan.next().toUpperCase();
					}
				}
				
				while(continuar.equalsIgnoreCase("S")) {
					System.out.print("\nInforme o nome do(a) aluno(a): ");
					String nomeAluno = scan.next().toUpperCase();
					Aluno aluno = new Aluno(nomeAluno);
					
					boolean condicaoGabarito = false;
					String respostasAluno = "";
					while(!condicaoGabarito) {
						System.out.printf("Informe as respostas de %s em %s: ", aluno.getNome(), teste.getNome());
						respostasAluno = scan.next().toUpperCase();
						try {
							Validar.gabarito(respostasAluno);
							condicaoGabarito = true;
						}catch (GabaritoInvalidoException e) {
							System.out.println(e.getMessage());
						}						
					}
					
					aluno.setRespostas(respostasAluno);
					aluno.calcularResultado(teste);
					
					System.out.println("\nAguarde...");
					Sistema.esperar();
					
					System.out.printf("Dados do aluno %s registrados! A nota foi: %d. "
							+ "Registrar outro aluno? [s/n]: ", aluno.getNome(), aluno.getNota());
					continuar = scan.next().toUpperCase();
					Sistema.esperar();
				}
				
				teste.gerarResultadosOrdemNumerica();
				teste.gerarResultadosOrdemAlfabetica();
				
				break;
				
			case 2: //A escolha foi de consultar as respostas dos alunos
				System.out.print("Informe o nome da disciplina: ");
				String disciplina = scan.next();
				try {
					Disciplina d = new Disciplina(disciplina);
					System.out.println("Carregando respostas. Aguarde...");					
					Menu.esperar();
					System.out.println(d.consultarResultados());
				}catch(FileNotFoundException e) {
					System.out.println("\nNão há alunos cadastrados nesta disciplina");
				}
				
				break;
				
			case 3: //A escolha foi de consultar os resultados em ordem crescente
				System.out.print("Informe o nome da disciplina: ");
				String cadeira = scan.next();
				try {
					Disciplina cad = new Disciplina(cadeira);
					System.out.println("\nCarregando resultados em ordem crescente. Aguarde...");
					Menu.esperar();
					System.out.println(cad.getResultadosOrdemCrescente());					
				}catch(FileNotFoundException e) {
					System.out.println("\nNão há alunos cadastrados nesta disciplina");					
				}
				
				break;
				
			case 4: //A escolha foi de consultar os resultados em ordem alfabética
				System.out.print("Informe o nome da disciplina: ");
				String disc = scan.next();
				try {
					Disciplina chair = new Disciplina(disc);
					System.out.println("\nCarregando resultados em ordem alfabetica. Aguarde...");
					Menu.esperar();
					System.out.println(chair.getResultadosOrdemAlfabetica());					
				}catch(FileNotFoundException e) {
					System.out.println("\nNão há alunos cadastrados nesta disciplina");					
				}
				
				break;
				
			case 5: //A escolha foi de consultar os resultados de algum aluno
				System.out.print("Informe o nome do(a) aluno(a): ");
				String nomeAluno = scan.next().toUpperCase();
				try {
					Aluno aluno = new Aluno(nomeAluno);
					aluno.mostrarTodosResultados();	
					
				}catch (NenhumaDisciplinaCadastradaException n) {
					System.out.println(n.getMessage());
				}
				
			//	catch (FileNotFoundException e) {}
				break;
			}
			if(escolha == 6)
				break;
			
			System.out.print("\nDeseja voltar ao menu inicial? [s/n]: ");
			continuar = scan.next().toUpperCase();
		}
		scan.close();
		
		Menu.finalizarPrograma();
	}
	
	public static void esperar() {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
