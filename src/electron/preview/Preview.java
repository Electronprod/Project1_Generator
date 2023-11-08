package electron.preview;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import electron.Project1_Generator;
import electron.data.FileIteractor;

public class Preview {
	/**
	 * Open preview method
	 * 
	 * Creates HTML file and launching it.
	 */
	public static void showPreview() {
		File showfile = new File("show.html");
		FileIteractor.loadFile(showfile);
		FileIteractor.writeFile(HTMLGenerator.generateIndex(), showfile);
    	try {
			Desktop.getDesktop().open(showfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
