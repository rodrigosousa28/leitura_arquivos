package excecao;

public class GabaritoInvalidoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private int situacao;
	
	public GabaritoInvalidoException(int situacao) {
		this.situacao = situacao;
	}
	
	public String getMessage() {
		if(this.situacao == 1) {
			return "Gabarito Invalido, precisa de 10 respostas";			
		}else if(this.situacao == 2) {
			return "Gabarito Invalido, apenas respostas V ou F aceitas";			
		}
		return null;
	}
}
