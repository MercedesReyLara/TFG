
package modelosClases;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="nombre",unique = true)
    private String nombre;

    @Column(name="descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "category",cascade=CascadeType.ALL,orphanRemoval=true)
    private List<Producto> products; 

    public Category() {
    }

    public Category(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Category(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Producto> getProducts() {
        return products;
    }

    public void setProducts(List<Producto> products) {
        this.products = products;
    }
    
    
}

