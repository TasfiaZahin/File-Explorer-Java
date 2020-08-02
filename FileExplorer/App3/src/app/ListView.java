/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.text.SimpleDateFormat;
import static java.util.Collections.list;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Renderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author acer
 */
public class ListView implements ListSelectionListener{
    public static  DefaultListModel list_model=new DefaultListModel();
    JTextField text;
    JList list;
    public ListView(JTextField t)
    {
        text=t;
        list=new JList(list_model);
        list.setCellRenderer(new L_Renderer());
        list.setVisibleRowCount(-1);
        list.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
        list.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(this);
    }
    
    @Override
    public void valueChanged(ListSelectionEvent lse) {
        int index=list.getSelectedIndex();
        //System.out.println("yo");
        if(index>=0){
            String n= (String) list_model.getElementAt(index).toString();
            File f=new File(TreeView.map.get(n));
            text.setText(f.getPath().toString());
            File [] files= f.listFiles();
            int p=ListView.list_model.getSize();
            for(int i=p-1;i>=0;i--)
            {
                ListView.list_model.removeElementAt(i);
            }
            int s=TableView.model.getRowCount();

            for(int i=s-1;i>=0;i--)
            {
                TableView.model.removeRow(i);
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
                    ListView.list_model.addElement(name);
                    TableView.model.addRow(new Object []{icon,name,size,date});
                }
            }
        }
    }
    public JList get_list()
    {
            return list;
    }
}
   


