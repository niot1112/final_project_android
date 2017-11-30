package kmitl.chanchai.koiauction.Model;


import android.widget.EditText;

public class PostInfo {
    public String type, size, price, bidrate, time, bidder_id;

    public PostInfo(){
    }

    public PostInfo(String type,String size,String price,String bidrate,String time, String bidder_id){
        this.type = type;
        this.size = size;
        this.price = price;
        this.bidrate = bidrate;
        this.time = time;
        this.bidder_id = bidder_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBidrate() {
        return bidrate;
    }

    public void setBidrate(String bidrate) {
        this.bidrate = bidrate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBidder_id() {
        return bidder_id;
    }

    public void setBidder_id(String bidder_id) {
        this.bidder_id = bidder_id;
    }
}
