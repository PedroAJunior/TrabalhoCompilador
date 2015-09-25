package br.com.trabalhocompiladores;



import java.util.Arrays;

public class AnalisadorLexico {
	
    private String bufferIn = null;
    private int posicao = -1;
    private TabelaSimbolos tabelaSimbolos;
    private int linha = 0;
    private int coluna = 0;
    
    public AnalisadorLexico(){
    }
    
    public void setTabelaSimbolos(TabelaSimbolos Tab){
        this.tabelaSimbolos = Tab;
    }
    
    public void setEntrada(String CodigoFonte){
        this.bufferIn = CodigoFonte + "  ";
        this.posicao = 0;
        
        if(this.bufferIn.trim().length()>0){
            this.incrementaLinha();
        }
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
    	if(this.isBr(c)){
            this.incrementaLinha();
    	}
    }
    private void incrementaLinha(){
    	this.linha+=1;
    	this.coluna = 0;
    } 
    
    private void incrementaColuna(char c){
    	if(!this.isBr(c)){
            this.coluna+=1;
    	}
    }
    
    private void decrementaColuna(){
    	this.coluna-=1;
    }
    
    private boolean isBr(char c){
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
                        //Erro L�xico.
			TratamentoErros.getInstancia().erroLexico(linha, coluna, "caractere n�o reconhecido pela linguagem: "+c);
                    }
                    break;
					
                case 2:
                    //Estado de reconhecimento do caractere [
                    regLex.setToken(TipoToken.ABRE_COLCHETES);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
                    return regLex;
					
                case 3:
                    //Estado de reconhecimento do caractere ]
                    regLex.setToken(TipoToken.FECHA_COLCHETES);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
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
                    return regLex;
					
                case 6:
                    //Estado de reconhecimento do caractere (
                    regLex.setToken(TipoToken.ABRE_PARENTESES);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
                    return regLex;
					
                case 7:
                    //Estado de reconhecimento do caractere )
                    regLex.setToken(TipoToken.FECHA_PARENTESES);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
                    return regLex;
					
                case 8:
                    //Estado de reconhecimento da vircula
                    regLex.setToken(TipoToken.VIRGULA);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
                    return regLex;
					
                case 9: 
                    if(c=='\n'){
                        //Final do coment�rio de linha-�nica
                        e = 10;
                        this.incrementaLinha();
                    }
                    else if(c=='#'){
                        //Inicio dos caracteres do coment�rio de multi-linha
                         e = 11;
                    }
                    else{
                        //Caracteres do coment�rio de linha-�nica
                        e = 9; 
                    }	
                    break;
				
		case 10:
                    //Estado de reconhecimento do comentario de linha-�nica
                    e = 1;
                    break;
					
		case 11:
                    if(c=='#'){
                        //Coment�rio vazio, n�o h� caracteres entre ## e #.
                        e = 13;
                    }else{
                        //Caracteres do coment�rio.
			e = 12;
                    }
                    break;
					
		case 12:
                    if(c=='#'){
                        e = 13;
                    }else{
                        //Caracteres do coment�rio.
                        e = 12;
                        this.incrementaLinha(c);
                    }
                    break;
					
		case 13:
                    if(c=='#'){
                        //Caractere final do coment�rio de muilti-linha
			e = 14;
                    }else{
                        //Volta para o estado 12 pois neste caso o caractere # faz parte do comentario. Ex. ##aaa#aa
			e = 12;
                    }
                    break;
					
		case 14:
                    //Estado de reconhecimento do coment�rio de multi-linha.
                    //volta pro inicio.
                    e = 1;
                    break;
				
		case 15:
                    //Estado de reconhecimento do ponto e virgula.
                    regLex.setToken(TipoToken.PONTO_E_VIRGULA);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
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
                    //Estado de reconhecimento do operador de atribui��o :=	
                    regLex.setToken(TipoToken.OP_ATRIB);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
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
                    regLex = this.tabelaSimbolos.procurarAdicionarId(lexema);
                    return regLex;
					
		case 20:
                    //Estado de reconhecimento do operador de nega��o ~
                    regLex.setToken(TipoToken.OP_NOT);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
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
			//Erro L�xico.
                        TratamentoErros.getInstancia().erroLexico(linha, coluna, "Falta um digito para completar o n�mero real.");
                        e = 1;
                        //Como o caractere lido n�o e um digito e retra�do uma posi��o para verificar se o mesmo pode ser reconhecido em outro padr�o. 
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
                    //Estado de reconhecimento do n�mero real.
                    this.retrair();
                    this.decrementaColuna();
                    regLex.setToken(TipoToken.NUM);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.REAL);
                    return regLex;
				
		case 25:
                    //Estado de reconhecimento do n�mero inteiro.
                    this.retrair();
                    this.decrementaColuna();
                    regLex.setToken(TipoToken.NUM);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.INTEIRO);
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
                        //Erro L�xico.
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
                    return regLex;	
			
		case 33:
                    //Estado de reconhecimento do operador >= ou =
                    regLex.setToken(TipoToken.OP_REL);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
                    return regLex;
                            
		case 34:
                    //Estado de reconhecimento dos operadores *, / e %
                    regLex.setToken(TipoToken.OP_MUL);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
                    return regLex;	
				
		case 35:
                    //Estado de reconhecimento dos operadores + ou -
                    regLex.setToken(TipoToken.OP_ADD);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
                    return regLex;	
				
		case 36:
                    //Estado de reconhecimento do operador :
                    this.retrair();
                    this.decrementaColuna();
                    regLex.setToken(TipoToken.DOIS_PONTOS);
                    regLex.setLexema(lexema);
                    regLex.setTipoVar(TipoVar.SEM_TIPO);
                    return regLex;	
            }
        }
        
        if(this.eof() && !this.estadoFinal(e, finais) && e==12){
           TratamentoErros.getInstancia().erroLexico(linha, coluna, "O comentario de multi-linhas n�o foi finalizado."); 
        }
        
        return regLex;
    }
}
