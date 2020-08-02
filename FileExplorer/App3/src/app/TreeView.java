/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author acer
 */
public class TreeView {
    
    public static Map<String,String> map = new HashMap<String,String>();
    public static Map<String,ImageIcon> map2= new HashMap<String,ImageIcon>();
    
    JTree tree;
    JTextField text;
    
    public TreeView(JTextField t)
    {
        text=t;
        DefaultMutableTreeNode top =new DefaultMutableTreeNode("My PC");
        
        File file=new File("E://Tas");
        
        CreateNodes(top,file);
        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode
            (TreeSelectionModel.SINGLE_TREE_SELECTION);
        
        tree.addMouseListener(mouseListener);
    }
    
    public JTree get_tree()
    {
        return tree;
    }
    
    public String get_path()
    {
        return tree.getLastSelectedPathComponent().toString();
    }
    
     MouseListener mouseListener = new MouseAdapter() {
      public void mousePressed(MouseEvent evt) {
        //Returns the last path element of the selection.
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                           tree.getLastSelectedPathComponent();

        if (node == null)
        {     
            return;
        }
        text.setText(node.toString());
        
        if(node.toString()=="My PC")
        {
            int s=TableView.model.getRowCount();

            for(int i=s-1;i>=0;i--)
            {
                TableView.model.removeRow(i);
            }
            int p=ListView.list_model.getSize();
            for(int i=p-1;i>=0;i--)
            {
                ListView.list_model.removeElementAt(i);
            }
        
            for(int i=0;i<node.getChildCount();i++)
            {
                File f=new File(node.getChildAt(i).toString());
                ImageIcon icon=(ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(f);
                String name=f.getName();
                String size=Long.toString(f.length());
                //String date=Long.toString(files[i].lastModified());
                SimpleDateFormat simp_date =new SimpleDateFormat("dd/MM/yyyy");
                String date=simp_date.format(f.lastModified());
                TableView.model.addRow(new Object []{icon,name,size,date});
                ListView.list_model.addElement(name);
            }
            return;
        }
        
        File file=new File(node.toString());
        File [] files= file.listFiles();
        int s=TableView.model.getRowCount();

        for(int i=s-1;i>=0;i--)
        {
            TableView.model.removeRow(i);
        }
        int p=ListView.list_model.getSize();
        for(int i=p-1;i>=0;i--)
        {
            ListView.list_model.removeElementAt(i);
        }
        
        if(files!=null)
        {
            for(int i=0;i<files.length;i++)
            {
                ImageIcon icon=(ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(files[i]);
                String name=files[i].getName();
                String size=Long.toString(files[i].length());
                //String date=Long.toString(files[i].lastModified());
                SimpleDateFormat simp_date =new SimpleDateFormat("dd/MM/yyyy");
                String date=simp_date.format(files[i].lastModified());
                TableView.model.addRow(new Object []{icon,name,size,date});
                ListView.list_model.addElement(name);
            }
        }
    }
    };
    private void CreateNodes(DefaultMutableTreeNode top, File file) {
       //System.out.println(file.getPath());
       DefaultMutableTreeNode node =new DefaultMutableTreeNode(file.getPath());
       top.add(node);
       
       map.put(file.getName(), file.getPath());
       map2.put(file.getName(), (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(file));
       
       File [] files= file.listFiles();
       
       if(files==null)
       {
           return;
       }
       else   
       {
           for(int i=0;i<files.length;i++)
           {
               CreateNodes(node,files[i]);
           }
       }
    }
}
