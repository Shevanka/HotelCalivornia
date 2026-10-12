public class KamarSuite extends Kamar {
    private static final double SERVICE_CHARGE = 0.15;

    public KamarSuite(String nomorKamar, double hargaPerMalam) {
        super(nomorKamar, "Suite", hargaPerMalam);
    }

    @Override
    public double hitungTarif(int jumlahMalam) {
        double subtotal = hitungSubtotal(jumlahMalam);
        return subtotal + subtotal * SERVICE_CHARGE;
    }

    @Override
    public String getKeteranganTarif() {
        return "Ditambah service charge 15%";
    }
}
