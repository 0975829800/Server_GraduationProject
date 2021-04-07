package DBS;

public class Equipment_bag {
  public int PlayerID;
	public int Equipment_ID;
	public int Rarity;
	public int Part;
	public int Level;
	public int Equipping;
	public int Skill_ID_1;
	public int Skill_ID_2;
	public Equipment_bag(int PlayerID, int Equipment_ID, int Rarity, int Part, int Level, int Equipping, int Skill_ID_1, int Skill_ID_2){
	  this.PlayerID = PlayerID;
	  this.Equipment_ID = Equipment_ID;
	  this.Equipping = Equipping;
    this.Rarity = Rarity;
    this.Part = Part;
    this.Level = Level;
    this.Skill_ID_1 = Skill_ID_1;
    this.Skill_ID_2 = Skill_ID_2;
  }
}
