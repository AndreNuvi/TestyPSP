package pl.strazpozarna.inspekcja.testypsp;

/**
 * Created by Andre on 2017-02-26.
 */

public class ListItem {
    String chapterIndex;
    int startChapter;
    int endChapter;
    String titleChapter;
    int questionQuantity;
    int doneQuantity;
    int rememberedQuantity;

    public String getChapterIndex() {
        return chapterIndex;
    }

    public int getStartChapter() {
        return startChapter;
    }

    public int getEndChapter() {
        return endChapter;
    }

    public String getTitleChapter() {
        return titleChapter;
    }

    public int getQuestionQuantity() {
        return questionQuantity;
    }

    public int getDoneQuantity() {
        return doneQuantity;
    }

    public int getRememberedQuantity() {
        return rememberedQuantity;
    }

    public ListItem(String chapterIndex, int startChapter, int endChapter, String titleChapter, int questionQuantity, int doneQuantity, int rememberedQuantity) {
        this.chapterIndex = chapterIndex;
        this.startChapter = startChapter;
        this.endChapter = endChapter;
        this.titleChapter = titleChapter;
        this.questionQuantity = questionQuantity;
        this.doneQuantity = doneQuantity;
        this.rememberedQuantity = rememberedQuantity;
    }
}

