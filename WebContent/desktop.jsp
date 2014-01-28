<!DOCTYPE html>
<%@ include file="pages/commonHeader.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>desktop桌面</title>
<script type="text/javascript" src="jslib/jquery-ui-1.10.4.custom/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="jslib/jquery-ui-1.10.4.custom/js/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="jslib/desktop/js/myLib.js"></script>
<script type="text/javascript">
$(function(){
		   myLib.progressBar();
		   });
//lazying loading css/js resources which append URI resources in the context of current page like as AMD machnizm.
$.include(['jslib/jquery-ui-1.10.4.custom/css/start/jquery-ui-1.10.4.custom.min.css',
           'jslib/jquery-smartMenu/css/smartMenu.css',
           'jslib/desktop/js/jquery.winResize.js', 
           'jslib/jquery-smartMenu/js/jquery-smartMenu-min.js',
           'jslib/desktop/css/desktop.css',
           'jslib/desktop/js/desktop.js']);
$(window).load(function(){
		   myLib.stopProgress();
		   
		   //in the future, we could query Json data by user privilege and subscription app.
		   var lrBarIconData={
				   'app0':{
						   'title':'个人主页',
						   'url':'',
						   'winWidth':1100,
						   'winHeight':650
						   },
					'app1':{
						   'title':'用户管理',
						   'url':'',
						   'winWidth':1100,
						   'winHeight':650
						   },
					'app2':{
						   'title':'文件管理',
						   'url':'',
						   'winWidth':1100,
						   'winHeight':650
						   },
					'app3':{
						   'title':'资源分享',
						   'url':'https://github.com/learncenter2014/dynamicform',
						   'winWidth':1100,
						   'winHeight':650
						   }
					};
		   
		//in the future, we could query Json data by user privilege and subscription app.		   
		  var deskIconData={
			        'kuwoMusic':{
					   'title':'酷我音乐盒',
					   'url':'http://mbox.kuwo.cn/',
					   'winWidth':950,
					   'winHeight':500
						},
					'dynamicform':{
					   'title':'表单服务',
					   'url':'dynamicform.jsp',
					   'winWidth':950,
					   'winHeight':480
						},
					 'browser':{
						   'title':'浏览网页',
						   'url':'http://hao.qq.com/index_webqq.html',
						   'winWidth':950,
						   'winHeight':480
					    },
					'dubianFim':{
					   'title':'豆瓣FIM',
					   'url':'http://douban.fm/partner/qq_plus',
					   'winWidth':550,
					   'winHeight':480
						},
					'Pixlr':{
					   'title':'Pixlr',
					   'url':'http://pixlr.com/editor/?loc=zh-cn',
					   'winWidth':942,
					   'winHeight':547
						},
					'qidian':{
					   'title':'起点中文',
					   'url':'http://webqq.qidian.com',
					   'winWidth':942,
					   'winHeight':547
						},
					'youku':{
					   'title':'优酷',
					   'url':'http://www.youku.com',
					   'winWidth':842,
					   'winHeight':547
						},
					'qianqianMusic':{
					   'title':'千千音乐',
					   'url':'http://www.qianqian.com/paihang.html',
					   'winWidth':930,
					   'winHeight':500
						}
			  };			   
 		   
		  //存储桌面布局元素的jquery对象
		   myLib.desktop.desktopPanel();
 		   
		   //初始化桌面背景
		   myLib.desktop.wallpaper.init("jslib/desktop/images/blue_glow.jpg");
		   
		   //初始化任务栏
		   myLib.desktop.taskBar.init();
		   
		   //初始化桌面图标
		   myLib.desktop.deskIcon.init(deskIconData);
		   
		   //初始化桌面导航栏
		   myLib.desktop.navBar.init();
		   
		   //初始化侧边栏
		   myLib.desktop.lrBar.init(lrBarIconData);
		   
  		  
		  });		

//添加应用函数
function addIcon(data){
	 myLib.desktop.deskIcon.addIcon(data);
	}
</script>
</head>
<body>
<a href="#" class="powered_by">Powered by Peter</a>
<div id="wallpapers"></div>
<div id="navBar"><a href="#" class="currTab" title="桌面1"></a><a href="#"  title="桌面2"></a><a href="#"  title="桌面3"></a><a href="#"  title="桌面4"></a></div>
<div id="desktopPanel">
<div id="desktopInnerPanel">
<ul class="deskIcon currDesktop">
  <li class="desktop_icon" id="youku"> <span class="icon"><img src="jslib/desktop/icon/iconyouku.png"/></span> <div class="text">优酷<s></s></div> </li>
  <li class="desktop_icon" id="Pixlr"> <span class="icon"><img src="jslib/desktop/icon/icon6.png"/></span> <div class="text">Pixlr<s></s></div> </li>
  <li class="desktop_icon" id="dubianFim"> <span class="icon"><img src="jslib/desktop/icon/icon7.png"/></span> <div class="text">豆瓣FIM <s></s></div> </li>
  <li class="desktop_icon" id="qidian"> <span class="icon"><img src="jslib/desktop/icon/icon9.png"/></span> <div class="text">起点中文 <s></s></div> </li>
  <li class="desktop_icon" id="dynamicform"> <span class="icon"><img src="jslib/desktop/icon/icon10.png"/></span> <div class="text">表单服务<s></s></div> </li>
  <li class="desktop_icon" id="browser"> <span class="icon"><img src="jslib/desktop/icon/icon10.png"/></span> <div class="text">浏览网页<s></s></div> </li>
  <li class="desktop_icon add_icon" id="addIcon0"> <span class="icon"><img src="jslib/desktop/images/add_icon.png"/></span> <div class="text">添加 <s></s></div> </li>
</ul>
</div>
</div>

<div id="taskBarWrap">
<div id="taskBar">
  <div id="leftBtn"><a href="#" class="upBtn"></a></div>
  <div id="rightBtn"><a href="#" class="downBtn"></a> </div>
  <div id="task_lb_wrap"><div id="task_lb"></div></div>
</div>
</div>

<div id="lr_bar">
  <ul id="default_app">
   <li id="app0"><span><img src="jslib/desktop/icon/icon1.png" title="Space"/></span><div class="text">个人主页<s></s></div></li>
   <li id="app1"><span><img src="jslib/desktop/icon/icon2.png" title="Product"/></span><div class="text">用户管理<s></s></div></li>
   <li  id="app2"><span><img src="jslib/desktop/icon/icon3.png" title="Introduction"/></span><div class="text">文件管理<s></s></div></li>
   <li id="app3"><span><img src="jslib/desktop/icon/icon11.png" title="Share Resource"/></span><div class="text">资源共享<s></s></div></li>
  </ul>
  <div id="default_tools"> <span id="showZm_btn" title="全屏"></span><span id="shizhong_btn" title="时钟"></span><span id="weather_btn" title="天气"></span> <span id="them_btn" title="主题"></span></div>
  <div id="start_block">
<a title="开始" id="start_btn"></a>
<div id="start_item">
      <ul class="item admin">
        <li><span class="adminImg"></span>Peter</li>
      </ul>
      <ul class="item">
        <li><span class="sitting_btn"></span>系统设置</li>
        <li><span class="help_btn"></span>使用指南 <b></b></li>
        <li><span class="about_btn"></span>关于我们</li>
        <li><span class="logout_btn"></span>退出系统</li>
      </ul>
    </div>
</div>
</div>

</body>
</html>
