package aes;

import java.util.Scanner;

/**
 * @author sofyan_sugianto
 * @author putri_kharisma
 * @author fatimatus_zahro
 */

public class Main {

    private static final String banner = "AES-128 CRYPTOGRAPHY PROGRAM\n"
      + "------------------------\n"
      + "1. ENCRYPT\n"
      + "2. DECRYPT\n"
      + "------------------------\n"
      + "Pilih (1/2) ? ";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int pilihan;
        String exitCode = "y";
        String text[] = {"",""};
        String kunci = "";
        String hasilEnkripsi = "";
        String hasilDekripsi = "";
        AES aes = new AES();
        while (exitCode.equalsIgnoreCase("y")){
            System.out.print(banner);
            pilihan = input.nextInt();
            while(String.valueOf(pilihan).length() > 1 || pilihan > 2 || pilihan < 1){
                System.err.println("Input tidak valid!!!");
                System.out.print("Pilih (1/2) ? ");
                pilihan = input.nextInt();
            }
            input.nextLine();
            if (pilihan == 1) {
                System.out.print("Masukkan plaintext: ");
                text[0] = input.nextLine();
                while(text[0].length() > 16){
                    System.err.println("Oops, teks terlalu panjang, masih belum didukung untuk lebih dari 16 karakter!!!");
                    System.out.print("Masukkan plaintext: ");
                    text[0] = input.nextLine();
                }
                System.out.print("Masukkan kunci (teks biasa): ");
                kunci = input.nextLine();
                while(kunci.length() > 16){
                    System.err.println("Oops, kunci terlalu panjang, masih belum didukung untuk lebih dari 16 karakter!!!");
                    System.out.print("Masukkan kunci: ");
                    text[0] = input.nextLine();
                }
                hasilEnkripsi = aes.encrypt(text[0], kunci);
                System.out.println("Plaintext="+aes.text2Hex(text[0]));
                System.out.println("enkrypted="+hasilEnkripsi);
            }
            else if(pilihan == 2){
                System.out.print("Masukkan ciphertext (hexa tanpa spasi): ");
                text[1] = input.nextLine();
                while(text[1].length() > 32){
                    System.err.println("Oops, teks terlalu panjang, masih belum didukung untuk lebih dari 16 bytes!!!");
                    System.out.print("Masukkan plaintext: ");
                    text[1] = input.nextLine();
                }
                System.out.print("Masukkan kunci (teks biasa): ");
                kunci = input.nextLine();
                while(kunci.length() > 16){
                    System.err.println("Oops, kunci terlalu panjang, masih belum didukung untuk lebih dari 16 karakter!!!");
                    System.out.print("Masukkan kunci: ");
                    text[1] = input.nextLine();
                }
                hasilDekripsi = aes.decrypt(text[1], kunci);
                System.out.println("dekripted="+hasilDekripsi);
                //cek apakah string plaintext sesuai dengan hasil dekripsi
                if (hasilDekripsi.equalsIgnoreCase(aes.text2Hex(text[0]))) {
                    System.out.println("VALID!");
                }else{
                    System.out.println("TIDAK VALID!!");
                }
            }
            System.out.print("Lagi (y/n)?");
            exitCode = input.next();
        }
    }
}

