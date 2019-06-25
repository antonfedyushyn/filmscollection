<%--suppress ELValidationInJSP --%>
<%--suppress HtmlFormInputWithoutLabel --%>
<%--suppress XmlPathReference --%>
<%--suppress HtmlUnknownTarget --%>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Кинотеатр онлайн</title>
        <link media="screen" href="<c:url value="resources/css/styles.css" />" type="text/css" rel="stylesheet" />
        <link media="screen" href="<c:url value="resources/css/engine.css" />" type="text/css" rel="stylesheet" />
        <link media="screen" href="<c:url value="resources/highslide/highslide.css" />" type="text/css" rel="stylesheet" />
        <link rel="shortcut icon" href="<c:url value="resources/img/favicon.ico" />" type="image/x-icon">
        <style>
            body {background: url(<c:url value="resources/img/IronMan3.jpg" />) 50% 0 no-repeat #000;}
        </style>
        <script type="text/javascript" src="resources/highslide/highslide.js"></script>
    </head>

    <body bgcolor='#ccc'>
        <script type="text/javascript">
            hs.graphicsDir = 'resources/highslide/graphics/';
            hs.outlineType = 'rounded-white';
            hs.numberOfImagesToPreload = 0;
            hs.showCredits = false;
            hs.dimmingOpacity = 0.60;
            hs.lang = {
                loadingText :     'Загрузка...',
                playTitle :       'Просмотр слайдшоу (пробел)',
                pauseTitle:       'Пауза',
                previousTitle :   'Предыдущее изображение',
                nextTitle :       'Следующее изображение',
                moveTitle :       'Переместить',
                closeTitle :      'Закрыть (Esc)',
                fullExpandTitle : 'Развернуть до полного размера',
                restoreTitle :    'Кликните для закрытия картинки, нажмите и удерживайте для перемещения',
                focusTitle :      'Сфокусировать',
                loadingTitle :    'Нажмите для отмены'
            };
        </script>
        <div class="header_black"></div>
        <div class="wrapper">
            <div class="header">
                <div class="header44">
                    <div class="main_menu">
                        <a href="<c:url value="/"/>"><img src="<c:url value="resources/img/filmcollection.png" />" width="91" height="26"></a>
                    </div>
                    <div class="search_panel">
                        <span class="searchbar">
                            <form action="<c:url value="/find"/>" name="searchform" method="post">
                                <input name="titleonly" value="3" type="hidden">
                                <input type="hidden" name="do" value="search">
                                <input type="hidden" name="subaction" value="search">
                                <input id="story" name="findText" value="Поиск" onblur="if(this.value==='') this.value='Поиск';" onfocus="if(this.value==='Поиск') this.value='';" type="text" title="Поиск">
                                <button class="fbutton2" onclick="submit();" type="submit" title="ok" style="float: right;"><span>ok</span></button>
                            </form>
                        </span>
                    </div>

                    <div class="user_panel">
                        <div class="loginin" >
                            <c:set var="isAuth" value="${isRegistration}" />
                            <c:if test="${isAuth == true}">
                                <a class="lbn" id="logbtn" href="/user/${login}">${login}</a>  /
                                <a class="thide lexit" href="<c:url value="/unauthorized"/>">Выход</a>
                            </c:if>
                            <c:if test="${isAuth == false}">
                                <script>
                                    function change(idName) {
                                        if(document.getElementById(idName).style.display==='none') {
                                            document.getElementById(idName).style.display = '';
                                        } else {
                                            document.getElementById(idName).style.display = 'none';
                                        }
                                        return false;
                                    }
                                </script>

                                <a href="#" onclick="change('test')">Вход</a> / <a href="<c:url value="/register"/>">Регистрация</a>
                            </c:if>
                        </div>
                        <div style="display:none; float:left; padding-left: 10px; padding-top: 4px;" id="test">
                            <form method="post" action="<c:url value="/j_spring_security_check"/>">
                                <label for="j_login">Логин: </label>
                                <input type="text" name="j_login" id="j_login" style="width: 60px;"/>
                                <label for="j_password">Пароль</label>
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
                        <div style="border-top: 1px solid #404040;background: #2b2b2b;position: relative;border-left: 1px solid #404040;"></div>
                        <div id='dle-content'>
                            <c:if test = "${result == false}">
                                <br/><br/>
                                <h1><div class="errormsg"><c:out value="${resMessage}"/></div></h1>
                                <br/><br/>
                                <br/><br/>
                            </c:if>
                            <table cellpadding="8" width="100%">
                                <c:forEach var="s" items="${films}">
                                    <tr>
                                        <div class="shortstory">
                                            <div class="shortstorytitle">
                                                <h2 class="zagolovki">
                                                    <a href="/film?code=<c:out value="${s.code}"/>"><c:out value="${s.name}"/></a>
                                                </h2>
                                            </div><!--shortstorytitle-->
                                            <div class="shortimg">
                                                <div id="news-id-<c:out value="${s.id}" />" style="display:inline;">
                                                    <a href="<c:out value="${s.posterUrl}"/>" onclick="return hs.expand(this)" >
                                                        <img src="<c:out value="${s.posterUrl}"/>" style="float:left; width: 35%" alt='<c:out value="${s.name}"/>' title='<c:out value="${s.name}"/>'  />
                                                    </a>
                                                    <c:out value="${s.notes}"/><br />
                                                    <br/>
                                                    <b>Год выпуска:</b><a href="/year?code=<c:out value="${s.year.name}"/>/" ><c:out value="${s.year.name}"/></a>
                                                    <br/>
                                                    <b>Страна:</b> <a href="/country?code=<c:out value="${s.country.code}" />/" ><c:out value="${s.country.name}"/></a>
                                                    <br/>
                                                    <b>Жанр:</b>
                                                    <c:forEach var="genres" items="${s.filmGenres}">
                                                        <a href="/genre?code<c:out value="${genres.code}"/>/" ><c:out value="${genres.name}"/></a>,
                                                    </c:forEach>
                                                    <br/>
                                                    <b>Качество:</b> <c:out value="${s.qualityStr}"/>
                                                    <br/>
                                                    <b>Перевод:</b> <spring:message code="${s.translationStr}"/>
                                                    <br/>
                                                    <b>Продолжительность:</b> <c:out value="${s.duration}"/>
                                                    <br/><b>Премьера:</b> <fmt:formatDate value="${s.premiereDate}" pattern="dd.MM.yyyy" />
                                                </div>
                                            </div>
                                        </div><!--shortstory-->
                                        <div style="clear:both"></div>
                                        <div class="icons">
                                            <span class="podrobnee" style="padding: 4px 0 4px 20px;">
                                                <a href="/film?code=<c:out value="${s.code}"/>">
                                                    <span class="fbutton-img">
                                                        <img src="<c:url value="resources/img/viewmore.png" />" border="0" alt="смотреть <c:out value="${s.name}"/> онлайн">
                                                    </span>
                                                </a>
                                            </span>
                                            <span style="float:right; padding: 7px 20px 19px 20px;">
                                                <span class="dateicon"  title="Дата">
                                                    <fmt:formatDate value="${s.dateUpload}" pattern="dd.MM.yyyy" />
                                                </span>
                                                <span class="editicon"  title="Редактировать"></span>
                                                </span>
                                        </div><!--icons-->
                                    </tr>
                                </c:forEach>
                            </table>
                            <c:if test = "${filmCountPages > 0}">
                                <div class="bot-left">
                                    <div class="bot-navigation"  align="center">
                                        <c:forEach var="s" items="${filmsPages}">
                                            <c:choose>
                                                <c:when test="${(s.typePage == 'FIRSTPAGE')&&(s.isActive)}">
                                                    <a href='<c:out value="${s.url}"/>'>
                                                        <spring:message code="btn.firstpage"/>
                                                    </a>
                                                </c:when>
                                                <c:when test="${(s.typePage == 'FIRSTPAGE')&&(!s.isActive)}">
                                                    <span><spring:message code="btn.firstpage"/></span>
                                                </c:when>
                                                <c:when test="${(s.typePage == 'PREVIOUSPAGE')&&(s.isActive)}">
                                                    <a href='<c:out value="${s.url}"/>'>
                                                        <spring:message code="btn.previouspage"/>
                                                    </a>
                                                </c:when>
                                                <c:when test="${(s.typePage == 'PREVIOUSPAGE')&&(!s.isActive)}">
                                                    <span><spring:message code="btn.previouspage"/></span>
                                                </c:when>
                                                <c:when test="${(s.typePage == 'PAGE')&&(s.isActive)}">
                                                    <a href='<c:out value="${s.url}"/>'>
                                                        <c:out value="${s.name}"/>
                                                    </a>
                                                </c:when>
                                                <c:when test="${(s.typePage == 'PAGE')&&(!s.isActive)}">
                                                    <span><c:out value="${s.name}"/></span>
                                                </c:when>
                                                <c:when test="${(s.typePage == 'NEXTPAGE')&&(s.isActive)}">
                                                    <a href='<c:out value="${s.url}"/>'>
                                                        <spring:message code="btn.nextpage"/>
                                                    </a>
                                                </c:when>
                                                <c:when test="${(s.typePage == 'NEXTPAGE')&&(!s.isActive)}">
                                                    <span><spring:message code="btn.nextpage"/></span>
                                                </c:when>
                                                <c:when test="${(s.typePage == 'LASTPAGE')&&(s.isActive)}">
                                                    <a href='<c:out value="${s.url}"/>'>
                                                        <spring:message code="btn.lastpage"/>
                                                    </a>
                                                </c:when>
                                                <c:when test="${(s.typePage == 'LASTPAGE')&&(!s.isActive)}">
                                                    <span><spring:message code="btn.lastpage"/></span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span><c:out value="${s.name}"/></span>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:if>
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
                                    <div class="mini" style="border-top:0; padding-top:0;">
                                        <i style="font-size:14px;">Категории</i><br><br>
                                        <div class="film_category">
                                            <c:forEach var="s" items="${genres}">
                                                <a href="/genre?code=<c:out value="${s.code}" />"><c:out value="${s.name}" /></a><br>
                                            </c:forEach>
                                        </div>
                                    </div>
                                    <div style="padding-top:20px;"></div><!--div-->
                                </div><!--miniblock-->
                                <!--div-->
                            </div><!--leftblok1-->
                            <div class="leftblok2">
                                <div class="miniblock">
                                    <div class="mini" style="border-top:0; padding-top:0;">
                                        <i style="font-size:14px;">По году</i><br><br>
                                        <div class="film_years">
                                            <c:forEach var="s" items="${years}">
                                                <a href="/year?code=<c:out value="${s.name}" />"><c:out value="${s.name}" /> года</a><br>
                                            </c:forEach>
                                        </div>
                                        <div class="poloska"></div>
                                    </div>
                                    <div class="mini">
                                        <i style="font-size:14px;">По странам</i><br><br>
                                        <div class="film_country">
                                            <c:forEach var="s" items="${countries}">
                                                <a href="/country?code=<c:out value="${s.code}" />"><c:out value="${s.name}" /></a><br>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div><!--miniblock-->
                            </div><!--leftblok2-->
                        </span><!--leftblok_contener2-->
                    </span><!--leftblok_contener3-->
                </div><!--contener2-->
            </div><!--contener-->

            <div class="footer">
                <div class="footer-text">
                    <p><img src="<c:url value="resources/img/footer-main.jpg" />" title="просмотр фильмов онлайн">Специально для наших уважаемых пользователей мы постарались собрать лучшую коллекцию фильмов различных жанров, времен и народов! Команда проекта постоянно следит за выходом фильмов в прокат и регулярно добавляет картины, чтобы вы могли  одними из первых наслаждаться, как лучшими мировыми шедеврами, так и новинками кино совершенно бесплатно! </p>
                    <p>У нас вы найдете лучшие фильмы «Золотой эпохи» Голливуда, классическое европейское кино, лучшие французские комедии, азиатское кино, советские картины и современные хиты <a href="/year?code=2011">2011</a>, <a href="/year?code=2012">2012</a>, а также <a href="/year?code=2013">фильмы 2013</a> годов. Мы собрали внушительную коллекцию художественных и документальных фильмов, боевиков и триллеров, фэнтези и фантастики, драм и мелодрам, здесь же вы найдете <a href="/genre?code=camedy">комедии</a>, <a href="/genre?code=horrors">ужасы</a>, приключенческие и исторические ленты, детективы, вестерны, кино на военную тематику, а также картины других жанров и направлений. У нас смотреть фильмы можно в любое время, в неограниченных количествах, совершенно бесплатно и при этом в отличном качестве! Собирайтесь с друзьями, выбирайте хорошее кино, смотрите и делитесь впечатлениями! Здесь каждый сможет найти что-то интересное для себя. Жанровое кино, старый добрый советский фильм, а может быть, интересную документальную ленту. Любители новинок также смогут смотреть свежие фильмы 2014 в хорошем качестве картинки и звука.</p>
                    <p>Специально для вашего удобства на сайте действует поисковая строка, благодаря которой можно быстро отыскать нужный фильм, введя его русское или оригинальное название. Если поиск не дал результатов, попробуйте воспользоваться расширенным поиском или напишите нам, и мы обязательно постараемся добавить нужный вам фильм. Также мы постарались максимально удобно структурировать сам сайт, чтобы искать фильмы различных жанров стало еще проще. Мы стараемся удовлетворять запросы широкого круга зрителей, поэтому оперативно добавляем новые фильмы практически сразу после их выхода в прокат, а затем регулярно обновляем их качество на лучшее сразу после релиза DVD или Blu-ray дисков. Поэтому наш кинотеатр готов стать верным партнером и помощником, как для ценителей отменного качества, так и для тех зрителей, которые предпочитают посмотреть новый фильм как можно быстрее.</p>
                    <p>Смотрите хорошее <strong>кино онлайн бесплатно</strong>, без регистрации и отправки смс даже не выходя из дома!</p>
                </div><!--div-->
                <div style="float:right;  text-align: right; width:300px; padding-top: 3px;"></div>
            </div><!--footer-->
        </div><!--wrapper-->
    </body>
</html>