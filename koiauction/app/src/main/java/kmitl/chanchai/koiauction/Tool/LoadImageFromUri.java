package kmitl.chanchai.koiauction.Tool;


import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import kmitl.chanchai.koiauction.R;

public class LoadImageFromUri {
    public static void loadImageFromUri(Uri url, Activity activity, ImageView imageView) {
        Picasso.with(activity).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }
}
