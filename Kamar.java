/**
 * Abstract class Kamar. Subclass menentukan cara hitung tarif (polymorphism).
 */
public abstract class Kamar {
    private static final int MAKS_FASILITAS = 5;

    private String nomorKamar;
    private String tipe;
    private double hargaPerMalam;
    private boolean tersedia;
    private Fasilitas[] fasilitas = new Fasilitas[MAKS_FASILITAS];
    private int jumlahFasilitas;

    protected Kamar(String nomorKamar, String tipe, double hargaPerMalam) {
        this.nomorKamar = nomorKamar;
        this.tipe = tipe;
        setHargaPerMalam(hargaPerMalam);
        this.tersedia = true;
    }

    public String getNomorKamar() {
        return nomorKamar;
    }

    public String getTipe() {
        return tipe;
    }

    public double getHargaPerMalam() {
        return hargaPerMalam;
    }

    public final void setHargaPerMalam(double hargaPerMalam) {
        if (hargaPerMalam <= 0) {
            throw new IllegalArgumentException("Harga kamar harus lebih dari 0.");
        }
        this.hargaPerMalam = hargaPerMalam;
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

    protected double getBiayaFasilitasPerMalam() {
        double total = 0;
        for (int i = 0; i < jumlahFasilitas; i++) {
            total += fasilitas[i].getBiayaTambahan();
        }
        return total;
    }

    protected double hitungSubtotal(int jumlahMalam) {
        return (hargaPerMalam + getBiayaFasilitasPerMalam()) * jumlahMalam;
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

    /** Dioverride oleh subclass: total tarif untuk sekian malam. */
    public abstract double hitungTarif(int jumlahMalam);

    /** Keterangan kebijakan tarif tipe kamar. */
    public abstract String getKeteranganTarif();

    @Override
    public String toString() {
        return String.format("Kamar %s [%s] %s/malam | %s | Fasilitas: %s",
                nomorKamar, tipe, Pembayaran.rupiah(hargaPerMalam),
                tersedia ? "Tersedia" : "Terisi", getDaftarFasilitas());
    }
}
