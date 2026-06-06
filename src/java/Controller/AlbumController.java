package java.Controller;

import java.Model.Album;
import java.Service.AlbumService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/albumes")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    // GET /api/v1/albumes
    @GetMapping
    public ResponseEntity<List<Album>> getAll() {
        return ResponseEntity.ok(albumService.findAll());
    }

    // GET /api/v1/albumes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Album> getById(@PathVariable Long id) {
        return albumService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/v1/albumes
    // Body debe incluir "artista": { "id": 1 } para asociar el artista
    @PostMapping
    public ResponseEntity<Album> create(@RequestBody Album album) {
        return albumService.save(album)
                .map(saved -> ResponseEntity.status(201).body(saved))
                .orElse(ResponseEntity.badRequest().build());
    }

    // PUT /api/v1/albumes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Album> update(@PathVariable Long id, @RequestBody Album album) {
        return albumService.update(id, album)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/v1/albumes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (albumService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Módulo A — GET /api/v1/albumes/artista/{artistaId}
    // Endpoint que usa la relación para obtener los álbumes de un artista
    @GetMapping("/artista/{artistaId}")
    public ResponseEntity<List<Album>> getByArtista(@PathVariable Long artistaId) {
        return ResponseEntity.ok(albumService.findByArtista(artistaId));
    }

    // Módulo B — GET /api/v1/albumes/buscar?titulo=...&genero=...
    @GetMapping("/buscar")
    public ResponseEntity<List<Album>> buscar(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String genero) {
        return ResponseEntity.ok(albumService.buscar(titulo, genero));
    }
}
