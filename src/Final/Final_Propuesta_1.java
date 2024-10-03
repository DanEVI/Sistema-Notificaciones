package Final;

public class Final_Propuesta_1 {
    public static void main(String[] args) {
        Pedido pedido1 = new Pedido("001", "email@cliente.com", "Producto A", 2);
        System.out.println("Pedido creado: " + pedido1.getIdPedido());
    }
}

class Pedido {
    private String idPedido;
    private String emailCliente;
    private String producto;
    private int cantidad;

    public Pedido(String idPedido, String emailCliente, String producto, int cantidad) {
        this.idPedido = idPedido;
        this.emailCliente = emailCliente;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public String getIdPedido() {
        return idPedido;
    }
}