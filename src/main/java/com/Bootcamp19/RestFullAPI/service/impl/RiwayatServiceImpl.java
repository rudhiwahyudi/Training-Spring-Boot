package com.Bootcamp19.RestFullAPI.service.impl;

import com.Bootcamp19.RestFullAPI.service.RiwayatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RiwayatServiceImpl implements RiwayatService {

    @Async
    @Override
    public void catatanRiwayat(Long id, String kalimat) {
        log.info("[{}] mulai catat riwayat id={}", Thread.currentThread().getName(), id);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }

        log.info("[{}] mulai catat riwayat id={}", Thread.currentThread().getName(), id);
    }
}
