package susi_bot;

import java.util.Scanner;


public class Main {
	
	public static void main(String[] args) throws Exception {
		 
		String url = "https://susi.uni-sofia.bg/ISSU/forms/Login.aspx";
		String susiLogged = "https://susi.uni-sofia.bg/ISSU/forms/students/ElectiveDisciplinesSubscribe.aspx";
		Scanner input = new Scanner(System.in);
		System.out.println("enter user name: ");
		String userName = input.next();
		System.out.println("enter password : ");
		String password = input.next();
		input.close();
		
		HttpUrlConnection http = new HttpUrlConnection();
		
		System.out.println("Sending 'GET' request to URL : " + url);
		String page = http.GetPageContent(url);
		System.out.println("\nExtracting form's data!");
		
		String postParams = http.getFormParams(page, userName, password);
		http.sendPost(url, postParams);
		
		//logged now go to susi's page for disciplines
		System.out.println("\nSending 'GET' request to URL : " + url);
		String result = http.GetPageContent(susiLogged);
		System.out.println(susiLogged + "/n content /n" + result);
	  }
}
