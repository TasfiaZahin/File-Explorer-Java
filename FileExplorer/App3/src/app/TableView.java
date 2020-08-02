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
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author acer
 */
public class TableView{
    public static DefaultTableModel model =new DefaultTableModel();
    JTextField text;
    JTable table;
    public TableView(JTextField t)
    {
        text=t;
        
        model.addColumn("Icon");
        model.addColumn("Name");
        model.addColumn("Size");
        model.addColumn("Date Modified");

        table=new JTable(model){
        public boolean isCellEditable(int rowIndex, int colIndex) {
            return false;   
        }
        };
        
        table.getColumnModel().getColumn(0).setCellRenderer(table.getDefaultRenderer(ImageIcon.class));
        table.addMouseListener(mouseListener);
           
}
        MouseListener mouseListener = new MouseAdapter() {
        public void mousePressed(MouseEvent evt) {
            int row = table.rowAtPoint(evt.getPoint());
            int col = table.columnAtPoint(evt.getPoint());
            if (row >= 0 && col >= 0 && evt.getClickCount() == 2) {
                String n=(String) model.getValueAt(row, 1);
                
                File f=new File(TreeView.map.get(n));
                text.setText(f.getPath().toString());
                File [] files= f.listFiles();
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
        }
    };
    public JTable get_table()
    {
            return table;
    }
    
    
}
