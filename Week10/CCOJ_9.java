package Week10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;


public class CCOJ_9 {
    static class MyThread extends Thread{
        ArrayList<Integer> list;
        public long gap = 0;
        MyThread(ArrayList<Integer> list){
            this.list = list;
        }
        @Override
        public void run(){
            Collections.sort(list);
            gap = getGap(list);
        }

        long getGap(ArrayList<Integer> list){
            long gap = 0;
            for(int i=0; i<list.size()-1; i++){
                gap += Math.abs(list.get(i+1)-list.get(i));
            }
            return gap;
        }
    }

    static class SingleThread {
        long time;
        long gap = 0;
        ArrayList[] lists;
        MyThread[] threads;

        SingleThread(ArrayList[] lists) {
            this.lists = lists;
            threads = new MyThread[lists.length];
            for (int i = 0; i < lists.length; i++) {
                threads[i] = new MyThread(lists[i]);
            }
        }

        public void start() {
            long t1 = System.currentTimeMillis();
            for (MyThread t : threads) {
                try {
                    t.run();
                    t.join();
                } catch (InterruptedException e) {
                }
                this.gap += t.gap;
            }
            time = System.currentTimeMillis() - t1;
        }
    }

    static class MultiThread {
        int M;
        ArrayList[] list;
        MyThread[] thread;
        long gap = 0;
        long time;
        MultiThread(ArrayList[] list){
            this.M = list.length;
            this.list = list;
            this.thread = new MyThread[M];
            for(int i = 0; i < M; i++){
                thread[i] = new MyThread(list[i]);
            }
        }

        public void start(){
            long t1 = System.currentTimeMillis();
            try{
                for(MyThread t : thread){
                    t.start();
                    t.join();
                }
            } catch (InterruptedException e) {

            }
            for(MyThread t: thread){
                this.gap += t.gap;
            }
            long t2 = System.currentTimeMillis();
            time = t2-t1;
        }
    }
    static int step, seed, N, M;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        step = sc.nextInt();
        seed = sc.nextInt();
        N = sc.nextInt();
        M = sc.nextInt();

        if(step>=1){
            System.out.printf("Step-1:seed(%d),N(%d),M(%d)%n", seed, N, M);
        }

        Random rand = new Random();
        ArrayList<Integer>[] list = new ArrayList[M];
        for(int i=0; i<M; i++) list[i] = new ArrayList<Integer>();
        for(int i=0; i<M; i++){
            rand.setSeed(seed++);
            for(int j=0; j<N; j++) list[i].add(rand.nextInt(N));
        }

        if(step>=2){
            long g = 0;
            for(ArrayList<Integer> l : list) g += getGap(l);
            System.out.printf("Step-2:Gap(%d)%n", g);
        }

        SingleThread singleThread = new SingleThread(list);
        singleThread.start();
        if(step>=3){
            System.out.printf("Step-3:Single Threading Gap(%d)%n", singleThread.gap);
        }

        MultiThread multiThread = new MultiThread(list);
        multiThread.start();
        if(step>=4){
            System.out.printf("Step-4:Multi Threading Gap(%d)%n", multiThread.gap);
        }

        //System.out.printf(" single %d, multi %d", singleThread.time, multiThread.time);
    }
    static long getGap(ArrayList<Integer> list){
        long gap = 0;
        for(int i=0; i<list.size()-1; i++){
            gap += Math.abs(list.get(i+1)-list.get(i));
        }
        return gap;
    }


}
