package chatsystemTDa2;

public class FileRequest extends Message {
	
	private static final long serialVersionUID = 4L;
	
	private String name;
	
	public FileRequest(String name){
		this.name=name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public String toString(){
		return "FileRequest [name=" + name + "]";
	}

}
