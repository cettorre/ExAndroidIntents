package com.example.cettorre.ejercicioandroidintents;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    ImageView mImageView;

    Button btnTakePhoto;
    Button btnSelectFile;
    Button btnInsertWeb;
    Button btnInsertNumber;
    Button btnProgram;
    Button btnOpen;

    EditText etInsertWeb;
    EditText etInsertNumber;
    EditText etDate;
    EditText etTime;
    EditText etText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        mImageView=findViewById(R.id.foto);

        btnTakePhoto=findViewById(R.id.btnTakePhoto);
        btnSelectFile=findViewById(R.id.btnSelectFile);
        btnInsertWeb=findViewById(R.id.btnInsertWeb);
        btnInsertNumber=findViewById(R.id.btnInsertNumber);
        btnProgram=findViewById(R.id.btnProgram);
        btnOpen=findViewById(R.id.btnOpen);

        etInsertWeb=findViewById(R.id.etInsertWeb);
        etInsertNumber=findViewById(R.id.etInsertNumber);
        etDate=findViewById(R.id.etDate);
        etTime=findViewById(R.id.etTime);
        etText=findViewById(R.id.etText);

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                capturePhoto("com.example.cettorre.ejercicioandroidintents.foto1.jpg");

            }
        });



        btnSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btnInsertWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("go","gooooooooooooooooooooooooooooooooo");

                Uri webpage = Uri.parse(etInsertWeb.getText().toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);


                //??
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }else {
                    Log.e("go","nooooooooooooooooooooooo");

                }

            }
        });

        btnInsertNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + etInsertNumber.getText().toString()));
                startActivity(intent);


            }
        });

        btnProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                            .putExtra(AlarmClock.EXTRA_HOUR, Integer.valueOf(etDate.getText().toString()))
                            .putExtra(AlarmClock.EXTRA_MINUTES, Integer.valueOf(etTime.getText().toString()));
                    startActivity(intent);
                }catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "only numbers are admitted", Toast.LENGTH_SHORT);
                    t.show();
                }


            }
        });

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                i.putExtra("text",etText.getText().toString());
                startActivity(i);

            }
        });


    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final Uri mLocationForPhotos= null;//>>asignar para guardar imagen

    public void capturePhoto(String targetFilename) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      //  intent.putExtra(MediaStore.EXTRA_OUTPUT,                 Uri.withAppendedPath(mLocationForPhotos, targetFilename));
        //Attempt to invoke virtual method 'android.net.Uri$Builder android.net.Uri.buildUpon()' on a null object reference

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }


    static final int REQUEST_IMAGE_GET = 1;

    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            //capture thumbnail from extra
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);

    //        Bitmap thumbnail = data.getParcelable("data"); //cannot resolve method
            // Do other work with full size photo saved in mLocationForPhotos


            //get selected image and assign to mImageView after selectImage method
            Uri fullPhotoUri = data.getData();
            mImageView.setImageURI(fullPhotoUri);


        }
    }
}
