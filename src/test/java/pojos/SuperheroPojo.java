package pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import lombok.Data;
import utils.serializers.DateDeserializer;
import utils.serializers.DateSerilizer;
import utils.serializers.IdSerializer;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SuperheroPojo {
    private String mainSkill;
    private String gender;
    private String city;
    private String phone;
    private String fullName;
    @JsonSerialize(using = IdSerializer.class)
    private int id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonSerialize(using = DateSerilizer.class)
    private LocalDate birthDate;
}
