package susi_bot;
 
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
public class HttpUrlConnection {
 
  private HttpsURLConnection conn;
 
  private final String USER_AGENT = "Mozilla/5.0";
  
  String GetPageContent(String url) throws IOException {
	  
		URL obj = new URL(url);
		conn = (HttpsURLConnection) obj.openConnection();
	 
		// default is GET
		conn.setRequestMethod("GET");
	 
		// act like a browser
		conn.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = conn.getResponseCode();
		System.out.println("Response Code : " + responseCode);
		String inputLine;
		StringBuilder response = new StringBuilder();
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response.toString();
	  }
  
  public String getFormParams(String html, String username, String password) throws UnsupportedEncodingException {

	Document doc = Jsoup.parse(html);
 
	//find form by id
	Element loginform = doc.getElementById("Form1");
	Elements inputElements = loginform.getElementsByTag("input");
	List<String> paramList = new ArrayList<String>();
	for (Element inputElement : inputElements) {
		String key = inputElement.attr("name");
		String value = inputElement.attr("value");
 
		if (key.equals("txtUserName"))
			value = username;
		else if (key.equals("txtPassword"))
			value = password;
		paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
	}
 
	// build parameters list
	StringBuilder result = new StringBuilder();
	for (String param : paramList) {
		if (result.length() == 0) {
			result.append(param);
		} else {
			result.append("&" + param);
		}
	}
	return result.toString();
  }
 
  void sendPost(String url, String postParams) throws IOException{
	  
	URL obj = new URL(url);
	conn = (HttpsURLConnection) obj.openConnection();
 
	// like a browser
	conn.setRequestMethod("POST");
	conn.setRequestProperty("User-Agent", USER_AGENT);
	conn.setDoOutput(true);
	conn.setDoInput(true);
 
	// post request
	DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
	dos.writeBytes(postParams);
	dos.flush();
	dos.close();
 
	int responseCode = conn.getResponseCode();
	System.out.println("Response Code : " + responseCode);
 
	String inputLine;
	StringBuffer response = new StringBuffer();
	try(BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));){
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}		
	}
//	in.close();
  }
}