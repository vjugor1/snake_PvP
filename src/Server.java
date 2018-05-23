import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {

    public static void main(String[] args) {
        ServerSocket serv = null;
        try {
            serv = new ServerSocket(11111);
            while(true){
                MyThread myThrd = new MyThread(serv.accept());
                myThrd.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (serv != null){
                try {
                    serv.close();
                } catch (IOException e) {
                }
            }
        }

    }
}

class MyThread extends Thread{
    Socket socket;
    MyThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            int get = 0;
            String tmp;
            get = (int)socket.getInputStream().read();
            FileWriter fw = new FileWriter("results.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            FileReader fr = new FileReader("results.txt");
            BufferedReader br = new BufferedReader(fr);
            //String buff;
            //ArrayList<String> fileBuff = new ArrayList<>();
            //PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            //while ((buff = br.readLine()) != null && !buff.equals("")) {
            //    fileBuff.add(buff);
            //}
            //String txt = fileBuff.toString();
            //txt = txt.substring(1, txt.length() - 1);
            bw.write("hey: " + get + "\n");
            //fw.flush();
            //socket.getOutputStream().write(get);
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
                if (br != null){
                    br.close();
                }
                if (bw != null){
                    bw.close();
                }

            } catch (IOException e) {
                System.err.println("Не получилось закрыть buffer или file");
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
