package com.example.mobilecomputing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button yesButton=(Button) findViewById(R.id.button1);
        yesButton.setOnClickListener(new  View.OnClickListener(){
            public void onClick(View view){
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1);
            }
        });

        Button noButton=(Button) findViewById(R.id.button2);
        noButton.setOnClickListener(new  View.OnClickListener(){
            public void onClick(View view){
                finish();
                System.exit(0);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap capturedPhoto = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        capturedPhoto.compress(Bitmap.CompressFormat.PNG, 100, byteStream);
        byte[] photoByteArray = byteStream.toByteArray();
        Intent nextPage = new Intent(MainActivity.this, MainActivity2.class);
        nextPage.putExtra("picture", photoByteArray);
        startActivity(nextPage);
    }
}