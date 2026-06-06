package java.Repository;

import java.Model.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CancionRepository extends JpaRepository<Cancion, Long> {

    // Módulo A — canciones de un álbum concreto
    List<Cancion> findByAlbumId(Long albumId);

    // Módulo B — busca canciones por título (sin distinción mayúsculas)
    List<Cancion> findByTituloContainingIgnoreCase(String titulo);
}
