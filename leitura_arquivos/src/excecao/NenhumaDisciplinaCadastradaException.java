package excecao;

public class NenhumaDisciplinaCadastradaException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NenhumaDisciplinaCadastradaException() {}
	
	public String getMessage() {
		return "O aluno não está cadastrado em nenhuma disciplina!";
	}
}
