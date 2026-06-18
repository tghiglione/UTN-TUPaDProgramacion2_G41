package integradoR.prog2.entities;

import java.time.LocalDateTime;
import java.util.Objects;

/*
 * Clase abstracta Base: factoriza los atributos comunes a todas las entidades
 */
public abstract class Base {

    protected Long id;
    protected boolean eliminado;
    protected LocalDateTime createdAt;

    public Base() {
        this.eliminado = false;
        this.createdAt = LocalDateTime.now();
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /*
     * equals y hashCode basados en id: dos entidades son la misma si comparten id.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Base base = (Base) o;
        return Objects.equals(id, base.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
