package cinelli.lam.geocal1;

import java.util.ArrayList;


import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MapOverlay extends  ItemizedOverlay<OverlayItem> {
	
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	
    public MapOverlay(Drawable arg0, Context context) {
    	  super(boundCenterBottom(arg0));	
    	  mContext = context;
	}


	@Override
	protected OverlayItem createItem(int i) {
		  return mOverlays.get(i);	
	}
	
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}

	@Override
	public int size() {
		return mOverlays.size();
	}
} 
