/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest.uteis;

import br.com.aicare.di.api_rest.configuracoes.internacionalizacao.Translator;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Paulo Collares
 */
public class Data {

    private final Date date;

    public Data(Date date) {
        this.date = date;
    }

    public String duracao() {
        return formata(0, false);
    }

    public String formata(long base, boolean diferenca) {

        long timestamp;

        if (diferenca) {
            timestamp = base - date.getTime();
        } else {
            timestamp = date.getTime();
        }

        if (timestamp <= 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
            return sdf.format(date);
        }
        double segundos = timestamp / 1000;
        if (segundos <= 1) {
            return Translator.toLocale("tempo_neste_instante");
        }
        if (segundos < 60) {
            return ((int) segundos) + " " + (segundos >= 2 ? Translator.toLocale("tempo_segundos") : Translator.toLocale("tempo_segundo"));
        }
        double minutos = segundos / 60;
        segundos = segundos % 60;
        if (minutos < 60) {
            return ((int) minutos) + " " + (minutos >= 2 ? Translator.toLocale("tempo_minutos") : Translator.toLocale("tempo_minuto"))
                    + " " + Translator.toLocale("tempo_e")
                    + " " + ((int) segundos) + " " + (segundos >= 2 ? Translator.toLocale("tempo_segundos") : Translator.toLocale("tempo_segundo"));
        }
        double horas = minutos / 60;
        minutos = minutos % 60;
        if (horas < 24) {
            return ((int) horas) + " " + (horas >= 2 ? Translator.toLocale("tempo_horas") : Translator.toLocale("tempo_hora"))
                    + " " + Translator.toLocale("tempo_e")
                    + " " + ((int) minutos) + " " + (minutos >= 2 ? Translator.toLocale("tempo_minutos") : Translator.toLocale("tempo_minuto"));
        }
        double dias = horas / 24;
        if (dias > 30) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
            return sdf.format(date);
        }
        horas = horas % 24;

        return ((int) dias) + " " + (dias >= 2 ? Translator.toLocale("tempo_dias") : Translator.toLocale("tempo_dia"))
                + " " + Translator.toLocale("tempo_e")
                + " " + ((int) horas) + " " + (horas >= 2 ? Translator.toLocale("tempo_horas") : Translator.toLocale("tempo_hora"));
    }

    /**
     * Retorna com base na diferença da data atual
     *
     * @return
     */
    public String tempoAtras() {
        return formata(System.currentTimeMillis(), true);
    }

    @Override
    public String toString() {
        String pattern;
        Date now = new Date();

        if (now.getYear() == date.getYear()) {
            if (now.getDay() == date.getDay() && now.getMonth() == date.getMonth()) {
                pattern = "'" + Translator.toLocale("tempo_hoje") + ",' HH:mm:ss";
            } else {
                pattern = "dd/MM',' HH:mm:ss";
            }
        } else {
            pattern = "dd/MM/YYYY',' HH:mm:ss";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

}
