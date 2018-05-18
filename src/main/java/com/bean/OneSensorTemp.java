package com.bean;

import java.util.Date;
import java.util.Vector;

public class OneSensorTemp {
	  String SenName=null;
	  boolean heartBeat=false;
	  Date lastHeartBeat=null;
	  
	  Vector< Double > SenValue=null;
	  
	  static int MAX_LENGTH=20;   //每个SensorTemp存储的最大温度数
	  
	   public OneSensorTemp(String nm){
	     SenName=nm;	     
	     SenValue = new Vector<Double>();
	   }
	  public String GetSensorName(){
	    return SenName;
	  }

	  public void AddTemp(double temp,Date thisHeartBeat){
		Double newOne=new Double(temp);
		lastHeartBeat=thisHeartBeat;
	    if( (SenValue.size() )< OneSensorTemp.MAX_LENGTH){    	
	    	SenValue.addElement(newOne);
	    }
	    else
	    {
	      //SenValueToFile();   This function is to be added
	      SenValue.clear();
	      SenValue.addElement(newOne);
	    }
	  }

	  public double GetTemp(){
	    if( SenValue.size() > 0)
	      return (double)SenValue.elementAt( SenValue.size()-1);
	    else
	      return 0.0f;
	  }
	  
	  public Date GetLastHeartBeat() {
		  return lastHeartBeat;
	  }
	  
	  
	}