package com.BC181.Syahputra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_film";
    private final static String TABLE_FILM = "t_film";
    private final static String KEY_ID_FILM = "ID_film";
    private final static String KEY_JUDUl = "Judul";
    private final static String KEY_TAHUN = "Tahun";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_GENRE = "Genre";
    private final static String KEY_PEMAIN = "Pemain";
    private final static String KEY_SIPNOSIS= "SIPNOSIS";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
    private Context context;


    public DatabaseHandler(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FILM = "CREATE TABLE " +  TABLE_FILM
                + "(" + KEY_ID_FILM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUl + " TEXT, " + KEY_TAHUN + " DATE, "
                + KEY_GAMBAR + " TEXT, " + KEY_GENRE + " TEXT, "
                + KEY_PEMAIN + " TEXT, " + KEY_SIPNOSIS + " TEXT);";
        db.execSQL(CREATE_TABLE_FILM);
        inisialisasiFilmAwal(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_FILM;
        db.execSQL(DROP_TABLE);
        onCreate(db);

    }

    public void tambahFilm(Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUl, dataFilm.getJudul());
        cv.put(KEY_TAHUN, sdFormat.format(dataFilm.getTahun()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_PEMAIN, dataFilm.getPemain());
        cv.put(KEY_SIPNOSIS, dataFilm.getSipnosis());


        db.insert(TABLE_FILM, null, cv);
        db.close();
    }

    public void tambahFilm(Film dataFilm, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUl, dataFilm.getJudul());
        cv.put(KEY_TAHUN, sdFormat.format(dataFilm.getTahun()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_PEMAIN, dataFilm.getPemain());
        cv.put(KEY_SIPNOSIS, dataFilm.getSipnosis());

        db.insert(TABLE_FILM, null, cv);

    }

    public void editFilm(Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUl, dataFilm.getJudul());
        cv.put(KEY_TAHUN, sdFormat.format(dataFilm.getTahun()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_PEMAIN, dataFilm.getPemain());
        cv.put(KEY_SIPNOSIS, dataFilm.getSipnosis());


        db.update(TABLE_FILM, cv, KEY_ID_FILM + "=?", new String[]{String.valueOf(dataFilm.getIdFilm())});
        db.close();
    }

    public void hapusFilm(int idFilm) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FILM, KEY_ID_FILM + "=?", new String[]{String.valueOf(idFilm)});
        db.close();
    }

    public ArrayList<Film> getAllFilm() {
        ArrayList<Film> dataFilm = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FILM;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if(csr.moveToFirst()){
            do {
                Date tempDate = new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString(2));
                }
                catch (ParseException er){
                    er.printStackTrace();
                }

                Film tempFilm = new Film(
                        csr.getInt(0),
                        csr.getString(1),
                        tempDate,
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6)
                );

                dataFilm.add(tempFilm);
            } while (csr.moveToNext());
        }
        return dataFilm;
    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inisialisasiFilmAwal(SQLiteDatabase db) {
        int idFilm = 0;
        Date tempDate = new Date();

        //menambah data Film 1
        try{
            tempDate = sdFormat.parse("2019");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Film film1 = new Film(
                idFilm,
                "Joker",
                tempDate,
                storeImageFile(R.drawable.joker),
                "Crime",
                "Joaquin Phoenix sebagai Arthur Fleck/Joker,Robert De Niro sebagai Murray Franklin,Zazie Beetz sebagai Sophie Dumond",
                "Joker ditayangkan secara perdana di Festival Film Venesia pada tanggal 31 Agustus 2019[4] serta ditayangkan di Amerika Serikat pada 4 Oktober 2019 dan Indonesia dua hari sebelumnya.[5][6] Joker adalah film laga hidup pertama Batman yang mendapatkan klasifikasi R dari Motion Picture Association of America karena kekerasan berdarah yang kuat, perilaku mengganggu, bahasa dan gambar seksual singkat.\n" +
                        "\n" +
                        "Pada 1981, Arthur Fleck, seorang badut yang berusia 40 tahun tinggal bersama ibunya, Penny di Kota Gotham yang kacau balau. Dia menderita kelainan otak yang menyebabkan dia tertawa pada waktu yang tidak tepat dan dia sering mengunjungi pekerja layanan sosial untuk mendapatkan obatnya. Setelah sekelompok anak jalanan mencuri papan milik Arthur dan mengeroyokinya di lorong, salah seorang rekan kerjanya meminjamkan pistol sebagai alat perlindungan diri. Suatu saat, penyakitnya kambuh ketika ia sedang menaiki kereta api, sehingga Sophie keheranan dengan tingkahnya dan Arthur memberikan sebuah kartu untuk menjawab keheranannya. Arthur kemudian menjalin hubungan baik dengannya, yang kemudian baru disadarinya bahwa Sophie tinggal di apartemen yang sama dengannya.\n."
        );

        tambahFilm(film1, db);
        idFilm++;

        // Data Film ke 2
        try{
            tempDate = sdFormat.parse("2019");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Film film2 = new Film(
                idFilm,
                "Avengers Endgame",
                tempDate,
                storeImageFile(R.drawable.avenger),
                "Action",
                "Robert Downey Jr.\n" +
                        "Chris Evans\n" +
                        "Mark Ruffalo\n" +
                        "Chris Hemsworth\n" +
                        "Scarlett Johansson\n" +
                        "Jeremy Renner\n" +
                        "Don Cheadle",
                "Film ini diumumkan pada bulan Oktober 2014 sebagai Avengers: Infinity War - Part 2. Russo bersaudara ditunjuk untuk mengarahkan pada bulan April 2015, dan pada bulan Mei, Markus dan McFeely dikontrak sebagai penulis naskah film tersebut. Pada bulan Juli 2016, Marvel menghapus judul, merujuknya hanya sebagai Untitled Avengers. Pembuatan film dimulai pada bulan Agustus 2017 di Pinewood Atlanta Studios di Fayette County, Georgia, mengambil gambar secara berurutan dengan Infinity War, dan berakhir pada Januari 2018. Pembuatan film tambahan dilakukan di wilayah Metro dan Pusat Kota Atlanta dan New York. Judul resmi terungkap pada Desember 2018. Dengan perkiraan anggaran 356 juta dolar, menjadikan film ini termahal ketiga yang pernah dibuat.\n" +
                        "Avengers: Endgame sangat dinanti, dan Disney mendukung film ini dengan kampanye pemasaran yang luas. Tayang perdana di Los Angeles pada 22 April 2019, dan dirilis secara teatrikal di Amerika Serikat pada 26 April 2019, dalam IMAX dan 3D. Film ini menerima pujian untuk arahan, akting, skor musik, efek visual, dan bobot emosional, dengan kritik memuji puncak dari seluruh cerita 22-film MCU. Dengan pendapatan lebih dari 2,7 miliar dolar di seluruh dunia, film ini memecahkan banyak rekor box office dan menjadi film terlaris sepanjang masa.\n");
        tambahFilm(film2, db);
        idFilm++;

        // Tambah Film 3

        try{
            tempDate = sdFormat.parse("2010");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Film film3 = new Film(
                idFilm,
                "Insidious",
                tempDate,
                storeImageFile(R.drawable.insi),
                "horror",
                "Patrick Wilson sebagai Josh Lambert,Patrick Wilson sebagai Josh Lambert,Ty Simpkins sebagai Dalton Lambert",
                "Renai dan Josh Lambert baru saja pindah ke rumah baru dengan tiga anak mereka. Pada suatu pagi, Renai melihat album foto keluarga dengan anaknya, Dalton. Kemudian anaknya itu bertanya mengapa tidak ada foto Josh ketika masih anak-anak. Renai beralasan bahwa ayahnya malu dan tidak menyukai foto dirinya sendiri.\n" +
                        "Suatu hari, Dalton mendengar sesuatu di atap dan pergi untuk menyelidiki. Saat ia masuk ke dalam, ia mencoba memanjat tangga untuk menyalakan lampu, tapi jatuh karena tangga retak. Saat jatuh ke lantai, ia menatap penuh ketakutan di kegelapan seolah-olah melihat sesuatu yang mengerikan. Kemudian, Renai menyuruhnya untuk beristirahan. Josh menasihatinya untuk tidak bermain di atas atap lagi.\n" +
                        "Keesokan harinya, Dalton tidak terbangun dari tidur. Renai dan Josh bergegas membawanya ke rumah sakit. Para dokter mengatakan dia dalam keadaan koma. Tiga bulan kemudian, Dalton dipindahkan kembali ke rumahnya saat masih dalam koma. Tak lama setelah itu, rangkaian peristiwa mengusik mereka mulai terjadi. Renai mendengar suara aneh dari alat pemantau bayi dan cetakan tangan berdarah di alas kasur tempat tidur Dalton, disertai penampakan seorang pria aneh di kamar tidur bayi perempuannya. Renai menjadi lebih terganggu ketika anak mereka, Foster mengatakan dia tidak suka ketika Dalton \"berjalan di sekitarnya\" pada malam hari. Renai memberitahu Josh tentang peristiwa tersebut, dan ingin segera pindah dari rumah yang mereka huni saat ini. Di rumah baru, peristiwa supranatural masih saja terjadi. Ibunya Josh, Lorraine ingat sebuah mimpinya. Dalam mimpinya itu, ia masuk ke dalam kamar Dalton dan melihat suatu bayangan hitam berdiri di sudut dan mengatakan bahwa kaumnya menginginkan Dalton. Selanjutnya Lorraine melihat makhluk berwajah merah berdiri di belakang Josh. Kemudian, Dalton diserang di kamarnya. Peristiwa ini membuat Lorraine menghubungi temannya, Elise Reiner yang ahli dalam kegiatan penyelidikan paranormal. Keluarga, Elise, dan tim cenayang memasuki ruangan Dalton. Elise melihat dan menggambarkan sosok yang ada di kamar tersebut kepada asistennya. Sosok hitam dengan wajah merah dan mata berongga gelap di langit-langit kamar Dalton. Sosok yang sama yang Lorraine lihat sebelumnya dalam rumah. Elise menjelaskan kepada Renai dan Josh bahwa Dalton memiliki kemampuan proyek astral sejak kecil. Alasan bahwa Dalton berada dalam keadaan koma karena ia tanpa rasa takut bepergian terlalu jauh ke dunia roh, dan akibatnya ia tersesat di suatu tempat yang disebut \"The Further\". Itu adalah sebuah tempat bagi roh-roh yang tersiksa setelah kematian. Sementara roh Dalton ada dalam dunia lain, sehingga raganya tanpa roh. Roh-roh yang tersiksa mendambakan kesempatan untuk masuk dalam hidup melalui raga Dalton, selain itu setan ingin menggunakan Dalton untuk niat jahat. Untuk mengonsumsi tubuh, diperlukan periode waktu dan energi.\n" +
                        "Skeptis pada awalnya, kemudian Josh menyesal karena keraguannya tersebut dan akhirnya percaya tentang kemistisan. Ketika ia menemukan gambar-gambar oleh Dalton yang menyerupai setan angka yang dijelaskan Elise. Mereka menjalankan ritual untuk berkomunikasi dengan Dalton. Setan menggunakan tubuh Dalton untuk melawan tiga roh lainnya yang menginginkan tubuh Dalton. Setelah ritual, Elise memanggil Lorraine dan mengungkapkan bahwa Josh juga bisa melakukan proyek astral, dan diteror oleh roh jahat pada masa kecilnya. Lorraine menunjukkan mereka foto Josh pada masa kanak-kanak, menjelaskan tentang bayangan wanita tua di belakangnya. Pada foto Josh tersebut ada bayangan wanita tua yang mendekati Josh sampai beberapa inci darinya, ia menjelaskan sambil dipenuhi rasa takutnya akan foto tersebut. Elise menunjukkan bahwa Josh harus menggunakan kemampuannya untuk menemukan dan membantu mengembalikan jiwa Dalton. Josh menyetujuinya, dan pergi ke dunia roh. Di sana, ia menemukan Dalton yang sedang dirantai ke lantai dan membebaskan anaknya, tetapi setan menemukan kehadiran Josh dan menyerang mereka. Dalam pencarian raga mereka, roh Josh dan Dalton melarikan diri dari setan yang mengejar mereka. Tepat sebelum terbangun, Josh meninggalkan anaknya untuk menghadapi bayangan wanita tua yang muncul untuk menuju ke dalam rumahnya. Saat ia berteriak untuk pergi darinya, dan berteriak bahwa ia tidak takut. Akhirnya roh tadi mundur ke dalam kegelapan. Beberapa saat kemudian, Josh dan Dalton terbangun dan semua roh lenyap. Segenap keluarga sekarang gembira karena bersatu kembali.\n" +
                        "Renai, Dalton, dan Lorraine sedang ngobrol di dapur. Elise sedang berbicara untuk berpamitan kepada Josh. Saat Elise mengambil foto masa kanak-kanak dari tangan Josh, Elise merasakan sesuatu dan memotret Josh. Josh langsung memarahi Elise seraya berata bahwa ia tidak ingin difoto, dan mencekik Elise. Renai mendengar Josh berteriak, ia menuju ke ruangan sumber suara. Di sana, ia menemukan Elise telah tewas setelah Josh pergi. Dia mencari Josh, ia melihat kamera Elise terdapat foto bayangan seorang wanita dengan gaun pengantin. Ini menunjukkan bahwa apa yang Elise lihat adalah Josh dengan tampang tua dengan tangan dan kuku yang kotor. Tiba-tiba Josh memegang bahu Renai sambil mengatakan, \"Renai, aku di sini\". Kengerian menyelubungi wajahnya saat ia melihat ke belakangnya. Dalam sebuah adegan setelah kredit film, ada sekilas wajah wanita tua buruk rupa terlihat meniup lilin dan akhirnya film selesai.");
        tambahFilm(film3, db);


    }

}