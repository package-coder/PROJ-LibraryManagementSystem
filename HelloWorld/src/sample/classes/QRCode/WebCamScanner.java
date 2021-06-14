package sample.classes.QRCode;

import com.github.sarxos.webcam.Webcam;

import java.awt.image.BufferedImage;

public abstract class WebCamScanner {
    private Webcam webcam;
    private final VideoLapse video;
    private boolean isRunning = false;

    public WebCamScanner(Webcam webcam) {
        video = new VideoLapse();
        setWebcam(webcam);
    }

    public Webcam getWebcam() {
        return webcam;
    }

    public void setWebcam(Webcam webcam) {

        if(webcam == null)
            throw new NullPointerException("Webcam can't be null");

        this.webcam = webcam;
        start();
    }

    public void start(){
        if(!isRunning){
            video.start();
            isRunning = true;
        }

        webcam.open();
    }

    public void stop(){
        if(isRunning)
            isRunning = false;

        webcam.close();
    }

    public abstract void elapsedImage(BufferedImage image);

    private class VideoLapse extends Thread{

        @Override
        public void run(){
            while (isRunning){
                try {
                    if(webcam.isOpen()){
                        var image = webcam.getImage();
                        if (image != null)
                            elapsedImage(image);
                    }
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    System.err.println("VideoLapse.run");
                }
            }
        }

    }
}
