import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="shoppers")
public class Shopper{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="name")
    private String name;
    @ManyToMany
    @JoinTable(
            name="shoppers_products",
            joinColumns = @JoinColumn(name="shopper_id"),
            inverseJoinColumns = @JoinColumn(name="product_id")
    )
    private List<Product> products;
    public Shopper() {
    }

    public Shopper(String name) {
        this.name = name;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    @Override
    public String toString() {
        return String.format("name: %s", name);
    }
}
