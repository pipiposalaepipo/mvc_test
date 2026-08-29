import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import java.awt.Font;
import Controll.MainController;

public class Main {
    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");

        try {
            FontUIResource thaiFont = new FontUIResource("Tahoma", Font.PLAIN, 13);
            
            UIManager.put("Button.font", thaiFont);
            UIManager.put("TabbedPane.font", thaiFont);
            UIManager.put("Label.font", thaiFont);
            UIManager.put("Table.font", thaiFont);
            UIManager.put("TableHeader.font", thaiFont);
            UIManager.put("OptionPanes.font", thaiFont);
            UIManager.put("TextField.font", thaiFont);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new MainController();
    }
}