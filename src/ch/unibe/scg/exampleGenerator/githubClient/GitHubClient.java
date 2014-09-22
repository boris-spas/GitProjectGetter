package ch.unibe.scg.exampleGenerator.githubClient;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GitHubClient {


	private String gitSearchUrl = "https://github.com/search?l=Java&p=";

	private String gitSearchUrl2 ="&q=size%3A0<500000+stars%3A>10+size%3A>100+updated%3A>2013-01-01&s=stars&type=Repositories";

	private static final int minimumExampleSize = 100;

	private Set<String> projects = new HashSet<String>();
	
	private Map<String, String> filesToLinksMap = new HashMap<String, String>();
	
	private boolean done = false;

	public void setGitSearchUrl(String gitSearchUrl) {
		this.gitSearchUrl = gitSearchUrl;
	}

	public void setGitSearchUrl2(String gitSearchUrl2) {
		this.gitSearchUrl2 = gitSearchUrl2;
	}

	public void getFilesForClass(String inputClass)
	{
		String responce;

		int count = 1;

		responce = WebClient.getResponceFromUrl(gitSearchUrl + Integer.toString(count++) + gitSearchUrl2);
		while(!responce.equals(""))
		{
			parseResponce(responce);
			responce = WebClient.getResponceFromUrl(gitSearchUrl + Integer.toString(count++) + gitSearchUrl2);
		}
	}


	public void doTheWork(/*String inputClass, List<String> additional*/)
	{
		String responce;

		int count = 1;

		responce = WebClient.getResponceFromUrl(gitSearchUrl + Integer.toString(count++) + gitSearchUrl2);
		while(!responce.equals(""))
		{
			parseResponce(responce);
			if (done)
				break;

			responce = WebClient.getResponceFromUrl(gitSearchUrl + Integer.toString(count++) + gitSearchUrl2);
		}


		File file = new File("projs.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter("projs.txt", true)));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Iterator<String> it = projects.iterator();
		while (it.hasNext())
		{
			out.println(it.next());
		}

		out.close();
		
	}


	public void parseResponce(String input)
	{
		//Elements li = Jsoup.parse(input).getElementsByClass("public source");
		Elements h3 = Jsoup.parse(input).getElementsByTag("h3");

		File file = new File("projs.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		int count = 0;
		for (int i = 0; i < h3.size(); i++)
		{
			Element a = h3.get(i);
			String s = a.toString();
			if (s.contains("href")){
				int begin = s.indexOf("href=\"") + 7;
				s = "http://github.com/" + s.substring(begin, s.indexOf('"', begin)) + ".git";
				projects.add(s);
				System.out.println(s);
				count ++;
			}

		}
		
		if (count == 0)
			done = true;


	}

	private void initialize(String input) {
		int splitLocation = input.indexOf("p=");
		setGitSearchUrl(input.substring(0, splitLocation) + "p=");
		setGitSearchUrl2(input.substring(splitLocation+3));
	}

	public static void main(String[] args) {
		if(args[0] == null)
		{
			System.out.println("NO INPUT GITHUB LINK FOUND");
			return;
		}
		
		GitHubClient g = new GitHubClient();
		
		String input = args[0];
		
		g.initialize(input);
		g.doTheWork();
		
	}

	

}
