package com.micropop.contactz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {

    private static final String EXTRA_NAME = "com.micropop.contactz.extraName";
    private static final String EXTRA_NUMBER = "com.micropop.contactz.extraNumber";
    private static final String EXTRA_EMAIL = "com.micropop.contactz.extraEmail";
    public TextView numberView;
    public TextView mailView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //setTheme(R.style.AppTheme_transparent);

        //} else {
            //setTheme(R.style.AppTheme_NoActionBar);
        //}
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact);

        mailView = (TextView) findViewById(R.id.mailadress);

        numberView = (TextView) findViewById(R.id.phonenumber);
        String itemName = getIntent().getStringExtra(EXTRA_NAME);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
       collapsingToolbarLayout.setTitle(itemName);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Set the status bar to dark-semi-transparentish
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
               Toolbar toolbarr = (Toolbar) findViewById(R.id.toolbar);
            // Set paddingTop of toolbar to height of status bar.
            // Fixes statusbar covers toolbar issue
            toolbarr.setPadding(0, getStatusBarHeight(), 0, 0);


        } else {

        }
        String itemNumber = getIntent().getStringExtra(EXTRA_NUMBER);
        String itemEmail = getIntent().getStringExtra(EXTRA_EMAIL);
        mailView.setText(itemEmail);
        numberView.setText(itemNumber);
        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("picture");

        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        ImageView imageV = (ImageView) findViewById(R.id.imagennn);
        imageV.setImageBitmap(bmp);
    }
    public void callP(View view) {
        String numbahh = "tel:" + numberView.getText().toString();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(numbahh));


        startActivity(callIntent);
    }
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    public void emailAdresss(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + mailView.getText().toString())); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, mailView.getText().toString());
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
