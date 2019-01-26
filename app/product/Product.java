package product;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name="price")
    private long price;

    @Column(name="imageurl")
    @JsonProperty("imageurl")
    private String imageUrl;


    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_at")
    @JsonProperty("created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "updatedAt")
    @JsonProperty("updatedAt")
    private Date updatedAt;

    public Product(){

    }

    public Product(long userId, String name, long price, String imageUrl){
        this.userId = userId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
