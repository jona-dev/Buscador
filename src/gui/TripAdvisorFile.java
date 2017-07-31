package gui;

public class TripAdvisorFile {

	private String url;
	private String userName;
	
	public TripAdvisorFile(){
		this.setUrl("");
		this.setUserName("");
	}
	public TripAdvisorFile(String _url, String _userName){
		this.setUrl(_url);
		this.setUserName(_userName);
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
	public void setUserName(String fileName) {
		this.userName = fileName;
	}
	public void getUserName(String fileName) {
		this.userName = fileName;
	}	
	
}
