/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Joshua
 */
public class ParseQuiz {

    final int CHAPTERS = 44;
    private int chapter;

    public static void main(String[] args) {
        ParseQuiz quizlist = new ParseQuiz();

    }

    public ParseQuiz() {
        printQuestionList();
    }

    public void printQuestionList() {
        ArrayList read;
        for (int i = 1; i <= CHAPTERS; i++) {
            this.chapter = i;
            read = readQuestions("C:\\selftest\\selftest11e\\chapter" + i + ".txt");
            //read.forEach(e -> System.out.println(e.toString()));
            //read.forEach(e -> import(e));
        }
    }

    static class Question {

        int chapterNo;
        int questionNo;
        String questionText;
        String choiceA;
        String choiceB;
        String choiceC;
        String choiceD;
        String choiceE;
        String answerKey;
        String hint;

        @Override
        public String toString() {
            return chapterNo + "\n"
                    + questionNo + "\n"
                    + questionText + "\n"
                    + choiceA + "\n"
                    + choiceB + "\n"
                    + choiceC + "\n"
                    + choiceD + "\n"
                    + choiceE + "\n"
                    + answerKey + "\n"
                    + hint + "\n";
        }

    }

    /**
     *
     * @param filename
     * @return NodeList
     */
    private ArrayList<Question> readQuestions(String filename) {
        String matchThis = "\\d{1,2}";
        Pattern pattern = Pattern.compile("\\d{1,2}\\.\\s+(.*)");
        Pattern answers = Pattern.compile("^[Kk]ey:([a-e]{1,5})\\s*(.*)$");//^[kK]ey:([a-e]+)\s*(.*)$
        Pattern choice = Pattern.compile("^[a-e]\\.\\s*(.*)$");
        Matcher matcher;
        Matcher choiceMatcher;
        ArrayList<Question> questionList = new ArrayList<>();
        int questionCounter = 0;

        try {
            BufferedReader input = new BufferedReader(new FileReader(filename));
            String line;
            input.readLine();// Skip 
            input.readLine();// Skip 
            input.readLine();// 4th line is the first real line of questions

            while ((line = input.readLine()) != null) {

                Question question = new Question();
                question.questionNo = ++questionCounter;
                question.chapterNo = chapter;
                StringBuilder description = new StringBuilder();
                if (line.startsWith("Section")) {
                    line = input.readLine(); //Discard section Lines for now.
                }
                while (!line.equals("#")) {
                    matcher = pattern.matcher(line);
                    choiceMatcher = choice.matcher(line);

                    if (matcher.matches()) {
                        description.append(matcher.group(1));
                        line = input.readLine();
                    } else if (line.startsWith("a.") || line.startsWith("A.")) {
                        choiceMatcher.matches();
                        question.choiceA = choiceMatcher.group(1);
                        line = input.readLine();
                    } else if (line.startsWith("b.") || line.startsWith("B.")) {
                        choiceMatcher.matches();
                        question.choiceB = choiceMatcher.group(1);
                        line = input.readLine();
                    } else if (line.startsWith("c.") || line.startsWith("C.")) {
                        choiceMatcher.matches();
                        question.choiceC = choiceMatcher.group(1);
                        line = input.readLine();
                    } else if (line.startsWith("d.") || line.startsWith("D.")) {
                        choiceMatcher.matches();
                        question.choiceD = choiceMatcher.group(1);
                        line = input.readLine();
                    } else if (line.startsWith("e.") || line.startsWith("E.")) {
                        choiceMatcher.matches();
                        question.choiceE = choiceMatcher.group(1);
                        line = input.readLine();
                    } else if (line.startsWith("Key") || line.startsWith("key")) {
                        matcher = answers.matcher(line);
                        if (matcher.matches()) {
                            question.answerKey = matcher.group(1);
                            question.hint = matcher.group(2);
                        }
                        if ((line = input.readLine()) == null) { // final description and question here
                            question.questionText = description.toString();
                            questionList.add(question);
                            return questionList;
                        }
                    } else {
                        description.append(line).append("\n");
                        line = input.readLine();
                    }
                }
                question.questionText = description.toString();
                questionList.add(question);
            }
            input.close();
        } catch (IllegalStateException | IOException ex) {
            System.out.println(ex.getMessage());
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
            // return questionList;
        }
        return questionList;
    }
}
