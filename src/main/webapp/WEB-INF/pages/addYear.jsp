<%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 11.01.2019
  Time: 19:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Кинотеатр онлайн</title>
    <link media="screen" href="<c:url value="resources/css/styles.css" />" type="text/css" rel="stylesheet" />
    <link media="screen" href="<c:url value="resources/css/engine.css" />" type="text/css" rel="stylesheet" />
    <link rel="shortcut icon" href="<c:url value="resources/img/favicon.ico" />" type="image/x-icon">
    <style>
        body {background: url(<c:url value="resources/img/IronMan3.jpg" />) 50% 0 no-repeat #000;}
    </style>
</head>
<body bgcolor='#ccc'>
<div class="header_black"></div>
<div class="wrapper">
    <div class="header">
        <div class="header44">
            <div class="main_menu">
                <a href="/"><img src="<c:url value="resources/img/filmcollection.png" />" width="91" height="26"></a>
            </div>
            <div class="search_panel">
                <span class="searchbar">
                    <form action="" name="searchform" method="post">
                        <input name="titleonly" value="3" type="hidden">
                        <input type="hidden" name="do" value="search">
                        <input type="hidden" name="subaction" value="search">
                        <input id="story" name="story" value="Поиск" onblur="if(this.value==&#39;&#39;) this.value=&#39;Поиск&#39;;" onfocus="if(this.value==&#39;Поиск&#39;) this.value=&#39;&#39;;" type="text">
                        <button class="fbutton2" onclick="submit();" type="submit" title="ok" style="float: right;"><span>ok</span></button>
                    </form>
                </span>
            </div>
            <div class="user_panel">
                <div class="loginin" >
                    <c:set var="isAuth" value="${isRegistration}" />
                    <c:if test="${isAuth == true}">
                        <a class="lbn" id="logbtn" href="/user/${login}">${login}</a>  /
                        <a class="thide lexit" href="/unauthorized">Выход</a>
                    </c:if>
                    <c:if test="${isAuth == false}">
                        <script>
                            function change(idName) {
                                if(document.getElementById(idName).style.display=='none') {
                                    document.getElementById(idName).style.display = '';
                                } else {
                                    document.getElementById(idName).style.display = 'none';
                                }
                                return false;
                            }
                        </script>

                        <a href="#" onclick="change('test')">Вход</a> / <a href="/register">Регистрация</a>
                    </c:if>
                </div>
                <div style="display:none; float:left; padding-left: 10px; padding-top: 4px;" id="test">
                    <form method="post" action="/j_spring_security_check">
                        <label for="j_login">Логин: </label>
                        <input type="text" name="j_login" id="j_login" style="width: 60px;"/>
                        <label for="j_password">Пароль (<a href="/index.php?do=lostpassword">Забыли?</a>): </label>
                        <input type="password" name="j_password" id="j_password"  style="width: 60px;"/>&nbsp;
                        <button class="fbutton2" onclick="submit();" type="submit" title="Войти"><span>Войти</span></button>
                        <input name="login" type="hidden" id="login" value="submit" />
                    </form>
                </div>
            </div>
        </div><!--header44-->
        <div class="horizontal2"></div>
    </div><!--header-->
    <div class="contener">
        <div class="content">
            <div id='dle-content'>
                <form  method="post" name="addYear" id="addYear" action="/addYear">
                    <div class="padding_border">
                        <table cellpadding="8" width="100%">
                            <tr>
                                <td colspan="2" align="center" >
                                    <p><b>Добавление нового года фильма</b></p>
                                </td>
                            </tr>
                            <tr>
                                <td width="15%" class="label">Год:</td>
                                <td width="85%" >
                                    <input type="text" name="filmYear" id="filmYear" style="border: solid 1px #cfcfcf; width: 95%;" value=""/> &nbsp;
                                </td>
                            </tr>
                            <c:if test="${error ne null}">
                                <script>alert("${error}");</script>
                            </c:if>
                            <tr>
                                <td colspan="2" align="center" >
                                    <div class="fieldsubmit"><br><br>
                                        <button name="submit" class="fbutton" type="submit"><span>Отправить</span></button>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </form>
            </div>
            <div class="pomoshnik"></div>
        </div><!--content-->
        <span class="leftblok_contener">
            <div style="padding: 7px 20px; position: relative; border-bottom: 1px solid #3f413f;">
                <i style="font-size:15px;"> Панель навигации</i>
            </div><!--div-->
            <span class="leftblok_contener2">
                <div class="leftblok1">
                    <div class="miniblock">
                        <div class="mini" style="border-top:0px; padding-top:0px;">
                            <div class="film_category">
                                <a href="/addGenre" />Добавить новый жанр</a><br>
                                <a href="/addYear" />Добавить новый год</a><br>
                                <a href="/addCountry" />Добавить новую страну</a><br>
                                <a href="/addFilm" />Добавить новый фильм</a><br>
                            </div>
                        </div>
                        <div style="padding-top:20px;"></div><!--div-->
                    </div><!--miniblock-->
                </div><!--leftblok1-->
                <div class="leftblok2">
                    <div class="miniblock">
                        <div class="mini" style="border-top:0px; padding-top:0px;">
                            <i style="font-size:14px;">Года выпуска</i><br><br>
                            <div class="film_years">
                                <c:forEach var="s" items="${years}">
                                    <i style="font-size:12px;"><c:out value="${s.name}" /></i><br>
                                </c:forEach>
                            </div>
                            <div class="poloska"></div>
                        </div>
                    </div><!--miniblock-->
                </div><!--leftblok2-->
            </span><!--leftblok_contener2-->
        </span><!--leftblok_contener3-->
    </div><!--contener-->
</div><!--wrapper-->
</body>
</html>
