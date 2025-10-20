package data.blogdata;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jsoup.Jsoup;

import paths.JsonFiles;

public class CoinDeskScraper implements Extractor {
	
	private int blogScraped;

	public int getBlogScraped() {
		return blogScraped;
	}

	@Override
	public void scrape() {
		// URL of the website
		String baseUrl = "https://www.coindesk.com/tag/blockchain/";
		
		// Set to store extracted data
		Set<BlogObject> blogList = new HashSet<>();
		
		//Iterate pages
		for (int i = 1; i <= 10; i++) {
			String pageUrl = baseUrl + i + "/";
			System.out.println(pageUrl);
			try {
				// Fetch the HTML content from the blog page
				Document document = Jsoup.connect(pageUrl).userAgent("Mozilla").get();
				
				//Select <a> elements descend from <h6> with the specified class
				Elements links = document.select("h6.typography__StyledTypography-sc-owin6q-0.bhrWMt a");
		
				//Extract the url from the above elements and get a blog object for each blog's link
				for(Element link : links) {
					String url = link.absUrl("href");
					BlogObject blog = extractData(url);
					blogList.add(blog);
					blogScraped++;
				}
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//Convert each blog object to json elements and write json string to the indicated file
		String jsonString = blogListToJson(blogList);
		writeJsonFile(jsonString, JsonFiles.COINDESK);

	}

	//Extract data from url (title, time, content, ...)
	@Override
	public BlogObject extractData(String url) {
		try {
			Document document = Jsoup.connect(url).userAgent("Mozilla").get();
			String title = document.select("h1").text();
			String time = extractTime(document);
			String author = document.select("div.at-authors a").html();
			String content = extractContent(document);
			List<String> keywords = extractKeywords(document);
			
			return new BlogObject(url, title, time, author, content, keywords, 0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//Extract and reformat time
	@Override
	public String extractTime(Document document) {
		Element element = document.selectFirst("span.typography__StyledTypography-sc-owin6q-0.iOUkmj");
		String timeText = element.ownText();
		timeText = timeText.substring(0, timeText.indexOf("at") - 1);
		return convertDate(timeText);
	}
	
	@Override
	public String convertDate(String oldDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            Date date = inputFormat.parse(oldDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return oldDate; 
        }
    }

	//Extract main content of the blog
	@Override
	public String extractContent(Document document) {
		Elements paragraphs = document.select("article p");
		
		StringBuilder content = new StringBuilder();
		
		for (Element paragraph : paragraphs) {
			if(paragraph.text() != "")
				content.append("\t").append(paragraph.text()).append("\n");
		}
		return content.toString();
	}
	
	@Override
	public List<String> extractKeywords(Document document) {
		return Collections.emptyList();
	}

	//Method to convert list of blog objects to json
	@Override
	public String blogListToJson(Set<BlogObject> blogList) {
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        return gson.toJson(blogList);
	}

	//Method to write json String to the file
	@Override
	public void writeJsonFile(String jsonString, String file) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}