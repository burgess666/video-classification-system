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

public class play_video_servlet extends HttpServlet  {
	 
	      Train train = Train.getInstance();
	      public void doGet(HttpServletRequest request, HttpServletResponse response)
	              throws ServletException, IOException {
	    	     
	    	   
	    	    String id = request.getParameter("id"); //the id of uploaded video
	    	    findAllDaiChuLiVideo(id,request,response);
	      }
	      
	     
        /**
        * Search classification video files
        * @param req
        * @param res
        * @throws ServletException
        * @throws IOException
        */
	  	public void findAllDaiChuLiVideo(String id,HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
		{
			List<TVideo> videoList=new ArrayList<TVideo>();
			String sql="select * from t_video where id= ? ";
			Object[] params={id};
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
			req.setAttribute("video", videoList.get(0));
			req.getRequestDispatcher("play.jsp").forward(req, res);
		}
	  	
	  	
	      public void doPost(HttpServletRequest request, HttpServletResponse response)
	              throws ServletException, IOException {
	          doGet(request, response);
	      }
}
