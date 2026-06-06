package java.Service;

import java.Model.Cancion;
import java.Repository.AlbumRepository;
import java.Repository.CancionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CancionService {

    private final CancionRepository cancionRepository;
    private final AlbumRepository albumRepository;

    public CancionService(CancionRepository cancionRepository, AlbumRepository albumRepository) {
        this.cancionRepository = cancionRepository;
        this.albumRepository = albumRepository;
    }

    public List<Cancion> findAll() {
        return cancionRepository.findAll();
    }

    public Optional<Cancion> findById(Long id) {
        return cancionRepository.findById(id);
    }

    public Optional<Cancion> save(Cancion cancion) {
        Long albumId = cancion.getAlbum().getId();
        return albumRepository.findById(albumId).map(album -> {
            cancion.setAlbum(album);
            return cancionRepository.save(cancion);
        });
    }

    public Optional<Cancion> update(Long id, Cancion datos) {
        return cancionRepository.findById(id).map(cancion -> {
            cancion.setTitulo(datos.getTitulo());
            cancion.setDuracion(datos.getDuracion());
            cancion.setTrackNumber(datos.getTrackNumber());
            if (datos.getAlbum() != null && datos.getAlbum().getId() != null) {
                albumRepository.findById(datos.getAlbum().getId())
                        .ifPresent(cancion::setAlbum);
            }
            return cancionRepository.save(cancion);
        });
    }

    public boolean deleteById(Long id) {
        if (cancionRepository.existsById(id)) {
            cancionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Módulo A — canciones de un álbum concreto
    public List<Cancion> findByAlbum(Long albumId) {
        return cancionRepository.findByAlbumId(albumId);
    }

    // Módulo B — búsqueda por título
    public List<Cancion> buscar(String titulo) {
        if (titulo != null && !titulo.isBlank()) {
            return cancionRepository.findByTituloContainingIgnoreCase(titulo);
        }
        return cancionRepository.findAll();
    }
}
