package Persistencia.converters;

import domain.Entidad.Usuario.Rol;
import domain.Entidad.Usuario.RolAdministrador;
import domain.Entidad.Usuario.RolEstandar;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter(autoApply = true)
public class RolAttributeConverter implements AttributeConverter<Rol,Character> {
    @Override
    public Character convertToDatabaseColumn(Rol rol) {
        return rol instanceof RolAdministrador? 'a' : 'e';
    }

    @Override
    public Rol convertToEntityAttribute(Character dbData) {
        return dbData == 'a'? new RolAdministrador():new RolEstandar();
    }

}
