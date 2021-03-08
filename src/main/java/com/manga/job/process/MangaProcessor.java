package com.manga.job.process;

import com.manga.job.model.Manga;
import org.jsoup.nodes.Document;

public interface MangaProcessor {

    public Manga readManga(Document document);
}
