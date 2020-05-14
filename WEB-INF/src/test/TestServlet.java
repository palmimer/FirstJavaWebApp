package test;

import java.io.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.util.Properties;
import java.util.Base64;
 
public class TestServlet extends HttpServlet {
	
	private static final String CONFIG_LOCATION = "C:\\Program Files\\Java\\apache-tomcat-10.0.0-M4\\webapps\\FirstJavaWebApp\\config.conf";
	//ezeknek az init metódus ad értéket
	private String USERNAME;
	private String PASSWORD;
	
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
			    throws IOException, ServletException {
					
		// check if the authorization data in request header is good
		boolean success = checkAuth(request);
		
		if(success){
					   
			// Set the response message's MIME type (here we set it to html)
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
		  
			// Allocate an output writer to write the response message into the network socket
			// létrehozunk egy PrintWriter típusú objektumot, ami a response csomagnak az írógépe
			// beletehetek pl html szerkezetű szöveget
			PrintWriter out = response.getWriter();
	 
			// Write the response message, in an HTML page
			try {
				out.println("<!DOCTYPE html>");
				out.println("<html><head>");
				out.println("<meta http-equiv='Content-Type' content='text/html' charset='UTF-8'>");
				out.println("<title>Üdvözlet</title></head>");
				out.println("<body>");
				out.println("<h1>Helló te ló!</h1>");  // says Hello
				// Echo client's request information
				out.println("<p>Request URI: " + request.getRequestURI() + "</p>");
				out.println("<p>Protocol: " + request.getProtocol() + "</p>");
				out.println("<p>PathInfo: " + request.getPathInfo() + "</p>");
				out.println("<p>Remote Address: " + request.getRemoteAddr() + "</p>");
				// Generate a random number upon each request
				out.println("<p>A Random Number: <strong>" + Math.random() + "</strong></p>");
				out.println("</body>");
				out.println("</html>");
			} finally {
				out.close();  // Always close the output writer
			}
				
		} else {
			noAccess(response);
		}
		
	} 

	// a jogosultság megvizsgálása
	private boolean checkAuth(HttpServletRequest request){
		String authValue = request.getHeader("Authorization");
		// megnézzük megvan-e ez az info a headerben, ha nincs: false
		if(authValue == null){
			return false;
		} else {
			//ha van ilyen érték, megnézzük jó-e
			// ha nem Basic szóval kezdődik, nem jó
			if(!authValue.toUpperCase().startsWith("BASIC")) return false;
			else{
				// kiszedjük a Basic nélküli dekódolt username:password szópárt
				String userpass = new String(Base64.getDecoder().decode(authValue.substring(6)));
				int index = userpass.indexOf(":");
				String observedUsername = userpass.substring(0,index);
				String observedPassword = userpass.substring(index + 1);
				if(observedUsername.equals(USERNAME) && observedPassword.equals(PASSWORD)) return true;
			}
		}
		return false;
	}
	
	// jogosultság megtagadása
	private void noAccess(HttpServletResponse response){
		try{
			response.sendError(401);
		} catch (Exception e){
			System.out.println("" + e);
		}
	}
	
	//ez az init metódus csak egyszer fut le, amikor a szervlet létrejön	
    @Override
    public void init(){
	    try{
		    //beimportálom a properties osztályt, és csinálok egy objektumot
			Properties pr = new Properties();
			//beimportálom a config file-t
		    File conf = new File(CONFIG_LOCATION);
		    if (conf.exists() && conf.canRead()){
			   System.out.println("Mondom a file-ból kiszedett adatokat:");
			   //a pr objektumnak átadom a file-t, és kiszedem belőle az adatokat
			   pr.load(new FileInputStream(conf));
			   this.USERNAME = pr.getProperty("username");
			   this.PASSWORD = pr.getProperty("password");
			   System.out.println("user: "  + USERNAME + ", password: " + PASSWORD);
			   
		    }else{
			  System.out.println("Nincs meg a fájl");
		    }
	    } catch (Exception e){
			e.printStackTrace(System.err);
	    }
    }
   
    @Override
    public void destroy(){
	 
    }
   
    @Override
    public String getServletInfo(){
		return "Ez egy teszt szervlet";
    }
   
}