package com.manga.job.application;

import com.manga.job.model.Manga;
import com.manga.job.process.MangaProcessor;
import com.manga.job.process.impl.MangaProcessorImpl;
import com.manga.job.util.ApplicationUtility;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public class JobRunner {

    public static void main(String[] args) {

        MangaProcessor mangaProcessor = new MangaProcessorImpl();
        String url = "https://tumangaonline.site/manga/soredemo-boku-wa-kimi-ga-suki";

        System.out.println("processing manga... ");
        Manga manga = mangaProcessor.readManga(ApplicationUtility.urlToDocument(url));
        System.out.println("Done...");


        File theDir = new File("C:\\manga");
        if (!theDir.exists()) {
            theDir.mkdirs();
        }

        System.out.println("Writing data to file...");
        ApplicationUtility.writeMangaToDisk(manga,theDir.getAbsolutePath());
        System.out.println("Done...");
    }
}
