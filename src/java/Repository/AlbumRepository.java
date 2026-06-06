package java.Repository;

import com.musicapi.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    // Módulo A — obtener álbumes de un artista concreto (usa la relación)
    List<Album> findByArtistaId(Long artistaId);

    // Módulo B — busca álbumes por título (sin distinción mayúsculas)
    List<Album> findByTituloContainingIgnoreCase(String titulo);

    // Módulo B — filtra álbumes por género
    List<Album> findByGeneroIgnoreCase(String genero);
}
