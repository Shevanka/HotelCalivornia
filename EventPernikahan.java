import java.time.LocalDate;

public class EventPernikahan extends Event {
    private static final double BIAYA_DEKORASI = 7500000;
    private static final double BIAYA_KATERING_PER_TAMU = 150000;

    public EventPernikahan(String idEvent, String namaEvent, LocalDate tanggal, int durasiJam,
                           Tamu pemesan, Ruangan ruangan) {
        super(idEvent, namaEvent, tanggal, durasiJam, pemesan, ruangan);
    }

    @Override
    public double hitungBiaya() {
        return hitungBiayaSewaRuangan() + BIAYA_DEKORASI
                + BIAYA_KATERING_PER_TAMU * getJumlahPeserta();
    }

    @Override
    public String getJenisEvent() {
        return "Pernikahan";
    }
}
