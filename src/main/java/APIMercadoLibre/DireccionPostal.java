package APIMercadoLibre;

import APIMercadoLibre.modelos.CodigoPostalAPI;

import java.util.Optional;

public class DireccionPostal {
    private String calle;
    private Integer altura;
    private Optional<Integer> piso;
    private Optional<String> depto;
    private CodigoPostalAPI codigoPostalAPI;
}
