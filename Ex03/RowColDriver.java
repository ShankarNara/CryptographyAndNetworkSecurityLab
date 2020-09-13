import java.io.*;
import java.lang.*;
import java.util.*;

class RowColCipher{

	String ptext;
	int[] key;
	StringBuffer ctext;
	char[][] mat;
	int m,row, col;

	public RowColCipher(String p, int[] k,int l){
		System.out.println("constructor");
		this.ptext = p;
		this.key = k;
		this.m = l;
		ctext = new StringBuffer();
		mat = new char[1000][1000];
		row=0;
		col=0;

		for(int i=0;i<1000;i++){
			for(int j=0;j<1000;j++){
				mat[i][j]='0';
			}
		}	

	}


	public void encrypt(){
		System.out.println("encrypt");
		int i,j,n;
		n = ptext.length();

		int k=0;
		i=0;
		j=0;
		while(k<n){
			mat[i][j] = ptext.charAt(k);
			k++;
			j++;

			if(j>=m){
				j=0;
				i++;
			}
		}

		while(j<m){
			mat[i][j]=' ';
			j++;
		}

		row=i+1;
		col=m;

		//displaying the row col table
		for(i=0;i<m;i++){
			System.out.print(key[i]+" ");
		}
		System.out.println("\n");

		for(i=0;i<row;i++){
			for(j=0;j<col;j++){
				System.out.print(mat[i][j]+" ");
			} System.out.print("\n");
		}System.out.print("\n");

		//getting cipher text from row col table

		for(i=1;i<=m;i++){
			int ind=-1;
			for(j=0;j<m;j++){
				if(key[j]==i) {
					ind=j;
					break;
				}
			}

			for(j=0;j<row;j++){
				char ch = mat[j][ind];
				ctext.append(ch);
			}				
		}



		System.out.println("The encrypted ciphertext : "+ctext);
	}

	public void decrypt(){
		int i,j,n;
		n = ctext.length();

		//clearing the matrix mat[][] for decryption

		int k=0;

		for(i=1;i<=m;i++){
			int ind=-1;

			for(j=0;j<m;j++){
				if(key[j]==i) ind = j;
			}

			for(j=0;j<row;j++){
				mat[j][ind]=ctext.charAt(k);
				k++;
			}
		}

		//reading the matrix row-wise to get the plaintext back 

		StringBuffer decr = new StringBuffer();
		for(i=0;i<row;i++){
			for(j=0;j<col;j++){
				decr.append(mat[i][j]);
			}
		}

		System.out.println("\n\nThe decrypted message : "+decr);
	}


}

public class RowColDriver{

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

		String ptext;
		int i,m;
		int[] key = new int[1000];

		System.out.println("Enter the plaintext : ");
		ptext = in.nextLine();

		System.out.println("Enter the key length  : ");
		m = in.nextInt();
		//System.out.println(m);

		System.out.println("Enter the key  : ");
		for(i=0;i<m;i++){
			int a;
			a = in.nextInt();
			key[i] = a;
			//System.out.println(a);
		}

		//check for key validity here 

		//--------------------

		RowColCipher rcc = new RowColCipher(ptext, key, m);

		rcc.encrypt();

		rcc.decrypt();

	}
}