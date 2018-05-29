package com.service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.PreparedStatement;  

import com.db.DBManager;

import com.bean.SensorTempArray;
import com.db.DBManager;

//Serves a client
class ProcessSensorClient extends Thread{
    Socket socket = null;
    SensorTempArray  handelToSenTempArr=null;  

    public ProcessSensorClient(Socket socket, SensorTempArray senTempArr ){  
        super();
        this.socket = socket;
        handelToSenTempArr = senTempArr;    
    }

    @Override
    public void run(){
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            while( true ){
              byte[] dataTemp=new byte[10];
              int length = dis.read( dataTemp );
              if(length == -1)
                break;  //this inputstream ends
                /*
              if( dataTemp[0] != (byte)(0x88))
              {
                dos.writeUTF(    "Wrong message head from sensor, Server discard this messages"  );
                continue;       //wrong head, something wrong, try to read till end then re-read message
              }*/
              String nm=null;
              switch( dataTemp[0] )
              {
                case (byte)0x88: nm="Sensor1"; break;   //Office
                case (byte)0x89: nm="Sensor2"; break;   //Home
                default: break;
              }
              if(nm == null){
                dos.writeUTF(    "Wrong message head from sensor, Server discard this messages"  );
                continue;       //wrong head, something wrong, try to read till end then re-read message
              }

              if (length != 5 )
              {
                dos.writeUTF(    "Wrong message length from sensor, Server discard this messages"  );
                continue;       //wrong head, something wrong, try to read till end then re-read message
              }
              System.out.println("Receive from sensor: " + String.format("%x",dataTemp[0] ) + " "
                              + String.format("%x",dataTemp[1] )+" " + String.format("%x",dataTemp[2] )
                              + " " +String.format( "%x",dataTemp[3] ) + " " +String.format( "%x",dataTemp[4] ));

              int tmp= ( (0xFF&dataTemp[4])<<24 ) +( (0xFF&dataTemp[3])<<16 ) + ( (0xFF&dataTemp[2])<<8 ) + (0xFF&dataTemp[1]);
              //System.out.println("dataTemp[2]: " + String.format("%x",dataTemp[2] ) );
              //int tmp=  ( (int) (dataTemp[2]) )*256 + ( int ) (dataTemp[1]);
              //System.out.println("tmp is: "+ tmp);

              double tmpValue= ( (double)tmp)*0.0625;
              
              Date thisHeartBeat=new Date();
              handelToSenTempArr.Add(nm,tmpValue,thisHeartBeat);                
              
              System.out.println("tmpValue is: "+ String.format("%3.2f", tmpValue));
              dos.writeUTF(    "Server dealed a message from sensor"  );
              dos.flush();                           
              
              java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
              String currentTime = sdf.format(thisHeartBeat);
              
              String InsertDataSql =  "insert into temp_data values(NULL,'"+ tmpValue+ "','"+ currentTime+ "','"+ nm+ "') ";

              // get database instance
              DBManager sql = DBManager.createInstance();
              sql.connectDB();

              int ret = sql.executeUpdate(InsertDataSql);
              if (ret != 0) {
                  sql.closeDB();
              }
              sql.closeDB();
              
              }

        } catch (IOException e) {
            System.out.println("Communication to sensor fails.");
            e.printStackTrace();
        }

        System.out.println("I am still alive.");

    }
}