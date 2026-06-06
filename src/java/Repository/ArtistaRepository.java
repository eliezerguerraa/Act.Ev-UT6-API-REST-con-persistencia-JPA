package java.Repository;

import java.Model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long> {

    // Módulo B — método derivado: busca artistas cuyo nombre contenga el texto (sin
    // distinción mayúsculas)
    List<Artista> findByNombreContainingIgnoreCase(String nombre);

    // Módulo B — método derivado: filtra artistas por país de origen
    List<Artista> findByPaisOrigenIgnoreCase(String paisOrigen);
}
