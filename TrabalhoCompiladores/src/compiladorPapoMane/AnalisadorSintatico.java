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
public class AnalisadorSintatico {
    
    private AnalisadorLexico anLex;
    private TabelaSimbolos tabelaSimbolos;
    
    private RegistroLexico prox;
    
    public AnalisadorSintatico() {
        anLex = new AnalisadorLexico();
        tabelaSimbolos = new TabelaSimbolos();
        
        anLex.setTabelaSimbolos(tabelaSimbolos);
    }
    
    public void setArquivoFonte(String code) throws IOException {
        anLex.setArquivoFonte(code);
    }
    
    public void casaToken(TipoToken Esperado) {
        if (prox.getToken() == Esperado) {
            //OKprox.getToken() == Esperado
            //System.out.printf("[INFO]casaToken(%s) \n", prox); //debug
            prox = anLex.obterProx();
        } else {
            //erro sintatico
            TratamentoErros.getInstancia().erroSintatico(prox, Esperado);
        }
    }
    
    public void parse() {
        prox = anLex.obterProx();
        TratamentoErros.getInstancia().zerarErros();

        //Começa a gramática
        //exp1();
        //expSimples();
        start();
        
        if (prox.getToken() != TipoToken.FIM_DE_ARQUIVO) {
            TratamentoErros.getInstancia().erroSintatico("[ERRO]a gramática terminou mas o arquivo não.");
        }
        
        if (TratamentoErros.getInstancia().getQuantidadeErros() == 0) {
            System.out.println("[FINISH]Compilação terminada com sucesso.");
        } else {
            TratamentoErros.getInstancia().relatorioErros();
        }
    }

    /*
     <constante> -> num | const_char | const_str | const_logica
     */
    private void constante() {
        if (prox.getToken() == TipoToken.NUM) {
            casaToken(TipoToken.NUM);
        } else if (prox.getToken() == TipoToken.CONST_CHAR) {
            casaToken(TipoToken.CONST_CHAR);
        } else if (prox.getToken() == TipoToken.CONST_STR) {
            casaToken(TipoToken.CONST_STR);
        } else if (prox.getToken() == TipoToken.CONST_LOGICA) {
            casaToken(TipoToken.CONST_LOGICA);
        } else {
            TratamentoErros.getInstancia().erroSintatico("<constante> era esperado NUM, CHAR ou BOOLEANO neste ponto da gramática, mas recebido " + prox + ": linha "+prox.getLinha()+", coluna "+prox.getColuna());
        }
    }
    
    private boolean isConstante() {
        return prox.getToken() == TipoToken.NUM
                || prox.getToken() == TipoToken.CONST_CHAR
                || prox.getToken() == TipoToken.CONST_STR
                || prox.getToken() == TipoToken.CONST_LOGICA;
    }

    /*
     <tipo> -> inteiro | real | caractere | sequencia | logico
     */
    private void tipo() {
        if (prox.getToken() == TipoToken.INTEIRO) {
            casaToken(TipoToken.INTEIRO);
        } else if (prox.getToken() == TipoToken.REAL) {
            casaToken(TipoToken.REAL);
        } else if (prox.getToken() == TipoToken.CARACTERE) {
            casaToken(TipoToken.CARACTERE);
        } else if (prox.getToken() == TipoToken.SEQUENCIA) {
            casaToken(TipoToken.SEQUENCIA);
        } else if (prox.getToken() == TipoToken.LOGICO) {
            casaToken(TipoToken.LOGICO);
        } else {
            TratamentoErros.getInstancia().erroSintatico("<tipo> era esperado INTEIRO, REAL, CARACTERE, SEQUENCIA ou LOGICO neste ponto da gramática, mas recebido " + prox+ ": linha: "+prox.getLinha()+", coluna "+prox.getColuna());
        }
    }
    
    private boolean isTipo() {
        return prox.getToken() == TipoToken.INTEIRO
                || prox.getToken() == TipoToken.REAL
                || prox.getToken() == TipoToken.CARACTERE
                || prox.getToken() == TipoToken.SEQUENCIA
                || prox.getToken() == TipoToken.LOGICO;
    }

    /*
     <tipo-func> -> <tipo> [ ac fc ]
     */
    private void tipoFunc() {
        tipo();
        if (prox.getToken() == TipoToken.ABRE_COLCHETES) {
            casaToken(TipoToken.ABRE_COLCHETES);
            casaToken(TipoToken.FECHA_COLCHETES);
        }
    }

    /*
     <dec-vetor> -> [ ac num fc ]
     */
    private void decVetor() {
        if (prox.getToken() == TipoToken.ABRE_COLCHETES) {
            casaToken(TipoToken.ABRE_COLCHETES);
            casaToken(TipoToken.NUM);
            casaToken(TipoToken.FECHA_COLCHETES);
        }
    }

    /*
     <dec> -> <tipo> id <dec-vetor> {, id <dec-vetor> };
     */
    private void dec() {
        tipo();
        casaToken(TipoToken.ID);
        decVetor();
        while (prox.getToken() == TipoToken.VIRGULA) {
            casaToken(TipoToken.VIRGULA);
            casaToken(TipoToken.ID);
            decVetor();
        }
        casaToken(TipoToken.PONTO_E_VIRGULA);
    }

    /*
     <pars> -> [ <tipo-func> id {, <tipo-func> id } ]
     */
    private void pars() {
        if (isTipo()) {
            tipoFunc();
            casaToken(TipoToken.ID);
            while (prox.getToken() == TipoToken.VIRGULA) {
                casaToken(TipoToken.VIRGULA);
                tipoFunc();
                casaToken(TipoToken.ID);
            }
        }
        
    }

    /*
     <variaveis> -> variaveis { <dec> } fim_variaveis;
     */
    private void variaveis() {
        casaToken(TipoToken.VARIAVEIS);
        while (isTipo()) {
            dec();
        }
        casaToken(TipoToken.FIM_VARIAVEIS);
        casaToken(TipoToken.PONTO_E_VIRGULA);
    }

    /*
     <funcao> -> [<tipo-func> | sem_tipo] funcao id ap <pars> fp :
     <variaveis> <bloco> fim_funcao;
     */
    private void funcao() {
        if (isTipo()) {
            tipoFunc();
        } else if (prox.getToken() == TipoToken.SEM_TIPO) {
            casaToken(TipoToken.SEM_TIPO);
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
        casaToken(TipoToken.PONTO_E_VIRGULA);
    }
    
    public boolean isFuncao() {
        return isTipo() || prox.getToken() == TipoToken.SEM_TIPO || prox.getToken() == TipoToken.FUNCAO;
    }

    /*
     <dec-funcoes> -> { <funcao> }
     */
    private void decFuncoes() {
        while (isFuncao()) {
            funcao();
        }
    }

    /*
     <chamada-funcao> -> [ ap [ <exp> {, <exp> } ] fp | ac <exp> fc ]
     */
    private void chamadaFuncao() {
        if (prox.getToken() == TipoToken.ABRE_PARENTESES) {
            casaToken(TipoToken.ABRE_PARENTESES);
            if (prox.getToken() == TipoToken.ID) {
                exp();
                while (prox.getToken() == TipoToken.VIRGULA) {
                    casaToken(TipoToken.VIRGULA);
                    exp();
                }
            }
            casaToken(TipoToken.FECHA_PARENTESES);
        } else if (prox.getToken() == TipoToken.ABRE_COLCHETES) {
            casaToken(TipoToken.ABRE_COLCHETES);
            exp();
            casaToken(TipoToken.FECHA_COLCHETES);
        }
    }

    /*
     <fator> -> id <chamada-funcao> | <constante> | op_not <fator> | ap <exp> fp
     */
    private void fator() {
        if (prox.getToken() == TipoToken.ID) {
            casaToken(TipoToken.ID);
            chamadaFuncao();
        } else if (isConstante()) {
            constante();
        } else if (prox.getToken() == TipoToken.OP_NOT) {
            casaToken(TipoToken.OP_NOT);
            fator();
        } else if (prox.getToken() == TipoToken.ABRE_PARENTESES) {
            casaToken(TipoToken.ABRE_PARENTESES);
            exp();
            casaToken(TipoToken.FECHA_PARENTESES);
        } else {
            TratamentoErros.getInstancia().erroSintatico("<fator> era esperado ID, OP_NOT ou ABRE_PARENTESES neste ponto da gramática, mas recebido " + prox+ ": linha "+prox.getLinha()+", coluna "+prox.getColuna());
        }
    }
    
    private boolean isFator() {
        return prox.getToken() == TipoToken.ID
                || isConstante()
                || prox.getToken() == TipoToken.OP_NOT
                || prox.getToken() == TipoToken.ABRE_PARENTESES;
    }
    /*
     <termo> -> <fator> { op_mul <fator> }
     */
    
    private void termo() {
        fator();
        while (prox.getToken() == TipoToken.OP_MUL) {
            casaToken(TipoToken.OP_MUL);
            fator();
        }
    }

    /*
     <exp-simples> -> <termo> { op_add <termo> }
     */
    private void expSimples() {
        termo();
        while (prox.getToken() == TipoToken.OP_ADD) {
            casaToken(TipoToken.OP_ADD);
            termo();
        }
    }

    /*
     <exp> -> <exp-simples> [ op_rel <exp-simples> ]
     */
    private void exp() {
        expSimples();
        while (prox.getToken() == TipoToken.OP_REL) {
            casaToken(TipoToken.OP_REL);
            expSimples();
        }
    }

    /*
     <procedimento> -> ap [ <exp> {, <exp> } ] fp
     */
    private void procedimento() {
        casaToken(TipoToken.ABRE_PARENTESES);
        if (isFator()) {
            exp();
            while (prox.getToken() == TipoToken.VIRGULA) {
                casaToken(TipoToken.VIRGULA);
                exp();
            }
        }
        casaToken(TipoToken.FECHA_PARENTESES);
    }

    /*
     <atribuicao> -> [ ac <exp> fc ] op_atrib <exp>
     */
    private void atribuicao() {
        if (prox.getToken() == TipoToken.ABRE_COLCHETES) {
            casaToken(TipoToken.ABRE_COLCHETES);
            exp();
            casaToken(TipoToken.FECHA_COLCHETES);
        }
        casaToken(TipoToken.OP_ATRIB);
        exp();
    }
    
    private boolean isAtribuicao() {
        return prox.getToken() == TipoToken.ABRE_COLCHETES
                || prox.getToken() == TipoToken.OP_ATRIB;
    }

    /*
     <atrib_proc> -> id ( <atribuicao> | <procedimento> ) ;
     */
    private void atribProc() {
        casaToken(TipoToken.ID);
        if (isAtribuicao()) {
            atribuicao();
        } else if (prox.getToken() == TipoToken.ABRE_PARENTESES) {
            procedimento();
        } else {
            TratamentoErros.getInstancia().erroSintatico("<atrib_proc> era esperado ABRE_COLCHETES ou ABRE_PARENTESES neste ponto da gramática, mas recebido " + prox + "linha "+prox.getLinha()+", coluna "+prox.getColuna());
        }
        casaToken(TipoToken.PONTO_E_VIRGULA);
        
    }

    /*
     <saida> -> saida ap <exp> {, <exp> } fp ;
     */
    private void saida() {
        casaToken(TipoToken.SAIDA);
        casaToken(TipoToken.ABRE_PARENTESES);
        exp();
        while (prox.getToken() == TipoToken.VIRGULA) {
            casaToken(TipoToken.VIRGULA);
            exp();
        }
        casaToken(TipoToken.FECHA_PARENTESES);
        casaToken(TipoToken.PONTO_E_VIRGULA);
    }

    /*
     <entrada> -> entrada ap id [ ac <exp> fc ] fp;
     */
    private void entrada() {
        casaToken(TipoToken.ENTRADA);
        casaToken(TipoToken.ABRE_PARENTESES);
        casaToken(TipoToken.ID);
        if (prox.getToken() == TipoToken.ABRE_COLCHETES) {
            casaToken(TipoToken.ABRE_COLCHETES);
            exp();
            casaToken(TipoToken.FECHA_COLCHETES);
        }
        casaToken(TipoToken.FECHA_PARENTESES);
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
        if (prox.getToken() == TipoToken.PASSO) {
            casaToken(TipoToken.PASSO);
            exp();
        }
        casaToken(TipoToken.FACA);
        bloco();
        casaToken(TipoToken.FIM_PARA);
        casaToken(TipoToken.PONTO_E_VIRGULA);
    }

    /*
     <retorno> -> retornar [ <exp> ] ;
     */
    private void retorno() {
        casaToken(TipoToken.RETORNAR);
        if (isFator()) {
            exp();
        }
        casaToken(TipoToken.PONTO_E_VIRGULA);
    }

    /*
     <se> -> se <exp> entao <bloco> { senao_se <exp> entao <bloco> } [ senao <bloco> ] fim_se;
    
     */
    private void se() {
        casaToken(TipoToken.SE);
        exp();
        casaToken(TipoToken.ENTAO);
        bloco();
        while (prox.getToken() == TipoToken.SENAO_SE) {
            casaToken(TipoToken.SENAO_SE);
            exp();
            casaToken(TipoToken.ENTAO);
            bloco();
        }
        if (prox.getToken() == TipoToken.SENAO) {
            casaToken(TipoToken.SENAO);
        }
        casaToken(TipoToken.FIM_SE);
        casaToken(TipoToken.PONTO_E_VIRGULA);
    }

    /*
     <comando> -> <se> | <enquanto> | <para> | <retorno> | <entrada> | <saida> | <atrib_proc>
     */
    private void comando() {
        if (prox.getToken() == TipoToken.SE) {
            se();
        } else if (prox.getToken() == TipoToken.ENQUANTO) {
            enquanto();
        } else if (prox.getToken() == TipoToken.PARA) {
            para();
        } else if (prox.getToken() == TipoToken.RETORNAR) {
            retorno();
        } else if (prox.getToken() == TipoToken.ENTRADA) {
            entrada();
        } else if (prox.getToken() == TipoToken.SAIDA) {
            saida();
        } else if (prox.getToken() == TipoToken.ID) {
            atribProc();
        } else {
            TratamentoErros.getInstancia().erroSintatico("<comando> era esperado SENAO, ENQUANTO, PARA, RETORNAR, ENTRADA, SAIDA ou ID neste ponto da gramática, mas recebido " + prox+ ": linha "+prox.getLinha()+", Coluna "+prox.getColuna());
        }
    }
    
    //<comando> -> <se> | <enquanto> | <para> | <retorno> | <entrada>| <saida> | <atrib_proc>
    private boolean isComando() {
        return prox.getToken() == TipoToken.SE
                || prox.getToken() == TipoToken.ENQUANTO
                || prox.getToken() == TipoToken.PARA
                || prox.getToken() == TipoToken.RETORNAR
                || prox.getToken() == TipoToken.ENTRADA
                || prox.getToken() == TipoToken.SAIDA
                || prox.getToken() == TipoToken.ID;
    }

    /*
     <bloco> -> { <comando> }
     */
    private void bloco() {
        while (isComando()) {
            comando();
        }
    }

    /*
     <start> -> principal id : <variaveis> <bloco> fim; <dec-funcoes>
     */
    private void start() {
        casaToken(TipoToken.PRINCIPAL);
        casaToken(TipoToken.ID);
        casaToken(TipoToken.DOIS_PONTOS);
        variaveis();
        bloco();
        casaToken(TipoToken.FIM);
        casaToken(TipoToken.PONTO_E_VIRGULA);
        decFuncoes();
    }
}
