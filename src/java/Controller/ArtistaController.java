package java.Controller;

import java.Model.Artista;
import java.Service.ArtistaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/artistas")
public class ArtistaController {

    private final ArtistaService artistaService;

    public ArtistaController(ArtistaService artistaService) {
        this.artistaService = artistaService;
    }

    // GET /api/v1/artistas → lista todos los artistas
    @GetMapping
    public ResponseEntity<List<Artista>> getAll() {
        return ResponseEntity.ok(artistaService.findAll());
    }

    // GET /api/v1/artistas/{id} → 200 OK o 404 Not Found
    @GetMapping("/{id}")
    public ResponseEntity<Artista> getById(@PathVariable Long id) {
        return artistaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/v1/artistas → 201 Created
    @PostMapping
    public ResponseEntity<Artista> create(@RequestBody Artista artista) {
        Artista saved = artistaService.save(artista);
        return ResponseEntity.status(201).body(saved);
    }

    // PUT /api/v1/artistas/{id} → 200 OK o 404 Not Found
    @PutMapping("/{id}")
    public ResponseEntity<Artista> update(@PathVariable Long id, @RequestBody Artista artista) {
        return artistaService.update(id, artista)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/v1/artistas/{id} → 204 No Content o 404 Not Found
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (artistaService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Módulo B — GET /api/v1/artistas/buscar?nombre=...&paisOrigen=...
    // Los parámetros son opcionales (required=false) y tienen valor por defecto
    // vacío
    @GetMapping("/buscar")
    public ResponseEntity<List<Artista>> buscar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String paisOrigen) {
        return ResponseEntity.ok(artistaService.buscar(nombre, paisOrigen));
    }
}
