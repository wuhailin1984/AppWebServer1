package com.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.SensorTempArray;
import com.service.StartAppClientService;
import com.service.StartSensorService;

/**
 * Servlet implementation class StartSensorTempServicesLet
 */
@WebServlet("/StartSensorTempServicesLet")
public class StartSensorTempServicesLet extends HttpServlet {
	
    StartAppClientService appService=null;
    StartSensorService sensorService=null;
    SensorTempArray SenTempArr=null;
		
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StartSensorTempServicesLet() {
        super();
        // TODO Auto-generated constructor stub
    }   
    
    public void init(ServletConfig config) throws ServletException { 
    	SenTempArr=new SensorTempArray();
        appService = new StartAppClientService(SenTempArr);
        sensorService =new StartSensorService(SenTempArr);
        appService.start();
        sensorService.start();
 	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
