package dto;

public class TierDto {
  private int tierSeq;
  private String tier;
  private int benefit;
  private int condition;
  
  public TierDto(String tier, int benefit, int condition) {
    this.tier = tier;
    this.benefit = benefit;
    this.condition = condition;
  }

  public TierDto(int tierSeq, String tier, int benefit, int condition) {
    this.tierSeq = tierSeq;
    this.tier = tier;
    this.benefit = benefit;
    this.condition = condition;
  }

  public int getTierSeq() {
    return tierSeq;
  }

  public String getTier() {
    return tier;
  }

  public int getBenefit() {
    return benefit;
  }

  public int getCondition() {
    return condition;
  }
  
  
  
}
