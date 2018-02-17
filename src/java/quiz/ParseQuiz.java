/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiz;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Joshu
 */
public class ParseQuiz {
    //ArrayList<Question> questionList;

    public static void main(String[] args) {
        ParseQuiz quizlist = new ParseQuiz();

    }

    public ParseQuiz() {
        printQuestionList();
    }

    public void printQuestionList() {
        ArrayList read;
        read = readQuestions("C:\\selftest\\selftest11e\\chapter1.txt");
        read.forEach(e -> System.out.println(e.toString()));

    }

    static class Question {

        int chapterNo = 1;
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
            return  chapterNo + "\n"
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
        Pattern pattern = Pattern.compile("(\\d{1,2}\\..*)");
        Pattern answers = Pattern.compile("^([a-e]{1,5})");
        Matcher matcher;
        ArrayList<Question> questionList = new ArrayList<>();
        int questionCounter = 0;

        try {
            BufferedReader input = new BufferedReader(new FileReader(filename));

            // read in the first line extracting the chapter Number
            int chapterNo;// = Integer.parseInt(input.readLine().substring(8, 9));
            input.readLine();// Skip 
            input.readLine();// Skip 

            String line = input.readLine();// 4th line is the first real line of questions
            while ((line = input.readLine()) != null) {
                
                Question question = new Question();
                question.questionNo = ++questionCounter;
                //question.chapterNo = 1;
                StringBuilder description = new StringBuilder();

                while (!line.equals("#")) {
                    matcher = pattern.matcher(line);
                    //System.out.println(matcher.matches());
                    if (matcher.matches()) {
                        if (line.startsWith("Section"))input.readLine();
                        //System.out.println(matcher.matches());
                        description.append(line).append("\n");
                        line = input.readLine();
                    
                        
                    } else if (line.startsWith("a.")) {
                        question.choiceA = line.substring(3);
                        line = input.readLine();
                    } else if (line.startsWith("b.")) {
                        question.choiceB = line.substring(3);
                        line = input.readLine();
                    } else if (line.startsWith("c.")) {
                        question.choiceC = line.substring(3);
                        line = input.readLine();
                    } else if (line.startsWith("d.")) {
                        question.choiceD = line.substring(3);
                        line = input.readLine();
                    } else if (line.startsWith("e.")){
                        question.choiceE = line.substring(3);
                        line = input.readLine();
                    } else if (line.startsWith("Key")) {
                        matcher = answers.matcher("a 8");
                        int end = matcher.end();
                        System.out.println("end of answers here" + end);
                        question.answerKey = line.substring(4);
                        line = input.readLine();
                    } else {
                        description.append(line).append("\n");
                        //if (!input.readLine().isEmpty()){
                        line = input.readLine();
                        // }

                    }

                }
                question.questionText = description.toString();
                questionList.add(question);

            }

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
            return questionList;
        }
        return questionList;
    }

}
