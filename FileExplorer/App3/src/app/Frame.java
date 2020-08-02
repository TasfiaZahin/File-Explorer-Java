/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class Frame {
    private static Frame instance=null;
    private JFrame frame;
    private JButton button1;
    private JButton button2;
    private JTextField text;
    private Frame()
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                frame=new JFrame("File Explorer");
                frame.setSize(700, 500);
                frame.setLocation(450, 100);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
               
                JLabel label=new JLabel("Current Directory");
                label.setBounds(10, 10, 100, 20);
                text=new JTextField(20);
                text.setEditable(false);
                text.setBounds(120,10,550,20);
                
                button1 = new JButton("List View");
                button2 = new JButton("Table View");
                button1.setBounds(10,40,120,20);
                button2.setBounds(140,40,120,20);
                
                   
                JPanel panel=new JPanel(new GridLayout(1,0));
                
                JSplitPane pane_h=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
                pane_h.setDividerLocation(70);
                pane_h.setPreferredSize(new Dimension(500,300));
                
                JSplitPane pane_v=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
                pane_v.setDividerLocation(170);
                pane_v.setPreferredSize(new Dimension(500,300));
                
                TreeView tr=new TreeView(text);
                
                JScrollPane sp1=new JScrollPane(tr.get_tree());
                sp1.setPreferredSize(new Dimension(100,50));
                
                TableView tb=new TableView(text);
                
                JScrollPane sp2=new JScrollPane(tb.get_table());
                sp2.setPreferredSize(new Dimension(100,50));
                
                ListView tl=new ListView(text);
                
                JScrollPane sp3=new JScrollPane(tl.get_list());
                sp3.setPreferredSize(new Dimension(100,50));
                
                pane_v.setLeftComponent(sp1);
                pane_v.setRightComponent(sp2);
                
                button1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        pane_v.remove(sp2);
                        pane_v.setRightComponent(sp3);
                    }

                });
                
                button2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        pane_v.remove(sp3);
                        pane_v.setRightComponent(sp2);
                    }
                });
                
                Container cont=new Container();
                cont.setSize(30, 50);
                cont.add(text);
                cont.add(label);
                cont.add(button1);
                cont.add(button2);
                
                pane_h.setTopComponent(cont);
                pane_h.setBottomComponent(pane_v);
                panel.add(pane_h);                
                frame.add(panel);    
            }
        });
        
    }
    static public Frame get_instance()
    {
        if(instance==null)
        {
            instance=new Frame();
        }
        return instance;
    }
}
