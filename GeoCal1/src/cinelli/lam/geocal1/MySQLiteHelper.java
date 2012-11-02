package cinelli.lam.geocal1;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 

public class MySQLiteHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "eventi2.db";
  private static final int DATABASE_VERSION = 17;

  // Database creation sql statement
  private static final String DATABASE_CREATE_EVENTS = "CREATE TABLE IF NOT EXISTS evento " +
	  		"( _id integer PRIMARY KEY autoincrement, " +
	  		" idnotifica integer NOT NULL, " +
	  		" idposizione integer NOT NULL, " +
	  		" etichetta text NOT NULL, " +
	  		" raggio integer NOT NULL, " +
	  		" movimento text NOT NULL, " +
	  		" dipendenze text NOT NULL, " +
	  		" stato text NOT NULL, " +
	  		" ripetizione text NOT NULL); ";
  private static final String DATABASE_CREATE_NOTIFICATION = "CREATE TABLE IF NOT EXISTS notifica " +
	  		"( _id integer PRIMARY KEY autoincrement, " +
	  		" etichetta text NOT NULL," +
	  		" tipo text NOT NULL, " +
	  		"azione text NOT NULL); ";
	  		
  private static final String DATABASE_CREATE_POSITION = "CREATE TABLE IF NOT EXISTS posizione " +
	  		"( _id integer PRIMARY KEY autoincrement, " +
	  		"etichetta text NOT NULL, " +
	  		"Lat real NOT NULL, " +
	  		"Lon real NOT NULL," +
	  		" pref tinyint(1) NOT NULL);";
  
  public MySQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE_EVENTS);
    database.execSQL(DATABASE_CREATE_NOTIFICATION);
    database.execSQL(DATABASE_CREATE_POSITION);
    
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(MySQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS evento");
    db.execSQL("DROP TABLE IF EXISTS notifica");
    db.execSQL("DROP TABLE IF EXISTS posizione");
    onCreate(db);
  }

} 