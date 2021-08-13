package petstore;

import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class User {

    String uri = "https://petstore.swagger.io/v2/user";

    public String LerJson(String caminhojson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhojson)));
    }
@Test
    public void incluirUser() throws IOException {
        String jsonBody = LerJson("db/user1.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("phone", is ("2124240303"))
        ;
    }
    @Test
    public void consultarUser(){
        String userID = "stefanycorrea";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + userID )
        .then()
                .log().all()
                .statusCode(200)
                .body("phone", is("2124240303"))
        ;
    }


}
