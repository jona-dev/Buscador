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
//		String separador ="\\";
//		String name = url.split(separador)[1];
		return url.substring(104, url.length()); // no pude hacer andar el split, no se porque no funca
		
	}
	public void setUserName(String fileName) {
		this.userName = fileName;
	}
	public void getUserName(String fileName) {
		this.userName = fileName;
	}	
	
}
