package com.seladanghijau.testocr;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvOutput;
    Button btnRead;
    ImageView ivImg;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvOutput = (TextView) findViewById(R.id.tvOutput);
        btnRead = (Button) findViewById(R.id.btnRead);
        ivImg = (ImageView) findViewById(R.id.ivImg);

        btnRead.setOnClickListener(this);

        ivImg.setImageResource(R.drawable.download);
    }

    private void output() {
        new readImg(this).execute();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRead:
                try {
                    output();
                    Toast.makeText(this, getAssets().open("tesseract").toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) { e.printStackTrace(); }
                break;
        }
    }

    private class readImg extends AsyncTask<Void, Void, Void> {
        private Context context;
        private String result;

        public readImg(Context context) {
            this.context = context;
        }

        protected Void doInBackground(Void... params) {
            try {
                Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.download);

                TessBaseAPI tessBaseAPI = new TessBaseAPI();
                tessBaseAPI.init("/mnt/sdcard/ocrtest/", "eng");
                tessBaseAPI.setImage(image);

                result = tessBaseAPI.getUTF8Text();
                tessBaseAPI.end();
            } catch (Exception e) { e.printStackTrace(); }

            return  null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            tvOutput.setText(result);
        }
    }
}
