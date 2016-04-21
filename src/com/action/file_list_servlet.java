package com.action;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.DB;
import com.orm.TVideo;

public class file_list_servlet extends HttpServlet  {
	 
	      private static Map<String,String> typeMap = new HashMap<String,String>();      
	      static{
	    	  
	    	  typeMap.put("100","Car");
	    	  typeMap.put("101","Finacne");
	    	  typeMap.put("102","IT");
	    	  typeMap.put("103","Health");
	    	  typeMap.put("104","Sports");
	    	  typeMap.put("105","Travel");
	    	  typeMap.put("106","Education");
	    	  typeMap.put("107","Employment");
	    	  typeMap.put("108","Culture");
	    	  typeMap.put("109","Military");
	    	  typeMap.put("110","Other");
	      }
	      public void doGet(HttpServletRequest request, HttpServletResponse response)
	              throws ServletException, IOException {
	    	           
	    	          String type = request.getParameter("type");
	    	          type = typeMap.get(type);
	    	          findAllDaiChuLiVideo(type,request, response);
	                   
	      }
	      
	    
	      /**
	       * query classification video files
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
	  
	      public void doPost(HttpServletRequest request, HttpServletResponse response)
	              throws ServletException, IOException {
	  
	          doGet(request, response);
	      }
}
