package com.workfall.jwt_checking.service;

import com.workfall.jwt_checking.document.Checking;
import com.workfall.jwt_checking.repo.CheckingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckingService {

    private final CheckingRepo checkingRepo;

    public void checking(){
        Checking checking = new Checking();
        checking.setId("Abed");
        checkingRepo.save(checking);
    }
}
