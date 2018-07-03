package com.jakartaproject.alice;
 
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;

import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.SeekableStream;

public class UploadServlet extends HttpServlet {

	private final static String allowance = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789";
	private String forward = null;
	private String permission = null;
	private String upload = null;
	private String target = null;
	private String validate = null;
	private String thumbnail = null;
	private String thumbnail_upload = null;
	private String sep = null;
	long maxsize = 0;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		Map property  = new HashMap();
		ResourceBundle resourcebundle = null;
		System.out.println("########################################################");
		String environment = config.getServletContext().getInitParameter("environment");
		if (environment == null) {
			environment = "alice";
		}
		System.out.println("environment : "+environment);
		try {
			resourcebundle = ResourceBundle.getBundle(environment);
			Enumeration enumeration = resourcebundle.getKeys();
			while (enumeration.hasMoreElements()) {
				String bkey = (String)enumeration.nextElement();
				property.put(bkey, (String)resourcebundle.getString(bkey));
				System.out.println(bkey+" : "+resourcebundle.getString(bkey));
			}
		} catch (Exception exception) {
			System.out.println("alice upload init failure!");
		}		

		if (property.get("target") != null) {
			target = (String)property.get("target");
			upload = config.getServletContext().getRealPath("/")+target;
		}
		System.out.println("upload : "+upload);
		
		if (property.get("thumbnail_target") != null) {
			thumbnail_upload = config.getServletContext().getRealPath("/")+property.get("thumbnail_target");
		}
		System.out.println("thumbnail : "+thumbnail_upload);		
		System.out.println("########################################################");
		
		if (property.get("forward") != null) {
			forward = (String)property.get("forward");
		}

		permission = (String)property.get("permission");
		if (permission == null) {
			permission = "gif,jpg,jpeg,png,swf";
		}
		if (property.get("thumbnail") != null) {
			thumbnail = (String)property.get("thumbnail");
		}
		sep = System.getProperty("file.separator");
		validate = (String)property.get("validate");
		try {
			maxsize = Integer.parseInt((String)property.get("maxsize"))*1024*1024;
		} 
		catch (NumberFormatException numberformatexception) {
			maxsize	= 1*1024*1024;
		}		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (upload == null || forward == null) {
			return;
		}

		long filesize = 0;
		String filelogc = null;
		String filephys = null;
		String extendname = null;
		String thumbsize = null;

		try {
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(1024 * 100);
				factory.setRepository(new File(upload));

				ServletFileUpload fileUpload = new ServletFileUpload(factory);
				fileUpload.setSizeMax(maxsize);
				fileUpload.setHeaderEncoding("EUC_KR");
			
				List fileItemList = fileUpload.parseRequest(request);
				for (int i = 0; i < fileItemList.size(); i++) {
					FileItem fileItem = (FileItem)fileItemList.get(i);
					if (fileItem.isFormField()) {
						if ("thumbsize".equals(fileItem.getFieldName())) thumbsize = fileItem.getString();
					}
				}			
				
				for (int i = 0; i < fileItemList.size(); i++) {
					FileItem fileItem = (FileItem)fileItemList.get(i);
					if (!fileItem.isFormField()) {
						filesize = fileItem.getSize();
						if (filesize > 0) {
							int idx = fileItem.getName().lastIndexOf("/");
							if (idx == -1) {
								idx = fileItem.getName().lastIndexOf("\\");
							}

							filelogc = fileItem.getName().substring(idx+1);
							filelogc = StringUtils.replace(StringUtils.replace(filelogc, "'", ""), "\"", "");							

							/**
							 * File Name Validation
							 */
							if (StringUtils.countMatches(filelogc, "..") > 0 || filelogc.indexOf(".") == -1 || 
									filelogc.indexOf("/") > -1 || filelogc.indexOf("\\") > -1 ||
									filelogc.length() <= filelogc.lastIndexOf(".")+1) {
								throw new RuntimeException("invalid filelogc");
							}

							if (filelogc.lastIndexOf(".") > -1) {
								extendname = filelogc.substring(filelogc.lastIndexOf(".")+1).trim();
							}
							
							/**
							 * File Extendname Validation
							 */
							if (extendname.length() == 0 || extendname.length() >= 16) {
								throw new RuntimeException("invalid extension");
							}

							/**
							 * Allowance Extendsname Validation
							 */
							boolean isExist = false;
							StringTokenizer tokens = new StringTokenizer(permission, ",");
							while (tokens.hasMoreTokens()) {
								if (extendname.equalsIgnoreCase(tokens.nextToken())) {
									isExist = true;
									break;
								}
							}
							if (!isExist) {
								throw new RuntimeException("invalid extension");
							}
							
							if (!validateAllowChar(extendname)) {
								extendname = "exception";
							}

							String yyyymmdd = getDate(new Date(), "yyyyMMdd");
							filephys = yyyymmdd+generateKey(i+1)+"_"+extendname.toLowerCase();
							
							File subdir = new File(upload+sep+yyyymmdd+sep);
							if (!subdir.exists()) {
								subdir.mkdirs();
							}
								
							File origin = new File(upload+sep+yyyymmdd+sep+filephys);
							fileItem.write(origin);
								
							/**
							 * Image Type Validateion
							 */
							if ("y".equalsIgnoreCase(validate)) {
								String imageType = getFileType(origin);
								System.out.println(extendname +" : " +imageType);
								if (((extendname.equalsIgnoreCase("gif") ||
										extendname.equalsIgnoreCase("jpeg") ||
										extendname.equalsIgnoreCase("bmp") ||
										extendname.equalsIgnoreCase("png"))
								      && !extendname.equalsIgnoreCase(imageType))
								      || (extendname.equalsIgnoreCase("jpg") && !"JPEG".equals(imageType))) {
									if (origin.exists() && origin.isFile() && !origin.isHidden()) {
										origin.delete();										
									}
								    throw new RuntimeException("image invalidate");
								}
							}
							
							if ("y".equalsIgnoreCase(thumbnail)) {
								try {
									int size = 50;
									try {
										size = Integer.parseInt(thumbsize);
									} catch (NumberFormatException s) {}

									File dir = new File(thumbnail_upload+sep+yyyymmdd+sep);
									if (!dir.exists()) {
										dir.mkdirs();
									}
									
									int thumbWidth = 0;
									int thumbHeight = 0;									
									BufferedInputStream bufferedis = null;
									FileInputStream fileis = null;
									BufferedImage bufferedimg = null;
									try {
										fileis = new FileInputStream(upload+sep+yyyymmdd+sep+filephys);
										bufferedis = new BufferedInputStream(fileis);
										bufferedimg = ImageIO.read(bufferedis);
											
										int width = bufferedimg == null? 0 : bufferedimg.getWidth();
										int height = bufferedimg == null? 0 : bufferedimg.getHeight();
										thumbWidth = size;
										thumbHeight = (width > 0)? size * height / width : size;
									} catch (Exception e) {
										System.out.println("Error at alice.UploadServlet caculate image");
										e.printStackTrace();
									} finally {
										if (fileis != null) try { fileis.close(); } catch (Exception e){}
										if (bufferedis != null) try { bufferedis.close(); } catch (Exception e){}
									}
									
									if (thumbWidth == 0) {
										thumbWidth = size;
										thumbHeight = size;
									}
								
									if (thumbWidth > 0) {
										/**
										 *  com.sun.media.jai.* 패키지를 사용
										 *  png 파일로 포맷
										 */
										createImage(upload+sep+yyyymmdd+sep+filephys, thumbnail_upload+sep+yyyymmdd+sep+filephys, thumbWidth, thumbHeight, true, "#FFFFFF");
									}
								} catch (Exception e) {
									System.out.println("Error at alice.UploadServlet make thumbnail");
								}
							}
						}
					}
				}
			}			

		} catch (org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException e) {
			System.out.println(e);
			request.setAttribute("message", "max size exceed!");
		} catch (RuntimeException e) {
			System.out.println(e);
		    request.setAttribute("message", "file invalidateion error");
		} catch (Exception e) {
			System.out.println("Error at UploadServlet "+ e.toString());
			e.printStackTrace();
			request.setAttribute("message","file upload error");
		}
		
		request.setAttribute("target", target);
		request.setAttribute("filename", filephys);
		request.setAttribute("filesize", String.valueOf(filesize));
		System.out.println("################ ALICE UPLOAD FILE INFO ##################");
		System.out.println("filename : "+filephys);
		System.out.println("filesize : "+filesize);
		System.out.println("##########################################################");
		RequestDispatcher rd;
		rd = getServletContext().getRequestDispatcher(forward);
		rd.forward(request, response);
	}
	
	protected String generateKey(int seq) {
		DecimalFormat decimalFormat = new DecimalFormat("000");
		return String.valueOf(System.currentTimeMillis()) + decimalFormat.format(seq);
	}
	
	protected String getDate(java.util.Date date, String format) {
		if (date==null || format == null)
	        	return "";

		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
		return sdf.format(date);
	}
	protected boolean validateAllowChar(String string) {
		boolean result = true;
		if (string == null)	return false;

		for (int i = 0; i < string.length(); i++) {
			if (allowance.indexOf(string.substring(i, i+1)) < 0) {
				result = false;
				break;
			}				
		}
		
		return result;
	}
	
	protected void createImage(String loadfile, String savefile, int width, int height, boolean border, String bdColor) throws ServletException, IOException 	{
		
		if (getDecoderCheck(loadfile)) {
			File 	    save = new File(savefile);
			RenderedOp    op = JAI.create("fileload", loadfile);
	     	BufferedImage im = op.getAsBufferedImage();
	     	
	     	if(im.getWidth()  < width) width = im.getWidth();
	     	if(im.getHeight() < height) height = im.getHeight();
			
			BufferedImage thumb = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D 	  g2d   = thumb.createGraphics();
						
			g2d.drawImage(im, 0, 0, width, height, null);
			
			if(border == true)
			{
				g2d.setColor(Color.decode(bdColor));
				g2d.drawRect(0, 0, --width, --height);
			}
						
			ImageIO.write(thumb, "png", save);
		}
	}
	
	protected boolean getDecoderCheck(String filename) {
		SeekableStream 	ss 	   = null;
		File 			file   = null;
		String[]		ext	   = null;
		String[]		images = {"gif","jpe","png","tiff","bmp"};
		boolean 		check  = false;
		
		try {
			file = new File(filename);
			ss   = new FileSeekableStream(file);
			ext  = ImageCodec.getDecoderNames(ss);
			
			for(int i=0; i<ext.length; i++) 
			{
				for(int j=0; j<images.length; j++) 
				{
					if(ext[i].indexOf(images[j]) > -1) 
					{
						check = true;
						break;
					}
				}
			}	
		}catch(Exception e) {}
		
		return check;
	}

	protected String getFileType (File file) { 
		InputStream inputStream = null;
		byte[] buf = new byte[132]; 
		try { 
			inputStream = new FileInputStream(file); 
			inputStream.read(buf, 0, 132); 
		} catch (IOException ioexception) {
			return "UNKNOWN"; 
		} finally {
			if (inputStream != null) try { inputStream.close(); } catch (Exception exception) {}
		}
		
		int b0 = buf[0] & 255; 
		int b1 = buf[1] & 255; 
		int b2 = buf[2] & 255; 
		int b3 = buf[3] & 255; 
		
		if (buf[128] == 68 && buf[129] == 73 && buf[130] == 67 && buf[131] == 77 
			&& ((b0 == 73 && b1 == 73) || (b0 == 77 && b1 == 77))) 
			return "TIFF_AND_DICOM"; 
		
		if (b0 == 73 && b1 == 73 && b2 == 42 && b3 == 0) 
			return "TIFF"; 
		
		if (b0 == 77 && b1 == 77 && b2 == 0 && b3 == 42) 
			return "TIFF"; 
		
		if (b0 == 255 && b1 == 216 && b2 == 255) 
			return "JPEG"; 
		
		if (b0 == 71 && b1 == 73 && b2 == 70 && b3 == 56) 
			return "GIF"; 
		
		if (buf[128] == 68 && buf[129] == 73 && buf[130] == 67 && buf[131] == 77) 
			return "DICOM"; 
		
		if (b0 == 8 && b1 == 0 && b3 == 0) 
			return "DICOM"; 
		
		if (b0 == 83 && b1 == 73 && b2 == 77 && b3 == 80) 
			return "FITS"; 
		
		if (b0 == 80 && (b1 == 50 || b1 == 53) && (b2 == 10 || b2 == 13 || b2 == 32 || b2 == 9)) 
			return "PGM"; 
		
		if ( b0 == 66 && b1 == 77)
			return "BMP"; 
		
		if (b0 == 73 && b1 == 111)
			return "ROI"; 
		
		if (b0 >= 32 && b0 <= 126 && b1 >= 32 && b1 <= 126 && b2 >= 32 && b2 <= 126 
			&& b3 >= 32 && b3 <= 126 && buf[8] >= 32 && buf[8] <= 126)
			return "TEXT"; 
		
		if (b0 == 137 && b1 == 80 && b2 == 78 && b3 == 71) 
			return "PNG"; 
		
		return "UNKNOWN"; 
	}
	
}


