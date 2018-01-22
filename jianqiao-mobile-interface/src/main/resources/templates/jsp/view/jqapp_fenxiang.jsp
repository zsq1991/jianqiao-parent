<%@ page pageEncoding="UTF-8"%>
<%@ include file="commonPublic.jsp"%>
<%-- ${helpdetails}
${imgList}
${consultationDetail}
 --%>

<head>
	<meta charset="UTF-8">
	<title>分享详情</title>
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen"> 
	<meta content="telephone=no,email=no" name="format-detection">
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache"> 
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"> 
	<META HTTP-EQUIV="Expires" CONTENT="0"> 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/qiuzhu.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jqapp.js"></script>
	<style>
		.comment_item .comment_content {
			color:#000;
		}
	</style>
</head>
<body>
<div id="body_box">
<!-- 头部 -->
<%-- <div id="header">
	<!-- <h2 class="title">分享详情</h2> -->
	<a class="sos" onclick="downloadApp('moreActions')">
		<img src="${pageContext.request.contextPath}/img/icon/sos.png" alt="">求助
	</a>
</div> --%>
<!-- 访谈部分 -->
<!-- <ul> -->
<div class="section">
	
	<div class="reply">
		<c:forEach items="${helpdetails}" var="help">
		<h3 class="title">${help.title}</h3>
		<div class="reply_header clearfix">
			<div class="reply_box lf">
				<div class="head lf">
					<a  onclick="downloadApp('moreInfo')">
					<c:if test="${empty help.nickname_address}">
					<img class="touxiang" src="${pageContext.request.contextPath}/img/icon/morentouxiang.jpg">
					</c:if>
					<c:if test="${not empty help.nickname_address}">
						<img class="touxiang" src="${ctx_ali}/${help.nickname_address}">
					</c:if>
					<c:if test="${help.userType ==1}">
						<span class="v">
							<img src="${pageContext.request.contextPath}/img/icon/v.png">
						</span>
					</c:if>
					</a>
				</div>
				<div class="core_reply lf">
					<h4>
						<span>${help.nickname}</span>
					</h4>
					<ul class="core_reply_tail">
						<li>
							<span>${help.createdTime}</span>
						</li>
					</ul>
				</div>
			</div>
			<div class="rt">
				<button class="attent_btn" onclick="downloadApp('moreInfo')">+关注</button>
			</div>
		</div>
		</c:forEach>
		
		<div class="reply_section">
			<div class="reply_img">
				<c:forEach items="${imgList}" var="img">
					<c:if test="${img.type == '0'}">
						<pre style="font-size:1.4rem;font-family:microsoft yahei;white-space: pre-wrap!important;
word-wrap: break-word!important">${img.detailContent}</pre>
					</c:if>
					<c:if test="${img.type == '2' }">
						<img class="pic3" style="width:100%" src="${img.address}">
					</c:if>
				</c:forEach>
			</div>
		</div>
		
	</div>
	
	<!-- <div class="all_replay"  onclick="downloadApp('moreInfo')">
		<h4>全部回答</h4>
	</div> -->
</div>
<div class="comment">
	
	<h3 class="title">用户评论（<span>${ftplNum.countnum }</span>条）</h3>
	
	<c:if test="${ftplNum.countnum >0}">
	<ul>
		<c:forEach items="${consultationDetail}" var="sccc" >
		<li class="comment_item">
			<div class="head lf">
				<a href="#">
					<%-- <img class="touxiang" src="${ctx_ali}/${sccc.useraddress}"> --%>
					<c:if test="${empty sccc.useraddress }">
					<img class="touxiang" src="${pageContext.request.contextPath}/img/icon/morentouxiang.jpg">
					</c:if><c:if test="${not empty sccc.useraddress }">
						<img class="touxiang" src="${ctx_ali}/${sccc.useraddress }">
					
					</c:if>
					<c:if test="${sccc.userType ==1}">
					<span class="v">
						<img src="${pageContext.request.contextPath}/img/icon/v.png">
					</span>
					</c:if>
				</a>
			</div>
			<div class="user">
				<div class="user_info clearfix" style="height:2.2rem">
					<a class="user_name lf" style="margin-left:0">${sccc.nickname }</a>
					<a class="zan rt" onclick="downloadApp('moreActions')">
						<span class="zan_num">${sccc.reply_num}</span>
					</a>
				</div>
				<div class="comment_content" style="margin-top:0;">${sccc.content}</div>
				<p>
					<span class="date">${sccc.createdTime }</span>
					<a class="answer active" onclick="downloadApp('moreActions')">${sccc.reply}回复</a>
				</p>
				 
				<div class="answer_box">
					 <ul>
					 	<c:forEach items="${sccc.replyDetail}" var="ssss" >
						<li>
							<a class="answer_user" href="${ctx_ali }/${ssss.address}">${ssss.nickname }：</a>
							<span>${ssss.content }</span>
						</li>
						
						</c:forEach>
						<c:if test="${sccc.reply > 2}">
							<li>
								<a  onclick="downloadApp('moreInfo')">查看全部${sccc.reply}条回复&gt;&gt;</a>
							</li> 
						</c:if>
					</ul> 
				</div>
				 
			</div>
		</li>
						</c:forEach>
		<li class="comment_item check_all"  onclick="downloadApp('moreInfo')">
			<h4>查看全部</h4>
		</li>
	</ul>
	</c:if>
</div>
<div class="footer">
	<form>
		<ul>
			<li>
				<input type="text" class="write_comment" placeholder="写评论…" readonly="readonly">
			</li>
			<li>
				<a class="pl"  onclick="downloadApp('moreActions')">
					<img src="${pageContext.request.contextPath}/img/icon/commont_normal.png" alt="">评论
				</a>
			</li>
			<li>
				<a class="shoucang"  onclick="downloadApp('moreActions')">
					<img src="${pageContext.request.contextPath}/img/icon/star.png" alt="">收藏
				</a>
			</li>
			<li>
				<a class="fenxiang"  onclick="downloadApp('moreActions')">
					<img src="${pageContext.request.contextPath}/img/icon/fenxiang.png" alt="">分享
				</a>
			</li>
			<li class="last">
				<a class="qiuzhu"  onclick="downloadApp('moreActions')">
					<img src="${pageContext.request.contextPath}/img/icon/sos.png" alt="">求助
				</a>
			</li>
		</ul>
	</form>
</div>
<div id="model">
	<span>下载健桥APP，进行更多操作！</span>
</div>
</div>
<!-- </ul> -->
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
			doc.addEventListener('DOMContentLoaded',false);
		})(window,document);
		
		
		/* function downloadApp(){
			//alert("点击确定跳转到下载页面!");
			setTimeout(
					function downloadApp1(){
					window.location.href ="${pageContext.request.contextPath}/mobile/view/picture/download";
					},2000
			);
		} */
		function downloadApp(argu){
			var model = document.getElementById("model");
			model.style.display = 'block';
			model.style.opacity = 1;
			var span = model.firstElementChild;
			span.className="active";
			if(argu=="moreInfo"){
				span.innerHTML="下载健桥APP，查看更多内容！"
			}
			else if(argu=="moreActions"){
				span.innerHTML="下载健桥APP，进行更多操作！"
			}
			setTimeout(function(){
				/* location.href="erweima.html" */
				window.location.href ="${pageContext.request.contextPath}/mobile/view/app/download";
			},2000)

		}
		function myAddClass(selector){
			var img = document.querySelectorAll(selector);
			var num = 0,len=img.length;
			if(len<=3){num=len}else{num=3}
			for(var i=0;i<len;i++){
				console.log(num);
				img[i].className="pic"+num;
			}
		};
		window.onload = function(){
			myAddClass(".reply_img img");
			myAddClass(".comment_img img");
			
			var answer_box = document.querySelectorAll(".answer_box");
			for (var i=0 ,len= answer_box.length ; i<len;i++){
				var answer_num = answer_box[i].querySelectorAll("ul li");
				console.log(answer_num)
				if(answer_num.length === 0){
					answer_box[i].style.display="none";
				}
			}
			
			var h= document.documentElement.clientHeight || document.body.clientHeight || windows.innerHeight ;
			var body_box = document.querySelector("#body_box");
			body_box.style.minHeight = h + "px";
		}
	</script> 
</body>
</html>
