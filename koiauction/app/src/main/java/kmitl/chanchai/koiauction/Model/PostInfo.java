package kmitl.chanchai.koiauction.Model;


import android.widget.EditText;

public class PostInfo {
    public String type, size, price, bidrate, time;

    public PostInfo(){
    }

    public PostInfo(String type,String size,String price,String bidrate,String time){
        this.type = type;
        this.size = size;
        this.price = price;
        this.bidrate = bidrate;
        this.time = time;
    }
}
