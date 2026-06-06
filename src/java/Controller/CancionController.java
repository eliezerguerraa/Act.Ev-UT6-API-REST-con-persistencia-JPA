package java.Controller;

import java.Model.Cancion;
import java.Service.CancionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/canciones")
public class CancionController {

    private final CancionService cancionService;

    public CancionController(CancionService cancionService) {
        this.cancionService = cancionService;
    }

    // GET /api/v1/canciones
    @GetMapping
    public ResponseEntity<List<Cancion>> getAll() {
        return ResponseEntity.ok(cancionService.findAll());
    }

    // GET /api/v1/canciones/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Cancion> getById(@PathVariable Long id) {
        return cancionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/v1/canciones
    // Body debe incluir "album": { "id": 1 } para asociar el álbum
    @PostMapping
    public ResponseEntity<Cancion> create(@RequestBody Cancion cancion) {
        return cancionService.save(cancion)
                .map(saved -> ResponseEntity.status(201).body(saved))
                .orElse(ResponseEntity.badRequest().build());
    }

    // PUT /api/v1/canciones/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Cancion> update(@PathVariable Long id, @RequestBody Cancion cancion) {
        return cancionService.update(id, cancion)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/v1/canciones/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (cancionService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Módulo A — GET /api/v1/canciones/album/{albumId}
    @GetMapping("/album/{albumId}")
    public ResponseEntity<List<Cancion>> getByAlbum(@PathVariable Long albumId) {
        return ResponseEntity.ok(cancionService.findByAlbum(albumId));
    }

    // Módulo B — GET /api/v1/canciones/buscar?titulo=...
    @GetMapping("/buscar")
    public ResponseEntity<List<Cancion>> buscar(
            @RequestParam(required = false) String titulo) {
        return ResponseEntity.ok(cancionService.buscar(titulo));
    }
}
