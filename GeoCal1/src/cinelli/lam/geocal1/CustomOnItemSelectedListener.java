package cinelli.lam.geocal1;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;


public class CustomOnItemSelectedListener extends Activity implements OnItemSelectedListener {
	
	View scelta;

	
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		 System.out.println("qui ci arrivo selettore di spinner");
		try{
			 System.out.println("qui ci arrivo new notifica2");
			//ViewGroup root = (ViewGroup)findViewById(R.id.NChangable);
			LinearLayout root = (LinearLayout)findViewById(R.id.NChangable);
			 System.out.println("qui ci arrivo new notifica3");
			if (scelta != null) {
	            root.removeView(scelta);
	        }
			int sceltaID;
			switch(pos){
			case 0:
				sceltaID = R.layout.promemoria;
				break;
			default:
				sceltaID = R.layout.promemoria;
				break;
			}
			View hiddenInfo = getLayoutInflater().inflate(sceltaID, root, false);
			
			root.addView(hiddenInfo);
		}catch(Exception e){

			System.out.println(e.getMessage());
		}
		
		/*if (selected.equals(a)){
			System.out.println(selected);
			
			if(Promem_Layout == null){
				//LayoutInflater inflater = getLayoutInflater();
				//View Promem_Info = getLayoutInflater().inflate(R.layout.promemoria, null);
				myLayout.addView(Promem_Layout );
			
			}
			
		}
		switch(selected){
		case "Promemoria":
			RelativeLayout Promem_Layout = (RelativeLayout)findViewById(R.id.LPromemo);
			if(Promem_Layout == null){
				RelativeLayout myLayout = (RelativeLayout)findViewById(R.id.NChangable);
				View Promem_Info = getLayoutInflater().inflate(R.layout.promemoria, myLayout, false);
				myLayout.addView(Promem_Info);
			
			}
		}*/
		
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
