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

public class FreightWavesScraper implements Extractor {
	
	private int blogScraped;
	
	public int getBlogScraped() {
		return blogScraped;
	}

	@Override
	public void scrape() {
		// URL of the website
		
		String baseUrl = "https://www.freightwaves.com/blockchain/page/";
		
		// Set to store extracted data
		Set<BlogObject> blogList = new HashSet<>();
		
		//Iterate pages
		for (int i = 1; i <= 6; i++) {
			String pageUrl = baseUrl + i;
			
			try {
				// Fetch the HTML content from the blog page
				Document document = Jsoup.connect(pageUrl).userAgent("Mozilla").get();
				
				//Select <a> elements with class "fw-block-post-item-blue"
				Elements links = document.select("span.fw-block-post-item-blue a");
				
				//Extract the url from the above elements and get a blog object for each blog's link
				for (Element link : links) {
					String url = link.absUrl("href");
					BlogObject blog = extractData(url);
					blogList.add(blog);
					blogScraped++;
				}
				
				//Convert each blog object to json elements and write json string to the indicated file
				String jsonString = blogListToJson(blogList);
				writeJsonFile(jsonString, JsonFiles.FREIGHTWAVES);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	//Extract data from url (title, time, content, ...)
	@Override
	public BlogObject extractData(String url) {
		try {
			Document document = Jsoup.connect(url).userAgent("Mozilla").get();
			String title = document.select("h1").text();
			String time = extractTime(document);
			String author = document.selectFirst("span.meta-author").text();
			String content = extractContent(document);
			List<String> keywords = extractKeywords(document);
			return new BlogObject(url, title, time, author, content, keywords, 0);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	//Extract and reformat time
	@Override
	public String extractTime(Document document) {
		Element timeElement = document.selectFirst("time.entry-date.published.updated");
		String timeText = timeElement.text();
		timeText = timeText.substring(timeText.indexOf(",") + 2);
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
		Elements paragraphs = document.select("div.entry-content p");
		
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
		Elements tags = document.select("div.entry-tag-cloud a");
		
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
