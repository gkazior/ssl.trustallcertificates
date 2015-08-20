import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;


public class TestApp {

  public static void main(String[] args) throws Exception {
        
    if (args.length < 1) {
      System.out.println("Please provide the argument - https address to connect");      
      return;
    } 
    
    final String address = args[0];
    
    try {
      System.out.println("Trying to connect to:" + address);

      URL url = new URL(address);
      URLConnection con = url.openConnection();
      Reader reader = new InputStreamReader(con.getInputStream());
      
      while (true) {
        int ch = reader.read();
        if (ch == -1) {
          break;
        }
        System.out.print((char)ch);
      }

    } catch (Exception e) {
      System.out.println("Unexpected exception: " + e.getMessage());
      throw e;
    }
  }
}