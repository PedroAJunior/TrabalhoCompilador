package br.com.trabalhocompiladores.util;

import br.com.trabalhocompiladores.TipoToken;
import br.com.trabalhocompiladores.TipoVar;

public class TipoTokenUtil {
    private TipoTokenUtil() {
    }
	
    public static TipoVar getTipo(TipoToken tipoToken){
	TipoVar tipo = null;
	switch (tipoToken) {
            case VERDADE:
            case FALSO:	
		tipo = TipoVar.BOOLEANO;
		break;
            default:
		tipo = TipoVar.SEM_TIPO;
		break;
	}
	return tipo;
    }
    
    public static TipoToken getTipoToken(TipoToken tipoToken){
        TipoToken tipo = null;
        
        switch(tipoToken){
           case E:
               tipo = TipoToken.OP_MUL;
               break;
           case OU:
               tipo = TipoToken.OP_ADD;
               break;    
            default:
               tipo = tipoToken;
               break;     
        }
        return tipo;
    }
}
