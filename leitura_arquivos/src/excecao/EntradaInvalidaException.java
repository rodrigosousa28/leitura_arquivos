package excecao;

public class EntradaInvalidaException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public EntradaInvalidaException() {}
	
	public String getMessage() {
		return "ERRO! A entrada deve ser um número de 1 a 6";
	}

}
