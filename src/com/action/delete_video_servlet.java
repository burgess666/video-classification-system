package com.action;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.DB;
import com.orm.TVideo;
import com.util.Train;

public class delete_video_servlet extends HttpServlet  {
	 
	      Train train = Train.getInstance();
	      public void doGet(HttpServletRequest request, HttpServletResponse response)
	              throws ServletException, IOException {
	    	     
	    	  
	    	    String id = request.getParameter("id"); //the id of uploaded files
	    	    deleteType(id);
				findAllDaiChuLiVideo("Non-classification",request,response);       
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
			req.getRequestDispatcher("video_upload.jsp").forward(req, res);
		}
	  	
	  	
	  	public void deleteType(String id) throws ServletException, IOException
		{
			String sql="delete from  t_video   where id= ? ";
			Object[] params={id};
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
