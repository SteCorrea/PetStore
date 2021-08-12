//1-pacote(pode conter várias class)
package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

//2-bibliotecas
//3-classe
public class Pet {
    //3.1-atributos(são adjetivos, caracteristicas do objeto)
    String uri = "https://petstore.swagger.io/v2/pet"; // endereco da entidade Pet

    //3.2-metodos/funções (retornam nenhum valor / fazem um ação e devolve um resultado)
    public String LerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
        //ler o arquivo json e retona o que esta pedindo
    }

    // Incluir - Create  - Post
    @Test // Identifica o metodo ou funcao como um teste para o TestNG
    public void incluirPet() throws IOException {
        String jsonBody = LerJson("db/pet1.json");

        // Sintaxe Gherkin - cenario de teste (para nao azedar a comunicacao)
        // Dado(onde estou, quem sou eu) - Quando(faço tal ação) - Entao(acontece tal reação)
        // Given - When - Then

        given() //Dado
                .contentType("application/json") //comum em API REST - nas antigas era "text/xml"
                .log().all()
                .body(jsonBody)
        .when() //Quando
                .post(uri)
        .then() //Entao
                .log().all()
                .statusCode(200)
        ;


    }



}
