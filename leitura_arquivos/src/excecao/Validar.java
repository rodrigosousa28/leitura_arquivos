package excecao;

import java.util.InputMismatchException;

public class Validar {

	private Validar() {}
	
	//Usar na classe main para validar se o gabarito tem 10 caracteres e se todos são 'V' ou 'F'
	public static void gabarito(String gabarito) {
		if(gabarito.length() != 10) {
			throw new GabaritoInvalidoException(1);
		}else {
			for(int i = 0; i < gabarito.length(); i++) {
				if(gabarito.charAt(i) != 'V' && gabarito.charAt(i) != 'F') {
					throw new GabaritoInvalidoException(2);
				}
			}
		}
	}
	
	public static void entrada(int entrada) {
		
		boolean condicaoOk = entrada >= 1 && entrada <= 6;
		if(!condicaoOk) {
			throw new EntradaInvalidaException();
		}
		 
	}
	
}
