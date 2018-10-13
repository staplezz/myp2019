package fciencias.myp2019;

import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.ArrayList;

public class Proyecto02{
	public static void main(String[] args) {
		String quijote = "";
		BufferedImage img = null;
		MetodosTxt ltxt = new MetodosTxt();
		MetodosImagen imgMethods = new MetodosImagen();
		Esteganografo est = new Esteganografo();
		quijote = ltxt.leeTxt("quijote.txt");
		img = imgMethods.cargaImagen("audio.jpg");
		est.escondeMensaje(quijote, img, "decoded");
		est.getTextoDeImagen(img, "textoSecreto");
	}
}