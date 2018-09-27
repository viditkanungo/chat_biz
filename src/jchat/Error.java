/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jchat;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author VIDIT
 */

public class Error extends Thread{
   
    public void err(Object j,String m)
    {
        JOptionPane.showMessageDialog((Component) j,m,"ERROR", 0);
        UIManager.put("OptionPane.background", Color.cyan);
         UIManager.put("Panel.background", Color.cyan);
      UIManager.put("Button.background", Color.white);
         
    }
    public void con(Object j,String m)
    {
        JOptionPane.showMessageDialog((Component) j,m,"CONFIRMATION", 1);
        UIManager.put("OptionPane.background", Color.cyan);
         UIManager.put("Panel.background", Color.cyan);
      UIManager.put("Button.background", Color.white);
        
  
    }
    public void war(Object j,String m)
    {
        JOptionPane.showMessageDialog((Component) j,m,"WARNING", 2);
        UIManager.put("OptionPane.background", Color.cyan);
         UIManager.put("Panel.background", Color.cyan);
      UIManager.put("Button.background", Color.white);
    }
    public void un(Object j,String m)
    {
        JOptionPane.showMessageDialog((Component) j,m,"UNKNOWN", 3);
        UIManager.put("OptionPane.background", Color.cyan);
         UIManager.put("Panel.background", Color.cyan);
      UIManager.put("Button.background", Color.white);
    }
    public int cond(Object j,String m)
    {
        int i=JOptionPane.showConfirmDialog((Component) j, m, "CONFIRMATION", JOptionPane.YES_NO_CANCEL_OPTION, 2);
        UIManager.put("OptionPane.background", Color.cyan);
         UIManager.put("Panel.background", Color.cyan);
      UIManager.put("Button.background", Color.white);
        return i;
    }
     public String indt(Object j,String m)
    {
        String i;
        i = JOptionPane.showInputDialog((Component) j,m, "INPUT", 3);
        UIManager.put("OptionPane.background", Color.cyan);
         UIManager.put("Panel.background", Color.cyan);
      UIManager.put("Button.background", Color.white);
        return i;
    }
     public String indc(Object j,String m,Object[] ol)
    {
        String i;
        i = (String) JOptionPane.showInputDialog((Component) j, m, "INPUT", 3, null, ol,null);
        UIManager.put("OptionPane.background", Color.cyan);
         UIManager.put("Panel.background", Color.cyan);
      UIManager.put("Button.background", Color.white);
        return i;
    }
    
}
