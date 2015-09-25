package br.com.trabalhocompiladores;



public class TratamentoErros {
    
    private static TratamentoErros Instancia = null;
    
    private int QuantidadeErros = 0;
    public static final int MAX_ERROS = 20;
    
    private TratamentoErros(){
        
    }
    
    public static TratamentoErros getInstancia(){
        if(Instancia==null){
            Instancia = new TratamentoErros();
        }
        return Instancia;
    }
    
    public void erroLexico(int Linha, int Coluna, String Msg){
        QuantidadeErros++;
        System.out.printf("Erro lexico na linha %d, coluna %d: %s \n",Linha,Coluna,Msg);
    }
    
    public int getQuantidadeErros(){
        return QuantidadeErros;
    }
    
    
}
