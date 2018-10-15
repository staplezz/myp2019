package fciencias.myp2019;

import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;

/**
* @author Jorge Francisco Cortes Lopez.
* No. de cuenta: 31433098-1.
*/

/**
* <p>Implementamos diversos métodos para usar en nuestro estenógrafo como:
* leer un archivo txt que nos dará el usuario, escribir un archivo txt para
* cuando volvamos a obtener la información, convertir una cadena a binario,
* y otros métodos que nos serán de utilidad.</p>
*/

public class MetodosTexto {

    /**
    * Método para leer y devolver la información que contiene un archivo txt.
    * @param archivoTxt la ubicación del archivo en formato txt.
    * @return La cadena con la información del archivo txt.
    */
    public String cargaTxt(String archivoTxt) {
        String cadenaTxt = "";
        try {
            File txt = new File(archivoTxt);
            
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    new FileInputStream(txt), "UTF8"));
                
            String str;
             
            while ((str = in.readLine()) != null) {
                cadenaTxt += str + " ";
            }
            in.close();
            return cadenaTxt;        
        } catch (Exception e) {
            System.out.println("Ocurrió un error al cargar el archivo de texto.");
        }
        return cadenaTxt;
    }

    /**
    * Método para escribir un archivo en formato txt.
    * @param archivoAEscribir La cadena con la información a escribir.
    * @param nombreDelArchivo El nombre que se le dará al archivo.
    */
    public static void guardaTxt(String archivoAEscribir, 
        String nombreDelArchivo) {
        try {
            File fileDir = new File(nombreDelArchivo);
            Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileDir), "UTF8"));
            out.append(archivoAEscribir);
            out.flush();
            out.close();

        } catch (Exception e) {
            System.out.println("Ocurrió un error al guardar el archivo de texto");
        } 
    }

    /**
    * Convierte una cadena de caracteres a una cadena con valores binarios.
    * @param input La cadena a ser convertida.
    * @return La cadena que contiene los valores binarios de la cadena dada.
    */
    public String convierteCadenaABinario(String input) {
        String cadena8bits = "";
        byte[] bytes = input.getBytes();
        StringBuilder binario = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binario.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        cadena8bits += binario.toString();
        return cadena8bits;
    }

    /**
    * Convierte una cadena binaria a una en texto.
    * Usamos el método de clase split que recibe un regex
    * en donde (?<=\\G.{8}) en donde con "?<=" recorre la cadena
    * y si "G.{8}" sucede, es decir, recorre 8 caracteres, entonces
    * divide la cadena en 8 caracteres y a cada uno le aplica el
    * método "parseInt" que convierte el binario en caracter y luego
    * lo junta caracter con caracter en nuestro objeto StringBuilder,
    * finalmente lo convertimos a String para poder ser regresado.
    * @param cadena La cadena a ser convertida.
    * @return La cadena convertida a texto.
    */
    String binarioAString(String cadena) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(cadena.split("(?<=\\G.{8})")).forEach
        (s -> sb.append((char) Integer.parseInt(s, 2)));
        String output = sb.toString();
        return output;
    }
    
}
