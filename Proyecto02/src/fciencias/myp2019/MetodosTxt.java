package fciencias.myp2019;

import java.io.*;
import java.util.Arrays;
import java.io.IOException;
import java.nio.*;
import java.nio.charset.Charset;
import java.util.stream.Stream;

/**
* <p>Clase para representar métodos que utilizaremos en nuestro programa.</p>
*
* <p>Implementamos diversos métodos para usar en nuestro estenógrafo como:
* leer un archivo txt que nos dará el usuario, escribir un archivo txt para
* cuando volvamos a obtener la información, convertir una cadena a binario,
* y otros métodos que nos serán de utilidad.</p>
*/

public class MetodosTxt{

    /**
    * Método para leer y devolver la información que contiene un archivo txt.
    * @param archivoTxt la ubicación del archivo en formato txt.
    * @return La cadena con la información del archivo txt.
    //cambia
    */
    public String leeTxt(String archivoTxt) {
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
        } 
        catch (UnsupportedEncodingException e) 
        {
            System.out.println(e.getMessage());
        } 
        catch (IOException e) 
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return cadenaTxt;
    }

    /**
    * Método para escribir un archivo en formato txt.
    * @param archivoAEscribir La cadena con la información a escribir.
    * @param nombreDelArchivo El nombre que se le dará al archivo.
    */
	void escribeTxt(String archivoAEscribir, String nombreDelArchivo){
        try {
            File fileDir = new File(nombreDelArchivo);
            Writer out = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(fileDir), "UTF8"));
            out.append(archivoAEscribir);
            System.out.println("Archivo guardado exitosamente en: " + fileDir.getAbsolutePath());
            out.flush();
            out.close();

        } catch (UnsupportedEncodingException e) 
       {
        System.out.println(e.getMessage());
       } 
       catch (IOException e) 
       {
        System.out.println(e.getMessage());
        }
       catch (Exception e)
       {
        System.out.println(e.getMessage());
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
    * @param cadena La cadena a ser convertida.
    * @return La cadena convertida a texto.
    */
	String bin2String(String cadena){
		StringBuilder sb = new StringBuilder();
		Arrays.stream(cadena.split("(?<=\\G.{8})")).forEach(s -> sb.append((char) Integer.parseInt(s, 2)));
		String output = sb.toString();
		return output;
	}
}
