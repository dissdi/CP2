package Week11;

import java.io.*;
import java.util.*;

class Factory {
    Queue<Character> list = new LinkedList<Character>();
    Random rand = new Random();
    int seed;
    int capacity;
    StringBuilder producedLine = new StringBuilder();
    StringBuilder consumedLine = new StringBuilder();
    boolean flag = true;

    Factory(int seed, int capacity){
        this.seed = seed;
        this.capacity = capacity;
        rand.setSeed(seed);
    }

    public synchronized void produce() throws InterruptedException {
        while(list.size()==capacity) wait();
        while(true){
            char c = (char)('A'+rand.nextInt(26));
            list.add(c);

            if(c=='A' || c=='E' || c=='I' || c=='O' || c=='U') {
                producedLine.append(c);
            }

            if(c=='Z' || list.size()==capacity) {
                notify();
                if(c=='Z') return;
            }
        }
    }

    public synchronized void consume() throws InterruptedException {
        while(list.size()==0) wait();
        while(true){
            char c = list.poll();
            consumedLine.append(c);
            if(c=='Z') {
                return;
            }
            if(list.isEmpty()) {
                notify();
            }
        }
    }



}
public class Synchronize {
    static int step, seed, capacity;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        step = Integer.parseInt(st.nextToken());
        seed = Integer.parseInt(st.nextToken());
        capacity = Integer.parseInt(st.nextToken());
        Random rand = new Random();
        rand.setSeed(seed);

        if(step==1){
            System.out.println("Step-1:");
            System.out.printf(" Input Stream:%d %d %d%n", step, seed, capacity);
            System.out.printf(" Input Parameters:step(%d),seed(%d),capacity(%d)%n", step, seed, capacity);
        }

        if(step==2){
            System.out.println("Step-2:");
            System.out.print(" List:");
            while(true){
                char c = (char)('A' + rand.nextInt(26));
                System.out.print(c);
                if(c=='Z') break;
            }
        }

        if(step==3){
            System.out.println("Step-3:");
            StringBuilder producedLine = new StringBuilder();

            int count = 1;
            while(true){
                char c = (char)('A'+rand.nextInt(26));
                if(c=='A' || c=='E' || c=='I' || c=='O' || c=='U') {
                    producedLine.append(c);
                }
                if(c=='Z' || count++==capacity) {
                    break;
                }
            }
            System.out.printf(" Produce:%s", String.valueOf(producedLine));
        }

        if(step==4){
            System.out.println("Step-4:");
            Factory factory = new Factory(seed, capacity);
            Thread consumer = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        factory.consume();
                    } catch (InterruptedException e) { }
                }
            });
            Thread producer = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        factory.produce();
                    } catch (InterruptedException e) { }
                }
            });
            consumer.start();
            producer.start();

            try{
                consumer.join();
                producer.join();
            } catch (Exception e) {}
            System.out.printf(" Produce:%s%n", factory.producedLine);
            System.out.printf(" Consume:%s%n", factory.consumedLine);
        }

    }
}
