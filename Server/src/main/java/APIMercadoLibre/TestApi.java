package APIMercadoLibre;

import APIMercadoLibre.modelos.*;
import java.util.List;
import java.util.Scanner;

public class TestApi {
    public static void main(String[] args) throws Exception {
        InfoMercadoLibre infoMercadoLibre = InfoMercadoLibre.instancia();
        List<Pais> paises = infoMercadoLibre.getListaDePaises();
        List<Provincia> provincias = infoMercadoLibre.getListaDeProvincias();
        /*
        //Prueba: imprimir descripciones de las monedas
        List<Moneda> listaDeMonedas = servicioMercadoLibre.listaDeMonedas();
        System.out.println("Lista de monedas: ");
        for (Moneda moneda : listaDeMonedas){
            System.out.println(moneda.description);
        }
        */

        //Prueba: Informacion del pais, la provincia y la ciudad basandose en el codigo postal.
        System.out.println("Lista de paises");
        for(Pais pais : paises){
            System.out.println(pais.name);
        }
        System.out.println("Ingrese el nombre de su pais: ");
        Scanner entradaScanner = new Scanner(System.in);
        String nombrePaisElegido = entradaScanner.nextLine();
        Pais paisElegido = infoMercadoLibre.paisDeNombre(nombrePaisElegido);
        for(Identificable provincia : paisElegido.mostrarProvincias()){
            System.out.println(provincia.name);
        }
        System.out.println("Ingrese el nombre de la provincia: ");
        String nombreProvinciaElegida = entradaScanner.nextLine();
        Provincia provinciaElegida = infoMercadoLibre.provinciaDeNombre(nombreProvinciaElegida);
        for(Ciudad ciudad : provinciaElegida.cities){
            System.out.println(ciudad.name);
        }
        System.out.println("Ingrese el nombre de la ciudad: ");
        String nombreCiudadElegida = entradaScanner.nextLine();
        System.out.println("Ingrese su codigo postal: ");
        String codigoPostal = entradaScanner.nextLine();

        AdapterDireccionMercadoLibre adapterDireccionMercadoLibre = new AdapterDireccionMercadoLibre(paisElegido, provinciaElegida, nombreCiudadElegida);

        DireccionPostal direccionPostal = new DireccionPostal("Una calle", 12345, adapterDireccionMercadoLibre);

        System.out.println("Direccion: " + direccionPostal.getCiudad() + ", " + direccionPostal.getProvincia() + ", " + direccionPostal.getPais());

        entradaScanner.close();

    }

}
