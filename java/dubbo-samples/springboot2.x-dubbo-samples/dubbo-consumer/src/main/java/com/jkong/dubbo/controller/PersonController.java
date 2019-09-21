package com.jkong.dubbo.controller;

import com.jkong.dubbo.entity.PersonEntity;
import com.jkong.dubbo.service.IPersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @author JKong
 * @version v1.0
 * @description Person controller
 * @date 2019/8/1 12:56.
 */
@Slf4j
@RestController
public class PersonController {

    @Reference(version = "1.0.0")
    private IPersonService iPersonService;


    @PostMapping(value = "/persons", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PersonEntity savePersonInfo(@RequestBody PersonEntity personEntity) {
        personEntity.setId(UUID.randomUUID().toString());
        boolean b = iPersonService.saveOrUpdatePerson(personEntity);
        if (b) {
            return personEntity;
        }
        return null;
    }


    @DeleteMapping(value = "/persons/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Boolean deletePersonInfo(@PathVariable(value = "id") String id) {
        PersonEntity personInfo = iPersonService.getPersonInfo(id);
        if (personInfo == null) {
            return false;
        }
        return iPersonService.deletePerson(personInfo);
    }


    @GetMapping(value = "/persons/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PersonEntity getPersonInfo(@PathVariable(value = "id") String id) {
        return iPersonService.getPersonInfo(id);
    }


    @GetMapping(value = "/persons", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<PersonEntity> listPersonInfos() {
        return iPersonService.listAllPerson();
    }
}