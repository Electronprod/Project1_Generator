package electron;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.LockSupport;

import javax.swing.*;
import javax.swing.event.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import electron.data.outFile;
import electron.data.settings;

public class MainWindow extends JPanel {
    private JComboBox dayselectro;
    private JList showlist;
    private JLabel status;
    private JButton addbtn;
    private JButton removebtn;
    private JComboBox classselector;
    private JButton updatebtn;
    
  //Window building
    public MainWindow() {	
        //construct preComponents
        String[] dayselectroItems = {"Monday", "Tuesday", "Wednesday","Thursday","Friday","Saturday","Sunday"};
        String[] showlistItems = {"Nothing"};
        String[] classselectorItems = settings.getListClasses().toArray(new String[0]);

        //construct components
        dayselectro = new JComboBox (dayselectroItems);
        showlist = new JList ();
        status = new JLabel ("Started Project1_Generator");
        addbtn = new JButton ("Add");
        removebtn = new JButton ("Remove");
        classselector = new JComboBox (classselectorItems);
        updatebtn = new JButton ("Update");

        //adjust size and set layout
        setPreferredSize (new Dimension (484, 333));
        setLayout (null);

        //add components
        add (dayselectro);
        add (updatebtn);
        add (showlist);
        add (status);
        add (addbtn);
        add (removebtn);
        add (classselector);

        //set component bounds (only needed by Absolute Positioning)
        dayselectro.setBounds (10, 10, 310, 25);
        updatebtn.setBounds (340, 215, 120, 25);
        showlist.setBounds (10, 40, 315, 265);
        status.setBounds (0, 305, 325, 25);
        addbtn.setBounds (340, 135, 120, 25);
        removebtn.setBounds (340, 175, 120, 25);
        classselector.setBounds (335, 10, 100, 25);
        //End of window building
        removebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String selected = String.valueOf(showlist.getSelectedValue());
            	selected = selected.replace(" ", "");
            	String day = dayselectroItems[dayselectro.getSelectedIndex()];
            	String classname = classselectorItems[classselector.getSelectedIndex()];
            	JSONArray arr = (JSONArray) outFile.info.get(day);
            	if(arr == null) {
            		status.setText("Error removing item: JSON error");
            		return;
            	}
            	for(int i=0;i<arr.size();i++) {
            		JSONObject obj = (JSONObject) arr.get(i);
            		if(selected.contains(String.valueOf(obj.get("lesson"))) && selected.contains(String.valueOf(obj.get("teacher"))) && selected.contains(String.valueOf(obj.get("time"))) && classname.equals(String.valueOf(obj.get("class")))) {
            			arr.remove(i);
            			outFile.write();
            			updateList(dayselectroItems[dayselectro.getSelectedIndex()], classselectorItems[classselector.getSelectedIndex()]);
            			status.setText("Removed item succesfully.");
            			return;
            		}
            	}
            	status.setText("Error removing: item not found.");
            }});
        updatebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateList(dayselectroItems[dayselectro.getSelectedIndex()], classselectorItems[classselector.getSelectedIndex()]);
                status.setText("Updated list");
            }});
        dayselectro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateList(dayselectroItems[dayselectro.getSelectedIndex()], classselectorItems[classselector.getSelectedIndex()]);
            }});
        classselector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateList(dayselectroItems[dayselectro.getSelectedIndex()], classselectorItems[classselector.getSelectedIndex()]);
            }});
        addbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame ("Lesson adding - "+ dayselectro.getSelectedItem()+" | "+classselector.getSelectedItem());
                frame.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add (new AddWindow(dayselectroItems[dayselectro.getSelectedIndex()], classselectorItems[classselector.getSelectedIndex()], status,frame));
                frame.pack();
                frame.setVisible (true);
                updateList(dayselectroItems[dayselectro.getSelectedIndex()], classselectorItems[classselector.getSelectedIndex()]);
            }});
        updateList(dayselectroItems[dayselectro.getSelectedIndex()], classselectorItems[classselector.getSelectedIndex()]);
        
    }
    public void updateList(String day,String classname) {
    	JSONArray dayarr = (JSONArray) outFile.info.get(day);
    	if(dayarr==null) {
    		String[] t = {"Nothing"};
    		showlist.setListData(t);
    		return;
    	}
    	ArrayList<Object> toShow =  new ArrayList();
    	for(int i = 0; i < dayarr.size();i++) {
    		JSONObject obj = (JSONObject) dayarr.get(i);
    		if(String.valueOf(obj.get("class")).equals(classname)) {
    			toShow.add(String.valueOf(obj.get("time"))+" | "+String.valueOf(obj.get("lesson"))+" | "+String.valueOf(obj.get("teacher")));
    		}
    	}
    	if(toShow.size()==0) {
    		String[] t = {"Nothing"};
    		showlist.setListData(t);
    		return;
    	}
    	showlist.setListData(toShow.toArray());
    }
 }