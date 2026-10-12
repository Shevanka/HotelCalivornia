import java.time.LocalDate;

/**
 * Abstract class Event. Biaya acara dihitung berbeda oleh tiap jenis event (polymorphism).
 */
public abstract class Event {
    private static final int MAKS_PANITIA = 10;

    private String idEvent;
    private String namaEvent;
    private LocalDate tanggal;
    private int durasiJam;
    private Tamu pemesan;
    private Ruangan ruangan;
    private Panitia[] daftarPanitia = new Panitia[MAKS_PANITIA];
    private int jumlahPanitia;
    private Peserta[] daftarPeserta;
    private int jumlahPeserta;
    private Pembayaran pembayaran;
    private boolean terkonfirmasi;

    protected Event(String idEvent, String namaEvent, LocalDate tanggal, int durasiJam,
                    Tamu pemesan, Ruangan ruangan) {
        if (durasiJam <= 0) {
            throw new IllegalArgumentException("Durasi event harus lebih dari 0 jam.");
        }
        if (pemesan == null || ruangan == null) {
            throw new IllegalArgumentException("Pemesan dan ruangan wajib diisi.");
        }
        this.idEvent = idEvent;
        this.namaEvent = namaEvent;
        this.tanggal = tanggal;
        this.durasiJam = durasiJam;
        this.pemesan = pemesan;
        this.ruangan = ruangan;
        this.daftarPeserta = new Peserta[ruangan.getKapasitas()];
    }

    public String getIdEvent() {
        return idEvent;
    }

    public String getNamaEvent() {
        return namaEvent;
    }

    public int getDurasiJam() {
        return durasiJam;
    }

    public Tamu getPemesan() {
        return pemesan;
    }

    public Ruangan getRuangan() {
        return ruangan;
    }

    public int getJumlahPeserta() {
        return jumlahPeserta;
    }

    public int getJumlahPanitia() {
        return jumlahPanitia;
    }

    public Pembayaran getPembayaran() {
        return pembayaran;
    }

    public void setPembayaran(Pembayaran pembayaran) {
        this.pembayaran = pembayaran;
    }

    public boolean isTerkonfirmasi() {
        return terkonfirmasi;
    }

    public boolean tambahPanitia(Panitia p) {
        if (p == null || jumlahPanitia >= MAKS_PANITIA) {
            return false;
        }
        daftarPanitia[jumlahPanitia++] = p;
        return true;
    }

    /** Overloading 1: daftar dengan object Peserta. */
    public boolean daftarPeserta(Peserta p) {
        if (p == null) {
            return false;
        }
        if (jumlahPeserta >= ruangan.getKapasitas()) {
            System.out.println("   [!] Pendaftaran ditolak, kapasitas " + ruangan.getNamaRuangan()
                    + " (" + ruangan.getKapasitas() + " orang) sudah penuh: " + p.getNama());
            return false;
        }
        daftarPeserta[jumlahPeserta++] = p;
        return true;
    }

    /** Overloading 2: daftar langsung dengan nama dan instansi (ID dibuat otomatis). */
    public boolean daftarPeserta(String nama, String instansi) {
        String id = String.format("%s-P%02d", idEvent, jumlahPeserta + 1);
        return daftarPeserta(new Peserta(id, nama, instansi));
    }

    public void konfirmasiKehadiranSemua() {
        for (int i = 0; i < jumlahPeserta; i++) {
            daftarPeserta[i].konfirmasiKehadiran();
        }
    }

    protected double hitungBiayaSewaRuangan() {
        return ruangan.hitungSewa(durasiJam);
    }

    public abstract double hitungBiaya();

    public abstract String getJenisEvent();

    public boolean konfirmasi() {
        if (pembayaran != null && pembayaran.isLunas()) {
            terkonfirmasi = true;
            return true;
        }
        return false;
    }

    public void selesai() {
        ruangan.setTersedia(true);
    }

    public void cetakRingkasan() {
        System.out.println("   ============ RINGKASAN EVENT ============");
        System.out.println("   ID / Jenis   : " + idEvent + " / " + getJenisEvent());
        System.out.println("   Nama acara   : " + namaEvent);
        System.out.println("   Tanggal      : " + tanggal + " (" + durasiJam + " jam)");
        System.out.println("   Pemesan      : " + pemesan.getNama());
        System.out.println("   Ruangan      : " + ruangan.getNamaRuangan());
        System.out.print("   Panitia      : ");
        for (int i = 0; i < jumlahPanitia; i++) {
            System.out.print((i > 0 ? ", " : "") + daftarPanitia[i]);
        }
        System.out.println();
        System.out.println("   Peserta (" + jumlahPeserta + "/" + ruangan.getKapasitas() + "):");
        for (int i = 0; i < jumlahPeserta; i++) {
            System.out.println("     " + (i + 1) + ". " + daftarPeserta[i]);
        }
        System.out.println("   Biaya acara  : " + Pembayaran.rupiah(hitungBiaya()));
        if (pembayaran != null) {
            System.out.println("   Metode       : " + pembayaran.getMetode()
                    + " (admin " + Pembayaran.rupiah(pembayaran.hitungBiayaAdmin()) + ")");
            System.out.println("   Total bayar  : " + Pembayaran.rupiah(pembayaran.getTotalTagihan()));
        }
        System.out.println("   Status       : " + (terkonfirmasi ? "DIKONFIRMASI" : "MENUNGGU PEMBAYARAN"));
        System.out.println("   =========================================");
    }

    @Override
    public String toString() {
        return idEvent + " | " + getJenisEvent() + " | " + namaEvent + " | "
                + ruangan.getNamaRuangan() + " | " + jumlahPeserta + " peserta";
    }
}
