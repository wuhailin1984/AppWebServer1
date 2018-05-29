package com.service;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.db.DBManager;

@ServerEndpoint(value="/websocketTemp")
public class ProcessWebClient{
    
    private static String userId;
    
    //连接时执行
    @OnOpen
    public void onOpen(@PathParam("userId") String userId,Session session) throws IOException{
        this.userId = userId;
    }
    
    //关闭时执行
    @OnClose
    public void onClose(){
    }
    
    //收到消息时执行
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        //session.getBasicRemote().sendText("收到 "+this.userId+" 的消息 "); //repose to the client
    	System.out.println("received sth from web client");
    	if( message.equals("Sensor1") ||message.equals("Sensor2")  ) {
    		String nm= message;
    		DBManager sql = DBManager.createInstance();
    		sql.connectDB();  
    		String QueryDataSql= "select * from temp_data where id_temp_data =(select max(id_temp_data) from temp_data where name_temp_data='"+nm+"')";
    		try {
    			ResultSet rs = sql.executeQuery(QueryDataSql);
    			if (rs.next()) {
    				
    				String data=rs.getString("value_temp_data");  
    				String dataTime=rs.getString("time_temp_data");
    				session.getBasicRemote().sendText(data+"***"+dataTime+nm); //repose to the client
    				System.out.println(data+"***"+dataTime+nm);			 
    				}
    			sql.closeDB();
    		} 	catch (SQLException e) {
    				e.printStackTrace();
    			} 
    		
    		
    		/*
    		String MaxIDSql= "select max(id_temp_data) from temp_data";
    		try {
    			ResultSet rs = sql.executeQuery(MaxIDSql);
    			if (rs.next()) {
    				int maxId=Integer.parseInt( (rs.getString("max(id_temp_data)")) );
    				String QueryDataSql = "select * from temp_data where id_temp_data ='" + maxId + "'";
    				ResultSet data_rs = sql.executeQuery(QueryDataSql);
        			if (data_rs.next()) {
        				String data=rs.getString("value_temp_data");  
        				session.getBasicRemote().sendText(data); //repose to the client
        				}				 
    				}
    			sql.closeDB();
    		} 	catch (SQLException e) {
    				e.printStackTrace();
    			}  
    			*/ 		
    	}   	  	
    	
    }
    
    //连接错误时执行
    @OnError
    public void onError(Session session, Throwable error){
        error.printStackTrace();
    }

}
