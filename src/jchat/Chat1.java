package jchat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;

class Chat1 implements Runnable
{
	Socket s;
	PrintStream ps;
	BufferedReader br;	
        ServerSocket ss1;
	Socket s1;
	BufferedReader br1;
	PrintStream ps1;
	Thread t;
        private gui Gui;
        private final String IP;
	Chat1(gui gu,String ip)
	{
            this.Gui=gu;
            this.IP=ip;
            t=new Thread(this);
            t.start();	
	}
	public static void main(String args[])
	{
		//Chat1 c=new Chat1();
	}
	public void send(String mes) throws Exception
	{
		s=new Socket(IP,2000);
		ps=new PrintStream(s.getOutputStream());
                ps.println(mes);
                System.out.println("2 sent:"+mes);
		//br=new BufferedReader(new InputStreamReader(s.getInputStream()));
                //System.out.println(br.readLine());
		//br.close();
		ps.close();
		s.close();
	}
	public void run()
	{
		try
		{
                    rev();
		}
		catch(Exception e)
		{
		}
	}
	public void rev() throws Exception
	{
		for(;;)
		{
			ss1=new ServerSocket(2010);
			System.out.println("waiting");
			s1=ss1.accept();
			br1=new BufferedReader(new InputStreamReader(s1.getInputStream()));
			String s=br1.readLine();
                        Gui.mes(s,1);
                        System.out.println("2 rev:"+s);
                        //ps1=new PrintStream(s1.getOutputStream());
			br1.close();
			//ps1.close();
			ss1.close();
			s1.close();
			try
			{
				Thread.sleep(1000);
			}
			catch(Exception e)
			{
                            
                        }
		}
	}
}
