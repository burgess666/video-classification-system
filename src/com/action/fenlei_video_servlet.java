package com.action;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.dao.DB;
import com.orm.TVideo;
import com.util.Train;

public class fenlei_video_servlet extends HttpServlet  {
	 
	      Train train = Train.getInstance();
	      public void doGet(HttpServletRequest request, HttpServletResponse response)
	              throws ServletException, IOException {
	    	    String type="";
	    	    //text content after VR session with IFlyTek
	    	    String contentVideo = request.getParameter("contentVideo_zh");
	    	    if(contentVideo.equals("")){
	    	    	type = "Other";
	    	    }else{
	    	    	Map<String, Double> resultMap = train.classify(contentVideo);
					type = train.getType(resultMap);
					if("".equals(type)){
						type = "Other";
					}
	    	    }
	    	    String id = request.getParameter("id"); //the id of uploaded videos
				updateType(id,type); //update types for videos
				findAllDaiChuLiVideo(type,request,response);       
	      }
	      
	     
        /**
        * Query classification video files
        * @param req
        * @param res
        * @throws ServletException
        * @throws IOException
        */
	  	public void findAllDaiChuLiVideo(String type,HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
		{
			List<TVideo> videoList=new ArrayList<TVideo>();
			String sql="select * from t_video where vtype= ? ";
			Object[] params={type};
			DB mydb=new DB();
			try
			{
				mydb.doPstm(sql, params);
				ResultSet rs=mydb.getRs();
				while(rs.next())
				{
					TVideo video=new TVideo();
					video.setId(rs.getInt("id"));
					video.setVname(rs.getString("vname"));
					video.setVurl(rs.getString("vurl"));
					video.setVtype(rs.getString("vtype"));
					video.setCreateDate(rs.getString("createDate"));
					videoList.add(video);
			    }
				rs.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			mydb.closed();
			req.setAttribute("type", type);
			req.setAttribute("videoList", videoList);
			req.getRequestDispatcher("filelist.jsp").forward(req, res);
		}
	  	
	  	
	  	public void updateType(String id,String type) throws ServletException, IOException
		{
			String sql="update t_video set vtype=? where id= ? ";
			Object[] params={type,id};
			DB mydb=new DB();
			try
			{
				mydb.doPstm(sql, params);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			mydb.closed();
		}
	  
	      public void doPost(HttpServletRequest request, HttpServletResponse response)
	              throws ServletException, IOException {
	  
	          doGet(request, response);
	      }
}
