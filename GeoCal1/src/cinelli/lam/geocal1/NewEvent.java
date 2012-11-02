package cinelli.lam.geocal1;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class NewEvent extends MapActivity implements OnClickListener, OnItemSelectedListener{
	
	EventsDataSource DS = new EventsDataSource(this);
	Button addNweEvent;
	EditText ED_etichetta; 
	AutoCompleteTextView ED_posizione;
	EditText ED_PromemoMsg;
	EditText ED_Raggio;
	Spinner SP_movimento;
	Spinner SP_App;
	Spinner SP_Serv;
	Spinner SP_azione;
	LinearLayout root; 
	View promemoria;
	View app;
	View servizio;
	CheckBox Ripetition_CB;
	int sceltaID = 0;
		/*Variabili per la gestione della mappetta*/
	private MapController mapController;
	private MapView mapView;
 	private LocationManager locationManager;
 	GeoPoint point;
    List<Overlay> mapOverlays;
    MapOverlay itemizedoverlay;
    Drawable drawable;
    ImageButton Find_Position;
    private static final ArrayList<String> listPrefPosition = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        
        addNweEvent = (Button) findViewById(R.id.SaveNewEvent);
        
        ED_etichetta = (EditText) findViewById(R.id.eventEtichetta_ED);
        ED_posizione = (AutoCompleteTextView) findViewById(R.id.posizione_ED);
        ED_PromemoMsg = (EditText) findViewById(R.id.ED_Promem);
        ED_Raggio = (EditText) findViewById(R.id.ED_Raggio);
        
        SP_azione = (Spinner) findViewById(R.id.Action_spinner);
        SP_App = (Spinner) findViewById(R.id.app_spinner);
        SP_Serv = (Spinner) findViewById(R.id.service_spinner);
        SP_movimento = (Spinner) findViewById(R.id.Movimenti_spinner);
        
        Ripetition_CB = (CheckBox) findViewById(R.id.Ripetition_CB);
        
        root = (LinearLayout) findViewById(R.id.NChangable);
        promemoria = (View) findViewById(R.id.l_promemoria);
        app = (View) findViewById(R.id.l_app);
        servizio = (View) findViewById(R.id.l_servizio);
       
        //Gestione Mappa
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.setBuiltInZoomControls(true);
        mapView.setSatellite(true);
        mapView.setClickable(true);
        mapController = mapView.getController();
        mapController.setZoom(14);
        mapOverlays = mapView.getOverlays();
        Find_Position = (ImageButton) findViewById(R.id.imageButton1);
        
        Find_Position.setOnClickListener(this);
        addNweEvent.setOnClickListener(this);
        ED_posizione.setOnClickListener(this);
        SP_azione.setOnItemSelectedListener(this);
        
        fillAutoCompliteLocation();
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, listPrefPosition);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.posizione_ED);
        textView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_new_event, menu);
        return true;
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	public void fillAutoCompliteLocation(){
		DS.open();
		
		List<Position> plist = DS.getAllPrefLocation();
		for(int i=0; i < plist.size(); i++){
			listPrefPosition.add(plist.get(i).getEtichetta());
			System.out.println(plist.get(i).getEtichetta());
		}
		DS.close();
	}
	
	public void onClick(View v) {
		
		switch ( v.getId() ) { 
			case R.id.SaveNewEvent:
					saveNewEvent();
					finish();
				break;	
			case R.id.imageButton1:
					putMarkers();
				break;
		}
	}
	
	// Metodo per la gestione dello spiner_action (Scelta dell'azione da interpretare)
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		
			try{
				if (sceltaID != 0) {
					root.removeAllViews();
		        }
				switch(pos){
				case 0:
					sceltaID = R.layout.promemoria;
					break;
				case 1:
					sceltaID = R.layout.servizio;
					break;
				case 2:
					sceltaID = R.layout.servizio;
					break;
				case 3:
					sceltaID = R.layout.app;
					break;
				case 4:
					sceltaID = R.layout.app;
					break;
				default:
					sceltaID = R.layout.promemoria;
					break;
				}
				View hiddenInfo = getLayoutInflater().inflate(sceltaID, root, false);
				
				root.addView(hiddenInfo);
			}catch(Exception e){

				System.err.println(e);
			}		
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void getAppList(){
		List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
        System.out.println(packs.size());
        for(int i=0;i<packs.size();i++) {
        	
	        PackageInfo p = packs.get(i);
	        System.out.println(p.applicationInfo.loadLabel(getPackageManager()).toString());
	        System.out.println(i);
        }
      }

	public void putMarkers(){
			String address = ED_posizione.getText().toString();
			Geocoder gc = new Geocoder(getBaseContext(), Locale.getDefault());
			mapOverlays = mapView.getOverlays();
			drawable = this.getResources().getDrawable(R.drawable.mapmarker);
			itemizedoverlay = new MapOverlay(drawable, this);
			try {
				 List<Address> addresses = gc.getFromLocationName(address, 5);
				 if (addresses.size() > 0) {
					 System.out.println(addresses.get(0).getLatitude());
					 
			            point = new GeoPoint((int) (addresses.get(0).getLatitude() * 1E6), 
			                             (int) (addresses.get(0).getLongitude() * 1E6));
			            OverlayItem overlayitem = new OverlayItem(point, "qualcosa", "qualcosaltro");
			        	
						itemizedoverlay.addOverlay(overlayitem);
			        }else{
			        	
			        }
			}catch(IOException e) {
	            e.printStackTrace();
	        }
			
			mapController.animateTo(point);
			mapController.setZoom(18);
			
		    mapOverlays.clear();
		    mapOverlays.add(itemizedoverlay);
		    
		    mapView.invalidate();	
		    InputMethodManager imm = (InputMethodManager)getSystemService(
		    	      Context.INPUT_METHOD_SERVICE);
		    	imm.hideSoftInputFromWindow(ED_posizione.getWindowToken(), 0);
		
	}
	public void saveNewEvent(){
		  
		 int selected;
		 /*creo le 3 classi da riempire*/
		 Event e = new Event();
		 MyNotification n = new MyNotification();
		 Position p = new Position();

		 /*riempio Position*/
		 p.setEtichetta(ED_posizione.getText().toString());
		 p.setPref(false);
		 
		 p.setLongitudine(microDegreesToDegrees(point.getLongitudeE6()));
		 p.setLatitudine(microDegreesToDegrees(point.getLatitudeE6()));
		 
		 /*riempio Notificaton*/
		 
		 if (SP_azione.getSelectedItem().toString().equals("Promemoria")){
			 n.setTipo("Promemoria");
			 n.setEtichetta(ED_etichetta.getText().toString());
			 n.setAzione("Alert");
		 }else{
			 n.setAzione(SP_azione.getSelectedItem().toString());
			 n.setTipo(SP_Serv.getSelectedItem().toString());
			 n.setEtichetta("");
		 }
		 
		 /*Riempio Event*/
		 e.setEtichetta(ED_etichetta.getText().toString());
		 e.setMovimento(SP_movimento.getSelectedItem().toString());
		 e.setStato("attivo");
		 
		 if(Ripetition_CB.isChecked()){
			 e.setRipetizione(true); 
		 }else{
			 e.setRipetizione(false);   
		 }
		 
		 e.setDipendenze(0); 		//TODO AGGIUNGERE COLLEGAMENTO A DIPENDENZE
		 if (ED_Raggio.getText().toString().equals("")){
			 //TODO fai qualcosa in caso non sia settato il raggio 
		 }else{
			 e.setRaggio(Integer.parseInt(ED_Raggio.getText().toString()));		
		 }
		
		 e.setNotifica(n);
		 e.setPosizione(p);
		 
		 DS.open();
		 DS.addNewEvent(e);
		 DS.close();
		 
		
	}
	public static double microDegreesToDegrees(int microDegrees) {
	    return microDegrees / 1E6;
	}

	
}