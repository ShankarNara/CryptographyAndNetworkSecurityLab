import java.util.*;
import java.io.*;
import java.lang.*;

class VigenereCipher{

  public String ptext;
  public String key;
  public StringBuffer keyT, encr;

  public VigenereCipher(String k, String p){
    this.ptext = p;
    this.key = k;
  }

  public void generateKey(){
    keyT = new StringBuffer();

    int i,n = ptext.length();
    int m = key.length();
    for(i=0;i<n;i++){
      keyT.append(key.charAt(i%m));
    }
  }

  public void encrypt(){
    int i,n;
    n = ptext.length();

    encr = new StringBuffer();

    for(i=0;i<n;i++){
      int a = ptext.charAt(i) - 'a';
      int b = keyT.charAt(i) - 'a';

      char cur = (char)(97+(a+b)%26);
      encr.append(cur);
    }
  }

  public void decrypt(){
    int i,n;
    n = ptext.length();

    StringBuffer decr = new StringBuffer();

    for(i=0;i<n;i++){
      int a = encr.charAt(i) - 'a';
      int b = keyT.charAt(i) - 'a';

      char cur = (char)(97 + (a-b+26)%26);

      decr.append(cur);
    }

    //displaying decrypted message 

    System.out.println("Decrypted message : "+decr);
  }

  public void displayTable(){
    System.out.println("\n\nDisplaying Vigenere Table : ");

    for(int i=0;i<26;i++){
      for(int j=i;j<i+26;j++){
        System.out.print((char)(97+j%26)+" ");
      }
      System.out.print("\n");
    }

    System.out.println("\n");
  }
}

public class VigDriver{

  public static void main(String[] args){
    Scanner in = new Scanner(System.in);

    String key, ptext;
    String ctext;

    System.out.println("Enter the Plain text (small characters only) : ");
    ptext = in.nextLine();

    System.out.println("Enter the key (small characters only) : ");
    key= in.nextLine();

    VigenereCipher vc = new VigenereCipher(key, ptext);

    vc.displayTable();
    
    vc.generateKey();
    System.out.println("Generated key : "+vc.keyT);

    vc.encrypt();
    System.out.println("Encrypted text : "+vc.encr);

    vc.decrypt();

  }
}