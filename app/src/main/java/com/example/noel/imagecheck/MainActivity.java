package com.example.noel.imagecheck;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.io.InputStream;
import java.net.URL;
import android.os.AsyncTask;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    Button load_img;
    ImageView img1,img2;
    Bitmap bitmap[] = new Bitmap[2];
    ProgressDialog pDialog;
    EditText urlText1,urlText2;
    TextView text;
    int img1ht,img2ht;
    int img1wd,img2wd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        load_img = (Button)findViewById(R.id.enter);
        img1 = (ImageView)findViewById(R.id.imagedisplay1);
        img2 = (ImageView)findViewById(R.id.imagedisplay2);
        urlText1 = (EditText)findViewById(R.id.url1);
        urlText2 = (EditText)findViewById(R.id.url2);
        text = (TextView)findViewById(R.id.urloutput);
        load_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String urlSring1 = urlText1.getText().toString();
                String urlSring2 = urlText2.getText().toString();
                //text.append(name);
                bitmap[0]=null;
                bitmap[1]=null;
                new LoadImage().execute(urlSring1,urlSring2);

            }
        });


    }
    private class LoadImage extends AsyncTask<String,Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading Image ....");
            pDialog.show();

        }
        protected Void doInBackground(String... args) {
                    if(args.length>0){
                        for(int i=0;i<args.length;i++)
                        {
                    try {
                        bitmap[i] = BitmapFactory.decodeStream((InputStream) new URL(args[i]).getContent());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }}}


            return null;
        }

        protected void onPostExecute(Void result) {

            if(bitmap[0] != null && bitmap[1] != null){
                img1.setImageBitmap(bitmap[0]);
                img2.setImageBitmap(bitmap[1]);
                pDialog.dismiss();}


            else{

                pDialog.dismiss();
                Toast.makeText(MainActivity.this, "Invalid URL or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }

}
