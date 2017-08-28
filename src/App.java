import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

/**
 * Hello ChitChat!
 */
public class App {
    public static void main(String[] args) throws ClientProtocolException, URISyntaxException, IOException {
        try {
            String hello = Request.Get("http://chitchat.andrej.com")
                                  .execute()
                                  .returnContent().asString();
            System.out.println(hello);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //login("Ines");
        seznam();
        //logout("Ines");
        sendMessage("Ines", "oooj");
        recieveMessage("Ines");
    }

    public static void seznam(){
    	Random r = new Random();
        int s = r.nextInt(600);
        try {
            String hello = Request.Get("http://chitchat.andrej.com/users?stopcache=" + s )
                                  .execute()
                                  .returnContent().asString();
            System.out.println(hello);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    

    public static void login(String ime) throws URISyntaxException, ClientProtocolException, IOException {
    	URI uri = new URIBuilder("http://chitchat.andrej.com/users")
    	          .addParameter("username", ime)
    	          .build();

    	  String responseBody = Request.Post(uri)
    	                               .execute()
    	                               .returnContent()
    	                               .asString();

    	  System.out.println(responseBody);
    }
    
    public static void logout(String ime) throws URISyntaxException, ClientProtocolException, IOException{
    	URI uri = new URIBuilder("http://chitchat.andrej.com/users")
    	          .addParameter("username", ime)
    	          .build();

    	  String responseBody = Request.Delete(uri)
    	                               .execute()
    	                               .returnContent()
    	                               .asString();

    	  System.out.println(responseBody);
    }
    
    public static void sendMessage(String username, String msg) throws URISyntaxException, ClientProtocolException, IOException {
    	URI uri = new URIBuilder("http://chitchat.andrej.com/messages")
    	          .addParameter("username", username)
    	          .build();

    	  String message = "{ \"global\" : true, \"text\" : \"" + msg + "\"  }";
    	  String responseBody = Request.Post(uri)
    	          .bodyString(message, ContentType.APPLICATION_JSON)
    	          .execute()
    	          .returnContent()
    	          .asString();

    	  System.out.println(responseBody);
    }
    
    public static void recieveMessage (String username) throws URISyntaxException, ClientProtocolException, IOException{
    	URI uri = new URIBuilder("http://chitchat.andrej.com/messages")
    	          .addParameter("username", username)
    	          .build();

    	  String responseBody = Request.Get(uri)
    	                               .execute()
    	                               .returnContent()
    	                               .asString();

    	  System.out.println(responseBody);
    }
    
}


