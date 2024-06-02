package br.com.alura.screenmatch.exercicios;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.Strictness;

public class PrincipalExercicios {
    public static void main(String[] args) {
        String json = """
            {
               "nome":"Gabriel",
               "idade":"23",
               "cidade":"Brasília"
            }
            """;
        Gson gson = new GsonBuilder().setStrictness(Strictness.LENIENT).create();
        Pessoa pessoa = gson.fromJson(json, Pessoa.class);
        System.out.println(pessoa);
    }

}
