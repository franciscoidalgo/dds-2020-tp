package APIMercadoLibre.modelos;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ListaIdentificables  {



    private final List<Identificable> identificadores;

    public ListaIdentificables (Identificable[] identificadores){
        this.identificadores = Arrays.asList(identificadores);
    }

    public ListaIdentificables (List<Identificable> identificadores){
        this.identificadores = identificadores;
    }

    public String idDeIdentificador(String nombreIdentificador) throws Exception {
        Optional<Identificable> posiblePais = this.identificadores.stream().filter(identificador -> identificador.name.equals(nombreIdentificador)).findFirst();
        if(posiblePais.isPresent()) {
            Identificable paisBuscado = posiblePais.get();
            return paisBuscado.id;
        }
        else throw new Exception("No existe el elemento buscado");
    }
    public List<Identificable> getIdentificadores() {
        return identificadores;
    }
    public void mostrarNombres (){
        for(Identificable pais: identificadores){
            System.out.println(pais.name);
        }
    }
    public List<String> mostraId(){
        return this.identificadores.stream().map(identificador -> identificador.id).collect(Collectors.toList());
    }

}
