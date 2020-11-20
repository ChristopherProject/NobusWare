package QuarantineAPI.system.object;

import java.util.ArrayList;

import QuarantineAPI.system.exception.HippoFileException;

public final class HippoFileObject {
  private final String name;
  
  private final ArrayList<HippoFileElement> elements;
  
  public HippoFileObject(String name) {
    this.name = name;
    this.elements = new ArrayList<>();
  }
  
  public HippoFileElement getElement(String element) throws HippoFileException {
    for (HippoFileElement hippoFileElement : this.elements) {
      if (hippoFileElement.getName().equalsIgnoreCase(element))
        return hippoFileElement; 
    } 
    throw new HippoFileException("An exception was thrown whilst finding element!\n\nDetails:\nObject Name: " + this.name + "\nElement Name: " + element + "\nFound: null\n\nMaybe the element does not exist?");
  }
  
  public HippoFileObject addElement(HippoFileElement element) {
    this.elements.add(element);
    return this;
  }
  
  public void scanElements(char[] bounds) throws HippoFileException {
    StringBuilder boundBuilder = new StringBuilder();
    for (char character : bounds)
      boundBuilder.append(character); 
    boundBuilder.delete(0, 1).delete(boundBuilder.length() - 1, boundBuilder.length());
    String[] elements = boundBuilder.toString().split("\\)\\(");
    for (String element : elements) {
      StringBuilder nameBuilder = new StringBuilder();
      String[] elementIndexes = element.split("\\[");
      nameBuilder.append(elementIndexes[0]);
      StringBuilder valueBuilder = new StringBuilder();
      ArrayList<String> values = new ArrayList<>();
      for (int i = 1; i < elementIndexes.length; i++) {
        String value = elementIndexes[i];
        valueBuilder.append(value).delete(valueBuilder.length() - 1, valueBuilder.length());
        values.add(valueBuilder.toString());
        valueBuilder.delete(0, valueBuilder.length());
      } 
      HippoFileElement hippoFileElement = new HippoFileElement(nameBuilder.toString(), values.toArray());
      this.elements.add(hippoFileElement);
    } 
  }
  
  public ArrayList<HippoFileElement> getElements() {
    return this.elements;
  }
  
  public String getContent() {
    StringBuilder content = new StringBuilder();
    content.append(this.name + "{");
    for (HippoFileElement hippoFileElement : this.elements)
      content.append(hippoFileElement.getContent()); 
    content.append("}\n");
    return content.toString();
  }
  
  public String getName() {
    return this.name;
  }
}
