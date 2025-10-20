package data.blogdata;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import paths.JsonFiles;

public class BlockchainNewsScraper implements Extractor{
private int blogScraped;
	
	public int getBlogScraped() {
		return blogScraped;
	}

	@Override
	public void scrape() {
		int targetAmount = 250;
		
		// URL of the website
		
		String baseUrl = "https://blockchain.news/search/blockchain";
		
		// Set to store extracted data
		Set<BlogObject> blogList = new HashSet<>();
			
		try {
			// Fetch the HTML content from the blog page
			Document document = Jsoup.connect(baseUrl).userAgent("Mozilla").get();
			
			//Select <a> elements descend from h3
			Elements links = document.select("h3 a");
			
			//Extract the url from the above elements and get a blog object for each blog's link
			for (Element link : links) {
				String url = link.absUrl("href");
				BlogObject blog = extractData(url);
				blogList.add(blog);
				
				blogScraped++;
				if(blogScraped == targetAmount) break;
			}
			
			//Convert each blog object to json elements and write json string to the indicated file
			String jsonString = blogListToJson(blogList);
			writeJsonFile(jsonString, JsonFiles.BLOCKCHAINNEWS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	//Extract data from url (title, time, content, ...)
	@Override
	public BlogObject extractData(String url) {
		try {
			Document document = Jsoup.connect(url).userAgent("Mozilla").get();		
			String title = document.select("h1").text();
			String time = extractTime(document);
			
			if(time == "") {
				return null;
			}
			
			
			String author = document.selectFirst("a.h5.stretched-link.mt-2.mb-0.d-block").text();
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
		Element timeElement = document.selectFirst("li.list-inline-item.d-lg-block.my-lg-2");
		if(timeElement == null) {
			return "";
		}
		
		String timeText = timeElement.text();
		timeText = timeText.substring(0, 12);
		return convertDate(timeText);
	}
	
	@Override
	public String convertDate(String oldDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("MMMM dd, yyyy");
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
		Elements paragraphs = document.select("div.col-lg-7.mb-5 p");
		
		StringBuilder content = new StringBuilder();
		
		for (Element paragraph: paragraphs) {
			if(paragraph.text() != "")
				content.append("\t").append(paragraph.text()).append("\n");
		}
		
		return content.toString();
	}

	//Extract tags / keywords
	@Override
	public List<String> extractKeywords(Document document) {
		Elements tags = document.select("li.list-inline-item a");
		
		List<String> keywords = new ArrayList<>();
		
		for (Element tag : tags) {
			String keyword = tag.text().substring(0);
			keywords.add(keyword);
		}
		return keywords;
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
