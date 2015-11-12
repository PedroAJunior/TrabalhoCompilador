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
    
    public int getQuantidadeErros(){
        return QuantidadeErros;
    }

    public void erroLexico(int Linha, int Coluna, String Msg){
        QuantidadeErros++;
        System.out.printf("Linha: %d,Coluna: %d - erro Lexico: %s \n",Linha,Coluna,Msg);
        verificaMaxErros();
    }
    
    public void erroSintatico(String Mensagem){
        QuantidadeErros++;
        System.out.printf("erro sintático: %s \n",Mensagem);
        verificaMaxErros();
    }

    public void erroSintatico(RegistroLexico Prox, TipoToken Esperado){
        QuantidadeErros++;
        System.out.printf("Linha: "+Prox.getLinha()+", Coluna: "+Prox.getColuna()+" - erro sintático: Recebido %s, Esperado %s \n",Prox,Esperado);
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
