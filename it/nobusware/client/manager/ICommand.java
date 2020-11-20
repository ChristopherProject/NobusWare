package it.nobusware.client.manager;

public interface ICommand {
  String getAlias();
  
  String getDescription();
  
  String getSyntax();
  
  void onCommand(String comando, String[] args) throws Exception;
}
