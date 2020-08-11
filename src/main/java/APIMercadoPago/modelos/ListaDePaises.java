package APIMercadoPago.modelos;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ListaDePaises {

    private List<Pais> paises;

    public ListaDePaises (Pais[] paises){
        this.paises = Arrays.asList(paises);
    }

    public String idDePais(String cualPais) throws Exception {
        Optional<Pais> posiblePais = this.paises.stream().filter(pais -> pais.name.equals(cualPais)).findFirst();
        if(posiblePais.isPresent()) {
            Pais paisBuscado = posiblePais.get();
            return paisBuscado.id;
        }
        else throw new Exception("No existe el pais buscado");
    }

    public List<Pais> paises() {
        return paises;
    }

}
