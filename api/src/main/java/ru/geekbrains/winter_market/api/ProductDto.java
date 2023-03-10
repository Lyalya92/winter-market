package ru.geekbrains.winter_market.api;


import java.math.BigDecimal;

public class ProductDto {

    private  Long id;

    private String title;

    private BigDecimal price;

    private String category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {return category; }

    public void setCategory(String category) {this.category = category;}

}
