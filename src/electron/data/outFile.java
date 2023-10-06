package electron.data;

import java.io.File;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Class responsible for the output file
 */
public class outFile {
	private static File outFile = new File("config.json");
	public static JSONObject info = new JSONObject();
	
	/**
	 * Initialize method for output file
	 */
	@SuppressWarnings("unchecked")
	public static void load() {
		if (!outFile.exists()) {
			//not exists
			FileIteractor.loadFile(outFile);
			info.put("port", 80); //Put port for server. Can be changed by hands.
		}else {
			info = (JSONObject) FileIteractor.ParseJs(FileIteractor.getFileLine(outFile));
		}
	}
	/**
	 * Write method for output file
	 */
	public static void write() {
		FileIteractor.writeFile(info.toJSONString(), outFile);
	}
	/**
	 * Get JSON info for day
	 * @param day - day name
	 * @return JSONArray day info
	 */
	public static JSONArray getDay(String day) {
		JSONArray arr = (JSONArray) info.get(day);
    	return arr;
	}
	/**
	 * Get all lessons for class
	 * @param dayarr - day
	 * @param classname - class
	 * @return
	 */
	@SuppressWarnings("unchecked")
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
