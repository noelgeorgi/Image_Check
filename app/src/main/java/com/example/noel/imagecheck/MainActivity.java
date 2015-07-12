package com.example.noel.imagecheck;

import android.graphics.Color;
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
    Bitmap bitmap[] = new Bitmap[4];
    ProgressDialog pDialog;
    EditText urlText1,urlText2;
    TextView text;
    int pixel1,pixel2,r1=0,g1=0,b1=0,r2=0,g2=0,b2=0;
    long diff=0;
    String urlString1,urlString2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        load_img = (Button)findViewById(R.id.enter);
        img1 = (ImageView)findViewById(R.id.imagedisplay1);
        img2 = (ImageView)findViewById(R.id.imagedisplay2);
        urlText1 = (EditText)findViewById(R.id.url1);
        urlText2 = (EditText)findViewById(R.id.url2);
        text = (TextView)findViewById(R.id.percentval);
        load_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                urlString1="";
                urlString2="";
                urlString1 = urlText1.getText().toString();
                urlString2 = urlText2.getText().toString();
                bitmap[0]=null;
                bitmap[1]=null;
                r1=0;g1=0;b1=0;r2=0;g2=0;b2=0;
                diff=0;
                text.setText("Similarity Percent = ");
                new LoadImage().execute(urlString1,urlString2);

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
                bitmap[2]=Bitmap.createScaledBitmap(bitmap[0],300,300,true);
                bitmap[3]=Bitmap.createScaledBitmap(bitmap[1],300,300,true);
                img1.setImageBitmap(bitmap[2]);
                img2.setImageBitmap(bitmap[3]);
                for(int x=0;x<300;++x)
                {
                    for(int y=0;y<300;++y)
                    {
                        pixel1 = bitmap[2].getPixel(x,y);
                        pixel2 = bitmap[3].getPixel(x,y);
                        r1 = Color.red(pixel1);
                        g1 = Color.green(pixel1);
                        b1 = Color.blue(pixel1);
                        r2 = Color.red(pixel2);
                        g2 = Color.green(pixel2);
                        b2 = Color.blue(pixel2);
                        diff += Math.abs(r1 - r2);
                        diff += Math.abs(g1 - g2);
                        diff += Math.abs(b1-b2);

                    }
                }
                int n = 90000*3;
                double percent = 100 - diff*100/n/255;
                String prct=Integer.toString((int)percent);
                text.setText("Similarity Percent  = " + prct);
                pDialog.dismiss();}


            else{

                pDialog.dismiss();
                Toast.makeText(MainActivity.this, "Invalid URL or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }

}
