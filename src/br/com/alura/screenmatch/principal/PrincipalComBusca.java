package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.excecao.ErroDeConversaoDeAnoException;
import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();
        List<Titulo> filmes = new ArrayList<>();
        String filme = "";

        while (!filme.equalsIgnoreCase("sair")) {


            System.out.println("Digite o filme que deseja buscar: ");
            filme = sc.nextLine();

            if (filme.equalsIgnoreCase("sair")){
                break;
            }

            // Para funcionar bote a sua APIkey aqui:
            String buscaFilme = "https://www.omdbapi.com/?t=" + filme + "&apikey=";


            try {
                // Primeiramente temos que ter um cliente
                HttpClient client = HttpClient.newHttpClient();

                // Depois fazemos a requisição
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(buscaFilme))
                        .build();

                // Agora precisamos pegar a resposta, e deve tratar uma exception
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


                String json = response.body();


                TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);

                Titulo meuTitulo = new Titulo(meuTituloOmdb);
                System.out.println("Título já convertido");
                System.out.println(meuTitulo);

                filmes.add(meuTitulo);
            } catch (NumberFormatException e) {
                System.out.println("Aconteceu um erro: ");
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Algum erro de argumento na busca, verifique o endereço");
            } catch (ErroDeConversaoDeAnoException e) {
                System.out.println(e.getMessage());
            }
        }
        FileWriter escrita = new FileWriter("filmes.json");
        escrita.write(gson.toJson(filmes));
        escrita.close();
        System.out.println("O programa finalizou corretamente!");

    }

}
