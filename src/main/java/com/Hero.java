package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Hero {
	private String name; 
    protected float hp;
     
    public static void main(String[] args) {
        ADHero ad = new ADHero();
        Hero h = ad;
        AD adi = (AD) h;

        Hero h2=new Hero();
        h2.name="dada";
        System.out.println(h2.name);
        
        
        File f = null;
        String[] paths;
              
        try {    
        
           // create new file
           f = new File("C:/Users/hwu/");
                                   
           // array of files and directory
           paths = f.list();
              
           // for each name in the path array
           for(String path:paths) {
           
              // prints filename and directory name
              System.out.println(path);
           }
           
           File f1=new File("C:/Users/hwu/test11.txt");
           
           if (!f1.exists()) {
               f1.createNewFile();
           }
           
           
           FileInputStream fis=new FileInputStream(f1);          
           byte[] all =new byte[(int) f1.length()];
           
           //以字节流的形式读取文件所有内容           
           fis.read(all);
           for(byte b:all) {
        	   System.out.println(b);
           }
        	  
           fis.close();
                    
           byte[] data= { 88, 89 };
           
           // 创建基于文件的输出流
           FileOutputStream fos = new FileOutputStream(f1);
           // 把数据写入到输出流
           fos.write(data);
           // 关闭输出流
           fos.close(); 
                        
           
        } catch(IOException e) {
           
           // if any error occurs
           e.printStackTrace();
        }
        
    }
}

