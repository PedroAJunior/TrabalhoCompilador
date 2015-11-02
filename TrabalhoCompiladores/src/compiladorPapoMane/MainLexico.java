package compiladorPapoMane;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pedro
 */
public class MainLexico {
    
    public static void main(String[] args){
        
        try {
            String code = Arquivo.readFile("code.ppmn");
            
            //System.out.println("Code: " + code);
            
            AnalisadorLexico anLex = new AnalisadorLexico();
            
            anLex.setTabelaSimbolos(new TabelaSimbolos());
            
            anLex.setArquivoFonte(code);
            
            RegistroLexico rl = anLex.obterProx();
            
            while(rl.getToken() != TipoToken.FIM_DE_ARQUIVO){
                System.out.println(rl);
                rl = anLex.obterProx();
            }
            
            //RegistroLexico registroLexico = anLex.;
            
            
        } catch (IOException ex) {
            Logger.getLogger(MainLexico.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
}
