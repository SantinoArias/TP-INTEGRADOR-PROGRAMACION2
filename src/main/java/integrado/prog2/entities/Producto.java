package integrado.prog2.entities;

import integrado.prog2.exceptions.StockInsuficienteException;
import integrado.prog2.exceptions.ValidacionException;

public class Producto extends Base {

    private String nombre;
    private Double precio;
    private String descripcion;
    private Integer stock;
    private String imagen;
    private Boolean disponible;
    private Categoria categoria;

    public Producto(Long id, String nombre, Double precio, String descripcion, Integer stock,
                    String imagen, Boolean disponible, Categoria categoria) {
        super(id);
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        this.imagen = imagen;
        this.disponible = disponible;
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void validarStockPara(Integer cantidad) throws StockInsuficienteException, ValidacionException {
        if (cantidad == null || cantidad <= 0) {
            throw new ValidacionException("La cantidad del detalle debe ser mayor a 0.");
        }
        if (stock < cantidad) {
            throw new StockInsuficienteException("No hay stock suficiente para el producto: " + nombre
                    + ". Stock actual: " + stock + ", solicitado: " + cantidad + ".");
        }
    }

    public void restarStock(Integer cantidad) throws StockInsuficienteException, ValidacionException {
        validarStockPara(cantidad);
        stock -= cantidad;
        if (stock == 0) {
            disponible = false;
        }
    }

    @Override
    public String toString() {
        String nombreCategoria = categoria != null ? categoria.getNombre() : "Sin categoria";
        return "ID: " + getId()
                + " | Producto: " + nombre
                + " | Precio: $" + String.format("%.2f", precio)
                + " | Stock: " + stock
                + " | Disponible: " + (disponible ? "Si" : "No")
                + " | Categoria: " + nombreCategoria;
    }
}
