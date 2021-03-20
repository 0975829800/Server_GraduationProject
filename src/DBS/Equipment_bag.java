package DBS;

public class Equipment_bag {
  int PlayerID;
	int Equipment_ID;
	int Rarity;
	int Part;
	int Level;
	boolean Equipping;
	int Skill_ID_1;
	int Skill_ID_2;
	public Equipment_bag(int PlayerID, int Equipment_ID, int Rarity, int Part, int Level, boolean Equipping, int Skill_ID_1, int Skill_ID_2){
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
