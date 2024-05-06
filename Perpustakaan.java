import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Perpustakaan {
    private JPanel PeminjamanBuku;
    private JTextField tfNoBuku;
    private JTextField tfJudul;
    private JTextField tfPengarang;
    private JTextField tfTahun;
    private JTextField tfPenerbit;
    private JTextField tfHasil;
    private JButton lihatHasilButton;
    private JButton keluarButton;
    private JButton resetButton;
    private JButton pinjamButton;
    private JButton kembalikanButton;
    private JTextField tfStatus;

    private PeminjamBuku peminjamBuku;
    private BukuDatabase bukuDatabase;

    public Perpustakaan(PeminjamBuku peminjamBuku, BukuDatabase bukuDatabase) {
        this.peminjamBuku = peminjamBuku;
        this.bukuDatabase = bukuDatabase;

        JFrame frame = new JFrame("Sistem Peminjaman Buku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(PeminjamanBuku);
        frame.pack();
        frame.setVisible(true);

        lihatHasilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mengambil informasi pencarian dari inputan
                String noBuku = tfNoBuku.getText();
                String judul = tfJudul.getText();

                String kataKunci = noBuku + judul;
                String[] hasilPencarian = bukuDatabase.cariBuku(kataKunci);

                if (hasilPencarian != null) {
                    // Menampilkan hasil pencarian pada field hasil
                    tfHasil.setText(hasilPencarian[0]);

                    // Memisahkan hasil pencarian untuk menempatkannya pada field yang sesuai
                    String[] infoBuku = hasilPencarian[0].split("\n");

                    // Menyimpan informasi buku pada field yang sesuai
                    tfNoBuku.setText(infoBuku[0].substring(infoBuku[0].indexOf(":") + 1).trim());
                    tfJudul.setText(infoBuku[1].substring(infoBuku[1].indexOf(":") + 1).trim());
                    tfPengarang.setText(infoBuku[2].substring(infoBuku[2].indexOf(":") + 1).trim());
                    tfTahun.setText(infoBuku[3].substring(infoBuku[3].indexOf(":") + 1).trim());
                    tfPenerbit.setText(infoBuku[4].substring(infoBuku[4].indexOf(":") + 1).trim());
                    tfStatus.setText(infoBuku[5].substring(infoBuku[5].indexOf(":") + 1).trim());
                } else {
                    tfHasil.setText("Buku dengan kata kunci tersebut tidak ditemukan");
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mereset semua inputan
                tfNoBuku.setText("");
                tfJudul.setText("");
                tfPengarang.setText("");
                tfTahun.setText("");
                tfPenerbit.setText("");
                tfHasil.setText("");
                tfStatus.setText("");
            }
        });

        keluarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Keluar dari aplikasi
                System.exit(0);
            }
        });


        pinjamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mengambil nomor buku dari inputan
                String noBuku = tfNoBuku.getText();

                // Memeriksa apakah buku sudah dipinjam
                if (peminjamBuku.isBukuDipinjam(noBuku)) {
                    // Mengatur nilai field hasil untuk menampilkan pesan
                    tfHasil.setText("Buku ini telah dipinjam. Silahkan mencari buku yang lain.");
                } else {
                    // Melakukan peminjaman buku
                    peminjamBuku.pinjamBuku(noBuku);

                    // Mengatur nilai field hasil untuk menampilkan pesan
                    tfHasil.setText("Buku telah berhasil dipinjam.");

                    // Update tampilan status buku yang dipinjam pada UI (opsional)
                    tfStatus.setText("Dipinjam"); // Ubah status pada UI
                }
            }
        });

        kembalikanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String noBuku = tfNoBuku.getText();

                // Memeriksa apakah buku ada di database
                String[] hasilPencarian = bukuDatabase.cariBuku(noBuku);

                if (hasilPencarian != null) {
                    // Mengecek status peminjaman buku dari database
                    boolean isDipinjam = hasilPencarian[0].toLowerCase().contains("dipinjam");

                    if (isDipinjam) {
                        // Jika buku sedang dipinjam, lakukan pengembalian buku
                        peminjamBuku.kembalikanBuku(noBuku);
                        tfHasil.setText("Buku telah berhasil dikembalikan.");

                        // Update tampilan status buku yang dikembalikan pada UI (opsional)
                        tfStatus.setText("Tidak Dipinjam"); // Ubah status pada UI
                    } else {
                        tfHasil.setText("Buku ini tidak sedang dipinjam. Silahkan periksa kembali nomor buku.");
                    }
                } else {
                    tfHasil.setText("Buku dengan nomor tersebut tidak ditemukan");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Isi database buku
                String[][] initialData = {
                        {"01", "Java Programming", "John Doe", "2020", "Tech Publications"},
                        {"02", "Python Basics", "Jane Smith", "2019", "Code Books"},
                        {"03", "Data Structures and Algorithms", "David Johnson", "2021", "Algorithm House"},
                        {"04", "Matematika Diskrit Dasar", "Udin Simanjuntak", "2018", "Sinar Publisher"},
                        {"05", "Web Development Dasar", "Dina Mortis", "2017", "Penerbit Erlangga"}
                };

                // Membuat objek PeminjamBuku dan BukuDatabase dengan data awal
                PeminjamBuku peminjamBuku = new PeminjamBuku();
                BukuDatabase bukuDatabase = new BukuDatabase(initialData);

                // Membuat objek Perpustakaan dan mengirimkan objek PeminjamBuku serta BukuDatabase
                new Perpustakaan(peminjamBuku, bukuDatabase);
            }
        });
    }
}

// Kelas untuk menyimpan data buku
class Buku {
    private String noBuku;
    private String judul;
    private String pengarang;
    private String tahun;
    private String penerbit;
    private boolean dipinjam;


    public Buku(String noBuku, String judul, String pengarang, String tahun, String penerbit) {
        this.noBuku = noBuku;
        this.judul = judul;
        this.pengarang = pengarang;
        this.tahun = tahun;
        this.penerbit = penerbit;
        this.dipinjam = false;
    }

    // Getter methods
    public String getNoBuku() {
        return noBuku;
    }

    public String getJudul() {
        return judul;
    }

    public String getPengarang() {
        return pengarang;
    }

    public String getTahun() {
        return tahun;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public boolean isDipinjam() {
        return dipinjam;
    }

    public void setDipinjam(boolean dipinjam) {
        this.dipinjam = dipinjam;
    }
}

// Interface untuk operasi peminjaman buku
interface PeminjamanBuku {
    void simpanBuku(Buku buku);
    String[] lihatHasilPeminjaman(String noBuku);
    void pinjamBuku(String noBuku);
    void kembalikanBuku(String noBuku);
    boolean isBukuDipinjam(String noBuku);
    List<Buku> getBukuData();
}

// Implementasi interface PeminjamanBuku
// Implementasi interface PeminjamanBuku
class PeminjamBuku implements PeminjamanBuku {
    private List<Buku> bukuData = new ArrayList<>();

    @Override
    public void simpanBuku(Buku buku) {
        bukuData.add(buku);
    }

    @Override
    public String[] lihatHasilPeminjaman(String noBuku) {
        for (Buku buku : bukuData) {
            if (buku.getNoBuku().equals(noBuku)) {
                return bukuToArray(buku);
            }
        }
        return null;
    }

    @Override
    public void pinjamBuku(String noBuku) {
        Buku buku = cariBukuByNo(noBuku);
        if (buku != null && !buku.isDipinjam()) {
            buku.setDipinjam(true);
        }
    }

    @Override
    public void kembalikanBuku(String noBuku) {
        Buku buku = cariBukuByNo(noBuku);
        if (buku != null && buku.isDipinjam()) {
            buku.setDipinjam(false);
        }
    }

    @Override
    public boolean isBukuDipinjam(String noBuku) {
        Buku buku = cariBukuByNo(noBuku);
        return buku != null && buku.isDipinjam();
    }

    @Override
    public List<Buku> getBukuData() {
        return bukuData;
    }

    private Buku cariBukuByNo(String noBuku) {
        for (Buku buku : bukuData) {
            if (buku.getNoBuku().equals(noBuku)) {
                return buku;
            }
        }
        return null;
    }

    private String[] bukuToArray(Buku buku) {
        return new String[]{buku.getNoBuku(), buku.getJudul(), buku.getPengarang(), buku.getTahun(), buku.getPenerbit()};
    }
}


// Kelas yang menyimpan data buku dalam bentuk array
class BukuDatabase {
    private List<Buku> bukuList = new ArrayList<>();

    // Konstruktor untuk menginisialisasi BukuDatabase dengan data awal
    public BukuDatabase(String[][] initialData) {
        for (String[] bukuInfo : initialData) {
            Buku buku = new Buku(bukuInfo[0], bukuInfo[1], bukuInfo[2], bukuInfo[3], bukuInfo[4]);
            addBuku(buku);
        }
    }

    // Menambahkan buku ke dalam array bukuList
    public void addBuku(Buku buku) {
        bukuList.add(buku);
    }

    public String[] cariBuku(String kataKunci) {
        List<String> hasilPencarian = new ArrayList<>();

        for (Buku buku : bukuList) {
            if (buku.getNoBuku().equalsIgnoreCase(kataKunci) ||
                    buku.getJudul().toLowerCase().contains(kataKunci.toLowerCase()) ||
                    buku.getPengarang().toLowerCase().contains(kataKunci.toLowerCase()) ||
                    buku.getTahun().equalsIgnoreCase(kataKunci) ||
                    buku.getPenerbit().toLowerCase().contains(kataKunci.toLowerCase())) {
                hasilPencarian.add("Nomor Buku: " + buku.getNoBuku() +
                        "\nJudul: " + buku.getJudul() +
                        "\nPengarang: " + buku.getPengarang() +
                        "\nTahun Terbit: " + buku.getTahun() +
                        "\nPenerbit: " + buku.getPenerbit() +
                        "\nStatus: " + (buku.isDipinjam() ? "Dipinjam" : "Tidak Dipinjam"));
            }
        }

        return hasilPencarian.isEmpty() ? null : hasilPencarian.toArray(new String[0]);
    }

    // Metode untuk mendapatkan seluruh data buku
    public List<Buku> getBukuData() {
        return bukuList;
    }

    private String[] bukuToArray(Buku buku) {
        return new String[]{buku.getNoBuku(), buku.getJudul(), buku.getPengarang(), buku.getTahun(), buku.getPenerbit()};
    }
}
