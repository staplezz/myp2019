package fciencias.myp2019;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.Color;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.ArrayList;

/**
* <p>Clase para representar métodos que utilizaremos en nuestro programa.</p>
*
* <p>Implementamos diversos métodos para usar en nuestro estenógrafo como:
* cargar una imágen, guardar una imágen, obtener los valores en 8 bits de
* cada pixel de la imágen y otros métodos auxiliares.</p>
*/
public class MetodosImagen{

    /**
    * Método para cargar una imágen en un objeto de tipo <tt>BufferedImage<tt>.
    * @param imagen la imágen que será cargada del usuario.
    * @return El objeto <tt>BufferedImage<tt> con la información de la imagen.
    * @throws IOException si ocurre un error al leer el arhivo.
    */
    public static BufferedImage cargaImagen(String imagen) {
	    try {
	        File file = new File(imagen);
	        BufferedImage in = ImageIO.read(file);
	        BufferedImage newImage = new BufferedImage(
	                in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);

	        Graphics2D g = newImage.createGraphics();
	        g.drawImage(in, 0, 0, null);
	        g.dispose();

	        return newImage;
	    } catch (IOException e) {
	        System.err.println("Error al leer la imágen.");
	        e.printStackTrace();
	    }

	    return null;
	}

    /**
    * Guarda un BufferedImage a un .png en la ubicación del programa.
	* @param imagen la imagen a ser guardada.
	* @param nombreImagen el nombre de la imagen a ser guardada.
    * @throws IOException si ocurre un error al leer el arhivo.
 	*/
    public void guardaImagen(BufferedImage imagen, String nombreImagen) {
        try {
            File output = new File(nombreImagen);
            ImageIO.write(imagen, "png", output);
            System.out.println("Imagen guardada en: " + output.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("No pude guardar el archivo png.");
            e.printStackTrace();
        }
	}

    /**
    * Convierte el valor entero de los componentes de color a valor binario como una cadena.
    * @param color El color del componente de color que se desea obtener.
    * @return un ArrayList de cadenas que contienen el componente de color como valores binarios.
 	*/
    public ArrayList<String> getColoresBinarios(Color color) {
        ArrayList<String> coloresBinarios = new ArrayList<>();
        coloresBinarios.add(decABinario(color.getRed()));
        coloresBinarios.add(decABinario(color.getGreen()));
        coloresBinarios.add(decABinario(color.getBlue()));
        coloresBinarios.add(decABinario(color.getAlpha()));
        
        return coloresBinarios;
    }

    /**
 	* Convierte un entero a una cadena en binario.
 	* @param decimal El número entero decimal a ser convertido.
	* @return Una cadena que contiene el número en binario.
 	*/
    public String decABinario(int decimal) {
        return Integer.toBinaryString(decimal);
    }

	/**
    * Obtiene los últimos dos bits del canl rojo, verde, azul y alfa.
    * dado un <tt>ArrayList<tt> que contiene los valores de 8 bits de estos canales
    * como una cadena binaria. 
    * @param rgba Las cadenas binarias de un pixel (Rojo, verde, azul, alfa).
    * @return Una cadena concatenada de los últimos dos bits de cada canal.
    */
    public String getParesBinarios(ArrayList<String> rgba) {
        String paresBinarios = "";

        for (String e : rgba)
            paresBinarios += e.substring(6);

        return paresBinarios;
    }

    /**
    * Convierte un ArrayList de cadenas binarias a un ArrayList con valores enteros.
    * @param arregloBinario Un ArrayList de cadenas binarias de tipo String.
    * @return Un ArrayList de valores enteros que representan el color en bits.
    */
    public ArrayList<Integer> rgbaAEntero(ArrayList<String> arregloBinario) {
        ArrayList<Integer> arregloEntero = new ArrayList<>();

        for (String e : arregloBinario)
            arregloEntero.add(Integer.parseInt(e, 2));

        return arregloEntero;
    }

}