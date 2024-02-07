package com.drcome.project.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drcome.project.common.domain.Detailcode;
import com.drcome.project.common.repository.DetailCodeRepository;

@Service
public class DetailCodeService {

    @Autowired
    private DetailCodeRepository detailCodeRepository;

    public Map<String, List<Detailcode>> getAlphabetGroups() {
        List<Detailcode> allDetailCodes = detailCodeRepository.findAll();

        // 알파벳으로 그룹화
        Map<String, List<Detailcode>> alphabetGroups = allDetailCodes.stream()
                .collect(Collectors.groupingBy(detailCode -> detailCode.getDetailCode().substring(0, 1)));

        return alphabetGroups;
    }
}
