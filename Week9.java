public class Week9 {
    public static void main(String[] args) {
        SingleThread singleThread = new SingleThread();
        MultiThread multiThread = new MultiThread();
        try{
            singleThread.start();
            singleThread.join();
        } catch (Exception e) {

        }
        multiThread.start();


    }

    static class SingleThread extends Thread {
        public void run() {
            long time = System.currentTimeMillis();
            System.out.println("Single Thread Start");
            System.out.print("[");
            for(int i=0; i<2_000_000; i++){
                if(i%100==0) System.out.print(".");
            }
            System.out.print("]\n");
            System.out.printf("Taken time:%d%n", System.currentTimeMillis()-time);
        }
    }

    static class MultiThread {
        Thread t1 = new Thread(){
            public void run(){
                for(int i=0; i<1_000_000; i++) {
                    if(i%100==0) System.out.print(".");
                }
            }
        };
        Thread t2 = new Thread(){
            public void run(){
                for(int i=0; i<1_000_000; i++) {
                    if(i%100==0) System.out.print(".");
                }
            }
        };
        public void start(){
            long time = System.currentTimeMillis();
            System.out.println("Multi Thread Start");
            System.out.print("[");
            t1.start();
            t2.start();
            System.out.print("]\n");
            System.out.printf("Taken time:%d%n", System.currentTimeMillis()-time);
        }
    }
}