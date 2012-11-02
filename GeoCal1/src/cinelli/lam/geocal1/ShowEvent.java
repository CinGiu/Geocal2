package cinelli.lam.geocal1;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.widget.TextView;

public class ShowEvent extends MapActivity {
	
	EventsDataSource DS = new EventsDataSource(this);
	TextView title;
	TextView type;
	TextView msg;
	
	private MapView mapView;
	MapController mc;
	List<Overlay> mapOverlays;
	GeoPoint p;
    MapOverlay itemizedoverlay;
    Drawable drawable;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);
        Intent i = getIntent();
        
        int id_event = i.getIntExtra("ID", 0);
        System.out.println("id evento in ShowEvent: "+id_event);
        
        title = (TextView) findViewById(R.id.Event_etichetta_SW);
        type = (TextView) findViewById(R.id.Event_tipo_SW);
        msg = (TextView) findViewById(R.id.Event_testo_SW);
        mapView = (MapView) findViewById(R.id.mapView_SW);
        mapOverlays = mapView.getOverlays();
        drawable = this.getResources().getDrawable(R.drawable.mapmarker);
        itemizedoverlay = new MapOverlay(drawable, this);
        
        showEvent(id_event);
        
    }
    
    public void showEvent(int id){
    	DS.open();
    	Event e = DS.getEvent(id);
    	title.setText(e.getEtichetta());
    	type.setText(e.getNotifica().getTipo());
    	DS.close();
    	
    	/*Metodi per la gestione della mappa*/
    	mc = mapView.getController();
    	mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);
        mc.setZoom(12);
        p = new GeoPoint((int) (e.getPosizione().getLatitudine() ),(int)(e.getPosizione().getLongitudine()));
        OverlayItem overlayitem = new OverlayItem(p, "qualcosa", e.getPosizione().getEtichetta());
        itemizedoverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedoverlay);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_show_event, menu);
        return true;
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
