package com.multitap.auth.application;

import org.springframework.web.multipart.MultipartFile;

public interface DataInsertService {
    void addMemberFromCsv(MultipartFile file);
}
