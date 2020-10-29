package Persistencia.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BooleanConverter implements AttributeConverter<Boolean, Character> {

    @Override
    public Character convertToDatabaseColumn(Boolean aBoolean) {
        return aBoolean? 'T' : 'F';
    }

    @Override
    public Boolean convertToEntityAttribute(Character character) {
        return character.equals('T');
    }
}
