package QuarantineAPI.system.object;

public final class HippoFileElement {
  private final String name;
  
  private final Object[] values;
  
  public HippoFileElement(String name, Object... values) {
    this.name = name;
    this.values = values;
  }
  
  public String getContent() {
    StringBuilder content = new StringBuilder();
    content.append("(").append(this.name);
    for (Object value : this.values)
      content.append("[").append(value).append("]"); 
    content.append(")");
    return content.toString().replace("[[", "[").replace("]]", "]");
  }
  
  public String getName() {
    return this.name;
  }
  
  public Object[] getValues() {
    return this.values;
  }
}
