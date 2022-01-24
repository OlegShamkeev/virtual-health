import com.github.javafaker.Faker;
import utils.RestWrapper;
import utils.SuperheroGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojos.SuperheroPojo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SuperheroTest {

    private static RestWrapper api;

    @BeforeAll
    public static void setApi() {
        api = new RestWrapper();
    }

    @Test
    public void getSuperhero() {
        List<SuperheroPojo> superheroes = api.superhero.getSuperheroes();

        SuperheroPojo newOne = SuperheroGenerator.generateSuperhero(api.faker);
        SuperheroPojo response = api.superhero.createSuperhero(newOne);


        assertThat(superheroes).extracting(SuperheroPojo::getFullName).contains("Doctor Strange");
        System.out.println(superheroes);
    }
}
