package APIMercadoPago.modelos;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ListaDeProvincias {
    private List<Provincia> provincias;

    public ListaDeProvincias (List<Provincia> provincias){
        this.provincias = provincias;
    }

    public String idDePais(String cualProvincia) throws Exception {
        Optional<Provincia> posiblePais = this.provincias.stream().filter(pais -> pais.name.equals(cualProvincia)).findFirst();
        if(posiblePais.isPresent()) {
            Provincia provinciaBuscada = posiblePais.get();
            return provinciaBuscada.id;
        }
        else throw new Exception("No existe el pais buscado");
    }

    public void mostrarNombres (){
        for (Provincia provincia : provincias){
            System.out.println(provincia.name);
        }
    }

    public List<Provincia> paises() {
        return provincias;
    }
}
