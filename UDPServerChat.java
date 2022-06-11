import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;


public class UdpServer {

 public static void main(String[] args) {
  byte[] receiveBytes = new byte[1024];
  final int SERVER_PORT = 8080;
  final String TERMINIO = "-1";
  Scanner sc = new Scanner(System.in);
  System.out.println("Iniciando chat...");
  System.out.println("digite seu nickname: ");
  String nickname = sc.nextLine();
  System.out.println("Bem vindo "+nickname+", vamos iniciar o chat.\n"+"Observação: para finalizar o chat digite -1.");
  System.out.println("Aguardando mensagem...");
  
  try (DatagramSocket ds = new DatagramSocket(SERVER_PORT); Scanner sz = new Scanner(System.in);) {
    
   while (!ds.isClosed()) {
    DatagramPacket dp = new DatagramPacket(receiveBytes, receiveBytes.length);
    ds.receive(dp);
    String dataString = new String(dp.getData(), "UTF-8");
    System.out.println("Mensagem recebida: " + dataString);
    System.out.println("Digite sua mensagem: ");
    String input = sz.nextLine();
    System.out.println("Aguardando mensagem...");
    if (input.trim().equalsIgnoreCase(TERMINIO)) {
      System.out.println("Encerrando chat!");
      break;
     }
    DatagramPacket sendPacket = new DatagramPacket(input.getBytes(), input.getBytes().length,
      dp.getAddress(), dp.getPort());
    ds.send(sendPacket);
    dataString=null;
   }
   ds.close();
  } catch (Exception e) {
   e.printStackTrace();
  }
 }
}