package dados;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Aluno {

	private String nome;
	private String respostas;
	private List<Disciplina> disciplinas;
	private int nota;
	
	
	public Aluno(String nome) {
		this.nome = nome;
		disciplinas = new ArrayList<>();
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getRespostas() {
		return respostas;
	}
	
	public void setRespostas(String respostas) {
		this.respostas = respostas;
	}
	
	public int getNota() {
		return nota;
	}
	
	public void adicionarDisciplina(Disciplina disciplina) {
		disciplinas.add(disciplina);
	}
	
	//vai pegar a disciplina em questão, calcular o resultado, e salvar no arquivo ao qual ela pertence, chamando o método getDiretorio
	public void calcularResultado(Disciplina disciplina) throws IOException {
		adicionarDisciplina(disciplina);
		
		File respostasAluno = disciplina.getRespostasAlunos();
		FileWriter fw = new FileWriter(respostasAluno, true); //o true impede a sobrescrita
		BufferedWriter escritorRespostas = new BufferedWriter(fw);
		escritorRespostas.write(this.respostas + "    " + this.nome);
		escritorRespostas.newLine();
		escritorRespostas.close();
		fw.close();
		
		setRespostas(respostas);
		File gabarito = disciplina.getGabarito();
		FileReader lGabarito = new FileReader(gabarito);
		BufferedReader leitorGabarito = new BufferedReader(lGabarito);
		String respostasGabarito = leitorGabarito.readLine();
		leitorGabarito.close();
		lGabarito.close();
		
		int nota = 0;
		for(int i = 0; i < respostas.length(); i++) {
			if(respostas.charAt(i) == respostasGabarito.charAt(i)) {
				nota++;
			}
		}
		this.nota = nota;
		
		File arquivosResultadosAlunos = disciplina.getArquivosResultadosAlunos();
		FileWriter resultadosDesordenados = new FileWriter(arquivosResultadosAlunos, true); //o true impede a sobrescrita
		BufferedWriter bw = new BufferedWriter(resultadosDesordenados);
		bw.write(this.nome + "    " + this.nota);
		bw.newLine();
		bw.close();
		resultadosDesordenados.close();
	}
	
	public void mostrarTodosResultados() throws IOException {
		String path = "C:/BancoDados"; 
        File file = new File(path);
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return new File(dir, name).isDirectory();
            }
        });
        
        
        for(int i = 0; i < directories.length; i++) {
        	File resultadosAlunos = new File(path.concat("/").concat(directories[i]).concat("/Resultados.txt"));
        	FileReader fr = new FileReader(resultadosAlunos);
        	BufferedReader leitorResultados = new BufferedReader(fr);
        	String linha = leitorResultados.readLine();
        	while(linha != null) {
        		if(linha.contains(this.getNome())) {
        			System.out.println("\n" + directories[i]);
					String[] dados = linha.split("    ");
					System.out.println("Nota: " + dados[1]);
					break;
        		}
        		linha = leitorResultados.readLine();
        	}
        	leitorResultados.close();
			fr.close();
        }        
	}
	
}
