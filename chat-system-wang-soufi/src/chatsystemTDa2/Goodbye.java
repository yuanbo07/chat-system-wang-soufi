package chatsystemTDa2;

public class Goodbye extends Message
{
   private static final long serialVersionUID = 3L;

   private String nickname;

   
   
   public Goodbye(String nickname){
          this.nickname = nickname;
   }

   
   
   public String getNickname(){
          return this.nickname;
   }



@Override
public String toString() {
	return "Goodbye [nickname=" + nickname + "]";
}
   
   
}
