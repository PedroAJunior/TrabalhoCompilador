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

        AnalisadorSintatico Parser = new AnalisadorSintatico();
        
        try {
            
        String codigo = TrataArquivoFonte.LerArquivo();

        System.out.println("\n CÓDIGO FONTE LIDO: \n");
        System.out.println(codigo);
        
        Parser.setArquivoFonte(codigo);
        Parser.parse();
            
        } catch (Exception e) {
            System.out.print(e);
        }

    }
}
