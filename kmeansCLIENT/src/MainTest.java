import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import keyboardinput.Keyboard;

public class MainTest {

    /**
     * @param args
     */
    private ObjectOutputStream out;
    private ObjectInputStream in ;

    public MainTest(String ip, int port) throws IOException {
        InetAddress addr = InetAddress.getByName(ip); //ip
        System.out.println("\nINFO DI CONNESSIONE:");
        System.out.println("addr = " + addr);
        Socket socket = new Socket(addr, port); //Port
        System.out.println(socket);

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream()); // stream con richieste del client
    }

    /*
     * CASO 0
     * crea Data in base alla tabella passata in input.
     */
    private void storeTableFromDb() throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(0);
        System.out.print("Nome tabella: ");
        String tabName = Keyboard.readString();
        out.writeObject(tabName);
        String result = (String) in.readObject();
        if (!result.equals("OK"))
            throw new ServerException(result);
    }

    /*
     * CASO 1
     * Fa i cluster dal DB.
     */
    private String learningFromDbTable() throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(1);
        System.out.print("Numero di cluster: ");
        int k = Keyboard.readInt();
        out.writeObject(k);
        String result = (String) in.readObject();
        if (result.equals("OK")) {
            System.out.println("\nNumero iterazioni: " + in.readObject());
            return (String) in.readObject();
        } else throw new ServerException(result);

    }

    /*
     * CASO 2
     * salva i cluster su file.
     */
    private void storeClusterInFile() throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(2);

        String result = (String) in.readObject();
        if (!result.equals("OK"))
            throw new ServerException(result);

    }

    /*
     * CASO 3
     */
    private String learningFromFile() throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(3);

        System.out.print("Nome tabella: ");
        String tabName = Keyboard.readString();
        out.writeObject(tabName);
        String result = (String) in.readObject();
        if (result.equals("OK"))
            return (String) in.readObject();
        else throw new ServerException(result);
    }

    private void closeConnection() throws IOException{ 
       out.writeObject(4);
    }

    private int menu() {
        int answer;
        System.out.println("Scegli una opzione:");
        do {
            System.out.println("(1) Carica Cluster da File");
            System.out.println("(2) Carica Dati");
            System.out.print("Risposta: ");
            answer = Keyboard.readInt();
        }
        while (answer <= 0 || answer > 2);
        return answer;
    }

    public static void main(String[] args) {
        String answer = "y";
        String ip;
        int port;
        if (args.length > 0) {
            ip = args[0];
            port = Integer.parseInt(args[1]);
        } else {
            System.out.print("IP: ");
            ip = Keyboard.readString();
            System.out.print("PORTA: ");
            port = Keyboard.readInt();
        }
        MainTest main = null;
        try {
            main = new MainTest(ip, port);
            System.out.println("+++ Connessione avvenuta +++\n");
        } catch (IOException e) {
            System.out.println(e);
            return;
        }

        do {
            int menuAnswer = main.menu();
            switch (menuAnswer) {
            case 1:
                try {
                    String kmeans = main.learningFromFile();
                    System.out.println(kmeans);
                } catch (SocketException e) {
                    System.out.println(e);
                    return;
                } catch (FileNotFoundException e) {
                    System.out.println(e);
                    return;
                } catch (IOException e) {
                    System.out.println(e);
                    return;
                } catch (ClassNotFoundException e) {
                    System.out.println(e);
                    return;
                } catch (ServerException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 2: // learning from db
                while (true) {
                    try {
                        main.storeTableFromDb();
                        break; //esce fuori dal while
                    } catch (SocketException e) {
                        System.out.println(e);
                        return;
                    } catch (FileNotFoundException e) {
                        System.out.println(e);
                        return;
                    } catch (IOException e) {
                        System.out.println(e);
                        return;
                    } catch (ClassNotFoundException e) {
                        System.out.println(e);
                        return;
                    } catch (ServerException e) {
                        System.out.println(e.getMessage());
                    }
                } //end while [viene fuori dal while con un db (in alternativa il programma termina)

                boolean flag = true;
                do {
                    try {
                        String clusterSet = main.learningFromDbTable();
                        System.out.println(clusterSet);

                        main.storeClusterInFile();
                        System.out.println("File salvato");
                        flag = true;
                    } catch (SocketException e) {
                        System.out.println(e);
                        return;
                    } catch (FileNotFoundException e) {
                        System.out.println(e);
                        return;
                    } catch (ClassNotFoundException e) {
                        System.out.println(e);
                        return;
                    } catch (IOException e) {
                        System.out.println(e);
                        return;
                    } catch (ServerException e) {
                        System.out.println(e.getMessage());
                        flag = false;
                    }
                    if (flag) {
                        System.out.println("Vuoi ripetere l'esecuzione?(y/n)");
                        do {
                            answer = Keyboard.readString();
                        } while (!answer.matches("[y|n]"));
                    }
                }
                while (answer.equals("y") || flag == false);
                break; //fine case 2
            }

            System.out.println("Vuoi scegliere una nuova operazione da menu?(y/n)");
            do {
                answer = Keyboard.readString();
            } while (!answer.matches("[y|n]"));
            if (answer.equals("n")) {
                try {
                    main.closeConnection();
                } catch (IOException e) {
                    System.out.println(e);
                }
                break;
            }
        }
        while (true);
    }
}