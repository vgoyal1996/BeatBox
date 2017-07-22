package soundplayer;
import javax.sound.midi.*;

public class MiniMiniMusicApp {
	
	public static void main(String args[]){
		MiniMiniMusicApp mini = new MiniMiniMusicApp();
		mini.play();
	}
	
	public void play(){
		try{
			//1.get a sequencer and open it
			Sequencer player = MidiSystem.getSequencer();
			player.open();
			//2
			Sequence seq = new Sequence(Sequence.PPQ,4);
			//3. ask the sequencer for a track
			Track track = seq.createTrack();
			//4
			ShortMessage a = new ShortMessage();
			ShortMessage b = new ShortMessage();
			for(int i=0;i<8;i++){
				if(i%2==0){
					a.setMessage(144,1,44+i*2,100);
					MidiEvent noteOn = new MidiEvent(a,2*i);
					track.add(noteOn);
				}
				else{
					b.setMessage(144,1,44+i*4,100);
					MidiEvent noteOff = new MidiEvent(b,4*i);
					track.add(noteOff);
				}
			}
			//give the sequence to the sequencer
			player.setSequence(seq);
			//start the sequencer
			player.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
