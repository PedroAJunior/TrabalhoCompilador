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

    public AnalisadorSintatico() {
        AnLex = new AnalisadorLexico();
        TabSim = new TabelaSimbolos();

        AnLex.setTabelaSimbolos(TabSim);
    }

    public void setArquivoFonte(String NomeArquivo) throws IOException {
        AnLex.setEntrada(NomeArquivo);
    }

    public void casaToken(TipoToken Esperado) {
        if (Prox.getToken() == Esperado) {
            //OK
            System.out.printf("     casaToken(%s) \n", Prox); //debug
            Prox = AnLex.obterProx();
        } else {
            //erro sintatico
            TratamentoErros.getInstancia().erroSintatico(Prox, Esperado);
        }
    }

    public void parse() {
        Prox = AnLex.obterProx();
        TratamentoErros.getInstancia().zerarErros();

        //Começa as gramaticas
        if (Prox.getToken() != TipoToken.FIM_DE_ARQUIVO) {
            TratamentoErros.getInstancia().erroSintatico("A gramática terminou mas o arquivo não.");
        }

        if (TratamentoErros.getInstancia().getQuantidadeErros() == 0) {
            System.out.println("Compilação terminada com sucesso.");
        } else {
            TratamentoErros.getInstancia().relatorioErros();
        }
    }

    private void start() {

    }

    private void variaveis() {

    }
    
    /*
    <dec> -> <tipo> id <dec-vetor> {, id <dec-vetor> };
    */
    private void dec() {
        tipo();
        casaToken(TipoToken.ID);
        decVetor();
        while(Prox.getToken() == TipoToken.VIRGULA){
            casaToken(TipoToken.VIRGULA);
            decVetor();
        }
        casaToken(TipoToken.PONTO_E_VIRGULA);
    }

    /*
     <tipo> -> inteiro | real | caractere | sequencia | logico
     */
    private void tipo() {
        if (Prox.getToken() == TipoToken.INTEIRO) {
            casaToken(TipoToken.INTEIRO);
        } else if (Prox.getToken() == TipoToken.REAL) {
            casaToken(TipoToken.REAL);
        } else if (Prox.getToken() == TipoToken.CARACTERE) {
            casaToken(TipoToken.CARACTERE);
        } else if (Prox.getToken() == TipoToken.SEQUENCIA) {
            casaToken(TipoToken.SEQUENCIA);
        } else if (Prox.getToken() == TipoToken.LOGICO) {
            casaToken(TipoToken.LOGICO);
        } else {
            //tratamento de erro
        }
    }

    private void decVetor() {

    }

    private void bloco() {

    }

    private void comando() {

    }

    private void se() {

    }

    private void retorno() {

    }

    /*
     <para> -> para id de <exp> ate <exp> [passo <exp>]
     faca <bloco> fim_para;
     */
    private void para() {
        casaToken(TipoToken.PARA);
        casaToken(TipoToken.ID);
        casaToken(TipoToken.DE);
        exp();
        casaToken(TipoToken.ATE);
        exp();
        if (Prox.getToken() == TipoToken.PASSO) {
            casaToken(TipoToken.PASSO);
            exp();
        }
        casaToken(TipoToken.FACA);
        bloco();
        casaToken(TipoToken.FIM_PARA);
        casaToken(TipoToken.PONTO_E_VIRGULA);
    }

    /*
    <enquanto> -> enquanto <exp> faca <bloco> fim_enquanto;
    */
    private void enquanto() {
        casaToken(TipoToken.ENQUANTO);
        exp();
        casaToken(TipoToken.FACA);
        bloco();
        casaToken(TipoToken.FIM_ENQUANTO);
        casaToken(TipoToken.PONTO_E_VIRGULA);
    }
    
    /*
    <entrada> -> entrada ap id [ ac <exp> fc ] fp;
    */
    private void entrada() {
        casaToken(TipoToken.ENTRADA);
        casaToken(TipoToken.ABRE_PARENTESES);
        casaToken(TipoToken.ID);
        if(Prox.getToken() == TipoToken.ABRE_COLCHETES){
            casaToken(TipoToken.ABRE_COLCHETES);
            exp();
            casaToken(TipoToken.FECHA_COLCHETES);
        }
        casaToken(TipoToken.FECHA_PARENTESES);
    }

    /*
     <saida> -> saida ap <exp> {, <exp> } fp ;
     */
    private void saida() {
        casaToken(TipoToken.SAIDA);
        casaToken(TipoToken.ABRE_PARENTESES);
        exp();
        while (Prox.getToken() == TipoToken.VIRGULA) {
            casaToken(TipoToken.VIRGULA);
            exp();
        }
        casaToken(TipoToken.FECHA_PARENTESES);
    }

    private void atribProc() {

    }

    private void atribuicao() {

    }

    private void procedimento() {

    }

    private void exp() {

    }

    private void expSimples() {

    }

    private void termo() {

    }

    private void fator() {

    }

    /*
     <constante> -> num | const_char | const_str | const_logica
     */
    private void constante() {
        if (Prox.getToken() == TipoToken.NUM) {
            casaToken(TipoToken.NUM);
        } else if (Prox.getToken() == TipoToken.CONST_CHAR) {
            casaToken(TipoToken.CONST_CHAR);
        } else if (Prox.getToken() == TipoToken.CONST_STR) {
            casaToken(TipoToken.CONST_STR);
        } else if (Prox.getToken() == TipoToken.CONST_LOGICA) {
            casaToken(TipoToken.CONST_LOGICA);
        } else {
            //tratamento de erro
        }
    }

    private void chamadaFuncao() {

    }

    /*
     <dec-funcoes> -> { <funcao> }
     */
    private void descFuncoes() {
        while (Prox.getToken() == TipoToken.SEM_TIPO
                || Prox.getToken() == TipoToken.FUNCAO
                || Prox.getToken() == TipoToken.INTEIRO
                || Prox.getToken() == TipoToken.REAL
                || Prox.getToken() == TipoToken.CARACTERE
                || Prox.getToken() == TipoToken.SEQUENCIA
                || Prox.getToken() == TipoToken.LOGICO) {
            funcao();
        }
    }

    /*
     <funcao> -> [<tipo-func> | sem_tipo] funcao id ap <pars> fp :
     <variaveis> <bloco> fim_funcao;
     */
    private void funcao() {
        if (Prox.getToken() == TipoToken.SEM_TIPO) {
            casaToken(TipoToken.SEM_TIPO);
        } else if (Prox.getToken() == TipoToken.INTEIRO
                || Prox.getToken() == TipoToken.REAL
                || Prox.getToken() == TipoToken.CARACTERE
                || Prox.getToken() == TipoToken.SEQUENCIA
                || Prox.getToken() == TipoToken.LOGICO) {
            tipoFunc();
        }
        casaToken(TipoToken.FUNCAO);
        casaToken(TipoToken.ID);
        casaToken(TipoToken.ABRE_PARENTESES);
        pars();
        casaToken(TipoToken.FECHA_PARENTESES);
        casaToken(TipoToken.DOIS_PONTOS);
        variaveis();
        bloco();
        casaToken(TipoToken.FIM_FUNCAO);
    }
    /*
     <tipo-func> -> <tipo> [ ac fc ]
     */

    private void tipoFunc() {
        tipo();
        if (Prox.getToken() == TipoToken.ABRE_COLCHETES) {
            casaToken(TipoToken.ABRE_COLCHETES);
            casaToken(TipoToken.FECHA_COLCHETES);
        }
    }

    /*
     <pars> -> [ <tipo-func> id {, <tipo-func> id } ]
     */
    private void pars() {
        if (Prox.getToken() == TipoToken.ABRE_COLCHETES) {
            tipoFunc();
            casaToken(TipoToken.ID);
            while (Prox.getToken() == TipoToken.VIRGULA) {
                casaToken(TipoToken.VIRGULA);
                tipoFunc();
                casaToken(TipoToken.ID);
            }
        }

    }

}
