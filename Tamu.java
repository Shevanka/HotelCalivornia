public class Tamu {
    private static final int MAKS_RIWAYAT = 10;

    private String idTamu;
    private String nama;
    private String noTelepon;
    private String email;
    private Reservasi[] riwayatReservasi = new Reservasi[MAKS_RIWAYAT];
    private int jumlahRiwayat;

    public Tamu(String idTamu, String nama, String noTelepon, String email) {
        this.idTamu = idTamu;
        setNama(nama);
        this.noTelepon = noTelepon;
        this.email = email;
    }

    public String getIdTamu() {
        return idTamu;
    }

    public String getNama() {
        return nama;
    }

    public final void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama tamu tidak boleh kosong.");
        }
        this.nama = nama;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean tambahRiwayat(Reservasi r) {
        if (r == null || jumlahRiwayat >= MAKS_RIWAYAT) {
            return false;
        }
        riwayatReservasi[jumlahRiwayat++] = r;
        return true;
    }

    public void tampilkanRiwayat() {
        System.out.println("Riwayat reservasi " + nama + ":");
        if (jumlahRiwayat == 0) {
            System.out.println("  (belum ada)");
        }
        for (int i = 0; i < jumlahRiwayat; i++) {
            System.out.println("  - " + riwayatReservasi[i].getIdReservasi()
                    + " | Kamar " + riwayatReservasi[i].getKamar().getNomorKamar()
                    + " | " + riwayatReservasi[i].getStatus());
        }
    }

    @Override
    public String toString() {
        return idTamu + " - " + nama + " (" + noTelepon + ")";
    }
}
