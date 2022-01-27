package br.com.tcp.servidor.app;


public class ExemploLista {

    public static void main(String[] args) {

        String item1 = "Primeiro item";
        String item2 = "Segundo item";
        String[] minhaLista = {item1, item2};

        //Percorre a minhaLista e imprime os itens dela
        for (String atual : minhaLista) {
            System.out.println(atual);
            //algo=faz(mensam);
        }

        for (int i = 0; i < minhaLista.length; i++) {
            String atual = minhaLista[i];
            System.out.println(atual);
        }


        for (int i = 0; i < 1000000; i++) {
            System.out.println(i);
            if(i>=10000){
                break;
            }
        }



    }
}

