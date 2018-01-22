<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/commonPublic.jsp"%>
<head>
<title>健桥资讯</title>
<style>
.BaoCuo {
	width: 1210px;
	background: #fff;
	margin: 0 auto;
	padding-top: 30px;
}

.BaoCuo_1 {
	display: block;
	margin: 0 auto;
}

.BaoCuo_2 {
	text-align: center;
	font-size: 20px;
	color: #000;
}

.BaoCuo_2 .Cuo_1 {
	padding-top: 30px;
	padding-bottom: 30px;
}

.BaoCuo_2 .Cuo_2 {
	width: 120px;
	height: 46px;
	display: block;
	text-align: center;
	line-height: 46px;
	background: #b84250;
	color: #fff;
	font-size: 16px;
	margin: 0 auto;
	text-decoration: none;
}
</style>
</head>
<body>
	<div class="BaoCuo">
		<img class="BaoCuo_1" src="${ctx }/themes/img/404.jpg">
		<div class="BaoCuo_2">
			<p class="Cuo_1">您访问的页面被外星人抓走了</p>
			<a href="${ctx }/" class="Cuo_2">返回首页</a>
		</div>
	</div>
</body>