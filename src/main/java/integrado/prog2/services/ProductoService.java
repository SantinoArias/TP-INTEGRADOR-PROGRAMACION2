package integrado.prog2.services;

import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Producto;
import integrado.prog2.exceptions.EntidadNoEncontradaException;
import integrado.prog2.exceptions.NegocioException;
import integrado.prog2.exceptions.ValidacionException;
import java.util.ArrayList;
import java.util.List;

public class ProductoService {

    private final List<Producto> productos;
    private final CategoriaService categoriaService;
    private long siguienteId;

    public ProductoService(CategoriaService categoriaService) {
        this.productos = new ArrayList<>();
        this.categoriaService = categoriaService;
        this.siguienteId = 1L;
    }

    public Producto crear(String nombre, String descripcion, Double precio, Integer stock,
                          String imagen, Boolean disponible, Long categoriaId) throws NegocioException {

        validarProducto(nombre, precio, stock);
        validarNombreUnico(nombre, null);
        Categoria categoria = categoriaService.buscarActivaPorId(categoriaId);

        Producto producto = new Producto(siguienteId++, nombre.trim(), precio, descripcion.trim(), stock,
                imagen.trim(), disponible, categoria);
        productos.add(producto);
        categoria.agregarProducto(producto);
        return producto;
    }

    public List<Producto> listarActivos() {
        return productos.stream()
                .filter(Producto::isActivo)
                .toList();
    }

    public List<Producto> listarDisponibles() {
        return productos.stream()
                .filter(Producto::isActivo)
                .filter(Producto::isDisponible)
                .filter(producto -> producto.getStock() > 0)
                .toList();
    }

    public List<Producto> listarPorCategoria(Long categoriaId) throws EntidadNoEncontradaException {
        Categoria categoria = categoriaService.buscarActivaPorId(categoriaId);
        return productos.stream()
                .filter(Producto::isActivo)
                .filter(producto -> categoria.equals(producto.getCategoria()))
                .toList();
    }

    public Producto buscarActivoPorId(Long id) throws EntidadNoEncontradaException {
        return productos.stream()
                .filter(Producto::isActivo)
                .filter(producto -> producto.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntidadNoEncontradaException("No existe un producto activo con ID " + id + "."));
    }

    public Producto editar(Long id, String nombre, String descripcion, Double precio, Integer stock,
                           String imagen, Boolean disponible, Long categoriaId) throws NegocioException {

        Producto producto = buscarActivoPorId(id);

        if (nombre != null && !nombre.isBlank()) {
            validarNombreUnico(nombre, id);
            producto.setNombre(nombre.trim());
        }
        if (descripcion != null) {
            producto.setDescripcion(descripcion.trim());
        }
        if (precio != null) {
            validarPrecio(precio);
            producto.setPrecio(precio);
        }
        if (stock != null) {
            validarStock(stock);
            producto.setStock(stock);
        }
        if (imagen != null) {
            producto.setImagen(imagen.trim());
        }
        if (disponible != null) {
            producto.setDisponible(disponible);
        }
        if (categoriaId != null) {
            Categoria nuevaCategoria = categoriaService.buscarActivaPorId(categoriaId);
            Categoria categoriaAnterior = producto.getCategoria();
            if (categoriaAnterior != null) {
                categoriaAnterior.quitarProducto(producto);
            }
            producto.setCategoria(nuevaCategoria);
            nuevaCategoria.agregarProducto(producto);
        }
        return producto;
    }

    public void eliminar(Long id) throws EntidadNoEncontradaException {
        Producto producto = buscarActivoPorId(id);
        producto.eliminar();
        producto.setDisponible(false);
    }

    private void validarProducto(String nombre, Double precio, Integer stock) throws ValidacionException {
        if (nombre == null || nombre.isBlank()) {
            throw new ValidacionException("El nombre del producto es obligatorio.");
        }
        validarPrecio(precio);
        validarStock(stock);
    }

    private void validarPrecio(Double precio) throws ValidacionException {
        if (precio == null || precio < 0) {
            throw new ValidacionException("El precio del producto no puede ser negativo.");
        }
    }

    private void validarStock(Integer stock) throws ValidacionException {
        if (stock == null || stock < 0) {
            throw new ValidacionException("El stock del producto no puede ser negativo.");
        }
    }

    private void validarNombreUnico(String nombre, Long idActual) throws ValidacionException {
        boolean existe = productos.stream()
                .filter(Producto::isActivo)
                .anyMatch(producto -> producto.getNombre().equalsIgnoreCase(nombre.trim())
                        && (idActual == null || !producto.getId().equals(idActual)));

        if (existe) {
            throw new ValidacionException("Ya existe un producto activo con ese nombre.");
        }
    }
}
