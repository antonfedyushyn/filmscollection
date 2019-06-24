<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ru">
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
                                <input id="story" name="story" value="Поиск" onblur="if(this.value=='\'\'') this.value='\''+Поиск+'\'';" onfocus="if(this.value=='\''+Поиск+'\'') this.value='\'\'';" type="text">
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
                <div class="contener2">
                    <div class="content">
                        <div id='dle-content'>
                            <div id="orderdesc-area">
                                <div>
                                    <font color="red">
                                <b>
                                    <c:if test="${result ne null}">
                                        <c:if test="${result == false}">
                                            <c:if test="${resultMessage ne null}">
                                                ${resultMessage}
                                            </c:if>
                                        </c:if>
                                    </c:if>
                                </b>
                                    </font>
                            </div>
                            </div>
                            <form  method="post" name="registration" id="registration" action="/register">
                                <div class="padding_border">
                                    <table class="tableform">
                                        <tr>
                                            <td colspan="2">
                                                <p>
                                                    <b>Здравствуйте, уважаемый посетитель нашего сайта!</b>
                                                    <br/><br/>
                                                    Регистрация на нашем сайте позволит Вам быть его полноценным участником.
                                                    Вы сможете добавлять новости на сайт, оставлять свои комментарии, просматривать скрытый текст и многое другое.
                                                    В случае возникновения проблем с регистрацией, обратитесь к <a href="/index.php?do=feedback">администратору</a> сайта.
                                                    <br/><br/>
                                                </p>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="167" class="label">Логин:</td>
                                            <td width="962">
                                                <input type="text" name="login" id='login' class="f_input"
                                                       <c:if test="${login ne null}">
                                                           value="${login}"
                                                       </c:if>
                                                /> &nbsp;
                                                <input class="fbutton"  title="Проверить доступность логина для регистрации" onclick="CheckLogin(); return false;" type="button" value="Проверить имя" />
                                                <div id='result-registration'>
                                                    <font color="red">
                                                        <b>
                                                            <c:if test="${result ne null}">
                                                                <c:if test="${result == false}">
                                                                    <c:if test="${resultMessage ne null}">
                                                                        ${resultMessage}
                                                                    </c:if>
                                                                </c:if>
                                                            </c:if>
                                                        </b>
                                                    </font>
                                                </div>
                                            </td>
                                        </tr>
<c:if test="${resultMessage ne null}">
                                        <tr>
                                            <td colspan="2" style="color:red" >
                                                <div style="color: red;">
                                                    <b> ${resultMessage}</b>
                                                </div>
                                            </td>
                                        </tr>
</c:if>
                                        <tr>
                                            <td class="label">Пароль:</td>
                                            <td><input type="password" name="password" id="password" class="f_input" /></td>
                                        </tr>
                                        <tr>
                                            <td class="label">Повторите:</td>
                                            <td><input type="password" name="password_confirm" id="password_confirm" class="f_input" /></td>
                                        </tr>
                                        <tr>
                                            <td class="label">Ваш E-Mail:</td>
                                            <td><input type="text" name="email" id="email" class="f_input"
                                                    <c:if test="${email ne null}">
                                                        value="${email}"
                                                    </c:if>
                                            /></td>
                                        </tr>
                                    </table>
<c:if test="${error ne null}">
    <script>alert("${error}");</script>
</c:if>
                                    <div class="fieldsubmit"><br><br>
                                        <button name="submit" class="fbutton" type="submit"><span>Отправить</span></button>
                                    </div>
                                </div>
                                <input name="submit_reg" type="hidden" id="submit_reg" value="submit_reg" />
                                <input name="do" type="hidden" id="do" value="register" />
                            </form>
                            <script language='javascript' type="text/javascript">
                                <!--
                                function check_reg_daten () {
                                    if(document.forms.registration.name.value == '') {
                                        DLEalert('Имя пользователя не может быть пустым!', dle_info);
                                        return false;
                                    }
                                    if(document.forms.registration.password1.value.length < 6) {
                                        DLEalert('Длина пароля должна быть не менее 6 символов!', dle_info);
                                        return false;
                                    }
                                    if(document.forms.registration.password1.value != document.forms.registration.password2.value) {
                                        DLEalert('Оба введенных пароля должны быть идентичны!', dle_info);
                                        return false;
                                    }
                                    if(document.forms.registration.email.value == '') {
                                        DLEalert('Введен неверный E-Mail адрес!', dle_info);
                                        return false;
                                    }
                                    return true;
                                };
                                //-->
                            </script>
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
                                        <i style="font-size:14px;">Категории</i><br><br>
                                        <div class="film_category">
                                            <c:forEach var="s" items="${genres}">
                                                <a href="/genre?code=<c:out value="${s.code}" />/"><c:out value="${s.name}" /></a><br>
                                            </c:forEach>
                                        </div>
                                    </div>
                                    <div style="padding-top:20px;"></div><!--div-->
                                </div><!--miniblock-->
                            </div><!--leftblok1-->
                            <div class="leftblok2">
                                <div class="miniblock">
                                    <div class="mini" style="border-top:0px; padding-top:0px;">
                                        <i style="font-size:14px;">По году</i><br><br>
                                        <div class="film_years">
                                            <c:forEach var="s" items="${years}">
                                                <a href="/year?code=<c:out value="${s.name}" />/"><c:out value="${s.name}" /> года</a><br>
                                            </c:forEach>
                                        </div>
                                        <div class="poloska"></div>
                                    </div>
                                    <div class="mini">
                                        <i style="font-size:14px;">По странам</i><br><br>
                                        <div class="film_country">
                                            <c:forEach var="s" items="${countries}">
                                                <a href="/country?code=<c:out value="${s.code}" />/"><c:out value="${s.name}" /></a><br>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div><!--miniblock-->
                            </div><!--leftblok2-->
                        </span><!--leftblok_contener2-->
                    </span><!--leftblok_contener3-->
                </div><!--contener2-->
            </div><!--contener-->
        </div><!--wrapper-->
    </body>
</html>