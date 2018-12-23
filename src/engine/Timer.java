package engine;


//Really simple timer class could have used the Java.Timer instead
public class Timer {
    private long time;


    public Timer(){
        stop();
    }
    public void start(){

        time = System.nanoTime();

    }
    public void stop(){
        time = -1;
    }
    public long getElapsedTime(){
        if(time != -1){
            return (System.nanoTime()-time)/1000000000;
        }
        else
            return -20;
    }

    public long getTime() {
        return time;
    }
}
