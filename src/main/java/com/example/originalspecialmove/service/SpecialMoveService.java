package com.example.originalspecialmove.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.originalspecialmove.domain.CheckedSp;
import com.example.originalspecialmove.domain.SpecialMove;
import com.example.originalspecialmove.domain.SpecialMoveDeck;
import com.example.originalspecialmove.domain.SpecialMoveGallary;
import com.example.originalspecialmove.domain.dto.CheckedSpDto;
import com.example.originalspecialmove.domain.dto.SpecialMoveDeckDto;
import com.example.originalspecialmove.domain.dto.SpecialMoveDto;
import com.example.originalspecialmove.repository.CheckedSpRepository;
import com.example.originalspecialmove.repository.SpecialMoveDeckRepository;
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
    @Autowired
    private SpecialMoveDeckRepository spDeckRepository;
    @Autowired
    private CheckedSpRepository checkedSpRepository;

    public SpecialMove saveSpecialMove(SpecialMove specialMove) {
        return repository.save(specialMove);
    }

    public SpecialMoveGallary saveSPG(SpecialMoveGallary spg) {
        return spgRepository.save(spg);
    }

    public SpecialMoveGallary registSPG(Long spId, String userId) {
        SpecialMove sp = repository.getReferenceById(spId);
        SpecialMoveGallary spg = new SpecialMoveGallary();
        spg.setLineUserId(userId);
        spg.setAuthorLineUserId(sp.getUserId());
        spg.setSpecialMoveId(sp.getId());
        spg.setGetTime(LocalDateTime.now());
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

    public List<SpecialMoveDto> getSpecialMoveBattle(String userId) {
        List<SpecialMove> specialMoves = repository.findByUserIdNot(userId);

        List<SpecialMoveDto> spList = new ArrayList<>();
        for (SpecialMove sp : specialMoves) {
            SpecialMoveDto spDto = new SpecialMoveDto(sp);
            spList.add(spDto);
        }

        return spList;
    }

    public SpecialMoveDeck saveSpecialMoveDeck(SpecialMoveDeck spDeck) {
        return spDeckRepository.save(spDeck);
    }

    public List<SpecialMoveDeckDto> getSpecialMoveDeck(String userId) throws Exception {
        List<SpecialMoveDeck> spDecks = spDeckRepository.findByLineUserIdAndIsSettingTrue(userId);

        if (spDecks.size() > 5) {
            throw new Exception();
        }

        List<SpecialMoveDeckDto> spList = new ArrayList<>();
        for (SpecialMoveDeck spDeck : spDecks) {
            SpecialMove sp = repository.getReferenceById(spDeck.getSpecialMoveId());

            SpecialMoveDeckDto spDto = new SpecialMoveDeckDto(new SpecialMoveDto(sp), spDeck.getId());
            spList.add(spDto);
        }

        return spList;
    }

    public void deleteDeck(Long deckId) {
        spDeckRepository.deleteById(deckId);
    }

    public void updateSpBattleResult(Long spId, Long yourSpId) {
        SpecialMove sp = repository.getReferenceById(spId);
        SpecialMove yourSp = repository.getReferenceById(yourSpId);
        repository.save(sp.winResult());
        repository.save(yourSp.loseResult());
    }

    public void checkSp(Long spId, String userId) {
        CheckedSp checkedSp = new CheckedSp();
        checkedSp.setSpId(spId);
        checkedSp.setUserId(userId);
        checkedSp.setCheckedTime(LocalDateTime.now());
        checkedSpRepository.save(checkedSp);
    }

    public List<CheckedSpDto> getCheckedSP(String userId) {
        List<CheckedSp> checkedSps = checkedSpRepository.findByUserId(userId);
        List<CheckedSpDto> checkedSpList = new ArrayList<>();
        for (CheckedSp sp : checkedSps) {
            checkedSpList.add(new CheckedSpDto(sp));
        }
        return checkedSpList;
    }

    // @PostConstruct
    public void init() {
        // テストデータ１
        SpecialMove sp = new SpecialMove();
        sp.setUserId("fuga");
        sp.setSpName("骸帝の魔袍");
        sp.setFurigana(null);
        sp.setHeading("己が身に骸を宿す、最凶最悪の魔");
        sp.setDescription("愛する者の魂を継承し、骸として己の身に宿す。魔の力を手に入れることで、凶悪な魔力を発することができる。闇を統べる者が持つ力。");
        sp.setImageName("qstLKSVVJuseZTYnSwls.png");
        sp.setRegistedTime(LocalDateTime.of(2023, 10, 22, 22, 10));
        repository.save(sp);
        SpecialMoveGallary spg = new SpecialMoveGallary();
        spg.setLineUserId("fuga");
        spg.setAuthorLineUserId("fuga");
        spg.setSpecialMoveId(1L);
        spg.setGetTime(LocalDateTime.of(2023, 10, 22, 22, 10));
        spgRepository.save(spg);

        // テストデータ２
        SpecialMove sp2 = new SpecialMove();
        sp2.setUserId("U5a62fcb7b4777ad78174bb14a4c31a59");
        sp2.setSpName("超強い技");
        sp2.setFurigana("ギルティ・ギルティ");
        sp2.setHeading("断罪");
        sp2.setDescription("最強技");
        sp2.setImageName("czOwoGfwooGSxhmarBCE.png");
        sp2.setRegistedTime(LocalDateTime.of(2023, 10, 23, 22, 10));
        repository.save(sp2);
        SpecialMoveGallary spg2 = new SpecialMoveGallary();
        spg2.setLineUserId("U5a62fcb7b4777ad78174bb14a4c31a59");
        spg2.setAuthorLineUserId("U5a62fcb7b4777ad78174bb14a4c31a59");
        spg2.setSpecialMoveId(2L);
        spg2.setGetTime(LocalDateTime.of(2023, 10, 23, 22, 10));
        spgRepository.save(spg2);

        // テストデータ３
        SpecialMove sp3 = new SpecialMove();
        sp3.setUserId("fuga");
        sp3.setSpName("ノーイメージ");
        sp3.setFurigana(null);
        sp3.setHeading("シンプル・イズ・ベスト");
        sp3.setDescription("相手は死ぬ");
        sp3.setImageName("noimage.png");
        sp3.setRegistedTime(LocalDateTime.of(2023, 10, 25, 22, 10));
        repository.save(sp3);
        SpecialMoveGallary spg3 = new SpecialMoveGallary();
        spg3.setLineUserId("U5a62fcb7b4777ad78174bb14a4c31a59");
        spg3.setAuthorLineUserId("hoge");
        spg3.setSpecialMoveId(3L);
        spg3.setGetTime(LocalDateTime.of(2023, 10, 25, 22, 10));
        spgRepository.save(spg3);

        // テストデータ４
        SpecialMove sp4 = new SpecialMove();
        sp4.setUserId("fuga");
        sp4.setSpName("つよつよわざ");
        sp4.setFurigana(null);
        sp4.setHeading("己が身に骸を宿す、最凶最悪の魔");
        sp4.setDescription("愛する者の魂を継承し、骸として己の身に宿す。魔の力を手に入れることで、凶悪な魔力を発することができる。闇を統べる者が持つ力。");
        sp4.setImageName("qstLKSVVJuseZTYnSwls.png");
        sp4.setRegistedTime(LocalDateTime.of(2023, 10, 22, 22, 10));
        repository.save(sp4);
        SpecialMoveGallary spg4 = new SpecialMoveGallary();
        spg4.setLineUserId("fuga");
        spg4.setAuthorLineUserId("fuga");
        spg4.setSpecialMoveId(4L);
        spg4.setGetTime(LocalDateTime.of(2023, 10, 22, 22, 10));
        spgRepository.save(spg4);

        // テストデータ５
        SpecialMove sp5 = new SpecialMove();
        sp5.setUserId("fuga");
        sp5.setSpName("ほねほね");
        sp5.setFurigana(null);
        sp5.setHeading("己が身に骸を宿す、最凶最悪の魔");
        sp5.setDescription("愛する者の魂を継承し、骸として己の身に宿す。魔の力を手に入れることで、凶悪な魔力を発することができる。闇を統べる者が持つ力。");
        sp5.setImageName("qstLKSVVJuseZTYnSwls.png");
        sp5.setRegistedTime(LocalDateTime.of(2023, 10, 22, 22, 10));
        repository.save(sp5);
        SpecialMoveGallary spg5 = new SpecialMoveGallary();
        spg5.setLineUserId("fuga");
        spg5.setAuthorLineUserId("fuga");
        spg5.setSpecialMoveId(5L);
        spg5.setGetTime(LocalDateTime.of(2023, 10, 22, 22, 10));
        spgRepository.save(spg5);

        // テストデータ６
        SpecialMove sp6 = new SpecialMove();
        sp6.setUserId("fuga");
        sp6.setSpName("fugafuga");
        sp6.setFurigana(null);
        sp6.setHeading("己が身に骸を宿す、最凶最悪の魔");
        sp6.setDescription("愛する者の魂を継承し、骸として己の身に宿す。魔の力を手に入れることで、凶悪な魔力を発することができる。闇を統べる者が持つ力。");
        sp6.setImageName("qstLKSVVJuseZTYnSwls.png");
        sp6.setRegistedTime(LocalDateTime.of(2023, 10, 22, 22, 10));
        repository.save(sp6);
        SpecialMoveGallary spg6 = new SpecialMoveGallary();
        spg6.setLineUserId("fuga");
        spg6.setAuthorLineUserId("fuga");
        spg6.setSpecialMoveId(6L);
        spg6.setGetTime(LocalDateTime.of(2023, 10, 22, 22, 10));
        spgRepository.save(spg6);

        // テストデータ７
        SpecialMove sp7 = new SpecialMove();
        sp7.setUserId("fuga");
        sp7.setSpName("hogehoge");
        sp7.setFurigana(null);
        sp7.setHeading("己が身に骸を宿す、最凶最悪の魔");
        sp7.setDescription("愛する者の魂を継承し、骸として己の身に宿す。魔の力を手に入れることで、凶悪な魔力を発することができる。闇を統べる者が持つ力。");
        sp7.setImageName("qstLKSVVJuseZTYnSwls.png");
        sp7.setRegistedTime(LocalDateTime.of(2023, 10, 22, 22, 10));
        repository.save(sp7);
        SpecialMoveGallary spg7 = new SpecialMoveGallary();
        spg7.setLineUserId("fuga");
        spg7.setAuthorLineUserId("fuga");
        spg7.setSpecialMoveId(7L);
        spg7.setGetTime(LocalDateTime.of(2023, 10, 22, 22, 10));
        spgRepository.save(spg7);

    }

}
