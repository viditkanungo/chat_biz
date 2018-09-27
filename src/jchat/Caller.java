package jchat;

import java.io.BufferedInputStream;
import java.io.IOException;

/* CLASS THAT USES COMMAND PROMPT I.E USED FOR THE SYSTEM CALLS IN THE PROGRAM */ 

public class Caller 
{ 
    Caller()
    {
        
    }

 /* METHOD TO EXECUTE THE GIVEN STRING IN COMMAND PROMPT */ 

 public void system(String executer)  
 { 
  try 
  {
      Runtime r=Runtime.getRuntime(); 
      Process p=r.exec("cmd /c "+executer); 
  } 
  catch(IOException ie) 
  { 
      ie.printStackTrace(); 
  } 
 } 

 /* METHOD TO EXECUTE THE GIVEN STRING IN COMMAND PROMPT AND COPY THE EXECUTED TEXT TO THIS PROGRAM */ 

 public String system(String executer,boolean big)  
 { 
    int rr;
    String re=" "; 
    try 
    { 
        Runtime r=Runtime.getRuntime(); 
        Process p=r.exec("cmd /c "+executer);               
        try 
        { 
            BufferedInputStream bf = new BufferedInputStream(p.getInputStream()); 
            re=""; 
            while((rr=bf.read())!=-1) 
                re=re+((char)rr);
            return re;
        } 
        catch(IOException ie) 
        { 
            ie.printStackTrace(); 
        }   
    } 
    catch(IOException ie) 
    { 
       ie.printStackTrace(); 
    }
    return null;
 }
 
} 