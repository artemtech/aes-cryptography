package aes;

/**
 * @author sofyan_sugianto
 * @author putri_kharisma
 * @author fatimatus_zahro
 */

public class Main {

    private static final String banner = "AES CRYPTOGRAPHY PROGRAM\n"
      + "------------------------\n"
      + "1. ENCRYPT\n"
      + "2. DECRYPT\n"
      + "------------------------\n"
      + "Pilih (1/2) ? ";

    public static void main(String[] args) {
        AES aes = new AES();
        System.out.println(banner);
        String text = "Hello World";
        String kunci = "hai";
        System.out.println("plain text: "+text);
        String hasilEnkripsi = aes.encrypt(text, kunci);
        System.out.println("enkrypted="+hasilEnkripsi);
        String hasilDekripsi = aes.decrypt(hasilEnkripsi, kunci);
        System.out.println("dekripted="+hasilDekripsi);
    }

}

