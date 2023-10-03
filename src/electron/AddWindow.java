package electron;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import electron.data.outFile;

public class AddWindow extends JPanel{
    private JTextField lfield;
    private JLabel lname;
    private JLabel tname;
    private JTextField tfileld;
    private JLabel time;
    private JTextField timefield;
    private JButton submitbtn;

    public AddWindow(String day,String classname,JLabel status,Frame thframe) {
    	status.setText("Building new frame...");
    	//Building window
        //construct components
        lfield = new JTextField (5);
        lname = new JLabel ("Lesson name");
        tname = new JLabel ("Teacher name");
        tfileld = new JTextField (5);
        time = new JLabel ("Time");
        timefield = new JTextField (5);
        submitbtn = new JButton ("Submit");

        //adjust size and set layout
        setPreferredSize (new Dimension (193, 225));
        setLayout (null);

        //add components
        add (lfield);
        add (lname);
        add (tname);
        add (tfileld);
        add (time);
        add (timefield);
        add (submitbtn);

        //set component bounds (only needed by Absolute Positioning)
        lfield.setBounds (15, 30, 160, 25);
        lname.setBounds (15, 0, 100, 25);
        tname.setBounds (15, 65, 100, 25);
        tfileld.setBounds (15, 90, 160, 25);
        time.setBounds (15, 120, 100, 25);
        timefield.setBounds (15, 145, 160, 25);
        submitbtn.setBounds (35, 185, 100, 25);
        status.setText("Built frame.");
        //End of building.
        submitbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                status.setText("Adding new item...");
                if(outFile.info.isEmpty()) {
                	status.setText("Creating JSON map...");
                	JSONObject obj = new JSONObject();
                	obj.put("time", timefield.getText());
                	obj.put("lesson", lfield.getText());
                	obj.put("teacher", tfileld.getText());
                	obj.put("class", classname);
                	JSONArray arr = new JSONArray();
                	arr.add(obj);
                	outFile.info.put(day, arr);
                	outFile.write();
                	status.setText("Created map & added lesson");
                }else {
                	status.setText("Creating item...");
                	JSONObject obj = new JSONObject();
                	obj.put("time", timefield.getText());
                	obj.put("lesson", lfield.getText());
                	obj.put("teacher", tfileld.getText());
                	obj.put("class", classname);
                	if(outFile.info.get(day)==null) {
                		status.setText("Map for this day not found. Creating...");
                		JSONArray arr = new JSONArray();
                    	arr.add(obj);
                    	outFile.info.put(day, arr);
                    	outFile.write();
                    	status.setText("Created item and day map.");
                	}else {
                		JSONArray arr = (JSONArray) outFile.info.get(day);
                		arr.add(obj);
                    	outFile.write();
                    	status.setText("Created item.");
                	}
                }
                thframe.dispose();
         }});
        	
        } 
    }