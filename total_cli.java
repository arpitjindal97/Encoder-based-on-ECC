import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.ArrayList;
public class total_cli
{
    static Main1 assign(BigInteger defined[],Main1 bob)
    {
        Main1 temp=new Main1((new Elliptic(bob.curve.p,bob.curve.a,bob.curve.b)),bob.generator.x,bob.generator.y,bob.secret);
        try
        {
            Point1 plain=new Point1(new BigInteger("14949"),new BigInteger("498419"));
            bob.curve.p=(defined[0]);
            bob.curve.a=(defined[1]);
            bob.curve.b=(defined[2]);
            bob.generator.x=(defined[3]);
            bob.generator.y= (defined[4]);
            bob.secret= (defined[5]);
            bob.pub=Point1.multiply1(bob.secret,bob.generator,bob.curve);
            ArrayList<Point1> done=Mapping.generateMap1(plain,bob.curve);
            if(done.size()!=512)
                throw new RuntimeException();
            return bob;
        }
        catch(Exception e)
        {
            System.out.println("Invalid configuration given");
            return temp;
        }
    }
    static void clrscr()throws Exception
    {
        new ProcessBuilder("cmd.exe","/c","cls").inheritIO().start().waitFor();
    }
    static String[] getArray(String str)
    {
        int j=0,k=0;String array[]={"","","",""};
        for(int i=0;i<str.length();i++)
        {
            char ch=str.charAt(i);
            if(ch==' '&&i==0)
                return null;
            if(ch==' '&&j==4)
                return null;
            if(ch=='\"')
            {
                if(k==0)
                    k=1;
                else
                    k=0;
                continue;
            }
            if(ch==' '&&k==0)
            {
                if(j==3)
                    return null;
                j++;continue;
            }
            array[j]=array[j]+ch;
        }
        return array;
    }
    static void print_details(Main1 bob)
    {
        System.out.println("         P = "+bob.curve.p+"\n         a = "+bob.curve.a+"\n         b = "
        +bob.curve.b+
        "\n     gen_x = "+bob.generator.x+"\n     gen_y = "+
        bob.generator.y+"\nsecret_key = "+bob.secret+"\nPublic Key = "+bob.pub+"");
    }
    static BigInteger[] fillArray(Main1 bob)
    {
        BigInteger values[]=new BigInteger[6];
        values[0]=bob.curve.p;
        values[1]=bob.curve.a;
        values[2]=bob.curve.b;
        values[3]=bob.generator.x;
        values[4]=bob.generator.y;
        values[5]=bob.secret;
        return values;
    }
    public static void main(String arg[])throws Exception
    {
        System.out.println("-----Encoder and Decoder based on ECC created by Arpit-----\n");
        BigInteger p=new BigInteger("599");
        BigInteger a=new BigInteger("545353568684684");
        BigInteger b=new BigInteger("74353518449494997979");
        BigInteger gen_x=new BigInteger("434531");
        BigInteger gen_y=new BigInteger("7345355");
        Elliptic curve=new Elliptic(p,a,b);
        Point1 plain=new Point1(new BigInteger("14949"),new BigInteger("498419"));
        Main1 bob=new Main1(curve,gen_x,gen_y,new BigInteger("7345351"));
        String defined[]={"p","a","b","gen_x","gen_y","secret_key"};
        InputStreamReader isr=new InputStreamReader(System.in);
        BufferedReader br=new BufferedReader(isr);
        String str;
        String array[];
        str="fkvbks";
        while(!str.equals("exit"))
        {
            System.out.print("\\arpit\\encoder\\cli>");
            str=br.readLine();
            array=getArray(str);
            if(str.equals(""))
                continue;
            else if(array==null)
            {
                System.out.println("Wrong command");
            }
            else if(array[0].equals("set")&&array[3].equals(""))
            {
                BigInteger values[]=fillArray(bob);
                str="fail";
                for(int i=0;i<6;i++)
                {
                    if(defined[i].equals(array[1]))
                    {
                        str="success";
                        try
                        {
                            values[i]=new BigInteger(array[2]);
                        }
                        catch(Exception e)
                        {
                            System.out.println("Invalid Configuration");
                            break;
                        }
                        bob=assign(values,bob);
                        break;
                    }
                }
                if(str.equals("fail"))
                    System.out.println("No matching variable found");
            }
            else if(array[0].equals("export")&&array[3].equals(""))
            {
                if(array[1].equals("config"))
                {
                    str=bob.curve.p.toString()+","+bob.curve.a.toString()+
                    ","+bob.curve.b.toString()+","+bob.generator.x.toString()+
                    ","+bob.generator.y.toString()+","+bob.secret.toString();
                    encode_gui.write_to_file(new File(array[2]),str);
                }
                else if(array[1].equals("key"))
                {
                    str=bob.curve.p.toString()+","+bob.curve.a.toString()+
                    ","+bob.curve.b.toString()+","+bob.generator.x.toString()+
                    ","+bob.generator.y.toString()+",";
                    String temp=bob.pub.toString();
                    for(int h=1;h<temp.length()-1;h++)
                        str=str+temp.charAt(h);
                    encode_gui.write_to_file(new File(array[2]),str);
                }
                else
                {
                    System.out.println("Wrong command");
                }
            }
            else if(array[0].equals("import")&&array[1].equals("config")&&array[3].equals(""))
            {
                String values[]=encode_gui.read_from_file(new File(array[2]));
                if(values.length!=6)
                {
                    System.out.println("File corrupted");
                }
                BigInteger values1[]=new BigInteger[6];
                try
                {
                    for(int i=0;i<6;i++)
                        values1[i]=new BigInteger(values[i]);
                    bob=assign(values1,bob);
                }
                catch(Exception e)
                {
                    System.out.println("File Corrupted");
                }
            }
            else if(array[0].equals("cls")&&array[1].equals("")&&array[2].equals("")&&array[3].equals(""))
            {clrscr();
            }
            else if(array[0].equals("exit"))
            {continue;
            }
            else if(array[0].equals("show")&& array[1].equals("options"))
                print_details(bob);
            else if(array[0].equals("encrypt"))
            {
                try
                {
                    encrypt(plain,array[1],array[2],array[3]);
                    System.out.println("Process Done");
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
            }
            else if(array[0].equals("decrypt")&&array[3].equals(""))
            {
                decrypt(bob,array[1],array[2]);
                System.out.println("Process Done");
            }
            else if(array[0].equals("start")&&array[1].equals("gui")&&array[2].equals("")&&array[3].equals(""))
            {
                File ff=new File(total_cli.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
                if(ff.getName().toLowerCase().endsWith(".jar"))
                    new ProcessBuilder("java","-cp",ff.getName(),"encode_gui").start();
                else
                    new ProcessBuilder("java","","","encode_gui").start();
            }
            else if(array[0].equals("help")&&array[1].equals("")&&array[2].equals("")&&array[3].equals(""))
            {
                System.out.println("   import config <path_to_file>                    "+"\n"+
                                    "   export config <path_to_file>    "+"\n"+
                                    "   export key <path_to_file>       "+"\n"+
                                    "   cls                     "+"\n"+
                                    "   set <variable_name> <new_value>  "+"\n"+
                                    "   start gui               "+"\n"+
                                    "   encrypt <key_file> <input_file> <output_file>"+"\n"+
                                    "   decrypt <input_file> <output_file>");
            }
            else
            {
                System.out.println("Wrong command");
            }
            System.out.println();
        }
    }
    static void encrypt(Point1 plain,String key,String input,String output)throws Exception
    {
        String values[]=encode_gui.read_from_file(new File(key));
        Elliptic curve1=new Elliptic(new BigInteger(values[0]),new BigInteger(values[1]),new BigInteger(
        values[2]));
        Main1 alice=new Main1(curve1,new BigInteger(values[3]),new BigInteger(values[4]),
        new BigInteger("3164"));
        alice.pub.x=new BigInteger(values[5]);
        alice.pub.y=new BigInteger(values[6]);
        plain.x=plain.x.mod(curve1.p);
        plain.y=plain.y.mod(curve1.p);
        ArrayList<Point1> done=Mapping.generateMap1(plain,curve1);
        Point1 cipher[]=Main1.encrypt(alice,plain);
        String str;
        File arpit=new File(input);
        File encoded=new File(output);
        FileWriter fos=new FileWriter(encoded);
        BufferedWriter bw=new BufferedWriter(fos);
        bw.write(cipher[0]+""+cipher[1]);
        FileInputStream fis =new FileInputStream(arpit);
        int i=0;
        while((i=fis.read())!=-1)
        {
            str=done.get(i-1).toString();
            bw.write(str+"");
        }
        fis.close();
        bw.close();
    }
    static void decrypt(Main1 alice,String input,String output)throws Exception
    {
        int i=0,j=0;
        char ch;
        Point1 cipher[]=new Point1[2];
        Point1 recovered;
        ArrayList<Point1> done=null;;
        File encoded=new File(input);
        File decoded=new File(output);
        FileReader fr=new FileReader(encoded);
        BufferedReader fbr=new BufferedReader(fr);
        FileOutputStream fos=new FileOutputStream(decoded);
        i=0;
        int k=1;
        String str="";
        while((i=fbr.read())!=-1)
        {
            ch=(char)i;
            if(ch=='(')
            {
                k=0;
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
                        recovered=Main1.decrypt(cipher,alice.secret,alice.curve);
                        done=Mapping.generateMap1(recovered,alice.curve);
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
        fos.close();fbr.close();
    }
}
