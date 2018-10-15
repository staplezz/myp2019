package fciencias.myp2019;

import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
* @author Jorge Francisco Cortes Lopez.
* No. de cuenta: 31433098-1.
*/

/**
* <p>Clase principal para pedir al usuario la imágen y el texto</p>
*/
public class Proyecto02{

    public static void main(String[] args) {

        Esteganografo esteganografo = new Esteganografo();

        String archivoTexto = "";
        String nombreTexto = "";
        String nombreImagenSaliente = "";
        BufferedImage imagenFuente = null;

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
            MetodosTexto metodosTexto = new MetodosTexto();

            if(args.length != 4){
                Proyecto02.mensajeUso();
                System.exit(1);
            }

            BufferedImage imagenSalida = null;
            archivoTexto = metodosTexto.cargaTxt(args[1]);
            imagenFuente = MetodosImagen.cargaImagen(args[2]);
            nombreImagenSaliente = args[3];
            try{
                imagenSalida = 
                esteganografo.escondeMensaje(archivoTexto, imagenFuente);
            } catch (Exception e){
                System.out.println("Ocurrio un error al ocultar el mensaje.");
            }
            MetodosImagen.guardaImagen(imagenSalida, nombreImagenSaliente);
            System.out.println("Imagen guardada exitosamente");
        }

        // Opción develar
        if(args[0].equals("-u")){

            if(args.length != 3){
                Proyecto02.mensajeUso();
                System.exit(1);
            }

            String textoEscondido = "";
            imagenFuente = MetodosImagen.cargaImagen(args[1]);
            nombreTexto = args[2];
            try{
                textoEscondido = esteganografo.getTextoDeImagen(imagenFuente);
            } catch (Exception e) {
                System.out.println("Ocurrió un error al develar tu imágen.");
            }
            MetodosTexto.guardaTxt(textoEscondido, nombreTexto);
            System.out.println("Archivo guardado exitosamente");
        }

        System.out.println("Gracias por usar el programa.");
        System.exit(0);
    }

    //Instrucciones para uso del programa si ocurre algún error.
        static void mensajeUso(){
            System.out.println("Uso:");
            System.out.println("Para ocultar texto en una imagen:");
            System.out.println("java -jar Proyecto02 -jar -h <textoAOcultar.txt>" +
            "<imagenAUsar.png/jpg...> <imagenResultante>");
            System.out.println("Para develar texto de una imagen:");
            System.out.println("java -jar Proyecto02 -jar -u <imagenADevelar.png>" +
            "<archivoAGuardar.txt>");
            System.out.println("Ejemplo: " + "Proyecto02 -h quijote.txt" +
                "gatitos.png aquinohaynada.png");
        }
}
