<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/play.css" />
		<link rel="stylesheet" href="<%=path%>/css/style.css" type="text/css" />
		<script language="JavaScript" src="<%=path%>/js/jquery-1.9.1.min.js"></script>
		<script language="JavaScript" src="<%=path%>/js/mediaelement.js"></script>
		<script language="JavaScript" src="<%=path%>/js/md5.js"></script>
		<script src="http://blog.faultylabs.com/files/md5.js"></script>	
		<script src="http://webapi.openspeech.cn/socket.io/socket.io.js"></script>
		<script src='http://webapi.openspeech.cn/js/util/zepto.min.js'></script>
		<script src='http://webapi.openspeech.cn/js/util/jwav.min.js'></script>
		<script src='http://webapi.openspeech.cn/fingerprint.js'></script>
		<script src="http://webapi.openspeech.cn/iat.min.js"></script>
	
	
		<title>Play video</title>
		<style type="text/css"> 

.demo{margin:60px auto; text-align:center}
.audio{margin-top:20px}
</style> 
</head>
 
<body>
  <form method="post">
<div id="main">
   <h2 class="top_title"></h2>
   <div class="demo">
         <video width="360" height="203" id="player1" src="<%=path%>/${requestScope.video.vurl}" type="video/mp4" controls="controls"></video>
   </div>
   
 
    <ul class="forminfo">
    <input name="id" type="hidden" class="dfinput" value="${requestScope.video.id}"/>
    <li><input id="startBtn" name="startBtn" type="button" class="btn"  value="Voice Recognition"/>
    &nbsp;&nbsp;&nbsp;
        <input id="endBtn" name="endBtn" type="button" class="btn" onclick="submitFen();"  value="Classification"/>
    </li>
      <li><textarea id="result" rows="10" cols="20" name="contentVideo" type="text" class="textinput"></textarea>
     	  <textarea id="result_zh" onfocus="translation();" rows="10" cols="20" name="contentVideo_zh" type="text" class="textinput"></textarea>
     </li>
    </ul>
</div> 
</form>
</body>
</html>

<script type="text/javascript">
	    /**
		  * initialize Session Object
		  */
	    var session = new IFlyIatSession({
                                      'url' : 'http://webapi.openspeech.cn/',							
                                      'reconnection'       : true,
									  'reconnectionDelay'  : 30000,
									  'compress' : 'speex',
									  'speex_path' : '<%=path%>/xunfei/speex.js',              //speex.js local path  
									  'vad_path' : '<%=path%>/xunfei/vad.js',                  //vad.js  local path
									  'recorder_path' : '<%=path%>/xunfei/recorderWorker.js'    //recordWorker.js local path 
						         });
		
		var byteArray = null;
		/**
		  * start record and get results through VR
		  */
		function start() {
		    /*******************************************************Fill in all real information*************************************************/
		 
		   var appid = '5630dc6e';                              //apply APPID
		    var timestamp =  new Date().toLocaleTimeString();                      //Current date，eg:new Date().toLocaleTimeString()
            var expires = '60000';                          //signature overdue time，unit:ms，eg:60000		
		    //secretkey setup
		    var signature = faultylabs.MD5(appid + '&' + timestamp + '&' + expires + '&' + 'da8e0bc5292a21a6');	

		  
		   /************************************************************Fill in all real information**************************************************/
		    var params = {"grammar_list" : null, "params" : "aue=speex-wb;-1, usr = mkchen, ssm = 1, sub = iat, net_type = wifi, ent =sms-en16k, rst = plain, auf  = audio/L16;rate=16000, vad_enable = 1, vad_timeout = 5000, vad_speech_tail = 500, caller.appid = " + appid + ",timestamp = " + timestamp + ",expires = " + expires, "signature" : signature};
			
            /* Call record interface and take the result of volume and result */
		    session.start(params, function (volume)
		    {	
                /* get current record volume */			
			    if(volume < 6 && volume > 0)
				    console.log("volume : " + volume);
					
			    /* if volume value that is less than 0，that means microphone fails to start*/
			    if(volume < 0)
				    console.log("Microphone fails to start.");
		    }, function (err, result)
		    {
			    /* if err is NULL or error code is 0, the session is success and results can be displayed.*/
		        if(err == null || err == undefined || err == 0)
			    {
				    if(result == '' || result == null){
				        document.getElementById('result').innerHTML = "No results";
				    }
				    else{
				        document.getElementById('result').innerHTML = result;
				        translation();
				    }
					    
			    /* if err is not NULL or error code is not 0, the session is failed and error can be took. */	
			    } else
			    {
			        document.getElementById('result').innerHTML = 'error code : ' + err + ", error description : " + result;
			    }
			    
			   
		    }, function(message)
			{
				if(message == 'onStop')
				{
					console.log("Record stop");
				} else if(message == 'onEnd')
				{
					console.log("Done with session");
				}
			}, function(data)
			{
				console.log(data);
			});		

            /* read files*/
			file.onload = function (onEvent) {
				var arrayBuffer = file.response;
				if(arrayBuffer)
				{
					byteArray = new Int16Array(arrayBuffer);
					console.log(byteArray.length);
					window.setTimeout(writeAudioData, 20);
				}
			}			
			
		};
		
		function writeAudioData()
		{
			if(byteArray.length > 320)
			{
				window.setTimeout(writeAudioData, 20);
				var data = byteArray.subarray(0, 320);
				byteArray = byteArray.subarray(320, byteArray.length);
				session.writeAudio(data, 2);
			} else {
				session.writeAudio(byteArray, 4);
			}
		}
		
		function stop() {
			session.stop(null);
		};
 
   MediaElement('player1', {success: function(me) {

	//me.play();
	
	/**
	me.addEventListener('timeupdate', function() {
		document.getElementById('time').innerHTML = me.currentTime;
	}, false);
	

    **/
    document.getElementById('startBtn')['onclick'] = function() {
		if (me.paused){
		    $('#startBtn').val('Done with recognition');
			me.play();
			start();
		}

		else{
		    
			me.pause();
			stop();
		}
		
	  };
}});


function translation(){
	var appid = '20160405000017802';
	var key = 'T4AJCwh8Gw1OrfYJYrME';
	var salt = (new Date).getTime();
	var query = $('#result').val();
	var from = 'en';
	var to = 'zh';
	var str1 = appid + query + salt +key;
	var sign = MD5(str1);
	if(query==null || ""==query){
	   return;
	}
	$.ajax({
    url: 'http://api.fanyi.baidu.com/api/trans/vip/translate',
    type: 'get',
    dataType: 'jsonp',
    data: {
        q: query,
        appid: appid,
        salt: salt,
        from: from,
        to: to,
        sign: sign
    },
    success: function (data) {
        var res = data.trans_result[0].dst;
        $('#result_zh').val(res);
    } 
});

}

function submitFen(){
     document.forms[0].action = "<%=path%>/typeVideo";
     document.forms[0].submit();
}
</script>
