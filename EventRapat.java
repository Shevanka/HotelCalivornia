import java.time.LocalDate;

public class EventRapat extends Event {
    private static final double BIAYA_SNACK_PER_PESERTA = 35000;

    public EventRapat(String idEvent, String namaEvent, LocalDate tanggal, int durasiJam,
                      Tamu pemesan, Ruangan ruangan) {
        super(idEvent, namaEvent, tanggal, durasiJam, pemesan, ruangan);
    }

    @Override
    public double hitungBiaya() {
        return hitungBiayaSewaRuangan() + BIAYA_SNACK_PER_PESERTA * getJumlahPeserta();
    }

    @Override
    public String getJenisEvent() {
        return "Rapat";
    }
}
