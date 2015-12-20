package com.micropop.contactz;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v4.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class Configure extends AppCompatActivity {
public AppCompatEditText namet;
    public AppCompatEditText numberet;
    public ImageView contactImage;
    public View imageView;
    public View textView;
    public TextView email;
    private static final String EXTRA_IMAGE = "resourseInt";
    private static final String EXTRA_NAME = "com.micropop.contactz.extraName";
    private static final String EXTRA_EMAIL = "com.micropop.contactz.extraEmail";
    private static final String EXTRA_NUMBER = "com.micropop.contactz.extraNumber";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);
         imageView = findViewById(R.id.thaImage);
        textView = findViewById(R.id.editText2);
        email = (TextView) findViewById(R.id.editText3);
        contactImage = (ImageView) findViewById(R.id.thaImage);
namet = (AppCompatEditText) findViewById(R.id.editText);
        numberet = (AppCompatEditText) findViewById(R.id.editText2);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_24dp);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_configure, menu);
        return true;
    }
    public void imageP(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 10);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK && requestCode == 10) {
            Uri selectedImageUri = data.getData();



                String[] projection = {MediaStore.MediaColumns.DATA};
                Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                        null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                final Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);
                ByteArrayOutputStream streame = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, streame);
                byte[] byteArray = streame.toByteArray();

                contactImage.setImageBitmap(bm);
            }
        }

    public void ok() {
        if (namet.getText().toString().equals("")) {
            Bitmap bmpo = BitmapFactory.decodeResource(getResources(), R.drawable.ic_person_24dp);
            ByteArrayOutputStream streame = new ByteArrayOutputStream();
            bmpo.compress(Bitmap.CompressFormat.PNG, 100, streame);
            byte[] byteArray = streame.toByteArray();
            Intent ContactNew = new Intent(Configure.this, ContactActivity.class);

            ContactNew.putExtra(EXTRA_NAME, "Untitled");
            ContactNew.putExtra(EXTRA_NUMBER, "123344");
            ContactNew.putExtra(EXTRA_EMAIL, "example@gmail.com");
            ContactNew.putExtra("picture", byteArray);

                startActivity(ContactNew);


        } else {
            Bitmap bmp = ((BitmapDrawable) contactImage.getDrawable()).getBitmap();

            ByteArrayOutputStream streame = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, streame);
            byte[] byteArray = streame.toByteArray();
            Intent ContactNew = new Intent(Configure.this, ContactActivity.class);
            String ContactName = namet.getText().toString();
            String ContactEmail = email.getText().toString();
            String ContactNumber = numberet.getText().toString();
            ContactNew.putExtra(EXTRA_NAME, ContactName);
            ContactNew.putExtra(EXTRA_NUMBER, ContactNumber);
            ContactNew.putExtra(EXTRA_EMAIL, ContactEmail);
            ContactNew.putExtra("picture", byteArray);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(this, imageView, getString(R.string.activity_image_trans));
                startActivity(ContactNew, options.toBundle());
            } else {

                startActivity(ContactNew);
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_ok) {
             ok();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
