package kmitl.chanchai.koiauction;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import kmitl.chanchai.koiauction.Model.PostInfo;


public class AddActivity extends BaseActivity {
    private ImageView imageView;
    private EditText type, size, price, bidrate, time;
    private String Stype, Ssize, Sprice, Sbidrate, Stime, user_id;

    private static final int PREVIEW_REQEST_CODE = 1;
    private static final int ADD_REQEST_CODE = 2;

    private StorageReference mStorage;
    private ProgressDialog mProgress;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        drawerLayout = findViewById(R.id.headbar);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView = findViewById(R.id.nav_bar);
        if (NavigationView != null) {
            NavigationView.setNavigationItemSelectedListener(this);
        }
        setUsername();

        imageView = findViewById(R.id.koi_image);
        mStorage = FirebaseStorage.getInstance().getReference();
        mProgress = new ProgressDialog(this);

        type = findViewById(R.id.koi_type);
        size = findViewById(R.id.koi_size);
        price = findViewById(R.id.koi_price);
        bidrate = findViewById(R.id.koi_bidrate);
        time = findViewById(R.id.koi_time);

        databaseref = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.home){
            finish();
        }else if(id == R.id.add_auction){
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
            finish();
        }else if(id == R.id.logout){
            logout();
        }
        return false;
    }

    public void onAdd(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, ADD_REQEST_CODE);
    }

    private void EdittextToString() {
        user_id = getUsername();
        Stype = type.getText().toString();
        Ssize = size.getText().toString();
        Sprice = price.getText().toString();
        Sbidrate = bidrate.getText().toString();
        Stime = time.getText().toString();

        PostInfo postInfo = new PostInfo(Stype, Ssize, Sprice, Sbidrate, Stime);
        databaseref.child("post").child(user_id).setValue(postInfo);
    }

    public void onUpload(View view) {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PREVIEW_REQEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PREVIEW_REQEST_CODE && resultCode == RESULT_OK)
        {
            Uri uri = data.getData();
            Picasso.with(AddActivity.this).load(uri).fit().centerCrop().into(imageView);
        }

        if(requestCode == ADD_REQEST_CODE && resultCode == RESULT_OK){
            mProgress.setMessage("Uploading Image");
            mProgress.show();

            Uri uri = data.getData();
            user_id = getUsername();

            StorageReference filepath = mStorage.child(user_id).child("image");

            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mProgress.dismiss();

                    Uri download_uri = taskSnapshot.getDownloadUrl();
                    Picasso.with(AddActivity.this).load(download_uri).fit().centerCrop().into(imageView);

                    Toast.makeText(AddActivity.this, "Uploading finished...", Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent(AddActivity.this, InPostActivity.class);
                    EdittextToString();
                    startActivity(intent2);
                    finish();
                }
            });
        }
    }

}
