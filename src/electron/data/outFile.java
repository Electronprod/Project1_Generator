package electron.data;

import java.io.File;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class outFile {
	private static File outFile = new File("config.json");
	public static JSONObject info = new JSONObject();
	public static void load() {
		if (!outFile.exists()) {
			//Если не существует
			FileIteractor.loadFile(outFile);
			info.put("port", 80);
		}else {
			info = (JSONObject) FileIteractor.ParseJs(FileIteractor.getFileLine(outFile));
		}
		}
	public static void write() {
		FileIteractor.writeFile(info.toJSONString(), outFile);
	}
	public static JSONArray getDay(String day) {
		JSONArray arr = (JSONArray) info.get(day);
    	return arr;
	}
	public static JSONArray getLessonsForClass(JSONArray dayarr,String classname) {
		if(dayarr==null) {return null;}
    	JSONArray toShow =  new JSONArray();
    	for(int i = 0; i < dayarr.size();i++) {
    		JSONObject obj = (JSONObject) dayarr.get(i);
    		if(String.valueOf(obj.get("class")).equals(classname)) {
    			toShow.add(obj);
    		}
    	}
    	return toShow;	
	}
}
