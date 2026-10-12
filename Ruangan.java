/**
 * Ruangan untuk penyelenggaraan acara (ballroom, ruang seminar, ruang rapat).
 */
public class Ruangan {
    private static final int MAKS_FASILITAS = 6;

    private String kodeRuangan;
    private String namaRuangan;
    private int kapasitas;
    private double hargaSewaPerJam;
    private boolean tersedia;
    private Fasilitas[] fasilitas = new Fasilitas[MAKS_FASILITAS];
    private int jumlahFasilitas;

    public Ruangan(String kodeRuangan, String namaRuangan, int kapasitas, double hargaSewaPerJam) {
        this.kodeRuangan = kodeRuangan;
        this.namaRuangan = namaRuangan;
        setKapasitas(kapasitas);
        setHargaSewaPerJam(hargaSewaPerJam);
        this.tersedia = true;
    }

    public String getKodeRuangan() {
        return kodeRuangan;
    }

    public String getNamaRuangan() {
        return namaRuangan;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public final void setKapasitas(int kapasitas) {
        if (kapasitas <= 0) {
            throw new IllegalArgumentException("Kapasitas harus lebih dari 0.");
        }
        this.kapasitas = kapasitas;
    }

    public double getHargaSewaPerJam() {
        return hargaSewaPerJam;
    }

    public final void setHargaSewaPerJam(double hargaSewaPerJam) {
        if (hargaSewaPerJam <= 0) {
            throw new IllegalArgumentException("Harga sewa harus lebih dari 0.");
        }
        this.hargaSewaPerJam = hargaSewaPerJam;
    }

    public boolean isTersedia() {
        return tersedia;
    }

    public void setTersedia(boolean tersedia) {
        this.tersedia = tersedia;
    }

    public boolean tambahFasilitas(Fasilitas f) {
        if (f == null || jumlahFasilitas >= MAKS_FASILITAS) {
            return false;
        }
        fasilitas[jumlahFasilitas++] = f;
        return true;
    }

    /** Sewa = tarif per jam x durasi + seluruh biaya fasilitas (sekali per acara). */
    public double hitungSewa(int durasiJam) {
        double total = hargaSewaPerJam * durasiJam;
        for (int i = 0; i < jumlahFasilitas; i++) {
            total += fasilitas[i].getBiayaTambahan();
        }
        return total;
    }

    public String getDaftarFasilitas() {
        if (jumlahFasilitas == 0) {
            return "-";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < jumlahFasilitas; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(fasilitas[i].getNamaFasilitas());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("%s - %s | Kapasitas %d | %s/jam | %s | Fasilitas: %s",
                kodeRuangan, namaRuangan, kapasitas, Pembayaran.rupiah(hargaSewaPerJam),
                tersedia ? "Tersedia" : "Dipakai", getDaftarFasilitas());
    }
}
