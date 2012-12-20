import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Song {
	
	private AudioInputStream in;
	private AudioFormat decodedFormat;
	private AudioInputStream din;
	
	private String title;
	private String author;
	private String year;
	private String genre;
	private long duration;
	
	public Song(String url) {
		
		File file = new File(url);
		
		din = null;
		
		openMetaData(file);
		openFile(file);
		
	}
	
	public void playFile() {
		// Play now.
		try {
			rawplay(decodedFormat, din, 30);
		} catch (IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void openFile(File file) {
		in = null;
		
		try {
			in = AudioSystem.getAudioInputStream(file);
		} catch (UnsupportedAudioFileException | IOException e1) {
			e1.printStackTrace();
		}
		
		AudioFormat baseFormat = in.getFormat();
		decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
								                    baseFormat.getSampleRate(),
								                    16,
								                    baseFormat.getChannels(),
								                    baseFormat.getChannels() * 2,
								                    baseFormat.getSampleRate(),
								                    false);
		din = AudioSystem.getAudioInputStream(decodedFormat, in);
	}
	
	public void openMetaData(File file) {
		AudioFileFormat baseFileFormat = null;
		
		try {
			baseFileFormat = AudioSystem.getAudioFileFormat(file);
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
		
		Map<String, Object> properties = baseFileFormat.properties();
		this.title = (String) properties.get("title");
		this.author = (String) properties.get("author");
		this.duration = (Long) properties.get("duration");
		this.genre = "Unknown";
		
		
	}
	
	private void rawplay(AudioFormat targetFormat, AudioInputStream din, int duration)
	throws IOException, LineUnavailableException {
		duration *= 320 * 1024;
		byte[] data = new byte[4096];
		SourceDataLine line = getLine(targetFormat);
		if (line != null) {
			din.skip(320 * 1024);
			// Start
			line.start();
			int nBytesRead = 0;
			while(nBytesRead != -1 && duration > 0) {
				nBytesRead = din.read(data, 0, data.length);
				if(nBytesRead != -1) {
					line.write(data, 0, nBytesRead);
					duration -= nBytesRead;
				}
			}
			
			// Stop
			line.drain();
			line.stop();
			line.close();
			din.close();
		}
	}
	
	private SourceDataLine getLine(AudioFormat audioFormat) throws LineUnavailableException {
		SourceDataLine res = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		res = (SourceDataLine) AudioSystem.getLine(info);
		res.open(audioFormat);
		return res;
	}
	
	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getYear() {
		return year;
	}

	public String getStyle() {
		return genre;
	}

	public long getDuration() {
		return duration;
	}
	
	public String toString() {
		   return "Titre: " + this.title +
			  ", Auteur: " + this.author + 
			  ", Année: " + this.year + 
			  ", Genre: " + this.genre;
	}
}
