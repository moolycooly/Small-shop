import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="price")
    private long price;

    @ManyToMany
    @JoinTable(
        name="shoppers_products",
        joinColumns = @JoinColumn(name="product_id"),
        inverseJoinColumns = @JoinColumn(name="shopper_id")
    )
    private List<Shopper> shoppers;

    public Product(String name, long price) {
        this.name = name;
        this.price = price;
    }

    public Product() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public List<Shopper> getShoppers() {
        return shoppers;
    }

    public void setShoppers(List<Shopper> shoppers) {
        this.shoppers = shoppers;
    }
    @Override
    public String toString() {
        return String.format("name: '%s', price: %d",name,price);
    }
}
