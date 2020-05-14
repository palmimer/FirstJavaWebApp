package test;

import java.io.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.util.Properties;
 
public class TestServlet extends HttpServlet {
	
	private static final String CONFIG_LOCATION = "C:\\Program Files\\Java\\apache-tomcat-10.0.0-M4\\webapps\\TestServlet\\config.conf";
	
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
			    throws IOException, ServletException {
				   
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
			   System.out.println("Mondom a fájlból kiszedett adatokat:");
			   //a pr objektumnak átadom a file-t, és kiszedem belőle az adatokat
			   pr.load(new FileInputStream(conf));
			   String username = pr.getProperty("dbusername");
			   String password = pr.getProperty("dbpassword");
			   System.out.println("user: "  + username + ", password: " + password);
			   
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