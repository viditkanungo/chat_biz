/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jchat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;


public class ac extends Thread
{
    
    private FileInputStream in;
    private AudioStream as;
    private String file;
    private Thread t;
    ac(String name)
    {
        this.file=name;
        t=new Thread(this);
        t.start();
    }
    
    @Override
    public void run() {
        try {
            in= new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ac.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            as= new AudioStream(in);
        } catch (IOException ex) {
            Logger.getLogger(ac.class.getName()).log(Level.SEVERE, null, ex);
        }
        AudioPlayer.player.start(as);
    }
    public static void main(String args[])
    {
        new ac("voice2.jpg");
    }
}
