/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 *
 * @author Joshua Lambert
 */
public class OneQuestion implements java.io.Serializable {

    String chapterNo = "";
    String questionNo = "";
    String question = "";
    String choiceA = "";
    String choiceB = "";
    String choiceC = "";
    String choiceD = "";
    String choiceE;
    String answerKey;
    String hint;
    String[] answerList = {"", "", "", "", ""};
    String buttonName;
    String[] submittedAnswers = {"x"};

    Boolean answerA = false;
    Boolean answerB = false;
    Boolean answerC = false;
    Boolean answerD = false;
    Boolean answerE = false;
    String hostName;
    Boolean isCorrect;
    String username = "Anonymous";

    private PreparedStatement selectStatement;
    private PreparedStatement insertStatement;
    private PreparedStatement updateStatement;
    private ResultSet rset;

    public OneQuestion() {
        initializeDB();

    }

    public void viewQuestion() {
        try {
            //if (id.contains(" ") || id.equals("")) {
            //setError("ID must not be blank or contain spaces.");

            // }
            System.out.println(chapterNo);
            System.out.println(questionNo);
            selectStatement.setString(1, chapterNo);
            selectStatement.setString(2, questionNo);
            rset = selectStatement.executeQuery();

            if (rset.next()) {
                question = rset.getString(1);
                choiceA = rset.getString(2);
                choiceB = rset.getString(3);
                choiceC = rset.getString(4);
                choiceD = rset.getString(5);
                choiceE = rset.getString(6);
                answerKey = rset.getString(7);
                hint = rset.getString(8);

            }

        } catch (SQLException ex) {
            // setError(ex.getMessage());
            System.out.println(ex.getMessage());
        }

    }

    private void insertRecord() {

        try {

            insertStatement.setString(1, chapterNo);
            insertStatement.setString(2, questionNo);
            insertStatement.setBoolean(3, isCorrect);
            //insertStatement.setString(4, time);
            insertStatement.setString(4, hostName);
            insertStatement.setBoolean(5, answerA);
            insertStatement.setBoolean(6, answerB);
            insertStatement.setBoolean(7, answerC);
            insertStatement.setBoolean(8, answerD);
            insertStatement.setBoolean(9, answerE);
            insertStatement.setString(10, username);
            insertStatement.execute();

        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }

    }

    private void initializeDB() {

        try {
            //Load the JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded");

            //Establish a connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/javabook", "lambert", "tiger");
            System.out.println("Database connected");

            String selectString = "SELECT  question, choiceA, choiceB, choiceC, choiceD, choiceE, answerKey, hint "
                    + "  FROM intro11equiz WHERE chapterNo = ? AND questionNo = ?";

            String insertString = "INSERT INTO intro11e (chapterNo, questionNo, isCorrect,"
                    + " hostname, answerA, answerB, answerC, answerD, answerE, username)"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?)";

            String updateString = "UPDATE Staff SET "
                    + "id=?, lastName=?, mi=?, firstName=?, address=?, city=?, state=?,"
                    + "telephone=?, email=? WHERE id=?";

            selectStatement = connection.prepareStatement(selectString);
            insertStatement = connection.prepareStatement(insertString);
            updateStatement = connection.prepareStatement(updateString);

        } catch (ClassNotFoundException | SQLException ex) {
            //setError(ex.getMessage());
        }

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(String isCorrect) {
        System.out.println(isCorrect);

        //this.isCorrect = isCorrect;
    }

    public String[] getSubmittedAnswers() {
        return submittedAnswers;
    }

    public void setSubmittedAnswers(String[] submittedAnswers) {
            this.submittedAnswers = submittedAnswers;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
//        System.out.println("Answers: " + Arrays.toString(answerList).contains(Arrays.toString(submittedAnswers)));
//        System.out.println("AnswerList: " )
        StringBuilder check = new StringBuilder();
        if (getSubmittedAnswers() != null) {
            for (String e : getSubmittedAnswers()) {
                check.append(e);
                switch (e) {
                    case "a":
                        answerA = true;
                        break;
                    case "b":
                        answerB = true;
                        break;
                    case "c":
                        answerC = true;
                        break;
                    case "d":
                        answerD = true;
                        break;
                    case "e":
                        answerE = true;
                        break;

                }

            }
        }
        isCorrect = check.toString().equals(getAnswerKey());

        insertRecord();
    }

    public String[] getAnswerList() {
        String[] answerList1 = {choiceA, choiceB, choiceC, choiceD, choiceE};

        for (String e : answerList1) {
            System.out.println(e);
        }

        answerList = answerList1;
        return answerList;
    }

    public void setAnswerList(String[] questionList) {
        this.answerList = questionList;
    }

    public String getChapterNo() {
        return chapterNo;
    }

    public void setChapterNo(String chapterNo) {
        this.chapterNo = chapterNo;
    }

    public String getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
    }

    public String getChoiceA() {
        return choiceA;
    }

    public void setChoiceA(String choiceA) {
        this.choiceA = choiceA;
    }

    public String getChoiceB() {
        return choiceB;
    }

    public void setChoiceB(String choiceB) {
        this.choiceB = choiceB;
    }

    public String getChoiceC() {
        return choiceC;
    }

    public void setChoiceC(String choiceC) {
        this.choiceC = choiceC;
    }

    public String getChoiceD() {
        return choiceD;
    }

    public void setChoiceD(String choiceD) {
        this.choiceD = choiceD;
    }

    public String getChoiceE() {
        return choiceE;
    }

    public void setChoiceE(String choiceE) {
        this.choiceE = choiceE;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public PreparedStatement getSelectStatement() {
        return selectStatement;
    }

    public void setSelectStatement(PreparedStatement selectStatement) {
        this.selectStatement = selectStatement;
    }

    public PreparedStatement getInsertStatement() {
        return insertStatement;
    }

    public void setInsertStatement(PreparedStatement insertStatement) {
        this.insertStatement = insertStatement;
    }

    public PreparedStatement getUpdateStatement() {
        return updateStatement;
    }

    public void setUpdateStatement(PreparedStatement updateStatement) {
        this.updateStatement = updateStatement;
    }

    public ResultSet getRset() {
        return rset;
    }

    public void setRset(ResultSet rset) {
        this.rset = rset;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerKey() {
        return answerKey;
    }

    public void setAnswerKey(String answerKey) {
        this.answerKey = answerKey;
    }

}
