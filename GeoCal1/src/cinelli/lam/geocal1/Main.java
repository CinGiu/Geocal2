package cinelli.lam.geocal1;   


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.view.Menu;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;



import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;



public class Main extends MapActivity implements OnClickListener, OnItemClickListener{
	private ListView listaEventi;
	private MapView mapView;
	List<Position> listaPosizioni = new ArrayList<Position>();
    MapController mc;
    List<Overlay> mapOverlays;
        
    GeoPoint p;
    MapOverlay itemizedoverlay;
    Drawable drawable;
     
    LocationManager locationManager;
    EventsDataSource DS = new EventsDataSource(this);
	int[] list_IDevent = new int[50];

    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
            
        final Button addNweEvent = (Button) findViewById(R.id.button1);
        addNweEvent.setOnClickListener(this);
        
        listaEventi = (ListView)findViewById(R.id.listEvents);
        
        mapView = (MapView) findViewById(R.id.mapView);
        mapOverlays = mapView.getOverlays();
        drawable = this.getResources().getDrawable(R.drawable.mapmarker);
        itemizedoverlay = new MapOverlay(drawable, this); 
       
        listaEventi.setOnItemClickListener(new OnItemClickListener()
        {
        	public void onItemClick(AdapterView<?> arg0, View v, int position, long id)
        	{
        		Intent i = new Intent(getApplicationContext(), ShowEvent.class);
        		i.putExtra("ID", list_IDevent[position]);
        		startActivity(i);
        	}
        });
        createListEvent();
        
        manageMap();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
        getMenuInflater().inflate(R.menu.main, menu);
	        menu.add("Pref. Position").setOnMenuItemClickListener(new OnMenuItemClickListener() {
	            public boolean onMenuItemClick(MenuItem item) {
			            	Intent i = new Intent(getApplicationContext(), NewPrefPosition.class);
			        		startActivity(i);
	                    return true;
	            }
	       });
        return true;
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/*	
	 * CREA LA MAPPA E GESTISCE I CONTROLLI
	 * 
	 * */
	private void manageMap(){
		
        mc = mapView.getController();
        mapOverlays.clear();
        mapView.invalidate();
        
       //----------------------- metodi per la gestione della mappa
        
        mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);
        
        
        mc.setZoom(12);
        for(int i=0; i < listaPosizioni.size(); i++){
        	p = new GeoPoint((int) (listaPosizioni.get(i).getLatitudine()), (int) (listaPosizioni.get(i).getLongitudine()));
        	OverlayItem overlayitem = new OverlayItem(p, "qualcosa", listaPosizioni.get(i).getEtichetta());
        	itemizedoverlay.addOverlay(overlayitem);
        }
        if(listaPosizioni.size() != 0){
        	mapOverlays.add(itemizedoverlay);
        }
	}
	
	
	/*
	 * CREA LA LISTA DI EVENTI PER LA PRIMA PAGINA
	 * METODO PRIVATO
	 * 
	 * */
	private void createListEvent(){
		DS.open();
		List<Event> values = DS.getAllEvents();
       
        ArrayList<HashMap<String, Object>> data=new ArrayList<HashMap<String,Object>>();
       
        for(int i=0; i < values.size(); i++){
        	Event e = values.get(i);
        	
        	HashMap<String,Object> eventsMap=new HashMap<String, Object>();
        	eventsMap.put("etichetta", e.getEtichetta());
        	eventsMap.put("raggio", e.getRaggio());
        	eventsMap.put("stato", e.getStato());
        	data.add(eventsMap);
        	        	
        	listaPosizioni.add(e.getPosizione());
        	list_IDevent[i] = e.getId();
        	
        	Intent resultIntent = new Intent(this, SuccessEvent.class);
       
        	
    		resultIntent.putExtra("ID", e.getId());
    		resultIntent.putExtra("Father", "Main");
    		
            PendingIntent resultPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, resultIntent, 0);
            
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
           
           	locationManager.addProximityAlert(((double) e.getPosizione().getLatitudine() / 1E6), ((double) e.getPosizione().getLongitudine() / 1E6), 500, e.getRaggio(), resultPendingIntent );
           	IntentFilter intentFilter = new IntentFilter();
           	registerReceiver(new NewNotification(),intentFilter);
        }
         
        String[] from={"etichetta", "raggio", "stato"};
        int[] to={R.id.eventEtichetta,R.id.eventRaggio, R.id.eventStato};
        
        SimpleAdapter adapter=new SimpleAdapter(
                getApplicationContext(),
                data,								//sorgente dati
                R.layout.list_events, 				//layout contenente gli id di "to"
                from,
                to);
        listaEventi.setAdapter(adapter);
        
        DS.close();
	}
	
	@Override
	public void onResume()
	    {  // After a pause OR at startup
	    super.onResume();
		    
	    createListEvent();
        manageMap();
	         
	     }

	/*Onclick del bottone Nuovo Evento*/
	public void onClick(View v) {
		
		switch ( v.getId() ) {
			case R.id.button1:
				Intent ListEvent = new Intent(getApplicationContext(), NewEvent.class);	
				startActivity(ListEvent);
			 	break;
		}
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
	}
	
}
