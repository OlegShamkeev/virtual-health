package utils.services;

import pojos.ErrorPojo;
import pojos.SuperheroPojo;

import java.util.List;

import static io.restassured.RestAssured.given;

public class SuperheroService extends RestService {


    public <T> T createSuperhero(SuperheroPojo request, int statusExpected, Class<T> clazz) {
        return given()
                .spec(REQUEST_SPECIFICATION)
                .body(request)
                .post()
                .then()
                .statusCode(statusExpected)
                .extract()
                .as(clazz);
    }

    public List<SuperheroPojo> getSuperheroes() {
        return given()
                .spec(REQUEST_SPECIFICATION)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().jsonPath().getList("", SuperheroPojo.class);
    }

    public SuperheroPojo getSuperhero(String id) {
        return given()
                .spec(REQUEST_SPECIFICATION)
                .get("/" + id)
                .as(SuperheroPojo.class);
    }

    public <T> T updateSuperhero(String id, SuperheroPojo request, int statusExpected, Class<T> clazz) {
        return given()
                .spec(REQUEST_SPECIFICATION)
                .body(request)
                .put("/" + id)
                .then()
                .statusCode(statusExpected)
                .extract()
                .as(clazz);
    }

    public ErrorPojo deleteSuperhero(String id) {
        return given()
                .spec(REQUEST_SPECIFICATION)
                .delete("/" + id)
                .then()
                .statusCode(400)
                .extract()
                .as(ErrorPojo.class);
    }

    public void deleteSuperhero(int id) {
        given()
                .spec(REQUEST_SPECIFICATION)
                .delete("/" + id)
                .then()
                .statusCode(200);
    }

    @Override
    protected String getBasePath() {
        return "/superheroes";
    }
}
