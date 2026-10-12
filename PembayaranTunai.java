public class PembayaranTunai extends Pembayaran {
    public PembayaranTunai(String idPembayaran, double jumlahTagihan) {
        super(idPembayaran, jumlahTagihan);
    }

    @Override
    public double hitungBiayaAdmin() {
        return 0;
    }

    @Override
    public String getMetode() {
        return "Tunai";
    }
}
