import java.io.*;
import java.lang.*;
import java.util.*;

class PlayfairCipher{

	public String key;
	public String ptext;
	public char[][] keyT;
	//keyn is the prepared text of the plaintext 
	public StringBuffer keyn;
	public int x1,x2,y1,y2;

	public PlayfairCipher(String k,String p){
		this.key = k;
		this.ptext = p;
		keyn = new StringBuffer();
		keyT = new char[5][5];
	}

	public void generateKey(){
		int[] rem = new int[27];
		int i,j;
		for(i=0;i<26;i++){
			rem[i]=1;
		} 

		i=0; j=0;
		int k=0;
		int ks = key.length();
		for(k=0;k<ks;k++){
			int ind = key.charAt(k)-'a';
			//System.out.println(key.charAt(k));
			if(rem[ind] == 1){
				if(ind==8 || ind==9) {
					keyT[i][j] = 'i';
					rem[8]=0;
					rem[9]=0;
				}
				else {
					keyT[i][j] = key.charAt(k);
				}
				rem[ind]=0;
				j++;

				if(j==5){
					i++;
					j=0;
				}
			}
		}

		for(k=0;k<26;k++){
			if(rem[k] != 0){
				if(k==9) continue;
				char ch = (char)(97+k);
				keyT[i][j] = ch;
				j++;

				if(j==5){
					i++;
					j=0;
				}
			}
		}
	}

	public void displayKeyT(){
		System.out.println("Displaying Key Table : ");

		for(int i=0;i<5;i++){
			for(int j=0;j<5;j++){
				System.out.print(keyT[i][j]+" ");
			}
			System.out.println();
		}
	}

	public void prepareText(){
		int i=0;

		int n = ptext.length();
		while(i < n){
			if(i == n-1){
				keyn.append(ptext.charAt(n-1));
				keyn.append('x');
				break;
			}

			char a,b;
			a = ptext.charAt(i);
			b = ptext.charAt(i+1);

			if(a==b){
				keyn.append(a);
				keyn.append('x');
				i+=1;
			} else {
				keyn.append(a);
				keyn.append(b);
				i+=2;
			}


		}
	}

	public void search(char a, char b){
		int i,j;

		for(i=0;i<5;i++){
			for(j=0;j<5;j++){

				if(keyT[i][j] == a){
					x1=i;
					y1=j;
				}

				if(keyT[i][j] == b){
					x2=i;
					y2=j;
				}
			}
		}
	}

	public StringBuffer encrypt(boolean decrypt, StringBuffer pt){
		int n,i,j;
		n = pt.length();
		StringBuffer ans = new StringBuffer();

		int p=1;
		if(decrypt) p=-1;

		for(i=0;i<n;i+=2){
			char a,b;
			a = pt.charAt(i);
			b = pt.charAt(i+1);

			search(a,b);

			if(x1 == x2){
				ans.append(keyT[x1][(y1+p+5)%5]);
				ans.append(keyT[x2][(y2+p+5)%5]);
			} else if(y1 == y2){
				ans.append(keyT[(x1+p+5)%5][y1]);
				ans.append(keyT[(x2+p+5)%5][y2]);
			} else {
				ans.append(keyT[x1][y2]);
				ans.append(keyT[x2][y1]);
			}
		}

		return ans;
	}
}

public class PFDriver{
	
	public static void main(String []args){

		String key,ptext;
		Scanner in = new Scanner(System.in);

		System.out.println("Enter the plaintext : ");
		ptext = in.nextLine();
		
		System.out.println("Enter the key : ");
		key = in.nextLine();

		PlayfairCipher pf = new PlayfairCipher(key, ptext);

		pf.prepareText();

		System.out.println("\n\nprepared text : "+pf.keyn);

		pf.generateKey();

		pf.displayKeyT();

		StringBuffer encr = pf.encrypt(false,pf.keyn);
		System.out.println("Encrypted message : "+encr);

		StringBuffer decr = pf.encrypt(true,encr);
		System .out.println("Decrypted message : "+decr);

	}
}