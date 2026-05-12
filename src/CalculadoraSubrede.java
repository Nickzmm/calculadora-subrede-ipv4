import java.util.Scanner;

public class CalculadoraSubrede {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o IP (ex: 192.168.1.10): ");
        String ipInput = scanner.nextLine();

        System.out.print("Digite o prefixo da rede (ex: 24): ");
        int prefixo = scanner.nextInt();

        try {
            long ipDecimal = ipParaDecimal(ipInput);
            long mascaraDecimal = (0xFFFFFFFFL << (32 - prefixo)) & 0xFFFFFFFFL;
            long redeDecimal = ipDecimal & mascaraDecimal;
            long broadcastDecimal = redeDecimal | (~mascaraDecimal & 0xFFFFFFFFL);

            System.out.println("\n--- Resultados ---");
            System.out.println("Máscara de Sub-rede: " + decimalParaIp(mascaraDecimal));
            System.out.println("IP da Rede: " + decimalParaIp(redeDecimal));
            System.out.println("IP de Broadcast: " + decimalParaIp(broadcastDecimal));
            System.out.println("Primeiro IP utilizável: " + decimalParaIp(redeDecimal + 1));
            System.out.println("Último IP utilizável: " + decimalParaIp(broadcastDecimal - 1));
            System.out.println("Total de Hosts: " + ((1L << (32 - prefixo)) - 2));

        } catch (Exception e) {
            System.out.println("Erro ao processar o IP. Verifique o formato.");
        }
        scanner.close();
    }

    private static long ipParaDecimal(String ip) {
        String[] octetos = ip.split("\\.");
        long resultado = 0;
        for (int i = 0; i < 4; i++) {
            resultado |= (Long.parseLong(octetos[i]) << (24 - (8 * i)));
        }
        return resultado;
    }

    private static String decimalParaIp(long ip) {
        return ((ip >> 24) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                (ip & 0xFF);
    }
}