<%@page import="quiz.OneQuestion" %>
<jsp:useBean id="question" class="quiz.OneQuestion" scope="page">
    <jsp:setProperty name="question" property="chapterNo" value="<%= request.getParameter("chapterNo")%>"/>
    <jsp:setProperty name="question" property="questionNo" value="<%= request.getParameter("questionNo")%>" />

    <% question.viewQuestion();%>


</jsp:useBean>
<jsp:setProperty name="question" property="*"/>
<link rel="stylesheet"      href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/styles/default.min.css">
<script src="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/highlight.min.js"></script>
<script>hljs.initHighlightingOnLoad();</script>


<!DOCTYPE html>
<html>


    <head>
        <title>Multiple-Choice Question by Y. Daniel Liang</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
        <style type = "text/css">
            body {font-family: "Courier New", sans-serif; font-size: 100%; color: black}
            .keyword {color: #000080; font-weight: normal}
            .comment {color: gray}
            .literal {font-weight: normal; color: #3366FF}
        </style>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
        <meta content="Introduction to Java Programming, Java Multiple-choice questions" name="description">
        <meta content="Java Multiple-choice questions, Test, Java, Liang, Y. Daniel Liang, Introduction to Java Programming, Brief, Comprehensive" name="keywords">
        <link rel="stylesheet" type="text/css" href="intro6e.css" />
        <link rel="stylesheet" type="text/css" href="intro6eselftest.css" />
        <style>
            #question {font-family: "Courier New", Courier, Verdana, Helvetica, sans-serif; font-size: 100%;
                       color: #8000f0; color: black; margin-left: 0.5em}
            #questionstatement {font-family:
                                    Times New Roman, monospace, Courier, Verdana, Helvetica, sans-serif; font-size: 100%; color: #8000f0;
                                color: black; margin-left:1.8em; margin-top:0.5em; margin-bottom:0.5em; }
            #choices {font-family: Times New Roman, Helvetica, sans-serif; font-size: 100%;
                      color: #8000f0; color: black; margin-left:25.0pt; margin-left:0.5em; margin-bottom:0.5em; }
            #choicemargin {font-family: Times New Roman, Helvetica, sans-serif; font-size: 100%; }
            #choicestatement {font-family: Times New Roman, Helvetica, sans-serif; font-size: 100%;
                              color: #8000f0; color: black; margin-left:25.0pt; margin-left:0.5em; margin-bottom:0.5em; }
            .preBlock {margin-left:0px;text-indent:0.0px; font-family:monospace; font-size: 120%}
            .keyword {color: green;}
            .comment {color: darkred;  }
            .literal {color: darkblue}
            .constant {color: teal}
            #h3style {color: white; font-family: Helvetica, sans-serif;  font-size: 100%; border-color: #6193cb; text-align: center;margin-bottom: 0.5em; background-color: #6193cb;}  </style>

        <!-- Global Site Tag (gtag.js) - Google Analytics -->


    </head>
    <body>
        <h3 id="h3style" style = " width: 500px auto; max-width: 620px; margin: 0 auto; ">Multiple-Choice Question 5.2.1</h3>
        <div style="width: 500px auto; max-width: 620px; margin: 0 auto; border: 1px solid #f6912f; font-weight: normal ">

            <form method="post" >

                <% String[] questionText = question.getQuestion().split("\n", 2);
                    //String questionBody = questionText[1];
                    String questionBody = "";
                    if (questionText.length > 1) {
                        questionBody = questionText[1];
                    }
                %>
                <%= questionText[0]%>

                <pre><code style="background-color: white;"><%= questionBody%></code></pre>

                <% String[] answerList = question.getAnswerList();%>
                <% int answerlistlength = answerList.length;%>
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
                        for (String e: answers) {
                            submit.append(e);
                        }
                        if (submit.toString().equalsIgnoreCase(question.getAnswerKey())) {
                            out.print("<span style=\"margin-left: 5px;color: green;\">Your answer is correct. </span><img border=\"0\" src=correct.jpg width=\"42\" height=\"30\">");
                        } else {
                            out.print("<span style = \"color: red\">Your answer E is incorrect <img border=\"0\" src=wrong.jpg width=\"28\" height=\"28\"></span>");
                        }
                    }
                %><br>

                <input type="submit" style = "margin-bottom: 0px;
                       margin-top: 10px;
                       margin-left: 5px;
                       border: 0px;
                       font-family: Helvetica, monospace;
                       font-size: 85%;
                       background-color: rgba(0, 128, 0, 0.7);
                       border-radius: 0px; color:
                       black;" name = "buttonName" value= "Check My Answer"> </div>
                       
                       


                
                <input type="hidden" value="<%= request.getParameter("chapterNo")%>" name="chapterNo" />
                <input type="hidden" value="<%= request.getParameter("questionNo")%>" name="questionNo" />
                <input type="hidden" value=500 name="width" />
                <input type="hidden" value=620 name="maxwidth" /></form></div>
    </body>
</html>
