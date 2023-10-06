package electron;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import electron.data.outFile;
import electron.data.settings;
import electron.preview.Preview;
/**
 * Main window builder & handler
 */
public class MainWindow extends JPanel {
	//Defining window objects
	private JComboBox dayselectro;
    private JList showlist;
    private JLabel status;
    private JButton addbtn;
    private JButton removebtn;
    private JComboBox classselector;
    private JButton updatebtn;
    private JLabel lstime;
    private JTextField lstimef;
    private JLabel lsname;
    private JTextField lsnamef;
    private JLabel tname;
    private JTextField tnamef;
    
  //Window building
	public MainWindow() {	
        //construct preComponents
        String[] dayselectroItems = {"Monday", "Tuesday", "Wednesday","Thursday","Friday","Saturday","Sunday"};
        String[] classselectorItems = settings.getListClasses().toArray(new String[0]);

        //construct components
        dayselectro = new JComboBox (dayselectroItems);
        showlist = new JList ();
        status = new JLabel ("Started Project1_Generator");
        addbtn = new JButton ("Add");
        removebtn = new JButton ("Remove");
        classselector = new JComboBox (classselectorItems);
        updatebtn = new JButton ("Preview");
        lstime = new JLabel ("Lesson time:");
        lstimef = new JTextField (5);
        lsname = new JLabel ("Lesson name:");
        lsnamef = new JTextField (5);
        tname = new JLabel ("Teacher name:");
        tnamef = new JTextField (5);

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
        add (lstime);
        add (lstimef);
        add (lsname);
        add (lsnamef);
        add (tname);
        add (tnamef);

        //set component bounds (only needed by Absolute Positioning)
        dayselectro.setBounds (10, 10, 310, 25);
        updatebtn.setBounds (340, 280, 120, 25);
        showlist.setBounds (10, 40, 315, 265);
        status.setBounds (0, 305, 325, 25);
        addbtn.setBounds (340, 220, 120, 25);
        removebtn.setBounds (340, 250, 120, 25);
        classselector.setBounds (335, 10, 100, 25);
        lstime.setBounds (335, 40, 100, 25);
        lstimef.setBounds (330, 65, 145, 25);
        lsname.setBounds (330, 95, 100, 25);
        lsnamef.setBounds (330, 120, 145, 25);
        tname.setBounds (330, 150, 100, 25);
        tnamef.setBounds (330, 175, 145, 25);
        //End of window building
        
        //Window actions listeners
        
        //Remove action
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
        
        //Preview action
        updatebtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Preview.showPreview();
            	status.setText("Showed preview");
            }});
        //Update action for day selector
        dayselectro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateList(dayselectroItems[dayselectro.getSelectedIndex()], classselectorItems[classselector.getSelectedIndex()]);
            }});
        //Update action for class selector
        classselector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateList(dayselectroItems[dayselectro.getSelectedIndex()], classselectorItems[classselector.getSelectedIndex()]);
            }});
        //Add lesson action
        addbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	status.setText("Adding new item...");
                if(outFile.info.isEmpty()) {
                	status.setText("Creating JSON map...");
                	JSONObject obj = new JSONObject();
                	obj.put("time", lstimef.getText());
                	obj.put("lesson", lsnamef.getText());
                	obj.put("teacher", tnamef.getText());
                	obj.put("class", classselectorItems[classselector.getSelectedIndex()]);
                	JSONArray arr = new JSONArray();
                	arr.add(obj);
                	outFile.info.put(dayselectroItems[dayselectro.getSelectedIndex()], arr);
                	outFile.write();
                	status.setText("Created map & added lesson");
                	clearFields();
                }else {
                	status.setText("Creating item...");
                	JSONObject obj = new JSONObject();
                	obj.put("time", lstimef.getText());
                	obj.put("lesson", lsnamef.getText());
                	obj.put("teacher", tnamef.getText());
                	obj.put("class", classselectorItems[classselector.getSelectedIndex()]);
                	if(outFile.info.get(dayselectroItems[dayselectro.getSelectedIndex()])==null) {
                		status.setText("Map for this day not found. Creating...");
                		JSONArray arr = new JSONArray();
                    	arr.add(obj);
                    	outFile.info.put(dayselectroItems[dayselectro.getSelectedIndex()], arr);
                    	outFile.write();
                    	status.setText("Created item and day map.");
                	}else {
                		JSONArray arr = (JSONArray) outFile.info.get(dayselectroItems[dayselectro.getSelectedIndex()]);
                		arr.add(obj);
                    	outFile.write();
                    	status.setText("Created item.");
                	}
                	clearFields();
                }
                updateList(dayselectroItems[dayselectro.getSelectedIndex()], classselectorItems[classselector.getSelectedIndex()]);
            }});
        updateList(dayselectroItems[dayselectro.getSelectedIndex()], classselectorItems[classselector.getSelectedIndex()]);
        
    }
	/**
	 * Clear text fields from text
	 */
    private void clearFields() {
    	lstimef.setText("");
    	lsnamef.setText("");
    	tnamef.setText("");
    }
    /**
     * Updates the displayed information
     * @param day - day
     * @param classname - class name
     */
    private void updateList(String day,String classname) {
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