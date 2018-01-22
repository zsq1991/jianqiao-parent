<%@ page pageEncoding="UTF-8"%>
<%@ include file="commonPublic.jsp"%>
<head>
	<meta charset="UTF-8">
	<title>求助详情</title>
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="yes" name="apple-touch-fullscreen"> 
	<meta content="telephone=no,email=no" name="format-detection">
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache"> 
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"> 
	<META HTTP-EQUIV="Expires" CONTENT="0"> 
	<link rel="stylesheet" href="${ctx}/css/base.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/css/qiuzhu.css">
	<style>
		.comment_img img.pic3,.reply_img img.pic3{
			padding:0 0 0.5rem 0.5rem;
			width:33.33%;
		}
	</style>
</head>
<body>
<div id="body_box">
<!-- 头部 -->
<%-- <div id="header">
	<h2 class="title">求助详情</h2>
	<a class="sos" onclick="downloadApp('moreActions')">
		<img src="${ctx}/img/icon/sos.png" alt="">求助
	</a>
</div> --%>
<!-- 访谈部分 -->
<!-- <ul> -->
<div class="section">
	<div class="reply">
		<h3 class="title">${helpAuthor.title  }</h3>
		<div class="reply_header clearfix">
			<div class="reply_box lf">
				<div class="head lf">
					<a  onclick="downloadApp('moreInfo')">
						<%-- <img class="touxiang" src="${ctx_ali}/${helpAuthor.address}"> --%>
						<c:if test="${empty helpAuthor.address }">
					<img class="touxiang" src="${pageContext.request.contextPath}/img/icon/morentouxiang.jpg">
					</c:if>.<c:if test="${not empty helpAuthor.address }">
						<img class="touxiang" src="${ctx_ali}/${helpAuthor.address }">
					
					</c:if>
					<c:if test="${helpAuthor.userType ==1}">
						<span class="v">
							<img src="${ctx}/img/icon/v.png">
						</span>
					</c:if>
					</a>
				</div>
				<div class="core_reply lf">
					<h4>
						<span>${helpAuthor.nickname }</span>
					</h4>
					<ul class="core_reply_tail">
						<li><span>${helpAuthor.num }</span>回答</li>
						<li><span>${helpAuthor.collectNum }</span>收藏</li>
						<li>
							<span>${helpAuthor.createdTime }</span>
						</li>
					</ul>
				</div>
			</div>
			<div class="rt">
				<button class="attent_btn" onclick="downloadApp('moreInfo')">+关注</button>
			</div>
		</div>
		<div class="reply_section">
			<c:forEach items="${imageList}" var="sc">
			<c:if test="${not empty sc.detailContent }">
			<p>${sc.detailContent }</p>
			</c:if>
			<div class="reply_img">
					<%-- <img class="pic3" src="${ctx_ali}/${sc.address}"> --%>
					<c:if test="${not empty sc.address }">
						<img class="touxiang" src="${ sc.address }">
					
					</c:if>
			</div>
				</c:forEach>
			
		</div>
	</div>
	<div class="all_replay"  onclick="downloadApp('moreInfo')">
		<h4>全部回答</h4>
	</div>
</div>
<div class="comment">
	<ul>
	<c:forEach items="${authoruserList}" var="user">
		
		<li class="comment_item">
			<div class="user_info clearfix">
				<div class="head lf">
					<a  onclick="downloadApp('moreInfo')">
					
						<%-- <img class="touxiang" src="${ctx_ali}/${author.useraddress}"> --%>
						<c:if test="${empty user.useraddress }">
					<img class="touxiang" src="${pageContext.request.contextPath}/img/icon/morentouxiang.jpg">
					</c:if><c:if test="${not empty user.useraddress }">
						<img class="touxiang" src="${ctx_ali}/${user.useraddress }">
					
					</c:if>
					<c:if test="${user.userType ==1 }">
						<span class="v">
							<img src="${ctx}/img/icon/v.png">
						</span>
						</c:if>
						
					</a>
				</div>
				<div class="user_name lf">
					<h4>
						<a>${user.nickname }</a>
					</h4>
					<span class="date">${user.createdTime }</span>
				</div>
				<%-- <a class="zan rt">
					<span class="zan_num">${user.fabulous_num }</span>
				</a> --%>
			</div>
			 <c:forEach items="${consultationList}" var="detail">
				 <c:forEach items="${detail}" var="details">
				<c:if test="${user.id == details.id }">
				 
				 <c:if test="${not empty details.detailsss }">
					<div>
						<span class="comment_content">${details.detailsss }</span>
					</div>
				</c:if>
				 
				<c:if test="${not empty details.address }">
					<div class="comment_img">
							<img class="pic1" src="${ctx_ali}/${details.address}">
					</div>
				</c:if>
				
				</c:if>
				</c:forEach>
			</c:forEach> 
		</li>
		</c:forEach>
		<li class="comment_item check_all" onclick="downloadApp('moreInfo')">
			<h4>查看全部</h4>
		</li>
	</ul>
</div>
<div class="footer">
	<form>
		<ul>
			<li>
				<input type="text" class="write_comment" placeholder="写回答…" readonly="readonly">
			</li>
			<li>
				<a class="pl"  onclick="downloadApp('moreActions')">
					<img src="${ctx}/img/icon/commont_normal.png" alt="">评论
				</a>
			</li>
			<li>
				<a class="shoucang"  onclick="downloadApp('moreActions')">
					<img src="${ctx}/img/icon/star.png" alt="">收藏
				</a>
			</li>
			<li>
				<a class="fenxiang"  onclick="downloadApp('moreActions')">
					<img src="${ctx}/img/icon/fenxiang.png" alt="">分享
				</a>
			</li>
			<li class="last">
				<a class="qiuzhu"  onclick="downloadApp('moreActions')">
					<img src="${ctx}/img/icon/sos.png" alt="">求助
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
				location.href ="${pageContext.request.contextPath}/mobile/view/app/download";
			},2000)

		}
		window.onload= function(){
			var comment_item = document.querySelectorAll(".comment_item");
			var len = comment_item.length;
			if(len>3){
				document.querySelector(".check_all").display = "block";
			}
			for(var i=0;i<len;i++){
				var comment_img = comment_item[i].querySelectorAll(".comment_img");
				var comment_content = comment_item[i].querySelector(".comment_content");
				if(!comment_content){
					var comment_img = comment_item[i].querySelectorAll(".comment_img");
					for(var j=0,leng=comment_img.length;j<leng;j++){
						comment_img[j].style.display="none";
						
					}
				}
				else if (comment_content && comment_img.length>=3){
						var comment_img = comment_item[i].querySelectorAll(".comment_img");
						for(var j=0,leng=comment_img.length;j<leng;j++){
							console.log(comment_img[j].className)
						comment_img[j].className += " comment_img_3"
						
					}
				}
				
			} 
			var h= document.documentElement.clientHeight || document.body.clientHeight || windows.innerHeight ;
			var body_box = document.querySelector("#body_box");
			body_box.style.minHeight = h + "px";
		}
		 
	</script> 
</body>
</html>