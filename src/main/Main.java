package main;

import cifras.AES;
import cifras.Vernam;
import cifras.Vigenere;

import java.util.Scanner;

public class Main {
    static Scanner in = new Scanner(System.in);
    static Processo P1 = new Processo(1, "P1");
    static Processo P2 = new Processo(2,"P2");
    static Processo P3 = new Processo(3, "P3");

    public static void main(String[] args) throws Exception {

        demo();

    }

    public static void demo() throws Exception {
        //Escolha da criptografia
        System.out.println("Escolha o tipo de criptografia: ");
        System.out.println("1: Cifra de Vigenere");
        System.out.println("2: Cifra de Vernan");
        System.out.println("3: Cifra AES");
        System.out.print("Digite: ");
        int tipo = in.nextInt();
        clearBuffer(in);

        if(tipo == 1) {

            Processo P_enviar = escolherProcesso("Escolha o processo 1, 2 ou 3 para enviar mensagem: ");
            linha();
            linha(P_enviar);
            int escolha = escolherEnviarMsg(true);

            if(escolha == 1 || escolha == 2 || escolha == 3){
                Processo P_receber = escolherProcesso(escolha);
                unicast(P_enviar, P_receber, 1);

            }else{
                broadcast(P_enviar, 1);
            }


        }else if(tipo == 2){

            Processo P_enviar = escolherProcesso("Escolha o processo 1, 2 ou 3 para enviar mensagem: ");
            linha();
            linha(P_enviar);
            int escolha = escolherEnviarMsg(true);

            if(escolha == 1 || escolha == 2 || escolha == 3){
                Processo P_receber = escolherProcesso(escolha);
                unicast(P_enviar, P_receber, 2);

            }else{
                broadcast(P_enviar, 2);
            }

        }else if(tipo == 3){

            Processo P_enviar = escolherProcesso("Escolha o processo 1, 2 ou 3 para enviar mensagem: ");
            linha();
            linha(P_enviar);
            int escolha = escolherEnviarMsg(false);

            if(escolha == 1 || escolha == 2 || escolha == 3){
                Processo P_receber = escolherProcesso(escolha);
                unicast(P_enviar, P_receber, 3);

            }else{
                broadcast(P_enviar, 3);
            }

        }else{

        }
    }

    public static int escolherEnviarMsg(boolean bool){
        if(bool) {
            System.out.println("Escolha o Processo para receber mensagem: ");
            System.out.println("1: P1");
            System.out.println("2: P2");
            System.out.println("3: P3");
            System.out.println("4: Todos");
            System.out.print("Digite: ");

            int choice;
            choice = in.nextInt();
            clearBuffer(in);

            return choice;
        }else{
            System.out.println("Escolha o Processo para receber mensagem: ");
            System.out.println("1: P1");
            System.out.println("2: P2");
            System.out.println("3: P3");
            System.out.print("Digite: ");

            int choice;
            choice = in.nextInt();
            clearBuffer(in);

            return choice;
        }
    }
    public static Processo escolherProcesso(String texto){

        System.out.print(texto);
        int escolha;
        escolha = in.nextInt();
        clearBuffer(in);
        if(escolha == 1){
            return P1;
        }else if(escolha == 2){
            return P2;
        }else{
            return P3;
        }

    }

    public static Processo escolherProcesso(int choice){

        if(choice == 1){
            return P1;
        }else if(choice == 2){
            return P2;
        }else{
            return P3;
        }

    }

    public static void unicast(Processo p_Enviar, Processo p_Receber, int tipoCifra) throws Exception {



        if(tipoCifra == 1) {
            System.out.print("Digite a mensagem: ");
            String mensagem = in.nextLine();
            System.out.print("Digite a chave: ");
            String chave = in.nextLine();

            Vigenere cifra = new Vigenere();
            String msgCifrada = cifra.codificar(mensagem, chave);

            p_Receber.mensagem = msgCifrada;

            System.out.println("Mensagem encripitada e enviada para "+ p_Receber.nome +" com sucesso!");
            linha();
            linha(p_Receber);
            System.out.println("Você recebeu mensagem de " + p_Enviar.nome);
            System.out.println(p_Enviar.nome + ": " + p_Receber.mensagem);

            System.out.print("Digite a chave para decifrar: ");
            String c;


            do{
                c = in.nextLine();
                if(c.equals(chave)){
                    break;
                }else{
                    System.out.print("Chave incorreta, digite novamente: ");
                }
            }while(!(c.equals(chave)));

            String msgDecifrada = cifra.decodificar(p_Receber.mensagem, c);
            System.out.println("Mensagem decifrada com sucesso");
            System.out.println(p_Enviar.nome + ": " + msgDecifrada);
            linha();
            demo();


        }else if(tipoCifra == 2) {
            System.out.print("Digite a mensagem: ");
            String mensagem = in.nextLine().toUpperCase();

            Vernam cifra = new Vernam();
            String chave = cifra.generateKey(mensagem.length());
            System.out.println("Chave da cifra de Vernam de tamanho " + mensagem.length() + " gerada: " + chave);
            String msgCifrada = cifra.codificar(mensagem, chave);

            p_Receber.mensagem = msgCifrada;

            System.out.println("Mensagem encripitada e enviada para "+ p_Receber.nome +" com sucesso!");
            linha();
            linha(p_Receber);
            System.out.println("Você recebeu mensagem de " + p_Enviar.nome);
            System.out.println(p_Enviar.nome + ": " + p_Receber.mensagem);

            System.out.print("Digite a chave para decifrar: ");
            String c;

            do{
                c = in.nextLine();
                if(c.equals(chave)){
                    break;
                }else{
                    System.out.print("Chave incorreta, digite novamente: ");
                }
            }while(!(c.equals(chave)));

            String msgDecifrada = cifra.decodificar(p_Receber.mensagem, c);
            System.out.println("Mensagem decifrada com sucesso");
            System.out.println(p_Enviar.nome + ": " + msgDecifrada);
            linha();
            demo();


        }else{
            AES cifra = new AES();
            System.out.print("Digite a mensagem: ");
            String mensagem = in.nextLine();
            byte[] inputData = mensagem.getBytes();

            //Leitura da chave
            System.out.print("Digite a chave: ");
            String chave = in.nextLine();
            String chaveAjustada = cifra.ajustarChave(chave, cifra.key_Tamanho.length());
            cifra.KEY_BYTES = chaveAjustada.getBytes();

            // Criptografar
            byte[] encryptedData = cifra.codificar(inputData);
            String msgCifrada = new String(encryptedData);

            p_Receber.mensagem = msgCifrada;

            System.out.println("Mensagem encripitada e enviada para "+ p_Receber.nome +" com sucesso!");
            linha();
            linha(p_Receber);
            System.out.println("Você recebeu mensagem de " + p_Enviar.nome);
            System.out.println(p_Enviar.nome + ": " + p_Receber.mensagem);

            System.out.print("Digite a chave para decifrar: ");
            String c;

            do{
                c = in.nextLine();
                if(c.equals(chave)){
                    break;
                }else{
                    System.out.print("Chave incorreta, digite novamente: ");
                }
            }while(!(c.equals(chave)));

            // Descriptografar
            byte[] msgDecifrada = cifra.decodificar(encryptedData);
            System.out.println("Mensagem decifrada com sucesso");
            System.out.println(p_Enviar.nome + ": " + new String(msgDecifrada));

            linha();
            demo();
        }

    }

    public static void broadcast(Processo p_Enviar, int tipoCifra) throws Exception {
        if(tipoCifra == 1 ) {
            if (p_Enviar.id == 1) {
                Processo p2 = P2;
                Processo p3 = P3;
                System.out.println("***Broadcast com Cifra de Vigenere***");
                System.out.print("Digite a mensagem: ");
                String mensagem = in.nextLine();
                System.out.print("Digite a chave: ");
                String chave = in.nextLine();

                Vigenere cifra = new Vigenere();
                String msgCifrada = cifra.codificar(mensagem, chave);

                p2.mensagem = msgCifrada;
                p3.mensagem = msgCifrada;

                System.out.println("Mensagem encripitada e enviada para "+ p2.nome + " e " + p3.nome +" com sucesso!");

                //P2---------------------------------------------------------------------------------
                linha();
                linha(p2);
                System.out.println("Você recebeu mensagem de " + p_Enviar.nome);
                System.out.println(p_Enviar.nome + ": " + p2.mensagem);

                System.out.print("Digite a chave para decifrar: ");
                String c;


                do{
                    c = in.nextLine();
                    if(c.equals(chave)){
                        break;
                    }else{
                        System.out.print("Chave incorreta, digite novamente: ");
                    }
                }while(!(c.equals(chave)));

                String msgDecifrada = cifra.decodificar(p2.mensagem, c);
                System.out.println("Mensagem decifrada com sucesso");
                System.out.println(p_Enviar.nome + ": " + msgDecifrada);
                linha();

                //P3 ----------------------------------------------------------------------------------
                linha();
                linha(p3);
                System.out.println("Você recebeu mensagem de " + p_Enviar.nome);
                System.out.println(p_Enviar.nome + ": " + p3.mensagem);

                System.out.print("Digite a chave para decifrar: ");

                do{
                    c = in.nextLine();
                    if(c.equals(chave)){
                        break;
                    }else{
                        System.out.print("Chave incorreta, digite novamente: ");
                    }
                }while(!(c.equals(chave)));

                msgDecifrada = cifra.decodificar(p3.mensagem, c);
                System.out.println("Mensagem decifrada com sucesso");
                System.out.println(p_Enviar.nome + ": " + msgDecifrada);
                linha();
                demo();

            } else if (p_Enviar.id == 2) {
                Processo p1 = P1;
                Processo p3 = P3;
                System.out.println("***Broadcast com Cifra de Vigenere***");
                System.out.print("Digite a mensagem: ");
                String mensagem = in.nextLine();
                System.out.print("Digite a chave: ");
                String chave = in.nextLine();

                Vigenere cifra = new Vigenere();
                String msgCifrada = cifra.codificar(mensagem, chave);

                p1.mensagem = msgCifrada;
                p3.mensagem = msgCifrada;

                System.out.println("Mensagem encripitada e enviada para "+ p1.nome + " e " + p3.nome +" com sucesso!");

                //P1---------------------------------------------------------------------------------
                linha();
                linha(p1);
                System.out.println("Você recebeu mensagem de " + p_Enviar.nome);
                System.out.println(p_Enviar.nome + ": " + p1.mensagem);

                System.out.print("Digite a chave para decifrar: ");
                String c;


                do{
                    c = in.nextLine();
                    if(c.equals(chave)){
                        break;
                    }else{
                        System.out.print("Chave incorreta, digite novamente: ");
                    }
                }while(!(c.equals(chave)));

                String msgDecifrada = cifra.decodificar(p1.mensagem, c);
                System.out.println("Mensagem decifrada com sucesso");
                System.out.println(p_Enviar.nome + ": " + msgDecifrada);
                linha();

                //P3 ----------------------------------------------------------------------------------
                linha();
                linha(p3);
                System.out.println("Você recebeu mensagem de " + p_Enviar.nome);
                System.out.println(p_Enviar.nome + ": " + p3.mensagem);

                System.out.print("Digite a chave para decifrar: ");

                do{
                    c = in.nextLine();
                    if(c.equals(chave)){
                        break;
                    }else{
                        System.out.print("Chave incorreta, digite novamente: ");
                    }
                }while(!(c.equals(chave)));

                msgDecifrada = cifra.decodificar(p3.mensagem, c);
                System.out.println("Mensagem decifrada com sucesso");
                System.out.println(p_Enviar.nome + ": " + msgDecifrada);
                linha();
                demo();
            } else {
                Processo p1 = P1;
                Processo p2 = P2;
                System.out.println("***Broadcast com Cifra de Vigenere***");
                System.out.print("Digite a mensagem: ");
                String mensagem = in.nextLine();
                System.out.print("Digite a chave: ");
                String chave = in.nextLine();

                Vigenere cifra = new Vigenere();
                String msgCifrada = cifra.codificar(mensagem, chave);

                p1.mensagem = msgCifrada;
                p2.mensagem = msgCifrada;

                System.out.println("Mensagem encripitada e enviada para "+ p1.nome + " e " + p2.nome +" com sucesso!");

                //P1---------------------------------------------------------------------------------
                linha();
                linha(p1);
                System.out.println("Você recebeu mensagem de " + p_Enviar.nome);
                System.out.println(p_Enviar.nome + ": " + p1.mensagem);

                System.out.print("Digite a chave para decifrar: ");
                String c;


                do{
                    c = in.nextLine();
                    if(c.equals(chave)){
                        break;
                    }else{
                        System.out.print("Chave incorreta, digite novamente: ");
                    }
                }while(!(c.equals(chave)));

                String msgDecifrada = cifra.decodificar(p1.mensagem, c);
                System.out.println("Mensagem decifrada com sucesso");
                System.out.println(p_Enviar.nome + ": " + msgDecifrada);
                linha();

                //P3 ----------------------------------------------------------------------------------
                linha();
                linha(p2);
                System.out.println("Você recebeu mensagem de " + p_Enviar.nome);
                System.out.println(p_Enviar.nome + ": " + p2.mensagem);

                System.out.print("Digite a chave para decifrar: ");

                do{
                    c = in.nextLine();
                    if(c.equals(chave)){
                        break;
                    }else{
                        System.out.print("Chave incorreta, digite novamente: ");
                    }
                }while(!(c.equals(chave)));

                msgDecifrada = cifra.decodificar(p2.mensagem, c);
                System.out.println("Mensagem decifrada com sucesso");
                System.out.println(p_Enviar.nome + ": " + msgDecifrada);
                linha();
                demo();
            }
        }else{
            if(p_Enviar.id == 1){
                Processo p2 = P2;
                Processo p3 = P3;
                System.out.println("***Broadcast com cifra de Vernan***");
                System.out.print("Digite a mensagem: ");
                String mensagem = in.nextLine().toUpperCase();

                Vernam cifra = new Vernam();
                String chave = cifra.generateKey(mensagem.length());
                System.out.println("Chave da cifra de Vernam de tamanho " + mensagem.length() + " gerada: " + chave);
                String msgCifrada = cifra.codificar(mensagem, chave);

                p2.mensagem = msgCifrada;
                p3.mensagem = msgCifrada;

                System.out.println("Mensagem encripitada e enviada para "+ p2.nome + " e " + p3.nome +" com sucesso!");

                //P2----------------------------------------------------------
                linha();
                linha(p2);
                System.out.println("Você recebeu mensagem de " + p_Enviar.nome);
                System.out.println(p_Enviar.nome + ": " + p2.mensagem);

                System.out.print("Digite a chave para decifrar: ");
                String c;

                do{
                    c = in.nextLine();
                    if(c.equals(chave)){
                        break;
                    }else{
                        System.out.print("Chave incorreta, digite novamente: ");
                    }
                }while(!(c.equals(chave)));

                String msgDecifrada = cifra.decodificar(p2.mensagem, c);
                System.out.println("Mensagem decifrada com sucesso");
                System.out.println(p_Enviar.nome + ": " + msgDecifrada);

                //P3 ---------------------------------------------------------
                linha();
                linha(p3);
                System.out.println("Você recebeu mensagem de " + p_Enviar.nome);
                System.out.println(p_Enviar.nome + ": " + p3.mensagem);

                System.out.print("Digite a chave para decifrar: ");

                do{
                    c = in.nextLine();
                    if(c.equals(chave)){
                        break;
                    }else{
                        System.out.print("Chave incorreta, digite novamente: ");
                    }
                }while(!(c.equals(chave)));

                msgDecifrada = cifra.decodificar(p3.mensagem, c);
                System.out.println("Mensagem decifrada com sucesso");
                System.out.println(p_Enviar.nome + ": " + msgDecifrada);

                linha();
                demo();

            } else if (p_Enviar.id == 2) {
                Processo p1 = P1;
                Processo p3 = P3;
                System.out.println("***Broadcast com cifra de Vernan***");
                System.out.print("Digite a mensagem: ");
                String mensagem = in.nextLine().toUpperCase();

                Vernam cifra = new Vernam();
                String chave = cifra.generateKey(mensagem.length());
                System.out.println("Chave da cifra de Vernam de tamanho " + mensagem.length() + " gerada: " + chave);
                String msgCifrada = cifra.codificar(mensagem, chave);

                p1.mensagem = msgCifrada;
                p3.mensagem = msgCifrada;

                System.out.println("Mensagem encripitada e enviada para "+ p1.nome + " e " + p3.nome +" com sucesso!");

                //P2----------------------------------------------------------
                linha();
                linha(p1);
                System.out.println("Você recebeu mensagem de " + p_Enviar.nome);
                System.out.println(p_Enviar.nome + ": " + p1.mensagem);

                System.out.print("Digite a chave para decifrar: ");
                String c;

                do{
                    c = in.nextLine();
                    if(c.equals(chave)){
                        break;
                    }else{
                        System.out.print("Chave incorreta, digite novamente: ");
                    }
                }while(!(c.equals(chave)));

                String msgDecifrada = cifra.decodificar(p1.mensagem, c);
                System.out.println("Mensagem decifrada com sucesso");
                System.out.println(p_Enviar.nome + ": " + msgDecifrada);

                //P3 ---------------------------------------------------------
                linha();
                linha(p3);
                System.out.println("Você recebeu mensagem de " + p_Enviar.nome);
                System.out.println(p_Enviar.nome + ": " + p3.mensagem);

                System.out.print("Digite a chave para decifrar: ");

                do{
                    c = in.nextLine();
                    if(c.equals(chave)){
                        break;
                    }else{
                        System.out.print("Chave incorreta, digite novamente: ");
                    }
                }while(!(c.equals(chave)));

                msgDecifrada = cifra.decodificar(p3.mensagem, c);
                System.out.println("Mensagem decifrada com sucesso");
                System.out.println(p_Enviar.nome + ": " + msgDecifrada);

                linha();
                demo();
            }else{
                Processo p1 = P1;
                Processo p2 = P2;
                System.out.println("***Broadcast com cifra de Vernan***");
                System.out.print("Digite a mensagem: ");
                String mensagem = in.nextLine().toUpperCase();

                Vernam cifra = new Vernam();
                String chave = cifra.generateKey(mensagem.length());
                System.out.println("Chave da cifra de Vernam de tamanho " + mensagem.length() + " gerada: " + chave);
                String msgCifrada = cifra.codificar(mensagem, chave);

                p1.mensagem = msgCifrada;
                p2.mensagem = msgCifrada;

                System.out.println("Mensagem encripitada e enviada para "+ p1.nome + " e " + p2.nome +" com sucesso!");

                //P2----------------------------------------------------------
                linha();
                linha(p1);
                System.out.println("Você recebeu mensagem de " + p_Enviar.nome);
                System.out.println(p_Enviar.nome + ": " + p1.mensagem);

                System.out.print("Digite a chave para decifrar: ");
                String c;

                do{
                    c = in.nextLine();
                    if(c.equals(chave)){
                        break;
                    }else{
                        System.out.print("Chave incorreta, digite novamente: ");
                    }
                }while(!(c.equals(chave)));

                String msgDecifrada = cifra.decodificar(p1.mensagem, c);
                System.out.println("Mensagem decifrada com sucesso");
                System.out.println(p_Enviar.nome + ": " + msgDecifrada);

                //P3 ---------------------------------------------------------
                linha();
                linha(p2);
                System.out.println("Você recebeu mensagem de " + p_Enviar.nome);
                System.out.println(p_Enviar.nome + ": " + p2.mensagem);

                System.out.print("Digite a chave para decifrar: ");

                do{
                    c = in.nextLine();
                    if(c.equals(chave)){
                        break;
                    }else{
                        System.out.print("Chave incorreta, digite novamente: ");
                    }
                }while(!(c.equals(chave)));

                msgDecifrada = cifra.decodificar(p2.mensagem, c);
                System.out.println("Mensagem decifrada com sucesso");
                System.out.println(p_Enviar.nome + ": " + msgDecifrada);

                linha();
                demo();
            }
        }
    }

    public static void linha(){
        System.out.println();
        System.out.println("==========================================================");

    }

    public static void linha(Processo p){
        System.out.println("========================= " + p.nome + " =============================");
    }

    private static void clearBuffer(Scanner scanner) {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }
}