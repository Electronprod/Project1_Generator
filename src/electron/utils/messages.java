package electron.utils;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Methods for displaying information using dialog boxes
 * @version 1.0
 * @author Electron
 */
public class messages {
	/**
	 * Show info message
	 * @param text - message text
	 * @param title - message title
	 */
  public static void info(String text, String title) {
    JOptionPane.showMessageDialog(new JFrame(), text, title, 1);
  }
  /**
   * Ask user for information
   * @param question - message question
   * @param title - message title
   * @return
   */
  public static String input(String question, String title) {
    String id = JOptionPane.showInputDialog(new JFrame(), question, title, 3);
    if (id == null)
      return null; 
    return id;
  }
  
  /**
   * Show error message
   * @param err - message for user
   */
  public static void error(String err) {
    JOptionPane.showMessageDialog(new JFrame(), err, "Error", 0);
  }
}
