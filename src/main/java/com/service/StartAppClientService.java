package com.service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.bean.SensorTempArray;

//Server fot the app clients
public class StartAppClientService extends Thread{
    ServerSocket serverSocket = null;
    public final int port = 3000;
    SensorTempArray  handelToSenTempArr=null;  

    public StartAppClientService(SensorTempArray senTempArr){  
        super();
        handelToSenTempArr=senTempArr;  

        try {
            InetAddress addr = InetAddress.getLocalHost();
            //InetAddress addr = InetAddress.getByName("10.46.40.128");
            System.out.println("Start server app at local host:"+addr);
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    @Override
    public void run(){
      Socket socket = null;
      try {
          System.out.println("****waiting for apps...");
          //wait for connect, every new connect, creat a thread
          while(true){
              socket = serverSocket.accept();//wait for a client,block before connect from a client
              System.out.println("****App connect to"+socket.getInetAddress()+":"+socket.getLocalPort());
              new ProcessAppClient(socket, this.handelToSenTempArr).start(); 
            }

          } catch (IOException e) {
              // TODO Auto-generated catch block
                System.out.println("IOException");
                e.printStackTrace();
          	}finally {
        	  try{
        		  if(socket!=null)socket.close();
        	  }catch (IOException e) {e.printStackTrace();}
          	}      
      }
  }