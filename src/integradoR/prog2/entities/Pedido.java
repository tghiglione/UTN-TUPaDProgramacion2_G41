package integradoR.prog2.entities;

import integradoR.prog2.enums.Estado;
import integradoR.prog2.enums.FormaPago;
import integradoR.prog2.exception.StockInvalidoException;
import integradoR.prog2.interfaces.Calculable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
 * Pedido hereda de Base e implementa la interfaz Calculable.
 * Relacion N:1 con Usuario y composicion 1:N con DetallePedido
 */
public class Pedido extends Base implements Calculable {

    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Usuario usuario;
    private List<DetallePedido> detalles = new ArrayList<>();
    private int contadorDetalles = 0;

    public Pedido(LocalDate fecha, Estado estado, FormaPago formaPago, Usuario usuario) {
        super();
        this.fecha = fecha;
        this.estado = estado;
        this.formaPago = formaPago;
        this.usuario = usuario;
        this.total = 0.0;
    }

    /*
     * Crea un DetallePedido (subtotal = cantidad * precio), lo asocia al producto
     * y lo agrega a la lista. Valida la cantidad contra el stock: si es invalida
     * lanza StockInvalidoException, lo que permite cancelar el pedido.
     */
    public void addDetallePedido(int cantidad, Double precio, Producto producto) throws StockInvalidoException {
        if (cantidad <= 0) {
            throw new StockInvalidoException("La cantidad debe ser mayor a cero");
        }
        if (cantidad > producto.getStock()) {
            throw new StockInvalidoException("Stock insuficiente para el producto " + producto.getNombre() + " (disponible: " + producto.getStock() + ")");
        }
        Double subtotal = cantidad * precio;
        DetallePedido detalle = new DetallePedido(cantidad, subtotal, producto);
        detalle.setId((long) ++contadorDetalles); // id propio dentro del pedido
        detalles.add(detalle);
    }

    /*
     * Busca el detalle asociado a un producto dado (o null si no existe).
     */
    public DetallePedido findeDetallePedidoByProducto(Producto producto) {
        for (DetallePedido detalle : detalles) {
            if (detalle.getProducto() != null && detalle.getProducto().equals(producto)) {
                return detalle;
            }
        }
        return null;
    }

    /*
     * Elimina de la lista el detalle asociado a un producto dado, si existe.
     */
    public void deleteDetallePedidoByProducto(Producto producto) {
        DetallePedido detalle = findeDetallePedidoByProducto(producto);
        if (detalle != null) {
            detalles.remove(detalle);
        }
    }

    /*
     * Implementacion de Calculable: recorre los detalles, suma sus subtotales
     * y setea el campo total del propio Pedido.
     */
    @Override
    public void calcularTotal() {
        double suma = 0.0;
        for (DetallePedido detalle : detalles) {
            suma += detalle.getSubtotal();
        }
        this.total = suma;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }


    @Override
    public String toString() {
        return "Pedido(id=" + id + ", fecha=" + fecha + ", usuario=" + (usuario != null ? usuario.getNombre() : "sin usuario") + ", estado=" + estado + ", formaPago=" + formaPago + ", total=" + total + ", detalles=" + detalles.size() + ")";
    }
}
