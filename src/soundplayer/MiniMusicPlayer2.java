package soundplayer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;
import javax.sound.midi.*;

public class MiniMusicPlayer2 {
	
	static JFrame frame = new JFrame("My first music video");
	static MyDrawPanel ml;
	
	public static void main(String args[]){
		MiniMusicPlayer2 mini = new MiniMusicPlayer2();
		mini.go();
	}
	
	public void setupGui(){
		ml = new MyDrawPanel();
		frame.setContentPane(ml);
		frame.setBounds(30,30,300,300);
		frame.setVisible(true);
	}
	
	public void go(){
		
		setupGui();
		try{
			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();
			
			sequencer.addControllerEventListener(ml, new int[] {127});
			Sequence seq = new Sequence(Sequence.PPQ,4);
			Track track = seq.createTrack();
			
			int r=0;
			for(int i=5;i<60;i+=4){
				r = (int)((Math.random()*120)+1);
				track.add(makeEvent(144,1,r,100,i));
				track.add(makeEvent(176,1,127,0,i));
				track.add(makeEvent(128,1,r,100,i+2));
			}
			sequencer.setSequence(seq);
			sequencer.setTempoInBPM(120);
			sequencer.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static MidiEvent makeEvent(int comd,int chan,int one,int two,int tick){
		MidiEvent event=null;
		try{
			ShortMessage a = new ShortMessage();
			a.setMessage(comd,chan,one,two);
			event = new MidiEvent(a,tick);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return event;
	}
	
	class MyDrawPanel extends JPanel implements ControllerEventListener{
		boolean msg = false;
		public void controlChange(ShortMessage event){
			msg = true;
			repaint();
		}
		
		public void paintComponent(Graphics g){
			if(msg){
				Graphics2D d = (Graphics2D)g;
				int r = (int)(Math.random()*250);
				int gr = (int)(Math.random()*250);
				int b = (int)(Math.random()*250);
				g.setColor(new Color(r,gr,b));
				int ht = (int)((Math.random()*120)+10);
				int width = (int)((Math.random()*120)+10);
				int x = (int)((Math.random()*40)+10);
				int y = (int)((Math.random()*40)+10);
				g.fillRect(x, y, ht, width);
				msg = false;
			}
		}
	}

}
