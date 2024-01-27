package cookbook;

public class IngredientNode {

	// stores ingredient's measurement data
	private String measurement;
	// stores ingredient's name data
	private String ingredient; 
	private IngredientNode next; 
	
	public IngredientNode(String measurement, String ingredient) {
		super();
		this.ingredient = ingredient;
		this.measurement = measurement;
		this.next=null;
	}

	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}
	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}
	public void setNext(IngredientNode next) {
		this.next = next;
	}
	public String getIngredient() {
		return ingredient;
	}
	public String getMeasurement() {
		return measurement;
	}
	public IngredientNode getNext() {
		return next;
	}
	
}
