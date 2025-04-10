package id.ac.ui.cs.advprog.log.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class LogBuilderTest {

    private Log log;

    @BeforeEach
    void setUp() {
        log = new LogBuilder()
                .judul("Asistensi Minggu 1")
                .keterangan("Membantu praktikum struktur data")
                .kategori("Asistensi")
                .tanggalLog(LocalDate.of(2025, 4, 6))
                .waktuMulai(LocalTime.of(10, 0))
                .waktuSelesai(LocalTime.of(12, 0))
                .pesanUntukDosen("Upload tugas selesai")
                .status("Menunggu")
                .build();
    }

    @Test
    void testBuildLog_Success() {
        assertNotNull(log);
        assertEquals("Asistensi Minggu 1", log.getJudul());
        assertEquals("Asistensi", log.getKategori());
        assertEquals("Menunggu", log.getStatus());
        assertEquals(LocalTime.of(10, 0), log.getWaktuMulai());
        assertEquals(LocalTime.of(12, 0), log.getWaktuSelesai());
        assertEquals("Upload tugas selesai", log.getPesanUntukDosen());
    }

    @Test
    void testBuildLog_EmptyJudul_ShouldThrowException() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new LogBuilder()
                    .judul("")
                    .keterangan("Deskripsi kosong")
                    .kategori("Mengawas")
                    .tanggalLog(LocalDate.now())
                    .waktuMulai(LocalTime.of(9, 0))
                    .waktuSelesai(LocalTime.of(10, 0))
                    .status("Menunggu")
                    .build();
        });
        assertTrue(ex.getMessage().contains("Judul tidak boleh kosong"));
    }

    @Test
    void testBuildLog_TanggalMasaDepan_ShouldThrowException() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new LogBuilder()
                    .judul("Log Masa Depan")
                    .keterangan("Tidak valid")
                    .kategori("Lain-lain")
                    .tanggalLog(LocalDate.now().plusDays(1))
                    .waktuMulai(LocalTime.of(9, 0))
                    .waktuSelesai(LocalTime.of(11, 0))
                    .status("Menunggu")
                    .build();
        });
        assertTrue(ex.getMessage().contains("Tanggal log tidak boleh di masa depan"));
    }

    @Test
    void testBuildLog_WaktuSelesaiBeforeMulai_ShouldThrowException() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new LogBuilder()
                    .judul("Log Salah")
                    .keterangan("Waktu tidak valid")
                    .kategori("Mengoreksi")
                    .tanggalLog(LocalDate.now())
                    .waktuMulai(LocalTime.of(14, 0))
                    .waktuSelesai(LocalTime.of(13, 0))
                    .status("Menunggu")
                    .build();
        });
        assertTrue(ex.getMessage().contains("Waktu selesai harus setelah waktu mulai"));
    }
}
