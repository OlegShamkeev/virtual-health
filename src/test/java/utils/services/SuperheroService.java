package utils.services;

import pojos.SuperheroPojo;

import java.util.List;

import static io.restassured.RestAssured.given;

public class SuperheroService extends RestService {


    public SuperheroPojo createSuperhero(SuperheroPojo request) {
        return given()
                .spec(REQUEST_SPECIFICATION)
                .body(request)
                .log().all()
                .post()
                .as(SuperheroPojo.class);
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

    public SuperheroPojo getSuperhero(int id) {
        return given()
                .spec(REQUEST_SPECIFICATION)
                .get("/" + id)
                .as(SuperheroPojo.class);
    }

    @Override
    protected String getBasePath() {
        return "/superheroes";
    }
}
