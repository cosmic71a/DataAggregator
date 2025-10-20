package functions.function1;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import data.blogdata.BlogObject;

public class LoadedBlogList {
	protected List<BlogObject> blogList = new ArrayList<>();
	
	public List<BlogObject> getBlogList() {
		return blogList;
	}
	
	public List<BlogObject> loadData(String jsonString){
		Gson gson = new Gson();
		JsonArray jsonArray = gson.fromJson(jsonString, JsonArray.class);
		
		if (jsonArray == null) {
            System.out.println("Invalid JSON array format.");
            return null;
        }
		
		for (JsonElement element : jsonArray) {
            if (element.isJsonObject()) {
                JsonObject jsonObject = element.getAsJsonObject();
                BlogObject blog = gson.fromJson(jsonObject, BlogObject.class);
                blogList.add(blog);
            }
		}
		
		return blogList;
	}
	
	public void sortListByDate() {
		blogList.sort(new BlogComparatorByDate());
	}
	
	public void sortListByRank(int compareByDate) {
		blogList.sort(new BlogComparatorByRank(compareByDate));
	}
	
	public void displayBlogList(int hideRankZero) {
        for (BlogObject blog : blogList) {
        	if(hideRankZero == 0 || blog.getRank() != 0) {
        		System.out.println(blog.getRank() + " " + blog.getTitle() + " " + blog.getTime()); 
        	}
        }
    }
}
