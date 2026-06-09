package integrado.prog2.services;

import integrado.prog2.entities.Categoria;
import integrado.prog2.exceptions.EntidadNoEncontradaException;
import integrado.prog2.exceptions.ValidacionException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaService {

    private final List<Categoria> categorias;
    private long siguienteId;

    public CategoriaService() {
        this.categorias = new ArrayList<>();
        this.siguienteId = 1L;
    }

    public Categoria crear(String nombre, String descripcion) throws ValidacionException {
        validarTextoObligatorio(nombre, "El nombre de la categoria es obligatorio.");
        validarNombreUnico(nombre, null);

        Categoria categoria = new Categoria(siguienteId++, nombre.trim(), descripcion.trim());
        categorias.add(categoria);
        return categoria;
    }

    public List<Categoria> listarActivas() {
        return categorias.stream()
                .filter(Categoria::isActivo)
                .toList();
    }

    public List<Categoria> listarTodas() {
        return new ArrayList<>(categorias);
    }

    public Categoria buscarActivaPorId(Long id) throws EntidadNoEncontradaException {
        return categorias.stream()
                .filter(Categoria::isActivo)
                .filter(categoria -> categoria.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntidadNoEncontradaException("No existe una categoria activa con ID " + id + "."));
    }

    public Categoria editar(Long id, String nuevoNombre, String nuevaDescripcion)
            throws EntidadNoEncontradaException, ValidacionException {

        Categoria categoria = buscarActivaPorId(id);

        if (nuevoNombre != null && !nuevoNombre.isBlank()) {
            validarNombreUnico(nuevoNombre, id);
            categoria.setNombre(nuevoNombre.trim());
        }
        if (nuevaDescripcion != null) {
            categoria.setDescripcion(nuevaDescripcion.trim());
        }
        return categoria;
    }

    public void eliminar(Long id) throws EntidadNoEncontradaException, ValidacionException {
        Categoria categoria = buscarActivaPorId(id);
        if (categoria.tieneProductosActivos()) {
            throw new ValidacionException("No se puede eliminar la categoria porque tiene productos activos asociados.");
        }
        categoria.eliminar();
    }

    private void validarNombreUnico(String nombre, Long idActual) throws ValidacionException {
        boolean existe = categorias.stream()
                .filter(Categoria::isActivo)
                .anyMatch(categoria -> categoria.getNombre().equalsIgnoreCase(nombre.trim())
                        && (idActual == null || !categoria.getId().equals(idActual)));

        if (existe) {
            throw new ValidacionException("Ya existe una categoria activa con ese nombre.");
        }
    }

    private void validarTextoObligatorio(String texto, String mensaje) throws ValidacionException {
        if (texto == null || texto.isBlank()) {
            throw new ValidacionException(mensaje);
        }
    }
}
