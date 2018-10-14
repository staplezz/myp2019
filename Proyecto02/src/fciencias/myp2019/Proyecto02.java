package fciencias.myp2019;

import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.ArrayList;

/**
* <p>Clase principal para pedir al usuario la imágen y el texto</p>
*/
public class Proyecto02{

	public static void main(String[] args) {

		MetodosTxt ltxt = new MetodosTxt();
		MetodosImagen imgMet = new MetodosImagen();
		Esteganografo est = new Esteganografo();

		String archivoTxt = "";
		String nombreTxt = "";
		String nombreImg = "";
        BufferedImage imagen = null;

        // Si no hay argumentos le informamos al usuario que lea como usar el programa
        if (args.length == 0) {
            Proyecto02.mensajeUso();
            System.exit(1);
        }

        if (!args[0].equals("-h") && !args[0].equals("-u") ){
            Proyecto02.mensajeUso();
            System.exit(1);
        }

        // Opción ocultar
    	if(args[0].equals("-h")){
    		archivoTxt = ltxt.leeTxt(args[1]);
    		imagen = imgMet.cargaImagen(args[2]);
    		nombreImg = args[3];
    		est.escondeMensaje(archivoTxt, imagen, nombreImg);
    		System.out.println("Gracias por usar el programa.");
    		System.exit(0);
    	}

    	// Opción develar
    	if(args[0].equals("-u")){
    		imagen = imgMet.cargaImagen(args[1]);
    		nombreTxt = args[2];
    		est.getTextoDeImagen(imagen, nombreTxt);
    		System.out.println("Gracias por usar el programa.");
    		System.exit(0);
    	}

	}

	//Instrucciones para uso del programa si ocurre algún error.
	    static void mensajeUso(){
	    	System.out.println("Uso:");
	    	System.out.println("Para ocultar texto en una imagen:");
	    	System.out.println("Proyecto02 -jar -h <textoAOcultar.txt> <imagenAUsar.png/jpg...> <imagenResultante>");
	    	System.out.println("Para develar texto de una imagen:");
	    	System.out.println("Proyecto02 -jar -u <imagenADevelar.png> <archivoAGuardar.txt>");
	    	System.out.println("Ejemplo: " + "Proyecto02 -h quijote.txt gatitos.png aquinohaynada");
	    }
}
