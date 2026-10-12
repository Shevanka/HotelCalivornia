public class KamarDeluxe extends Kamar {
    private static final double DISKON_MENGINAP_LAMA = 0.10;

    public KamarDeluxe(String nomorKamar, double hargaPerMalam) {
        super(nomorKamar, "Deluxe", hargaPerMalam);
    }

    @Override
    public double hitungTarif(int jumlahMalam) {
        double subtotal = hitungSubtotal(jumlahMalam);
        if (jumlahMalam >= 3) {
            subtotal -= subtotal * DISKON_MENGINAP_LAMA;
        }
        return subtotal;
    }

    @Override
    public String getKeteranganTarif() {
        return "Diskon 10% untuk menginap minimal 3 malam";
    }
}
