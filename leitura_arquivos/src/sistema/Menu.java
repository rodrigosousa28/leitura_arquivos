package sistema;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Menu {

	private Menu() {}

	static void exibirMenu()  {
		Menu.exibirCabecalho();
		
		Menu.esperar();
		
		System.out.println("Observação: Caso queira acessar, o banco de dados com"
				+ " as disciplinas criadas está "
				+ "localizado no seguinte caminho: "
				+ "C:\\BancoDados");
		
		Menu.esperar();
		System.out.println();
		
		System.out.println("\nMenu Inicial:");
		System.out.println("\n1. Inserir respostas de alunos para correção automática");
		System.out.println("2. Consultar respostas dos alunos");
		System.out.println("3. Consultar resultados em ordem crescente");
		System.out.println("4. Consultar resultados em ordem alfabética");
		System.out.println("5. Consultar resultados de um aluno");
		System.out.println("6. Sair");
		System.out.print("\nEscolha:");
	}
	
	static String cadastrarDisciplina() {
		Scanner tec = new Scanner(System.in);
		System.out.print("\nDigite o nome da disciplina que vai cadastrar: ");
		String disciplina = tec.next();
		tec.close();
		return disciplina.toLowerCase();
	}
	
	public static void esperar() {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	static void exibirCabecalho() {
		System.out.println("-----------------------------------------------------------");
		System.out.println("-----------BEM VINDO AO SISTEMA ESCOLAR UECE---------------");
		System.out.println("-----------------------------------------------------------");
	}
	
	static void finalizarPrograma() {
		Menu.esperar();
		System.out.println();
		System.out.println("-----------------------------------------------------------");
		System.out.println("----------------FOI UM PRAZER, ATÉ MAIS--------------------");
		System.out.println("-----------------------------------------------------------");
	}
}
