 package fciencias.myp2019;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.ArrayList;

/**
* <p>Clase para representar los algorítmos de esteganográfía.</p>
*
* <p>Se implementan los métodos para esconder el mensaje dentro de un archivo
* de imágen usando una cadena y para obtener el mensaje escondido en alguna
* imágen guardandolo después en un archivo de texto.</p>
*/
public class Esteganografo{
	//La clase para usar los métodos de imágen.
	MetodosImagen imgMet = new MetodosImagen();
	//La clase para usar los métodos de texto.
	MetodosTxt txtMet = new MetodosTxt();

	/**
	* Esconde el mensaje de tipo String en una imágen ya cargada de
	* tipo BufferedImage y luego de ejecutar el algorítmo actualiza
	* los valores de la imágen.
	* @param mensaje El mensaje a ser escondido en la imágen.
	* @param imagen La imágen a ser utilizada para esconder nuestro texto.
	* @param nombreImg El nombre de la imágen nueva.
	*/
	public void escondeMensaje(String mensaje, BufferedImage imagen, String nombreImg){
		//Convertimos el mensaje a binario.
		String mensajeBinario = txtMet.convierteCadenaABinario(mensaje);

		//Indica si ya terminamos de esconder el mensaje.
		Boolean bloqueFinal = false;

		//Iteramos la imagen.
		outerloop:
		for(int fila = 0; fila < imagen.getHeight(); fila++) {
			for(int columna = 0; columna < imagen.getWidth(); columna++) {

				// El inicio y el final de nuestra subcadena binaria que
				// agregaremos.
				int inicio = fila * imagen.getWidth() + columna * 8;
				int fin = inicio + 8;

				// La subcadena que estará en binario.
				String bloqueMensaje;
				if (fin < mensajeBinario.length()){
					//Obtenemos 8 bits del mensaje binario
					bloqueMensaje = mensajeBinario.substring(inicio, fin);	
				} else {
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
				ArrayList<String> rgbaOriginal = imgMet.getColoresBinarios(colorOriginal);
				ArrayList<String> rgbaActualizado = new ArrayList<>();

				// Hacemos la modificación de los colores en base a nuestra cadena binaria.
				for (int i = 0; i < rgbaOriginal.size(); i++){
					rgbaActualizado.add(rgbaOriginal.get(i).substring(0, 6) + bloqueMensaje.substring(i * 2, (i + 1) * 2));
				}

				// Convertimos nuestro arreglo a uno en decimal para actualizar la imágen.
				ArrayList<Integer> componentesNuevos = imgMet.rgbaAEntero(rgbaActualizado);

				// Asignamos el color a nuestra imagen.
				Color colorFinal = new Color(componentesNuevos.get(0), componentesNuevos.get(1),
				 				   componentesNuevos.get(2), componentesNuevos.get(3));
				imagen.setRGB(columna, fila, colorFinal.getRGB());

				// Terminamos si ya se agrego el bloque final.
				if (bloqueFinal)
					break outerloop;
			}
		}
		// Guardamos la imagen con el nombre que nos da el usuario.
		imgMet.guardaImagen(imagen, nombreImg);
	}

	/**
	* Obtiene el mensaje de la imágen dada recolectando los últimos 2 bits de
	* cada componente de canal Rojo, Verde, Azul y Alfa de cada pixel empezando
	* en la posición (0,0) de la imágen. El proceso termina cuando encontramos el bloque
	* "11111111". Luego el mensaje se guarda en un archivo de texto.
	* @param imagen La imágen a ser decodificada.
	* @param nombreTxt El nombre del archivo con la información.
	*/
    public void getTextoDeImagen(BufferedImage imagen, String nombreTxt) {
        String textoEscondido = "";

        outerloop:
        for (int fila = 0; fila < imagen.getHeight(); fila++) {
            for (int columna = 0; columna < imagen.getWidth(); columna++) {
                int rgb = imagen.getRGB(columna, fila);
                Color color = new Color(rgb, true);

                ArrayList<String> rgba = imgMet.getColoresBinarios(color);
                
                String bloque = imgMet.getParesBinarios(rgba);
                if (bloque.equals("11111111")) {
                    break outerloop;
                } else {
					textoEscondido += txtMet.bin2String(bloque);
                }
            }
        }
        System.out.println("Obtuve el texto escondido");
        System.out.println(textoEscondido);
        txtMet.escribeTxt(textoEscondido, nombreTxt);
	}

} 
