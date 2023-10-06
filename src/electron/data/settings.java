package electron.data;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import electron.utils.messages;

public class settings {
	static File confFile = new File("settings.txt");
	/**
	 * Initialize settings file method
	 */
	public static void load() {
		FileIteractor.loadFile(confFile);
	    if (FileIteractor.getFileLines(confFile.getPath().toString()).isEmpty()) {
	    	messages.error("You need to fill settings.txt file with classes names \nto add. Format: new line - classname (in ascending order) \n Now program shutting down.");
	    	try {
				Desktop.getDesktop().open(confFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	System.exit(0);
	    }
	}
	/**
	 * Get classes from settings
	 * @return List<String> classes
	 */
	public static List<String> getListClasses(){
		List<String> strs = FileIteractor.getFileLines(confFile.getPath());
		Collections.sort(strs);
		return strs;
	}
}
