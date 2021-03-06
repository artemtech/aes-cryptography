package aes;

/**
 * @author sofyan_sugianto
 * @author putri_kharisma
 * @author fatimatus_zahro
 * 
 * Referensi pengerjaan: AES Cryptography -> What's Creel (youtube)
 * Referensi tabel: Wikipedia
 */

public class AES {
    private static final int Nk = 4; // panjang kunci tiap blok
    private static final int Nb = 4; // panjang blok teks
    private static final int Nr = 10; // banyak putaran yang harus dilakukan

     /**
     * AES. Advanced Encryption Standard - 128
     */
    public AES(){
    }
    
    /** Tabel SBox.
     * digunakan untuk substitusi state saat menjalankan perintah {@link #subBytes(int[][]) } 
     */
    public static final int[][] SBOX = {
        /*0    1      2     3     4      5     6     7     8    9     A     B      C    D     E     F  */
   /*0*/{0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76}, 
   /*1*/{0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0}, 
   /*2*/{0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15}, 
   /*3*/{0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75}, 
   /*4*/{0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84}, 
   /*5*/{0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf}, 
   /*6*/{0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8}, 
   /*7*/{0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2}, 
   /*8*/{0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73}, 
   /*9*/{0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb}, 
   /*A*/{0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79}, 
   /*B*/{0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08}, 
   /*C*/{0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a}, 
   /*D*/{0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e}, 
   /*E*/{0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf}, 
   /*F*/{0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16}};
    
    /** Tabel invers Sbox.
     * digunakan untuk melakukan substitusi state saat menjalankan perintah {@link #invSubBytes(int[][]) }
     */
    public static final int[][] INV_SBOX = {
        /*0    1      2     3     4      5     6     7     8    9     A     B      C    D     E     F  */
   /*0*/{0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb}, 
   /*1*/{0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb}, 
   /*2*/{0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e}, 
   /*3*/{0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25}, 
   /*4*/{0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92}, 
   /*5*/{0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84}, 
   /*6*/{0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06}, 
   /*7*/{0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b}, 
   /*8*/{0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73}, 
   /*9*/{0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e}, 
   /*A*/{0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b}, 
   /*B*/{0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4}, 
   /*C*/{0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f}, 
   /*D*/{0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef}, 
   /*E*/{0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61}, 
   /*F*/{0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d}};
    
    /** Tabel Galois. 
     *  sebagai referensi menggunakan tabel referensi mana dalam operasi {@link #mixColumn(int[][]) }
     */
    public static final int[][] GALOIS = {
        {0x02, 0x03, 0x01, 0x01},
        {0x01, 0x02, 0x03, 0x01},
        {0x01, 0x01, 0x02, 0x03},
        {0x03, 0x01, 0x01, 0x02}
    };
    
    /** Tabel Invers Galois.
     * sebagai referensi menggunakan tabel referensi mana dalam operasi {@link #invMixColumn(int[][]) }
     */
    public static final int[][] INV_GALOIS = {
        //14 13 11 9
        {0x0e, 0x0b, 0x0d, 0x09},
        {0x09, 0x0e, 0x0b, 0x0d},
        {0x0d, 0x09, 0x0e, 0x0b},
        {0x0b, 0x0d, 0x09, 0x0e}
    };
    public String text2Hex(String plainText){
        String res = "";
        int[] c = new int[16];
        for (int i = 0; i < plainText.getBytes().length; i++) {
            c[i] = plainText.getBytes()[i];
        }
        for (int i = 0; i < c.length; i++) {
            if (Integer.toHexString(c[i]).length() < 2) {
                    res += "0"+Integer.toHexString(c[i])+"";
                }else{
                    res += Integer.toHexString(c[i])+"";
                }
        }
        return res;
    }
    
    /** Fungsi Enkripsi.
     *
     * @param plainText berupa pesan biasa yang hendak dienkripsi
     * @param kunci berupa teks biasa sebagai kunci untuk enkripsi
     * @return
     */
    public String encrypt(String plainText, String kunci) {
        int[] c = new int[plainText.getBytes().length];
        int[] k = new int[kunci.getBytes().length];
        
        String hasilEnkripsi = "";
        int[][] state = new int[4][4];
        int[][] key = new int[4][4];
        
        for (int i = 0; i < c.length; i++) {
            c[i] = plainText.getBytes()[i];
        }
        for (int i = 0; i < k.length; i++) {
            k[i] = kunci.getBytes()[i];
        }
        convertInt2State(c, state);
        convertInt2State(k, key);

        // kunci yang akan digunakan untuk key tiap round
        // butuh: original key + Nr * 16
        int[][] expandedKey = new int[4][4 * Nr + 4];
        // ronde yang dibutuhkan: -> untuk AES-128 butuh 10 ronde, tetapi untuk pengulangannya butuh 9 ronde, 1 ronde di final round
        int banyakRonde = 9;
        // ekspansi kunci 
        keyExpansion(key,expandedKey);

        // initial round -> addroundkey
        addRoundKey(state,expandedKey,0);
        
        // pengulangan round 
        for (int i = 0; i < banyakRonde ; i++) {
            subBytes(state);
            shiftRow(state);
            mixColumn(state);
            addRoundKey(state, expandedKey, (i+1));
        }
        
        // final round
        subBytes(state);
        shiftRow(state);
        addRoundKey(state, expandedKey, Nr);
        
        // copy bytes ke string
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                hasilEnkripsi += Integer.toHexString(state[j][i])+"";
            }
        }
        return hasilEnkripsi;
    }

    /** Fungsi Dekrip ciphertext.
     *
     * @param cipherText merupakan string dalam bentuk hexadecimal TANPA SPASI
     * @param kunci berupa string dalam bentuk plain (bukan hex)
     * @return berupa array integer yang nantinya akan di proses menjadi plaintext kembali
     */
    public String decrypt(String cipherText, String kunci) {
        int[] k = new int[kunci.getBytes().length];
        for (int i = 0; i < k.length; i++) {
            k[i] = kunci.getBytes()[i];
        }
        String hasilDekripsi = "";
        int[][] state = new int[4][4];
        int[][] key = new int[4][4];        
        convertHex2State(cipherText, state);
        convertInt2State(k, key);
       
        // kunci yang akan digunakan untuk key tiap round
        // butuh: original key + Nr * 16
        int[][] expandedKey = new int[4][4 * Nr + 4];
        // ronde yang dibutuhkan: -> untuk Main-128 butuh 10 ronde, tetapi untuk pengulangannya butuh 9 ronde, 1 ronde di final round
        int banyakRonde = 9;
        
        keyExpansion(key,expandedKey);
                    
        // initial round -> addroundkey shiftrow dan subbytes
        addRoundKey(state, expandedKey,Nr); // -> key 40 - 43 -> Nr * 4 = 10*4 = 40
                
        // pengulangan round 
        for (int i = 9; i > 0 ; i--) {
            invShiftRow(state);
            invSubBytes(state);
            addRoundKey(state, expandedKey, (i));
            invMixColumn(state);
        }
        
        // final round
        invShiftRow(state);
        invSubBytes(state);
        addRoundKey(state,expandedKey,0);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (Integer.toHexString(state[j][i]).length() < 2) {
                    hasilDekripsi += "0"+Integer.toHexString(state[j][i])+"";
                }else{
                    hasilDekripsi += Integer.toHexString(state[j][i])+"";
                }
            }
        }
        return hasilDekripsi;
        
    }

    /** Key Expansion.
     * @param inputKey berupa input key array 4x4 yang akan diekspansi
     * @param expandedKey sebagai output menyimpan hasil operasi
     * @see #keyExpansionCore(int[], int) 
     */
    private void keyExpansion(int[][] inputKey, int[][] expandedKey) {
        // Kunci awal dari expandedKey adalah input key itu sendiri..
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                expandedKey[i][j] = inputKey[i][j];
            }
        }
        
        int currentGeneratedWords = 4;
        int rconState = 1; // key rcon dimulai dari 1
        int temp[] = new int[4];
        while(currentGeneratedWords < (4 * Nr)+4){
            for (int i = 0; i < 4; i++) {
                temp[i] = expandedKey[i][currentGeneratedWords - 1];
            }
            // setiap 4 word kunci yang  telah digenerate, lakukan operasi rcon
            if (currentGeneratedWords % 4 == 0) {
                keyExpansionCore(temp, rconState);
                rconState++;
            }
            
            // melakukan XOR dengan kunci 4bit awal yang sudah digenerate
            for (int i = 0; i < 4; i++) {
                expandedKey[i][currentGeneratedWords] = expandedKey[i][currentGeneratedWords - 4] ^ temp[i];
            }
                currentGeneratedWords++;
        }
    }
    
    /** Key Expansion Core.
     * merupakan badan dari key expansion yang berisi 3 metode, yaitu:
     * <p><b>Rotasi</b> -> menggeser index pertama ke ujung kanan array, lalu menggeser index2 yang lain ke kiri
     * <p><b>SubBytes</b> -> mengganti nilai tiap index array dengan nilai di tabel {@link #SBOX}
     * <p><b>Rcon</b> -> melakukan operasi XOR di index pertama array dengan nilai di tabel {@link #rcon}
     * 
     * @param input merupakan array 1x4 sebagai temporary data
     * @param index sebagai inputan untuk mengambil nilai {@link #rcon}
     */
    private void keyExpansionCore(int[] input, int index){
       // rotasi
       int temp = input[0];
       input[0] = input[1];
       input[1] = input[2];
       input[2] = input[3];
       input[3] = temp;
       
       // subwords 
        for (int i = 0; i < 4; i++) {
            input[i] = SBOX[input[i]/16][input[i]%16];
        }
        
        // rcon
        input[0] ^= rcon[index];

    }
    
    /** Add Round Key.
     * memasukkan round key dari key ke state, yang sesungguhnya adalah operasi XOR dari keduanya..  
     * @param state masukan berupa state array 4x4
     * @param key kunci yang sudah diekspansi menjadi {@code 4*Nr}
     * @param begim index mulai untuk mengambil key
     */
    private void addRoundKey(int[][] state, int[][] key,int begin) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                state[i][j] ^= key[i][4 * begin + j];
            }
        }
    }
    
    /** Sub Bytes. Operasi Sub Bytes mengganti setiap elemen state dengan nilai SBOX yang sesuai.
 tabel referensi yang digunakan adalah tabel <b>{@code SBOX}</b>
     * @param state inputan berupa 4x4 array integer
     */
    private void subBytes(int[][] state) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                int hex = state[i][j];
                state[i][j]=SBOX[hex/16][hex%16];
            }
        }
    }

    /** Shift Row. 
     * menggeser ke kiri tiap baris state. Baris pertama tetap.
     * Baris kedua digeser ke kiri 1x.
     * Baris ketiga digeser ke kiri 2x.
     * Baris keempat digeser ke kiri 3x.
     * @param state inputan berupa 4x4 array Integer
     */
    private void shiftRow(int[][] state) {
        int[][] temp = new int[4][4];
        //row 1 tetap
        temp[0][0] = state[0][0];
        temp[0][1] = state[0][1];
        temp[0][2] = state[0][2];
        temp[0][3] = state[0][3];
        //row 2 geser kiri 1x
        temp[1][0] = state[1][1];
        temp[1][1] = state[1][2];
        temp[1][2] = state[1][3];
        temp[1][3] = state[1][0];
        //row 3 geser kiri 2x
        temp[2][0] = state[2][2];
        temp[2][1] = state[2][3];
        temp[2][2] = state[2][0];
        temp[2][3] = state[2][1];
        //row 4 geser kiri 3x
        temp[3][0] = state[3][3];
        temp[3][1] = state[3][0];
        temp[3][2] = state[3][1];
        temp[3][3] = state[3][2];
        // copy data temp ke state asli..
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                state[i][j] = temp[i][j];
            }
        }
        // end shiftRow
    }

    /** Operasi Mix Column.
     * menggunakan tabel bantuan {@code mc2} dan {@code mc3} sebagai pengganti
     * nilai dari perkalian matrik galois dengan {@code state}.
     * Lalu menambahkan hasilnya dengan melakukan operasi XOR
     * 
     * @param state berupa array 4x4 Integer
     * @see #mixColumn(int[][]) 
     * @see #GALOIS
     */
    private void mixColumn(int[][] state) {
        int temp[][] = new int[4][4];
        
        //kolom 1
        temp[0][0] = mc2[state[0][0]/16][state[0][0]%16] ^ mc3[state[1][0]/16][state[1][0]%16] ^ state[2][0] ^ state[3][0];
        temp[1][0] = state[0][0] ^ mc2[state[1][0]/16][state[1][0]%16] ^ mc3[state[2][0]/16][state[2][0]%16] ^ state[3][0];
        temp[2][0] = state[0][0] ^ state[1][0] ^ mc2[state[2][0]/16][state[2][0]%16] ^ mc3[state[3][0]/16][state[3][0]%16];
        temp[3][0] = mc3[state[0][0]/16][state[0][0]%16] ^ state[1][0] ^ state[2][0] ^ mc2[state[3][0]/16][state[3][0]%16];

        // kolom 2
        temp[0][1] = mc2[state[0][1]/16][state[0][1]%16] ^ mc3[state[1][1]/16][state[1][1]%16] ^ state[2][1] ^ state[3][1];
        temp[1][1] = state[0][1] ^ mc2[state[1][1]/16][state[1][1]%16] ^ mc3[state[2][1]/16][state[2][1]%16] ^ state[3][1];
        temp[2][1] = state[0][1] ^ state[1][1] ^ mc2[state[2][1]/16][state[2][1]%16] ^ mc3[state[3][1]/16][state[3][1]%16];
        temp[3][1] = mc3[state[0][1]/16][state[0][1]%16] ^ state[1][1] ^ state[2][1] ^ mc2[state[3][1]/16][state[3][1]%16];

        // kolom 3
        temp[0][2] = mc2[state[0][2]/16][state[0][2]%16] ^ mc3[state[1][2]/16][state[1][2]%16] ^ state[2][2] ^ state[3][2];
        temp[1][2] = state[0][2] ^ mc2[state[1][2]/16][state[1][2]%16] ^ mc3[state[2][2]/16][state[2][2]%16] ^ state[3][2];
        temp[2][2] = state[0][2] ^ state[1][2] ^ mc2[state[2][2]/16][state[2][2]%16] ^ mc3[state[3][2]/16][state[3][2]%16];
        temp[3][2] = mc3[state[0][2]/16][state[0][2]%16] ^ state[1][2] ^ state[2][2] ^ mc2[state[3][2]/16][state[3][2]%16];

        //kolom 4
        temp[0][3] = mc2[state[0][3]/16][state[0][3]%16] ^ mc3[state[1][3]/16][state[1][3]%16] ^ state[2][3] ^ state[3][3];
        temp[1][3] = state[0][3] ^ mc2[state[1][3]/16][state[1][3]%16] ^ mc3[state[2][3]/16][state[2][3]%16] ^ state[3][3];
        temp[2][3] = state[0][3] ^ state[1][3] ^ mc2[state[2][3]/16][state[2][3]%16] ^ mc3[state[3][3]/16][state[3][3]%16];
        temp[3][3] = mc3[state[0][3]/16][state[0][3]%16] ^ state[1][3] ^ state[2][3] ^ mc2[state[3][3]/16][state[3][3]%16];        
        
        // copy data temp ke state di parameter...
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                state[i][j] = temp[i][j];
            }
        }
    }
    
    /** Konversi Integer Blok ke State array 4x4.
     * @param intText berupa array Integer 1 dimensi yang merupakan representasi dari sebuah text
     * @param state berupa array Integer 4x4 sebagai output
     */
    private void convertInt2State(int[] intText, int[][] state) {
        for (int i = 0; i < intText.length; i++) {
            state[i%4][i/4]=intText[i];
        }
    }
    
    /** Konversi Hex String ke String.
     * @param hex berupa String hexadecimal tanpa spasi
     * @param state berupa array Integer 4x4
     */
    private void convertHex2State(String hex, int[][] state){
        String tmp = hex;
        int[] result = new int[hex.length()/2];
        for (int i = 0; i < tmp.length(); i+=2) {
            String a = "";
            int idx = i;
            while(a.length() < 2){
                a += tmp.charAt(idx);
                idx++;
            }
            result[i/2] = Integer.decode("0x"+a);
        }
        convertInt2State(result, state);
    }

    /** 
     * Fungsi-fungsi invers untuk Dekripsi.
     */
    
    /** Invers Shift Row. 
     * menggeser ke kanan tiap baris state. Baris pertama tetap.
     * Baris kedua digeser ke kanan 1x
     * Baris ketiga digeser ke kanan 2x
     * Baris keempat digeser ke kanan 3x
     * @param state inputan berupa 4x4 array Integer
     * @see #shiftRow(int[][]) 
     */
    private void invShiftRow(int[][] state) {
        int[][] temp = new int[4][4];
        //row 1 tetap
        temp[0][0] = state[0][0];
        temp[0][1] = state[0][1];
        temp[0][2] = state[0][2];
        temp[0][3] = state[0][3];
        //row 2 geser kanan 1x
        temp[1][0] = state[1][3];
        temp[1][1] = state[1][0];
        temp[1][2] = state[1][1];
        temp[1][3] = state[1][2];
        //row 3 geser kanan 2x
        temp[2][0] = state[2][2];
        temp[2][1] = state[2][3];
        temp[2][2] = state[2][0];
        temp[2][3] = state[2][1];
        //row 4 geser kanan 3x
        temp[3][0] = state[3][1];
        temp[3][1] = state[3][2];
        temp[3][2] = state[3][3];
        temp[3][3] = state[3][0];
        // copy data temp ke state asli..
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                state[i][j] = temp[i][j];
            }
        }
        // end shiftRow
    }
    
    /** Invers Sub Bytes. Invers dari operasi subBytes()
     * tabel referensi yang digunakan adalah tabel {@code INV_SBOX}
     * @param state inputan berupa 4x4 array Integer
     * @see #subBytes(int[][]) 
     */
    private void invSubBytes(int[][] state) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                int hex = state[i][j];
                state[i][j]=INV_SBOX[hex/16][hex%16];
            }
        }
    }

    /** Operasi Invers Mix Column. Invers dari mixColumn()
     * menggunakan tabel bantuan {@code mc9}, {@code mc11}, {@code mc13} 
     * dan {@code mc14} sebagai pengganti
     * nilai dari perkalian matrik invers galois dengan {@code state}.
     * Lalu menambahkan hasilnya dengan melakukan operasi XOR
     * 
     * @param state berupa array 4x4 Integer
     * @see #mixColumn(int[][]) 
     * @see #INV_GALOIS
     * @see #GALOIS
     */
    private void invMixColumn(int[][] state) {
        int temp[][] = new int[4][4];
        // kolom 1
        temp[0][0] = mc14[state[0][0]/16][state[0][0]%16] ^ mc11[state[1][0]/16][state[1][0]%16] ^ mc13[state[2][0]/16][state[2][0]%16] ^ mc9[state[3][0]/16][state[3][0]%16];
        temp[1][0] = mc9[state[0][0]/16][state[0][0]%16] ^ mc14[state[1][0]/16][state[1][0]%16] ^ mc11[state[2][0]/16][state[2][0]%16] ^ mc13[state[3][0]/16][state[3][0]%16];
        temp[2][0] = mc13[state[0][0]/16][state[0][0]%16] ^ mc9[state[1][0]/16][state[1][0]%16] ^ mc14[state[2][0]/16][state[2][0]%16] ^ mc11[state[3][0]/16][state[3][0]%16];
        temp[3][0] = mc11[state[0][0]/16][state[0][0]%16] ^ mc13[state[1][0]/16][state[1][0]%16] ^ mc9[state[2][0]/16][state[2][0]%16] ^ mc14[state[3][0]/16][state[3][0]%16];
        

        // kolom 2
        temp[0][1] = mc14[state[0][1]/16][state[0][1]%16] ^ mc11[state[1][1]/16][state[1][1]%16] ^ mc13[state[2][1]/16][state[2][1]%16] ^ mc9[state[3][1]/16][state[3][1]%16];
        temp[1][1] = mc9[state[0][1]/16][state[0][1]%16] ^ mc14[state[1][1]/16][state[1][1]%16] ^ mc11[state[2][1]/16][state[2][1]%16] ^ mc13[state[3][1]/16][state[3][1]%16];
        temp[2][1] = mc13[state[0][1]/16][state[0][1]%16] ^ mc9[state[1][1]/16][state[1][1]%16] ^ mc14[state[2][1]/16][state[2][1]%16] ^ mc11[state[3][1]/16][state[3][1]%16];
        temp[3][1] = mc11[state[0][1]/16][state[0][1]%16] ^ mc13[state[1][1]/16][state[1][1]%16] ^ mc9[state[2][1]/16][state[2][1]%16] ^ mc14[state[3][1]/16][state[3][1]%16];

        // kolom 3
        temp[0][2] = mc14[state[0][2]/16][state[0][2]%16] ^ mc11[state[1][2]/16][state[1][2]%16] ^ mc13[state[2][2]/16][state[2][2]%16] ^ mc9[state[3][2]/16][state[3][2]%16];
        temp[1][2] = mc9[state[0][2]/16][state[0][2]%16] ^ mc14[state[1][2]/16][state[1][2]%16] ^ mc11[state[2][2]/16][state[2][2]%16] ^ mc13[state[3][2]/16][state[3][2]%16];
        temp[2][2] = mc13[state[0][2]/16][state[0][2]%16] ^ mc9[state[1][2]/16][state[1][2]%16] ^ mc14[state[2][2]/16][state[2][2]%16] ^ mc11[state[3][2]/16][state[3][2]%16];
        temp[3][2] = mc11[state[0][2]/16][state[0][2]%16] ^ mc13[state[1][2]/16][state[1][2]%16] ^ mc9[state[2][2]/16][state[2][2]%16] ^ mc14[state[3][2]/16][state[3][2]%16];

        // kolom 4
        temp[0][3] = mc14[state[0][3]/16][state[0][3]%16] ^ mc11[state[1][3]/16][state[1][3]%16] ^ mc13[state[2][3]/16][state[2][3]%16] ^ mc9[state[3][3]/16][state[3][3]%16];
        temp[1][3] = mc9[state[0][3]/16][state[0][3]%16] ^ mc14[state[1][3]/16][state[1][3]%16] ^ mc11[state[2][3]/16][state[2][3]%16] ^ mc13[state[3][3]/16][state[3][3]%16];
        temp[2][3] = mc13[state[0][3]/16][state[0][3]%16] ^ mc9[state[1][3]/16][state[1][3]%16] ^ mc14[state[2][3]/16][state[2][3]%16] ^ mc11[state[3][3]/16][state[3][3]%16];
        temp[3][3] = mc11[state[0][3]/16][state[0][3]%16] ^ mc13[state[1][3]/16][state[1][3]%16] ^ mc9[state[2][3]/16][state[2][3]%16] ^ mc14[state[3][3]/16][state[3][3]%16];        
        
        // copy data temp ke state di parameter...
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                state[i][j] = temp[i][j];
            }
        }
    }
       
    /** Tabel Mix Column x2.
     * digunakan saat proses {@link #mixColumn(int[][]) }
     */
    public final int[][] mc2 = {
              /*0    1      2     3     4     5     6    7      8    9      a    b      c     d    e     f*/
    /*0*/    {0x00, 0x02, 0x04, 0x06, 0x08, 0x0a, 0x0c, 0x0e, 0x10, 0x12, 0x14, 0x16, 0x18, 0x1a, 0x1c, 0x1e},
    /*1*/    {0x20, 0x22, 0x24, 0x26, 0x28, 0x2a, 0x2c, 0x2e, 0x30, 0x32, 0x34, 0x36, 0x38, 0x3a, 0x3c, 0x3e},
    /*2*/    {0x40, 0x42, 0x44, 0x46, 0x48, 0x4a, 0x4c, 0x4e, 0x50, 0x52, 0x54, 0x56, 0x58, 0x5a, 0x5c, 0x5e},
    /*3*/    {0x60, 0x62, 0x64, 0x66, 0x68, 0x6a, 0x6c, 0x6e, 0x70, 0x72, 0x74, 0x76, 0x78, 0x7a, 0x7c, 0x7e},
    /*4*/    {0x80, 0x82, 0x84, 0x86, 0x88, 0x8a, 0x8c, 0x8e, 0x90, 0x92, 0x94, 0x96, 0x98, 0x9a, 0x9c, 0x9e},
    /*5*/    {0xa0, 0xa2, 0xa4, 0xa6, 0xa8, 0xaa, 0xac, 0xae, 0xb0, 0xb2, 0xb4, 0xb6, 0xb8, 0xba, 0xbc, 0xbe},
    /*6*/    {0xc0, 0xc2, 0xc4, 0xc6, 0xc8, 0xca, 0xcc, 0xce, 0xd0, 0xd2, 0xd4, 0xd6, 0xd8, 0xda, 0xdc, 0xde},
    /*7*/    {0xe0, 0xe2, 0xe4, 0xe6, 0xe8, 0xea, 0xec, 0xee, 0xf0, 0xf2, 0xf4, 0xf6, 0xf8, 0xfa, 0xfc, 0xfe},
    /*8*/    {0x1b, 0x19, 0x1f, 0x1d, 0x13, 0x11, 0x17, 0x15, 0x0b, 0x09, 0x0f, 0x0d, 0x03, 0x01, 0x07, 0x05},
    /*9*/    {0x3b, 0x39, 0x3f, 0x3d, 0x33, 0x31, 0x37, 0x35, 0x2b, 0x29, 0x2f, 0x2d, 0x23, 0x21, 0x27, 0x25},
    /*a*/    {0x5b, 0x59, 0x5f, 0x5d, 0x53, 0x51, 0x57, 0x55, 0x4b, 0x49, 0x4f, 0x4d, 0x43, 0x41, 0x47, 0x45},
    /*b*/    {0x7b, 0x79, 0x7f, 0x7d, 0x73, 0x71, 0x77, 0x75, 0x6b, 0x69, 0x6f, 0x6d, 0x63, 0x61, 0x67, 0x65},
    /*c*/    {0x9b, 0x99, 0x9f, 0x9d, 0x93, 0x91, 0x97, 0x95, 0x8b, 0x89, 0x8f, 0x8d, 0x83, 0x81, 0x87, 0x85},
    /*d*/    {0xbb, 0xb9, 0xbf, 0xbd, 0xb3, 0xb1, 0xb7, 0xb5, 0xab, 0xa9, 0xaf, 0xad, 0xa3, 0xa1, 0xa7, 0xa5},
    /*e*/    {0xdb, 0xd9, 0xdf, 0xdd, 0xd3, 0xd1, 0xd7, 0xd5, 0xcb, 0xc9, 0xcf, 0xcd, 0xc3, 0xc1, 0xc7, 0xc5},
    /*f*/    {0xfb, 0xf9, 0xff, 0xfd, 0xf3, 0xf1, 0xf7, 0xf5, 0xeb, 0xe9, 0xef, 0xed, 0xe3, 0xe1, 0xe7, 0xe5}};

    /** Tabel Mix Column x3.
     * digunakan saat proses {@link #mixColumn(int[][]) }
     */
    public final int[][] mc3 = {
             /*0    1      2     3     4     5     6    7      8    9      a    b      c     d    e     f*/
    /*0*/    {0x00, 0x03, 0x06, 0x05, 0x0c, 0x0f, 0x0a, 0x09, 0x18, 0x1b, 0x1e, 0x1d, 0x14, 0x17, 0x12, 0x11},
    /*1*/    {0x30, 0x33, 0x36, 0x35, 0x3c, 0x3f, 0x3a, 0x39, 0x28, 0x2b, 0x2e, 0x2d, 0x24, 0x27, 0x22, 0x21},
    /*2*/    {0x60, 0x63, 0x66, 0x65, 0x6c, 0x6f, 0x6a, 0x69, 0x78, 0x7b, 0x7e, 0x7d, 0x74, 0x77, 0x72, 0x71},
    /*3*/    {0x50, 0x53, 0x56, 0x55, 0x5c, 0x5f, 0x5a, 0x59, 0x48, 0x4b, 0x4e, 0x4d, 0x44, 0x47, 0x42, 0x41},
    /*4*/    {0xc0, 0xc3, 0xc6, 0xc5, 0xcc, 0xcf, 0xca, 0xc9, 0xd8, 0xdb, 0xde, 0xdd, 0xd4, 0xd7, 0xd2, 0xd1},
    /*5*/    {0xf0, 0xf3, 0xf6, 0xf5, 0xfc, 0xff, 0xfa, 0xf9, 0xe8, 0xeb, 0xee, 0xed, 0xe4, 0xe7, 0xe2, 0xe1},
    /*6*/    {0xa0, 0xa3, 0xa6, 0xa5, 0xac, 0xaf, 0xaa, 0xa9, 0xb8, 0xbb, 0xbe, 0xbd, 0xb4, 0xb7, 0xb2, 0xb1},
    /*7*/    {0x90, 0x93, 0x96, 0x95, 0x9c, 0x9f, 0x9a, 0x99, 0x88, 0x8b, 0x8e, 0x8d, 0x84, 0x87, 0x82, 0x81},
    /*8*/    {0x9b, 0x98, 0x9d, 0x9e, 0x97, 0x94, 0x91, 0x92, 0x83, 0x80, 0x85, 0x86, 0x8f, 0x8c, 0x89, 0x8a},
    /*9*/    {0xab, 0xa8, 0xad, 0xae, 0xa7, 0xa4, 0xa1, 0xa2, 0xb3, 0xb0, 0xb5, 0xb6, 0xbf, 0xbc, 0xb9, 0xba},
    /*a*/    {0xfb, 0xf8, 0xfd, 0xfe, 0xf7, 0xf4, 0xf1, 0xf2, 0xe3, 0xe0, 0xe5, 0xe6, 0xef, 0xec, 0xe9, 0xea},
    /*b*/    {0xcb, 0xc8, 0xcd, 0xce, 0xc7, 0xc4, 0xc1, 0xc2, 0xd3, 0xd0, 0xd5, 0xd6, 0xdf, 0xdc, 0xd9, 0xda},
    /*c*/    {0x5b, 0x58, 0x5d, 0x5e, 0x57, 0x54, 0x51, 0x52, 0x43, 0x40, 0x45, 0x46, 0x4f, 0x4c, 0x49, 0x4a},
    /*d*/    {0x6b, 0x68, 0x6d, 0x6e, 0x67, 0x64, 0x61, 0x62, 0x73, 0x70, 0x75, 0x76, 0x7f, 0x7c, 0x79, 0x7a},
    /*e*/    {0x3b, 0x38, 0x3d, 0x3e, 0x37, 0x34, 0x31, 0x32, 0x23, 0x20, 0x25, 0x26, 0x2f, 0x2c, 0x29, 0x2a},
    /*f*/    {0x0b, 0x08, 0x0d, 0x0e, 0x07, 0x04, 0x01, 0x02, 0x13, 0x10, 0x15, 0x16, 0x1f, 0x1c, 0x19, 0x1a}   };
    
    /**Tabel Mix Column x9.
     * digunakan saat proses {@link #invMixColumn(int[][]) }
     */
    public final int[][] mc9 = {
              /*0    1      2     3     4     5     6    7      8    9      a    b      c     d    e     f*/
    /*0*/    {0x00, 0x09, 0x12, 0x1b, 0x24, 0x2d, 0x36, 0x3f, 0x48, 0x41, 0x5a, 0x53, 0x6c, 0x65, 0x7e, 0x77},                          
    /*1*/    {0x90, 0x99, 0x82, 0x8b, 0xb4, 0xbd, 0xa6, 0xaf, 0xd8, 0xd1, 0xca, 0xc3, 0xfc, 0xf5, 0xee, 0xe7}, 
    /*2*/    {0x3b, 0x32, 0x29, 0x20, 0x1f, 0x16, 0x0d, 0x04, 0x73, 0x7a, 0x61, 0x68, 0x57, 0x5e, 0x45, 0x4c}, 
    /*3*/    {0xab, 0xa2, 0xb9, 0xb0, 0x8f, 0x86, 0x9d, 0x94, 0xe3, 0xea, 0xf1, 0xf8, 0xc7, 0xce, 0xd5, 0xdc}, 
    /*4*/    {0x76, 0x7f, 0x64, 0x6d, 0x52, 0x5b, 0x40, 0x49, 0x3e, 0x37, 0x2c, 0x25, 0x1a, 0x13, 0x08, 0x01}, 
    /*5*/    {0xe6, 0xef, 0xf4, 0xfd, 0xc2, 0xcb, 0xd0, 0xd9, 0xae, 0xa7, 0xbc, 0xb5, 0x8a, 0x83, 0x98, 0x91}, 
    /*6*/    {0x4d, 0x44, 0x5f, 0x56, 0x69, 0x60, 0x7b, 0x72, 0x05, 0x0c, 0x17, 0x1e, 0x21, 0x28, 0x33, 0x3a}, 
    /*7*/    {0xdd, 0xd4, 0xcf, 0xc6, 0xf9, 0xf0, 0xeb, 0xe2, 0x95, 0x9c, 0x87, 0x8e, 0xb1, 0xb8, 0xa3, 0xaa}, 
    /*8*/    {0xec, 0xe5, 0xfe, 0xf7, 0xc8, 0xc1, 0xda, 0xd3, 0xa4, 0xad, 0xb6, 0xbf, 0x80, 0x89, 0x92, 0x9b}, 
    /*9*/    {0x7c, 0x75, 0x6e, 0x67, 0x58, 0x51, 0x4a, 0x43, 0x34, 0x3d, 0x26, 0x2f, 0x10, 0x19, 0x02, 0x0b}, 
    /*a*/    {0xd7, 0xde, 0xc5, 0xcc, 0xf3, 0xfa, 0xe1, 0xe8, 0x9f, 0x96, 0x8d, 0x84, 0xbb, 0xb2, 0xa9, 0xa0}, 
    /*b*/    {0x47, 0x4e, 0x55, 0x5c, 0x63, 0x6a, 0x71, 0x78, 0x0f, 0x06, 0x1d, 0x14, 0x2b, 0x22, 0x39, 0x30}, 
    /*c*/    {0x9a, 0x93, 0x88, 0x81, 0xbe, 0xb7, 0xac, 0xa5, 0xd2, 0xdb, 0xc0, 0xc9, 0xf6, 0xff, 0xe4, 0xed}, 
    /*d*/    {0x0a, 0x03, 0x18, 0x11, 0x2e, 0x27, 0x3c, 0x35, 0x42, 0x4b, 0x50, 0x59, 0x66, 0x6f, 0x74, 0x7d}, 
    /*e*/    {0xa1, 0xa8, 0xb3, 0xba, 0x85, 0x8c, 0x97, 0x9e, 0xe9, 0xe0, 0xfb, 0xf2, 0xcd, 0xc4, 0xdf, 0xd6}, 
    /*f*/    {0x31, 0x38, 0x23, 0x2a, 0x15, 0x1c, 0x07, 0x0e, 0x79, 0x70, 0x6b, 0x62, 0x5d, 0x54, 0x4f, 0x46}   };
    
    /**Tabel Mix Column x11.
     * digunakan saat proses {@link #invMixColumn(int[][]) }
     */
    public final int[][] mc11 = {
              /*0    1      2     3     4     5     6    7      8    9      a    b      c     d    e     f*/
    /*0*/    {0x00, 0x0b, 0x16, 0x1d, 0x2c, 0x27, 0x3a, 0x31, 0x58, 0x53, 0x4e, 0x45, 0x74, 0x7f, 0x62, 0x69}, 
    /*1*/    {0xb0, 0xbb, 0xa6, 0xad, 0x9c, 0x97, 0x8a, 0x81, 0xe8, 0xe3, 0xfe, 0xf5, 0xc4, 0xcf, 0xd2, 0xd9}, 
    /*2*/    {0x7b, 0x70, 0x6d, 0x66, 0x57, 0x5c, 0x41, 0x4a, 0x23, 0x28, 0x35, 0x3e, 0x0f, 0x04, 0x19, 0x12}, 
    /*3*/    {0xcb, 0xc0, 0xdd, 0xd6, 0xe7, 0xec, 0xf1, 0xfa, 0x93, 0x98, 0x85, 0x8e, 0xbf, 0xb4, 0xa9, 0xa2}, 
    /*4*/    {0xf6, 0xfd, 0xe0, 0xeb, 0xda, 0xd1, 0xcc, 0xc7, 0xae, 0xa5, 0xb8, 0xb3, 0x82, 0x89, 0x94, 0x9f}, 
    /*5*/    {0x46, 0x4d, 0x50, 0x5b, 0x6a, 0x61, 0x7c, 0x77, 0x1e, 0x15, 0x08, 0x03, 0x32, 0x39, 0x24, 0x2f}, 
    /*6*/    {0x8d, 0x86, 0x9b, 0x90, 0xa1, 0xaa, 0xb7, 0xbc, 0xd5, 0xde, 0xc3, 0xc8, 0xf9, 0xf2, 0xef, 0xe4}, 
    /*7*/    {0x3d, 0x36, 0x2b, 0x20, 0x11, 0x1a, 0x07, 0x0c, 0x65, 0x6e, 0x73, 0x78, 0x49, 0x42, 0x5f, 0x54}, 
    /*8*/    {0xf7, 0xfc, 0xe1, 0xea, 0xdb, 0xd0, 0xcd, 0xc6, 0xaf, 0xa4, 0xb9, 0xb2, 0x83, 0x88, 0x95, 0x9e}, 
    /*9*/    {0x47, 0x4c, 0x51, 0x5a, 0x6b, 0x60, 0x7d, 0x76, 0x1f, 0x14, 0x09, 0x02, 0x33, 0x38, 0x25, 0x2e}, 
    /*a*/    {0x8c, 0x87, 0x9a, 0x91, 0xa0, 0xab, 0xb6, 0xbd, 0xd4, 0xdf, 0xc2, 0xc9, 0xf8, 0xf3, 0xee, 0xe5}, 
    /*b*/    {0x3c, 0x37, 0x2a, 0x21, 0x10, 0x1b, 0x06, 0x0d, 0x64, 0x6f, 0x72, 0x79, 0x48, 0x43, 0x5e, 0x55}, 
    /*c*/    {0x01, 0x0a, 0x17, 0x1c, 0x2d, 0x26, 0x3b, 0x30, 0x59, 0x52, 0x4f, 0x44, 0x75, 0x7e, 0x63, 0x68}, 
    /*d*/    {0xb1, 0xba, 0xa7, 0xac, 0x9d, 0x96, 0x8b, 0x80, 0xe9, 0xe2, 0xff, 0xf4, 0xc5, 0xce, 0xd3, 0xd8}, 
    /*e*/    {0x7a, 0x71, 0x6c, 0x67, 0x56, 0x5d, 0x40, 0x4b, 0x22, 0x29, 0x34, 0x3f, 0x0e, 0x05, 0x18, 0x13}, 
    /*f*/    {0xca, 0xc1, 0xdc, 0xd7, 0xe6, 0xed, 0xf0, 0xfb, 0x92, 0x99, 0x84, 0x8f, 0xbe, 0xb5, 0xa8, 0xa3}   };
    
    /**Tabel Mix Column x13.
     * digunakan saat proses {@link #invMixColumn(int[][]) }
     */
    public final int[][] mc13 = {
             /*0    1      2     3     4     5     6    7      8    9      a    b      c     d    e     f*/
    /*0*/    {0x00, 0x0d, 0x1a, 0x17, 0x34, 0x39, 0x2e, 0x23, 0x68, 0x65, 0x72, 0x7f, 0x5c, 0x51, 0x46, 0x4b}, 
    /*1*/    {0xd0, 0xdd, 0xca, 0xc7, 0xe4, 0xe9, 0xfe, 0xf3, 0xb8, 0xb5, 0xa2, 0xaf, 0x8c, 0x81, 0x96, 0x9b}, 
    /*2*/    {0xbb, 0xb6, 0xa1, 0xac, 0x8f, 0x82, 0x95, 0x98, 0xd3, 0xde, 0xc9, 0xc4, 0xe7, 0xea, 0xfd, 0xf0}, 
    /*3*/    {0x6b, 0x66, 0x71, 0x7c, 0x5f, 0x52, 0x45, 0x48, 0x03, 0x0e, 0x19, 0x14, 0x37, 0x3a, 0x2d, 0x20}, 
    /*4*/    {0x6d, 0x60, 0x77, 0x7a, 0x59, 0x54, 0x43, 0x4e, 0x05, 0x08, 0x1f, 0x12, 0x31, 0x3c, 0x2b, 0x26}, 
    /*5*/    {0xbd, 0xb0, 0xa7, 0xaa, 0x89, 0x84, 0x93, 0x9e, 0xd5, 0xd8, 0xcf, 0xc2, 0xe1, 0xec, 0xfb, 0xf6}, 
    /*6*/    {0xd6, 0xdb, 0xcc, 0xc1, 0xe2, 0xef, 0xf8, 0xf5, 0xbe, 0xb3, 0xa4, 0xa9, 0x8a, 0x87, 0x90, 0x9d}, 
    /*7*/    {0x06, 0x0b, 0x1c, 0x11, 0x32, 0x3f, 0x28, 0x25, 0x6e, 0x63, 0x74, 0x79, 0x5a, 0x57, 0x40, 0x4d}, 
    /*8*/    {0xda, 0xd7, 0xc0, 0xcd, 0xee, 0xe3, 0xf4, 0xf9, 0xb2, 0xbf, 0xa8, 0xa5, 0x86, 0x8b, 0x9c, 0x91}, 
    /*9*/    {0x0a, 0x07, 0x10, 0x1d, 0x3e, 0x33, 0x24, 0x29, 0x62, 0x6f, 0x78, 0x75, 0x56, 0x5b, 0x4c, 0x41}, 
    /*a*/    {0x61, 0x6c, 0x7b, 0x76, 0x55, 0x58, 0x4f, 0x42, 0x09, 0x04, 0x13, 0x1e, 0x3d, 0x30, 0x27, 0x2a}, 
    /*b*/    {0xb1, 0xbc, 0xab, 0xa6, 0x85, 0x88, 0x9f, 0x92, 0xd9, 0xd4, 0xc3, 0xce, 0xed, 0xe0, 0xf7, 0xfa}, 
    /*c*/    {0xb7, 0xba, 0xad, 0xa0, 0x83, 0x8e, 0x99, 0x94, 0xdf, 0xd2, 0xc5, 0xc8, 0xeb, 0xe6, 0xf1, 0xfc}, 
    /*d*/    {0x67, 0x6a, 0x7d, 0x70, 0x53, 0x5e, 0x49, 0x44, 0x0f, 0x02, 0x15, 0x18, 0x3b, 0x36, 0x21, 0x2c}, 
    /*e*/    {0x0c, 0x01, 0x16, 0x1b, 0x38, 0x35, 0x22, 0x2f, 0x64, 0x69, 0x7e, 0x73, 0x50, 0x5d, 0x4a, 0x47}, 
    /*f*/    {0xdc, 0xd1, 0xc6, 0xcb, 0xe8, 0xe5, 0xf2, 0xff, 0xb4, 0xb9, 0xae, 0xa3, 0x80, 0x8d, 0x9a, 0x97}   };

    /**Tabel Mix Column x14.
     * digunakan saat proses {@link #invMixColumn(int[][]) }
     */
    public final int[][] mc14 = {
              /*0    1      2     3     4     5     6    7      8    9      a    b      c     d    e     f*/
    /*0*/    {0x00, 0x0e, 0x1c, 0x12, 0x38, 0x36, 0x24, 0x2a, 0x70, 0x7e, 0x6c, 0x62, 0x48, 0x46, 0x54, 0x5a}, 
    /*1*/    {0xe0, 0xee, 0xfc, 0xf2, 0xd8, 0xd6, 0xc4, 0xca, 0x90, 0x9e, 0x8c, 0x82, 0xa8, 0xa6, 0xb4, 0xba}, 
    /*2*/    {0xdb, 0xd5, 0xc7, 0xc9, 0xe3, 0xed, 0xff, 0xf1, 0xab, 0xa5, 0xb7, 0xb9, 0x93, 0x9d, 0x8f, 0x81}, 
    /*3*/    {0x3b, 0x35, 0x27, 0x29, 0x03, 0x0d, 0x1f, 0x11, 0x4b, 0x45, 0x57, 0x59, 0x73, 0x7d, 0x6f, 0x61}, 
    /*4*/    {0xad, 0xa3, 0xb1, 0xbf, 0x95, 0x9b, 0x89, 0x87, 0xdd, 0xd3, 0xc1, 0xcf, 0xe5, 0xeb, 0xf9, 0xf7}, 
    /*5*/    {0x4d, 0x43, 0x51, 0x5f, 0x75, 0x7b, 0x69, 0x67, 0x3d, 0x33, 0x21, 0x2f, 0x05, 0x0b, 0x19, 0x17}, 
    /*6*/    {0x76, 0x78, 0x6a, 0x64, 0x4e, 0x40, 0x52, 0x5c, 0x06, 0x08, 0x1a, 0x14, 0x3e, 0x30, 0x22, 0x2c}, 
    /*7*/    {0x96, 0x98, 0x8a, 0x84, 0xae, 0xa0, 0xb2, 0xbc, 0xe6, 0xe8, 0xfa, 0xf4, 0xde, 0xd0, 0xc2, 0xcc}, 
    /*8*/    {0x41, 0x4f, 0x5d, 0x53, 0x79, 0x77, 0x65, 0x6b, 0x31, 0x3f, 0x2d, 0x23, 0x09, 0x07, 0x15, 0x1b}, 
    /*9*/    {0xa1, 0xaf, 0xbd, 0xb3, 0x99, 0x97, 0x85, 0x8b, 0xd1, 0xdf, 0xcd, 0xc3, 0xe9, 0xe7, 0xf5, 0xfb}, 
    /*a*/    {0x9a, 0x94, 0x86, 0x88, 0xa2, 0xac, 0xbe, 0xb0, 0xea, 0xe4, 0xf6, 0xf8, 0xd2, 0xdc, 0xce, 0xc0}, 
    /*b*/    {0x7a, 0x74, 0x66, 0x68, 0x42, 0x4c, 0x5e, 0x50, 0x0a, 0x04, 0x16, 0x18, 0x32, 0x3c, 0x2e, 0x20}, 
    /*c*/    {0xec, 0xe2, 0xf0, 0xfe, 0xd4, 0xda, 0xc8, 0xc6, 0x9c, 0x92, 0x80, 0x8e, 0xa4, 0xaa, 0xb8, 0xb6}, 
    /*d*/    {0x0c, 0x02, 0x10, 0x1e, 0x34, 0x3a, 0x28, 0x26, 0x7c, 0x72, 0x60, 0x6e, 0x44, 0x4a, 0x58, 0x56}, 
    /*e*/    {0x37, 0x39, 0x2b, 0x25, 0x0f, 0x01, 0x13, 0x1d, 0x47, 0x49, 0x5b, 0x55, 0x7f, 0x71, 0x63, 0x6d}, 
    /*f*/    {0xd7, 0xd9, 0xcb, 0xc5, 0xef, 0xe1, 0xf3, 0xfd, 0xa7, 0xa9, 0xbb, 0xb5, 0x9f, 0x91, 0x83, 0x8d}};
    
    /**Tabel RCon.
     * digunakan saat proses {@link #keyExpansionCore(int[], int)  }
     */
    public final int[] rcon = {
        0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 
    0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 
    0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 
    0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 
    0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 
    0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 
    0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 
    0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 
    0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 
    0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 
    0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 
    0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 
    0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 
    0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 
    0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 
    0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d
    };
}
