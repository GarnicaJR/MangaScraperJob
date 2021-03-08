package com.manga.job.process.impl;

import com.manga.job.model.Chapter;
import com.manga.job.model.Manga;
import com.manga.job.process.MangaProcessor;
import com.manga.job.util.ApplicationUtility;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class MangaProcessorImpl implements MangaProcessor {

    private final static String MANGA_TITLE = "i.fa.fa-leaf";
    private final static String MANGA_DESCRIPTION = "span.list-group-item";
    private final static String MANGA_STATUS = "span.label.label-success";
    private final static String MANGA_PICTURE = "img.img-responsive";
    private final static String MANGA_CHAPTERS = "table.table.table-hover a";
    private final static String MANGA_NUMBER_CHAPTER_PAGES = "select.selectpicker option";
    private final static String MANGA_IMG_FROM_PAGE = "img.img-responsive.scan-page";


    @Override
    public Manga readManga(Document document) {

        Manga manga = new Manga();
        manga.setName(getMangaName(document));
        manga.setDescription(getMangaDescription(document));
        manga.setOngoing(getMangaStatus(document));
        manga.setPicture(getMangaPicture(document));
        manga.setChapters(getMangaChapters(document));
        return manga;
    }

    private Image getMangaPicture(Document document) {
        Element picture = document.select(MANGA_PICTURE).first();
        String pictureURL = picture.attr("src");
        Image image = ApplicationUtility.getImageFromInternet(pictureURL);
        return image;
    }

    private boolean getMangaStatus(Document document) {
        Element mangaStatus = document.select(MANGA_STATUS).first();
        return mangaStatus.text().contains("Ongoing");
    }

    private String getMangaDescription(Document document) {
        Element mangaDescription = document.select(MANGA_DESCRIPTION).last();
        return mangaDescription.text().replace("Resumen Resumen:", "").trim();
    }

    private String getMangaName(Document document) {
        Element mangaName = document.select(MANGA_TITLE).first().parent();
        return mangaName.text().replace("(Manga)", "").trim();
    }


    private List<Chapter> getMangaChapters(Document document) {
        Elements elements = document.select(MANGA_CHAPTERS);
        List<Chapter> chapters = new LinkedList<>();

        for (int i = 0; i < elements.size(); i++) {
            try {
                Chapter chapter = new Chapter();
                chapter.setName(elements.get(i).text());
                String chapterURL = elements.get(i).attr("abs:href");
                chapter.setPictures(getPicturesFromChapter(chapterURL));
                chapters.add(chapter);

                System.out.println("Chapter number " + (i + 1) + " has been processed");
            } catch (Exception ex) {
                System.out.println("error when the job was process the manga number " + (i + 1));
                continue;
            }
        }
        return chapters;
    }

    private List<Image> getPicturesFromChapter(String chapterURL) {
        List<Image> images = new LinkedList<>();
        Document chapterDocument = ApplicationUtility.urlToDocument(chapterURL);
        int numberOfPages = getNumberOfPages(chapterDocument);

        for (int i = 1; i < numberOfPages; i++) {
            String currentPage = chapterURL + "/" + i;
            Image image = getImageFromPage(currentPage);
            if (image != null) {
                images.add(image);
            }
        }
        return images;
    }

    private Image getImageFromPage(String currentPage) {
        Document imageDocument = ApplicationUtility.urlToDocument(currentPage);
        Element imageElement = imageDocument.select(MANGA_IMG_FROM_PAGE).first();

        if (imageElement != null) {
            String imageURL = imageElement.attr("src");
            return ApplicationUtility.getImageFromInternet(imageURL);
        } else {
            return null;
        }
    }

    private int getNumberOfPages(Document document) {
        Element lastOption = document.select(MANGA_NUMBER_CHAPTER_PAGES).last();
        return Integer.parseInt(lastOption.text().trim());
    }
}
