package engine;

import javax.sound.sampled.*;
import java.util.ArrayList;

public class AudioPlayer {
	
	private Clip clip;

	public AudioPlayer(String s) {
		
		try {
			
			AudioInputStream ais =
				AudioSystem.getAudioInputStream(
					getClass().getResourceAsStream(
						s
					)
				);
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED,
				baseFormat.getSampleRate(),
				16,
				baseFormat.getChannels(),
				baseFormat.getChannels() * 2,
				baseFormat.getSampleRate(),
				false
			);
			AudioInputStream dais =
				AudioSystem.getAudioInputStream(
					decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	
	public void play() {
		if(clip == null) return;
		if(clip.isRunning()) return;
		stop();
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void stop() {
		if(clip.isRunning())
			clip.stop();
	}
	
	public void close() {
		stop();
		clip.close();
	}
	public void volumeDown(){
		setVolume(-10.5F);
	}
	public void volumeUp(){
		setVolume(1.0F);

	}
	public void setVolume(double volume){
		FloatControl gainControl =
				(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue((float)volume);
	}
	public void loop(int a){
		clip.loop(a);
	}

	public void mute(){
		this.setVolume(-80.0F);
	}
	
}














