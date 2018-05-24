import java.io.*;
import java.net.*;
import java.util.*;

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
    Comparator<String> RatingComparator = new Comparator<String>() {

        public int compare(String s1, String s2) {

            Integer score1 = Integer.valueOf(s1.split(": ")[1]);
            Integer score2 = Integer.valueOf(s2.split(": ")[1]);
            return score2 - score1;
        }
    };


    @Override
    public void run() {
        try {
            FileWriter fw = new FileWriter("results.txt", true);
            PrintWriter bw = new PrintWriter(fw);
            FileReader fr = new FileReader("results.txt");
            BufferedReader br = new BufferedReader(fr);



            BufferedReader bufferIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStreamWriter outStream = new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter pw = new BufferedWriter(outStream);
            String login = bufferIn.readLine();
            System.out.println("DFKJSHD");
            //String score = bufferIn.readLine();
            //String buff;
            //ArrayList<String> fileBuff = new ArrayList<>();
            //PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            //while ((buff = br.readLine()) != null && !buff.equals("")) {
            //    fileBuff.add(buff);
            //}
            //String txt = fileBuff.toString();
            //txt = txt.substring(1, txt.length() - 1);
            //System.out.println("login  = " + login);
            bw.print( login + "\n");
            bw.flush();
            ArrayList<String> lines = new ArrayList<>();
            String line;
            /*try{
                bw.close();
            } catch (IOException e){
                System.err.println("Error while closing stream(s)");
            }*/
            bw.close();
            while ((line = br.readLine()) != null && !line.equals("")) {
                lines.add(line);
            }
            //System.out.println(lines.toString().substring(1, lines.toString().length()-1));
            Collections.sort(lines, RatingComparator);
            //System.out.println(lines.toString());
            //System.out.println("going to write " + lines.size());

            //pw.write(lines.size());
            System.out.println("written " + lines.size());
            for (int i = 0; i < 3  && i < lines.size() ; i++) {
                System.out.println("going to send rating to player");
                pw.write(lines.get(i) + "\n");
            }

            //fw.flush();
            //socket.getOutputStream().write(get);
            try {
                    fw.close();
                    //bw.close();
                    pw.close();
                    bufferIn.close();

            } catch (IOException e) {
                System.err.println("Error while closing stream(s)");
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
