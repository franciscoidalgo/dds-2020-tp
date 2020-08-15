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



        //Prueba: Informacion del pais, la provincia y la ciudad basandose en el codigo postal.
        servicioMercadoLibre.listaDePaises().mostrarNombres();
        System.out.println("Ingrese el nombre de su pais: ");
        Scanner entradaScanner = new Scanner(System.in);
        String nombrePaisElegido = entradaScanner.nextLine();
        Pais paisElegido = servicioMercadoLibre.paisDeNombre(nombrePaisElegido);
        servicioMercadoLibre.listaDeProvincias(paisElegido).mostrarNombres();
        System.out.println("Ingrese el nombre de la provincia: ");
        String nombreProvinciaElegida = entradaScanner.nextLine();
        Provincia provinciaElegida = servicioMercadoLibre
                .provinciaDeId(servicioMercadoLibre.listaDeProvincias(paisElegido).idDeIdentificador(nombreProvinciaElegida));
        servicioMercadoLibre.listaDeCiudades(provinciaElegida).mostrarNombres();
        System.out.println("Ingrese el nombre de la ciudad: ");
        String nombreCiudadElegida = entradaScanner.nextLine();
        Ciudad ciudadElegida = servicioMercadoLibre
                .ciudadDeId(servicioMercadoLibre.listaDeCiudades(provinciaElegida).idDeIdentificador(nombreCiudadElegida));
        System.out.println("Ingrese su codigo postal: ");
        String codigoPostal = entradaScanner.nextLine();

        CodigoPostalAPI informacionCodigoPostal = servicioMercadoLibre
                .informacionCodigoPostal(servicioMercadoLibre.paisDeNombre(nombrePaisElegido).id, codigoPostal);

        entradaScanner.close();

    }

}