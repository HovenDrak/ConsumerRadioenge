package br.com.tcp.servidor.app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Processamento{

    public String decode(String evento) {

        String recebe = "(?<FORMATO>\\w{2})"
                + "(?<CONTA>\\w{4})"
                + "(?<QUALI>\\w{1})"
                + "(?<EVENTO>\\w{3})"
                + "(?<PARTICAO>\\w{2})"
                + "(?<ZONA>\\w{3})";


        String[] listaRegex = {recebe};

        for (String lista : listaRegex) {
            Pattern pattern = Pattern.compile(lista, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(evento);

            while (matcher.find()) {
                String encontrou = matcher.group(0);
                if (encontrou.matches(recebe)) {
                    String formato = matcher.group(1);
                    String conta = matcher.group(2);
                    String qualificador = matcher.group(3);
                    String event = matcher.group(4);
                    String particao = matcher.group(5);
                    String zona = matcher.group(6);

                    return "Formato >> " + formato + "\nConta >> " + conta + "\nEvento >> "
                            + qualificador + " " +  event + "\nPartição >> " + particao + "\nZona >> " + zona;

                }
            }
        }
        return evento;
    }
}