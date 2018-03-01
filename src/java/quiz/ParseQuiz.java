/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Joshua
 */
public class ParseQuiz {

    private PreparedStatement insertStatement;
    final int CHAPTERS = 44;
    private int chapter;
    // String line;

    public static void main(String[] args) {
        ParseQuiz quizlist = new ParseQuiz();

    }

    public ParseQuiz() {
        initDB();
        readFiles();
    }

    private void initDB() {

        try {
            //Load the JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded");

            //Establish a connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://35.185.94.191/lambert", "lambert", "tiger");
            System.out.println("Database connected");

            //String selectString = "SELECT  lastName, mi, firstName, address, city, "
            //        + " state, telephone, email, id FROM Staff WHERE Staff.id = ?";
            String insertString = "INSERT INTO intro11equiz (chapterNo, questionNO, question, choiceA,"
                    + " choiceB, choiceC, choiceD, choiceE, answerKey, hint)"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?)";

//            String updateString = "UPDATE Staff SET "
//                    + "id=?, lastName=?, mi=?, firstName=?, address=?, city=?, state=?,"
//                    + "telephone=?, email=? WHERE id=?";
            //  selectStatement = connection.prepareStatement(selectString);
            insertStatement = connection.prepareStatement(insertString);
            // updateStatement = connection.prepareStatement(updateString);

        } catch (ClassNotFoundException | SQLException ex) {
            //setError(ex.getMessage());
        }
    }

    private void importToDB(Question question) {
        try {

            //if (idNumber.contains(" ") || idNumber.equals("")) {
            //  throw new SQLException("ID must not be left blank or contain spaces!");
            // }
            insertStatement.setInt(1, question.chapterNo);
            insertStatement.setInt(2, question.questionNo);
            insertStatement.setString(3, question.questionText);
            insertStatement.setString(4, question.choiceA);
            insertStatement.setString(5, question.choiceB);
            insertStatement.setString(6, question.choiceC);
            insertStatement.setString(7, question.choiceD);
            insertStatement.setString(8, question.choiceE);
            insertStatement.setString(9, question.answerKey);
            insertStatement.setString(10, question.hint);
            insertStatement.execute();

            //setError("Record ID: " + idNumber + " insert success!");
        } catch (SQLException ex) {

            //setError(ex.getMessage());
        }

    }

    public void readFiles() {
        ArrayList read = null;
        int counter = 0;
        for (int i = 1; i <= CHAPTERS; i++) {
           this.chapter = i;
            read = readQuestions("C:\\selftest\\selftest11e\\chapter" + i + ".txt");
            //read.forEach(e -> System.out.println(e.toString()));
            //read.forEach(e -> import(e));
            read.forEach(e -> importToDB((Question) e));
            System.out.printf("%d\n", counter += read.size());

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
        Pattern pattern = Pattern.compile("\\d{1,2}\\.\\s*(.*)$");
        Pattern answers = Pattern.compile("^[Kk]ey:([a-e]{1,5})\\s*(.*)$");//^[kK]ey:([a-e]+)\s*(.*)$
        Pattern choice = Pattern.compile("^[a-eA-E]\\.\\s*(.*)$");
        Pattern choiceA = Pattern.compile("^\\s*[Aa]\\.");
        Pattern newQuestion = Pattern.compile("#{1}\\s");
        Matcher matcher;
        Matcher questionMatcher;
        Matcher choiceAMatcher;
        Matcher choiceMatcher;
        String line;
        ArrayList<Question> questionList = new ArrayList<>();
        int questionCounter = 0;

        try {
            BufferedReader input = new BufferedReader(new FileReader(filename));
            
            //input.readLine();// Skip 
            //input.readLine();// Skip 
            input.readLine();// 4th line is the first real line of questions

            while ((line = input.readLine()) != null) {

                Question question = new Question();
                question.questionNo = ++questionCounter;
                question.chapterNo = chapter;
                StringBuilder description = new StringBuilder();
                if (line.startsWith("Section")) {
                    line = input.readLine(); //Discard section Lines for now.
                }
                while (!line.startsWith("#")) {
                    questionMatcher = newQuestion.matcher(line);
                    matcher = pattern.matcher(line);
                    choiceMatcher = choice.matcher(line);
                    choiceAMatcher = choice.matcher(line);
                    
                    if (matcher.matches()) {
                        description.append(matcher.group(1));
                        line = input.readLine();
                    } else if (line.startsWith("Section")) {
                        line = input.readLine(); //Discard section lines.
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
                            line = input.readLine();
                        }
                        // End of the file gets hit here after the last key: hint
                        // final description and question here
                        if ((line = input.readLine()) == null) {
                            question.questionText = description.toString();
                            questionList.add(question);
                            line = null;
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

        //This return shouldnt actually ever get touched.
        return questionList;
    }
}
