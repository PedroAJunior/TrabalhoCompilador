/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladorPapoMane;

import java.util.HashMap;

/**
 *
 * @author henrique
 */
public class TabelaSimbolos {

    private HashMap<String, RegistroLexico> hsmTabela;

    public TabelaSimbolos() {
        hsmTabela = new HashMap<>();
        adicionarPalavrasReservadas();
    }

    private void adicionarPalavrasReservadas() {
        RegistroLexico Reg;

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.PRINCIPAL);
        Reg.setLexema("principal");
        hsmTabela.put("principal", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.FIM);
        Reg.setLexema("fim");
        hsmTabela.put("fim", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.VARIAVEIS);
        Reg.setLexema("variaveis");
        hsmTabela.put("variaveis", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.FIM_VARIAVEIS);
        Reg.setLexema("fim_variaveis");
        hsmTabela.put("fim_variaveis", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.INTEIRO);
        Reg.setLexema("inteiro");
        hsmTabela.put("inteiro", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.REAL);
        Reg.setLexema("real");
        hsmTabela.put("real", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.CARACTERE);
        Reg.setLexema("caractere");
        hsmTabela.put("caractere", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.SEQUENCIA);
        Reg.setLexema("sequencia");
        hsmTabela.put("sequencia", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.LOGICO);
        Reg.setLexema("logico");
        hsmTabela.put("logico", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.SEM_TIPO);
        Reg.setLexema("sem_tipo");
        hsmTabela.put("sem_tipo", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.RETORNAR);
        Reg.setLexema("retornar");
        hsmTabela.put("retornar", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.SE);
        Reg.setLexema("se");
        hsmTabela.put("se", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.ENTAO);
        Reg.setLexema("entao");
        hsmTabela.put("entao", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.SENAO);
        Reg.setLexema("senao");
        hsmTabela.put("senao", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.SENAO_SE);
        Reg.setLexema("senao_se");
        hsmTabela.put("senao_se", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.FIM_SE);
        Reg.setLexema("fim_se");
        hsmTabela.put("fim_se", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.ENTRADA);
        Reg.setLexema("entrada");
        hsmTabela.put("entrada", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.SAIDA);
        Reg.setLexema("saida");
        hsmTabela.put("saida", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.ENQUANTO);
        Reg.setLexema("enquanto");
        hsmTabela.put("enquanto", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.FIM_ENQUANTO);
        Reg.setLexema("fim_enquanto");
        hsmTabela.put("fim_enquanto", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.PARA);
        Reg.setLexema("para");
        hsmTabela.put("para", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.FIM_PARA);
        Reg.setLexema("fim_para");
        hsmTabela.put("fim_para", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.DE);
        Reg.setLexema("de");
        hsmTabela.put("de", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.ATE);
        Reg.setLexema("ate");
        hsmTabela.put("ate", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.PASSO);
        Reg.setLexema("passo");
        hsmTabela.put("passo", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.FACA);
        Reg.setLexema("faca");
        hsmTabela.put("faca", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.FUNCAO);
        Reg.setLexema("funcao");
        hsmTabela.put("funcao", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.FIM_FUNCAO);
        Reg.setLexema("fim_funcao");
        hsmTabela.put("fim_funcao", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.CONST_LOGICA);
        Reg.setLexema("verdade");
        Reg.setTipoVar(TipoVar.BOOLEANO);
        hsmTabela.put("verdade", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.CONST_LOGICA);
        Reg.setLexema("falso");
        Reg.setTipoVar(TipoVar.BOOLEANO);
        hsmTabela.put("falso", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.OP_ADD);
        Reg.setLexema("ou");
        hsmTabela.put("ou", Reg);

        Reg = new RegistroLexico();
        Reg.setToken(TipoToken.OP_MUL);
        Reg.setLexema("e");
        hsmTabela.put("e", Reg);

    }

    public RegistroLexico procurarAdicionarId(String Lexema, int linha, int coluna) {
        RegistroLexico Reg;
        Reg = hsmTabela.get(Lexema);
        if (Reg == null) { //Se for null não exisita na tabela
            // Então devemos adicionar o registro na tabela
            Reg = new RegistroLexico();
            Reg.setToken(TipoToken.ID);
            Reg.setLexema(Lexema);
            Reg.setLinha(linha);
            Reg.setColuna(coluna);
            hsmTabela.put(Lexema, Reg);
        } else {
            Reg.setLinha(linha);
            Reg.setColuna(coluna);
        }
        return Reg;
    }

    public void print() {
        System.out.println(hsmTabela);
    }
}
