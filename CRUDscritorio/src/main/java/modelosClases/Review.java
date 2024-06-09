package modelosClases;


import javax.persistence.*;


@Entity
@Table(name="review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="nombreR")
    private String nombreR;
    @Column(name="opinion")
    private String opinion;
    @Column(name="puntuacion")
    private int puntuacion;
    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="product_id",nullable = false)
    private Producto product;
}
