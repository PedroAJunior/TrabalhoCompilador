/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladorPapoMane;

/**
 *
 * @author henrique
 */
public enum TipoToken {
    ERRO, //Token especial para simbolizar Erro (não utilizado).
    
    //Marcador de Fim de Arquivo
    FIM_DE_ARQUIVO,  
    
    ID, 
    
    //Constantes
    NUM, CONST_CHAR, CONST_STR, CONST_LOGICA,
    
    //Operadores
    OP_REL, OP_ATRIB, OP_NOT, OP_ADD, OP_MUL,
    
    //Pontuação
    VIRGULA, PONTO_E_VIRGULA, DOIS_PONTOS, ABRE_PARENTESES, FECHA_PARENTESES, ABRE_COLCHETES, FECHA_COLCHETES,
    
    //Palavras chave
    PRINCIPAL, FIM, VARIAVEIS, FIM_VARIAVEIS, INTEIRO, REAL, CARACTERE, SEQUENCIA, LOGICO, SEM_TIPO, RETORNAR, 
    SE, ENTAO, SENAO, SENAO_SE, FIM_SE, ENTRADA, SAIDA, ENQUANTO, FIM_ENQUANTO, PARA, FIM_PARA, DE, ATE, PASSO,
    FACA, FUNCAO, FIM_FUNCAO;
      
}
