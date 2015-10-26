/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.trabalhocompiladores;

import java.io.IOException;

/**
 *
 * @author henrique
 */
public class AnalisadorSintatico {
    
    private AnalisadorLexico AnLex;
    private TabelaSimbolos TabSim;
    
    private RegistroLexico Prox;
    
    public AnalisadorSintatico(){
        AnLex = new AnalisadorLexico();
        TabSim = new TabelaSimbolos();
        
        AnLex.setTabelaSimbolos(TabSim);
    }
    
    public void setArquivoFonte(String NomeArquivo) throws IOException{
        AnLex.setEntrada(NomeArquivo);
    }
    
    public void casaToken(TipoToken Esperado){
        if(Prox.getToken() == Esperado ){
            //OK
            System.out.printf("     casaToken(%s) \n",Prox); //debug
            Prox = AnLex.obterProx();
        }
        else{
            //erro sintatico
            TratamentoErros.getInstancia().erroSintatico(Prox, Esperado);
        }
    }
    
    public void parse(){
        Prox = AnLex.obterProx();
        TratamentoErros.getInstancia().zerarErros();
        
        //Começa as gramaticas
        
        
        
        
        

        
        if(Prox.getToken()!=TipoToken.FIM_DE_ARQUIVO){
            TratamentoErros.getInstancia().erroSintatico("A gramática terminou mas o arquivo não.");
        }
        
        if(TratamentoErros.getInstancia().getQuantidadeErros()==0){
            System.out.println("Compilação terminada com sucesso.");
        } 
        else{
            TratamentoErros.getInstancia().relatorioErros();
        }
    }
    
    
}
