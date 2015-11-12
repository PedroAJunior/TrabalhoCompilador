/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladorPapoMane;

import java.util.Arrays;

/**
 *
 * @author pedro
 */
public class AnalisadorLexico {
    private String bufferIn = null;
    private int posicao = -1;
    private int linha = 1;
    private int coluna = 0;
    
    TabelaSimbolos tabelaSimbolos;
    
    public AnalisadorLexico(){
    }
    
    public void setTabelaSimbolos(TabelaSimbolos Tab){
        this.tabelaSimbolos = Tab;
    }
    
    public void setArquivoFonte(String codigo){
        this.bufferIn = codigo + "  ";
        this.posicao=0;
        this.linha = 1;
        this.coluna = 0;
    }
    
    public char proxChar(){
        return bufferIn.charAt(posicao++);
    }
    
    public boolean eof(){
        return posicao>=bufferIn.length();
    }
    
    public void retrair(){
        posicao--;
    }
    
    private boolean estadoFinal(int estado, int[] Finais){
        return Arrays.binarySearch(Finais, estado)>=0;
    }
    
    private void incrementaLinha(char c){
    	if(this.isQuebraLinha(c)){
            this.incrementaLinha();
    	}
    }
    private void incrementaLinha(){
    	this.linha+=1;
    	this.coluna = 0;
    } 
    
    private void incrementaColuna(char c){
    	if(!this.isQuebraLinha(c)){
            this.coluna+=1;
    	}
    }
    
    private void decrementaColuna(){
    	this.coluna-=1;
    }
    
    private boolean isQuebraLinha(char c){
    	return c=='\n';
    }
    
    
    public RegistroLexico obterProx(){
    	RegistroLexico regLex = new RegistroLexico();
        regLex.setToken(TipoToken.FIM_DE_ARQUIVO);

        int e = 1;
        char c = 0;
        int[] finais={2, 3, 5, 6, 7, 8, 10, 14, 15, 17, 19, 20, 24, 25, 28, 30, 32, 33, 34, 35, 36};
        String lexema = "";        
       
        while(!eof() && regLex.getToken()==TipoToken.FIM_DE_ARQUIVO){
           if(!this.estadoFinal(e, finais)){
        	c = this.proxChar();
        	this.incrementaColuna(c);
            }
        	
            switch (e) {
		case 1:
                    //c==' ' || c=='\t' || c=='\n' || c=='\r'
                    if(Character.isWhitespace(c)){
			e = 1;
			this.incrementaLinha(c);
                    }
                    else if(c=='['){
			e = 2;
			lexema += c;
                    }
                    else if(c==']'){
                        e = 3;
			lexema += c;
                    }
                    else if(c=='('){
                        e = 6;
			lexema += c;
                    }
                    else if(c==')'){
                        e = 7;
			lexema += c;
                    }
                    else if(c==','){
			e = 8;
			lexema += c;
                    }
                    else if(c==';'){ 
                        e = 15;
			lexema += c;
                    }
                    else if(c=='"'){
                        //Inicio da constante string.
			e = 4;
			lexema += c;
                    }
                    else if(c=='#'){
                        //Inicio de comentario.
			e = 9;
                    }
                    else if(c==':'){
                        e = 16;
			lexema += c;
                    }
                    else if(Character.isLetter(c)){
                        e = 18;
			lexema += c;
                    }
                    else if(c=='~'){
                        e = 20;
			lexema += c;
                    }
                    else if(Character.isDigit(c)){
                        e = 21;
			lexema += c;
                    }
                    else if(c=='\''){
			//Inicio da constante char.
			e = 26;
			lexema += c;
                    }
                    else if(c=='<'){ 
                        e = 29;
			lexema += c;
                    }
                    else if(c=='>'){ 
                        e = 31;
			lexema += c;
                    }
                    else if(c =='='){
                        e = 33;
			lexema += c;
                    }
                    else if(c =='/' || c =='*'|| c =='%') {
                        e = 34;
			lexema += c;
                    }
                    else if(c=='+' || c=='-'){
                        e = 35;
			lexema += c;
                    }else{
                        //Erro Léxico.
			TratamentoErros.getInstancia().erroLexico(linha, coluna, "caractere não reconhecido pela linguagem: "+c);
                    }
                    break;
					
                case 2:
                    //Estado de reconhecimento do caractere [
                    regLex.setToken(TipoToken.ABRE_COLCHETES);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
                    regLex.setColuna(coluna);
                    regLex.setLinha(linha);
                    return regLex;
					
                case 3:
                    //Estado de reconhecimento do caractere ]
                    regLex.setToken(TipoToken.FECHA_COLCHETES);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
                    regLex.setColuna(coluna);
                    regLex.setLinha(linha);
                    return regLex;
					
                case 4:
                    if(c=='"'){
                       //Final da constante string.
                        e = 5;
                        lexema+= c;
                    }
                    else if(c == '\n'){
                        TratamentoErros.getInstancia().erroLexico(linha, coluna, "falta uma aspas duplas para fechar a string.");
                        e = 1;
                        lexema="";
                        this.incrementaLinha();
                    }
                    else{
                        //Armazenando os caracteres da constante string.
                        e = 4;
                        lexema+= c;
                    }
                    break;	
					
                case 5:
                    //Estado de reconhecimento da constante string.
                    regLex.setToken(TipoToken.CONST_STR);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.STRING);
                    regLex.setColuna(coluna);
                    regLex.setLinha(linha);
                    return regLex;
					
                case 6:
                    //Estado de reconhecimento do caractere (
                    regLex.setToken(TipoToken.ABRE_PARENTESES);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
                    regLex.setColuna(coluna);
                    regLex.setLinha(linha);
                    return regLex;
					
                case 7:
                    //Estado de reconhecimento do caractere )
                    regLex.setToken(TipoToken.FECHA_PARENTESES);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
                    regLex.setColuna(coluna);
                    regLex.setLinha(linha);
                    return regLex;
					
                case 8:
                    //Estado de reconhecimento da vircula
                    regLex.setToken(TipoToken.VIRGULA);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
                    regLex.setColuna(coluna);
                    regLex.setLinha(linha);
                    return regLex;
					
                case 9: 
                    if(c=='\n'){
                        //Final do comentário de linha-única
                        e = 10;
                        this.incrementaLinha();
                    }
                    else if(c=='#'){
                        //Inicio dos caracteres do comentário de multi-linha
                         e = 11;
                    }
                    else{
                        //Caracteres do comentário de linha-única
                        e = 9; 
                    }	
                    break;
				
		case 10:
                    //Estado de reconhecimento do comentario de linha-única
                    e = 1;
                    break;
					
		case 11:
                    if(c=='#'){
                        //Comentário vazio, não há caracteres entre ## e #.
                        e = 13;
                    }else{
                        //Caracteres do comentário.
			e = 12;
                    }
                    break;
					
		case 12:
                    if(c=='#'){
                        e = 13;
                    }else{
                        //Caracteres do comentário.
                        e = 12;
                        this.incrementaLinha(c);
                    }
                    break;
					
		case 13:
                    if(c=='#'){
                        //Caractere final do comentário de muilti-linha
			e = 14;
                    }else{
                        //Volta para o estado 12 pois neste caso o caractere # faz parte do comentario. Ex. ##aaa#aa
			e = 12;
                    }
                    break;
					
		case 14:
                    //Estado de reconhecimento do comentário de multi-linha.
                    //volta pro inicio.
                    e = 1;
                    break;
				
		case 15:
                    //Estado de reconhecimento do ponto e virgula.
                    regLex.setToken(TipoToken.PONTO_E_VIRGULA);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
                    regLex.setColuna(coluna);
                    regLex.setLinha(linha);
                    return regLex;	
					
		case 16:
                    if(c=='='){
                        e = 17;
                        lexema += c;
                    }else{
			e = 36;
                    }
                    break;
					
		case 17:
                    //Estado de reconhecimento do operador de atribuição :=	
                    regLex.setToken(TipoToken.OP_ATRIB);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
                    regLex.setColuna(coluna);
                    regLex.setLinha(linha);
                    return regLex;
					
		case 18:
                    this.incrementaLinha(c);
                    if(Character.isLetter(c) || Character.isDigit(c) || c == '_'){
                        //Armazenando os caracteres que formam o id.
			e = 18;
			lexema += c;
                    }else{
			e = 19;
                    }
                    break;
				
		case 19:
                    //Estado de reconhecimento do id.
                    this.retrair();
                    this.decrementaColuna();
                    regLex = this.tabelaSimbolos.procurarAdicionarId(lexema, linha, coluna);
                    return regLex;
					
		case 20:
                    //Estado de reconhecimento do operador de negação ~
                    regLex.setToken(TipoToken.OP_NOT);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
                    regLex.setColuna(coluna);
                    regLex.setLinha(linha);
                    return regLex;
				
		case 21:
                    if(Character.isDigit(c)){
                        e = 21;
                        lexema += c;
                    }
                    else if(c=='.'){
                        e = 22;
                        lexema += c;
                    }else{
			e = 25;
                    }
                    break;	
				
		case 22:
                    if(Character.isDigit(c)){
			e = 23;
			lexema += c;
                    }else{
			//Erro Léxico.
                        TratamentoErros.getInstancia().erroLexico(linha, coluna, "Falta um digito para completar o número real.");
                        e = 1;
                        //Como o caractere lido não e um digito e retraído uma posição para verificar se o mesmo pode ser reconhecido em outro padrão. 
                        this.retrair();
                        this.decrementaColuna();
                        lexema="";
                    }
                    break;
				
		case 23:
                    if(Character.isDigit(c)){
                        e = 23;
			lexema += c;
                    }else{
			e = 24;
                    }
                    break;
	
		case 24:
                    //Estado de reconhecimento do número real.
                    this.retrair();
                    this.decrementaColuna();
                    regLex.setToken(TipoToken.NUM);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.REAL);
                    regLex.setColuna(coluna);
                    regLex.setLinha(linha);
                    return regLex;
				
		case 25:
                    //Estado de reconhecimento do número inteiro.
                    this.retrair();
                    this.decrementaColuna();
                    regLex.setToken(TipoToken.NUM);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.INTEIRO);
                    regLex.setColuna(coluna);
                    regLex.setLinha(linha);
                    return regLex;	
				
		case 26:
                    e = 27;
                    lexema += c;
                    break;
				
		case 27:
                    if(c == '\''){ 
                        e = 28;
			lexema += c;
                    }else{
                        //Erro Léxico.
                        TratamentoErros.getInstancia().erroLexico(linha, coluna, "Falta uma aspas simples para formar a constante char.\n Caractere encontrado : " + c);
                        e = 1;
                        lexema="";
                        this.incrementaLinha(c);
                    }
                    break;
				
		case 28:
                    //Estado de reconhecimento da constante char.
                    regLex.setToken(TipoToken.CONST_CHAR);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.CARACTERE);
                    regLex.setColuna(coluna);
                    regLex.setLinha(linha);
                    return regLex;
				
		case 29:
                    if(c =='>' || c =='='){
                        e = 30;
                        lexema += c;
                    }else{
			e = 32;
                    }
                    break;
				
		case 30:
                    //Estado de reconhecimento do operador <> ou <=
                    regLex.setToken(TipoToken.OP_REL);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
                    regLex.setColuna(coluna);
                    regLex.setLinha(linha);
                    return regLex;	
				
                case 31:
                    if(c =='='){
                        e = 33;
			lexema += c;
                    }else{
                        e = 32;
                    }
                    break;	
				
		case 32:
                    //Estado de reconhecimento do operador < ou >
                    this.retrair();
                    this.decrementaColuna();
                    regLex.setToken(TipoToken.OP_REL);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
                    regLex.setColuna(coluna);
                    regLex.setLinha(linha);
                    return regLex;	
			
		case 33:
                    //Estado de reconhecimento do operador >= ou =
                    regLex.setToken(TipoToken.OP_REL);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
                    regLex.setColuna(coluna);
                    regLex.setLinha(linha);
                    return regLex;
                            
		case 34:
                    //Estado de reconhecimento dos operadores *, / e %
                    regLex.setToken(TipoToken.OP_MUL);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
                    regLex.setColuna(coluna);
                    regLex.setLinha(linha);
                    return regLex;	
				
		case 35:
                    //Estado de reconhecimento dos operadores + ou -
                    regLex.setToken(TipoToken.OP_ADD);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
                    regLex.setColuna(coluna);
                    regLex.setLinha(linha);
                    return regLex;	
				
		case 36:
                    //Estado de reconhecimento do operador :
                    this.retrair();
                    this.decrementaColuna();
                    regLex.setToken(TipoToken.DOIS_PONTOS);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
                    regLex.setColuna(coluna);
                    regLex.setLinha(linha);
                    return regLex;	
            }
        }
        
        if(this.eof() && !this.estadoFinal(e, finais) && e==12){
           TratamentoErros.getInstancia().erroLexico(linha, coluna, "O comentario de multi-linhas não foi finalizado."); 
        }
        
        return regLex;
    }
}
