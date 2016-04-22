package com.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import com.dao.DB;
import com.orm.TVideo;

public class upload_file_servlet extends HttpServlet  {
	      public void doGet(HttpServletRequest request, HttpServletResponse response)
	              throws ServletException, IOException {
	    	  
	    	  
	    	          String type = request.getParameter("type");
	    	          if("query".equals(type)){
	    	        	  findAllDaiChuLiVideo(request, response);
	    	          }
	                  String savePath = request.getRealPath("/upload");
	                  File file = new File(savePath);
	                  if (!file.exists() && !file.isDirectory()) {
	                      System.out.println(savePath+"catalogue does not exist and it has to be created!");
	                      //create catalogue
	                      file.mkdir();
	                  }
	                  //notification
	                  String message = "";
	                  String vname="";   //the name of video
                      String vurl="";    //the path of file
                      String vtype="Non-classification"; //the type of file
	                  try{
	                      //using the file upload component of Apache to handle uploaded files
	                      //1¡¢create DiskFileItemFactory
	                      DiskFileItemFactory factory = new DiskFileItemFactory();
	                      //2¡¢create a parser for file upload
	                      ServletFileUpload upload = new ServletFileUpload(factory);
	                       //handle with the problems of Chinese file name
	                      upload.setHeaderEncoding("UTF-8"); 
	                      //3¡¢deal with the data
	                      if(!ServletFileUpload.isMultipartContent(request)){
	                          return;
	                      }
	                      //4¡¢using ServletFileUpload to parse data and return a List<FileItem>. Each FileItem corresponds to the input of form
	                      List<FileItem> list = upload.parseRequest(request);
	                     
	                      for(FileItem item : list){
	                          //if fileitem is general data
	                          if(item.isFormField()){
	                             String name = item.getFieldName();
	                              //using UTF-8 to deal with encoding problems
	                              String value = item.getString("UTF-8");
	                              //value = new String(value.getBytes("iso8859-1"),"UTF-8");
	                              System.out.println(name + "=" + value);
	                              vname = value;
	                          }else{
	                              	//take uploaded file name£¬
									//String filename = item.getName();
									//System.out.println(filename);
									//if(filename==null || filename.trim().equals("")){
									//	                                  continue;
									//}
									//	 filename = filename.substring(filename.lastIndexOf("\\")+1);
	                        	  String filename;
	                              UUID uuid = UUID.randomUUID();
	                              filename = uuid.toString() + ".mp4";
	                              vurl = "./upload/" + filename;
	                              //take the input stream of uploaded file
	                              InputStream in = item.getInputStream();
	                              //create a file output stream
	                              FileOutputStream out = new FileOutputStream(savePath + "\\" + filename);
	                              //create a cache area
	                              byte buffer[] = new byte[1024];
	                              // if all data is read or not
	                              int len = 0;
	                              //set inputstream to buffer£¬(len=in.read(buffer))>0 means that inoutstream has stream
	                              while((len=in.read(buffer))>0){
	                                  //using FileOutputStream to put outstream data into (savePath + "\\" + filename)
	                                  out.write(buffer, 0, len);
	                              }
	                              //close inputstream
	                              in.close();
	                              //close outstream
	                              out.close();
	                              //delete temporal files
	                              item.delete();
	                              message = "upload successfully£¡";
	                          }
	                      }
	                      addVideo(vname,vurl,vtype);
	                  }catch (Exception e) {
	                      message= "upload failed£¡";
	                      e.printStackTrace();
	                      
	                  }
	                  request.setAttribute("message",message);
	                  findAllDaiChuLiVideo(request, response);     
	      }
	      
	      public void addVideo(String vname,String vurl,String vtype)
	  	  {
		  		String sql="insert into t_video(vname,vurl,vtype,createDate) values(?,?,?,?)";
		  		Date date = new Date();
		  		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		  		String createDate = format.format(date);
		  		Object[] params={vname,vurl,vtype,createDate};
		  		DB mydb=new DB();
		  		mydb.doPstm(sql, params);
		  		mydb.closed();
	  	  }
	    
	      /**
	       * Query unclassification video files
	       * @param req
	       * @param res
	       * @throws ServletException
	       * @throws IOException
	       */
	  	public void findAllDaiChuLiVideo(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
		{
			List<TVideo> videoList=new ArrayList<TVideo>();
			String sql="select * from t_video where vtype='Non-classification'";
			Object[] params={};
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
			
			req.setAttribute("videoList", videoList);
			req.getRequestDispatcher("video_upload.jsp").forward(req, res);
		}
	  
	      public void doPost(HttpServletRequest request, HttpServletResponse response)
	              throws ServletException, IOException {
	  
	          doGet(request, response);
	      }
}
