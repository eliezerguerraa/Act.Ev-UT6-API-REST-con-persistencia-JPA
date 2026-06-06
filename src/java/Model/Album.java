package java.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "albumes")
@Data
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(name = "anio")
    private Integer anio;

    private String genero;

    // Muchos álbumes pertenecen a un artista
    // @JoinColumn define el nombre de la FK en la tabla albumes
    @ManyToOne
    @JoinColumn(name = "artista_id", nullable = false)
    private Artista artista;

    // Un álbum tiene muchas canciones
    // @JsonIgnore y @ToString.Exclude por la misma razón que en Artista
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private List<Cancion> canciones;
}