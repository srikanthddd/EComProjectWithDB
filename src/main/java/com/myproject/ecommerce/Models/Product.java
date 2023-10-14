package com.myproject.ecommerce.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel {

    //private long id;
    private String title;
    private double price;
    @ManyToOne
    private Category category;
    private String description;
    private String image; //Will just store the image url

}
