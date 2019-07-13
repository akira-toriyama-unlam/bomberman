package com.bomberman.extras;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	 private boolean repeat ;
	    private Clip audioClip;
	    private File audioFile;
	    
	    /* IF isRepeat = true , sound loop until user want to stop 
	    else sound will loop once
	    */
	    
	    public Sound(String audioFilePath,boolean isRepeat)
	    {
	        repeat = isRepeat;
	        audioFile = new File(audioFilePath);
	        try {
	            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
	 
	            AudioFormat format = audioStream.getFormat();
	 
	            DataLine.Info info = new DataLine.Info(Clip.class, format);
	 
	            audioClip = (Clip) AudioSystem.getLine(info);
	            audioClip.open(audioStream);
	             
	        } catch (UnsupportedAudioFileException ex) {
	            System.out.println("The specified audio file is not supported.");
	            ex.printStackTrace();
	        } catch (LineUnavailableException ex) {
	            System.out.println("Audio line for playing back is unavailable.");
	            ex.printStackTrace();
	        } catch (IOException ex) {
	            System.out.println("Error playing the audio file.");
	            ex.printStackTrace();
	        }
	        
	    }
	    
	    public void stop()
	    {
	        audioClip.stop();
	    }
	    
	    public void play()
	    {
	        if(repeat)
	            audioClip.loop(Clip.LOOP_CONTINUOUSLY);
	        audioClip.setFramePosition(0);
	        audioClip.start();
	    }
	    
}
