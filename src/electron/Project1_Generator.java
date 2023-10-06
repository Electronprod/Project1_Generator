package electron;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import electron.data.outFile;
import electron.data.settings;

/**
 * Main class of project.
 * @version 1.1
 * @author Electron_prod
 */
public class Project1_Generator {
	public static void main(String[] args) {
		settings.load();
		outFile.load();
        JFrame frame = new JFrame ("Project1_Generator");
		if(new File("icon.png").exists()) {
			ImageIcon img = new ImageIcon("icon.png");
			frame.setIconImage(img.getImage());
		}
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (new MainWindow());
        frame.setResizable(false);
        frame.pack();
        frame.setVisible (true);
	}
}
