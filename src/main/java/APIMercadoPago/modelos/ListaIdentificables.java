package APIMercadoPago.modelos;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ListaIdentificables {

    private List<Identificador> identificadores;

    public ListaIdentificables (Identificador[] identificadores){
        this.identificadores = Arrays.asList(identificadores);
    }

    public ListaIdentificables (List<Identificador> identificadores){
        this.identificadores = identificadores;
    }

    public String idDeIdentificador(String nombreIdentificador) throws Exception {
        Optional<Identificador> posiblePais = this.identificadores.stream().filter(identificador -> identificador.name.equals(nombreIdentificador)).findFirst();
        if(posiblePais.isPresent()) {
            Identificador paisBuscado = posiblePais.get();
            return paisBuscado.id;
        }
        else throw new Exception("No existe el pais buscado");
    }

    public void mostrarNombres (){
        for(Identificador pais: identificadores){
            System.out.println(pais.name);
        }
    }
}
