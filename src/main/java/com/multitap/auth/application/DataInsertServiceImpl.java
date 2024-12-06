package com.multitap.auth.application;

import com.multitap.auth.common.exception.BaseException;
import com.multitap.auth.common.response.BaseResponseStatus;
import com.multitap.auth.entity.Member;
import com.multitap.auth.entity.Role;
import com.multitap.auth.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataInsertServiceImpl implements DataInsertService {

    private final MemberRepository memberRepository;

    @Override
    public void addMemberFromCsv(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            List<CSVRecord> records = csvParser.getRecords();

            for (CSVRecord record : records) {
                String uuid = record.get("uuid");
                String name = record.get("name");
                String nickName = record.get("nickName");
                String email = record.get("email");
                String accountId = record.get("accountId");
                String password = record.get("password");
                String phoneNumber = record.get("phoneNumber");
                Role role = Role.valueOf(record.get("role"));

                Member member = Member.builder()
                        .uuid(uuid)
                        .name(name)
                        .nickName(nickName)
                        .email(email)
                        .accountId(accountId)
                        .password(password)
                        .phoneNumber(phoneNumber)
                        .role(role)
                        .build();

                memberRepository.save(member);
            }
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

