<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head> <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
request.setCharacterEncoding("UTF-8");
//request.setCharacterEncoding("EUC-KR");

String inputYn = request.getParameter("inputYn");
String roadFullAddr = request.getParameter("roadFullAddr");
String roadAddrPart1 = request.getParameter("roadAddrPart1");
String roadAddrPart2 = request.getParameter("roadAddrPart2");
String engAddr = request.getParameter("engAddr");
String jibunAddr = request.getParameter("jibunAddr");
String zipNo = request.getParameter("zipNo");
String addrDetail = request.getParameter("addrDetail");
String admCd = request.getParameter("admCd");
String rnMgtSn = request.getParameter("rnMgtSn");
String bdMgtSn = request.getParameter("bdMgtSn");
String detBdNmList = request.getParameter("detBdNmList");
//** 2017년 2월 추가제공 **/
String bdNm = request.getParameter("bdNm");
String bdKdcd = request.getParameter("bdKdcd");
String siNm = request.getParameter("siNm");
String sggNm = request.getParameter("sggNm");
String emdNm = request.getParameter("emdNm");
String liNm = request.getParameter("liNm");
String rn = request.getParameter("rn");
String udrtYn = request.getParameter("udrtYn");
String buldMnnm = request.getParameter("buldMnnm");
String buldSlno = request.getParameter("buldSlno");
String mtYn = request.getParameter("mtYn");
String lnbrMnnm = request.getParameter("lnbrMnnm");
String lnbrSlno = request.getParameter("lnbrSlno");
String emdNo = request.getParameter("emdNo");
%>
    <script language="javascript">
    //document.domain = "abc.go.kr";
        function init()
        {
            var url = location.href;
            var confmKey = "U01TX0FVVEgyMDE4MTIxODE3MzI0MDEwODM4Nzk="; // 연계싞청시 부여받은 승인키 입력(테스트용 승인키 : TESTJUSOGOKR)
            var resultType = "4"; // 도로명주소 검색결과 화면 출력유형,
            // 1 : 도로명, 2 : 도로명+지번, 3 : 도로명+상세건물명, 4 : 도로명+지번+상세건물명
            var inputYn= "<%=inputYn%>";
            if(inputYn != "Y"){
                document.form.confmKey.value = confmKey;
                document.form.returnUrl.value = url;
                document.form.resultType.value = resultType;
                document.form.action="http://www.juso.go.kr/addrlink/addrLinkUrl.do";
                //** 2017년 5월 모바일용 팝업 API 기능 추가제공 **/
                //document.form.action="http://www.juso.go.kr/addrlink/addrMobileLinkUrl.do"; //모바일 웹인 경우, 인터넷망
                document.form.submit();
            }
            else{
                opener.jusoCallBack("<%=roadFullAddr%>","<%=roadAddrPart1%>","<%=addrDetail%>","<%=roadAddrPart2%>", "<%=engAddr%>", "<%=jibunAddr%>","<%=zipNo%>","<%=admCd%>","<%=rnMgtSn%>","<%=bdMgtSn%>", "<%=detBdNmList%>","<%=bdNm%>","<%=bdKdcd%>", "<%=siNm%>","<%=sggNm%>","<%=emdNm%>", "<%=liNm%>","<%=rn%>","<%=udrtYn%>","<%=buldMnnm%>","<%=buldSlno%>","<%=mtYn%>", "<%=lnbrMnnm%>","<%=lnbrSlno%>","<%=emdNo%>");
                window.close();
            }
        }
    </script>
</head>

<body onload="init();">
<form id="form" name="form" method="POST">
    <input type="hidden" id="confmKey" name="confmKey" value=""/>
    <input type="hidden" id="returnUrl" name="returnUrl" value=""/>
    <input type="hidden" id="resultType" name="resultType " value=""/>
</form>
</body>
</html>