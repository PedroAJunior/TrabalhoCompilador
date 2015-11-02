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
public class RegistroLexico {
    
    private TipoToken token;
    private String lexema;
    private TipoVar tipoVar = TipoVar.SEM_TIPO;
    private int linha;
    private int coluna;

    public RegistroLexico(TipoToken Token, String Lexema, int linha, int coluna) {
        this.token = Token;
        this.lexema = Lexema;
        this.linha = linha;
    }
    
    public RegistroLexico(){
        this.token = TipoToken.ERRO;
        this.lexema = "";
    }

    public void setLinha(int linha){
        this.linha = linha;
    }
    
    public int getLinha(){
        return this.linha;
    }
    
    public void setColuna(int coluna){
        this.coluna = coluna;
    }
    
    public int getColuna(){
        return this.coluna;
    }
    /**
     * @return the Token
     */
    public TipoToken getToken() {
        return token;
    }

    /**
     * @param Token the Token to set
     */
    public void setToken(TipoToken Token) {
        this.token = Token;
    }

    /**
     * @return the Lexema
     */
    public String getLexema() {
        return lexema;
    }

    /**
     * @param Lexema the Lexema to set
     */
    public void setLexema(String Lexema) {
        this.lexema = Lexema;
    }
    
    /**
     * @return the Tipo
     */
    public TipoVar getTipoVar() {
        return tipoVar;
    }

    /**
     * @param Tipo the Tipo to set
     */
    public void setTipoVar(TipoVar Tipo) {
        this.tipoVar = Tipo;
    }
    
    
    @Override
    public String toString(){
        switch(token){
            case ID:
            case NUM:
            case CONST_LOGICA:
            case CONST_CHAR:
            case CONST_STR:
                return "<"+token.toString()+","+lexema+","+tipoVar.toString()+">";
                
            case OP_ADD:
            case OP_MUL:
            case OP_REL:
                return "<"+token.toString()+","+lexema+">";

            default:
                return "<"+token.toString()+">";
        }
    }
    
}
