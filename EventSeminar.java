import java.time.LocalDate;

public class EventSeminar extends Event {
    private static final double BIAYA_MATERI_KONSUMSI_PER_PESERTA = 75000;

    public EventSeminar(String idEvent, String namaEvent, LocalDate tanggal, int durasiJam,
                        Tamu pemesan, Ruangan ruangan) {
        super(idEvent, namaEvent, tanggal, durasiJam, pemesan, ruangan);
    }

    @Override
    public double hitungBiaya() {
        return hitungBiayaSewaRuangan() + BIAYA_MATERI_KONSUMSI_PER_PESERTA * getJumlahPeserta();
    }

    @Override
    public String getJenisEvent() {
        return "Seminar";
    }
}
