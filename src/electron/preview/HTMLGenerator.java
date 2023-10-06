package electron.preview;

import java.io.File;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import electron.data.FileIteractor;
import electron.data.outFile;
import electron.data.settings;
import electron.utils.messages;

/**
 * HTML generator class
 */
public class HTMLGenerator {
	private static String[] day = {"Monday", "Tuesday", "Wednesday","Thursday","Friday","Saturday","Sunday"};
	
	public static String generateIndex() {
		if(htmlCheck() != true) {
			messages.error("Preview files missing.");
			return null;
		}
		//Loading HTML template from file
		String index = FileIteractor.getFileLine(new File("index.html"));
		String gen="";
		for(int i=0;i<settings.getListClasses().size();i++) {
			gen=gen+"<div>";
			gen=gen+generateTable(settings.getListClasses().get(i));
			gen=gen+"</div>";
		}
		index=index.replace("%body%",gen);
		index = index.replaceAll("null", "");
		return index;
	}
	/**
	 * Generates HTML table for class
	 * @param classname - class
	 * @return generated HTML
	 */
	private static String generateTable(String classname) {
		//Loading HTML template from file
		String table = FileIteractor.getFileLine(new File("table.html"));
		//Заменяем плейсхолдеры
		table=table.replace("%classname%", classname);
		table=table.replace("%monday%", compile(day[0],classname));
		table=table.replace("%tuesday%", compile(day[1],classname));
		table=table.replace("%wednesday%", compile(day[2],classname));
		table=table.replace("%thursday%", compile(day[3],classname));
		table=table.replace("%friday%", compile(day[4],classname));
		table=table.replace("%saturday%", compile(day[5],classname));
		table=table.replace("%sunday%", compile(day[6],classname));
		return table;
		
	}
	/**
	 * Data parser
	 * @param day - day
	 * @param classname - class
	 * @return
	 */
	private static String compile(String day,String classname) {
		JSONArray arr = outFile.getLessonsForClass(outFile.getDay(day), classname);
		if(arr==null) {
			return "<th>No data</th><th>No data</th><th>No data</th><th>No data</th>";
		}
		String lessons=null;
		for(int i=0;i<arr.size();i++) {
			lessons=lessons+generateTableElement((JSONObject) arr.get(i),i+1);
		}
		if(lessons==null) {return "<th>No data</th><th>No data</th><th>No data</th><th>No data</th>";}
		return lessons;
	}
	/**
	 * Data parser
	 * @param lesson - lesson
	 * @param num - number of lesson
	 * @return
	 */
	private static String generateTableElement(JSONObject lesson,int num) {
		String time = String.valueOf(lesson.get("time"));
		String name = String.valueOf(lesson.get("lesson"));
		String teacher = String.valueOf(lesson.get("teacher"));
		String btn="<form action=\"/teacher:"+teacher+" \">\r\n"
				+ "            <button type=\"submit\">"+teacher+"</button>\r\n"
				+ "        </form>";
		return "<tr><th>"+num+"</th><th>"+time+"</th><th>"+name+"</th><th>"+btn+"</th></tr>";
	}
	private static boolean htmlCheck() {
		if(!new File("index.html").exists()) {
			return false;
		}
		if(!new File("table.html").exists()) {
			return false;
		}
		return true;
	}

}
