package com.example.onlaynmagazin;

public class Product {
    String productimage;
    String productname;
    String productdescription;
    String productprice;
    String uploadkey;

    public Product() {
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductdescription() {
        return productdescription;
    }

    public void setProductdescription(String productdescription) {
        this.productdescription = productdescription;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getUploadkey() {
        return uploadkey;
    }

    public void setUploadkey(String uploadkey) {
        this.uploadkey = uploadkey;
    }
}
