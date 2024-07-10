package dtoNaverMail;

public class RecipientForRequest {
  private String address = null;
  private String name = null;
  private String type = "R";
  private Object parameters = null;

  protected RecipientForRequest(String address, String name) {
    this.address = address;
    this.name = name;
  }

  public static RecipientForRequest of(String address, String name) {
    return new RecipientForRequest(address, name);
  }

}
