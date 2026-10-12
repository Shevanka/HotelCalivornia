import java.util.Locale;

/**
 * Abstract class Pembayaran. Subclass menentukan biaya admin per metode (polymorphism).
 */
public abstract class Pembayaran {
    private String idPembayaran;
    private double jumlahTagihan;
    private double jumlahDibayar;
    private boolean lunas;

    protected Pembayaran(String idPembayaran, double jumlahTagihan) {
        if (jumlahTagihan <= 0) {
            throw new IllegalArgumentException("Jumlah tagihan harus lebih dari 0.");
        }
        this.idPembayaran = idPembayaran;
        this.jumlahTagihan = jumlahTagihan;
    }

    public String getIdPembayaran() {
        return idPembayaran;
    }

    public double getJumlahTagihan() {
        return jumlahTagihan;
    }

    public double getJumlahDibayar() {
        return jumlahDibayar;
    }

    public boolean isLunas() {
        return lunas;
    }

    public abstract double hitungBiayaAdmin();

    public abstract String getMetode();

    public double getTotalTagihan() {
        return jumlahTagihan + hitungBiayaAdmin();
    }

    /** Memproses pembayaran. Mengembalikan true jika uang yang dibayarkan cukup. */
    public boolean proses(double uangDibayar) {
        if (lunas) {
            System.out.println("   [!] Pembayaran " + idPembayaran + " sudah lunas.");
            return false;
        }
        if (uangDibayar < getTotalTagihan()) {
            System.out.println("   [!] Pembayaran ditolak: uang kurang "
                    + rupiah(getTotalTagihan() - uangDibayar));
            return false;
        }
        this.jumlahDibayar = uangDibayar;
        this.lunas = true;
        return true;
    }

    public double getKembalian() {
        return lunas ? jumlahDibayar - getTotalTagihan() : 0;
    }

    /** Format angka ke Rupiah, misalnya Rp1.500.000 */
    public static String rupiah(double nominal) {
        return String.format(Locale.forLanguageTag("id-ID"), "Rp%,.0f", nominal);
    }

    @Override
    public String toString() {
        return idPembayaran + " | " + getMetode() + " | Tagihan " + rupiah(jumlahTagihan)
                + " + admin " + rupiah(hitungBiayaAdmin()) + " | " + (lunas ? "LUNAS" : "BELUM LUNAS");
    }
}
