package br.com.trabalhocompiladores;



import br.com.trabalhocompiladores.util.TipoTokenUtil;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class TabelaSimbolos {
    private HashMap<String, RegistroLexico> tabelaSimbolos;
	
    public TabelaSimbolos() {
        tabelaSimbolos = new HashMap<String, RegistroLexico>();
	initTable();
    }
	
    private void initTable(){
	for(TipoToken tipoToken : TipoToken.palavrasReservadas()){
            String name = TipoToken.getName(tipoToken);
            tabelaSimbolos.put(name, new RegistroLexico(name, TipoTokenUtil.getTipoToken(tipoToken), TipoTokenUtil.getTipo(tipoToken)));
	}
    }
	
    public RegistroLexico procurarAdicionarId(String lexema){
        RegistroLexico registro;
        registro = this.tabelaSimbolos.get(lexema);
        
        //Se for null não existe na tabela e o registro deve ser adicionado
        if(registro == null){
            registro = new RegistroLexico(lexema, TipoToken.ID);
            this.tabelaSimbolos.put(lexema, registro);
        } 
        return registro;
    }
	
    public void print(){
	for(Map.Entry<String, RegistroLexico> entry : tabelaSimbolos.entrySet()){
            System.out.println(this.formata(entry));
	}
    }
	
    private String formata(Map.Entry<String, RegistroLexico> entry){
	return MessageFormat.format("{0} : {1}", entry.getKey(), entry.getValue());
    }
}