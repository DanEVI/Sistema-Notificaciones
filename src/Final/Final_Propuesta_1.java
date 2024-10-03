package Final;

import java.util.HashMap;
import java.util.Map;

public class Final_Propuesta_1 {
    public static void main(String[] args) {
        SistemaDeNotificaciones sistema = new SistemaDeNotificaciones();
        Pedido pedido1 = new Pedido("001", "email@cliente.com", "Producto A", 2);
        pedido1.setEstado(2);  // Estado cambiado a "Pedido en Preparación"
        sistema.agregarPedido(pedido1);
    }
}

class Pedido {
    private String idPedido;
    private String emailCliente;
    private String producto;
    private int cantidad;
    private String estado;

    public Pedido(String idPedido, String emailCliente, String producto, int cantidad) {
        this.idPedido = idPedido;
        this.emailCliente = emailCliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.estado = "Registrado";
    }

    public String getIdPedido() {
        return idPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(int codigoEstado) {
        switch (codigoEstado) {
            case 1:
                this.estado = "Pedido Confirmado";
                break;
            case 2:
                this.estado = "Pedido en Preparación";
                break;
            case 3:
                this.estado = "Pedido en Ruta";
                break;
            case 4:
                this.estado = "Pedido Entregado";
                break;
            default:
                this.estado = "Código de estado no válido";
                break;
        }
    }
}

class SistemaDeNotificaciones {
    private Map<String, Pedido> pedidos;

    public SistemaDeNotificaciones() {
        pedidos = new HashMap<>();
    }

    public void agregarPedido(Pedido pedido) {
        pedidos.put(pedido.getIdPedido(), pedido);
        System.out.println("Pedido agregado al sistema: " + pedido.getIdPedido() + " con estado: " + pedido.getEstado());
    }
}
