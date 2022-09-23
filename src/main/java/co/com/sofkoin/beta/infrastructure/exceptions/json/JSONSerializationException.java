package co.com.sofkoin.beta.infrastructure.exceptions.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class JSONSerializationException extends RuntimeException{
    private final String message;
}
