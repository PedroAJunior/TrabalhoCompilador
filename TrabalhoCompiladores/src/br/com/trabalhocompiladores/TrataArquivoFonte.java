package br.com.trabalhocompiladores;



import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class TrataArquivoFonte {
	private static JFileChooser leitor;
	
	private TrataArquivoFonte() {
	}
		
	public static String LerArquivo(){
		String conteudo = null;
		
		try {
			leitor = new JFileChooser();
			int retorno = leitor.showOpenDialog(null);
			
			//Clicou no botão abrir.
			if(retorno == 0){
				File arquivo = leitor.getSelectedFile();
				if(arquivo!=null){
					Scanner scanner = new Scanner(new FileReader(arquivo));
					scanner.useDelimiter("\\Z");
					conteudo = scanner.next();
					scanner.close();
					
					//conteudo = new String(Files.readAllBytes(arquivo.toPath()));
				}
			} 
		}catch (Exception e) {
			e.printStackTrace();
		}	
		
		return conteudo;
	}
}
