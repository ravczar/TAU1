package lab4.tau.carRest.domain;

import java.util.concurrent.atomic.AtomicLong;

public class Car {
	private static final AtomicLong count = new AtomicLong(0);
	private long id;
	private String model;
	private String brand;
	private String body;
	private Integer dateOfProduction;
	
	public Car() { }
	public Car(String Model, String Brand, String Body, Integer DateOfProduction ) {
		this.id = count.incrementAndGet();
		this.model = Model;
		this.brand = Brand;
		this.body = Body;
		this.dateOfProduction = DateOfProduction;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getModel() { return model; }
	public void setModel(String model) {
		this.model = model;
	}

	public String getBrand() {return brand; }
	public void setBrand(String brand) { this.brand = brand; }

	public String getBody() {return body; }
	public void setBody(String body) { this.body = body; }

	public int getDateOfProduction() {
		return dateOfProduction;
	}
	public void setDateOfProduction(int dateOfProduction) {
		this.dateOfProduction = dateOfProduction;
	}

}
