package com;

import java.util.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;
import java.net.InetAddress;

import java.io.DataInputStream;
import java.io.DataOutputStream;


    //Server fot the app clients
    class StartAppClientService extends Thread{
        ServerSocket serverSocket = null;
        public final int port = 3000;
        //OneSensorTemp handelToSenTemp=null;   //*******after change from oneSensorTemp to SensorTempArray
        SensorTempArray  handelToSenTempArr=null;  //*******after change from oneSensorTemp to SensorTempArray

        public StartAppClientService(SensorTempArray senTempArr){  //*******after change from oneSensorTemp to SensorTempArray
            super();
            //handelToSenTemp = senTemp; //*******after change from oneSensorTemp to SensorTempArray
            handelToSenTempArr=senTempArr;  //*******after change from oneSensorTemp to SensorTempArray

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
          try {
              Socket socket = null;
              System.out.println("****waiting for apps...");
              //wait for connect, every new connect, creat a thread
              while(true){
                  socket = serverSocket.accept();//wait for a client,block before connect from a client
                  System.out.println("****App connect to"+socket.getInetAddress()+":"+socket.getLocalPort());
                  new ProcessAppClient(socket, this.handelToSenTempArr).start(); //*******after change from oneSensorTemp to SensorTempArray
                }

              } catch (IOException e) {
                  // TODO Auto-generated catch block
                    System.out.println("IOException");
                    e.printStackTrace();
              }
          }
      }


    //Serves a client
    class ProcessAppClient extends Thread{
        Socket socket = null;
        //OneSensorTemp handelToSenTemp=null;  //*******after change from oneSensorTemp to SensorTempArray
        SensorTempArray  handelToSenTempArr=null;  //*******after change from oneSensorTemp to SensorTempArray

        public ProcessAppClient(Socket socket, SensorTempArray senTempArr ){  //*******after change from oneSensorTemp to SensorTempArray
            super();
            this.socket = socket;
            handelToSenTempArr = senTempArr;   //*******after change from oneSensorTemp to SensorTempArray
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

                     //to be modified
                    if( msgRecv.equals( "Sensor1") ){
                      System.out.println("****received from app client:"+msgRecv);
                      OneSensorTemp senTemp=handelToSenTempArr.findBySensorName(msgRecv); //*******after change from oneSensorTemp to SensorTempArray
                      if(senTemp == null)                   //*******after change from oneSensorTemp to SensorTempArray
                        dos.writeUTF(    " wrong room or sensor isn't working"  );   //*******after change from oneSensorTemp to SensorTempArray
                      else                          //*******after change from oneSensorTemp to SensorTempArray
                        dos.writeUTF(    String.format("%.2f", senTemp.GetTemp() )  ); //*******after change from oneSensorTemp to SensorTempArray
                      dos.flush();
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    //Deal with sensor
    class StartSensorService extends Thread{
        ServerSocket serverSocket = null;
        public final int port = 3001;
        //OneSensorTemp handelToSenTemp=null;  //*******after change from oneSensorTemp to SensorTempArray
        SensorTempArray  handelToSenTempArr=null;  //*******after change from oneSensorTemp to SensorTempArray

        public StartSensorService(SensorTempArray senTempArr){   //*******after change from oneSensorTemp to SensorTempArray
            super();
            handelToSenTempArr = senTempArr;      //*******after change from oneSensorTemp to SensorTempArray

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
                  new ProcessSensorClient(socket, this.handelToSenTempArr).start(); //*******after change from oneSensorTemp to SensorTempArray
                }

              } catch (IOException e) {
                  // TODO Auto-generated catch block
                    System.out.println("IOException");
                    e.printStackTrace();
              }
          }
        }

        //Serves a client
        class ProcessSensorClient extends Thread{
            Socket socket = null;
            //OneSensorTemp handelToSenTemp=null;  //*******after change from oneSensorTemp to SensorTempArray
            SensorTempArray  handelToSenTempArr=null;  //*******after change from oneSensorTemp to SensorTempArray

            public ProcessSensorClient(Socket socket, SensorTempArray senTempArr ){   //*******after change from oneSensorTemp to SensorTempArray
                super();
                this.socket = socket;
                handelToSenTempArr = senTempArr;          //*******after change from oneSensorTemp to SensorTempArray
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
                        case (byte)0x88: nm="Sensor1"; break;
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

                      /*
                      int tmp=(int) (dataTemp[2] )*256;
                      tmp =  (int)(dataTemp[1]) + tmp;
                      System.out.println("second tmp is: "+ tmp);
                      */

                      double tmpValue= ( (double)tmp)*0.0625;
                      //handelToSenTemp.AddTemp(tmpValue);   //*******after change from oneSensorTemp to SensorTempArray
                      handelToSenTempArr.Add(nm,tmpValue);   //*******after change from oneSensorTemp to SensorTempArray
                      System.out.println("tmpValue is: "+ String.format("%3.2f", tmpValue));
                      dos.writeUTF(    "Server dealed a message from sensor"  );
                      dos.flush();
                      }

                } catch (IOException e) {
                    System.out.println("Communicate to sensor fails.");
                    e.printStackTrace();
                }

                System.out.println("I am still alive.");

            }
        }



class OneSensorTemp {
  String SenName=null;
  Vector SenValue=null;
   public OneSensorTemp(String nm){
     SenName=nm;
     SenValue = new Vector();
   }
  public String GetSensorName(){
    return SenName;
  }

  public void AddTemp(double temp){
    if( (SenValue.size() )< Server.MAX_LENGTH){
      SenValue.addElement(temp);
    }
    else
    {
      //SenValueToFile();   This function is to be added
      SenValue.clear();
      SenValue.addElement(temp);
    }
  }

  public double GetTemp(){
    if( SenValue.size() > 0)
      return (double)SenValue.elementAt( SenValue.size()-1);
    else
      return 0.0f;
  }
}


class SensorTempArray {
      private Vector sensorTempArr =null;
      public SensorTempArray( ){
        sensorTempArr = new Vector();
      }
      public int size() {
        return sensorTempArr.size();
      }

      public boolean isEmpty() {
        return sensorTempArr.isEmpty();
        }

      public OneSensorTemp findBySensorName(String nm){
        OneSensorTemp tmp=null;
        String strnm=null;
        for(int i=0;i<size();i++){
          tmp=(OneSensorTemp) (sensorTempArr.elementAt(i));
          strnm=tmp.GetSensorName();
          if(strnm.equals(nm)   ){
              //System.out.println("findBySensorName method called and found a exist sensor." + strnm);
              return tmp;
            }
          }
        return null;
        }

      public void Add(String nm, double senValue ) {
        OneSensorTemp senTemp=findBySensorName(nm);
        if( senTemp!=null)
          senTemp.AddTemp(senValue);
        else{
           senTemp=new OneSensorTemp(nm);
           //System.out.println("SensorTemp Arrar add method called." + nm);

           senTemp.AddTemp(senValue);
           sensorTempArr.addElement(senTemp);
        }
      }

        public double GetTemp(String nm ) {
          OneSensorTemp senTemp=findBySensorName(nm);
          if( senTemp!=null)
            return senTemp.GetTemp();
          else
            return 0.0f;
      }
  }


public class Server extends Thread {
    StartAppClientService appService=null;
    StartSensorService sensorService=null;
    SensorTempArray SenTempArr=null;
    //OneSensorTemp oneSenTemp=null;  //*******after change from oneSensorTemp to SensorTempArray
    static int MAX_LENGTH=20;

    public Server(){
           //oneSenTemp = new OneSensorTemp("Sensor1");  //*******after change from oneSensorTemp to SensorTempArray
           SenTempArr=new SensorTempArray();
           appService = new StartAppClientService(SenTempArr);
           sensorService =new StartSensorService(SenTempArr);

      }
    
    @Override
	public void run() {
    	 //Server myServer=new Server();
        appService.start();
        sensorService.start();
    }
}
