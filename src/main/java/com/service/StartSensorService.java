package com.service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.bean.SensorTempArray;

//Deal with sensor
public class StartSensorService extends Thread{
    ServerSocket serverSocket = null;
    public final int port = 3001;
    SensorTempArray  handelToSenTempArr=null; 

    public StartSensorService(SensorTempArray senTempArr){  
        super();
        handelToSenTempArr = senTempArr;     

        try {
            InetAddress addr = InetAddress.getLocalHost();
            //InetAddress addr = InetAddress.getByName("10.46.40.128");
            System.out.println("local host:"+addr);
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    @Override
    public void run(){

      try {
          System.out.println("waiting sensors...");
          //wait for connect, every new connect, creat a thread
          while(true){
              Socket socket = null;
              socket = serverSocket.accept();  //wait for a client,block before connect from a client
              System.out.println("Sensor connect to"+socket.getInetAddress()+":"+socket.getLocalPort());
              new ProcessSensorClient(socket, this.handelToSenTempArr).start(); 
            }

          } catch (IOException e) {
              // TODO Auto-generated catch block
                System.out.println("IOException");
                e.printStackTrace();
          }
      }
    }