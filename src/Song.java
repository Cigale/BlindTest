
public class Song {
	private String title;
	private String author;
	private String year;
	private String style;
	
	public Song(String title, String author, String year, String style) {
		this.title = title;
		this.author = author;
		this.year = year;
		this.style = style;
	}
	
	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getYear() {
		return year;
	}

	public String getStyle() {
		return style;
	}
	
	public String toString() {
		   return "Titre: " + this.title +
			  ", Auteur: " + this.author + 
			  ", Année: " + this.year + 
			  ", Style: " + this.style;
		}
}
