/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladorPapoMane;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author pedro
 */
public class Arquivo {
    
    public static String readFile(String NomeArquivo) throws FileNotFoundException, IOException {
        StringBuilder str = new StringBuilder(); //Objeto otimizado para construção de Strings

        //Abertura do Arquivo e Buffer
        FileReader flr = new FileReader(NomeArquivo); 
        BufferedReader bfr = new BufferedReader(flr,1024);

        char[] cbuf = new char[1024]; 
        int cont = bfr.read(cbuf);        
        while (cont > 0) { //até acabar o arquivo
            str.append(cbuf,0,cont);
            cont = bfr.read(cbuf);
        }
        //fechar os arquivos
        bfr.close();
        flr.close();
        
        str.append("   "); //adição do sentinela, para evitar que o último token seja perdido
        return str.toString();
    }
    
}
