package java.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "artistas")
@Data
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "pais_origen")
    private String paisOrigen;

    // Un artista puede tener muchos álbumes
    // @JsonIgnore evita la referencia circular al serializar (Artista → Álbum →
    // Artista → ...)
    // @ToString.Exclude evita el StackOverflow en el toString() de Lombok por la
    // misma razón
    @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private List<Album> albumes;
}
