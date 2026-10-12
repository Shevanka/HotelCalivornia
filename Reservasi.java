import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservasi {
    public static final String MENUNGGU_PEMBAYARAN = "MENUNGGU_PEMBAYARAN";
    public static final String DIKONFIRMASI = "DIKONFIRMASI";
    public static final String DIBATALKAN = "DIBATALKAN";

    private String idReservasi;
    private Tamu tamu;
    private Kamar kamar;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String status;
    private Pembayaran pembayaran;

    public Reservasi(String idReservasi, Tamu tamu, Kamar kamar,
                     LocalDate checkIn, LocalDate checkOut) {
        if (tamu == null || kamar == null) {
            throw new IllegalArgumentException("Tamu dan kamar wajib diisi.");
        }
        if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException("Tanggal check-out harus setelah check-in.");
        }
        this.idReservasi = idReservasi;
        this.tamu = tamu;
        this.kamar = kamar;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = MENUNGGU_PEMBAYARAN;
    }

    public String getIdReservasi() {
        return idReservasi;
    }

    public Tamu getTamu() {
        return tamu;
    }

    public Kamar getKamar() {
        return kamar;
    }

    public String getStatus() {
        return status;
    }

    public Pembayaran getPembayaran() {
        return pembayaran;
    }

    public void setPembayaran(Pembayaran pembayaran) {
        this.pembayaran = pembayaran;
    }

    public int getLamaMenginap() {
        return (int) ChronoUnit.DAYS.between(checkIn, checkOut);
    }

    public double hitungTotal() {
        return kamar.hitungTarif(getLamaMenginap());
    }

    public boolean konfirmasi() {
        if (pembayaran != null && pembayaran.isLunas() && !DIBATALKAN.equals(status)) {
            status = DIKONFIRMASI;
            return true;
        }
        return false;
    }

    public void batal() {
        status = DIBATALKAN;
        kamar.setTersedia(true);
    }

    public void cetakBukti() {
        System.out.println("   ============ BUKTI RESERVASI ============");
        System.out.println("   ID Reservasi : " + idReservasi);
        System.out.println("   Tamu         : " + tamu.getNama());
        System.out.println("   Kamar        : " + kamar.getNomorKamar() + " (" + kamar.getTipe() + ")");
        System.out.println("   Check-in     : " + checkIn);
        System.out.println("   Check-out    : " + checkOut + " (" + getLamaMenginap() + " malam)");
        System.out.println("   Tarif        : " + Pembayaran.rupiah(hitungTotal()) + "  [" + kamar.getKeteranganTarif() + "]");
        if (pembayaran != null) {
            System.out.println("   Metode       : " + pembayaran.getMetode());
            System.out.println("   Biaya admin  : " + Pembayaran.rupiah(pembayaran.hitungBiayaAdmin()));
            System.out.println("   Total bayar  : " + Pembayaran.rupiah(pembayaran.getTotalTagihan()));
            System.out.println("   Kembalian    : " + Pembayaran.rupiah(pembayaran.getKembalian()));
        }
        System.out.println("   Status       : " + status);
        System.out.println("   =========================================");
    }

    @Override
    public String toString() {
        return idReservasi + " | " + tamu.getNama() + " | Kamar " + kamar.getNomorKamar()
                + " | " + getLamaMenginap() + " malam | " + status;
    }
}
