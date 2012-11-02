package cinelli.lam.geocal1;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class NewPrefPosition extends MapActivity implements OnClickListener {
	
	EditText ED_posizione;
	Button AddNewPrefLoc;
	ImageButton SearchPref_Loc;
	
	private MapController mapController;
	private MapView mapView;
 	private LocationManager locationManager;
	GeoPoint point;
	List<Overlay> mapOverlays;
    MapOverlay itemizedoverlay;
    Drawable drawable;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_new_pref_position);
        
        AddNewPrefLoc = (Button) findViewById(R.id.SaveNewPrefLocation);
        SearchPref_Loc = (ImageButton) findViewById(R.id.CercaPrefLocation);
        ED_posizione = (EditText) findViewById(R.id.posizione_Pref);
        
        /*Gestione Mappa*/
        mapView = (MapView) findViewById(R.id.mapView_Pref);
        mapView.setBuiltInZoomControls(true);
        mapView.setSatellite(true);
        mapView.setClickable(true);
        mapController = mapView.getController();
        mapController.setZoom(14);
        mapOverlays = mapView.getOverlays();
        
        AddNewPrefLoc.setOnClickListener(this);
        SearchPref_Loc.setOnClickListener(this);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_new_pref_position, menu);
        return true;
    }

	public void onClick(View v) {
		switch ( v.getId() ) {
			case R.id.SaveNewPrefLocation:
					savePrefLocation();
					finish();
			 	break;
			case R.id.CercaPrefLocation:
					putMarkers();
				break;
		}
	}
	public void savePrefLocation(){
		EventsDataSource DS = new EventsDataSource(this);
		Position p = new Position();
		
		String title = ED_posizione.getText().toString();
		double Lat = microDegreesToDegrees(point.getLatitudeE6());
		double Lon = microDegreesToDegrees(point.getLongitudeE6());
		System.out.println(title);
		p.setEtichetta(title);
		p.setPref(true);
		 
		p.setLongitudine(Lon);
		p.setLatitudine(Lat);
		
		DS.open();
		DS.addNewLocation(p);
		DS.close();
		
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
	public static double microDegreesToDegrees(int microDegrees) {
	    return microDegrees / 1E6;
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
