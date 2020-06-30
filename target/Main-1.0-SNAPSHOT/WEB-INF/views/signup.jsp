<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
	<meta charset="utf-8">
	<!-- <link rel="stylesheet" type="text/css" href="css/idcheckresult.css"> -->
    <title> NIDS Air Quality Management System </title>
    <link href="/resources/css/jquery.datepicker.css" rel="stylesheet">
    <link href="/resources/css/idcheck.css" rel="stylesheet">
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600' rel='stylesheet' type='text/css'>
	<link href="http://netdna.bootstrapcdn.com/font-awesome/3.1.1/css/font-awesome.css" rel="stylesheet">
	<!-- <link rel="icon" type="image/png" href="images/icons/favicon.ico"/> -->
	<!-- <script type="text/javascript" src="/resources/js/jquery-3.3.1.min.js"></script> -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    
	<script type="text/javascript">	
		var exist_result = "";
        
		
		$(document).ready(function(){
			$(".input_id").focusin(function(){
				console.log("id focus in");
			});
			$(".input_id").focusout(function(){
				console.log("id focus out");
				chkIDAjax();
			});
			
			$(".input_id").keyup(function(event){ 
				if (!(event.keyCode >=37 && event.keyCode<=40)) {
					var inputVal = $(this).val();
					$(this).val(inputVal.replace(/[^a-z0-9]/gi,''));
				}
			});
		});
        
        function AddrPopUp(){
            var pop = window.open("/JusoPopup","pop","width=570,height=420, scrollbars=yes, resizable=yes");
            //** 2017년 5월 모바일용 팝업 API 기능 추가제공 **/
            // 모바일 웹인 경우, 호출된 페이지(jusopopup.jsp)에서
            // 실제 주소검색 URL(http://www.juso.go.kr/addrlink/addrMobileLinkUrl.do)를 호출하게 됩니다.
            // var pop = window.open("/popup/jusoPopup.jsp","pop","scrollbars=yes, resizable=yes");
        }
        
        function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn , detBdNmList, bdNm, bdKdcd, siNm, sggNm, emdNm, liNm, rn, udrtYn, buldMnnm, buldSlno, mtYn, lnbrMnnm, lnbrSlno, emdNo){
            // 2017년 2월 제공항목이 추가되었습니다. 원하시는 항목을 추가하여 사용하시면 됩니다.
            var full_addr = roadFullAddr;
            var road_addr_p1 = roadAddrPart1;
            var road_addr_p2 = roadAddrPart2;
            var addr_detail = addrDetail;
            var zip_num = zipNo;
            
            var user_admCd = admCd;
            var user_rnMgtSn = rnMgtSn;
            var user_udrtYn = udrtYn;
            var user_buldMnnm = buldMnnm;
            var user_buldSlno = buldSlno;
            
            console.log(user_admCd);
            console.log(user_rnMgtSn);
            console.log(user_udrtYn);
            console.log(user_buldMnnm);
            console.log(user_buldSlno);
            
            $.ajax({
                type:"POST",
                url:"/UserUtil",
                data: "type=position&admCd="+user_admCd+"&rnMgtSn="+user_rnMgtSn+"&udrtYn="+user_udrtYn+"&buldMnnm="+user_buldMnnm+"&buldSlno="+user_buldSlno,
                datatype:"json",
                success: function(data) {
                    console.log(data);
                    if(data.result == true){
                        var json_obj_position = JSON.parse(data.data);
                        $('#p_tmX').val(json_obj_position.results.juso[0].entX);
                        $('#p_tmY').val(json_obj_position.results.juso[0].entY);
                    }
                },
                error: function(e) {
                    console.log(e);
                }			
            }); 
            
            $('#p_addr1').val(zip_num);
            $('#p_addr2').val(road_addr_p1+" "+road_addr_p2);
            $('#p_addr3').val(addr_detail);
        }
        
		
		function chkEmptyInput(){
			
			var reg_email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i; 
			var reg_phone = /^\d{3}-\d{3,4}-\d{4}$/;
			var reg_id = /^[a-z0-9_]{4,20}$/; 
			
			var txt_id = $(".input_id").val();
			var txt_pw = $(".input_pw").val();
			var txt_name = $(".input_name").val();
			var txt_phone = $(".input_phone").val();
			
			
			if(!reg_id.test(txt_id)){
				alert("잘못된 아이디 형식입니다. 영문 및 숫자로만 조합가능합니다.");
				$(".input_id").focus();
				return false;
			}
			
			if(!/^[a-zA-Z0-9]{8,20}$/.test(txt_pw)){ 
				alert('비밀번호는 숫자와 영문자 조합으로 8~20자리를 사용해야 합니다.'); 
				return false;
			}
			
			if(txt_name == ""){
				alert("이름을 입력해주세요.");
				$(".input_name").focus();
				return false;
			}
			if(!reg_phone.test(txt_phone)){
				alert("잘못된 휴대전화번호 형식입니다. 010-****-****의 형식으로 입력해주세요.");
				$(".input_phone").focus();
				return false;
			}
			
			return true;
		}
		
		function chkIDAjax(){
	        var dataString = $("form").serialize().replace(/%/g, '%25'); //파라메터 직렬화
    	    console.log("parameter:"+dataString+"&type=check_exist"); 
	        $.ajax({
				type: "POST",
				url : "/UserUtil",
				async: true,
				data: dataString+"&type=check_exist", //파라메터
				success: whenSuccess, //성공시 callback
				error: whenError //실패시 callback
      		});
            document.getElementById("idcheck_exist").style.display = "none";
            document.getElementById("idcheck_noexist").style.display = "none";
			document.getElementById("idchecking").style.display = "inline-block";
    	}
		
		 function whenSuccess(resdata){
            document.getElementById("idchecking").style.display = "none";
			if(resdata.exist){
				//id exist
				console.log("exist");
				exist_result = "exist"
                document.getElementById("idcheck_exist").style.display = "inline-block";
                document.getElementById("idcheck_noexist").style.display = "none";
			}
			else{
				console.log("can insert");
				exist_result = "can insert";
                document.getElementById("idcheck_exist").style.display = "none";
                document.getElementById("idcheck_noexist").style.display = "inline-block";
			}
			
    	}
		
		function InsertSuccess(resdata){
			if(resdata.insert){
				alert('회원가입이 완료되었습니다. 로그인페이지로 이동합니다.');
				location.replace("/");
			}
			else{
				alert('회원가입 실패');
			}
		}
		
		function whenError(){
        	console.log("ajax error");
			document.getElementById("idchecking").style.display = "none";
    	}
		
		function btnClick(){
			var chk_result = chkEmptyInput();
			
			if(chk_result == true)
			{
				if(exist_result == "exist"){
				alert('이미 존재하는 아이디입니다.');
				}
				else if(exist_result == ""){
					alert('아이디 중복체크가 완료되지 않았습니다.');
				}
				else{
					var dataString = $("form").serialize(); //파라메터 직렬화 

					$.ajax({
						type: "POST",
						url : "/UserUtil",
						async:false,
						data: dataString+"&type=signup", //파라메터
						success: InsertSuccess, //성공시 callback
						error: whenError //실패시 callback
					});	
				}
			}	
		}
		
	</script>
</head>
<style>
	body, div, dl, dt, dd, ul, ol, li, h1, h2, h3, h4, h5, h6, 
	pre, form, fieldset, input, textarea, p, blockquote, th, td { 
	  padding:0;
	  margin:0;}

	fieldset, img {border:0}

	ol, ul, li {list-style:none}

	:focus {outline:none}

	body,
	input,
	textarea,
	select {
	  font-family: 'Open Sans', sans-serif;
	  font-size: 16px;
	  color: #4c4c4c;
	}

	p {
	  font-size: 12px;
	  width: 150px;
	  display: inline-block;
	  margin-left: 18px;
	}
	h1 {
	  font-size: 32px;
	  font-weight: 300;
	  color: #4c4c4c;
	  text-align: center;
	  padding-top: 10px;
	  margin-bottom: 10px;
	}

	html{
	  background-color: #ffffff;
	}

	.testbox {
	  margin: 20px auto;
	  width: 500px; 
	  height: 664px; 
	  -webkit-border-radius: 8px/7px; 
	  -moz-border-radius: 8px/7px; 
	  border-radius: 8px/7px; 
	  background-color: #ebebeb; 
	  -webkit-box-shadow: 1px 2px 5px rgba(0,0,0,.31); 
	  -moz-box-shadow: 1px 2px 5px rgba(0,0,0,.31); 
	  box-shadow: 1px 2px 5px rgba(0,0,0,.31); 
	  border: solid 1px #cbc9c9;
	}

	input[type=radio] {
	  visibility: hidden;
	}

	form{
	  margin: 0 30px;
	}

	label.radio {
		cursor: pointer;
	  text-indent: 35px;
	  overflow: visible;
	  display: inline-block;
	  position: relative;
	  margin-bottom: 15px;
	}

	label.radio:before {
	  background: #3a57af;
	  content:'';
	  position: absolute;
	  top:2px;
	  left: 0;
	  width: 20px;
	  height: 20px;
	  border-radius: 100%;
	}

	label.radio:after {
		opacity: 0;
		content: '';
		position: absolute;
		width: 0.5em;
		height: 0.25em;
		background: transparent;
		top: 7.5px;
		left: 4.5px;
		border: 3px solid #ffffff;
		border-top: none;
		border-right: none;

		-webkit-transform: rotate(-45deg);
		-moz-transform: rotate(-45deg);
		-o-transform: rotate(-45deg);
		-ms-transform: rotate(-45deg);
		transform: rotate(-45deg);
	}

	input[type=radio]:checked + label:after {
		opacity: 1;
	}

	hr{
	  color: #a9a9a9;
	  opacity: 0.3;
	}

	input[type=text],input[type=password]{
	  height: 39px; 
	  -webkit-border-radius: 0px 4px 4px 0px/5px 5px 4px 4px; 
	  -moz-border-radius: 0px 4px 4px 0px/0px 0px 4px 4px; 
	  border-radius: 0px 4px 4px 0px/5px 5px 4px 4px; 
	  background-color: #fff; 
	  -webkit-box-shadow: 1px 2px 5px rgba(0,0,0,.09); 
	  -moz-box-shadow: 1px 2px 5px rgba(0,0,0,.09); 
	  box-shadow: 1px 2px 5px rgba(0,0,0,.09); 
	  border: solid 1px #cbc9c9;
	  margin-left: -5px;
	  margin-top: 13px; 
	  padding-left: 10px;
	}

	#icon {
	  display: inline-block;
	  width: 30px;
	  background-color: #3a57af;
	  padding: 8px 0px 8px 15px;
	  margin-left: 15px;
	  -webkit-border-radius: 4px 0px 0px 4px; 
	  -moz-border-radius: 4px 0px 0px 4px; 
	  border-radius: 4px 0px 0px 4px;
	  color: white;
	  -webkit-box-shadow: 1px 2px 5px rgba(0,0,0,.09);
	  -moz-box-shadow: 1px 2px 5px rgba(0,0,0,.09); 
	  box-shadow: 1px 2px 5px rgba(0,0,0,.09); 
	  border: solid 0px #cbc9c9;
	}

	.gender {
	  margin-left: 30px;
	  margin-bottom: 30px;
	  margin-left: 8px;
	  margin-top: 20px;
	}

	.accounttype{
	  margin-left: 8px;
	  margin-top: 20px;
	}

	a.button {
	  font-size: 14px;
	  font-weight: 600;
	  color: white;
	  padding: 10px 25px 0px 20px;
      margin-left : 10px;
	  display: inline-block;
	  text-align: center;
	  text-decoration: none;
	  width: 90px; height: 27px; 
	  -webkit-border-radius: 5px; 
	  -moz-border-radius: 5px; 
	  border-radius: 5px; 
	  background-color: #3a57af; 
	  -webkit-box-shadow: 0 3px rgba(58,87,175,.75); 
	  -moz-box-shadow: 0 3px rgba(58,87,175,.75); 
	  box-shadow: 0 3px rgba(58,87,175,.75);
	  transition: all 0.1s linear 0s; 
	  top: 0px;
	  position: relative;
	}

	a.button:hover {
	  top: 3px;
	  background-color:#2e458b;
	  -webkit-box-shadow: none; 
	  -moz-box-shadow: none; 
	  box-shadow: none;

	}

	.input_id{
		width: 300px;
	}
    .input_addr1{
        width: 200px;
    }
	.input_pw, .input_name, .input_email, .input_phone, .form-control, .input_addr2{
		width: 350px;
	}
	
	
	
	.container{
		display:inline-block;
		width:39px;
		height:39px;
		margin-top:18px;
		margin-left:8px;
		position:absolute;
		display:inline-block;
	}

	.loader {
	  -webkit-animation: spin 1s linear infinite;
			  animation: spin 1s linear infinite;
	  border: 3px solid #ddd;
	  border-top: 3px solid #42a5f5;
	  border-radius: 50%;
	  height: 25px;
	  width: 25px;
      position: absolute;
      display:none;
	}

	@-webkit-keyframes spin {
	  to {
		border-top-color: #ec407a;
		-webkit-transform: rotate(360deg);
				transform: rotate(360deg);
	  }
	}

	@keyframes spin {
	  to {
		border-top-color: #ec407a;
		-webkit-transform: rotate(360deg);
				transform: rotate(360deg);
	  }
	}

</style>
<body>


<div class="testbox">
	<h1>회원등록</h1>

	<form action="/">
		<hr>
		<label id="icon" for="name"><i class="icon-shield"></i></label>
		<input type="text" name="id" id="p_id" class="input_id" placeholder="ID" required/>
        <input type="hidden" name="tmX" id="p_tmX" />
        <input type="hidden" name="tmY" id="p_tmY" />
		<div class="container" id="container_id">
            <div class="loader" id="idchecking"></div>
            
            <svg version="1.1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 130.2 130.2" id="idcheck_noexist">
                <circle class="path circle" fill="none" stroke="#73AF55" stroke-width="6" stroke-miterlimit="10" cx="65.1" cy="65.1" r="62.1"/>
                <polyline class="path check" fill="none" stroke="#73AF55" stroke-width="6" stroke-linecap="round" stroke-miterlimit="10" points="100.2,40.2 51.5,88.8 29.8,67.5 "/>
            </svg>    
            
            <svg version="1.1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 130.2 130.2" id="idcheck_exist">
                <circle class="path circle" fill="none" stroke="#D06079" stroke-width="6" stroke-miterlimit="10" cx="65.1" cy="65.1" r="62.1"/>
                <line class="path line" fill="none" stroke="#D06079" stroke-width="6" stroke-linecap="round" stroke-miterlimit="10" x1="34.4" y1="37.9" x2="95.8" y2="92.3"/>
                <line class="path line" fill="none" stroke="#D06079" stroke-width="6" stroke-linecap="round" stroke-miterlimit="10" x1="95.8" y1="38" x2="34.4" y2="92.2"/>
            </svg>
		</div>
        <br>
		<label id="icon" for="name"><i class="icon-shield"></i></label>
		<input type="password" name="pw" id="p_pw" class="input_pw" placeholder="Password" required/>
        <br>
		<label id="icon" for="name"><i class="icon-user"></i></label>
		<input type="text" name="name" id="p_name" class="input_name" placeholder="Name" required/>
        <br>
        <label id="icon" for="name"><i class="icon-user"></i></label>
        <input type="text" class="form-control" name="birth" id="p_birth" data-select="datepicker" placeholder="Birth" required>
        <br>
		<label id="icon" for="name"><i class="icon-signal"></i></label>
		<input type="text" name="phone" id="p_phone" class="input_phone" placeholder="Phone" required/>
        <br>
		<label id="icon" for="name"><i class="icon-envelope"></i></label>
		<input type="text" name="addr1" id="p_addr1" class="input_addr1" placeholder="Zip Code" required readonly />
        <a href="javascript:;" class="button" onClick="AddrPopUp()">주소찾기</a>
        <br>
        <label id="icon" for="name"><i class="icon-envelope"></i></label>
		<input type="text" name="addr2" id="p_addr2" class="input_addr2" placeholder="Address" required readonly />
        <br>
        <label id="icon" for="name"><i class="icon-envelope"></i></label>
		<input type="text" name="addr3" id="p_addr3" class="input_addr2" placeholder="Address Detail" required readonly />
        <br>
		<div class="gender">
            <input type="radio" value="0" id="male" name="gender" checked/>
            <label for="male" class="radio" chec>남자</label>
            <input type="radio" value="1" id="female" name="gender" />
            <label for="female" class="radio">여자</label>
		</div>
        <br>
		<a href="javascript:;" class="button" onClick="btnClick()" style="width:80%;">Sign Up</a>
	</form>
</div>
    
    
    <script type="text/javascript" src="/resources/js/jquery.datepicker.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.datepicker.min.js"></script>
    
    
</body>
</html>