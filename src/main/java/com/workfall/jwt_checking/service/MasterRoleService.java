package com.workfall.jwt_checking.service;

import com.workfall.jwt_checking.repo.MasterRoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MasterRoleService {

    private final MasterRoleRepo masterRoleRepo ;


}
