import java.time.LocalDate;

public class Main {

    private static void judul(String teks) {
        System.out.println();
        System.out.println("=== " + teks + " ===");
    }

    public static void main(String[] args) {
        // ---------- 1. Setup data master ----------
        judul("1. SETUP HOTEL CALIVORNIA");
        Hotel hotel = new Hotel("Hotel Calivornia", "Jl. Raya Darmo No. 1, Surabaya");

        Fasilitas wifi = new Fasilitas("WiFi Premium", 25000);
        Fasilitas sarapan = new Fasilitas("Sarapan", 50000);
        Fasilitas proyektor = new Fasilitas("Proyektor", 500000);
        Fasilitas soundSystem = new Fasilitas("Sound System", 1000000);
        Fasilitas panggung = new Fasilitas("Panggung", 2000000);
        hotel.tambahFasilitas(wifi);
        hotel.tambahFasilitas(sarapan);
        hotel.tambahFasilitas(proyektor);
        hotel.tambahFasilitas(soundSystem);
        hotel.tambahFasilitas(panggung);

        Kamar k101 = new KamarStandard("101", 400000);
        Kamar k102 = new KamarStandard("102", 450000);
        Kamar k201 = new KamarDeluxe("201", 750000);
        Kamar k301 = new KamarSuite("301", 1500000);
        k101.tambahFasilitas(wifi);
        k201.tambahFasilitas(wifi);
        k201.tambahFasilitas(sarapan);
        k301.tambahFasilitas(hotel.getFasilitas("WiFi Premium"));
        k301.tambahFasilitas(hotel.getFasilitas("Sarapan"));
        hotel.tambahKamar(k101);
        hotel.tambahKamar(k102);
        hotel.tambahKamar(k201);
        hotel.tambahKamar(k301);

        Ruangan rapat = new Ruangan("R-01", "Ruang Rapat Melati", 3, 300000);
        Ruangan seminar = new Ruangan("R-02", "Ruang Seminar Cendana", 50, 800000);
        Ruangan ballroom = new Ruangan("R-03", "Grand Ballroom", 300, 2500000);
        rapat.tambahFasilitas(proyektor);
        seminar.tambahFasilitas(proyektor);
        seminar.tambahFasilitas(soundSystem);
        ballroom.tambahFasilitas(soundSystem);
        ballroom.tambahFasilitas(panggung);
        hotel.tambahRuangan(rapat);
        hotel.tambahRuangan(seminar);
        hotel.tambahRuangan(ballroom);

        System.out.println(hotel.getNamaHotel() + " | " + hotel.getAlamat());
        System.out.println("Daftar kamar:");
        hotel.tampilkanDaftarKamar();
        System.out.println("Daftar ruangan:");
        hotel.tampilkanDaftarRuangan();

        // ---------- 2. Polymorphism: Kamar ----------
        judul("2. POLYMORPHISM - TARIF KAMAR (array Kamar[] berisi berbagai subclass)");
        hotel.tampilkanSimulasiTarif(3);

        // ---------- 3. Overloading ----------
        judul("3. OVERLOADING - cariKamar()");
        Kamar[] hasil1 = hotel.cariKamar("Standard");
        System.out.println("   cariKamar(\"Standard\") -> " + hasil1.length + " kamar:");
        for (int i = 0; i < hasil1.length; i++) {
            System.out.println("     " + hasil1[i]);
        }
        Kamar[] hasil2 = hotel.cariKamar("Standard", 420000);
        System.out.println("   cariKamar(\"Standard\", 420000) -> " + hasil2.length + " kamar:");
        for (int i = 0; i < hasil2.length; i++) {
            System.out.println("     " + hasil2[i]);
        }

        // ---------- 4. Alur bisnis 1: reservasi kamar ----------
        judul("4. ALUR BISNIS 1 - RESERVASI KAMAR");
        Tamu budi = new Tamu("T-001", "Budi Santoso", "081234567890", "budi@mail.com");

        System.out.println("-> Validasi tanggal salah (check-out sebelum check-in):");
        try {
            hotel.buatReservasi(budi, "201", LocalDate.of(2026, 11, 5), LocalDate.of(2026, 11, 2));
        } catch (IllegalArgumentException e) {
            System.out.println("   [!] Error: " + e.getMessage());
        }

        System.out.println("-> Reservasi kamar 201 (Deluxe), 3 malam:");
        Reservasi rsv = hotel.buatReservasi(budi, "201",
                LocalDate.of(2026, 11, 2), LocalDate.of(2026, 11, 5));
        System.out.println("   Dibuat: " + rsv);
        System.out.println("   Total tarif: " + Pembayaran.rupiah(rsv.hitungTotal()));

        System.out.println("-> Reservasi kamar yang sama oleh tamu lain:");
        Tamu sari = new Tamu("T-002", "Sari Wulandari", "085612345678", "sari@mail.com");
        hotel.buatReservasi(sari, "201", LocalDate.of(2026, 11, 3), LocalDate.of(2026, 11, 4));

        Pembayaran bayarRsv = new PembayaranTransfer("PAY-001", rsv.hitungTotal());
        System.out.println("-> Pembayaran pertama (uang kurang):");
        hotel.bayarReservasi(rsv, bayarRsv, 1000000);
        System.out.println("   Status reservasi: " + rsv.getStatus());
        System.out.println("-> Pembayaran kedua (uang cukup, ada kembalian):");
        boolean lunasRsv = hotel.bayarReservasi(rsv, bayarRsv, 2500000);
        System.out.println("   Berhasil: " + lunasRsv);
        rsv.cetakBukti();
        budi.tampilkanRiwayat();

        // ---------- 5. Polymorphism: Pembayaran ----------
        judul("5. POLYMORPHISM - METODE PEMBAYARAN (array Pembayaran[])");
        Pembayaran[] contohBayar = {
            new PembayaranTunai("DEMO-1", 1000000),
            new PembayaranTransfer("DEMO-2", 1000000),
            new PembayaranKartu("DEMO-3", 1000000)
        };
        for (int i = 0; i < contohBayar.length; i++) {
            System.out.println("   " + contohBayar[i].getMetode() + " -> admin "
                    + Pembayaran.rupiah(contohBayar[i].hitungBiayaAdmin())
                    + ", total " + Pembayaran.rupiah(contohBayar[i].getTotalTagihan()));
        }

        // ---------- 6. Alur bisnis 2: event ----------
        judul("6. ALUR BISNIS 2 - PENYELENGGARAAN EVENT");
        Panitia p1 = new Panitia("PN-01", "Rina Kusuma", "Ketua Panitia", "081111111111");
        Panitia p2 = new Panitia("PN-02", "Dedi Pratama", "Sekretaris", "082222222222");
        Panitia p3 = new Panitia("PN-03", "Maya Lestari", "Bendahara", "083333333333");

        // Event A: Seminar
        System.out.println("-> Event A: Seminar");
        Event seminarEvent = new EventSeminar("EV-001", "Seminar Teknologi AI",
                LocalDate.of(2026, 12, 1), 4, budi, hotel.getRuangan("R-02"));
        hotel.daftarkanEvent(seminarEvent);
        seminarEvent.tambahPanitia(p1);
        seminarEvent.tambahPanitia(p2);
        seminarEvent.daftarPeserta(new Peserta("PS-001", "Andi", "ITS"));
        seminarEvent.daftarPeserta(new Peserta("PS-002", "Bunga", "PENS"));
        seminarEvent.daftarPeserta("Citra", "UNAIR");     // overloading
        seminarEvent.daftarPeserta("Doni", "UNESA");      // overloading
        seminarEvent.konfirmasiKehadiranSemua();

        Pembayaran bayarSeminar = new PembayaranKartu("PAY-002", seminarEvent.hitungBiaya());
        hotel.bayarEvent(seminarEvent, bayarSeminar, bayarSeminar.getTotalTagihan());
        seminarEvent.cetakRingkasan();

        // Event B: Rapat (uji kapasitas)
        System.out.println("-> Event B: Rapat (kapasitas ruangan 3, mencoba mendaftarkan 4 peserta)");
        Event rapatEvent = new EventRapat("EV-002", "Rapat Evaluasi Triwulan",
                LocalDate.of(2026, 12, 2), 2, sari, hotel.getRuangan("R-01"));
        hotel.daftarkanEvent(rapatEvent);
        rapatEvent.tambahPanitia(p3);
        rapatEvent.daftarPeserta("Eko", "PT Maju");
        rapatEvent.daftarPeserta("Fina", "PT Maju");
        rapatEvent.daftarPeserta("Gilang", "PT Maju");
        rapatEvent.daftarPeserta("Hana", "PT Maju");      // ditolak: penuh
        Pembayaran bayarRapat = new PembayaranTunai("PAY-003", rapatEvent.hitungBiaya());
        hotel.bayarEvent(rapatEvent, bayarRapat, bayarRapat.getTotalTagihan() + 5000);
        rapatEvent.cetakRingkasan();

        // Event C: Pernikahan
        System.out.println("-> Event C: Pernikahan");
        Event nikahEvent = new EventPernikahan("EV-003", "Resepsi Pernikahan Rina & Dimas",
                LocalDate.of(2026, 12, 20), 6, sari, hotel.getRuangan("R-03"));
        hotel.daftarkanEvent(nikahEvent);
        nikahEvent.tambahPanitia(p1);
        for (int i = 1; i <= 5; i++) {
            nikahEvent.daftarPeserta("Tamu Undangan " + i, "Keluarga");
        }
        nikahEvent.cetakRingkasan();  // belum dibayar

        System.out.println("-> Mencoba memakai ruangan yang sedang dipakai:");
        Event bentrok = new EventRapat("EV-004", "Rapat Dadakan", LocalDate.of(2026, 12, 2), 1,
                budi, hotel.getRuangan("R-01"));
        hotel.daftarkanEvent(bentrok);

        // ---------- 7. Polymorphism: Event ----------
        judul("7. POLYMORPHISM - BIAYA EVENT (array Event[])");
        Event[] semuaEvent = { seminarEvent, rapatEvent, nikahEvent };
        for (int i = 0; i < semuaEvent.length; i++) {
            System.out.println("   " + semuaEvent[i].getJenisEvent() + " -> biaya "
                    + Pembayaran.rupiah(semuaEvent[i].hitungBiaya())
                    + " | " + semuaEvent[i]);
        }

        // ---------- 8. Laporan ----------
        judul("8. LAPORAN HOTEL");
        System.out.println("   Daftar event terdaftar:");
        hotel.tampilkanDaftarEvent();
        System.out.println("   Total pendapatan (transaksi lunas): "
                + Pembayaran.rupiah(hotel.hitungTotalPendapatan()));

        seminarEvent.selesai();
        rsv.batal();
        System.out.println("   Setelah seminar selesai & reservasi dibatalkan, Ruang Seminar dan Kamar 201 kembali tersedia:");
        hotel.tampilkanDaftarRuangan();
        System.out.println("   Kamar 201 tersedia: " + k201.isTersedia());

        // ---------- 9. Enkapsulasi ----------
        judul("9. ENKAPSULASI - VALIDASI SETTER");
        try {
            k101.setHargaPerMalam(-1000);
        } catch (IllegalArgumentException e) {
            System.out.println("   [!] setHargaPerMalam(-1000) ditolak: " + e.getMessage());
        }
        try {
            rapat.setKapasitas(0);
        } catch (IllegalArgumentException e) {
            System.out.println("   [!] setKapasitas(0) ditolak: " + e.getMessage());
        }
    }
}
