import java.io.*;
import java.lang.*;
import java.util.*;
import java.math.*;

class DHKE{
	BigInteger p,g,phi;
	BigInteger prA,prB,yA,yB;

	public DHKE(){

		int limit =20;
		String prime=new String("6");
		Random rand = new Random();
		//generating the prime numbers P,Q
		p = new BigInteger(prime);
		while(!MillerRabinTest(p)){
			prime=new String();
			for(int i=0;i<limit;i++){
				int dig = rand.nextInt(10);
				char ch = (char)(48+dig);
				prime = prime+ch;
			}

			p = new BigInteger(prime,10);
		}

		System.out.println("\nGenerated prime number P : "+p.toString(16).toUpperCase());

		g = getPrimitiveRoot();

		System.out.println("\nChosen primitive root : "+g.toString(16).toUpperCase());
	}

	public boolean returnPrime(BigInteger number) {
	    //check via BigInteger.isProbablePrime(certainty)
	    if (!number.isProbablePrime(5))
	        return false;

	    //check if even
	    BigInteger two = new BigInteger("2");
	    if (!two.equals(number) && BigInteger.ZERO.equals(number.mod(two)))
	        return false;

	    //find divisor if any from 3 to 'number'
	    for (BigInteger i = new BigInteger("3"); i.multiply(i).compareTo(number) < 1; i = i.add(two)) { //start from 3, 5, etc. the odd number, and look for a divisor if any
	        if (BigInteger.ZERO.equals(number.mod(i))) //check if 'i' is divisor of 'number'
	            return false;
	    }
	    return true;
	}

	boolean MillerRabinTest(BigInteger p){
	    BigInteger r = p.mod(new BigInteger("2"));
	    if(r.equals(new BigInteger("0"))){
	      return false;
	    }
	    BigInteger x = p.add(new BigInteger("-1"));
	    BigInteger q;
	    BigInteger k = new BigInteger("-1");
	    BigInteger pow2 = new BigInteger("1");
	    do{
	      k = k.add(k);
	      q = x.divide(pow2);
	      pow2 = pow2.multiply(new BigInteger("2"));
	    }while(q.mod(new BigInteger("2")).equals(new BigInteger("0")));
	    Random rnd = new Random();
	    BigInteger a;
	    do{
	      a = new BigInteger(x.bitLength(),rnd);
	    }while(a.compareTo(new BigInteger("1"))>0 && a.compareTo(x)<0);
	    BigInteger res = a.modPow(q,p);
	    if(res.equals(new BigInteger("1")))
	    return true;
	    BigInteger j = new BigInteger("0");
	    pow2 = new BigInteger("1");
	    while(j.compareTo(k)<0){
	      pow2 = pow2.multiply(new BigInteger("2"));
	      res = a.modPow(pow2.multiply(q),p);
	      j = j.add(new BigInteger("1"));
	      if(res.equals(x))
	      return true;
	    }
	    return false; 
  	}

	//Code to get the primitive roots
	public BigInteger getPrimitiveRoot(){
		BigInteger[] fact = new BigInteger[10000];
		phi = p.add(BigInteger.valueOf(-1));
		BigInteger i,n=phi;
		//System.out.println("flag");

		int k=0;
		for(i=BigInteger.valueOf(2);i.multiply(i).compareTo(phi)<=0;i=i.add(BigInteger.valueOf(1))){
			//System.out.println("i = "+i.toString());
			if(phi.mod(i).equals(BigInteger.ZERO)){
				fact[k++]=i;

				while(phi.mod(i).equals(BigInteger.ZERO)){
					//System.out.println("flag");
					phi = phi.divide(i);
				}
			}
		}

		if(phi.compareTo(BigInteger.ONE) > 0)
			fact[k++]=phi;

		System.out.println("\nDisplaying factors of P-1 : ");
		for(int it=0;it<k;it++){
			System.out.print(fact[it].toString()+" ");
		}
		System.out.println("\n\n");

		for(BigInteger res=BigInteger.valueOf(2);res.compareTo(p)<=0;res=res.add(BigInteger.ONE)){
			boolean ok=true;

			for(int it=0;it<k;it++){
				BigInteger temp = res.modPow(n.divide(fact[it]),p);
				ok = ok & (temp.compareTo(BigInteger.ONE) != 0);
			}

			if(ok) return res;
		}

		return BigInteger.valueOf(-1);
	}

	public void generateKey(){
		BigInteger xA,xB;
		//int lim = p.intValue();

		Random rand = new Random();
		xA = BigInteger.valueOf(7);
		//System.out.println("lim = "+lim);
		//int xa = rand.nextInt(lim);
		//if(xa<=0) xa=1;
		//xA = BigInteger.valueOf(xa);
		do {
			xA = new BigInteger(5199,rand);
			break;
		} while(xA.compareTo(p)>0);
		prA = g.modPow(xA,p);

		// int xb = rand.nextInt(lim);
		// if(xb<=0) xb=1;
		xB = BigInteger.valueOf(134);
		do {
			xB = new BigInteger(5,rand);
			break;
		}while(xB.compareTo(p)<0);
		prB = g.modPow(xB,p);

		System.out.println("Private key of A : "+prA.toString(16).toUpperCase()+
							"\nPrivate key of B : "+prB.toString(16).toUpperCase()+"\n\n");
		System.out.println("A sends her private key "+prA.toString(16).toUpperCase()+" to B");
		System.out.println("B sends his private key "+prB.toString(16).toUpperCase()+" to A\n\n");

		yA = prB.modPow(xA,p);
		yB = prA.modPow(xB,p);

		System.out.println("A generates key "+yA.toString(16).toUpperCase());
		System.out.println("B generates key "+yB.toString(16).toUpperCase());

		System.out.println("\n\n\nShared key =  "+yA.toString(16).toUpperCase());
	}

}

public class DHKEDriver{

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

		DHKE dhke = new DHKE();

		dhke.generateKey();
	}

}























