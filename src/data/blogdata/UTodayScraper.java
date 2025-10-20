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

public class UTodayScraper implements Extractor {
	
	private int blogScraped;
	
	public int getBlogScraped() {
		return blogScraped;
	}

	@Override
	public void scrape() {
		// URL of the website
		
		String[] baseUrls = {
				"https://u.today/latest-cryptocurrency-news",
				"https://u.today/reviews",
		};
		
		// Set to store extracted data
		Set<BlogObject> blogList = new HashSet<>();
		
		try {
			//Iterate urls to extract each blog's url
			for (String baseUrl : baseUrls) {
				// Fetch the HTML content from the blog page
				Document document = Jsoup.connect(baseUrl).get();
				
				//Select <a> elements with class "news__item-body"
				Elements links = document.select("a.news__item-body");
				
				//Extract the url from the above elements and get a blog object for each blog's link
				for (Element link : links) {
					String url = link.absUrl("href");
					BlogObject blog = extractData(url);
					blogList.add(blog);
					blogScraped++;
				}
			
			}
			
			//Convert each blog object to json elements and write json string to the indicated file
			String jsonString = blogListToJson(blogList);
			writeJsonFile(jsonString, JsonFiles.UTODAY);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Extract data from url (title, time, content, ...)
	@Override
	public BlogObject extractData(String url) {
		try {
			Document document = Jsoup.connect(url).get();
			String title = document.select("h1.article__title").text();
			String time = extractTime(document);
			String author = document.selectFirst("div.article__author-name").text();
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
		Element timeElement = document.select("div.article__short-humble").get(1);
		String timeText = timeElement.text();
		timeText = timeText.substring(5, 15);
		return convertDate(timeText);
	}
	
	@Override
	public String convertDate(String oldDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
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
		Elements paragraphs = document.select("div.article__content p");
		
		StringBuilder content = new StringBuilder();
		
		for (Element paragraph: paragraphs) {
			content.append("\t").append(paragraph.text()).append("\n");
		}
		
		return content.toString();
	}

	//Extract tags / keywords
	@Override
	public List<String> extractKeywords(Document document) {
		Elements tags = document.select("a.btn--hash-tag");
		
		List<String> keywords = new ArrayList<>();
		
		for (Element tag : tags) {
			String keyword = tag.text().substring(1);
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
