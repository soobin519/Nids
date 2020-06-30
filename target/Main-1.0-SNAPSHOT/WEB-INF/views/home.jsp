<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>
 
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">
	

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
    
  <!-- jQuery -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js" type="text/javascript" language="javascript"></script>
  <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.0/jquery-ui.min.js"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/css/bootstrap-datepicker3.min.css">
  <script type='text/javascript' src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/js/bootstrap-datepicker.min.js"></script>
  <script src="/resources/js/bootstrap-datepicker.kr.js" charset="UTF-8"></script>
  <script src="http://malsup.github.com/jquery.form.js"></script> 
  <script type="text/javascript">	
    
  var carnum = "<c:out value="${car_num}"/>";
  console.log(carnum+"여긴 home");    
      
  $(document).ready(function(){
      
       
      
			console.log("document ready called");
			$('#content_parent').children().remove();
				document.getElementById("content_parent").innerHTML='<object type="text/html" data="/Monitorin?num='+carnum+'" style="width:100%;height:570px;margin: 0 auto"></object>'+'<object type="text/html" data="/Monitor" style="width:100%;height:570px;margin: 0 auto"></object>';
          
		});	
      
   function pageChange(lnk){
			if(lnk=='dust'){
                document.getElementById("content_parent").innerHTML='<object type="text/html" data="/Monitorin?num='+carnum+'" style="width:100%;height:570px;margin: 0 auto"></object>'+'<object type="text/html" data="/Monitor" style="width:100%;height:570px;margin: 0 auto"></object>';
            }
			else if(lnk=='outdoor'){
				$('#content_parent').children().remove();
				document.getElementById("content_parent").innerHTML='<object type="text/html" data="/Outdoor" style="width:100%;height:1024px"></object>';
			}
            else if(lnk=='indoor'){
				$('#content_parent').children().remove();
				document.getElementById("content_parent").innerHTML='<object type="text/html" data="/Indoor?num='+carnum+'" style="width:100%;height:1024px"></object>';
			}
			// else if(lnk=='tvoc'){
			// 	$('#content_parent').children().remove();
			// 	document.getElementById("content_parent").innerHTML='<object type="text/html" data="/Tvoc" style="width:100%;height:1000px"></object>';
			// }
            else if(lnk=='map'){
				$('#content_parent').children().remove();
				document.getElementById("content_parent").innerHTML='<object type="text/html" data="/Map" style="width:100%;height:1000px"></object>';
			}
				
		}   

    </script>
  <title>Nids</title>

  <!-- Bootstrap core CSS -->
  <link href="/resources/bootstrap/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="/resources/bootstrap/css/shop-homepage.css" rel="stylesheet">

</head>

<body>

  <!-- Navigation -->
  <nav class="navbar navbar-expand-lg navbar-dark bg-custom fixed-top" style="background-color: #78A9ED">
    <div class="container">
      <a class="navbar-brand" href="#">Airvom</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item active">
            <a class="nav-link" href="javascript:;" onClick="pageChange('dust')">Home
              <span class="sr-only">(current)</span>
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="javascript:;" onClick="pageChange('outdoor')">Outdoor</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="javascript:;" onClick="pageChange('indoor')">Indoor</a>
          </li>
          <!-- <li class="nav-item">
            <a class="nav-link" href="javascript:;" onClick="pageChange('tvoc')">Tvoc</a>
          </li> -->
          <li class="nav-item">
            <a class="nav-link" href="javascript:;" onClick="pageChange('map')">Map</a> 
          </li>
        </ul>
      </div>
    </div>
  </nav>


        <!-- Page Content -->
        <div id="page-wrapper" style="padding:0px;background:#24303a; width: 100%">
            <div class="container-fluid  justify-content-between align-items-center" style="padding-right:0px; padding-left:0px;">
                <div class="row" style="margin:0px;">
                    <div class="col-sm-12" id="content_parent" style="padding:0px; width:100%; margin: 0 auto">
                        
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->    


    

  <!-- Footer -->
  <footer class="py-5 bg-custom" style="background-color: #78A9ED; width:100%">
    <div class="container"  style="background-color: #78A9ED; width:100%">
      <p class="m-0 text-center text-white">Copyright &copy; NIDS 2020</p>
    </div>
    <!-- /.container -->
  </footer>

  <!-- Bootstrap core JavaScript -->
  <script src="/resources/bootstrap/vendor/jquery/jquery.min.js"></script>
  <script src="/resources/bootstrap/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

</body>

</html>
