import java.io.*;
import java.lang.*;
import java.util.*;

public class CaesarCipher{

	public static StringBuffer encrypt(String plaintext,int key,boolean dec){
		StringBuffer ans= new StringBuffer();

		if(dec) {
			key = -key;
		}
		int n = plaintext.length();
		for(int i=0;i<n;i++){
			char ch = plaintext.charAt(i);
			if (ch == ' ') {
				ans.append(' ');
				continue;
			}
			int no = ch-'a';
			char an = (char)((no+key+26)%26 + 97);
			 
			ans.append(an);
		}

		return ans;
	}

	//function to apply brute-force cryptanalysis on the ciphertext
	public static void cryptAnalysis(StringBuffer ctext){
		String[] list ={"ab", "ad", "ah", "am","an", "as", "at", "au","be", "by", "do", 
						"er","go", "ha", "hi", "is","it", "my", "no", "of","on","or","re","to","we","ya","be","ba","at","re"};
		StringBuffer cur,mx;
		int cnt,mxcnt=0;
		int n = 30;
		int key=0;
		mx = new StringBuffer();

		System.out.println("\nCryptanalysis : \n\nDisplaying dictionary : \n");
		for(int i=0;i<30;i++){
			System.out.print(list[i]+" ");
		}
		System.out.println("\n");

		for (int k=1;k<26;k++){
			cnt=0;
			String text = ctext.toString();
			cur = encrypt(text,k,true);
			System.out.println(cur);

			for(int i=0;i<n;i++){
				String str = cur.toString();
				if(str.contains(list[i])){
					cnt++;
				}
			}

			if(cnt > mxcnt){
				//System.out.println("cur max = "+cnt);
				mxcnt = cnt;
				mx = cur;
				key=k;
			}
		}

		System.out.println("\n\nAfter cryptanalysis : \nKey = "+(key/2)+"\nDecrypted String : "+mx);

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
		StringBuffer encr = encrypt(plaintext,key,false);

		System.out.println(encr+"\n");
		

		System.out.println("Decrypted message : ");
		System.out.println(encrypt(encr.toString(),key,true)+"\n");

		// System.out.println("Enter message for cryptanalysis: ");
		// plaintext = in.nextLine();
		
		// System.out.println("Enter the key : ");
		// key = in.nextInt();

		encr = encrypt(encr.toString(),key,false);

		cryptAnalysis(encr);

	}
}