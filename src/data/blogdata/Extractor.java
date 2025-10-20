package data.blogdata;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;

public interface Extractor {
	void scrape();
	
	BlogObject extractData(String url);

    String extractTime(Document document);
    
    String convertDate(String oldDate);

    String extractContent(Document document);
    
    List<String> extractKeywords(Document document);
    
    String blogListToJson(Set<BlogObject> blogList);
	
	void writeJsonFile(String jsonString, String file) throws IOException;
}
