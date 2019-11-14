/*
 * AICare - Artificial Intelligence Care
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.uteis;

/**
 * Classe responsável por tratar o MAC Address
 *
 * @author Paulo Collares
 */
public class MAC {

    private final String mac;

    public MAC(String mac) {
        this.mac = mac;
    }

    public boolean isValido() {
        if (mac == null) {
            return false;
        }
        
        if (mac.matches("([a-fA-F0-9]){12}")) {
            return true;
        }
        
        return false;
    }

    public String getMac() {
        return mac;
    }

    public String toString() {
        String mac_ = mac.toUpperCase();
        StringBuilder sb = new StringBuilder();
        sb.append(mac_.charAt(0))
                .append(mac_.charAt(1))
                .append(":")
                .append(mac_.charAt(2))
                .append(mac_.charAt(3))
                .append(":")
                .append(mac_.charAt(4))
                .append(mac_.charAt(5))
                .append(":")
                .append(mac_.charAt(6))
                .append(mac_.charAt(7))
                .append(":")
                .append(mac_.charAt(8))
                .append(mac_.charAt(9))
                .append(":")
                .append(mac_.charAt(10))
                .append(mac_.charAt(11));

        return sb.toString();
    }

}
