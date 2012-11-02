package cinelli.lam.geocal1;

import cinelli.lam.geocal1.Position;
import cinelli.lam.geocal1.MyNotification;

public class Event {
	private String etichetta;
	private int id;
	private int raggio;
	private String movimento;
	private int dipendenze;
	private String stato;
	private boolean ripetizione;
	private MyNotification notifica;
	private Position posizione;
	
	public String getEtichetta(){
		return this.etichetta;
	}
	public int getId(){
		return this.id;
	}
	public int getRaggio(){
		return this.raggio;
	}
	public String getMovimento(){
		return this.movimento;
	}
	public int getDipendenze(){
		return this.dipendenze;
	}
	public String getStato(){
		return this.stato;
	}
	public boolean getRipetizione(){
		return this.ripetizione;
	}
	public MyNotification getNotifica(){
		return this.notifica;
	}
	public Position getPosizione(){
		return this.posizione;
	}
	
	public void setEtichetta(String etichetta){
		this.etichetta = etichetta;
	}
	public void setId(int id){
		this.id = id;
	}
	public void setRaggio(int raggio){
		this.raggio = raggio;
	}
	public void setMovimento(String movimento){
		this.movimento = movimento;
	}
	public void setDipendenze (int dipendenze){
		this.dipendenze = dipendenze;
	}
	public void setStato(String stato){
		this.stato = stato;
	}
	public void setRipetizione(boolean ripetizione){
		this.ripetizione = ripetizione;
	}
	public void setNotifica(MyNotification notifica){
		this.notifica = notifica;
	}
	public void setPosizione(Position posizione){
		this.posizione = posizione;
	}
	
	
	
}