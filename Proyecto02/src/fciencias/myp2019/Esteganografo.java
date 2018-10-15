package fciencias.myp2019;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
* @author Jorge Francisco Cortes Lopez.
* No. de cuenta: 31433098-1.
*/

/**
* <p>Clase para representar los algorítmos de esteganografía.</p>
*
* <p>Se implementan los métodos para esconder el mensaje dentro de un archivo
* de imágen usando una cadena y para obtener el mensaje escondido en alguna
* imágen guardandolo después en un archivo de texto.</p>
*/
public class Esteganografo {
    /** El objeto de tipo MetodosImagen para modificar y guardar imágenes. */
    private MetodosImagen metodosImagen = new MetodosImagen();
    /**La clase para usar los métodos de texto. */
    private MetodosTexto metodosTexto = new MetodosTexto();

    /**
    * Esconde el mensaje de tipo String en una imágen ya cargada de
    * tipo BufferedImage y luego de ejecutar el algorítmo actualiza
    * los valores de la imágen.
    * Al iniciar colocamos la bandera "K" en binario para después poder
    * saber si al develar la imágen buscamos un texto.
    * Al finalizar de esconder el mensaje se agrega una bandera con
    * "11111111" para delimitar de la demás información.
    * @param mensaje El mensaje a ser escondido en la imágen.
    * @param imagen La imágen a ser utilizada para esconder nuestro texto.
    * @return la imágen con el texto escondido a ser guardada.
    * @throws Exception si el texto que queremos esconder es demasiado grande
    * para el tamaño de la imágen.
    */
    public BufferedImage escondeMensaje(String mensaje,
        BufferedImage imagen) throws Exception {
        
        //Primero revisamos si podemos insertar el mensaje.
        if(!metodosImagen.textoCabeEnImagen(mensaje, imagen))
            throw new Exception("El texto que quieres insertar no cabe" + 
                " en la imágen, prueba con otra de mayor resolución.");

        //Convertimos el mensaje a binario.
        String mensajeBinario = metodosTexto.convierteCadenaABinario(mensaje);

        //Indica si ya terminamos de esconder el mensaje.
        boolean bloqueFinal = false;

        //Iteramos la imagen.
        outerloop:
        for(int fila = 0; fila < imagen.getHeight(); fila++) {
            for(int columna = 0; columna < imagen.getWidth(); columna++) {

                // El inicio y el final de nuestra subcadena binaria que
                // agregaremos.
                int inicio = (fila * imagen.getWidth() -1 + columna) * 8;
                int fin = inicio + 8;

                // La subcadena que estará en binario.
                String bloqueMensaje = "";

                //Bandera inicial K en binario para indicar el inicio
                if(fila == 0 && columna == 0){
                    bloqueMensaje = "01001011";
                }
                else if (fin < mensajeBinario.length()) {
                    //Obtenemos 8 bits del mensaje binario
                    bloqueMensaje = mensajeBinario.substring(inicio, fin);  
                }
                 else if(fin == mensajeBinario.length()) {
                    // Si ya terminamos agregamos la bandera que nos indica
                    // que ya terminamos de leer la cadena binaria.
                    bloqueMensaje = "11111111";
                    bloqueFinal = true;
                }
                // Obtenemos el color del pixel que estamos y lo
                // Guardamos en un objeto de color para manipularlo.
                int rgb = imagen.getRGB(columna, fila);
                Color colorOriginal = new Color(rgb, true);

                // Guardamos los colores en formato de 8 bits por cada canal.
                ArrayList<String> rgbaOriginal = 
                metodosImagen.getColoresBinarios(colorOriginal);
                ArrayList<String> rgbaActualizado = new ArrayList<>();

                // Hacemos la modificación de los colores en base a nuestra cadena binaria.
                for (int i = 0; i < rgbaOriginal.size(); i++) {
                    rgbaActualizado.add(rgbaOriginal.get(i).substring(0, 6) 
                    + bloqueMensaje.substring(i * 2, (i + 1) * 2));
                }

                // Convertimos nuestro arreglo a uno en decimal para actualizar la imágen.
                ArrayList<Integer> componentesNuevos = metodosImagen.rgbaAEntero(rgbaActualizado);

                // Asignamos el color a nuestra imagen.
                Color colorFinal = new Color(componentesNuevos.get(0), 
                componentesNuevos.get(1), componentesNuevos.get(2), 
                componentesNuevos.get(3));
                imagen.setRGB(columna, fila, colorFinal.getRGB());

                // Terminamos si ya se agrego el bloque final.
                if (bloqueFinal)
                    break outerloop;
            }
        }
        return imagen;
    }

    /**
    * Obtiene el mensaje de la imágen dada recolectando los últimos 2 bits de
    * cada componente de canal Rojo, Verde, Azul y Alfa de cada pixel empezando
    * en la posición (0,1) de la imágen, ya que el (0,0) está reservado para
    * nuestra bandera "K" para saber si hay texto oculto en la imágen.
    * El proceso termina cuando encontramos el bloque "11111111".
    * @param imagen La imágen a ser decodificada.
    * @return la cadena con la información obtenida de la imágen.
    * @throws Exception si no encuentra ninún texto en la imágen.
    */
    public String getTextoDeImagen(BufferedImage imagen) throws Exception {
        StringBuilder sb = new StringBuilder();
        String textoEscondido;

        // Buscamos nuestra bandera "K".
        int rgbEnCeroCero = imagen.getRGB(0,0);
        Color color = new Color(rgbEnCeroCero, true);
        ArrayList<String> rgba = metodosImagen.getColoresBinarios(color);
        String bloque = metodosImagen.getParesBinarios(rgba);

        if (!bloque.equals("01001011")){
            throw new Exception("No encontré ningún texto en la imágen." +
            "Por favor ingresa otra imagen.");
        }

        outerloop:
        for (int fila = 0; fila < imagen.getHeight(); fila++) {
            for (int columna = 1; columna < imagen.getWidth(); columna++) {

                int rgb = imagen.getRGB(columna, fila);
                color = new Color(rgb, true);
                rgba = metodosImagen.getColoresBinarios(color);
                bloque = metodosImagen.getParesBinarios(rgba);

                if (bloque.equals("11111111"))
                    break outerloop;
                sb.append(metodosTexto.binarioAString(bloque));
            }
        }

        textoEscondido = sb.toString();
        return textoEscondido;
    }
}
