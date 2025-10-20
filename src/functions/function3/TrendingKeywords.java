package functions.function3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.blogdata.BlogObject;

public class TrendingKeywords{
	private List<BlogObject> blogList;
	
	public TrendingKeywords() {
		
	}
	
	public TrendingKeywords(List<BlogObject> blogList) {
		this.blogList = blogList;
	}
	
	public List<KeywordObject> findTrendingKeywords(String oldestDate, String newestDate){
		Map<String, Integer> keywordCount = new HashMap<>();
		
		for (BlogObject blog : blogList) {
			if(blog.getTime().compareTo(newestDate) <= 0 && blog.getTime().compareTo(oldestDate) >= 0) {
				for (String keyword : blog.getKeywords()) {
				keywordCount.put(keyword, keywordCount.getOrDefault(keyword, 0) + 1);
				}
			}
			
		}
		
		List<KeywordObject> mostAppearedKeywords = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : keywordCount.entrySet()) {
		   mostAppearedKeywords.add(new KeywordObject(entry.getKey(), entry.getValue()));
		}
		
		KeywordComparator comparator = new KeywordComparator();
		mostAppearedKeywords.sort(comparator);
		
		return mostAppearedKeywords;
	}
	
}
