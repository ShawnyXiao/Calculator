package com.shawn.ui;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.shawn.core.CalculatorParser;

/**
 * calculator's frame
 *
 * @author Shawn（小粤）
 */
public class CalculatorFrame extends JFrame
{
    private static final int DEFAULT_WIDTH = 270;
    private static final int DEFAULT_HEIGHT = 380;

    CalculatorButton display;//a button that displays result

    public CalculatorFrame()
    {
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        display = new CalculatorButton("0");
        display.setEnabled(false);
        display.setHorizontalAlignment(JButton.RIGHT);
        this.add(display, new GBC(0, 0, 4, 1).setFill(GBC.BOTH).setWeight(100, 100));

        String[][] buttonString = new String[][]{
                {"CLEAR", "<-"},
                {"7", "8", "9", "÷"},
                {"4", "5", "6", "×"},
                {"1", "2", "3", "－"},
                {"0", ".", "＝", "＋"}};

        CalculatorButton[][] button = new CalculatorButton[buttonString.length][];
        for (int i = 0; i < buttonString.length; i++)
            button[i] = new CalculatorButton[buttonString[i].length];

        for (int i = 0; i < button.length; i++)
            for (int j = 0; j < button[i].length; j++)
            {
                button[i][j] = new CalculatorButton(buttonString[i][j]);
                if (4 == i && 2 == j)
                {
                    button[i][j].addActionListener(new EqualButtonAction());
                }
                else
                {
                    button[i][j].addActionListener(new OrdinaryButtonAction());
                }
            }

        for (int i = 0; i < button.length; i++)
            for (int j = 0; j < button[i].length; j++)
            {
                if (0 == i && 0 == j)
                {
                    this.add(button[i][j], new GBC(0, 1, 3, 1).setFill(GBC.BOTH).setWeight(100, 100));
                }
                else if (0 == i && 1 == j)
                {
                    this.add(button[i][j], new GBC(3, 1).setFill(GBC.BOTH).setWeight(100, 100));
                }
                else
                {
                    this.add(button[i][j], new GBC(j, i + 1).setFill(GBC.BOTH).setWeight(100, 100));
                }
            }
    }

    class OrdinaryButtonAction implements ActionListener
    {
        private String currentContent;

        @Override
        public void actionPerformed(ActionEvent e)
        {
            currentContent = display.getText();

            int tempChar = 0;
            String tempString = null;
            switch (e.getActionCommand())
            {
                case "CLEAR":
                    display.setText("0");
                    break;
                case "<-":
                    if (1 == currentContent.length())
                    {
                        display.setText("0");
                    }
                    else if (currentContent.equals("Invalid!"))
                    {
                        display.setText("0");
                    }
                    else
                    {
                        display.setText(currentContent.substring(0, currentContent.length() - 1));
                    }
                    break;
                case "0":
                    if (currentContent.equals("0"))
                    {
                        return;
                    }
                    else if (currentContent.equals("Invalid!") || currentContent.equals("∞"))
                    {
                        return;
                    }
                    else
                    {
                        display.setText(currentContent + e.getActionCommand());
                    }
                    break;
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                    if (currentContent.equals("0"))
                    {
                        display.setText(e.getActionCommand());
                    }
                    else if (currentContent.equals("Invalid!") || currentContent.equals("∞"))
                    {
                        return;
                    }
                    else
                    {
                        display.setText(currentContent + e.getActionCommand());
                    }
                    break;
                case "÷":
                case "×":
                case "－":
                case "＋":
                    tempChar = currentContent.codePointAt(currentContent.length() - 1);
                    if (!Character.isDigit(tempChar))
                    {
                        return;
                    }
                    else
                    {
                        display.setText(currentContent + e.getActionCommand());
                    }
                    break;
                case ".":
                    tempChar = currentContent.codePointAt(currentContent.length() - 1);
                    if (!Character.isDigit(tempChar))
                    {
                        return;
                    }
                    else
                    {
                        if (-1 != currentContent.lastIndexOf('.'))
                        {
                            tempString = currentContent.substring(currentContent.lastIndexOf('.') + 1, currentContent.length());
                            if (!tempString.matches(".*[÷×－＋].*"))
                            {
                                return;
                            }
                        }
                        display.setText(currentContent + e.getActionCommand());
                    }
                    break;
            }
        }
    }

    class EqualButtonAction implements ActionListener
    {

        CalculatorParser calculatorParser = new CalculatorParser();
        public String currentContent;

        @Override
        public void actionPerformed(ActionEvent e)
        {
            currentContent = display.getText();
            if (currentContent.equals("∞"))
            {
                return;
            }
            else if (!Character.isDigit(currentContent.codePointAt(currentContent.length() - 1)))
            {
                display.setText("Invalid!");
                return;
            }

            display.setText(calculatorParser.getResult(calculatorParser.getRPN(display.getText())));
        }

    }
}