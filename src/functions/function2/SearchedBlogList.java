package functions.function2;

import java.util.ArrayList;
import java.util.List;

import data.blogdata.BlogObject;
import functions.function1.LoadedBlogList;

import org.apache.commons.lang3.StringUtils;

public class SearchedBlogList extends LoadedBlogList {
	
	public SearchedBlogList() {
		
	}
	
	public SearchedBlogList(List<BlogObject> blogList) {
		this.blogList = blogList;
	}
	
	public List<BlogObject> searchByKeywords(String[] targetWords, int compareByDate){
		if(targetWords == null) {
			return blogList;
		}
		
		List<BlogObject> filteredList = new ArrayList<>();
		
		for (BlogObject blog : blogList) {
			blog.setRank(0);
			
			for (String targetWord : targetWords) {
				if(StringUtils.containsIgnoreCase(blog.getTitle(), targetWord)) {
					blog.setRank(blog.getRank() + 1);
				}
				
				for (String keyword : blog.getKeywords()) {
					if(StringUtils.containsIgnoreCase(keyword, targetWord)) {
						blog.setRank(blog.getRank() + 1);
					}
				}
			}
		}
		
		sortListByRank(compareByDate);
		
		for(BlogObject blog : blogList) {
			if(blog.getRank() != 0) {
				filteredList.add(blog);
			}
		}
		
		return filteredList;
	}
}
