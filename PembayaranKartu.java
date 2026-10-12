public class PembayaranKartu extends Pembayaran {
    private static final double PERSEN_ADMIN = 0.02;

    public PembayaranKartu(String idPembayaran, double jumlahTagihan) {
        super(idPembayaran, jumlahTagihan);
    }

    @Override
    public double hitungBiayaAdmin() {
        return getJumlahTagihan() * PERSEN_ADMIN;
    }

    @Override
    public String getMetode() {
        return "Kartu Kredit/Debit";
    }
}
