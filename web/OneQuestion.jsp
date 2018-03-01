<%@page import="java.util.Arrays"%>
<%@page import="quiz.OneQuestion" %>
<%String chap, ques;
    if ((chap = request.getParameter("chapterNo")) != null) {
    } else {
        chap = Integer.toString((int) (Math.random() * 43) + 1);
    }
    if ((ques = request.getParameter("questionNo")) != null) {
    } else {
        ques = "1";
    }
%>
<jsp:useBean id="question" class="quiz.OneQuestion" scope="page">
    <jsp:setProperty name="question" property="chapterNo" value="<%= chap%>"/>
    <jsp:setProperty name="question" property="questionNo" value="<%= ques%>" />
    <jsp:setProperty name="question" property="submittedAnswers" value="<%= request.getParameterValues("answer")%>" />
    <jsp:setProperty name="question" property="hostName" value="<%= request.getRemoteAddr()%>" />
    <% question.viewQuestion();%>
</jsp:useBean>
<jsp:setProperty name="question" property="*"/>

<link rel="stylesheet"  type="text/css"    href="highlight.css">
<script src="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/highlight.min.js"></script>
<script>hljs.initHighlightingOnLoad();</script>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<!DOCTYPE html>
<html>
    <head>
        <title>Individual Questions Project</title>
        <%  String[] questionText = question.getQuestion().trim().split("\n", 2);
            String theQuestion = questionText[0];
            String questionBody = "";
            if (questionText.length > 1) {
                questionBody = questionText[1];
            }
        %>
    </head>
    <body>
        <h3 id="h3style" style = " width: 500px auto; max-width: 620px; margin: 0 auto; ">Multiple-Choice Question 5.2.1</h3>
        <div style="width: 500px auto; max-width: 620px; margin: 0 auto; border: 1px solid #f6912f; font-weight: normal;padding-top: 10px; padding-left: 5px">
            <span style="margin-top: 10px;margin-left: 10px"><%= theQuestion%></span>
            <pre style="font-weight: bold;font-size: 100%"><code ><%= questionBody%></code></pre>             
        <form method="post" >
                <% String[] answerList = question.getAnswerList();
                    if (question.getAnswerKey().length() > 1) {
                        for (int i = 0; i < answerList.length; i++) {
                            if (answerList[i] != null) {%>                 
                                <input name="answer" type="checkbox" value="<%= (char) ((int) 'a' + i)%>"  /> <%= (char) ((int) 'A' + i)%>. <%=  answerList[i]%>  <br /> 
                            <%}
                        }
                    }
                    if (question.getAnswerKey().length() == 1) {
                        for (int i = 0; i < answerList.length; i++) {
                            if (answerList[i] != null) {%>                 
                                <input name="answer" type="radio" value="<%= (char) ((int) 'a' + i)%>"  /> <%= (char) ((int) 'A' + i)%>. <%=  answerList[i]%>  <br /> 
                            <%}
                        }
                    }
                String[] answers = request.getParameterValues("answer");
                    if (answers != null) {
                        StringBuilder submit = new StringBuilder();
                        for (String e : answers) {
                            submit.append(e);
                        }
                        if (submit.toString().equalsIgnoreCase(question.getAnswerKey())) {
                            out.print("<span style=\"margin-left: 10px;color: green;\">Your answer " + submit.toString().toUpperCase() + " is correct. </span><img border=\"0\" src=correct.jpg width=\"42\" height=\"30\">");
                        } else if (!submit.toString().equalsIgnoreCase(question.getAnswerKey())) {
                            out.print("<span style =\"color: red;margin-left: 10px;\">Your answer " + submit.toString().toUpperCase() + " is incorrect <img border=\"0\" src=wrong.jpg width=\"28\" height=\"28\"></span>");
                            out.print("<div id = \"a1\" style = \"color: green;margin-left: 10px;cursor: zoom-in;\"> Click here to show the correct answer and an explanation</div>");
                            out.print("<script type=\"text/javascript\">$(document).ready(function() {$(\"#a1\").click(function() {$(this).text(\"The correct answer is " + question.getAnswerKey().toUpperCase() + " \");$(this).append(\"<div style = 'color: purple; font-family: Times New Roman;'> Explanation: " + question.getHint() + " </div>\");});});</script>");
                        }
                    } else if (question.getButtonName() != null) {
                        out.print("<div>You did not answer this <img border=\"0\" src=noanswer.jpg width=\"40\" height=\"40\"></div>");
                    }
                %><br>

                <input type="submit" id="checkButton" name = "buttonName" value= "Check My Answer">                 
                <input type="hidden" value="<%= chap%>" name="chapterNo" />
                <input type="hidden" value="<%= ques%>" name="questionNo" />
                <input type="hidden" value=500 name="width" />
                <input type="hidden" value=620 name="maxwidth" />
        </form>      
        </div>
    </body>
</html>
