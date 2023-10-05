package electron.preview;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import electron.data.FileIteractor;

public class PreviewServer {
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
