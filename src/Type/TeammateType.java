package Type;

import Tools.ToCSharpTool;

public class TeammateType {
  public static final int SendSize = 4;
  public int TMID;

  public TeammateType(){

  }
  public TeammateType( int TMID) {
  	this.TMID = TMID;
  }
  public byte[] getByte(){
    byte[] temp;
    byte[] ans  = new byte[SendSize];
    temp = ToCSharpTool.ToCSharp(TMID);
    System.arraycopy(temp,0,ans,0,4);
    return ans;
  }
}
