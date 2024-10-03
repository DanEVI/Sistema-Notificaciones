package Final;

import java.util.HashMap;
import java.util.Map;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class Final_Propuesta_1 {

    public static void main(String[] args) {
        // Crear el sistema de notificaciones
        SistemaDeNotificaciones sistema = new SistemaDeNotificaciones();

        // Simulación de un pedido realizado por un cliente
        Pedido pedido1 = new Pedido("001", "garcia2020christian@gmail.com", "Producto A", 2);

        // Actualizar el estado antes de agregarlo y enviar notificación
        int codigoEstado = 2;  // Cambia este valor a 1, 2, 3 o 4 según el estado deseado
        pedido1.setEstado(codigoEstado);

        // Agregar el pedido al sistema y enviar la notificación con el estado correcto
        sistema.agregarPedido(pedido1);
    }
}

// Clase Pedido que representa un pedido realizado por un cliente
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
        this.estado = "Registrado";  // Estado inicial, no se enviará notificación con este estado
    }

    public String getIdPedido() {
        return idPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(int codigoEstado) {
        // Establecer el estado basado en el ID proporcionado
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

    public String getEmailCliente() {
        return emailCliente;
    }

    public String getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void enviarNotificacion() {
        String remitente = "tecnoretail1@gmail.com";  // Cambia esto por tu correo
        String password = "jacjhcaxioovcbfy";  // Cambia esto por tu contraseña de aplicación

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

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
            msg.setText("El estado actual de su pedido es: " + estado);

            // Enviar el mensaje
            Transport.send(msg);

            System.out.println("Correo enviado exitosamente a " + emailCliente + " con estado: " + estado);
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
        // Enviar la notificación solo cuando el estado ha sido establecido correctamente
        pedido.enviarNotificacion();
    }
}