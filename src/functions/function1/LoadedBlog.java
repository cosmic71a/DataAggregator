package functions.function1;

import java.util.List;

public class LoadedBlog {
	private final String url;
	private final String title;
	private final String time;
	private final String author;
	private final String content;
	private final List<String> keywords;
	
	private int rank = 0;
	
	public LoadedBlog(String url, String title, String time, String author, String content, List<String> keywords) {
		this.url = url;
		this.title = title;
		this.time = time;
		this.author = author;
		this.content = content;
		this.keywords = keywords;
	}

	public String getUrl() {
		return url;
	}

	public String getTitle() {
		return title;
	}

	public String getTime() {
		return time;
	}

	public String getAuthor() {
		return author;
	}

	public String getContent() {
		return content;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
}
