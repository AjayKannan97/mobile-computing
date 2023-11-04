package com.example.mobilecomputing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity2 extends AppCompatActivity {

    String category;
    byte[] photByteArray;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle extras = getIntent().getExtras();
        photByteArray = extras.getByteArray("picture");
        Bitmap photo = BitmapFactory.decodeByteArray(photByteArray, 0, photByteArray.length);
        ImageView capturedImage = (ImageView) findViewById(R.id.imageView);
        capturedImage.setImageBitmap(photo);

        Spinner categoryDropDown=findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> dropDownAdapter=ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        dropDownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        categoryDropDown.setAdapter(dropDownAdapter);
        categoryDropDown.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                category = parent.getItemAtPosition(pos).toString();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button uploadPhoto=(Button) findViewById(R.id.button);
        uploadPhoto.setOnClickListener(new  View.OnClickListener(){
            public void onClick(View view){
                postRequest();
            }
        });
    }

    private void postRequest() {
        String url = "http://10.0.2.2:"+5000+"/";
        MultipartBody.Builder photoObject = new MultipartBody.Builder().setType(MultipartBody.FORM);
        photoObject.addFormDataPart("image", "image"+ ".jpg", RequestBody.create(MediaType.parse("image/*jpg"), photByteArray));
        photoObject.addFormDataPart("category", category);
        RequestBody requestObject = photoObject.build();
        OkHttpClient httpHelper = new OkHttpClient();
        Request flaskRequest = new Request.Builder().post(requestObject).url(url).build();

        httpHelper.newCall(flaskRequest).enqueue(new Callback() {
            public void onResponse(Call call, final Response response) throws IOException {
                Intent refreshPage = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(refreshPage);
            }

            public void onFailure(final Call call, final IOException e) {
            }
        });
    }
}