public class Peserta {
    private String idPeserta;
    private String nama;
    private String instansi;
    private boolean hadir;

    public Peserta(String idPeserta, String nama, String instansi) {
        this.idPeserta = idPeserta;
        this.nama = nama;
        this.instansi = instansi;
        this.hadir = false;
    }

    public String getIdPeserta() {
        return idPeserta;
    }

    public String getNama() {
        return nama;
    }

    public String getInstansi() {
        return instansi;
    }

    public boolean isHadir() {
        return hadir;
    }

    public void konfirmasiKehadiran() {
        this.hadir = true;
    }

    @Override
    public String toString() {
        return idPeserta + " - " + nama + " (" + instansi + ")" + (hadir ? " [hadir]" : "");
    }
}
