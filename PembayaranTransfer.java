public class PembayaranTransfer extends Pembayaran {
    private static final double BIAYA_ADMIN_FLAT = 2500;

    public PembayaranTransfer(String idPembayaran, double jumlahTagihan) {
        super(idPembayaran, jumlahTagihan);
    }

    @Override
    public double hitungBiayaAdmin() {
        return BIAYA_ADMIN_FLAT;
    }

    @Override
    public String getMetode() {
        return "Transfer Bank";
    }
}
