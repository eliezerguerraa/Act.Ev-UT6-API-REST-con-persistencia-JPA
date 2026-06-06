package java.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "canciones")
@Data
public class Cancion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    // Duración en segundos (ej: 214 = 3:34)
    private Integer duracion;

    @Column(name = "track_number")
    private Integer trackNumber;

    // Muchas canciones pertenecen a un álbum
    // @ToString.Exclude para evitar bucle en Lombok toString
    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    @ToString.Exclude
    private Album album;
}
