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
import java.util.regex.Pattern;

/**
 *
 * @author Joshu
 */
public class ParseQuiz {

    public static void main() {

    }

    class Question {

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
    }

    /**
     *
     * @param filename
     * @return NodeList
     */
    private boolean readQuestions(String filename) {
        Pattern pattern = Pattern.compile("\\d");
        ArrayList<Question> questionList = new ArrayList<>();
        int questionCounter = 0;

        try {
            BufferedReader input = new BufferedReader(new FileReader(filename));

            // read in the first line extracting the chapter Number
            int chapterNo = Integer.parseInt(input.readLine().substring(8, 9));
            input.readLine();// Skip 
            input.readLine();// Skip 

            String line = input.readLine();// 4th line is the first real line of questions
            while (line != null) {
                questionCounter++;
                Question question = new Question();
                question.chapterNo = chapterNo;
                StringBuilder description = new StringBuilder();

                while (!line.equals("#")) {

                    if (line.substring(0, 1).matches("\\d")) {
                        description.append(questionCounter).append(". ").append(line).append("\n");
                        line = input.readLine();
                    } else if (line.startsWith("a.")) {
                        question.choiceA = line;
                        line = input.readLine();
                    } else if (line.startsWith("b.")) {
                        question.choiceB = line;
                        line = input.readLine();
                    } else if (line.startsWith("c.")) {
                        question.choiceC = line;
                        line = input.readLine();
                    } else if (line.startsWith("d.")) {
                        question.choiceD = line;
                        line = input.readLine();
                    } else if (line.startsWith("Key")) {
                        question.answerKey = line.substring(4);
                    } else {
                        description.append(line).append("\n");
                        line = input.readLine();
                    }

                }

            }

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return true;
    }

}
