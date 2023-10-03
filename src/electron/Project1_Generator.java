package electron;

import java.io.File;
import java.util.Scanner;

import javax.swing.JFrame;

import electron.data.outFile;
import electron.data.settings;

public class Project1_Generator {

	public static void main(String[] args) {
		settings.load();
		outFile.load();
        JFrame frame = new JFrame ("Project1_Generator");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (new MainWindow());
        frame.pack();
        frame.setVisible (true);
	}
}
