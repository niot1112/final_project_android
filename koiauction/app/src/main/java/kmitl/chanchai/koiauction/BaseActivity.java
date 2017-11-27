package kmitl.chanchai.koiauction;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kmitl.chanchai.koiauction.Tool.LoadImageFromUri;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public FirebaseDatabase database;
    public DatabaseReference databaseref;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle mToggle;
    public android.support.design.widget.NavigationView NavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

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
    }
    public void setUsername() {
        NavigationView navigationView = findViewById(R.id.nav_bar);
        View hview = navigationView.getHeaderView(0);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        database = FirebaseDatabase.getInstance();
        databaseref = database.getReference();
        TextView user_name = hview.findViewById(R.id.user_name);
        TextView user_email = hview.findViewById(R.id.user_email);
        ImageView user_picture = hview.findViewById(R.id.user_picture);

        if(user != null){
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri url = user.getPhotoUrl();

            user_name.setText(name);
            user_email.setText(email);
            LoadImageFromUri.loadImageFromUri(url, this, user_picture);
        } else {
            goLoginScreen();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return false;
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }
    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
