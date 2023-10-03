package electron.data;

import java.io.File;

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
}
