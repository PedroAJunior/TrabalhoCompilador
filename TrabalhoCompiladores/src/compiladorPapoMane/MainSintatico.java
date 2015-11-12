/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladorPapoMane;

import java.io.IOException;

/**
 *
 * @author henrique
 */
public class MainSintatico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //AnalisadorLexico.testarLexico();
        AnalisadorSintatico Parser = new AnalisadorSintatico();
        try {
            String code = Arquivo.readFile("code.ppmn");
            
            Parser.setArquivoFonte(code);
            Parser.parse();

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
}
