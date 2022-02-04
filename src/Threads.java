import java.util.Arrays;

public class Threads {
    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;

    public static void array(){
        long a = System.currentTimeMillis();
        float[] arr = new float[SIZE];

        Arrays.fill(arr, 1);

        for(int i = 0; i < SIZE; i++){
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Время работы метода:" + (System.currentTimeMillis() - a));
    }

    public static void arrayWThreads(){
        long a = System.currentTimeMillis();
        float[] arr = new float[SIZE];
        float[] a1 = new float[HALF];
        float[] a2 = new float[HALF];

        Arrays.fill(arr, 1);

        System.arraycopy(arr, 0, a1, 0, HALF);
        System.arraycopy(arr, HALF, a2, 0, HALF);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run(){
                for(int i = 0; i < HALF; i++){
                    a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run(){
                for(int i = 0; i < HALF; i++){
                    a2[i] = (float) (a2[i] * Math.sin(0.2f + (i + HALF) / 5) * Math.cos(0.2f + (i + HALF) / 5) * Math.cos(0.4f + (i + HALF) / 2));
                }
            }
        });

        t1.start();
        t2.start();
        try{
            t1.join();
            t2.join();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, HALF);
        System.arraycopy(a2, 0, arr, HALF, HALF);

        System.out.println("Время работы метода:" + (System.currentTimeMillis() - a));
    }
}