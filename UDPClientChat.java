import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class UdpClient {

 public static void main(String[] args) {
  byte[] receiveBytes = new byte[1024];
  final String TERMINIO = "-1";
  final int SERVER_PORT = 8080;
  Scanner sa = new Scanner(System.in);
  System.out.println("Iniciando chat...");
  System.out.println("digite seu nickname: ");
  String nickname = sa.nextLine();
  System.out.println("Bem vindo "+nickname+", vamos iniciar o chat.\n"+"Observação: para finalizar o chat digite -1.");
  try (Scanner sc = new Scanner(System.in); DatagramSocket ds = new DatagramSocket();) {
   // Get server address
   final InetAddress SERVER_ADDRESS = InetAddress.getLocalHost();
   DatagramPacket dataPacket = null;
   while (!ds.isClosed()) {
    System.out.println("Digite sua mensagem: ");
    String input = sc.nextLine();
    // Terminate the client if user says "bye"
    if (input.trim().equalsIgnoreCase(TERMINIO)) {
     System.out.println("Encerrando chat...");
     break;
    }
    System.out.println("Aguardando mensagem...");
    dataPacket = new DatagramPacket(input.getBytes(), input.getBytes().length, SERVER_ADDRESS, SERVER_PORT);
    ds.send(dataPacket);
    // Construct Datagram packet to receive message
    dataPacket = new DatagramPacket(receiveBytes, receiveBytes.length);
    ds.receive(dataPacket);
    System.out.println("Mensagen recebida: " + new String(receiveBytes, "UTF-8"));
   }
   
  } catch (SocketException | UnknownHostException e) {
   e.printStackTrace();
  } catch (IOException e) {
   e.printStackTrace();
  }
 }
}