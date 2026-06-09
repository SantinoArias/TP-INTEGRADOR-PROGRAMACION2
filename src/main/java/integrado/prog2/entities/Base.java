package integrado.prog2.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public abstract class Base {

    private Long id;
    private boolean eliminado;
    private LocalDateTime createdAt;

    protected Base() {
        this.eliminado = false;
        this.createdAt = LocalDateTime.now();
    }

    protected Base(Long id) {
        this();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public boolean isActivo() {
        return !eliminado;
    }

    public void eliminar() {
        this.eliminado = true;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getFechaCreacionFormateada() {
        return createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Base otraEntidad = (Base) obj;
        return id != null && Objects.equals(id, otraEntidad.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), id);
    }
}
