package dto;

public abstract class User {
  private String email;
  private Status status;

  public enum Status {
    NORMAL, STAY, STOP;
    
  }

  public String getEmail() {
    return email;
  }

  public Status getStatus() {
    return status;
  }
  
  public boolean isActive() {
    return this.status == Status.NORMAL;
  }
  
  public abstract String getProvider();
}
