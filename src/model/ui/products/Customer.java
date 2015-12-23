package model.ui.products;

public class Customer {

	private int id;
	private String name;
	private String secondName;
	
	public Customer(){
		
	}
	
	public Customer(String name, String secondName ) {
		this.name = name;
		this.secondName = secondName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		setSecondName("");
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	
}
