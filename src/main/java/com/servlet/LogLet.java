package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

public class LogLet extends HttpServlet {

	 public LogLet(){
	        System.out.println("LogLet constructor called");
	    }
	
	 public void init(ServletConfig config) {
	        System.out.println("init(ServletConfig)");
	    }
	 
	 public void destroy() {
	        System.out.println("destroy()");
	    }
	 
	 
    private static final long serialVersionUID = 369840050351775312L;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	/* add by whl ,to be deleted
    	 response.setContentType("text/html");
    	 PrintWriter out = response.getWriter();
         out.println("<h1>  try try try </h1>");
        */
    	

        // receive data from clients
        String username = request.getParameter("username");
        username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
        String password = request.getParameter("password");
        System.out.println(username + "--" + password);

        // create a new service to serve this client
        Service serv = new Service();

        // validation
        boolean loged = serv.login(username, password);
        if (loged) {
            System.out.print("Succss");
            request.getSession().setAttribute("username", username);
            
            // send information to clients
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.print("Successed");
            //out.print("Username:" + username);
            //out.print("Password:" + password);
            //out.print("logined in");
            out.flush();
            out.close();
            
        } else {
            System.out.print("Failed");
            
            // send information to clients
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.print("Failed");
            //out.print("Username:" + username);
            //out.print("Password:" + password);
            //out.print("failed in logging");
            out.flush();
            out.close();
        }

    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    		
    	// receive data from clients
        String username = request.getParameter("username");
        username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
        String password = request.getParameter("password");
        System.out.println(username + "--" + password);

        // create a new service to serve this client
        Service serv = new Service();

        // validation
        boolean loged = serv.login(username, password);
        if (loged) {
            System.out.print("Succss");
            request.getSession().setAttribute("username", username);
            
            // send information to clients
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.print("Successed");
            //out.print("Username:" + username);
            //out.print("Password:" + password);
            //out.print("logined in");
            out.flush();
            out.close();
            
        } else {
            System.out.print("Failed");
            
            // send information to clients
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.print("Failed");
            //out.print("Username:" + username);
            //out.print("Password:" + password);
            //out.print("failed in logging");
            out.flush();
            out.close();
        }


    }

}