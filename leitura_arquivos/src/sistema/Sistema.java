package sistema;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import dados.Aluno;
import dados.Disciplina;
import excecao.GabaritoInvalidoException;
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
			case 1:
				System.out.print("Informe a disciplina: "); //Vai estar tudo em pastas, dentro de BancoDados, no Disco C
				String novoDiretorio = scan.next();
				
				Disciplina teste = new Disciplina(novoDiretorio);
				
				boolean condicaoGabaritoOk = false;
				String gabarito = "";
				
				//Se a disciplina n�o existir, vai obrigar o usu�rio a digitar um gabarito v�lido
				if(!teste.existe()) {
					while(!condicaoGabaritoOk) {
						System.out.printf("%nInforme o gabarito de %s: ", teste.getNome());
						gabarito = scan.next().toUpperCase();
						try {
							Validar.gabarito(gabarito);
							condicaoGabaritoOk = true;
						}catch (GabaritoInvalidoException e) {
							System.out.println(e.getMessage());
						}
					}
					teste.gerarGabarito(gabarito); //Gabarito gerado apenas ap�s a valida��o			
				
				//Se j� existir, o gabarito ser� apenas o que j� existe
				}else {
					gabarito = teste.getRespostas();
				}
				
				while(continuar.equalsIgnoreCase("S")) {
					System.out.print("Informe o nome do(a) aluno(a): ");
					String nomeAluno = scan.next();
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
					
					System.out.printf("Dados do aluno %s registrados! A nota foi: %d. Deseja continuar? [s/n]: ", aluno.getNome(), aluno.getNota());
					continuar = scan.next().toUpperCase();
					Sistema.esperar();
				}
				
				teste.gerarResultadosOrdemNumerica();
				teste.gerarResultadosOrdemAlfabetica();
				
				break;
				
			case 2: //A escolha foi de consultar as respostas dos alunos
				System.out.print("Informe o nome da disciplina: ");
				String disciplina = scan.next();
				Disciplina d = new Disciplina(disciplina);
				System.out.println("Carregando respostas. Aguarde...");
				Menu.esperar();
				System.out.println(d.consultarResultados());
				
				break;
				
			case 3: //A escolha foi de consultar os resultados em ordem crescente
				System.out.print("Informe o nome da disciplina: ");
				String cadeira = scan.next();
				Disciplina cad = new Disciplina(cadeira);
				System.out.println("\nCarregando resultados em ordem crescente. Aguarde...");
				Menu.esperar();
				System.out.println(cad.getResultadosOrdemCrescente());
				break;
				
			case 4: //A escolha foi de consultar os resultados em ordem alfab�tica
				System.out.print("Informe o nome da disciplina: ");
				String disc = scan.next();
				Disciplina chair = new Disciplina(disc);
				System.out.println("\nCarregando resultados em ordem alfabetica. Aguarde...");
				Menu.esperar();
				System.out.println(chair.getResultadosOrdemAlfabetica());
				break;
				
			case 5: //A escolha foi de consultar os resultados de algum aluno
				System.out.print("Informe o nome do(a) aluno(a): ");
				String nomeAluno = scan.next();
				Aluno aluno = new Aluno(nomeAluno);
				aluno.mostrarTodosResultados();
				break;
			}
			
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