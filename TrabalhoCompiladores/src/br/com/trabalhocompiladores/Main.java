package br.com.trabalhocompiladores;


/**
 * 
 * @author DIVINO
 * 
 * Integrantes do grupo.
 * 
 * DIVINO JUNIO DA CRUZ FELIX
 * PEDRO ARAÚJO JÚNIOR
 * PABLO FLANMARION BORGES DE SOUZA
 * JUNIOR SOUZA
 *
 */
public class Main {

	public static void main(String[] args) {
            AnalisadorLexico analisadorLexico = new AnalisadorLexico();
            TabelaSimbolos tabelaSimbolo = new TabelaSimbolos();

            String codigo = TrataArquivoFonte.LerArquivo();

            System.out.println("\n CÓDIGO FONTE LIDO: \n");
            System.out.println(codigo);

            analisadorLexico.setTabelaSimbolos(tabelaSimbolo);
            analisadorLexico.setEntrada(codigo);

            System.out.println("\n ANALISADOR LEXICO: \n");
            RegistroLexico registro = null;
            registro = analisadorLexico.obterProx();

            while(registro.getToken()!=TipoToken.FIM_DE_ARQUIVO){
                System.out.println(registro);
                registro = analisadorLexico.obterProx();
            }

            System.out.println("\n TABELA DE SIMBOLOS: \n");
            tabelaSimbolo.print();
	}
}
