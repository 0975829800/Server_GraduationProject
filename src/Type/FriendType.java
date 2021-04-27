package Type;

import Tools.ToCSharpTool;

public class FriendType {
  public static final int SendSize = 8;
  public int PlayID;
  public int FID;
  public int State;

  public FriendType(){

  }
  public FriendType(int PlayID,int FID, int State) {
    this.PlayID = PlayID;
  	this.FID = FID;
  	this.State = State;
  }
  public byte[] getByte(){
    byte[] temp;
    byte[] ans  = new byte[SendSize];
    temp = ToCSharpTool.ToCSharp(FID);
    System.arraycopy(temp,0,ans,0,4);
    temp = ToCSharpTool.ToCSharp(State);
    System.arraycopy(temp,0,ans,4,4);
    return ans;
  }
}
