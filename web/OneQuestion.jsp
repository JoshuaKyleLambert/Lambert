<%@page import="quiz.OneQuestion" %>
<%String chap, ques;
    if ((chap = request.getParameter("chapterNo")) != null) {
    } else {
        chap = "5";
    }
    if ((ques = request.getParameter("questionNo")) != null) {
    } else {
        ques = "1";
    }
%>
<jsp:useBean id="question" class="quiz.OneQuestion" scope="page">
    <jsp:setProperty name="question" property="chapterNo" value="<%= chap%>"/>
    <jsp:setProperty name="question" property="questionNo" value="<%= ques%>" />
    <% question.viewQuestion();%>
</jsp:useBean>
<jsp:setProperty name="question" property="*"/>
<link rel="stylesheet"  type="text/css"    href="highlight.css">
<script src="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/highlight.min.js"></script>
<script>hljs.initHighlightingOnLoad();</script>
<!DOCTYPE html>
<html>

    <head>
        <title>Individual Questions Project</title>

        <%  String[] questionText = question.getQuestion().split("\n", 2);
            String theQuestion = questionText[0];
            String questionBody = "";
            if (questionText.length > 1) {
                questionBody = questionText[1];
            }
        %>

    </head>
    <body>
        <h3 id="h3style" style = " width: 500px auto; max-width: 620px; margin: 0 auto; ">Multiple-Choice Question 5.2.1</h3>
        <div style="width: 500px auto; max-width: 620px; margin: 0 auto; border: 1px solid #f6912f; font-weight: normal;padding-top: 10px; ">

            <span style="margin-top: 10px;margin-left: 10px"><%= theQuestion%></span>

            <pre><code ><%= questionBody%></code></pre>
                
        <form method="post" >
                <% String[] answerList = question.getAnswerList();%>
                
                <div id="questionstatement" style="margin-left: 5px;"><br>
                    
                    <% if (question.getAnswerKey().length() > 1) {%>
                    <% for (int i = 0; i < answerList.length; i++) {%>
                    <% if (answerList[i] != null) {%>                 
                    <input name="answer" type="checkbox" value="<%= (char) ((int) 'a' + i)%>"  /> <%= (char) ((int) 'A' + i)%>. <%=  answerList[i]%>  <br /> 
                    <%}%>
                    <%}%>
                    <%}%>

                    <% if (question.getAnswerKey().length() == 1)
                            for (int i = 0; i < answerList.length; i++) {
                                if (answerList[i] != null) {%>                 
                    <input name="answer" type="radio" value="<%= (char) ((int) 'a' + i)%>"  /> <%= (char) ((int) 'A' + i)%>. <%=  answerList[i]%>  <br /> 
                    <% }%>
                    <% }%>

                </div>
                
                <%String[] answers = request.getParameterValues("answer");
                    if (answers != null) {
                        StringBuilder submit = new StringBuilder();
                        for (String e : answers) {
                            submit.append(e);
                        }
                        if (submit.toString().equalsIgnoreCase(question.getAnswerKey())) {
                            out.print("<span style=\"margin-left: 10px;color: green;\">Your answer is correct. </span><img border=\"0\" src=correct.jpg width=\"42\" height=\"30\">");
                        } else {
                            out.print("<span style =\"color: red;margin-left: 10px;\">Your answer E is incorrect <img border=\"0\" src=wrong.jpg width=\"28\" height=\"28\"></span>");
                            out.print("<div id = \"a1\" style = \"color: green;margin-left: 10px;\"> Click here to show the correct answer and an explanation</div>");
                            out.print("<script type=\"text/javascript\">$(document).ready(function() {$(\"#a1\").click(function() {$(this).text(\"The correct answer is " + question.getAnswerKey() + " \");$(this).append(\"<div style = 'color: purple; font-family: Times New Roman;'> Explanation: " + question.getHint() + " </div>\");});});</script>");
                        }
                    }
                %><br>

                <input type="submit" id="checkButton" name = "buttonName" value= "Check My Answer">                 
                <input type="hidden" value="<%= chap%>" name="chapterNo" />
                <input type="hidden" value="<%= ques%>" name="questionNo" />
                <input type="hidden" value=500 name="width" />
                <input type="hidden" value=620 name="maxwidth" />


        </form>      
        </div>
                <script>
                    $(document).ready(function () {
                        $(".test").click(function () {
                            $("#thedialog").attr('src', $(this).attr("href"));
                            $("#somediv").dialog({
                                width: 400,
                                height: 440,
                                modal: true,
                                close: function () {
                                    $("#thedialog").attr('src', "about:blank");
                                }
                            });
                            return false;
                        });
                    });
</script>
    </body>
</html>
