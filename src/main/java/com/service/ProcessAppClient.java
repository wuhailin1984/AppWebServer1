package com.service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

import com.bean.OneSensorTemp;
import com.bean.SensorTempArray;

//Serves a client
class ProcessAppClient extends Thread{
    Socket socket = null;
    SensorTempArray  handelToSenTempArr=null; 

    public ProcessAppClient(Socket socket, SensorTempArray senTempArr ){  
        super();
        this.socket = socket;
        handelToSenTempArr = senTempArr;   
    }

    @Override
    public void run(){
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            while(true){
                String msgRecv = dis.readUTF();
                if(msgRecv==null)
                  break;
                System.out.println("****msg from app client:" + msgRecv);

                OneSensorTemp senTemp=handelToSenTempArr.findBySensorName(msgRecv);
                if(senTemp == null)                  
                  dos.writeUTF(    "No data for this rum"  );   
                else     {
                	Date thisTime=new Date();
                	long diff=thisTime.getTime() - senTemp.GetLastHeartBeat().getTime();
                	if(  diff < 10000)         		
                		dos.writeUTF(    String.format("%.2f", senTemp.GetTemp() )  ); 
                	else
                		dos.writeUTF(  "Temperature data expired" ); 
                	dos.flush();
                }          
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
