package chatsystemTDa2;

/** One text message, with UID for ack system. */
public class Send extends Message
{
  
	private static final long serialVersionUID = 1L;

	private String message;
	private int id;

	public Send(String msg, int id)
	{
      message = msg;
      this.id = id;
	}

	public String getMessage() {return message;}
	public int getID()         {return id;}

	@Override
	public String toString() {
		return "Send [message=" + message + ", id=" + id + "]";
	}
}
