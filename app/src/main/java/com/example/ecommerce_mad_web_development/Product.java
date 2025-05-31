package com.example.ecommerce_mad_web_development;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private String id;
    private String title;
    private String price;
    private String originalPrice;
    private String condition;
    private String description;
    private String imageUrl;
    private String sellerName;
    private String sellerEmail;
    private float rating;
    private int reviewCount;

    // Constructor
    public Product(String id, String title, String price, String originalPrice,
                   String condition, String description, String imageUrl,
                   String sellerName, String sellerEmail, float rating, int reviewCount) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.originalPrice = originalPrice;
        this.condition = condition;
        this.description = description;
        this.imageUrl = imageUrl;
        this.sellerName = sellerName;
        this.sellerEmail = sellerEmail;
        this.rating = rating;
        this.reviewCount = reviewCount;
    }

    // Parcelable implementation
    protected Product(Parcel in) {
        id = in.readString();
        title = in.readString();
        price = in.readString();
        originalPrice = in.readString();
        condition = in.readString();
        description = in.readString();
        imageUrl = in.readString();
        sellerName = in.readString();
        sellerEmail = in.readString();
        rating = in.readFloat();
        reviewCount = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(price);
        dest.writeString(originalPrice);
        dest.writeString(condition);
        dest.writeString(description);
        dest.writeString(imageUrl);
        dest.writeString(sellerName);
        dest.writeString(sellerEmail);
        dest.writeFloat(rating);
        dest.writeInt(reviewCount);
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getPrice() { return price; }
    public String getOriginalPrice() { return originalPrice; }
    public String getCondition() { return condition; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public String getSellerName() { return sellerName; }
    public String getSellerEmail() { return sellerEmail; }
    public float getRating() { return rating; }
    public int getReviewCount() { return reviewCount; }
}