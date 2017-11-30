package kmitl.chanchai.koiauction;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import kmitl.chanchai.koiauction.Model.PostInfo;

public class InPostActivity extends BaseActivity {
    private String image, post_id;
    private PostInfo postInfo = new PostInfo();
    private TextView time, type, size, bidder_id, price, bidrate;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_post);
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
        databaseref = FirebaseDatabase.getInstance().getReference();

        imageView = findViewById(R.id.image);
        time = findViewById(R.id.time);
        type = findViewById(R.id.type);
        size = findViewById(R.id.size);
        bidder_id = findViewById(R.id.bidder_id);
        price = findViewById(R.id.priceNow);
        bidrate = findViewById(R.id.bidRate);

        Intent intent = getIntent();
        post_id = intent.getStringExtra("post_id");

        setPostInfo();
    }

    private void setPage() {
        Uri download_uri = Uri.parse(image);
        Picasso.with(this).load(download_uri).fit().centerCrop().into(imageView);

        time.setText(postInfo.getTime());
        type.setText("Type : "+postInfo.getType());
        size.setText("Size : "+postInfo.getSize());
        bidder_id.setText("Last bid by : "+postInfo.getBidder_id());
        price.setText(postInfo.getPrice()+" ฿");
        bidrate.setText(postInfo.getBidrate()+" ฿");
    }

    private void setPostInfo() {
        databaseref.child("post").child(post_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postInfo.setType(dataSnapshot.child("type").getValue().toString());
                postInfo.setBidder_id(dataSnapshot.child("bidder_id").getValue().toString());
                postInfo.setBidrate(dataSnapshot.child("bidrate").getValue().toString());
                postInfo.setTime(dataSnapshot.child("time").getValue().toString());
                postInfo.setPrice(dataSnapshot.child("price").getValue().toString());
                postInfo.setSize(dataSnapshot.child("size").getValue().toString());
                image = dataSnapshot.child("image").getValue().toString();
                setPage();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

    public void onBid(View view) {
        int priceUpdate = Integer.parseInt(postInfo.getPrice())+Integer.parseInt(postInfo.getBidrate());
        databaseref.child("post").child(post_id).child("price").setValue(String.valueOf(priceUpdate));
        databaseref.child("post").child(post_id).child("bidder_id").setValue(getUsername().toString());
        setPostInfo();
        setPage();
    }
}
