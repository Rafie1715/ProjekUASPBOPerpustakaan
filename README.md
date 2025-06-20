# Proyek Sistem Peminjaman Buku Sederhana

Ini adalah proyek aplikasi desktop sederhana untuk mengelola peminjaman dan pengembalian buku di perpustakaan. Aplikasi ini dibangun menggunakan Java Swing untuk antarmuka pengguna grafis (GUI).

## Deskripsi

Aplikasi ini memungkinkan pengguna untuk melakukan beberapa operasi dasar dalam sistem perpustakaan, seperti:
* Mencari buku berdasarkan nomor, judul, pengarang, tahun terbit, atau penerbit.
* Melihat detail lengkap dari sebuah buku.
* Meminjam buku yang tersedia.
* Mengembalikan buku yang telah dipinjam.

Aplikasi ini memiliki data awal (database) buku yang sudah di-inisialisasi di dalam kode.

## Fitur

* **Pencarian Buku**: Pengguna dapat mencari buku menggunakan kata kunci apa pun yang relevan (nomor, judul, dll.).
* **Tampilan Detail**: Setelah buku ditemukan, detail lengkap seperti judul, pengarang, tahun terbit, dan penerbit akan ditampilkan di kolom yang sesuai.
* **Status Peminjaman**: Pengguna dapat melihat apakah sebuah buku sedang dipinjam atau tersedia.
* **Peminjaman Buku**: Pengguna dapat meminjam buku. Jika buku sudah dipinjam, sistem akan memberikan notifikasi.
* **Pengembalian Buku**: Pengguna dapat mengembalikan buku yang sedang dipinjam.
* **Reset Form**: Tombol untuk membersihkan semua kolom input.
* **Keluar**: Tombol untuk menutup aplikasi.

## Cara Menjalankan Proyek

1.  **Prasyarat**: Pastikan Anda memiliki Java Development Kit (JDK) yang terpasang di komputer Anda.

2.  **Kompilasi**: Buka terminal atau command prompt, navigasikan ke direktori tempat Anda menyimpan file `Perpustakaan.java`, lalu kompilasi file tersebut dengan perintah:
    ```bash
    javac Perpustakaan.java
    ```

3.  **Jalankan**: Setelah kompilasi berhasil, jalankan aplikasi dengan perintah:
    ```bash
    java Perpustakaan
    ```
    Jendela aplikasi peminjaman buku akan muncul.

## Struktur Kode

Proyek ini terdiri dari beberapa kelas dan satu interface yang bekerja sama:

* **`Perpustakaan.java`**: Kelas utama yang berisi logika untuk GUI (dibuat dengan Swing) dan menangani semua interaksi pengguna seperti klik tombol.
* **`Buku.java`**: Kelas yang merepresentasikan entitas buku. Ini berisi atribut seperti `noBuku`, `judul`, `pengarang`, `tahun`, `penerbit`, dan status `dipinjam`.
* **`BukuDatabase.java`**: Kelas yang berfungsi sebagai database dalam memori untuk menyimpan dan mencari data buku.
* **`PeminjamanBuku.java`**: Sebuah *interface* yang mendefinisikan operasi-operasi yang berkaitan dengan peminjaman, seperti `pinjamBuku` dan `kembalikanBuku`.
* **`PeminjamBuku.java`**: Implementasi dari *interface* `PeminjamanBuku`. Kelas ini menangani logika status peminjaman setiap buku.
