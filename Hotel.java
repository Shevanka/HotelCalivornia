import java.time.LocalDate;

/**
 * Kelas pusat yang mengelola kamar, ruangan, fasilitas, reservasi, dan event.
 */
public class Hotel {
    private static final int MAKS_KAMAR = 50;
    private static final int MAKS_RUANGAN = 10;
    private static final int MAKS_FASILITAS = 20;
    private static final int MAKS_RESERVASI = 100;
    private static final int MAKS_EVENT = 50;

    private String namaHotel;
    private String alamat;
    private Kamar[] daftarKamar = new Kamar[MAKS_KAMAR];
    private Ruangan[] daftarRuangan = new Ruangan[MAKS_RUANGAN];
    private Fasilitas[] daftarFasilitas = new Fasilitas[MAKS_FASILITAS];
    private Reservasi[] daftarReservasi = new Reservasi[MAKS_RESERVASI];
    private Event[] daftarEvent = new Event[MAKS_EVENT];
    private int jumlahKamar;
    private int jumlahRuangan;
    private int jumlahFasilitas;
    private int jumlahReservasi;
    private int jumlahEvent;

    public Hotel(String namaHotel, String alamat) {
        this.namaHotel = namaHotel;
        this.alamat = alamat;
    }

    public String getNamaHotel() {
        return namaHotel;
    }

    public String getAlamat() {
        return alamat;
    }

    public int getJumlahKamar() {
        return jumlahKamar;
    }

    public int getJumlahEvent() {
        return jumlahEvent;
    }

    // ---------- Pengelolaan data master ----------

    public boolean tambahKamar(Kamar k) {
        if (k == null || jumlahKamar >= MAKS_KAMAR) {
            return false;
        }
        daftarKamar[jumlahKamar++] = k;
        return true;
    }

    public boolean tambahRuangan(Ruangan r) {
        if (r == null || jumlahRuangan >= MAKS_RUANGAN) {
            return false;
        }
        daftarRuangan[jumlahRuangan++] = r;
        return true;
    }

    public boolean tambahFasilitas(Fasilitas f) {
        if (f == null || jumlahFasilitas >= MAKS_FASILITAS) {
            return false;
        }
        daftarFasilitas[jumlahFasilitas++] = f;
        return true;
    }

    public Kamar getKamar(String nomorKamar) {
        for (int i = 0; i < jumlahKamar; i++) {
            if (daftarKamar[i].getNomorKamar().equalsIgnoreCase(nomorKamar)) {
                return daftarKamar[i];
            }
        }
        return null;
    }

    public Ruangan getRuangan(String kodeRuangan) {
        for (int i = 0; i < jumlahRuangan; i++) {
            if (daftarRuangan[i].getKodeRuangan().equalsIgnoreCase(kodeRuangan)) {
                return daftarRuangan[i];
            }
        }
        return null;
    }

    public Fasilitas getFasilitas(String nama) {
        for (int i = 0; i < jumlahFasilitas; i++) {
            if (daftarFasilitas[i].getNamaFasilitas().equalsIgnoreCase(nama)) {
                return daftarFasilitas[i];
            }
        }
        return null;
    }

    // ---------- Pencarian (overloading) ----------

    /** Overloading 1: cari kamar tersedia berdasarkan tipe. */
    public Kamar[] cariKamar(String tipe) {
        return cariKamar(tipe, Double.MAX_VALUE);
    }

    /** Overloading 2: cari kamar tersedia berdasarkan tipe dan batas harga per malam. */
    public Kamar[] cariKamar(String tipe, double hargaMaks) {
        int cacah = 0;
        for (int i = 0; i < jumlahKamar; i++) {
            if (cocok(daftarKamar[i], tipe, hargaMaks)) {
                cacah++;
            }
        }
        Kamar[] hasil = new Kamar[cacah];
        int idx = 0;
        for (int i = 0; i < jumlahKamar; i++) {
            if (cocok(daftarKamar[i], tipe, hargaMaks)) {
                hasil[idx++] = daftarKamar[i];
            }
        }
        return hasil;
    }

    private boolean cocok(Kamar k, String tipe, double hargaMaks) {
        return k.isTersedia() && k.getTipe().equalsIgnoreCase(tipe)
                && k.getHargaPerMalam() <= hargaMaks;
    }

    public Ruangan[] cariRuangan(int kapasitasMinimal) {
        int cacah = 0;
        for (int i = 0; i < jumlahRuangan; i++) {
            if (daftarRuangan[i].isTersedia() && daftarRuangan[i].getKapasitas() >= kapasitasMinimal) {
                cacah++;
            }
        }
        Ruangan[] hasil = new Ruangan[cacah];
        int idx = 0;
        for (int i = 0; i < jumlahRuangan; i++) {
            if (daftarRuangan[i].isTersedia() && daftarRuangan[i].getKapasitas() >= kapasitasMinimal) {
                hasil[idx++] = daftarRuangan[i];
            }
        }
        return hasil;
    }

    // ---------- Alur 1: reservasi kamar ----------

    public Reservasi buatReservasi(Tamu tamu, String nomorKamar, LocalDate checkIn, LocalDate checkOut) {
        Kamar kamar = getKamar(nomorKamar);
        if (kamar == null) {
            System.out.println("   [!] Kamar " + nomorKamar + " tidak ditemukan.");
            return null;
        }
        if (!kamar.isTersedia()) {
            System.out.println("   [!] Kamar " + nomorKamar + " sedang tidak tersedia.");
            return null;
        }
        if (jumlahReservasi >= MAKS_RESERVASI) {
            System.out.println("   [!] Kapasitas data reservasi penuh.");
            return null;
        }
        String id = String.format("RSV-%03d", jumlahReservasi + 1);
        Reservasi r = new Reservasi(id, tamu, kamar, checkIn, checkOut); // bisa melempar exception
        kamar.setTersedia(false);
        daftarReservasi[jumlahReservasi++] = r;
        tamu.tambahRiwayat(r);
        return r;
    }

    public boolean bayarReservasi(Reservasi r, Pembayaran p, double uangDibayar) {
        r.setPembayaran(p);
        if (p.proses(uangDibayar)) {
            return r.konfirmasi();
        }
        return false;
    }

    // ---------- Alur 2: penyelenggaraan event ----------

    public boolean daftarkanEvent(Event e) {
        if (e == null || jumlahEvent >= MAKS_EVENT) {
            return false;
        }
        if (!e.getRuangan().isTersedia()) {
            System.out.println("   [!] Ruangan " + e.getRuangan().getNamaRuangan() + " sedang dipakai.");
            return false;
        }
        e.getRuangan().setTersedia(false);
        daftarEvent[jumlahEvent++] = e;
        return true;
    }

    public boolean bayarEvent(Event e, Pembayaran p, double uangDibayar) {
        e.setPembayaran(p);
        if (p.proses(uangDibayar)) {
            return e.konfirmasi();
        }
        return false;
    }

    // ---------- Laporan ----------

    public void tampilkanDaftarKamar() {
        for (int i = 0; i < jumlahKamar; i++) {
            System.out.println("   " + daftarKamar[i]);
        }
    }

    public void tampilkanDaftarRuangan() {
        for (int i = 0; i < jumlahRuangan; i++) {
            System.out.println("   " + daftarRuangan[i]);
        }
    }

    /** Polymorphism: tarif tiap kamar dihitung sesuai tipenya. */
    public void tampilkanSimulasiTarif(int jumlahMalam) {
        for (int i = 0; i < jumlahKamar; i++) {
            Kamar k = daftarKamar[i];
            System.out.println("   " + k.getNomorKamar() + " (" + k.getTipe() + ") "
                    + jumlahMalam + " malam = " + Pembayaran.rupiah(k.hitungTarif(jumlahMalam))
                    + "  -> " + k.getKeteranganTarif());
        }
    }

    public void tampilkanDaftarEvent() {
        for (int i = 0; i < jumlahEvent; i++) {
            System.out.println("   " + daftarEvent[i]);
        }
    }

    public double hitungTotalPendapatan() {
        double total = 0;
        for (int i = 0; i < jumlahReservasi; i++) {
            if (Reservasi.DIKONFIRMASI.equals(daftarReservasi[i].getStatus())) {
                total += daftarReservasi[i].getPembayaran().getTotalTagihan();
            }
        }
        for (int i = 0; i < jumlahEvent; i++) {
            if (daftarEvent[i].isTerkonfirmasi()) {
                total += daftarEvent[i].getPembayaran().getTotalTagihan();
            }
        }
        return total;
    }
}
