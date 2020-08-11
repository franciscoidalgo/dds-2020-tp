package APIMercadoPago;

import APIMercadoPago.modelos.*;
import APIMercadoPago.services.ServicioMercadoLibre;

import java.util.Scanner;

public class TestApi {
    public static void main(String[] args) throws Exception {
        ServicioMercadoLibre servicioMercadoLibre = ServicioMercadoLibre.instancia();

        //Prueba: conversion de dolares a pesos
        imprimir("Conversion de dolar a peso argentino");
        ConversionDeMonedas conversionDeMonedas = servicioMercadoLibre.conversionDeMonedas("USD", "ARS");
        imprimir("Conversion: " + conversionDeMonedas.ratio +
                "\nConversion de mercado pago: " + conversionDeMonedas.mercado_pago_ratio);

        //Prueba: imprimir descripciones de las monedas
        ListaDeMonedas listaDeMonedas = servicioMercadoLibre.listaDeMonedas();
        imprimir("Lista de monedas: ");
        for (Moneda moneda : listaDeMonedas.monedas()){
            imprimir(moneda.description);
        }

        //Prueba: mostrar paises
        ListaDePaises listaDePaises = servicioMercadoLibre.listaDePaises();
        imprimir("De que pais desea buscar mas informacion?");
        for (Pais pais : listaDePaises.paises()){
            imprimir(pais.name);
        }

        //Prueba: seleccionar un pais con un nombre determinado
        Scanner entradaScanner = new Scanner(System.in);
        String nombrePaisElegido = entradaScanner.nextLine();
        PaisExtendido paisElegido = servicioMercadoLibre.paisDeNombre(nombrePaisElegido);
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
