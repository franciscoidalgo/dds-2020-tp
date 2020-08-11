package APIMercadoPago;

import APIMercadoPago.modelos.*;
import APIMercadoPago.services.ServicioMercadoPago;

import java.util.Scanner;

public class TestApi {
    public static void main(String[] args) throws Exception {
        ServicioMercadoPago servicioMercadoPago = ServicioMercadoPago.instancia();

        //Prueba: imprimir descripciones de las monedas
        ListaDeMonedas listaDeMonedas = servicioMercadoPago.listaDeMonedas();
        imprimir("Lista de monedas: ");
        for (Moneda moneda : listaDeMonedas.monedas()){
            imprimir(moneda.description);
        }

        //Prueba: mostrar paises
        ListaDePaises listaDePaises = servicioMercadoPago.listaDePaises();
        imprimir("De que pais desea buscar mas informacion?");
        for (Pais pais : listaDePaises.paises()){
            imprimir(pais.name);
        }

        //Prueba: seleccionar un pais con un nombre determinado
        Scanner entradaScanner = new Scanner(System.in);
        String nombrePaisElegido = entradaScanner.nextLine();
        PaisExtendido paisElegido = servicioMercadoPago.paisDeNombre(nombrePaisElegido);
        imprimir("Moneda: " + paisElegido.currency_id +
                "\nProvincias: ");

        //Prueba: mostrar provincias (states) de un pais
        for(Provincia provinciaSimp : paisElegido.states){
            imprimir(provinciaSimp.name);
        }

    }

    private static void imprimir (String x){
        System.out.println(x);
    }
}
