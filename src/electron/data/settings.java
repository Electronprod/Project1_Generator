package electron.data;

import java.io.File;
import java.util.Collections;
import java.util.List;

import electron.utils.messages;

public class settings {
	static File confFile = new File("settings.txt");
	public static void load() {
		FileIteractor.loadFile(confFile);
	    if (FileIteractor.getFileLines(confFile.getPath().toString()).isEmpty()) {
	    	messages.error("Вы должны заполнить файл settings.txt классами, \nкоторые должны быть добавлены в таблицу. \nФормат - каждая новая строка - название класса в порядке возврастания. \n Сейчас программа выключается.");
	    	System.exit(0);
	    }
	}
	public static List<String> getListClasses(){
		List<String> strs = FileIteractor.getFileLines(confFile.getPath());
		Collections.sort(strs);
		return strs;
	}
}
