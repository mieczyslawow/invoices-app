package pl.zubardzka.facturingapp;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import net.sourceforge.tess4j.*;

import javax.imageio.ImageIO;

public class TesseractExample {

    public static void main(String[] args) {
        File folder = new File("E:\\projekty\\invoices-app\\invoices" );
        StringBuilder names = new StringBuilder();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isFile()) {
                System.out.println(fileEntry.getName());
                names.append(readFile(fileEntry));
            }
        }
        System.out.println("Koniec programu \n odczytano :" + names.toString());
    }

    public static String readFile(File imageFile ){
        Image image = null;
        try {
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Rectangle rect = new Rectangle();
        BufferedImage bi = toBufferedImage(image);
        ITesseract instance = new Tesseract();
        instance.setDatapath("E:\\Java\\Tessdata");
        instance.setLanguage("pol");
        for(Word word : instance.getWords(bi, ITessAPI.TessPageIteratorLevel.RIL_TEXTLINE)) {
            rect = word.getBoundingBox();
            if(word.getText().toLowerCase().contains("razem")) {
                System.out.println(rect.x+","+rect.y+","+rect.getWidth()+","+rect.getHeight()
                        +": "+word.getText());
                break;
            }
        }

        return "";
    }

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bimage;
    }
}