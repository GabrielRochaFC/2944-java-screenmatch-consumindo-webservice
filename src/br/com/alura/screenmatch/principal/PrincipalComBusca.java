package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.excecao.ErroDeConversaoDeAnoException;
import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("Digite o filme que deseja buscar: ");
        Scanner sc = new Scanner(System.in);
        String filme = sc.nextLine();
        String buscaFilme = "https://www.omdbapi.com/?t=" + filme + "&apikey=eddea30a";
        try{
            // Primeiramente temos que ter um cliente
            HttpClient client = HttpClient.newHttpClient();

            // Depois fazemos a requisição
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(buscaFilme))
                    .build();

            // Agora precisamos pegar a resposta, e deve tratar uma exception
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            String json = response.body();

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

            TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);

            Titulo meuTitulo = new Titulo(meuTituloOmdb);
            System.out.println("Título já convertido");
            System.out.println(meuTitulo);
        } catch (NumberFormatException e) {
            System.out.println("Aconteceu um erro: ");
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Algum erro de argumento na busca, verifique o endereço");
        } catch (ErroDeConversaoDeAnoException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("O programa finalizou corretamente!");

    }

}
