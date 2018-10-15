package fciencias.myp2019;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
* @author Jorge Francisco Cortes Lopez.
* No. de cuenta: 31433098-1.
*/

/**
* <p>Implementamos diversos métodos para usar en nuestro estenógrafo como:
* cargar una imágen, guardar una imágen, obtener los valores en 8 bits de
* cada pixel de la imágen y otros métodos auxiliares.</p>
*/
public class MetodosImagen {

    /**
    * Método para cargar una imágen en un objeto de tipo BufferedImage.
    * @param nombreImagen la imágen que será cargada del usuario.
    * @return El objeto BufferedImage con la información de la imágen.
    * @return null si no se logró cargar la imágen.
    */
    public static BufferedImage cargaImagen(String nombreImagen) {
        try {
            File file = new File(nombreImagen);
            BufferedImage in = ImageIO.read(file);
            BufferedImage imagenNueva = new BufferedImage(
                    in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics2D g = imagenNueva.createGraphics();
            g.drawImage(in, 0, 0, null);
            g.dispose();

            return imagenNueva;
        } catch (IOException e) {
            System.err.println("Error al leer la imágen.");
        }

        return null;
    }

    /**
    * Guarda un BufferedImage a un .png en la ubicación del programa.
    * @param imagen la imagen a ser guardada.
    * @param nombreImagen el nombre de la imagen a ser guardada.
    */
    public static void guardaImagen(BufferedImage imagen, String nombreImagen) {
        try {
            File output = new File(nombreImagen);
            ImageIO.write(imagen, "png", output);
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
    * Obtiene los últimos dos bits del canal rojo, verde, azul y alfa.
    * dado un ArrayList que contiene los valores de 8 bits de estos canales
    * como una cadena binaria. 
    * @param rgba Las cadenas binarias de un pixel (Rojo, verde, azul, alfa).
    * @return Una cadena concatenada de los últimos dos bits de cada canal.
    * @return null si la especificación del arreglo no es la que esperamos.
    */
    public String getParesBinarios(ArrayList<String> rgba) {

        if(rgba.size() > 4 || rgba.isEmpty())
            return null;

        for(String e : rgba){
            if(e.length() != 8)
                return null;
        }

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

    /**
    * Revisa si el texto que queremos insertar en una imágen mediante estegano-
    * grafía es demasiado grande para la imágen.
    * Sabemos que podemos insertar un carácter por cada pixel, entonces sólo
    * obtenemos el número de pixeles de la imágen y vemos si el número de
    * pixeles es mayor o igual que el número de carácteres a insertar.
    * @param textoAInsertar el texto que vamos a insertar.
    * @param imagen la imágen que usaremos para insertar el texto.
    * @return true si el texto es demasiado grande. false en otro caso.
    */
    public boolean textoCabeEnImagen(String textoAInsertar,
        BufferedImage imagen) {
        int alturaImagen = imagen.getHeight();
        int anchoImagen = imagen.getWidth();

        if((alturaImagen * anchoImagen) >= textoAInsertar.length())
            return true;
        return false;
    }
}
