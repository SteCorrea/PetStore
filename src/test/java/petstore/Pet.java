//1-pacote(pode conter várias class)
package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

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
    @Test(priority = 1) // Identifica o metodo ou funcao como um teste para o TestNG
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
                .body("name", is ("BobCesa"))
                .body("status", is ("available"))
                .body("category.name", is ("STE1921ZI"))
                .body("tags.name", contains("SemanaDoTeste"))
        ;


    }
    // Consultar - Read - Get
    @Test(priority = 2)
    public void consultarPet(){
        String petId = "201608210515";

        String token =
        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("category.name", is ("STE1921ZI"))
                .body("tags.name", contains("SemanaDoTeste"))
        .extract()
                .path("category.name")

        ;
        System.out.println("O token é " + token);

    }
    // Alterar - Update - PUT
    @Test(priority = 3)
    public void alterarPet() throws IOException {
       String jsonBody = LerJson("db/pet2.json");

       given()
               .contentType("application/json")
               .log().all()
               .body(jsonBody)
       .when()
               .put(uri) // nome do metodo, vai passar o endereco
       .then()
               .log().all()
               .statusCode(200)
               .body("name", is ("BobCesa"))
               .body("status", is ("sold"))

       ;
    }

    // Apagar - Delete -
    @Test(priority = 4)
    public void excluirPet(){
        String petId = "201608210515";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is (200))
                .body("type", is("unknown"))
                .body("message", is (petId))
        ;
    }



}
