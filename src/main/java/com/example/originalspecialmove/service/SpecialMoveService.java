package com.example.originalspecialmove.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.originalspecialmove.domain.SpecialMove;
import com.example.originalspecialmove.domain.SpecialMoveGallary;
import com.example.originalspecialmove.domain.dto.SpecialMoveDto;
import com.example.originalspecialmove.repository.SpecialMoveGallaryRepository;
import com.example.originalspecialmove.repository.SpecialMoveRepository;

import jakarta.annotation.PostConstruct;
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

    public List<SpecialMoveDto> getSpecialMove(String userId) {
        List<SpecialMoveGallary> spgList = spgRepository.findByLineUserId(userId);

        List<SpecialMoveDto> spList = new ArrayList<>();
        for (SpecialMoveGallary spg : spgList) {
            SpecialMove sp = repository.getReferenceById(spg.getSpecialMoveId());

            SpecialMoveDto spDto = new SpecialMoveDto(sp);
            spList.add(spDto);
        }

        return spList;
    }

    // @PostConstruct
    // public void init() {
    // // テストデータ１
    // SpecialMove sp = new SpecialMove();
    // sp.setUserId("U5a62fcb7b4777ad78174bb14a4c31a59");
    // sp.setSpName("骸帝の魔袍");
    // sp.setFurigana(null);
    // sp.setHeading("己が身に骸を宿す、最凶最悪の魔");
    // sp.setDescription("愛する者の魂を継承し、骸として己の身に宿す。魔の力を手に入れることで、凶悪な魔力を発することができる。闇を統べる者が持つ力。");
    // sp.setImageName("qstLKSVVJuseZTYnSwls.png");
    // sp.setRegistedTime(LocalDateTime.of(2023, 10, 22, 22, 10));
    // repository.save(sp);
    // SpecialMoveGallary spg = new SpecialMoveGallary();
    // spg.setLineUserId("U5a62fcb7b4777ad78174bb14a4c31a59");
    // spg.setAuthorLineUserId("U5a62fcb7b4777ad78174bb14a4c31a59");
    // spg.setSpecialMoveId(1L);
    // spg.setGetTime(LocalDateTime.of(2023, 10, 22, 22, 10));
    // spgRepository.save(spg);

    // // テストデータ２
    // SpecialMove sp2 = new SpecialMove();
    // sp2.setUserId("U5a62fcb7b4777ad78174bb14a4c31a59");
    // sp2.setSpName("超強い技");
    // sp2.setFurigana("ギルティ・ギルティ");
    // sp2.setHeading("断罪");
    // sp2.setDescription("最強技");
    // sp2.setImageName("czOwoGfwooGSxhmarBCE.png");
    // sp2.setRegistedTime(LocalDateTime.of(2023, 10, 23, 22, 10));
    // repository.save(sp2);
    // SpecialMoveGallary spg2 = new SpecialMoveGallary();
    // spg2.setLineUserId("U5a62fcb7b4777ad78174bb14a4c31a59");
    // spg2.setAuthorLineUserId("U5a62fcb7b4777ad78174bb14a4c31a59");
    // spg2.setSpecialMoveId(2L);
    // spg2.setGetTime(LocalDateTime.of(2023, 10, 23, 22, 10));
    // spgRepository.save(spg2);

    // // テストデータ３
    // SpecialMove sp3 = new SpecialMove();
    // sp3.setUserId("hoge");
    // sp3.setSpName("ノーイメージ");
    // sp3.setFurigana(null);
    // sp3.setHeading("シンプル・イズ・ベスト");
    // sp3.setDescription("相手は死ぬ");
    // sp3.setImageName("noimage.png");
    // sp3.setRegistedTime(LocalDateTime.of(2023, 10, 25, 22, 10));
    // repository.save(sp3);
    // SpecialMoveGallary spg3 = new SpecialMoveGallary();
    // spg3.setLineUserId("U5a62fcb7b4777ad78174bb14a4c31a59");
    // spg3.setAuthorLineUserId("hoge");
    // spg3.setSpecialMoveId(3L);
    // spg3.setGetTime(LocalDateTime.of(2023, 10, 25, 22, 10));
    // spgRepository.save(spg3);
    // }

}
