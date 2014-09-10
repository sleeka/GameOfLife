import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.JOptionPane;

public class GoLPanel extends JPanel implements ActionListener
{
	
	//private final int WIDTH=300, HEIGHT=200;
	//private final int RADIUS=5;
	private ArrayList pointList,pointList2;
	private int nRun = 0;
	private int count;
	private int GRID = 10;
	private static int tru = 0, speedCount=3;
	private final int WIDTH = 985, HEIGHT = 985, GRID_SIDE = 975;
	private int SQUARE_SIDE = 10,OLD_SQUARE_SIDE=10;
	public JPanel panel;
	protected JLabel Toggle_Run, Step, Clear, Rules;
	protected JComboBox Zoom, Speed;
	protected String[] zoom = {"Tiny Sq. Size","Small Sq. Size","Normal Sq. Size","Large Sq. Size","Uber Sq. Size"};
	protected String[] speed = {"Uber-Fast Speed (Caution!)","Quick Speed","Normal Speed","Uber-Slow Speed","Asleep Yet?"}; 
	JFrame frame;
	
	public GoLPanel(){
		
		pointList = new ArrayList();
		count=0;
		
		panel = new JPanel();
		String myText = "<HTML><u> | STEP | </u>";		
		Step = new JLabel(myText);
		//Step.setMnemonic(KeyEvent.VK_S);
		Step.setVerticalTextPosition(AbstractButton.BOTTOM);
        Step.setHorizontalTextPosition(AbstractButton.TRAILING);
      //  Step.setActionCommand("Step");
        //Step.addActionListener(this);     		
		myText = "<HTML><u> | RUN | </u>"; 		
		Toggle_Run = new JLabel(myText);
		//Toggle_Run.setMnemonic(KeyEvent.VK_R);
		Toggle_Run.setVerticalTextPosition(AbstractButton.BOTTOM);
        Toggle_Run.setHorizontalTextPosition(AbstractButton.TRAILING);
	//	Toggle_Run.setActionCommand("Run");
		//Toggle_Run.addActionListener(this);
 		myText = "<HTML><u> | CLEAR | </u>";       		
		Clear = new JLabel(myText);
	//	Clear.setMnemonic(KeyEvent.VK_C);
		Clear.setVerticalTextPosition(AbstractButton.BOTTOM);
        Clear.setHorizontalTextPosition(AbstractButton.TRAILING);
	//	Clear.setActionCommand("Clear");
	//	Clear.addActionListener(this);
		myText = "<HTML><u> | RULES | </u>";		
		Rules = new JLabel(myText);
	//	Rules.setMnemonic(KeyEvent.VK_U);
		Rules.setVerticalTextPosition(AbstractButton.BOTTOM);
        Rules.setHorizontalTextPosition(AbstractButton.TRAILING);
	//	Rules.setActionCommand("Rules");
	//	Rules.addActionListener(this);
		
		Zoom = new JComboBox(zoom);
		Zoom.setSelectedIndex(2);
        Zoom.addActionListener(this);

        		
		Speed = new JComboBox(speed);
		Speed.setSelectedIndex(2);
		Speed.addActionListener(this);
		
		addMouseListener (new DotsListener());
		addMouseMotionListener (new DotsListener());
		
		setBackground (Color.white);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		add(Step);
	    add(Toggle_Run);
        add(Zoom);
	    add(Speed);
	    add(Clear);
	    add(Rules);
	}
	
	public void paintComponent (Graphics page){
		super.paintComponent(page);
		page.setColor (Color.lightGray);
		page.fillRect(35,35,GRID_SIDE-35,GRID_SIDE-35);
		
		page.setColor (Color.black);		
		for (int i=35; i<=GRID_SIDE; i+=SQUARE_SIDE){
    	
    		page.drawLine(i,35,i,GRID_SIDE);
    		page.drawLine(35,i,GRID_SIDE,i);
		
		}
		page.setColor (Color.yellow);
		Iterator pointIterator = pointList.iterator();
		
		while (pointIterator.hasNext()){
			Point point1 = (Point) pointIterator.next();
		//	if (point1 != null && point2 != null)
			page.fillRect(point1.x, point1.y, SQUARE_SIDE, SQUARE_SIDE);
		}
	//	page.drawString ("Count: "+count,5,15);
	//	double distance = Math.sqrt(Math.pow(point1.x-point2.x,2)+Math.pow(point1.y-point2.y,2));
	//	page.drawString ("Distance: "+distance,5,45);
	}
	
		public void actionPerformed (ActionEvent e){
			
				JComboBox cb = (JComboBox)e.getSource();
       			String action = (String)cb.getSelectedItem();	
       		//	System.out.println(action);
       		//	System.out.println(e.getActionCommand());
       		//	if ("Clear"==e.getActionCommand())
       		//		clearGrid();
       		//	if ("Rules"==e.getActionCommand())
       		//		JOptionPane.showMessageDialog(frame,"1) A fragged cell will respawn if it has exactly 3 live friendly forces surrounding it.\n2) A live cell with 2 or 3 live neighbors has covering suppressive fire.\n3) A cell is fragged with a headshot between the eyes if it has 0, 1, 4 or more live friendly forces.","The Rules of the Game",JOptionPane.PLAIN_MESSAGE);
       		//	int term=0;
				
				for (int i=0; i<5; i++){
					if (speed[i].equals(action)){
						speedCount=i+1;
						//term=1;
						}
					else if (zoom[i].equals(action)){
						OLD_SQUARE_SIDE=SQUARE_SIDE;
						if (i>0)
							SQUARE_SIDE=i*5;
						else
							SQUARE_SIDE=3;
						//term=1;
						//setRows();
						repaint();
					//	setSide();
					}				
				}
       		}
       	
       	public void step(){
       		for (int i=0; i<pointList.size(); i++)
       			pointList2.add(pointList.get(i));      		
       		for (int i=0; i<pointList.size(); i++){
       			Point p4 = (Point) (pointList.get(i));
       			int num = countNums(p4.x,p4.y);
       		if (num<2 || num>3)
       			pointList2.remove(i);
       			}
       		for (int x=35; x<975; x+=SQUARE_SIDE){
       			for (int y=35; y<975; y+=SQUARE_SIDE){
       				int num = countNums(x,y);
       				if (num==3){
       					Point p = new Point(x,y);
       					pointList2.add(p);
       			}
       		}
       	}
       		pointList.clear();
       		for (int i=0; i<pointList2.size(); i++)
       			pointList.add(pointList2.get(i));
       		repaint();
    }
    
    	public void run(){
			int n=1;
			while (nRun!=0){
				if (n%speedCount==0)
					step();
			n++;
			}
		}       	
       	
       	public int countNums(int x, int y){
      		int count = 0;
      		int x1 = x+SQUARE_SIDE;
      		int x2 = x-SQUARE_SIDE;
      		int y1 = y+SQUARE_SIDE;
      		int y2 = y-SQUARE_SIDE;
      		Point a = new Point (x,y1);
      		Point b = new Point(x,y2);
      		Point c = new Point(x1,y1);
      		Point d = new Point(x2,y2);
      		Point e = new Point(x1,y2);
      		Point f = new Point(x2,y1);
      		Point g = new Point(x1,y);
      		Point h = new Point(x2,y);
      		if (pointList.contains(a)) count++;
      		if (pointList.contains(b)) count++;
      		if (pointList.contains(c)) count++;
      		if (pointList.contains(d)) count++;
      		if (pointList.contains(e)) count++;
      		if (pointList.contains(f)) count++;
      		if (pointList.contains(g)) count++;
      		if (pointList.contains(h)) count++;
      		return count;
       	}
       		
       	public void clearGrid(){
	       	pointList.clear();
	       	repaint();       			
       	}
       	
		private class DotsListener implements MouseListener,MouseMotionListener{
		public void mousePressed (MouseEvent event){
			Point p8 = event.getPoint();
		//	int div = 35-SQUARE_SIDE/2;
			int X = (((p8.x-35)/SQUARE_SIDE)*SQUARE_SIDE)+35;
			int Y = (((p8.y-35)/SQUARE_SIDE)*SQUARE_SIDE)+35;
			p8 = new Point(X,Y);
		if (p8.x>=35 && p8.x<975 && p8.y>=35 && p8.y<975){
			if (pointList.contains(p8) && tru==0)
				pointList.remove(pointList.indexOf(p8));
			else
				pointList.add(p8);
			repaint();
			}
			
		
			
		}
			public void mouseDragged (MouseEvent event){
	//		point2 = event.getPoint();
	//		repaint();
		//	int div = 35-SQUARE_SIDE/2;
			Point point2 = event.getPoint();
			int X = (((point2.x-35)/SQUARE_SIDE)*SQUARE_SIDE)+35;
			int Y = (((point2.y-35)/SQUARE_SIDE)*SQUARE_SIDE)+35;
			point2 = new Point(X,Y);
		if (point2.x>35 && point2.x<975 && point2.y>35 && point2.y<975){
		//	if (pointList.contains(point1) && tru==0)
		//		pointList.remove(pointList.indexOf(point1));
		//	else
				pointList.add(point2);
			count++;
			repaint();
			}
		}
		
		public void mouseReleased(MouseEvent event){}				
		public void mouseClicked(MouseEvent event){
			Point p8 = event.getPoint();
		//	System.out.println(p8);
			if (p8.x>285 && p8.x<325 && p8.y<25 && p8.y>10 ){
				if (nRun==0){
					nRun=1;
					String myText = "<HTML><u> | STOP | </u>"; 		
					Toggle_Run.setText(myText);
					}		
				else {
					nRun=0;
					String myText = "<HTML><u> | RUN | </u>"; 		
					Toggle_Run.setText(myText);
				}
					
					run();
				
			}
						
			if (p8.x>275 && p8.x<240 && p8.y<25 && p8.y>10 )
				step();
			if (p8.x>635 && p8.x<685 && p8.y<25 && p8.y>10 )
				clearGrid();
			if (p8.x>695 && p8.x<745 && p8.y<25 && p8.y>10 )
				JOptionPane.showMessageDialog(frame,"1) A fragged cell will respawn if it has exactly 3 live friendly forces surrounding it.\n2) A live cell with 2 or 3 live neighbors has covering suppressive fire.\n3) A cell is teamkilled with a headshot if it has 0, 1, 4 or more live friendly forces.","The Rules of the Game",JOptionPane.PLAIN_MESSAGE);
				
				}			
		public void mouseEntered(MouseEvent event){}
		public void mouseExited(MouseEvent event){}
		public void mouseMoved(MouseEvent event){}
	}
}