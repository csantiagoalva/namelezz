package mx.ipn.escom.ars.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Marco {

	public static void transformar(File archivo) throws IOException {
		BufferedImage fondo = new BufferedImage(960, 760,BufferedImage.TYPE_3BYTE_BGR);

		Image ptr,ptr2;
		Image img2 = ImageIO.read(archivo);
		Random rand = new Random();
		Integer r;
		Double width = (double) img2.getWidth(null);
		Double height = (double) img2.getHeight(null);
		Double div;
		Graphics2D g = fondo.createGraphics();
		
		g.setColor(Color.white);
		g.fillRect(0, 0, fondo.getWidth(), fondo.getHeight());

		if (width > 840 || height > 640) {
			if (width > height && width - height > 150) {
				div = width / 840;
				width = width / div;
				height = height / div;

			} else {
				div = height / 640;
				width = width / div;
				height = height / div;
			}
		} else {
			if (width > height && width - height > 150) {
				div = 840 / width;
				width = width * div;
				height = height * div;

			} else {
				div = 640 / height;
				width = width * div;
				height = height * div;
			}
		}

		g.drawImage(img2, 480 - width.intValue() / 2,380 - height.intValue() / 2, width.intValue(),height.intValue(), null);

		for (int i = 40; i < 920; i += 40) {
			do{
			r = rand.nextInt(200 + 1);	
			}while(r==0);
			ptr = ImageIO.read(new File("Repo/patrones/img-" + r + ".png"));			
			g.drawImage(ptr, i, 40, 40, 40, null);
			do{
			r = rand.nextInt(200 + 1);
			}while(r==0);				
			ptr = ImageIO.read(new File("Repo/patrones/img-" + r + ".png"));
			g.drawImage(ptr, i, 680, 40, 40, null);
			
		}

		for (int i = 40; i < 680; i += 40) {
			do{
			r = rand.nextInt(200 + 1);
			}while(r==0);
			ptr = ImageIO.read(new File("Repo/patrones/img-" + r + ".png"));
			g.drawImage(ptr, 880, i, 40, 40, null);
			do{
			r = rand.nextInt(200 + 1);
			}while(r==0);
			ptr2 = ImageIO.read(new File("Repo/patrones/img-" + r + ".png"));
			g.drawImage(ptr2, 40, i, 40, 40, null);
			
		}

		g.dispose();
		
		
		String ext=archivo.getAbsolutePath().substring(archivo.getAbsolutePath().length()-4);
		String name=archivo.getAbsolutePath().replaceAll(ext, ".png");
		
		File file2 = new File(name);
		ImageIO.write(fondo, "png", file2);
		
		archivo.delete();
		
	}
}
