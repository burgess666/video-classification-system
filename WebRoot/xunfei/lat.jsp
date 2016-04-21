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
		<link rel="stylesheet" href="<%=path%>/css/style.css" type="text/css" />
		<script language="JavaScript" src="<%=path%>/js/jquery-1.9.1.min.js"></script>
		<title>Left Nav</title>
		<style type="text/css"> 
.align-center{ 
position:fixed;left:50%;top:50%;margin-left:width/2;margin-top:height/2;
} 
</style> 
		 

</head>


 <body>
    <textarea id="result"></textarea>
	<br/>
	<script src="http://blog.faultylabs.com/files/md5.js"></script>	
	<script src="http://webapi.openspeech.cn/socket.io/socket.io.js"></script>
	<script src='http://webapi.openspeech.cn/js/util/zepto.min.js'></script>
	<script src='http://webapi.openspeech.cn/js/util/jwav.min.js'></script>
	<script src='http://webapi.openspeech.cn/fingerprint.js'></script>
	<script src="http://webapi.openspeech.cn/iat.min.js"></script>
	<script type="text/javascript">
	    /**
		  * initial session object
		  */
	    var session = new IFlyIatSession({
                                      'url' : 'http://webapi.openspeech.cn/',							
                                      'reconnection'       : true,
									  'reconnectionDelay'  : 30000,
									  'compress' : 'speex',
									  'speex_path' : 'speex.js',              //speex.js local path 
									  'vad_path' : 'vad.js',                  //vad.js local path 
									  'recorder_path' : 'recorderWorker.js'    //recordWorker.js local path
						         });
		
		var byteArray = null;
		/**
		  * Start record and gain the recognition results 
		  */
		function start() {
		    /*************************************Fill in relevant information to have an access to IflyTek service**************************************/
		 
		   var appid = '5630dc6e';                              //apply appid
		    var timestamp =  new Date().toLocaleTimeString();     //current date，example:new Date().toLocaleTimeString()
            var expires = '60000';                          //signature overdue date，unit:ms，eg:60000		
		    //secretkey setup
		    var signature = faultylabs.MD5(appid + '&' + timestamp + '&' + expires + '&' + 'da8e0bc5292a21a6');	

		  
		   /************************************************************Fill in relevant information**************************************************/
		    var params = {"grammar_list" : null, "params" : "aue=speex-wb;-1, usr = mkchen, ssm = 1, sub = iat, net_type = wifi, ent =sms16k, rst = plain, auf  = audio/L16;rate=16000, vad_enable = 1, vad_timeout = 5000, vad_speech_tail = 500, caller.appid = " + appid + ",timestamp = " + timestamp + ",expires = " + expires, "signature" : signature};
			
            /* 调用开始录音接口，通过function(volume)和function(err, obj)回调音量和识别结果 */
		    session.start(params, function (volume)
		    {	
                /* 获取并展示当前录音音量 */			
			    if(volume < 6 && volume > 0)
				    console.log("volume : " + volume);
					
			    /* 若volume返回负值，说明麦克风启动失败*/
			    if(volume < 0)
				    console.log("麦克风启动失败");
		    }, function (err, result)
		    {
			    /* 若回调的err为空或错误码为0，则会话成功，可提取识别结果进行显示*/
		        if(err == null || err == undefined || err == 0)
			    {
				    if(result == '' || result == null)
					    document.getElementById('result').innerHTML = "没有获取到识别结果";
				    else
					    document.getElementById('result').innerHTML = result;
			    /* 若回调的err不为空且错误码不为0，则会话失败，可提取错误码 */	
			    } else
			    {
			        document.getElementById('result').innerHTML = 'error code : ' + err + ", error description : " + result;
			    }
		    }, function(message)
			{
				if(message == 'onStop')
				{
					console.log("stop record");
				} else if(message == 'onEnd')
				{
					console.log("stop session");
				}
			}, function(data)
			{
				console.log(data);
			});		

            /* read files */
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
	</script>
	<input type="button" value="Record" onclick="start();"  />
	<input type="button" value="Done" onclick="stop();"  />
</body>
</html>
