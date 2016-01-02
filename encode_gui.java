import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.math.BigInteger;
import java.util.Random;
public class encode_gui
{
    JFrame frame;
    JPanel1 outer;
    JTextField key;
    JButton details;
        JPanel detp=new JPanel();
    JTextField path1,path2;
    JLabel key_label,input_label,output_label,key2;
    JButton input,output,encoding;
    JRadioButton ee,dd;
        JButton import_key=new JButton("Browse");
    Main1 bob;Elliptic curve;
        JButton arrow1=new JButton("<<");
        JButton arrow=new JButton(">>");
    JTextField uni[]=new JTextField[7];
    encode_gui()
    {
        //System.out.println('\f');
        BigInteger p=new BigInteger("599");
        BigInteger a=new BigInteger("545353568684684");
        BigInteger b=new BigInteger("74353518449494997979");
        BigInteger gen_x=new BigInteger("434531");
        BigInteger gen_y=new BigInteger("7345355");
        curve=new Elliptic(p,a,b);
        bob=new Main1(curve,gen_x,gen_y,new BigInteger("7345351"));
        encoding=new JButton("Encode It!");
        encoding.setBounds(168,190,90,50);
        input=new JButton("Browse");
        output=new JButton("Browse");
        input.setBounds(95+230+10,110,79,20);
        output.setBounds(95+240,150,79,20);
        key_label=new JLabel();
        key_label.setText("Encryption");
        key_label.setForeground(Color.RED);
        key_label.setBackground(Color.BLACK);
        key_label.setOpaque(true);
        Font font=new Font("Serif",Font.BOLD,16);
        key_label.setFont(font);
        key_label.setBounds(10,30,80,20);
        key2=new JLabel("           Key");
        key2.setBackground(Color.BLACK);
        key2.setForeground(Color.RED);
        key2.setOpaque(true);
        key2.setFont(font);
        key2.setBounds(10,50,80,20);
        outer=new JPanel1();
        outer.add(output);
        outer.add(key2);
        font=new Font("Serif",Font.BOLD,14);
        ee=new JRadioButton("Encryption",true);
        dd=new JRadioButton("Decryption");
        ee.setBounds(30,190,120,23);
        ee.setFont(font);
        ee.setBackground(Color.BLACK);
        ee.setForeground(Color.GREEN);
        ButtonGroup bg=new ButtonGroup();
        bg.add(dd);
        bg.add(ee);dd.setFont(font);
        ee.setFocusPainted(false);
        dd.setFocusPainted(false);
        ee.setLayout(null);
        font=new Font("Serif",Font.BOLD,22);
        JLabel arpit=new JLabel("Created By:");
        JLabel arpit1=new JLabel("      Arpit");
        arpit.setBounds(290,180,120,30);
        arpit.setFont(font);
        arpit.setForeground(Color.YELLOW);
        arpit1.setForeground(Color.YELLOW);
        arpit1.setBounds(290,210,120,30);
        arpit1.setFont(font);
        outer.add(arpit);
        outer.add(arpit1);
        outer.add(ee);
        outer.add(dd);
        dd.setBounds(30,190+23+5,120,23);
        JMenuBar menubar=new JMenuBar();
        JMenu file=new JMenu("File");
        JMenuItem fileexport=new JMenuItem("Export Config",null);
        file.setMnemonic(KeyEvent.VK_F);
        fileexport.setMnemonic(KeyEvent.VK_S);
        menubar.add(file);
        file.add(fileexport);
        fileexport.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String str=bob.curve.p.toString()+","+bob.curve.a.toString()+
                ","+bob.curve.b.toString()+","+bob.generator.x.toString()+
                ","+bob.generator.y.toString()+","+bob.secret.toString();
                String ext[]={".config"};
                String name[]={"Configuration"};
                export_to_file ff=new export_to_file();
                ff.getData(name,ext,str,frame);
            }
        });
        outer.add(arrow);
        for(int h=1;h<7;h++)
        {
            uni[h]=new JTextField();
            uni[h].addKeyListener(new KeyAdapter()
            {
                public void keyPressed(KeyEvent e)
                {
                    if(e.getKeyCode()==KeyEvent.VK_TAB)
                    {
                        e.consume();
                        KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
                    }
                    if(e.getKeyCode()==KeyEvent.VK_TAB&&e.isShiftDown())
                    {
                        e.consume();
                        KeyboardFocusManager.getCurrentKeyboardFocusManager().focusPreviousComponent();
                    }
                    if(e.getKeyCode()==KeyEvent.VK_ENTER)
                    {
                        e.consume();
                    }
                }
            });
        }
        uni[0]=new JTextField();
        uni[0].addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode()==KeyEvent.VK_TAB)
                {
                    e.consume();
                    KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
                }
                else if(e.getKeyCode()==KeyEvent.VK_TAB&&e.isShiftDown())
                {
                    e.consume();
                    KeyboardFocusManager.getCurrentKeyboardFocusManager().focusPreviousComponent();
                }
                else if((e.getKeyCode()==KeyEvent.VK_V&&e.isControlDown())||
                e.getKeyCode()==KeyEvent.VK_ENTER||
                e.getKeyCode()==KeyEvent.VK_BACK_SPACE)
                {
                    e.consume();
                }
                else
                {
                    //e.consume();
                }
            }
            public void keyTyped(KeyEvent r)
            {
                r.consume();
            }
        });
        JMenuItem fileimport=new JMenuItem("Import Config",null);
        file.add(fileimport);
        fileimport.setMnemonic(KeyEvent.VK_O);
        fileimport.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String name[]={"Configuration"};
                String ext[]={".config"};
                File ff=import_from_file.getData(name,ext,frame);
                if(ff==null)
                {}
                else
                {
                    try
                    {
                        String values[]=encode_gui.read_from_file(ff);
                        bob.curve.p=new BigInteger(values[0]);
                        bob.curve.a=new BigInteger(values[1]);
                        bob.curve.b=new BigInteger(values[2]);
                        bob.generator.x=new BigInteger(values[3]);
                        bob.generator.y=new BigInteger(values[4]);
                        bob.secret=new BigInteger(values[5]);
                        bob.pub=Point1.multiply1(bob.secret,bob.generator,bob.curve);
                        uni[0].setText(bob.pub.toString());
                        uni[1].setText(bob.curve.p.toString());
                        uni[2].setText(bob.curve.a.toString());
                        uni[3].setText(bob.curve.b.toString());
                        uni[4].setText(bob.generator.x.toString());
                        uni[5].setText(bob.generator.y.toString());
                        uni[6].setText(bob.secret.toString());
                        for(int h=0;h<7;h++)
                            uni[h].setCaretPosition(0);
                    }
                    catch(Exception ee)
                    {JOptionPane.showMessageDialog(frame,ee);}
                }
            }
        });
        dd.setBackground(Color.BLACK);
        dd.setForeground(Color.GREEN);
        arrow1.setBounds(335,60,79,20);
        arrow.setBounds(335,60,79,20);
        arrow.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                detp.setBackground(Color.BLACK);
                JLabel p=new JLabel("P");
                p.setForeground(Color.RED);
                JLabel a=new JLabel("a");
                a.setForeground(Color.RED);
                a.setBounds(20,40,20,20);
                p.setBounds(20,10,20,20);
                JLabel b=new JLabel("b");
                b.setForeground(Color.RED);
                b.setBounds(20,70,20,20);
                uni[0].setText(bob.pub.toString());
                uni[1].setText(bob.curve.p.toString());
                uni[2].setText(bob.curve.a.toString());
                uni[3].setText(bob.curve.b.toString());
                uni[4].setText(bob.generator.x.toString());
                uni[5].setText(bob.generator.y.toString());
                uni[6].setText(bob.secret.toString());
                for(int h=0;h<7;h++)
                    uni[h].setCaretPosition(0);
                uni[1].setBounds(40,10,70,20);
                uni[2].setBounds(40,40,70,20);
                uni[3].setBounds(40,70,70,20);
                JLabel gen_x=new JLabel("Gen X");
                gen_x.setBounds(150,10,40,20);
                gen_x.setForeground(Color.RED);
                uni[4].setBounds(190,10,70,20);
                JLabel gen_y=new JLabel("Gen Y");
                gen_y.setForeground(Color.RED);
                gen_y.setBounds(150,40,40,20);
                JLabel secret=new JLabel("Secret Key");
                secret.setForeground(Color.RED);
                secret.setBounds(120,70,70,20);
                uni[5].setBounds(190,40,70,20);
                uni[6].setBounds(190,70,70,20);
                JLabel pub=new JLabel("Public Key");
                pub.setForeground(Color.RED);
                pub.setBounds(20,100,60,20);
                uni[0].setBounds(110,100,150,20);
                JButton update=new JButton("<html><center>Save</center>Changes</html>");
                update.setBounds(20,140,110,50);
                detp.add(update);
                update.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        String str="";
                        for(int i=1;i<7;i++)
                        {
                            str=uni[i].getText();
                            try
                            {
                                BigInteger tt=new BigInteger(str);
                            }
                            catch(Exception e11)
                            {
                                JOptionPane.showMessageDialog(frame,"Invalid Configuration");return;
                            }
                        }
                        Elliptic etemp=new Elliptic(new BigInteger(uni[1].getText()),new BigInteger(uni[2].getText()),new BigInteger(uni[3].getText()));
                        Point1 plain1=new Point1(new BigInteger("14949"),new BigInteger("498419"));
                        plain1.x=plain1.x.mod(etemp.p);
                        plain1.y=plain1.y.mod(etemp.p);
                        ArrayList<Point1> done=Mapping.generateMap1(plain1,etemp);
                        if(done.size()!=512)
                        {
                            JOptionPane.showMessageDialog(frame,"Invalid Configuration");return;
                        }
                        bob.curve.p=new BigInteger(uni[1].getText());
                        bob.curve.a=new BigInteger(uni[2].getText());
                        bob.curve.b=new BigInteger(uni[3].getText());
                        bob.generator.x=new BigInteger(uni[4].getText());
                        bob.generator.y=new BigInteger(uni[5].getText());
                        bob.secret=new BigInteger(uni[6].getText());
                        bob.pub=Point1.multiply1(bob.secret,bob.generator,bob.curve);
                        uni[0].setText(bob.pub.toString());
                        for(int h=0;h<7;h++)
                            uni[h].setCaretPosition(0);
                    }
                });
                JButton exportkey=new JButton("<html><center>Export</center>Public Key</html>");
                exportkey.setBounds(150,140,110,50);
                exportkey.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        String str=bob.curve.p.toString()+","+bob.curve.a.toString()+
                        ","+bob.curve.b.toString()+","+bob.generator.x.toString()+
                        ","+bob.generator.y.toString()+",";
                        String temp=bob.pub.toString();
                        for(int h=1;h<temp.length()-1;h++)
                            str=str+temp.charAt(h);
                        String ext[]=new String[1];
                        String des[]=new String[1];
                        ext[0]=".key";
                        des[0]="Key ";
                        export_to_file fr=new export_to_file();
                        File ff=fr.getData(des,ext,str,frame);
                    }
                });
                detp.add(exportkey);
                detp.add(uni[0]);
                detp.add(pub);
                detp.add(uni[6]);
                detp.add(uni[5]);
                detp.add(secret);
                detp.add(gen_y);
                detp.add(uni[4]);
                detp.add(gen_x);
                detp.add(uni[3]);
                detp.add(uni[2]);
                detp.add(uni[1]);
                detp.add(p);
                detp.add(a);
                detp.add(b);
                detp.setLayout(null);
                detp.setBounds(420,10,280,220);
                outer.add(detp);
                outer.remove(arrow);
                outer.add(arrow1);
                frame.setSize(430+280,300);
                frame.repaint();
            }
        });
        arrow1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                outer.remove(arrow1);
                outer.add(arrow);
                frame.setSize(440,300);
                frame.repaint();
            }
        });
        outer.add(key_label);
        key=new JTextField();
        path2=new JTextField();
        path1=new JTextField();
        path1.setBounds(95,110,230,20);
        key.setBounds(95,30,230,20);
        import_key.setBounds(95+230+10,30,79,20);
        import_key.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ee)
            {
                File ff=import_from_file.getData(new String[]{"Key"},new String[]{".key"},frame);
                if(ff!=null)
                    key.setText(ff.getAbsolutePath());
            }
        });
        ee.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e1)
            {
                encoding.setText("Encode It!");
                key.setEditable(true);
                import_key.setEnabled(true);
            }
        });
        dd.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e1)
            {
                //key_label.setText("Decryption");
                key.setEditable(false);
                import_key.setEnabled(false);
                encoding.setText("Decode It!");
            }
        });
        outer.add(import_key);
        outer.add(key);
        input_label=new JLabel("Input File");
        input_label.setFont(new Font("Serif",Font.BOLD,16));
        input_label.setBackground(Color.BLACK);
        input_label.setForeground(Color.RED);
        input_label.setBounds(10,110,90,20);
        outer.add(input_label);
        outer.add(path1);
        outer.add(encoding);
        input.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String name[]={"Text","Java","C"};
                String ext[]={".txt",".java",".c"};
                File file=import_from_file.getData(name,ext,frame);
                if(file!=null)
                    path1.setText(file.getAbsolutePath());
            }
        });
        encoding.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    String str=key.getText();
                    File temp=new File(path1.getText());
                    if(!temp.getName().toLowerCase().endsWith(".txt"))
                    {
                        JOptionPane.showMessageDialog(frame,"Invalid File Format");
                        return;
                    }
                    temp=new File(path2.getText());
                    if(!temp.getName().toLowerCase().endsWith(".txt"))
                    {
                        JOptionPane.showMessageDialog(frame,"Invalid File Format");
                        return;
                    }
                    if(ee.isSelected())
                    {
                        //encoder.Encode(path1.getText(),path2.getText(),str);
                        //long time=System.nanoTime();
                        File filetemp=new File(key.getText());
                        int i=0,j=0;
                        char ch;
                        BigInteger p,a,b,gx,gy,x,y;
                        String values[]=read_from_file(new File(key.getText()));
                        p=new BigInteger(values[0]);
                        a=new BigInteger(values[1]);
                        b=new BigInteger(values[2]);
                        Elliptic curve1=new Elliptic(p,a,b);
                        Point1 plain=new Point1(new BigInteger("14949"),new BigInteger("498419"));
                        plain.x=plain.x.mod(curve1.p);
                        plain.y=plain.y.mod(curve1.p);
                        ArrayList<Point1> done=Mapping.generateMap1(plain,curve1);
                        
                        gx=new BigInteger(values[3]);
                        gy=new BigInteger(values[4]);
                        x=new BigInteger(values[5]);
                        y=new BigInteger(values[6]);
                        Main1 alice=new Main1(curve1,gx,gy,new BigInteger("54864"));
                        alice.pub.x=x;
                        alice.pub.y=y;
                        Point1 cipher[]=Main1.encrypt(alice,plain);
                        File arpit=new File(path1.getText());
                        File encoded=new File(path2.getText());
                        FileWriter fos=new FileWriter(encoded);
                        BufferedWriter bw=new BufferedWriter(fos);
                        bw.write(cipher[0]+""+cipher[1]);
                        FileInputStream fis =new FileInputStream(arpit);
                        i=0;
                        while((i=fis.read())!=-1)
                        {
                            str=done.get(i-1).toString();
                            bw.write(str+"");
                        }
                        fis.close();
                        bw.close();
                        //System.out.println(System.nanoTime()-time);
                    }
                    else
                    {
                        //decoder.Decode(path1.getText(),path2.getText(),str);
                        //long time=System.nanoTime();
                        int i=0,j=0;
                        char ch;
                        Point1 cipher[]=new Point1[2];
                        Point1 recovered;
                        ArrayList<Point1> done=null;;
                        File encoded=new File(path1.getText());
                        File decoded=new File(path2.getText());
                        FileReader fr=new FileReader(encoded);
                        BufferedReader fbr=new BufferedReader(fr);
                        FileOutputStream fos=new FileOutputStream(decoded);
                        i=0;
                        int k=1;
                        str="";
                        while((i=fbr.read())!=-1)
                        {
                            ch=(char)i;
                            if(ch=='(')
                            {
                                k=0;
                                //str=ch+"";
                                //continue;
                            }
                            if(ch==')')
                            {
                                k=1;str=str+ch;
                                if(j<2)
                                {
                                    String tempp[]=new String[2];int g=0;
                                    tempp[0]="";tempp[1]="";
                                    for(int h=1;h<str.length()-1;h++)
                                    {
                                        if(str.charAt(h)==',')
                                        {g++;continue;}
                                        tempp[g]=tempp[g]+str.charAt(h);
                                    }
                                    cipher[j]=new Point1(new BigInteger(tempp[0]),new BigInteger(tempp[1]));
                                    j++;
                                    if(j==2)
                                    {
                                        recovered=Main1.decrypt(cipher,bob.secret,curve);
                                        done=Mapping.generateMap1(recovered,curve);
                                    }
                                    str="";
                                    continue;
                                }
                                for(j=0;j<done.size();j++)
                                {
                                    if(done.get(j).toString().equals(str))
                                    {
                                        fos.write(j+1);
                                    }
                                }
                                str="";
                            }
                            if(k==0)
                            {
                                str=str+ch;
                            }
                        }
                        fos.close();fbr.close();//System.out.println(System.nanoTime()-time);
                    }
                    JOptionPane.showMessageDialog(frame,"Process Done");
                }
                catch(Exception e1)
                {
                    JOptionPane.showMessageDialog(frame,e1);
                }
            }
        });
        output.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String ext[]=new String[3];
                String des[]=new String[3];
                ext[0]=".txt";
                ext[1]=".java";
                ext[2]=".c";
                des[0]="Text";
                des[1]="Java";
                des[2]="C";
                export_to_file fr=new export_to_file();
                File file=fr.getData(des,ext,null,frame);
                if(file!=null)
                    path2.setText(file.getAbsolutePath());
            }
        });
        outer.add(input);
        output_label=new JLabel("Output File");
        output_label.setFont(new Font("Serif",Font.BOLD,16));
        output_label.setBackground(Color.BLACK);
        output_label.setForeground(Color.RED);
        output_label.setBounds(10,150,90,20);
        outer.add(output_label);
        path2=new JTextField();
        path2.setBounds(95,150,230,20);
        outer.add(path2);
        outer.setLayout(null);
        frame=new JFrame("Encoder and Decoder based on ECC");
        frame.setJMenuBar(menubar);
        frame.setSize(440,300);
        Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2,dim.height/2-frame.getSize().height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(outer);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    class JPanel1 extends JPanel
    {
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            setBackground(Color.BLACK);
        }
    }
    public static void main(String arg[])
    {
        new encode_gui();
    }
    boolean check(String str)
    {
        int i,ln=str.length();
        char ch;
        for(i=0;i<ln;i++)
        {
            ch=str.charAt(i);
            int j=ch;
            if((j>=48&&j<=57)||(j>=97&&j<=102))
            {}
            else
            {
                JOptionPane.showMessageDialog(null,"Invalid Key");
                return false;
            }
        }
        return true;
    }
    static void write_to_file(File f,String str)throws Exception
    {
        FileWriter fw=new FileWriter(f);
        BufferedWriter bw=new BufferedWriter(fw);
        bw.write(str);
        bw.close();
    }
    static String[] read_from_file(File f)throws Exception
    {
            FileReader fr=new FileReader(f);
            BufferedReader br=new BufferedReader(fr);
            int i;char ch;String str="";
            while((i=br.read())!=-1)
            {
                ch=(char)i;
                str=str+ch;
            }
            String temp="";
            ArrayList<String> arr=new ArrayList<String>(); 
            for(i=0;i<str.length();i++)
            {
                ch=str.charAt(i);
                if(ch!=',')
                {
                    temp=temp+ch;
                }
                else
                {
                    arr.add(temp);temp="";
                }
            }
            arr.add(temp);
            String t[]=new String[arr.size()];
            for(i=0;i<arr.size();i++)
                t[i]=arr.get(i);
            return t;
        
    }
}
