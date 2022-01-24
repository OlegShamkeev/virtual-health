package utils;

import com.github.javafaker.Faker;
import pojos.SuperheroPojo;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class SuperheroGenerator {

    public static SuperheroPojo generateSuperhero(Faker faker) {
        SuperheroPojo superhero = new SuperheroPojo();
        superhero.setBirthDate(faker.date().past(12000, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        superhero.setCity(faker.regexify("[a-zA-Z0-9]{10}"));
        superhero.setGender(faker.options().nextElement(Arrays.asList("F", "M")));
        superhero.setFullName(faker.regexify("[a-zA-Z0-9]{10}"));
        superhero.setMainSkill(faker.regexify("[a-zA-Z0-9]{10}"));
        return superhero;
    }
}
