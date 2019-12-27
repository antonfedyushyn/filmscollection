<%--suppress ELValidationInJSP --%>
<%--suppress JspAbsolutePathInspection --%>
<%--suppress HtmlFormInputWithoutLabel --%>
<%--suppress XmlPathReference --%>
<%--suppress HtmlUnknownTarget --%>
<%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 26.06.2019
  Time: 18:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Кинотеатр онлайн</title>
        <link media="screen" href="<c:url value="resources/css/styles_admin.css" />" type="text/css" rel="stylesheet" />
        <link media="screen" href="<c:url value="resources/css/engine.css" />" type="text/css" rel="stylesheet" />
        <link rel="stylesheet" href="<c:url value="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" />" />
        <link rel="shortcut icon" href="<c:url value="resources/img/favicon.ico" />" type="image/x-icon">
        <style>
            body {background: 0 no-repeat #000;}
        </style>
        <script src="<c:url value="https://code.jquery.com/jquery-1.12.4.js" />"></script>
        <script src="<c:url value="https://code.jquery.com/ui/1.12.1/jquery-ui.js" />"></script>
    </head>
    <body bgcolor='#ccc'>
        <div class="header_black"></div>
        <div class="wrapper">
            <div class="header">
                <div class="header44">
                    <a href="<c:url value="/"/>"><img src="<c:url value="resources/img/filmcollection.png" />" width="91" height="26"></a>
                    <div class="main_menu"></div>
                    <div class="search_panel">
                        <span class="searchbar">
                            <form action="<c:url value="/find"/>" name="searchform" method="post">
                                <input name="titleonly" value="3" type="hidden">
                                <input type="hidden" name="do" value="search">
                                <input type="hidden" name="subaction" value="search">
                                <input id="story" name="findText" value="Поиск" onblur="if(this.value==='') this.value='Поиск';" onfocus="if(this.value==='Поиск') this.value='';"
                                       title="Поиск">
                                <button class="fbutton2" onclick="submit();" title="ok" style="float: right;"><span>ok</span></button>
                            </form>
                        </span>
                    </div>
                    <div class="user_panel">
                        <div class="loginin" >
                            <div class="loginin" >
                                <c:if test="${isAuth == true}">
                                    <a class="lbn" id="logbtn" href="/user/<c:out value="${login}"/>"><c:out value="${login}"/></a>  /
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
                        </div>
                        <div style="display:none; float:left; padding-left: 10px; padding-top: 4px;" id="test">
                            <form method="post" action="<c:url value="/j_spring_security_check"/>">
                                <label for="j_login">Логин: </label>
                                <input name="j_login" id="j_login" style="width: 60px;"/>
                                <label for="j_password">Пароль</label>
                                <input type="password" name="j_password" id="j_password"  style="width: 60px;"/>&nbsp;
                                <button class="fbutton2" onclick="submit();" title="Войти"><span>Войти</span></button>
                                <input name="login" type="hidden" id="login" value="submit" />
                            </form>
                        </div>
                    </div>
                </div>
                <div class="horizontal2"></div>
            </div>
            <div class="contener">
                <div class="content">
                    <div id='dle-content'>
                        <form  method="post" name="modifyFilm" id="modifyFilm" action="<c:url value="/modifyFilm"/>">
                            <div class="padding_border">
                                <c:if test = "${result == false}">
                                    <br/><br/>
                                    <h1><div class="errormsg"><c:out value="${resMessage}"/></div></h1>
                                    <br/><br/>
                                </c:if>
                                <table cellpadding="8" width="100%" style="font-size:14px!important; border:2px solid #272727; ">
                                    <tr>
                                        <td colspan="20" align="center" >
                                            <p>
                                                <b>Редактирование фильма с кодом "<c:out value="${film.code}"/>"</b>
                                            </p>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td rowspan="8" colspan="6">
                                        <img id="posterFile" src="<c:url value = "${film.posterUrl}"/>" style="float:left; width: 90%" />
                                        <br/>
                                        <input type="file" id="addPosterFile" name="posterFile"/>
                                        </td>
                                        <td colspan="3" class="label" >Название фильма:</td>
                                        <td colspan="11"  valign="middle">
                                            <input name="filmName" id='filmName' class="f_input" value="<c:out value="${film.name}"/>" title="Название фильма"/>
                                            <input type="hidden" name="filmId" id="filmId" value=""/>
                                            <input type="hidden" name="filmCode" id="filmCode" value="<c:out value="${film.code}"/>"/>
                                        </td>
                                    <tr>
                                        <td colspan="3" class="label" >URL к постеру:</td>
                                        <td colspan="11" >
                                            <input name="posterFilePathShow" id='posterFilePathShow' class="f_input" value="<c:out value="${film.posterUrl}"/>" title="URL к постеру" disabled/>
                                            <input type="hidden" name="posterFilePath" id='posterFilePath' value="<c:out value="${film.posterUrl}"/>"/>
                                            <input type="hidden" name="posterFileName" id="posterFileName" value="<c:out value="${film.posterFileName}"/>"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3" class="label" >Путь к фильму:</td>
                                        <td colspan="11" >
                                            <input name="filmPath" id='filmPath' class="f_input" value="<c:out value="${film.filmDetail.pathFilm}"/>" title="Путь к фильму"/>
                                            <input type="hidden" name="filmVideoFileName" id="filmVideoFileName" value=""/>
                                            <input type="file" id="addFilmVideo" />
                                            <div id='uploadVideoProgress' class="uploadVideo" style='display:none'><img src="resources/img/loader.gif" alt="Uploading...."/></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3" class="label" >Файлы скриншотов:</td>
                                        <td colspan="11" >
                                            <input name="screenShoots" id="screenShoots" value="<c:out value="${film.filmDetail.imagesPath}"/>" class="f_input"  title="скриншоты"/>
                                            <input type="file" id="uloadFiles" name="file[]" class="f_input" multiple />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3" class="label" >Год выпуска фильма:</td>
                                        <td colspan="4" >
                                            <select id="filmYear" name="filmYear" class="f_select" title="Год выпуска фильма">
                                                <option value="">-выберите год-</option>
                                                <c:forEach var="s" items="${years}">
                                                    <option value="${s.name}" <c:if test="${film.year.name == s.name}">selected</c:if>> ${s.name}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td colspan="3" class="label" >&nbsp;Страна выпуска фильма:</td>
                                        <td colspan="4" >
                                            <select id="filmCountry" name="filmCountry" class="f_select" title="Страна выпуска фильма">
                                                <option value="">-выберите страну-</option>
                                                <c:forEach var="s" items="${countries}">
                                                    <option value="${s.code}" <c:if test="${film.country.code == s.code}">selected</c:if>> ${s.name}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3" class="label" >&nbsp;Качество: </td>
                                        <td colspan="4">
                                            <select id="filmQuality" name="filmQuality" class="f_select" title="Качество">
                                                <c:forEach var="s" items="${qualitiesEnums}">
                                                    <option value="${s.key}" <c:if test="${film.quality == s.key}">selected</c:if> >${s.value}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td colspan="3" class="label" >Продолжительность:</td>
                                        <td colspan="4" >
                                            <input name="filmDuration" id='filmDuration' class="f_select"
                                                   pattern="[0-9]{2}:[0-9]{2}:[0-9]{2}" placeholder="00:00:00"
                                                   value="<c:out value="${film.duration}"/>"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3" class="label" >Перевод:</td>
                                        <td colspan="4" >
                                            <select name="filmTranslation" id="filmTranslation" class="f_select" title="Перевод">
                                                <c:forEach var="s" items="${translationEnums}">
                                                    <option value="${s.key}" <c:if test="${film.translation == s.key}">selected</c:if> ><spring:message code="${s.value}"/></option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td colspan="3" class="label" >Премьера:</td>
                                        <td colspan="4" >
                                            <input type="text" style="border: solid 1px #cfcfcf; width: 98%;" name="premiereDate" id="premiereDate" class="input_data_picture" title="Дата премьеры" size="9"
                                                   value="<fmt:formatDate value="${film.premiereDate}" pattern="dd.MM.yyyy"/>"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3" class="label" >Жанр фильма:</td>
                                        <td colspan="11" >
                                            <br/>
                                            <table width="100%">
                                                <tr>
                                                    <c:set var="pos" value="1" />
                                                    <c:forEach var="s" items="${genres}">
                                                        <c:set var="isCh" value="0"  />
                                                        <c:forEach var="gen" items="${film.filmGenres}" >
                                                            <c:if test="${s.code eq gen.code}">
                                                                <c:set value="1" var="isCh" />
                                                            </c:if>
                                                        </c:forEach>
                                                        <td width="20%">
                                                            <label>
                                                                <input type="checkbox" name="filmGenres[]" id="<c:out value="${s.code}" />"
                                                                    value="<c:out value="${s.code}" />"
                                                                    <c:if test="${isCh eq 1}">checked</c:if>/>&nbsp;<c:out value="${s.name}" />
                                                            </label>
                                                        </td>
                                                        <c:set var="pos" value="${pos+1}"/>
                                                        <c:if test="${pos gt 4}">
                                                            <c:set var="pos" value="1"  />
                                                            </tr>
                                                            <tr>
                                                        </c:if>
                                                    </c:forEach>
                                                </tr>
                                            </table>
                                            <br/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3" class="label" >Режисер:</td>
                                        <td colspan="17" >
                                            <input name="filmDatailDirector" id='filmDatailDirector' class="f_input" value="<c:out value="${film.filmDetail.director}"/>" title="Режисер"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3" class="label" >В главных ролях:</td>
                                        <td colspan="17" >
                                            <input name="filmDatailCast" id='filmDatailCast' class="f_input" value="<c:out value="${film.filmDetail.cast}"/>" title="В главных ролях"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3" class="label" >Описание фильма:</td>
                                        <td colspan="17" >
                                            <textarea id="filmNotes" name="filmNotes" rows="7" class="f_textarea" title="Описание фильма"><c:out value="${film.notes}"/></textarea>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="20" align="center" >
                                            <div class="fieldsubmit"><br><br>
                                                <button name="submit" class="fbutton"><span>Отправить</span></button>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width="5%" ></td>
                                        <td width="5%" ></td>
                                        <td width="5%" ></td>
                                        <td width="5%" ></td>
                                        <td width="5%" ></td>
                                        <td width="5%" ></td>
                                        <td width="5%" ></td>
                                        <td width="5%" ></td>
                                        <td width="5%" ></td>
                                        <td width="5%" ></td>
                                        <td width="5%" ></td>
                                        <td width="5%" ></td>
                                        <td width="5%" ></td>
                                        <td width="5%" ></td>
                                        <td width="5%" ></td>
                                        <td width="5%" ></td>
                                        <td width="5%" ></td>
                                        <td width="5%" ></td>
                                        <td width="5%" ></td>
                                        <td width="5%" ></td>
                                    </tr>
                                </table>
                            </div>
                        </form>
                    </div>
                </div>
                <span class="leftblok_contener">
                    <div style="padding: 7px 20px; position: relative; border-bottom: 1px solid #3f413f;">
                        <i style="font-size:15px;"> Панель навигации</i>
                    </div>
                    <span class="leftblok_contener2">
                        <div class="leftblok1">
                            <div class="miniblock">
                                <div class="mini" style="border-top:0; padding-top:0;">
                                    <div class="film_category">
                                        <a href="<c:url value="/addGenre"/>" >Добавить новый жанр</a><br>
                                        <a href="<c:url value="/addYear"/>" >Добавить новый год</a><br>
                                        <a href="<c:url value="/addCountry"/>" >Добавить новую страну</a><br>
                                        <a href="<c:url value="/admin/addFilm"/>" >Добавить новый фильм</a><br>
                                    </div>
                                </div>
                                <div style="padding-top:20px;"></div><!--div-->
                            </div><!--miniblock-->
                        </div><!--leftblok1-->
                    </span><!--leftblok_contener2-->
                </span>
            </div>
        </div>

    </body>
</html>

<script>
    $(function() {
        $('.input_data_picture').datepicker({
            dayNamesMin: ['Вс', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб'],
            monthNames: ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь', 'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'],
            firstDay: 1,
            dateFormat: 'dd.mm.yy'
        });
    });

    $('#addPosterFile').on('change', function(){
        var posterfile = this.files[0];
        var oldFileName = $('#posterFileName').val();
        uploadPostrFile(posterfile, oldFileName);
    });

    $('#addFilmVideo').on('change', function(){
        var videofile = this.files[0];
        var oldVideoFileName = $('#filmVideoFileName').val();
        uploadVideoFile(videofile, oldVideoFileName);
    });

    $('#uloadFiles').on('change', function(){
        var imagefiles = this.files;
        uploadImageFiles(imagefiles);
    });



    function uploadPostrFile(fileData, oldFileName) {
        var data = new FormData();
        data.append("posterFile", fileData);
        $("#posterFile").attr("src", "resources/img/no-image-icon-10.png");
        $("#posterFilePath").val("resources/img/no-image-icon-10.png");
        $("#posterFilePathShow").val("resources/img/no-image-icon-10.png");
        $("#posterFileName").val("");

        if (fileData === undefined) {
            if (oldFileName.length > 0) {
                $.ajax({
                    url: "/deleteFile",
                    type: "POST",
                    dataType: "json",
                    data: {
                        fileName: oldFileName
                    }
                });
            }
        } else {
            $.ajax({
                url: "/uploadPosterFile",
                type: "POST",
                enctype: 'multipart/form-data',
                dataType: 'json',
                data: data,
                processData: false,
                contentType: false,
                cache: false,
                success: function (responseData) {
                    alert(responseData);
                    alert(JSON.stringify(responseData));
                    if (responseData.result) {
                        alert('res.path: '+responseData.path);
                        alert('res.name: '+responseData.fileName);
                        alert('res.message: '+responseData.message);
                        $("#posterFile").attr("src", responseData.path);
                        $("#posterFilePath").val(responseData.path);
                        $("#posterFilePathShow").val(responseData.path);
                        $("#posterFileName").val(responseData.fileName);
                        if (oldFileName.length > 0) {
                            $.ajax({
                                url: "/deleteFile",
                                type: "POST",
                                dataType: "json",
                                data: {
                                    "fileName": oldFileName
                                }
                            });
                        }
                    } else {
                        alert('res.path: '+responseData.path);
                        alert('res.name: '+responseData.fileName);
                        alert('res.message: '+responseData.message);
                        if ((!(responseData.message === null)) && (!(responseData.message === undefined))) {
                            alert(responseData.message);
                        }
                    }
                },
                error: function () {
                    alert('ОШИБКА ОТВЕТА СЕРВЕРА!');
                }
            });
        }
    }

    function uploadImageFiles(filesData) {
        var data = new FormData();
        for (var i = 0; i < filesData.length; i++) {
            data.append("imageFiles", filesData[i]);
        }
        if ((!(filesData === undefined)) && (filesData.length > 0)) {
            $.ajax({
                url: "/uploadImageFiles",
                type: "POST",
                enctype: 'multipart/form-data',
                data: data,
                processData: false,
                contentType: false,
                cache: false,
                success: function (responseData) {
                    if (responseData.result) {
                        $("#screenShoots").val(responseData.filesPathes);
                    } else {
                        alert(responseData.message);
                    }
                },
                error: function () {
                    alert('ОШИБКА ОТВЕТА СЕРВЕРА!');
                }
            });
        }
    }

    function uploadVideoFile(fileData, oldFileName) {
        var data = new FormData();
        data.append("videoFile", fileData);

        if (fileData === undefined) {
            if (oldFileName.length > 0) {
            }
            $("#filmPath").val("");
            $("#filmVideoFileName").val("");
        } else {
            $(".uploadVideo").show();
            $("#filmPath").hide();
            $("#filmVideoFileName").hide();
            $('#addFilmVideo').hide();
            $.ajax({
                url: "/uploadVideoFile",
                type: "POST",
                enctype: 'multipart/form-data',
                data: data,
                processData: false,
                contentType: false,
                cache: false,
                success: function (responseData) {
                    if (responseData.result) {
                        $("#filmPath").val(responseData.path);
                        $("#filmVideoFileName").val(responseData.fileName);
                        $(".uploadVideo").hide();
                        $("#filmPath").show();
                        $("#filmVideoFileName").show();
                        $('#addFilmVideo').show();
                    } else {
                        if ((!(responseData.message === null)) && (!(responseData.message === undefined))) {
                            alert(responseData.message);
                            $(".uploadVideo").hide();
                            $("#filmPath").show();
                            $("#filmVideoFileName").show();
                            $('#addFilmVideo').show();
                        }
                    }
                },
                error: function () {
                    alert('ОШИБКА ОТВЕТА СЕРВЕРА!');
                    $(".uploadVideo").hide();
                    $("#filmPath").show();
                    $("#filmVideoFileName").show();
                    $('#addFilmVideo').show();
                }
            });
        }
    }
</script>
