/**
 * Fasilitas tambahan hotel (WiFi, Proyektor, Sound System, dll).
 * Biaya tambahan: per malam untuk Kamar, per acara untuk Ruangan.
 */
public class Fasilitas {
    private String namaFasilitas;
    private double biayaTambahan;

    public Fasilitas(String namaFasilitas, double biayaTambahan) {
        setNamaFasilitas(namaFasilitas);
        setBiayaTambahan(biayaTambahan);
    }

    public String getNamaFasilitas() {
        return namaFasilitas;
    }

    public final void setNamaFasilitas(String namaFasilitas) {
        if (namaFasilitas == null || namaFasilitas.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama fasilitas tidak boleh kosong.");
        }
        this.namaFasilitas = namaFasilitas;
    }

    public double getBiayaTambahan() {
        return biayaTambahan;
    }

    public final void setBiayaTambahan(double biayaTambahan) {
        if (biayaTambahan < 0) {
            throw new IllegalArgumentException("Biaya fasilitas tidak boleh negatif.");
        }
        this.biayaTambahan = biayaTambahan;
    }

    @Override
    public String toString() {
        return namaFasilitas + " (" + Pembayaran.rupiah(biayaTambahan) + ")";
    }
}
