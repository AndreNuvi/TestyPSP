package pl.strazpozarna.inspekcja.testypsp;

/**
 * Created by Andre on 2017-02-19.
 */

public class Question {
    int indexGlobal;
    String question;
    String answerA;
    String answerB;
    String answerC;
    String answerD;
    int correctAnswer;
    int notify;
    int indexLocal;
    int star;


    public int getIndexGlobal() {
        return indexGlobal;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public int getNotify() {
        return notify;
    }

    public int getIndexLocal() {
        return indexLocal;
    }

    public int getStar() {return star;}

    public Question(int indexGlobal, String question, String answerA, String answerB, String answerC, String answerD, int correctAnswer, int notify, int indexLocal, int star) {
        this.indexGlobal = indexGlobal;
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.correctAnswer = correctAnswer;
        this.notify = notify;
        this.indexLocal = indexLocal;
        this.star = star;
    }

    @Override
    public String toString() {


        return ""+indexGlobal+question;
    }
}
