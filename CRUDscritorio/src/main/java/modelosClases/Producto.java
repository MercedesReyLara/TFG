package modelosClases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="product")
public class Producto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombreP",unique = true)
    private String nombreP;

    @Column(name = "descripcionP")
    private String descripcionP;

    @Column(name = "precio")
    private Double precio;

    @OneToMany(mappedBy = "product",cascade=CascadeType.REMOVE)
    private List<Review> resenas=new ArrayList<>();

    
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;

    @ManyToMany(mappedBy = "productsU")
    private List<User> users=new ArrayList<>();
    
    public Producto() {
        
    }

    public Producto( int id,String nombreP, String descripcionP, double precio, Category category) {
        this.id=id;
        this.nombreP = nombreP;
        this.descripcionP = descripcionP;
        this.precio = precio;
        this.category = category;
    }

        @Override
        public String toString() {
            return "Nombre:" + nombreP + "Descripcion: " + descripcionP + "Precio:" + precio + "Resenas:" 
                    + resenas.toString() + "Categoria:" + category+"";
        }

    public int getId() {
        return id;
    }

    public String getNombreP() {
        return nombreP;
    }

    public String getDescripcionP() {
        return descripcionP;
    }

    public Double getPrecio() {
        return precio;
    }

    public Category getCategory() {
        return category;
    }

    public List<Review> getResenas() {
        return resenas;
    }

    public void setResenas(List<Review> resenas) {
        this.resenas = resenas;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombreP(String nombreP) {
        this.nombreP = nombreP;
    }

    public void setDescripcionP(String descripcionP) {
        this.descripcionP = descripcionP;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}

