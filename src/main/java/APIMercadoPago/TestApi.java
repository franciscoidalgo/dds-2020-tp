package APIMercadoPago;

import APIMercadoPago.modelos.ListaDePaises;
import APIMercadoPago.modelos.Pais;
import APIMercadoPago.modelos.ProvinciaSimp;
import APIMercadoPago.services.ServicioMercadoPago;

import java.io.IOException;
import java.util.Scanner;

public class TestApi {
    public static void main(String[] args) throws Exception {
        ServicioMercadoPago servicioMercadoPago = ServicioMercadoPago.instancia();
        ListaDePaises listaDePaises = servicioMercadoPago.listaDePaises();
        System.out.println("De que pais desea buscar mas informacion?");
        for (Pais pais : listaDePaises.paises()){
            System.out.println(pais.name);
        }
        Scanner entradaScanner = new Scanner(System.in);
        String nombrePaisElegido = entradaScanner.nextLine();
        Pais paisElegido = servicioMercadoPago.paisDeNombre(nombrePaisElegido);
        System.out.println("Moneda: " + paisElegido.currency_id +
                "\nProvincias: ");
        for(ProvinciaSimp provinciaSimp : paisElegido.states){
            System.out.println(provinciaSimp.name);
        }
    }
}
