import java.io.*;
import java.lang.*;
import java.util.*;

public class CaesarCipher{

	public static StringBuffer encrypt(String plaintext,int key){
		StringBuffer ans= new StringBuffer();

		int n = plaintext.length();
		for(int i=0;i<n;i++){
			char ch = plaintext.charAt(i);
			ch+= key;

			ans.append(ch);
		}

		return ans;
	}

	public static StringBuffer decrypt(StringBuffer ciphertext, int key){
		StringBuffer ans=new StringBuffer();

		int n=ciphertext.length();
		for(int i=0;i<n;i++){
			char ch = ciphertext.charAt(i);
			ch-= key;

			ans.append(ch);
		}

		return ans;

	}

	public static void main(String[] args){

		String plaintext;
		int key;

		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter the plaintext : ");
		plaintext = in.nextLine();
		
		System.out.println("Enter the key : ");
		key = in.nextInt();

		System.out.println("Encrypted message : ");
		StringBuffer encr = encrypt(plaintext,key);
		StringBuffer ans = new StringBuffer();
		int n = encr.length();
		for(int i=0;i<n;i++) {
			if(encr.charAt(i) != '#'){
				ans.append(encr.charAt(i));	
			} else {
				ans.append(' ');
			}
		}

		System.out.println(ans);
		

		System.out.println("Decrypted message : ");
		System.out.println(decrypt(encr,key));

	}
}