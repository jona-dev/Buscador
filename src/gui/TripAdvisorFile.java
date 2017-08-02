package gui;

public class TripAdvisorFile {

	private String url;

	
	public TripAdvisorFile(){
		this.setUrl("");
	}
	public TripAdvisorFile(String _url, String _userName){
		this.setUrl(_url);
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFileName() {
		int index =url.indexOf("NewYork_");
		return url.substring(index+10, url.length()); 
		
	}

	
}
