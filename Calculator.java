import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;


public class Calculator implements ActionListener
{   
	// initializing empty operator stack
    Stack<Character> stackOp;
    boolean prevint,syntaxerror;
    //initializing empty number stack
    Stack<Integer> stackNum;
    JTextArea screen;
	JLabel brand;
	JPanel btnPanel;
	JButton[][] btn;
	JPanel mainPanel;
	JFrame frame;
	String screenString="",command;
	
	//constructor
	
	  public String[][] BUTTON_TEXTS = {
			  {"AC","C","",""},
		      {"7", "8", "9", "+"},
		      {"4", "5", "6", "-"},
		      {"1", "2", "3", "*"},
		      {"0", "00", "/", "="}
		   };
		   public Font BTN_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 24);
		   
	Calculator() 
	{   
		stackOp= new Stack();
		stackNum=new Stack();
		prevint=false;
		brand=new JLabel("            Designed and Developed by Ande Karthik Chakravarthy");
		syntaxerror=false;
		      screen = new JTextArea(2,25);
		      screen.setFont(BTN_FONT.deriveFont(Font.PLAIN));
		      btnPanel = new JPanel(new GridLayout(BUTTON_TEXTS.length,
		            BUTTON_TEXTS[0].length));
		      btn = new JButton[BUTTON_TEXTS.length][BUTTON_TEXTS[0].length];
		      for (int i = 0; i < BUTTON_TEXTS.length; i++) {
		         for (int j = 0; j < BUTTON_TEXTS[i].length; j++) {
		            btn[i][j] = new JButton(BUTTON_TEXTS[i][j]);
		            btn[i][j].setFont(BTN_FONT);
		            btn[i][j].addActionListener(this);
		            btnPanel.add(btn[i][j]);
		            
		         }
		      }

		      mainPanel = new JPanel(new BorderLayout());
		      mainPanel.add(screen, BorderLayout.PAGE_START);
		      mainPanel.add(btnPanel, BorderLayout.CENTER);
		      mainPanel.add(brand, BorderLayout.SOUTH);


		      frame = new JFrame("karthik's calculator");
		      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		      frame.getContentPane().add(mainPanel);
		      frame.pack();
		      frame.setLocationRelativeTo(null);
		      frame.setVisible(true);
		      frame.setSize(400,500);
    } 
	
	
	public void actionPerformed(ActionEvent ae)
	{ 

		command=ae.getActionCommand();             //action can be from any item
		
		
        if(command=="=")
        { 
        	screenString=expToNum(screenString);
            screen.setText(screenString);
            prevint=false;
            
        }	
        else if(command=="C")
        {
        	screenString=screenString.substring(0,screenString.length()-1);
        	screen.setText(screenString);
        }
		else if(command=="AC")
		{
			screenString="";
            screen.setText(screenString);
			stackNum.clear();
			stackOp.clear();
			System.out.println("clear is called");
		}
		else
		{   if(syntaxerror==true)
			screenString="";
		syntaxerror=false;
			screenString+=command;			
			screen.setText(screenString);	
			System.out.println(screenString);
		}
        
    }
		
    //converts an expression to number
    String expToNum(String exp)
    {
        for (int i = 0; i<exp.length();i++)
        {System.out.println("\n");
            char spec = exp.charAt(i);
            System.out.println("spec is"+spec);
            if (Character.isDigit(spec))
            {
            	if(prevint)
            		{
            		 String s=new Integer(stackNum.pop()).toString()+new Integer(spec-'0').toString();
            		int x=Integer.parseInt(s);
            		 stackNum.push(x);
            		System.out.println(x);
            		System.out.println("top is "+stackNum.peek());
            		}
            	else
            		{stackNum.push(new Integer(spec-'0'));
            		System.out.println("top is "+stackNum.peek());
            		}
            	
            	prevint=true;
            }    
            else if (spec == '(')
            { stackOp.push(spec);
            System.out.println("top is "+stackOp.peek());}

            else if (spec == ')')
            {   
                while (!stackOp.isEmpty() && stackOp.peek() != '(')                  
                {
                   	cal(stackOp.pop().toString());
                   	System.out.println("top is "+stackNum.peek());
                }
                if (stackOp.isEmpty())
                    return "Invalid Expression"; // invalid expression                
                else
                    stackOp.pop();
            }
            else // an operator is encountered
            {   prevint=false;
            	if(!stackOp.isEmpty())
                {
            		while (!stackOp.isEmpty() && prec(spec) <= prec(stackOp.peek()))
                	cal(stackOp.pop().toString());
                }
                stackOp.push(spec);
                System.out.println("top is "+stackOp.peek());
            }
      
        }
      
        // pop all the operators from the stack
        while (!stackOp.isEmpty())
        	{
        	try{
        	cal(stackOp.pop().toString());
        	}
        	catch(ArithmeticException e)
        	{
        		syntaxerror=true;
        		return "Undefined";
        	}
        	}
      
        return stackNum.pop().toString();
    }
    
	int prec(char s)
	{
		switch(s)
		{
		case'+':
		case'-':
			return 1;
		case'/':
		case'*':
			return 2;
		default:
			return -1;
		}
	}
	void cal(String s) throws ArithmeticException
	{
		int a,b;
		try{
      switch(s)
      {
      
		case"+":
		{
			b=stackNum.pop();
    	    a=stackNum.pop();
			stackNum.push(a+b);
			break;
		}
			
		case"-":
		{
			b=stackNum.pop();
    	    a=stackNum.pop();
			stackNum.push(a-b);
			break;
		}
		case"/":
		{   
				b=stackNum.pop();
	    	    a=stackNum.pop();
	    	    int x=a/b;
				stackNum.push(x);
			
			
		break;
		}
		case"*":
		{
			b=stackNum.pop();
    	    a=stackNum.pop();
			stackNum.push(a*b);
			break;
		}
      
		
		}
		}
		catch(EmptyStackException e)
		{
			screenString="Sytax Error";
			screen.setText(screenString);
			syntaxerror=true;
			return;
		}

	}
	
	public static void main(String args[])
	{
		SwingUtilities.invokeLater(new Runnable(){public void run(){new Calculator();}});
	}
	
	
}