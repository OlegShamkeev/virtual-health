import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojos.ErrorPojo;
import pojos.SuperheroPojo;
import utils.RestWrapper;
import utils.SuperheroGenerator;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class SuperheroTest {

    private static RestWrapper api;

    @BeforeAll
    public static void setApi() {
        api = new RestWrapper();
    }

    @Test
    public void createSuperhero() {
        SuperheroPojo newOne = SuperheroGenerator.generateSuperhero(api.faker);
        //attempt to create superhero with incorrect date of birth format
        newOne.setBirthDate("2002.04.03");
        ErrorPojo errorResponse = api.superhero.createSuperhero(newOne, 400, ErrorPojo.class);
        assertThat(errorResponse.getMessage()).contains("java.time.LocalDate");

        //attempt to create superhero with incorrect gender value
        newOne.setBirthDate(api.faker.date().past(12000, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
        newOne.setGender(api.faker.regexify("[a-zA-Z0-9]{6}"));
        errorResponse = api.superhero.createSuperhero(newOne, 400, ErrorPojo.class); //API should give error to value not equal F or M
        assertThat(errorResponse.getMessage()).contains(newOne.getGender());

        //attempt to create superhero with incorrect id value
        newOne.setGender(api.faker.options().nextElement(Arrays.asList("F", "M")));
        String randomId = api.faker.regexify("[a-zA-Z0-9]{10}");
        newOne.setId(randomId);
        errorResponse = api.superhero.createSuperhero(newOne, 400, ErrorPojo.class); // API should return error to id value as String
        assertThat(errorResponse.getMessage()).contains(randomId);

        //attempt to create superhero without mandatory field city
        newOne.setId("");
        newOne.setCity("");
        errorResponse = api.superhero.createSuperhero(newOne, 403, ErrorPojo.class);

        //check correct create superhero
        newOne.setCity(api.faker.regexify("[a-zA-Z0-9]{10}"));
        SuperheroPojo response = api.superhero.createSuperhero(newOne, 200, SuperheroPojo.class);
        assertThat(response).usingRecursiveComparison().ignoringFields("id").isEqualTo(newOne); //API contains bug field phone!

        //check created superhero presented in the list
        List<SuperheroPojo> superheroes = api.superhero.getSuperheroes(); //API returns correct list of superheroes only on the (mod/2) request
        assertThat(superheroes).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id").contains(newOne); //Also bug with phone field
    }

    @Test
    public void updateSuperhero() {
        List<SuperheroPojo> superheroes = api.superhero.getSuperheroes();
        List<Integer> listIds = takeIds(superheroes);
        int idToBeUpdated = api.faker.options().nextElement(listIds);
        SuperheroPojo updateSuperhero = api.superhero.getSuperhero(String.valueOf(idToBeUpdated));
        updateSuperhero.setId("");

        //attempt to update superhero with incorrect id value
        String randomId = api.faker.regexify("[a-zA-Z]{4}");
        ErrorPojo errorResponse = api.superhero.updateSuperhero(randomId, updateSuperhero, 400, ErrorPojo.class);
        assertThat(errorResponse.getMessage()).contains(randomId);

        //attempt to update superhero with incorrect birthDate format
        updateSuperhero.setBirthDate("2002.12.03");
        errorResponse = api.superhero.updateSuperhero(String.valueOf(idToBeUpdated), updateSuperhero, 400, ErrorPojo.class);
        assertThat(errorResponse.getMessage()).contains("2002.12.03");

        //attempt to update superhero to the incorrect gender value
        updateSuperhero.setBirthDate(api.faker.date().past(12000, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
        updateSuperhero.setGender("Gender");
        errorResponse = api.superhero.updateSuperhero(String.valueOf(idToBeUpdated), updateSuperhero, 400, ErrorPojo.class);
        assertThat(errorResponse.getMessage()).contains("Gender"); //API should give error to value not equal F or M

        //correct update of superhero
        updateSuperhero.setGender(api.faker.options().nextElement(Arrays.asList("F", "M")));
        updateSuperhero.setFullName(api.faker.regexify("[a-zA-Z0-9]{10}"));
        updateSuperhero.setMainSkill(api.faker.regexify("[a-zA-Z0-9]{10}"));
        updateSuperhero.setPhone(api.faker.numerify("##########"));
        updateSuperhero.setCity(api.faker.regexify("[a-zA-Z0-9]{10}"));
        SuperheroPojo updateResult = api.superhero.updateSuperhero(String.valueOf(idToBeUpdated), updateSuperhero, 200, SuperheroPojo.class);
        assertThat(updateResult).usingRecursiveComparison().ignoringFields("id").isEqualTo(updateSuperhero);
        updateResult = api.superhero.getSuperhero(String.valueOf(idToBeUpdated)); //check that superhero really updated
        assertThat(updateResult).usingRecursiveComparison().ignoringFields("id").isEqualTo(updateSuperhero);
    }

    @Test
    public void deleteSuperhero() {

        //attempt to delete by random String id
        String randomId = api.faker.regexify("[a-zA-Z]{4}");
        ErrorPojo errorResponse = api.superhero.deleteSuperhero(randomId); //API should give error on String value in id
        assertThat(errorResponse.getMessage()).contains(randomId);

        List<SuperheroPojo> superheroes = api.superhero.getSuperheroes();
        List<Integer> listIds = takeIds(superheroes);

        //attempt to delete by non-existing id
        int max = listIds
                .stream()
                .mapToInt(v -> v)
                .max()
                .orElse(9999);

        errorResponse = api.superhero.deleteSuperhero(String.valueOf(max + 1)); //API should return error for non-existing resource to be deleted
        assertThat(errorResponse.getMessage()).contains(String.valueOf(max + 1));

        //correct delete of superhero
        int idToDelete = api.faker.options().nextElement(listIds);
        api.superhero.deleteSuperhero(idToDelete);
        //check that superhero deleted
        superheroes = api.superhero.getSuperheroes();
        listIds = takeIds(superheroes);
        assertThat(listIds).doesNotContain(idToDelete); //API doesn't delete superhero actually
    }

    private List<Integer> takeIds(List<SuperheroPojo> list) {
        return list.
                stream()
                .map(SuperheroPojo::getId)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
