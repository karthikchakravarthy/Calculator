
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;


public class Calculator implements ActionListener
{
	JTextField screen;
	JLabel brand;
	JFrame frame;
	
	Calculator()                                          //initialising
	{   frame=new JFrame("karthik's calculator");
	    frame.setLayout(new FlowLayout());
        frame.setSize(300, 300);
	    screen=new JTextField("type an expression",25);//text field
        brand=new JLabel("             desinged by karthik chakravarthy               ");
		frame.add(screen);
        screen.addActionListener(this);
        JButton digits[]=new JButton[10];
        for(int i=0;i<10;i++)
	    {
        	digits[i]=new JButton(String.valueOf(i));
	        digits[i].addActionListener(this);
	        frame.add(digits[i]);
	    }
        JButton operators[]=new JButton[6];
        operators[0]=new JButton("+");
        operators[1]=new JButton("-");
        operators[2]=new JButton("*");
        operators[3]=new JButton("/");
        operators[4]=new JButton("=");
        operators[5]=new JButton("clear");
		
        for(int i=0;i<6;i++)
        {
        	frame.add(operators[i]);
            operators[i].addActionListener(this);
        }
		frame.add(brand);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    } 
	
	
	String screenString="",command,opt="h",temp="";
	int op[]=new int[10],flag1=1,count=0,numberCount=0,operatorCount=0;
	OperatorStack operatorStack=new OperatorStack();
	public void actionPerformed(ActionEvent ae)
	{ 

		command=ae.getActionCommand();             //action can be from any item
		screenString=screenString+command;
		screen.setText(screenString);
		
        if(command=="=")
        {
			String c=operatorStack.pop();
            if(numberCount<=operatorCount)
            {
				System.out.println("syntax error");
				screenString="syntax error";
				screen.setText(screenString);
			}
            else
			{  
				while(c!="h")
				{
					op[count-1]= cal(c);
					System.out.println(op[count-1]+"is calculated and put into "+(count-1));
					count--;
					c=operatorStack.pop();
				}
				if(op[count]==104729)
				{
					System.out.println("undefined");
					screen.setText("undefined");
				}
				else
				{	 
					System.out.println("answer is"+op[count]+" at"+count);
					command=String.valueOf(op[count]);
					screenString=screenString+command;
					screen.setText(screenString);
				}
			}
           
   	
        }
		else if(command=="+"||command=="-"||command=="/"||command=="*")
		{   
			if(priority(opt)>priority(command))
			 {   String c;
				while((priority(c=operatorStack.pop()))>priority(command))
			     {  
					System.out.println("priority of" + opt+" "+c+"is higher than" +command);
					
					op[count-1]= cal(c);
					System.out.println(op[count-1]+"is calculated and put into "+(count-1));
					count--;
					}
				operatorStack.push(c);
				count++;
		     opt=command;
		     
			operatorStack.push(command);
			
		     }
			else
			{	 
				opt=command;
				operatorStack.push(command);
				count++;
		    }
			flag1=1;
		    temp="";
		    operatorCount++;
		    System.out.println("operatorCount is "+operatorCount);
		}
		else if(command=="clear")
		{
			screenString="";
            screen.setText(screenString);
			count=0;
			numberCount=0;
			operatorCount=0;
			temp="";
			operatorStack.top=-1;
			flag1=1;
			System.out.println("clear is called");
		}
		else
		{   
			if(flag1==1)
				 numberCount++;
				
			temp=temp+command;
			op[count]=Integer.parseInt(temp); 
			System.out.println(op[count]+"at index "+count+"numberCount is "+numberCount);
			flag1=0;
			 
		}
		
    }
	int priority(String s)
	{
		switch(s)
		{
		case"+":
			return 1;
		case"-":
			return 1;
		case"/":
			return 2;
		case"*":
			return 2;
		default:
			return 0;
		}
	}
	int cal(String s)
	{
      switch(s)
      {
		case"+":
			return op[count-1]+op[count];
		case"-":
			return op[count-1]-op[count];
		case"/":
			if(op[count]==0)
				return 104729;
		    else
			return op[count-1]/op[count];
		case"*":
			return op[count-1]*op[count];
		default:
			return 0;
		}

	}	
	
	public static void main(String args[])
	{
		SwingUtilities.invokeLater(new Runnable(){public void run(){new Calculator();}});
	}
	
}
class OperatorStack
{
  int top;
  String ch[];
  operatorStack()
  { top=-1;
    ch=new String[10];
  }
  
  void push(String c)
  {
     if(top<10)
	  {
	  top++;
	  
      ch[top]=c;
      System.out.println(c+"is pushed");
      System.out.println("top is"+top);
      }
  }
  String pop()
  { if(top>=0)
    { 
	  String c= ch[top];
       top--;
       System.out.println(c+"is poped");
       System.out.println("top is"+top);
       return c;
   }
   else
	   return "h";
   }
}










