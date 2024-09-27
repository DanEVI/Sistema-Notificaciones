package Final;

import java.util.HashMap;
import java.util.Map;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

// Clase Pedido que representa un pedido realizado por un cliente
class Pedido {
    private String idPedido;
    private String estado;
    private String emailCliente;
    private String producto;
    private int cantidad;

    public Pedido(String idPedido, String estado, String emailCliente, String producto, int cantidad) {
        this.idPedido = idPedido;
        this.estado = estado;
        this.emailCliente = emailCliente;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public String getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void enviarNotificacion(String mensaje) {
        // Envío real de notificación por correo
        String remitente = "tecnoretail1@gmail.com";  // Cambia esto por tu correo
        String password = "jacjhcaxioovcbfy";         // Cambia esto por tu contraseña (o contraseña de aplicación)

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Sesión de correo
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, password);
            }
        });

        try {
            // Creación del mensaje
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(remitente));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailCliente));
            msg.setSubject("Estado de su pedido: " + idPedido);
            msg.setText(mensaje);

            // Enviar el mensaje
            Transport.send(msg);

            System.out.println("Correo enviado exitosamente a " + emailCliente);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

// Clase SistemaDeNotificaciones que maneja el ciclo de vida del pedido y las notificaciones
class SistemaDeNotificaciones {
    private Map<String, Pedido> pedidos;

    public SistemaDeNotificaciones() {
        pedidos = new HashMap<>();
    }

    // Agregar un nuevo pedido
    public void agregarPedido(Pedido pedido) {
        pedidos.put(pedido.getIdPedido(), pedido);
        pedido.enviarNotificacion("Su pedido ha sido registrado correctamente. Estado: " + pedido.getEstado());
    }

    // Cambiar el estado de un pedido y notificar al cliente
    public void actualizarEstadoPedido(String idPedido, String nuevoEstado) {
        Pedido pedido = pedidos.get(idPedido);
        if (pedido != null) {
            pedido.setEstado(nuevoEstado);
            String mensaje = generarMensajeNotificacion(nuevoEstado, pedido);
            pedido.enviarNotificacion(mensaje);
        } else {
            System.out.println("Pedido con ID " + idPedido + " no encontrado.");
        }
    }

    // Generar mensaje de notificación según el estado del pedido
    private String generarMensajeNotificacion(String estado, Pedido pedido) {
        switch (estado) {
            case "Confirmado":
                return "Su pedido ha sido confirmado. Producto: " + pedido.getProducto() + ", Cantidad: " + pedido.getCantidad();
            case "En proceso":
                return "Su pedido está siendo procesado.";
            case "Enviado":
                return "Su pedido ha sido enviado. Pronto lo recibirá.";
            case "Entregado":
                return "Su pedido ha sido entregado. Gracias por su compra.";
            default:
                return "Actualización de su pedido. Estado actual: " + estado;
        }
    }
}

// Clase principal para ejecutar el programa
public class Final_Propuesta_1 {
    public static void main(String[] args) {
        // Crear el sistema de notificaciones
        SistemaDeNotificaciones sistema = new SistemaDeNotificaciones();

        // Simulación de un pedido realizado por un cliente
        Pedido pedido1 = new Pedido("001", "Confirmado", "Andyc.atencio@gmail.com, villavicenciod139@gmail.com", "Producto A", 2);

        // Agregar el pedido al sistema
        sistema.agregarPedido(pedido1);

        // Actualizar el estado del pedido y enviar notificaciones
        sistema.actualizarEstadoPedido("001", "En proceso");
        sistema.actualizarEstadoPedido("001", "Enviado");
        sistema.actualizarEstadoPedido("001", "Entregado");
    }
}