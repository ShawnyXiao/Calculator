package com.shawn.main;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.shawn.ui.CalculatorFrame;

/**
 * This is a scientific calculator.
 *
 * @author Shawn（肖小粤）
 * @version 1.2 2015-07-10
 */
public class Main
{
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                CalculatorFrame frame = new CalculatorFrame();
                frame.setTitle("Calculator");
                frame.setIconImage(new ImageIcon(getClass().getResource("/com/shawn/image/cal.png")).getImage());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);
                frame.setVisible(true);
            }
        });
    }
}