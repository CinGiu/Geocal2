package cinelli.lam.geocal1;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class EventsDataSource {
	 private SQLiteDatabase database;
	 private MySQLiteHelper dbHelper;
	 
	 
	 public EventsDataSource(Context context) {
		    dbHelper = new MySQLiteHelper(context);
	 }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }
	  /*--------------------GESTIONE EVENTI SUL DATABASE-------------*/
	  /* AGGIUNGE NUOVO EVENTO 
	   * GLI PASSO: L'OGGETTO EVENTO 
	   * RITORNA: Id evento
	   */
	  public int addNewEvent(Event evento){
		  
		  int id_pos = this.addNewLocation(evento.getPosizione());				//inserisco posizione nel db
		  int id_not = this.addNewNotification(evento.getNotifica());			//inserisco notifica nel db
		  
		  ContentValues values = new ContentValues();
		  
		  values.put("idnotifica", id_not);
		  values.put("idposizione", id_pos);
		  values.put("etichetta", evento.getEtichetta());
		  values.put("raggio", evento.getRaggio());
		  values.put("movimento", evento.getMovimento());
		  values.put("dipendenze", evento.getDipendenze());
		  values.put("stato", evento.getStato());
		  values.put("ripetizione", evento.getRipetizione());
		  
		  long id = database.insert("evento", null, values);					//inserisco evento nel db
		  return (int) id;		   
	  }
	  /* ELIMINA EVENTO E NOTIFICA ASSOCIATA
	   * GLI PASSO: L'OGGETTO EVENTO 
	   * RITORNA: VOID
	   */
	  public void deleteEvent(Event evento){
		  database.delete("evento", "_id = "+evento.getId(), null);
		  this.deleteLocation(evento.getPosizione());
		  this.deleteNotification(evento.getNotifica());
	  }
	  
	  public Event getEvent(int id){
		  String query = "SELECT * FROM evento WHERE _id="+id;
		  Cursor cursor = database.rawQuery(query, null);
		  cursor.moveToFirst();
		  return cursorToEvent(cursor);
	  }
	  
	  public List<Event> getAllEvents(){
		  List<Event> eventi = new ArrayList<Event>();
		  String query = "SELECT * FROM evento";
		  Cursor cursor = database.rawQuery(query, null);
		  
		if(cursor.getCount() == 0){
			System.out.println("Lista Eventi Vuota!");
			return eventi;
		}else{                 //ciclo tutto il cursore e riempio la lista di eventi
		  cursor.moveToFirst();
		  
		  while (!cursor.isAfterLast()) {	
		      Event evento = cursorToEvent(cursor);
		      eventi.add(evento);
		      cursor.moveToNext();
		    }
		    //chiudo il cursore
		    cursor.close();
		    return eventi;
		}
	  }
	  /* Funzione di appoggio di getAllEvents()
	   * serve a riempire un oggetto evento da un cursore e a restituirlo 
	   */
	  public Event cursorToEvent(Cursor cursor){
		  Event evento = new Event();
		  int N_ID = cursor.getInt(1);
		  int P_ID = cursor.getInt(2);

		  evento.setId(cursor.getInt(0));
		  evento.setEtichetta(cursor.getString(3));
		  evento.setRaggio(cursor.getInt(4));
		  evento.setMovimento(cursor.getString(5));
		  evento.setDipendenze(cursor.getInt(6));
		  evento.setStato(cursor.getString(7));
		  if(cursor.getInt(8) == 0 ){
			  evento.setRipetizione(false);
		  }else{
			  evento.setRipetizione(true);
		  }
		  	  
		  evento.setNotifica(this.getNotification(N_ID));
		  evento.setPosizione(this.getLocation(P_ID));
		  
		  return evento;
		  
	  }
	  
	  /*-------------------------GESTIONE POSIZIONI SUL DATABASE-----------------*/
	  public int addNewLocation(Position posizione){
		  ContentValues values = new ContentValues();
		  
		  values.put("etichetta", posizione.getEtichetta());
		  values.put("Lon", posizione.getLongitudine());
		  values.put("Lat", posizione.getLatitudine());
		  values.put("pref", posizione.getPref());
		  
		  long id = database.insert("posizione", null, values);
		  return (int) id;
		  
	  }
	  
	  public void deleteLocation(Position posizione){
		  database.delete("posizione", "_id = "+posizione.getId(),null);
	  }
	  
	  public Position getLocation(int id){
		  String query = "SELECT * FROM posizione WHERE _id="+id;
		  Cursor cursor = database.rawQuery(query, null);
		  cursor.moveToFirst();
		  if (cursor.getCount() == 0){
			  System.out.println("TABELLA POSIZIONI: vuota");
			  return null;
		  }else{
			  return cursorToLocation(cursor);
		  }
	  }
	  
	  public List<Position> getAllPrefLocation(){
		  List<Position> posizioni = new ArrayList<Position>();
		  String query = "SELECT * FROM posizione WHERE pref='1'";
		  Cursor cursor = database.rawQuery(query, null);
		  
		  cursor.moveToFirst();
		  
		  while (!cursor.isAfterLast()) { 			//ciclo tutto il cursore e riempio la lista di eventi
			  Position posizione = cursorToLocation(cursor);
			  posizioni.add(posizione);
		      cursor.moveToNext();
		    }
		    //chiudo il cursore
		    cursor.close();
		    return posizioni;
	  }
	  
	  public Position cursorToLocation(Cursor cursor){
		  Position posizione =new Position();
		  
		  posizione.setId(cursor.getInt(0));
		  posizione.setEtichetta(cursor.getString(1) );
		  posizione.setLatitudine(cursor.getDouble(2) * 1E6);
		  posizione.setLongitudine(cursor.getDouble(3) * 1E6);
		  
		
		  if(cursor.getInt(4) == 0 ){
			  posizione.setPref(false);
		  }else{
			  posizione.setPref(true);
		  }
		  
		  return posizione;
	  }
	  
	  /*---------------------------------GETIONE NOTIFICHE DAL DATABASE---------------------------*/
	  public int addNewNotification(MyNotification notifica){

		  ContentValues values = new ContentValues();
		  
		  values.put("etichetta", notifica.getEtichetta());
		  values.put("tipo", notifica.getTipo());
		  values.put("azione", notifica.getAzione());
		  
		  long id = database.insert("notifica", null, values);
		  return (int) id;
		 
	  }
	  
	  public void deleteNotification(MyNotification notifica){
		  database.delete("notifica", "_id = "+notifica.getId(), null);	
	  }
	  
	  public MyNotification getNotification(int id){
		  String query = "SELECT * FROM notifica WHERE _id="+id;
		  Cursor cursor = database.rawQuery(query, null);
		  if (cursor.getCount() == 0){
			  System.out.println("TABELLA NOTIFICHE: vuota");
			  return null;
		  }else{
			  cursor.moveToFirst();
			  return cursorToNotification(cursor);	
		  }
	  }
	  
	  public MyNotification cursorToNotification(Cursor cursor){
		  MyNotification notifica = new MyNotification();
		  
		  //notifica.setId(cursor.getInt(0));
		  notifica.setEtichetta(cursor.getString(1));
		  notifica.setTipo(cursor.getString(2));
		  notifica.setAzione(cursor.getString(3));
		  
		  return notifica;
	  }
	  
}

