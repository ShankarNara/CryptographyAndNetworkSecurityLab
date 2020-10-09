import java.io.*;
import java.util.*;
import java.lang.*;
import java.math.*;

class RSA{
	public int p,q;
	public int e,d,phi,n;
	// if message is integer
	public int mess,cipher;
	// if message is string
	public String mess_str;

	public RSA(int p, int q,int m){
		this.p = p;
		this.q = q;
		this.mess=m;
		n = p*q;
		phi = (p-1)*(q-1);
	}

	public int getGcd(int a, int b){
		
		while(b>0){
			int rem = a%b;
			a=b;
			b=rem;
		}

		return a;
	}

	public int FastExpo(int x,int y,int p){
		int res=1;

		x=x%p;
		while(y>0){

			if(y%2==1) res=(res*x)%p;

			y=y>>1;
			x=(x*x)%p;
		}

		return res;
	}

	public int getInverse(){
		int inv=1;

		for(;inv<=phi;inv++){
			if((inv*e)%phi==1){
				return inv;
			}
		}
		return -1;
	}

	public void keygen(){
		System.out.println("n = p*q = "+n);
		System.out.println("phi = (p-1)*(q-1) = "+phi);

		//generating random element e
		Random rand = new Random();
		e=phi;
		while(getGcd(phi,e)!=1){
			e = rand.nextInt(phi);
			if(e<=0) e=2;
		}

		System.out.println("e = "+e);

		d = getInverse();
		System.out.println("d = inverse of e = "+d);

		System.out.println("Public key = ("+e+","+n+")");
		System.out.println("Private key = ("+d+","+n+")");
	}

	public void encrypt(){
		cipher = FastExpo(mess,e,n);

		System.out.println("\nEncrypted message : "+cipher);
	}

	public void decrypt(){
		int m = FastExpo(cipher,d,n);

		System.out.println("\nDecrypted message : "+m);
	}

}

public class RSADriver{

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

		int m;
		int p,q;
		System.out.println("Enter a message : ");
		m = in.nextInt();
		System.out.println("Enter prime numbers p, q");
		p = in.nextInt();
		q = in.nextInt();

		RSA rsa = new RSA(p,q,m);

		rsa.keygen();

		rsa.encrypt();

		rsa.decrypt();

	}
}