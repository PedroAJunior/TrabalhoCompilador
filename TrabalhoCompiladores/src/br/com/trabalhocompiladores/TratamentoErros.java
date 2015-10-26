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
    
    public void zerarErros(){
        this.QuantidadeErros = 0;
    }
    
    public void erroLexico(int Linha, int Coluna, String Msg){
        QuantidadeErros++;
        System.out.printf("Erro lexico na linha %d, coluna %d: %s \n",Linha,Coluna,Msg);
    }
    
    public int getQuantidadeErros(){
        return QuantidadeErros;
    }
    
    public void erroSintatico(String Mensagem){
        QuantidadeErros++;
        System.out.printf("erro sintático: %s \n",Mensagem);
        verificaMaxErros();
    }
    
    public void erroSintatico(RegistroLexico Prox, TipoToken Esperado){
        QuantidadeErros++;
        System.out.printf("erro sintático: Recebido %s, Esperado %s \n",Prox,Esperado);
        verificaMaxErros();
    }
    
    private void verificaMaxErros(){
        if(QuantidadeErros >= MAX_ERROS){
            relatorioErros();
            System.out.println(" Muitos erros no código, compilação interrompida.");
            System.exit(1);
        }
    }
    
    public void relatorioErros(){
        if(QuantidadeErros>1)
            System.out.printf("%d erros encontrados.\n",QuantidadeErros);
        else
            System.out.printf("%d erro encontrado.\n",QuantidadeErros);
    }
    
    
}
