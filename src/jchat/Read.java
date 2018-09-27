/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jchat;

/**
 *
 * @author VIDIT
 */
public class Read extends Thread{
    private final String message;
    private final Thread t;
    
    Read(String mes)
    {
        this.message=mes;
        t=new Thread(this);
        t.start();
    }
    public void run()
    {
        new Caller().system("speak \"you got a reply "+message+" \"", true);
    }
}
