public class KamarStandard extends Kamar {
    public KamarStandard(String nomorKamar, double hargaPerMalam) {
        super(nomorKamar, "Standard", hargaPerMalam);
    }

    @Override
    public double hitungTarif(int jumlahMalam) {
        return hitungSubtotal(jumlahMalam);
    }

    @Override
    public String getKeteranganTarif() {
        return "Tarif normal tanpa potongan";
    }
}
