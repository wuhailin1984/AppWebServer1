package com.bean;

import java.util.Date;
import java.util.Vector;


public class SensorTempArray {
    private Vector<OneSensorTemp> sensorTempArr =null;
    public SensorTempArray( ){
      sensorTempArr = new Vector<OneSensorTemp>();
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

    public void Add(String nm, double senValue, Date thisHeartBeat ) {
      OneSensorTemp senTemp=findBySensorName(nm);
      if( senTemp!=null)
        senTemp.AddTemp(senValue,thisHeartBeat);
      else{
         senTemp=new OneSensorTemp(nm);
         //System.out.println("SensorTemp Array add method called." + nm);

         senTemp.AddTemp(senValue, thisHeartBeat);
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