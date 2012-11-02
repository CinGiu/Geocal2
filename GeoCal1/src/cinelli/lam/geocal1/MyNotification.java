package cinelli.lam.geocal1;


public class MyNotification {
	private int id;
	private String etichetta;
	private String tipo;
	private String azione;

	
	public String getEtichetta(){
		return this.etichetta;
	}
	public int getId(){
		return this.id;
	}
	public String getTipo(){
		return this.tipo;
	}
	public String getAzione(){
		return this.azione;
	}
	
	
	public void setEtichetta(String etichetta){
		this.etichetta = etichetta;
	}
	public void setId(int id){
		this.id = id;
	}
	public void setTipo(String tipo){
		this.tipo = tipo;
	}
	public void setAzione(String azione){
		this.azione = azione;
	}
	
	

}