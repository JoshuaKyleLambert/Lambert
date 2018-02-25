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

/**
 *
 * @author Joshu
 */
public class OneQuestion implements java.io.Serializable {

    String chapterNo = "";
    String questionNo = "";
    String question = "";
    String choiceA = "";
    String choiceB = "";
    String choiceC = "";
    String choiceD = "";
    String choiceE ;
    String answerKey;
    String hint;
    String[] answerList = {"","","","",""};

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

    private void initializeDB() {

        try {
            //Load the JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded");

            //Establish a connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/javabook", "scott", "tiger");
            System.out.println("Database connected");

            String selectString = "SELECT  question, choiceA, choiceB, choiceC, choiceD, choiceE, answerKey, hint "
                    + "  FROM intro11equiz WHERE chapterNo = ? AND questionNo = ?";

            String insertString = "INSERT INTO Staff (id, lastName, firstName, mi,"
                    + " address, city, state, telephone, email)"
                    + " VALUES (?,?,?,?,?,?,?,?,?)";

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
