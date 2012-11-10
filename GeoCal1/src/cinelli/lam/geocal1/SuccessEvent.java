package cinelli.lam.geocal1;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.os.Bundle;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.Menu;
import android.widget.TextView;

public class SuccessEvent extends MapActivity{
	EventsDataSource DS = new EventsDataSource(this);
	TextView title;
	TextView raggio;
	TextView ripetizione;
	
	private MapView mapView;
	MapController mc;
	List<Overlay> mapOverlays;
	GeoPoint p;
    MapOverlay itemizedoverlay;
    Drawable drawable;
    
    NotificationManager mNotificationManager;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_event);
        Intent i = getIntent();
        
        int id_event = i.getIntExtra("ID", 0);
        String Father = i.getStringExtra("Father");
        System.out.println("Id evento in SuccesEvent:  "+id_event);
        
        title = (TextView) findViewById(R.id.Event_etichetta_SE);
        raggio = (TextView) findViewById(R.id.Event_Raggio_SE);
        ripetizione = (TextView) findViewById(R.id.Event_Ripetizione_SE);
        mapView = (MapView) findViewById(R.id.mapView_SE);
        mapOverlays = mapView.getOverlays();
        drawable = this.getResources().getDrawable(R.drawable.mapmarker);
        itemizedoverlay = new MapOverlay(drawable, this);
        if(Father.equals("Main")){
        	showNotifica(id_event);
        	showEvent(id_event);
        }else{
        showEvent(id_event);
        }
        
    }
    
    public void showNotifica(int id_event){
    	NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.mapmarker)
		        .setContentTitle("My notification")
		        .setContentText("Hello World!")
		        .setDefaults(Notification.DEFAULT_ALL);
		

		Intent resultIntent = new Intent(this, SuccessEvent.class);
		resultIntent.putExtra("ID", id_event);
		resultIntent.putExtra("Father", "Notifica");
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(SuccessEvent.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);
		mNotificationManager =
			    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(0, mBuilder.build());
        
    }
    
    public void showEvent(int id){
    	DS.open();
    	
    	Event e = DS.getEvent(id);
    	DS.deleteEvent(e);
    	
    	title.setText(e.getEtichetta());
    	raggio.setText(e.getRaggio());
    	if(e.getRipetizione()){
    		ripetizione.setText("Si");
    	}else{
    		ripetizione.setText("No");
    	}
    	
    	
    	DS.close();
    	
    	/*Metodi per la gestione della mappa*/
    	mc = mapView.getController();
    	mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);
        mc.setZoom(12);
        p = new GeoPoint((int) e.getPosizione().getLatitudine(),(int) e.getPosizione().getLongitudine());
        OverlayItem overlayitem = new OverlayItem(p, "qualcosa", e.getPosizione().getEtichetta());
        itemizedoverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedoverlay);
        mNotificationManager.cancelAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_success_event, menu);
        return true;
    }
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
