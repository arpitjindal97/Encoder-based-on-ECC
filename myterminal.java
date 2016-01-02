import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.text.*;
public class myterminal
{
    JFrame frame;
    JPanel panel;
    JTextArea area;
    int printed=0;String str="";
    myterminal()throws Exception
    {
        frame=new JFrame();
        Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
        area=new JTextArea();
        panel=new JPanel();
        File ff=new File(myterminal.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        ProcessBuilder pb;
        if(ff.getName().toLowerCase().endsWith(".jar"))
            pb=new ProcessBuilder("java","-cp",ff.getName(),"total_cli");
        else
            pb=new ProcessBuilder("java","","","total_cli");
        pb.redirectErrorStream(true);
        Process pp=pb.start();
        BufferedReader cmdbr=new BufferedReader(new InputStreamReader(pp.getInputStream()));
        BufferedWriter cmdout=new BufferedWriter(new OutputStreamWriter(pp.getOutputStream()));
        class Filter extends DocumentFilter {
            public void insertString(final FilterBypass fb, final int offset, final String string, final AttributeSet attr)
                    throws BadLocationException {
                if (offset >= printed) {
                    super.insertString(fb, offset, string, attr);
                }
            }
        
            public void remove(final FilterBypass fb, final int offset, final int length) throws BadLocationException {
                if (offset >= printed) {
                    super.remove(fb, offset, length);
                }
            }
        
            public void replace(final FilterBypass fb, final int offset, final int length, final String text, final AttributeSet attrs)
                    throws BadLocationException {
                if (offset >= printed) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        }
        ((AbstractDocument)area.getDocument()).setDocumentFilter(new Filter());
        Thread input=new Thread(new Runnable()
        {
            public void run()
            {
                while(true)
                {
                    try
                    {
                        int i=cmdbr.read();
                        area.append((char)i+"");
                        printed=area.getText().length();
                    }
                    catch(Exception eq)
                    {break;}
                }
            }
        });
        JScrollPane scroll=new JScrollPane(area);
        DefaultCaret caret = (DefaultCaret)area.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        frame.setSize(600,350);
        frame.setLocation(dim.width/2-frame.getWidth()/2,dim.height/2-frame.getHeight()/2);
        frame.add(scroll,BorderLayout.CENTER);
        frame.setVisible(true);
        area.setLineWrap(true);
        area.setBackground(Color.BLACK);
        Font font=new Font("Monospaced",Font.BOLD,12);
        area.setForeground(Color.GREEN);
        area.setFont(font);
        input.start();
        area.setCaretColor(Color.WHITE);
        int i=0;
        area.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode()==KeyEvent.VK_ENTER)
                {
                    int range=area.getText().length()-printed;
                    try
                    {
                        String tht=area.getText(printed, range).trim();
                        if(tht.equals("cls"))
                        {
                            printed=0;area.setText("");tht="";
                        }
                        cmdout.write(tht);
                        cmdout.newLine();
                        cmdout.flush();
                        area.setCaretPosition(area.getText().length());
                        printed=area.getText().length();
                        if(tht.equals("exit"))
                            System.exit(0);
                    }
                    catch(Exception e1)
                    {}
                }
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pp.waitFor();
    }
    public static void main(String arg[])throws Exception
    {
        new myterminal();
    }
}
