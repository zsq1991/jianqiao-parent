<%@ page pageEncoding="UTF-8" %>
<%@ include file="commonPublic.jsp" %>
<head>
    <meta charset="UTF-8">
    <title>访谈详情</title>
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta content="telephone=no,email=no" name="format-detection">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
    <META HTTP-EQUIV="Expires" CONTENT="0">
    <link rel="stylesheet" href="${ctx}/css/base.css">
    <link rel="stylesheet" href="${ctx}/css/jqapp.css">
    <script type="text/javascript" src="${ctx}/js/jqapp.js"></script>
    <style>
        .inter_section_list {
            padding: 0.5rem 0;
        }

        .inter_section_list > p {
            margin: 0.5rem 0;
            line-height: 1.6rem;
        }

        .inter_section_list > h4 {
            font-weight: 600;
        }

        .huida_img img {
            width: 100%;
        }

        .txt h3 {
            text-align: center;
        }
    </style>
</head>
<body>
<!-- 头部 -->
<%-- <div id="header">
	<!-- <h2 class="title">访谈详情</h2> -->
	<a class="sos" onclick="downloadApp('moreActions')">
		<img src="${pageContext.request.contextPath}/img/icon/sos.png" alt="">求助
	</a>
</div> --%>
<!-- banner部分 -->
<div id="body_box">
    <div id="banner">
        <img src="${ctx_ali}/${consultation.address}">
        <div class="txt" style="width:100%">
            <h3>${consultation.title }</h3>
            <p>${consultation.detail_summary } </p>
        </div>
    </div>
    <!-- 访谈部分 -->
    <!-- <ul> -->
    <div class="section">
        <div class="interview">
            <h3 class="title">${consultationTop.title }</h3>
            <div class="inter_header clearfix">
                <div class="reporter_box lf">
                    <div class="head lf">
                        <a onclick="downloadApp('moreInfo')">
                            <c:if test="${empty consultationTop.address }">
                                <img class="touxiang"
                                     src="${pageContext.request.contextPath}/img/icon/morentouxiang.jpg">
                            </c:if>
                            <c:if test="${not empty consultationTop.address }">
                                <img class="touxiang" src="${ctx_ali}/${consultationTop.address }">
                            </c:if>
                            <c:if test="${consultationTop.userType ==1 }">
						<span class="v">
							<img src="${pageContext.request.contextPath}/img/icon/v.png">
						</span>
                            </c:if>
                        </a>
                    </div>
                    <div class="reporter lf">
                        <h4>
                            <span>${consultationTop.nickname }</span>
                        </h4>
                        <span>${consultationTop.createdTime }</span>
                    </div>
                </div>
                <div class="rt">
                    <button class="attent_btn" onclick="downloadApp('moreInfo')">+关注</button>
                </div>
            </div>
            <div class="inter_section">
                <ul>
                    <%-- 	<li class="inter_section_list">
                            <h4><a>记者：</a><span class="question">犯病的时候什么感觉？</span></h4>
                            <p class="huida">答: 对于各类风湿骨病的不同症状，选对用药才是关键对于颈椎病来讲颈复康颗粒是不错的选择。</p>
                        </li>
                        <li class="inter_section_list">
                            <h4><a>记者：</a><span class="question">犯病的时候什么感觉？</span></h4>
                            <p class="huida">答: 对于各类风湿骨病的不同症状，选对用药才是关键对于颈椎病来讲颈复康颗粒是不错的选择。</p>
                            <div class="huida_img">
                                <img src="${pageContext.request.contextPath}/img/fengshi2.jpg" alt="">
                            </div>
                        </li> --%>
                    <li class="inter_section_list">
                        <!-- <h4><a>记者：</a><span class="question">犯病的时候什么感觉？</span></h4> -->
                        <%-- <p class="huida">${consultationTop.detailSummary }</p> --%>
                        <c:forEach items="${ftDetailList}" var="ftdl">
                            <c:if test="${not empty ftdl.detailContent }">
                                <%-- <img class="touxiang" src="${ctx_ali}/${consultationTop.address }"> --%>
                                <p class="huida">
                                <pre style="font-family: Microsoft YaHei;font-size:1.4rem;white-space: pre-wrap;word-wrap: break-word;">${ftdl.detailContent }</pre>
                                </p>
                            </c:if>
                            <c:if test="${not empty ftdl.address }">
                                <c:if test="${ftdl.type == 2}">
                                    <img class="detail" src="${ftdl.address}">
                                </c:if>
                                <c:if test="${ftdl.type == 3}">
                                    <video preload="load" id="video1" controls="" width="100%" height="500px">
                                        <source src="${ftdl.address}"  type="video/mp4"/>
                                    </video>
                                </c:if>
                            </c:if>
                        </c:forEach>
                    </li>
                </ul>
            </div>
        </div>
        <c:forEach items="${consultationList}" var="sc">
            <div class="view_item clearfix" onclick="downloadApp('moreInfo')">
                <h4 class="lf">${sc.title }</h4>
                <div class="store_box rt">
                    <div class="store lf">
                        <span>${sc.collectNum }</span>收藏
                    </div>
                    <div class="rt">${sc.createdTime }</div>
                </div>
            </div>
        </c:forEach>
        <!-- 	<div class="view_item clearfix"  onclick="downloadApp('moreInfo')">
                <h4 class="lf">风湿骨痛访谈三</h4>
                <div class="store_box rt">
                    <div class="store lf">
                        <span>500</span>收藏
                    </div>
                    <div class="rt">2017/05/20</div>
                </div>
            </div>
            <div class="view_item clearfix"  onclick="downloadApp('moreInfo')">
                <h4 class="lf">风湿骨痛访谈四</h4>
                <div class="store_box rt">
                    <div class="store lf">
                        <span>500</span>收藏
                    </div>
                    <div class="rt">2017/05/20</div>
                </div>
            </div>
            <div class="view_item clearfix"  onclick="downloadApp('moreInfo')">
                <h4 class="lf">风湿骨痛访谈五</h4>
                <div class="store_box rt">
                    <div class="store lf">
                        <span>500</span>收藏
                    </div>
                    <div class="rt">2017/05/20</div>
                </div>
            </div> -->
        <div class="view_item check_all" style="display:none; text-align: center" onclick="downloadApp('moreInfo')">
            <h6>查看全部</h6>
        </div>
    </div>
    <div class="comment">
        <h3 class="title">用户评论（
            <span>
                <c:choose>
                    <c:when test="${consultationTop.commentNum >0}">${consultationTop.commentNum}</c:when>
                    <c:otherwise>0</c:otherwise>
                </c:choose>
            </span>条）</h3>
        <ul>
            <c:if test="${consultationTop.commentNum >0}">
                <c:forEach items="${consultationDetail}" var="sccc">
                    <li class="comment_item">
                        <div class="head lf">
                            <a href="#">
                                    <%-- <img class="touxiang" src="${ctx_ali}/${sccc.useraddress}"> --%>
                                <c:if test="${empty sccc.useraddress }">
                                    <img class="touxiang"
                                         src="${pageContext.request.contextPath}/img/icon/morentouxiang.jpg">
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
                            <div class="user_info">
                                <a class="user_name lf">${sccc.nickname }</a>
                                <a class="zan rt" onclick="downloadApp('moreInfo')">
                                    <span class="zan_num">${sccc.reply_num}</span>
                                </a>
                            </div>
                            <div class="comment_content">${sccc.content}</div>
                            <p>
                                <span class="date">${sccc.createdTime }</span>
                                <a class="answer active" onclick="downloadApp('moreInfo')">${sccc.reply}回复</a>
                            </p>

                            <div class="answer_box">
                                <ul>
                                    <c:forEach items="${sccc.replyDetail}" var="ssss">
                                        <li>
                                            <a class="answer_user"
                                               href="${ctx_ali }/${ssss.address}">${ssss.nickname }：</a>
                                            <span>${ssss.content }</span>
                                        </li>

                                    </c:forEach>
                                    <c:if test="${sccc.reply > 2}">
                                        <li>
                                            <a onclick="downloadApp('moreInfo')">查看全部${sccc.reply}条回复&gt;&gt;</a>
                                        </li>
                                    </c:if>
                                </ul>
                            </div>

                        </div>
                    </li>
                </c:forEach>
            </c:if>

            <li class="comment_item check_all" onclick="downloadApp('moreInfo')">
                <h4>查看全部</h4>
            </li>
        </ul>
    </div>
    <div class="footer">
        <form>
            <ul>
                <li>
                    <input type="text" class="write_comment" placeholder="写评论…" readonly="readonly">
                </li>
                <li>
                    <a class="pl" onclick="downloadApp('moreActions')">
                        <img src="${pageContext.request.contextPath}/img/icon/commont_normal.png" alt="">评论
                    </a>
                </li>
                <li>
                    <a class="shoucang" onclick="downloadApp('moreActions')">
                        <img src="${pageContext.request.contextPath}/img/icon/star.png" alt="">收藏
                    </a>
                </li>
                <li>
                    <a class="fenxiang" onclick="downloadApp('moreActions')">
                        <img src="${pageContext.request.contextPath}/img/icon/fenxiang.png" alt="">分享
                    </a>
                </li>
                <li class="last">
                    <a class="qiuzhu" onclick="downloadApp('moreActions')">
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
    (function (win, doc) {
        var resize = 'orientationchange' in window ? 'orientationchange' : 'resize';

        function change() {
            var html = doc.documentElement;
            var deviceWidth = html.clientWidth;

            html.style.fontSize = deviceWidth / 36 + 'px';
        }

        win.addEventListener(resize, change, false);
        win.addEventListener('load', change, false);
        doc.addEventListener('DOMContentLoaded', false);
    })(window, document);

    /* 	function downloadApp(){
     //alert("点击确定跳转到下载页面!");
     setTimeout(
     function downloadApp1(){
     },2000
     );
     } */
    function downloadApp(argu) {
        var model = document.getElementById("model");
        model.style.display = 'block';
        model.style.opacity = 1;
        var span = model.firstElementChild;
        span.className = "active";
        if (argu == "moreInfo") {
            span.innerHTML = "下载健桥APP，查看更多内容！"
        }
        else if (argu == "moreActions") {
            span.innerHTML = "下载健桥APP，进行更多操作！"
        }
        setTimeout(function () {
            /* location.href="erweima.html" */
            location.href = "${pageContext.request.contextPath}/mobile/view/app/download";
        }, 2000)

    }

    window.onload = function () {
        var view_item = document.querySelectorAll(".view_item");
        var len = view_item.length;
        var check_all = document.querySelector(".check_all");
        if (len < 5) {

            check_all.style.display = "none";
        } else {
            check_all.style.display = "block";
        }
        var answer_box = document.querySelectorAll(".answer_box");
        for (var i = 0, len = answer_box.length; i < len; i++) {
            var answer_num = answer_box[i].querySelectorAll("ul li");
            console.log(answer_num)
            if (answer_num.length === 0) {
                answer_box[i].style.display = "none";
            }
        }
        var h = document.documentElement.clientHeight || document.body.clientHeight || windows.innerHeight;
        var body_box = document.querySelector("#body_box");
        body_box.style.minHeight = h + "px";
    }

</script>
</body>
</html>