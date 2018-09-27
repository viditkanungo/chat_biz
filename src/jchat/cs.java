/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jchat;

//Receiver program
import java.awt.Button;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


class doComms1 implements Runnable
{
    Socket s;
    ServerSocket ss;
    gui Gui;
    doComms1(gui tu)
    {
        this.Gui=tu;
    }
    public void run ()
    {
        for(;;)
        {
        try 
        {
            ss=new ServerSocket(7860);
            System.out.println("Server Started");
            s=ss.accept();
            RandomAccessFile fout = null;
            File file = null;
            DataInputStream dis = null;
            PrintStream out1 = null;
            try 
            {
                dis = new
                DataInputStream (s.getInputStream());
                out1 = new PrintStream(s.getOutputStream());
                String str,extn="";
                str=dis.readUTF();
                System.out.println("\n"+str);
                int flag=0,i;
                for(i=0;i<str.length();i++)
                {
                    if(str.charAt(i)=='.' || flag==1)
                    {
                        flag=1;
                        extn+=str.charAt(i);
                    }
                }
                if(extn.equals(".jpg") || extn.equals(".png"))
                {
                    file = new File("Re:"+str);
                    fout = new RandomAccessFile("Re"+file.getName(),"rw");
                    byte[] readData = new byte[1024];
                    while((i = dis.read(readData)) != -1)
                    {
                        fout.write(readData, 0, i);
                        if(flag==1)
                        {
                            System.out.println("Image HasBeen Received");
                            flag=0;
                        }
                        //System.out.println("error e4");
                    }
                    dis.close();
                    out1.close();
                    s.close();
                    ss.close();
                    System.out.println("error e1");
                    fout.close();
                    new vc("Re"+file.getName(),this.Gui);
                }
            } 
            catch (IOException ioe)
            {
                try {
                    dis.close();
                    out1.close();
                    s.close();
                    ss.close();
                    fout.close();
                    new vc("Re"+file.getName(),this.Gui);
                    System.out.println("error e");
                } catch (IOException ex) {
                    Logger.getLogger(doComms1.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        } 
        catch (IOException ex)
        {
            Logger.getLogger(doComms1.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    }
}

class Clientv1
{
Socket s;
DataInputStream din;
DataOutputStream dout;
String str;
//*******************************Client1 GUI*********************************//
TextField tf;
TextArea ta;
Label lb;
Button b;
    private final String IP;
public Clientv1(gui uui,String ip)
{
    //new Server1(uui);
    doComms1 connection;
    this.IP=ip;
                doComms1 conn_c= new doComms1(uui);
                Thread t = new Thread(conn_c);
                t.start();
}

public void send(String fil)
{
    
    try 
    {
        s=new Socket(IP,7862);
        System.out.println(s);
        din=new DataInputStream(s.getInputStream());
        dout=new DataOutputStream(s.getOutputStream());
        String fileName;
        fileName=fil;
        int flag=0,i;
        String extn="";
        for(i=0;i<fileName.length();i++)
        {
            if(fileName.charAt(i)=='.' || flag==1)
            {
                flag=1;
                extn+=fileName.charAt(i);
            }
        }
        if(extn.equals(".jpg") || extn.equals(".png"))
        {
            try
            {
                File file = new File(fileName);
                FileInputStream fin = new
                FileInputStream(file);
                dout.writeUTF(fileName);
                System.out.println("Sendingimage...");
                byte[] readData = new byte[1024];
                while((i = fin.read(readData)) != -1)
                {
                    dout.write(readData, 0, i);
                }
                System.out.println("Imagesent");
                dout.close();
                din.close();
                s.close();
                fin.close();
            }
            catch (IOException ex)
            {
                Logger.getLogger(Clientv1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
}
}