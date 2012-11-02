package cinelli.lam.geocal1;

public class Position {
	private int id;
	private String etichetta;
	private double longitudine;
	private double latitudine;
	private boolean pref;
	
	public int getId(){
		return this.id;
	}
	public String getEtichetta(){
		return this.etichetta;
	}
	public double getLongitudine(){
		return this.longitudine;
	}
	public double getLatitudine(){
		return this.latitudine;
	}
	public boolean getPref(){
		return this.pref;
	}
	
	public void setId(int id){
		this.id = id;
	}
	public void setEtichetta(String etichetta){
		this.etichetta = etichetta;
	}
	public void setLongitudine(double x){
		this.longitudine = x;
	}
	public void setLatitudine(double y){
		this.latitudine = y;
	}
	public void setPref(boolean pref){
		this.pref = pref;
	}
}
