# MangaScraperJob
Job to scrap manga from internet

***
Requeriments: 
*  Java Version 8
*  Maven


## How to build the project?

***
Run mvn clean package from a terminal or a IDE like eclipse or Intellij
```
$ mvn clean package
```

## How to run the code?

Execute the main class(JobRunner class)

```java

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


```

