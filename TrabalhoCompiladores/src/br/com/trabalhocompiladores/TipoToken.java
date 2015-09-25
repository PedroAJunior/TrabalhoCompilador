package br.com.trabalhocompiladores;



import java.util.Arrays;
import java.util.List;


public enum TipoToken {
	
	//Token especial para erro
	ERRO,
	
	FIM_DE_ARQUIVO,
	
	ID, 
	
	//Constantes
	NUM,  CONST_CHAR, CONST_STR, CONST_LOGICA,
	
	//Operadores
	OP_REL, OP_ATRIB, OP_NOT, OP_ADD, OP_MUL,
	
        //Operador add
        OU,
        
        //Operador mult
        E, 
        
	//Pontuação
	VIRGULA, PONTO_E_VIRGULA, DOIS_PONTOS, ABRE_PARENTESES, FECHA_PARENTESES, ABRE_COLCHETES, FECHA_COLCHETES,
	
	//Palavras chave
	PRINCIPAL, FIM, VARIAVEIS, FIM_VARIAVEIS, INTEIRO, REAL, CARACTERE, SEQUENCIA, LOGICO, SEM_TIPO, RETORNAR, 
	SE, ENTAO, SENAO, SENAO_SE, FIM_SE, ENTRADA, SAIDA, ENQUANTO, FIM_ENQUANTO, PARA, FIM_PARA, DE, ATE, PASSO,
	FACA, FUNCAO, FIM_FUNCAO, VERDADE, FALSO;
	
	public static List<TipoToken> palavrasReservadas(){
		return Arrays.asList(PRINCIPAL, FIM, VARIAVEIS, FIM_VARIAVEIS, INTEIRO, REAL, CARACTERE, SEQUENCIA, 
				             LOGICO, SEM_TIPO, RETORNAR, SE, ENTAO, SENAO, SENAO_SE, FIM_SE, ENTRADA, SAIDA,
				             ENQUANTO, FIM_ENQUANTO, PARA, FIM_PARA, DE, ATE, PASSO, FACA, FUNCAO, FIM_FUNCAO,
				             VERDADE, FALSO, E, OU);
	}
	
	public static String getName(TipoToken tipoToken){
		return tipoToken.name().toLowerCase();
	}
}
