package differnt_source.com.combo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;


public class Splash extends Activity {

    SharedPreferences pref;
    ImageView iv1;
    AnimationDrawable Anim;
    boolean counter=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);

     try{

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                public void run() {
                    copyDataBase1("ikon.db");
                    startHomeActivity();
                }
            }, 3350);

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    private void startHomeActivity() {
        Intent intent = new Intent(Splash.this, MainActivity.class);
        pref = getSharedPreferences("any_prefname", MODE_PRIVATE);

        if (pref.contains("Id"))
        {
            if(counter==true)
            {
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                counter=false;
            }
        }

        startActivity(intent);
        finish();

    }

    //  exportDatabse("bacchat.db");
    public void exportDatabse(String databaseName) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {

                String currentDBPath = "//data//" + getPackageName() + "//databases//" + databaseName + "";

                String backupDBPath = "ikon.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());

                    src.close();
                    dst.close();
                } else {
                    Log.e("not found", "filenot found");
                }
            }
        } catch (Exception e) {

        }
    }

    private void copyDataBase1(String databaseName) {
        Log.i("Database",
                "New database is being copied to device!");
        byte[] buffer = new byte[1024];
        OutputStream myOutput = null;
        int length;
        // Open your local db as the input stream
        InputStream myInput = null;
        try {
            myInput = getApplicationContext().getAssets().open(databaseName);
            // transfer bytes from the inputfile to the
            // outputfile
            String outFileName = Environment.getDataDirectory().getAbsolutePath() + "/data/" + getPackageName() + "/databases/";
            File db = new File(Environment.getDataDirectory().getAbsolutePath() + "/data/" + getPackageName() + "/databases");
            if (!db.exists()) {
                db.mkdir();
            }

            myOutput = new FileOutputStream(outFileName + databaseName);
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.close();
            myOutput.flush();
            myInput.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}