package electron.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.locks.LockSupport;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/**
 * Simple methods for iteration with file.
 * Contains: fileloader,filewriter,json parser
 * @author Electron
 * @version 1.2
 */
public class FileIteractor {
	//Поскольку этот класс переезжает из проекта в проект это нужно
	private static void log(String msg) {}
	private static void logerr(String msg) {System.err.println(msg);}
	
	/**
	 * Method provides reading line by line
	 * 
	 * @param path - path of file 
	 * Note: you can get the path using yourfile.getPath() function
	 * @return List<String> lines
	 */
	  public static List<String> getFileLines(String path) {
		    try {
		      List<String> lines = Files.readAllLines(Paths.get(path, new String[0]));
		      return lines;
		    } catch (IOException e) {
		      return getFileLines(path);
		    } 
		  }
	  /**
	   * Method provides reading in one string
	   * 
	   * @param f - file to load
	   * @return String data
	   */
		  public static String getFileLine(File f) {
		    List<String> infile = getFileLines(f.getPath());
		    String in = "";
		    for (int i = 0; i < infile.size(); i++)
		      in = String.valueOf(in) + (String)infile.get(i); 
		    return in;
		  }
	
	/**
	 * Method provides write to file function
	 * 
	 * @param in - the string to be written
	 * @param f - file to write
	 */
	  public static void writeFile(String in, File f) {
		    for (; !f.canWrite() && !f.canRead(); LockSupport.parkNanos(100L));
		    FileWriter fr = null;
		    try {
		      fr = new FileWriter(f);
		      fr.write(in);
		    } catch (IOException e) {
		      e.printStackTrace();
		    } finally {
		      try {
		        fr.close();
		      } catch (IOException e) {
		        e.printStackTrace();
		      } 
		    } 
		  }
	
	/**
	 * This method checks file for exists.
	 * If file doesn't found it creates.
	 * 
	 * @param f - File to check
	 */
	 public static void loadFile(File f) {
		    if (f.exists()) {
		      if (f.canRead() && f.canWrite()) {
		        log("[FileIteractor]: File " + f.getName() + " loaded.");
		      } else {
		    	  logerr("[FileIteractor]: File " + f.getName() + " can't be read or wrote.");
		    	  logerr("[FileIteractor]: Please, check for other launched copy of this program and for args of file " + f.getName());
		        System.exit(1);
		      } 
		    } else {
		      log("[FileIteractor]: File not found. Creating it...");
		      try {
		        f.createNewFile();
		        log("[FileIteractor]: File " + f.getName() + " created and loaded.");
		      } catch (IOException e) {
		    	  logerr(e.getLocalizedMessage());
		    	  logerr("[FileIteractor]: Please, create " + f.getName() + " yourself and try again. Bye.");
		        System.exit(1);
		      } 
		    } 
		  }
	 /**
	  * JSON format checker & loader
	  * 
	  * @param d - string in JSON format
	  * @return Object JSON
	  */
	  public static Object ParseJs(String d) {
		    if (d == null) {
		    	logerr("Error loading JSON. Please, check configs. Program will exit. \nError message: input string = null");
			  System.exit(1);
		      return null; 
		    }
		    try {
		      Object obj = (new JSONParser()).parse(d);
		      return obj;
		    } catch (ParseException e) {
		      logerr("Error loading JSON. Please, check configs. Program will exit. \nError message: " + e.getMessage());
		      System.exit(1);
		      return null;
		    } 
		  }
}
