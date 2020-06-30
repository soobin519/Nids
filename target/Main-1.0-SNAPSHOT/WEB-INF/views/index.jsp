<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title> NIDS Air Quality Management System </title>
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<spring:url value="/resources/vendor/bootstrap/css/bootstrap.min.css" var="bootstrapCss" />
	<spring:url value="/resources/fonts/font-awesome-4.7.0/css/font-awesome.min.css" var="awesomeCss" />
	<spring:url value="/resources/vendor/animate/animate.css" var="animateCss" />
	<spring:url value="/resources/vendor/css-hamburgers/hamburgers.min.css" var="hamburgersCss" />
	<spring:url value="/resources/vendor/select2/select2.min.css" var="select2Css" />
	<spring:url value="/resources/css/util.css" var="utilCss" />
	<spring:url value="/resources/css/main.css" var="mainCss" />

    <spring:url value="/resources/js/jquery-3.3.1.min.js" var="jquery331Js" />


	<spring:url value="/resources/vendor/jquery/jquery-3.2.1.min.js" var="jquery321Js" />
	<spring:url value="/resources/vendor/bootstrap/js/popper.js" var="popperJs" />
	<spring:url value="/resources/vendor/bootstrap/js/bootstrap.min.js" var="bootstrapJs" />
	<spring:url value="/resources/vendor/select2/select2.min.js" var="select2Js" />
	<spring:url value="/resources/vendor/tilt/tilt.jquery.min.js" var="tiltJs" />
	<spring:url value="/resources/js/main.js" var="mainJs" />
	<spring:url value="/resources/images/img-01.png" var="mainImg" />

	<link href="${bootstrapCss}" rel="stylesheet">
	<link href="${awesomeCss}" rel="stylesheet">
	<link href="${animateCss}" rel="stylesheet">
	<link href="${hamburgersCss}" rel="stylesheet">
	<link href="${select2Css}" rel="stylesheet">
	<link href="${utilCss}" rel="stylesheet">
	<link href="${mainCss}" rel="stylesheet">
    
	<script src="${jquery331Js}"></script>
    <script>
        $(function(){
            var responseMessage = "<c:out value="${message}" />";
            if(responseMessage == "E001" || responseMessage == "E002" ){
                alert('아이디 또는 비밀번호가 잘못입력되었습니다.');
                javascript:history.back();
            }
            else if(responseMessage == "E003"){
                alert('로그인 시 문제가 발생하였습니다.');
                javascript:history.back();
            }
                
        })
    </script>
	
</head>
<body>
	<div class="limiter">
		<div class="container-login100" style="display:table">
            <div style="display:table-cell; text-align:center; vertical-align:middle;">
                <div style="margin-bottom:20px;">
                <h1 style="color:white">
                    NIDS Clean Air
                </h1>    
                </div>
                
                <div class="wrap-login100" style="margin:auto;">
                    <div class="container-login-background"></div>
                    <div class="login100-pic js-tilt" data-tilt>
                        <img src="${mainImg}" alt="IMG">
                    </div>

                    <form class="login100-form validate-form" action="/UserLogin" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="hidden" name="type" value="login">
                        <span class="login100-form-title">
                            회원 로그인
                        </span>

                        <div class="wrap-input100 validate-input" data-validate = "아이디를 입력해주세요.">
                            <input class="input100" type="text" name="id" placeholder="ID">
                            <span class="focus-input100"></span>
                            <span class="symbol-input100">
                                <i class="fa fa-envelope" aria-hidden="true"></i>
                            </span>
                        </div>

                        <div class="wrap-input100 validate-input" data-validate = "비밀번호를 입력해주세요.">
                            <input class="input100" type="password" name="pw" placeholder="Password">
                            <span class="focus-input100"></span>
                            <span class="symbol-input100">
                                <i class="fa fa-lock" aria-hidden="true"></i>
                            </span>
                        </div>

                        <div class="container-login100-form-btn">
                            <button class="login100-form-btn" type="submit">
                                로그인
                            </button>
                        </div>

                        <div class="text-center p-t-12" style="display: none;">
                            </span>
                            <a class="txt2" href="javascript:;">
                                아이디 / 패스워드 분실
                            </a>
                        </div>

                        <div class="text-center p-t-136">
                            <a class="txt2" href="/SignUp">
                                새 계정 만들기
                                <i class="fa fa-long-arrow-right m-l-5" aria-hidden="true"></i>
                            </a>
                        </div>
                    </form>

                </div>
            </div>
		</div>
	</div>
	
	
	<script src="${jquery321Js}"></script>
	<script src="${popperJs}"></script>
	<script src="${bootstrapJs}"></script>
	<script src="${select2Js}"></script>
	<script src="${tiltJs}"></script>
	

	<script >
		$('.js-tilt').tilt({
			scale: 1.1
		})
	</script>
	<script src="${mainJs}"></script>

</body>
</html>