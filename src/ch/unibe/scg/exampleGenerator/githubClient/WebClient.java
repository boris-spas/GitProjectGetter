package ch.unibe.scg.exampleGenerator.githubClient;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

public class WebClient {
	

	public static String getResponceFromUrl(String url)
	{
		System.out.println(url);
		try{
			URL githubUrl = new URL(url);
			URLConnection yc = githubUrl.openConnection();
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							yc.getInputStream()));
			String inputLine;
			String output = "";	
			while ((inputLine = in.readLine()) != null) 
				output += inputLine + "\n";
			in.close();

			return output;
		}
		catch(FileNotFoundException e)
		{
			//Done, do nothing
		} catch (MalformedURLException e) {
			System.out.println("ERROR: URL MALFORMED: " + url);
		} catch (IOException e) {
			if (e.getMessage().contains("code: 404"))
				return "";
			
			System.out.println("ERROR: IO ERROR, sleeping for 60000 and retrying");
			try {
				Thread.sleep(60000);
				return getResponceFromUrl(url);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
		return "";
	}
}
