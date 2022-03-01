package Main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	private Clip clip;
	private URL soundURL[] = new URL[30];

	public Sound() {
		soundURL[0] = getClass().getResource("/Sound/tot_1.wav");
		soundURL[1] = getClass().getResource("/Sound/death1.wav");
	}

	/**
	 * Public setter method to set the music accordingly
	 * This is to enable different audio effects such as death sounds
	 * @param i - value coresponding to the url in the array
	 */
	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);

		} catch (Exception e) {

		}
	}

	/**
	 * Function to play main Background music
	 */
	public Clip playMusic(int i) {
		setFile(0);
		clip.start();
    	this.loop();
		return clip;
	}

	/**
	 * Function to stop the current music that is playing
	 */
	public void stopMusic() {
		clip.stop();
	}

	/**
	 * Function to play the death sound
	 */
	public void playDeathSound(){
		setFile(1);
		clip.start();
	}
	
	/**
	 * Function to loop the audio
	 */
	private void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
}