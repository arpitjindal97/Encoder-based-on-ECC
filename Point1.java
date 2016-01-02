import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
class Point1
{
    BigInteger x,y;
    Point1(BigInteger x,BigInteger y)
    {
        this.x=x;
        this.y=y;
    }
    public String toString()
    {
        String str="("+this.x+","+this.y+")";
        return str;
    }
    static Point1 add1(Point1 one,Point1 two,Elliptic curve)
    {
        BigInteger lambda,den;
        if(one.x.equals(two.x)&&one.y.equals(two.y))
        {
            lambda=((new BigInteger("3")).multiply(one.x).multiply(one.x));
            lambda=lambda.add(curve.a);
            den=(new BigInteger("2")).multiply(one.y);
        }
        else
        {
            lambda=two.y.subtract(one.y);
            den=two.x.subtract(one.x);
        }
        den=den.modInverse(curve.p);
        lambda=lambda.multiply(den);
        BigInteger x3=lambda.multiply(lambda).subtract(one.x).subtract(two.x);
        BigInteger y3=lambda.multiply(one.x.subtract(x3)).subtract(one.y);
        y3=y3.mod(curve.p);
        x3=x3.mod(curve.p);
        return new Point1(x3,y3);
    }
    static Point1 multiply1(BigInteger factor,Point1 pp,Elliptic curve) 
    {
        BigInteger two = new BigInteger("2");
        factor=factor.mod(curve.p);
        if (factor.equals(BigInteger.ONE))
            return pp;
        if (factor.equals(two))
            return add1(pp,pp,curve);
        if (factor.mod(two).equals(BigInteger.ZERO)) 
        {
            Point1 sqrt = multiply1(factor.divide(two),pp,curve);
            return add1(sqrt,sqrt,curve);
        }else 
        {
            factor = factor.subtract(BigInteger.ONE);
            return add1(pp,multiply1(factor,pp,curve),curve);
        }
    }
    static Point1 subtract1(Point1 a,Point1 b,Elliptic curve)
    {
        b=new Point1(b.x,curve.p.subtract(b.y));
        return add1(a,b,curve);
    }
}
class Elliptic
{
    BigInteger p,a,b;
    Elliptic(BigInteger p,BigInteger a,BigInteger b)
    {
        int k=1;
        int i=Integer.parseInt(p.toString());
        for(int j=2;j<i;j++)
        {
            if(i%j==0)
            {
                k=0;
                break;
            }
        }
        if(k!=1)
            throw new RuntimeException();
        this.a=a;
        this.b=b;
        this.p=p;
    }
}
class Mapping
{
    public static ArrayList<Point1> generateMap1(Point1 plain,Elliptic curve)
    {
        plain.x=plain.x.mod(curve.p);
        plain.y=plain.y.mod(curve.p);
        //System.out.print('\f');
        
        ArrayList<Point1> pending=new ArrayList<Point1>();
        ArrayList<Point1> done=new ArrayList<Point1>();
        
        pending.add(plain);
        done.add(plain);
        Point1 temp=new Point1(new BigInteger("3"),new BigInteger("13"));
        int i=0,j=0;
        int max=512;
        ccc:while(pending.size()!=0)
        {
            try
            {
                temp=Point1.add1(pending.get(i),pending.get(i),curve);
                for(j=0;j<done.size();j++)
                {
                    if(done.get(j).toString().equals(temp.toString()))
                    {
                        pending.remove(i);
                        continue ccc;
                    }
                }
                done.add(temp);
                pending.add(temp);
            }
            catch(Exception e)
            {
            }
            if(done.size()==max)
                break;
            try
            {
                temp=Point1.add1(pending.get(i),pending.get(i+1),curve);
                for(j=0;j<done.size();j++)
                {
                    if(done.get(j).toString().equals(temp.toString()))
                    {
                        pending.remove(i);
                        continue ccc;
                    }
                }
                pending.add(temp);
                done.add(temp);
                if(done.size()==max)
                    break;
            }
            catch(Exception e1)
            {
                pending.remove(i);
                continue;
            }
            pending.remove(i);
        }
        pending.clear();
        //System.out.println("Size of done is : "+done.size());
        return done;
    }
}
class Main1
{
    Point1 generator,pub;
    Elliptic curve;
    BigInteger secret;
    Main1(Elliptic curve,BigInteger x,BigInteger y,BigInteger secret)
    {
        this.generator=new Point1(x,y);
        secret=secret.mod(curve.p);
        this.curve=curve;
        this.secret=secret;
        this.pub=Point1.multiply1(secret,generator,curve);
    }
    static Point1[] encrypt(Main1 alice,Point1 plain)
    {
        Point1 cipher[]=new Point1[2];
        int bits = alice.curve.p.bitLength();
        BigInteger k = new BigInteger(bits, new Random());
        //System.out.println("Picked "+k+" as k for encrypting.");
        k=k.mod(alice.curve.p);
        //try
        {
            cipher[0]=Point1.multiply1(k,alice.generator,alice.curve);
            Point1 temp=Point1.multiply1(k,alice.pub,alice.curve);
            cipher[1]=Point1.add1(plain,temp,alice.curve);
        }
        //catch(Exception e)
        {
            //System.out.println(e);
        }
        return cipher;
    }
    static Point1 decrypt(Point1 cipher[],BigInteger secret,Elliptic curve)
    {
        Point1 temp=Point1.multiply1(secret,cipher[0],curve);
        temp=Point1.subtract1(cipher[1],temp,curve);
        return temp;
    }
}
