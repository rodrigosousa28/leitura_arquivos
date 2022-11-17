package sistema;

import java.util.concurrent.TimeUnit;

public class Menu {

	private Menu() {}

	static void exibirMenu()  {
		Menu.exibirCabecalho();
		
		Menu.esperar();
		
		System.out.println("\nObservação 1: Caso queira acessar, o banco de dados com"
				+ "\nas disciplinas criadas está "
				+ "localizado no seguinte caminho: "
				+ "C:\\BancoDados");
		System.out.println("\nObservação 2: Caso você informe uma disciplina que não existe, "
				+ "ela será criada automaticamente no banco de dados");
		
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
