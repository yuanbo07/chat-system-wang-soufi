package remoteApplication;

public class SendACK extends Message {
	
	private int id_message ;
	
	public SendACK(int id_message){
		super(nickname);
		this.id_message = id_message ;
	}

	public int getId_message() {
		return id_message;
	}

	public void setId_message(int id_message) {
		this.id_message = id_message;
	}
	
}