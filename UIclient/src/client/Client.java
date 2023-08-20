package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
@SuppressWarnings("resource")

/**
 * Classe che implementa il client per il programma di clustering.
 */
public class Client {

    /**
     * Stream di output verso il server.
     */
    private ObjectOutputStream out;
    /**
     * Stream di input dal server.
     */
    private ObjectInputStream in ;

    /**
     * Costruttore della classe che inizializza gli stream di input e output e si connette al server.
     * @throws IOException Eccezione lanciata in caso di errore di connessione.
     */
    public Client() throws IOException {
    	InetAddress addr = InetAddress.getByName("127.0.0.1");
		Socket socket = new Socket(addr, 8083);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    /*
     * CASO 0
     * crea Data in base alla tabella passata in input.
     */
    public void storeTableFromDb(String tabName) throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(0);
        out.writeObject(tabName);
        String result = (String) in.readObject();
        if (!result.equals("OK"))
            throw new ServerException(result);
    }

    /*
     * CASO 1
     * Fa i cluster dal DB.
     */
    public String learningFromDbTable(int k) throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(1);
        out.writeObject(k);
        String result = (String) in.readObject();
        if (result.equals("OK")) {
            String it = "\nNumero iterazioni: " + in.readObject() + "\n";
            return it + (String) in.readObject();
        } else throw new ServerException(result);
    }

    /*
     * CASO 2
     * salva i cluster su file.
     */
    public void storeClusterInFile() throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(2);

        String result = (String) in.readObject();
        if (!result.equals("OK"))
            throw new ServerException(result);
    }

    /*
     * CASO 3
     */
    public String learningFromFile(String tabName, int k) throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(3);
        out.writeObject(tabName);
		out.writeObject(k);
        String result = (String) in.readObject();
        if (result.equals("OK"))
            return (String) in.readObject();
        else throw new ServerException(result);
    }
}