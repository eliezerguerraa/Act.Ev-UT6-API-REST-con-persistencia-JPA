package java.Service;

import java.Model.Album;
import java.Repository.AlbumRepository;
import java.Repository.ArtistaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistaRepository artistaRepository;

    public AlbumService(AlbumRepository albumRepository, ArtistaRepository artistaRepository) {
        this.albumRepository = albumRepository;
        this.artistaRepository = artistaRepository;
    }

    public List<Album> findAll() {
        return albumRepository.findAll();
    }

    public Optional<Album> findById(Long id) {
        return albumRepository.findById(id);
    }

    public Optional<Album> save(Album album) {
        // Verificamos que el artista referenciado existe antes de guardar
        Long artistaId = album.getArtista().getId();
        return artistaRepository.findById(artistaId).map(artista -> {
            album.setArtista(artista);
            return albumRepository.save(album);
        });
    }

    public Optional<Album> update(Long id, Album datos) {
        return albumRepository.findById(id).map(album -> {
            album.setTitulo(datos.getTitulo());
            album.setAnio(datos.getAnio());
            album.setGenero(datos.getGenero());
            // Si viene un artista nuevo en el body, actualizamos la FK
            if (datos.getArtista() != null && datos.getArtista().getId() != null) {
                artistaRepository.findById(datos.getArtista().getId())
                        .ifPresent(album::setArtista);
            }
            return albumRepository.save(album);
        });
    }

    public boolean deleteById(Long id) {
        if (albumRepository.existsById(id)) {
            albumRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Módulo A — álbumes de un artista concreto
    public List<Album> findByArtista(Long artistaId) {
        return albumRepository.findByArtistaId(artistaId);
    }

    // Módulo B — búsqueda con parámetros opcionales
    public List<Album> buscar(String titulo, String genero) {
        if (titulo != null && !titulo.isBlank()) {
            return albumRepository.findByTituloContainingIgnoreCase(titulo);
        }
        if (genero != null && !genero.isBlank()) {
            return albumRepository.findByGeneroIgnoreCase(genero);
        }
        return albumRepository.findAll();
    }
}
