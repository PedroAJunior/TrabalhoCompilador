package br.com.trabalhocompiladores;



import java.text.MessageFormat;

public class RegistroLexico {
	
	private String lexema;
	private TipoToken token;
	private TipoVar tipo = TipoVar.SEM_TIPO;
	
	public RegistroLexico() {
		
	}
	
	public RegistroLexico(String lexema, TipoToken token) {
		this.lexema = lexema;
		this.token = token;
	}
	
	public RegistroLexico(String lexema, TipoToken token, TipoVar tipo) {
		this.lexema = lexema;
		this.token = token;
		this.tipo = tipo;
	}

	public String getLexema() {
		return lexema;
	}
	
	public void setLexema(String lexema) {
		this.lexema = lexema;
	}
	
	public TipoToken getToken() {
		return token;
	}
	
	public void setToken(TipoToken token) {
		this.token = token;
	}

	public TipoVar getTipoVar() {
		return tipo;
	}

	public void setTipoVar(TipoVar tipo) {
		this.tipo = tipo;
	}
	
	@Override
    public String toString(){
        return MessageFormat.format("<TOKEN: {0}, LEXEMA: {1}, TIPO: {2}>", this.token, this.lexema, this.tipo);
    }
	
}
