package APIMercadoPago;

import APIMercadoPago.modelos.*;
import APIMercadoPago.services.ServicioMercadoLibre;

import java.util.Scanner;

public class TestApi {
    public static void main(String[] args) throws Exception {
        ServicioMercadoLibre servicioMercadoLibre = ServicioMercadoLibre.instancia();

        /*
        //Prueba: conversion de dolares a pesos
        System.out.println("Conversion de dolar a peso argentino");
        ConversionDeMonedas conversionDeMonedas = servicioMercadoLibre.conversionDeMonedas("USD", "ARS");
        System.out.println("Conversion: " + conversionDeMonedas.ratio +
                "\nConversion de mercado pago: " + conversionDeMonedas.mercado_pago_ratio);

        //Prueba: imprimir descripciones de las monedas
        ListaDeMonedas listaDeMonedas = servicioMercadoLibre.listaDeMonedas();
        System.out.println("Lista de monedas: ");
        for (Moneda moneda : listaDeMonedas.monedas()){
            System.out.println(moneda.description);
        }
        */

        //Prueba: mostrar paises
        ListaDePaises listaDePaises = servicioMercadoLibre.listaDePaises();
        System.out.println("De que pais desea buscar mas informacion?");
        listaDePaises.mostrarNombres();

        //Prueba: seleccionar un pais con un nombre determinado
        Scanner entradaScanner = new Scanner(System.in);
        String nombrePaisElegido = entradaScanner.nextLine();
        PaisExtendido paisElegido = servicioMercadoLibre.paisDeNombre(nombrePaisElegido);
        System.out.println("Moneda: " + paisElegido.currency_id +
                "\nProvincias: ");

        //Prueba: mostrar provincias (states) de un pais
        ListaDeProvincias provinciasDelPais = servicioMercadoLibre.listaDeProvincias(paisElegido);

    }

}
