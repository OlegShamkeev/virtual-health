package utils;

import com.github.javafaker.Faker;
import utils.services.SuperheroService;

public class RestWrapper {

    public SuperheroService superhero;
    public Faker faker;

    public RestWrapper() {
        superhero = new SuperheroService();
        faker = new Faker();
    }
}
