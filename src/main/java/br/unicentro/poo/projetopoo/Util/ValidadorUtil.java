package br.unicentro.poo.projetopoo.Util;

public class ValidadorUtil {

    public static boolean isCpfValido(String cpf) {
        if (cpf == null) return false;
        String cpfLimpo = cpf.replaceAll("\\D", ""); // Remove tudo o que não for dígito

        if (cpfLimpo.length() != 11 || cpfLimpo.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += Character.getNumericValue(cpfLimpo.charAt(i)) * (10 - i);
            }
            int primDig = 11 - (soma % 11);
            if (primDig >= 10) primDig = 0;

            if (primDig != Character.getNumericValue(cpfLimpo.charAt(9))) {
                return false;
            }

            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += Character.getNumericValue(cpfLimpo.charAt(i)) * (11 - i);
            }
            int segDig = 11 - (soma % 11);
            if (segDig >= 10) segDig = 0;

            return segDig == Character.getNumericValue(cpfLimpo.charAt(10));

        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isTelefoneValido(String telefone) {
        if (telefone == null) return false;
        String telLimpo = telefone.replaceAll("\\D", "");
        return telLimpo.length() == 10 || telLimpo.length() == 11;
    }
}