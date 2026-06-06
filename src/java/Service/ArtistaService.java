package java.Service;

import java.Model.Artista;
import java.Repository.ArtistaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistaService {

    // Inyección por constructor — más recomendable que @Autowired en el campo
    private final ArtistaRepository artistaRepository;

    public ArtistaService(ArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
    }

    public List<Artista> findAll() {
        return artistaRepository.findAll();
    }

    public Optional<Artista> findById(Long id) {
        return artistaRepository.findById(id);
    }

    public Artista save(Artista artista) {
        return artistaRepository.save(artista);
    }

    public Optional<Artista> update(Long id, Artista datos) {
        return artistaRepository.findById(id).map(artista -> {
            artista.setNombre(datos.getNombre());
            artista.setPaisOrigen(datos.getPaisOrigen());
            return artistaRepository.save(artista);
        });
    }

    public boolean deleteById(Long id) {
        if (artistaRepository.existsById(id)) {
            artistaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Módulo B — búsqueda con parámetros opcionales
    public List<Artista> buscar(String nombre, String paisOrigen) {
        if (nombre != null && !nombre.isBlank()) {
            return artistaRepository.findByNombreContainingIgnoreCase(nombre);
        }
        if (paisOrigen != null && !paisOrigen.isBlank()) {
            return artistaRepository.findByPaisOrigenIgnoreCase(paisOrigen);
        }
        return artistaRepository.findAll();
    }
}
