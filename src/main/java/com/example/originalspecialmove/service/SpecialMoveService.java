package com.example.originalspecialmove.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.originalspecialmove.domain.SpecialMove;
import com.example.originalspecialmove.domain.SpecialMoveGallary;
import com.example.originalspecialmove.repository.SpecialMoveGallaryRepository;
import com.example.originalspecialmove.repository.SpecialMoveRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SpecialMoveService {
    @Autowired
    private SpecialMoveRepository repository;
    @Autowired
    private SpecialMoveGallaryRepository spgRepository;

    public SpecialMove saveSpecialMove(SpecialMove specialMove) {
        return repository.save(specialMove);
    }

    public SpecialMoveGallary saveSPG(SpecialMoveGallary spg) {
        return spgRepository.save(spg);
    }
}
