package com.manga.job.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.manga.job.model.Chapter;
import com.manga.job.model.Manga;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public final class ApplicationUtility {

    public static Image getImageFromInternet(String URL) {
        Image image = null;

        try {
            java.net.URL url = new URL(URL);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "");
            image = ImageIO.read(connection.getInputStream());

        } catch (MalformedURLException e) {
            System.out.println("The URL is not correct " + URL);
        } catch (IOException e) {
            System.out.println("The image doesnt exist " + URL);
        }
        return image;
    }


    public static Document urlToDocument(String URL) {
        Document document = null;
        try {
            document = Jsoup.connect(URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    public static void writeMangaToDisk(Manga manga, String path) {
        try (Writer writer = new FileWriter(path + "\\manga.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(manga, writer);

            File outputfile = new File(path + "\\portada.jpg");
            ImageIO.write(toBufferedImage(manga.getPicture()), "jpg", outputfile);

            int index = 1;
            for (Chapter chapter : manga.getChapters()) {
                File theDir = new File(path + "\\chapter" + index);
                if (!theDir.exists()) {
                    theDir.mkdirs();
                }
                int subindex = 1;
                for (Image image : chapter.getPictures()) {
                    File newFile = new File(theDir.getAbsolutePath() + "\\" + chapter.getName() +" "+ subindex + ".jpg");
                    ImageIO.write(toBufferedImage(image), "jpg", newFile);
                    subindex++;
                }

                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
}
