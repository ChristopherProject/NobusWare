package it.nobusware.client.config;

public class Config {

	private String name;

	public Config(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}