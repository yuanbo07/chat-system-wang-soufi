package chatsystemTDa2;

public class SendAck extends Message
{
   
	private static final long serialVersionUID = 1L;
	
	private int id_message ;

	public SendAck(int id_message){
		super();
		this.id_message = id_message ;
	}

	public int getId_message() {
		return id_message;
	}

	public void setId_message(int id_message) {
		this.id_message = id_message;
	}

	@Override
	public String toString() {
		return "SendAck [id_message=" + id_message + "]";
	}

}