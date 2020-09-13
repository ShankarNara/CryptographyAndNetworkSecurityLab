import java.io.*;
import java.lang.*;
import java.util.*;

class RailFenceCipher{

	public String ptext;
	public int key;
	public char[][] mat;
	public StringBuffer ctext;

	public RailFenceCipher(String p, int k){
		this.ptext = p;
		this.key = k;
		mat = new char[1000][1000];
		ctext = new StringBuffer();

		for(int i=0;i<1000;i++){
			for(int j=0;j<1000;j++){
				mat[i][j]='0';
			}
		}
	}

	public void encrypt(){
		int row=0, col=0;
		boolean down_dir=false;
		int n,i,j;
		n = ptext.length();

		for(i=0;i<n;i++){

			if(row==0 || row==key-1) down_dir=!down_dir;

			char ch = ptext.charAt(i);
			mat[row][col]= ch;

			if(down_dir) row++;
			else row--;
			col++;
		}

		for(i=0;i<key;i++){
			for(j=0;j<n+1;j++){
				if(mat[i][j] != '0')
					ctext.append(mat[i][j]);
			}
		}
	}

	public void decrypt(){
		int n,i,j;
		n = ptext.length();

		for(i=0;i<1000;i++){
			for(j=0;j<1000;j++){
				mat[i][j]='0';
			}
		}

		int row=0, col=0;
		boolean down_dir=false;
		for(i=0;i<n;i++){

			if(row==0 || row==key-1) down_dir=!down_dir;

			mat[row][col] = '*';

			if(down_dir) row++;
			else row--;
			col++;
		}

		int k=0;
		for(i=0;i<key;i++){
			for(j=0;j<=n;j++){
				if(mat[i][j]=='*'){
					char ch = ctext.charAt(k);
					mat[i][j] = ch;
					k++;
				}
			}
		}

		down_dir=false;
		StringBuffer decr = new StringBuffer();
		row=0;
		col=0;
		for(i=0;i<n;i++){
			
			if(row==0 || row==key-1) down_dir=!down_dir;

			decr.append(mat[row][col]);

			if(down_dir) row++;
			else row--;
			col++;
		}

		System.out.println("The decrypted plaintext : "+decr);	
	}	

}

public class RFDriver{
	
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

		String ptext;
		int key;

		System.out.println("Enter the plaintext string (small letters) : ");
		ptext = in.nextLine();

		System.out.println("Enter the key value : ");
		key = in.nextInt();

		RailFenceCipher rfc = new RailFenceCipher(ptext, key);

		rfc.encrypt();

		System.out.println("The cipher text : "+rfc.ctext);

		rfc.decrypt();
	}
}