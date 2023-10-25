package com.example.originalspecialmove.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.originalspecialmove.domain.SpecialMove;
import com.example.originalspecialmove.domain.SpecialMoveGallary;
import com.example.originalspecialmove.domain.dto.SpecialMoveDto;
import com.example.originalspecialmove.service.FileStorageService;
import com.example.originalspecialmove.service.LineUserService;
import com.example.originalspecialmove.service.SpecialMoveService;

@RestController
@CrossOrigin("*")
public class SpecialMoveController {

    @Autowired
    private SpecialMoveService service;
    @Autowired
    private LineUserService lineUserService;
    @Autowired
    private FileStorageService fileStorageService;

    @ResponseBody
    @PostMapping(value = "/regist")
    public void regist(@RequestParam String name, @RequestParam String furigana, @RequestParam String heading,
            @RequestParam String description, @RequestParam MultipartFile image, @RequestParam String idToken)
            throws Exception {

        // IDトークンの対象ユーザーIDを取得
        String lineUserId = lineUserService.getLineUser(idToken);

        String fileName = fileStorageService.fileUpload(image);

        SpecialMove specialMove = new SpecialMove();
        specialMove.setSpName(name);
        specialMove.setFurigana(furigana);
        specialMove.setHeading(heading);
        specialMove.setDescription(description);
        specialMove.setImageName(fileName);
        specialMove.setUserId(lineUserId);
        specialMove.setRegistedTime(LocalDateTime.now());
        SpecialMove sp = service.saveSpecialMove(specialMove);

        SpecialMoveGallary spg = new SpecialMoveGallary();
        spg.setLineUserId(lineUserId);
        spg.setAuthorLineUserId(lineUserId);
        spg.setSpecialMoveId(sp.getId());
        spg.setGetTime(LocalDateTime.now());
        service.saveSPG(spg);
    }

    @ResponseBody
    @PostMapping(value = "/get-specialmove")
    public ResponseEntity<List<SpecialMoveDto>> getSpecialMove(@RequestParam String idToken) throws Exception {
        // String lineUserId = lineUserService.getLineUser(idToken);
        String lineUserId = "U5a62fcb7b4777ad78174bb14a4c31a59";

        List<SpecialMoveDto> spList = service.getSpecialMove(lineUserId);

        return ResponseEntity.ok(spList);
    }
}
