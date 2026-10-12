public class Panitia {
    private String idPanitia;
    private String nama;
    private String jabatan;
    private String noTelepon;

    public Panitia(String idPanitia, String nama, String jabatan, String noTelepon) {
        this.idPanitia = idPanitia;
        this.nama = nama;
        this.jabatan = jabatan;
        this.noTelepon = noTelepon;
    }

    public String getIdPanitia() {
        return idPanitia;
    }

    public String getNama() {
        return nama;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    @Override
    public String toString() {
        return nama + " (" + jabatan + ")";
    }
}
