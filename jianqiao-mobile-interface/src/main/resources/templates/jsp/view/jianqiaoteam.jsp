<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="img_service" value="http://yst-images.img-cn-hangzhou.aliyuncs.com" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>Document</title>
	<link type="text/css" href="${pageContext.request.contextPath}/css/team_1.css" rel="stylesheet">
	<link type="text/css" href="${pageContext.request.contextPath}/css/base.css" rel="stylesheet">
	<link type="text/css" href="${ctx }/css/swipeslider/swipeslider.css"  rel="stylesheet" />
	<style>
		*{
			box-sizing:border-box;
		}
	</style>
</head>
<body>
	<div class="jieshao">
		<div id="full_feature" class="swipslider">
			<ul class="sw-slides">
				<li class="sw-slide">
					<img src="${pageContext.request.contextPath}/img/team_02.jpg">
				</li>
				<li class="sw-slide">
					<img src="${pageContext.request.contextPath}/img/team_02.jpg">
				</li>
				<li class="sw-slide">
					<img src="${pageContext.request.contextPath}/img/team_02.jpg">
				</li>
				<li class="sw-slide">
					<img src="${pageContext.request.contextPath}/img/team_02.jpg">
				</li>
			</ul>
		</div>
		<%--<img class="jieshaotu" src="${pageContext.request.contextPath}/img/team_02.jpg">--%>
		<p class="jieshao_1">由三甲医院工作背景名医及副主任医师领街的资深专家组成，提供权威在线咨询和健康管理服务。</p>
		<p class="jieshao_2"></p>
	</div>
	<div class="teamren">
		<div class="teamren_1">
			 <span>权威专家团队</span>
			<a style="color:#2a90d7;float: right" href="javascript:void(0)" id="more_btn" onclick="load_more(this,'1')">更多</a>
		</div>
		<div id="specialist_team" class="teamren_2 clearfix">
			<c:forEach items="${specialistItem}" var="specialist">
				<div class="ren_1">
					<div class="ren_2">
						<c:if test="${empty specialist.head_url && specialist.sex == 1}">
							<img onclick="specialistParticulars(${specialist.id})" class="ren_img" src="${pageContext.request.contextPath}/img/default/touxiang3.png">
						</c:if>
						<c:if test="${empty specialist.head_url && specialist.sex == 0}">
							<img onclick="specialistParticulars(${specialist.id})" class="ren_img" src="${pageContext.request.contextPath}/img/default/touxiang4.png">
						</c:if>
						<c:if test="${!empty specialist.head_url}">
							<img onclick="specialistParticulars(${specialist.id})" class="ren_img" src="${img_service}/${specialist.head_url}">
						</c:if>
					</div>
					<p class="ren_3">${specialist.name}</p>
					<p class="ren_4">${specialist.office}</p>
					<p class="ren_5">${specialist.position}</p>
				</div>
			</c:forEach>
		</div>

	</div>
<div class="teamren">

		<div class="teamren_1">
			<span class="team_name">民间高手团队</span>
			<a style="color:#2a90d7;float: right" href="javascript:void(0)" id="more_gaoshou" onclick="load_more(this,'2')">更多</a>
		</div>

	<div id="doctor_team" class="gaoshouren_2 clearfix">
		<c:forEach items="${doctorItem}" var="doctor">
			<div class="ren_1">
				<c:if test="${empty doctor.head_url && doctor.sex == 1}">
					<div class="ren_2"><img onclick="Particulars(${doctor.id})" class="ren_img" src="${pageContext.request.contextPath}/img/default/touxiang3.png"></div>
				</c:if>
				<c:if test="${empty doctor.head_url && doctor.sex == 0}">
					<div class="ren_2"><img onclick="Particulars(${doctor.id})" class="ren_img" src="${pageContext.request.contextPath}/img/default/touxiang4.png"></div>
				</c:if>
				<c:if test="${!empty doctor.head_url}">
					<div class="ren_2"><img onclick="Particulars(${doctor.id})" class="ren_img" src="${img_service}/${doctor.head_url}"></div>
				</c:if>
				<p class="ren_3">${doctor.name}</p>
				<p class="ren_4">${doctor.doctorAddress}</p>
				<p class="ren_5">${doctor.pacageName}</p>
			</div>
		</c:forEach>
	</div>

</div>
<div class="danwei">
	<div class="danwei_1">合作支持单位</div>
	<div class="danwei_4">
		<div class="danwei_2 clearfix">
			<div class="danwei_3">
				<img src="${pageContext.request.contextPath}/img/team_18.jpg">
			</div>
			<div  class="danwei_3">
				<img src="${pageContext.request.contextPath}/img/team_20.jpg">
			</div>
			<div  class="danwei_3">
				<img src="${pageContext.request.contextPath}/img/team_18.jpg">
			</div>
			<div  class="danwei_3">
				<img src="${pageContext.request.contextPath}/img/team_20.jpg">
			</div>
		</div>
	</div>
</div>



<script src="${ctx }/css/jquery.min.js"></script>

<script src="${ctx }/css/swipeslider/swipeslider.min.js"></script>
<script>

	(function(win,doc){
		var resize='orientationchange' in window?'orientationchange':'resize';

		function change(){
			var html=doc.documentElement;
			var deviceWidth = html.clientWidth;
			html.style.fontSize=deviceWidth/36+'px';
		}
		win.addEventListener(resize,change,false);
		win.addEventListener('load',change,false);
		doc.addEventListener('DOMContentLoaded',change,false);
	})(window,document);


	//高手详情
	function Particulars(lase){
		var ua = navigator.userAgent.toLowerCase();
		if (/iphone|ipad|ipod/.test(ua)) {
			window.webkit.messageHandlers.postDetailCarID.postMessage({id: lase});
		} else if (/android/.test(ua)) {
			var result = lase;
			window.stub.jsMethod(result);//用接口stub, 通过调用内部类中的方法jsMethod给java传回result。
		}
	}

	//专家详情
	function specialistParticulars(lase){
		var ua = navigator.userAgent.toLowerCase();
		if (/iphone|ipad|ipod/.test(ua)) {
			window.webkit.messageHandlers.postDetailCarIDs.postMessage({id: lase});
		} else if (/android/.test(ua)) {
			var result = lase;
			window.stub.jsMethods(result);//用接口stub, 通过调用内部类中的方法jsMethod给java传回result。
		}
	}


	$(".teamren_2 .ren_1:gt(5)").hide()
	$(".gaoshouren_2 .ren_1:gt(5)").hide()
	function load_more(self,utype){
		if($(self).hasClass("hide_more")){
			$(self).html("更多").removeClass("hide_more")
			$(self).parent().next().find(".ren_1:gt(5)").hide()
		}else{
			$(self).html("收起").addClass("hide_more")

			/**
			 * 加载数据
			 */
			$.ajax({
				type : 'POST',
				data: {"utype":utype},
				url : '${ctx}/mobile/view/specialist/moreDoctorSpecialist',
				success : function(result) {
					var sc = eval("(" + result + ")");
					if(sc.code == 1){
						if(utype == "1"){
							$.each(sc.content, function(i, specialist) {

								var imgstr = "";
								if(specialist.head_url==null && specialist.sex == 1){
									imgstr = '<img onclick="specialistParticulars('+specialist.id+')" ' +
											'class="ren_img" src="${pageContext.request.contextPath}/img/default/touxiang3.png">';
								}
								if(specialist.head_url==null && specialist.sex == 0){
									imgstr = '<img onclick="specialistParticulars('+specialist.id+')" ' +
											'class="ren_img" src="${pageContext.request.contextPath}/img/default/touxiang4.png">';
								}
								if(specialist.head_url !=null){
									imgstr = '<img onclick="specialistParticulars('+specialist.id+')" ' +
											'class="ren_img" src="${img_service}/'+specialist.head_url+'">';
								}

								$("#specialist_team").append(
								'<div class="ren_1">' +
								'<div class="ren_2">' +
								imgstr +
								'</div><p class="ren_3">'+specialist.name + '</p>'+
								'<p class="ren_4">'+specialist.office+ '</p>'+
								'<p class="ren_5">'+specialist.position + '</p></div>'
								);
							});
						}else if(utype == "2"){
							$.each(sc.content, function(i, doctor) {
								var imgstr = "";
								if(doctor.head_url==null && doctor.sex == 1){
									imgstr = '<img onclick="Particulars('+doctor.id+')" ' +
											'class="ren_img" src="${pageContext.request.contextPath}/img/default/touxiang3.png">';
								}
								if(doctor.head_url==null && doctor.sex == 0){
									imgstr = '<img onclick="Particulars('+doctor.id+')" ' +
											'class="ren_img" src="${pageContext.request.contextPath}/img/default/touxiang4.png">';
								}
								if(doctor.head_url !=null){
									imgstr = '<img onclick="Particulars('+doctor.id+')" ' +
									'class="ren_img" src="${img_service}/'+doctor.head_url+'">';
								}
								$("#doctor_team").append(
										'<div class="ren_1">' +
										'<div class="ren_2">' +
										imgstr +
										'</div><p class="ren_3">'+doctor.name + '</p>'+
										'<p class="ren_4">'+doctor.doctorAddress+ '</p>'+
										'<p class="ren_5">'+doctor.pacageName + '</p></div>'
								);
							});
						}

					}
				}
			});
			//$(self).parent().prev().find(".ren_1:gt(5)").show()
		}

	}
	$(window).load(function() {
		$('#full_feature').swipeslider();
	})
</script>
	<script>
;(function(win,doc){
	var resize='orientationchange' in window?'orientationchange':'resize';
	
	function change(){
		var html=doc.documentElement;
  var deviceWidth = html.clientWidth;
  
		  html.style.fontSize=deviceWidth/36+'px';
 }
	win.addEventListener(resize,change,false);
	win.addEventListener('load',change,false);
	doc.addEventListener('DOMContentLoaded',false);
})(window,document);
	</script>

</body>
</html>