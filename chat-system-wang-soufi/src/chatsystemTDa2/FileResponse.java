package chatsystemTDa2;


public class FileResponse extends Message {
	private boolean response;
	private String name;
	private static final long serialVersionUID = 5L;
	
	public FileResponse(boolean response,String name){
		this.response=response;
		this.name=name;
	}
	
	public boolean getResponse(){
		return response;
	}
	
	public void setResponse(boolean response){
		this.response=response;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public String toString(){
		return "FileResponse[name="+name+", response="+response+"]";
	}
}
