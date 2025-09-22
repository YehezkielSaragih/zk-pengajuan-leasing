package com.fif.zk.viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

public class CreditorViewModel {
    private String nama;
    private String alamat;
    private String noTelepon;
    private Integer jumlahPinjaman;

    @Init
    public void init() {
        reset();
    }

    @Command
    @NotifyChange({"nama", "alamat", "noTelepon", "jumlahPinjaman"})
    public void simpan() {
        Kreditur k = new Kreditur(nama, alamat, noTelepon, jumlahPinjaman);
        CreditorService.getInstance().tambahKreditur(k);
        reset();
    }

    @Command
    @NotifyChange({"nama", "alamat", "noTelepon", "jumlahPinjaman"})
    public void reset() {
        nama = "";
        alamat = "";
        noTelepon = "";
        jumlahPinjaman = null;
    }

    // Getter & Setter
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public String getNoTelepon() { return noTelepon; }
    public void setNoTelepon(String noTelepon) { this.noTelepon = noTelepon; }

    public Integer getJumlahPinjaman() { return jumlahPinjaman; }
    public void setJumlahPinjaman(Integer jumlahPinjaman) { this.jumlahPinjaman = jumlahPinjaman; }

    // Inner class Kreditur
    public static class Kreditur {
        private String nama;
        private String alamat;
        private String noTelepon;
        private Integer jumlahPinjaman;

        public Kreditur(String nama, String alamat, String noTelepon, Integer jumlahPinjaman) {
            this.nama = nama;
            this.alamat = alamat;
            this.noTelepon = noTelepon;
            this.jumlahPinjaman = jumlahPinjaman;
        }

        public String getNama() { return nama; }
        public String getAlamat() { return alamat; }
        public String getNoTelepon() { return noTelepon; }
        public Integer getJumlahPinjaman() { return jumlahPinjaman; }
    }
}
