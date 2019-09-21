package com.jkong.dubbo.service.impl;

import com.jkong.dubbo.entity.PersonEntity;
import com.jkong.dubbo.service.IPersonService;
import org.apache.dubbo.config.annotation.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JKong
 * @version v1.0
 * @description person service implement
 * @date 2019/8/1 11:54.
 */
@Service(version = "1.0.0")
public class PersonServiceImpl implements IPersonService {

    private  Map<String, PersonEntity> PERSON_DATA_STORAGE = new HashMap<>();

    @Override
    public boolean saveOrUpdatePerson(PersonEntity person) {
        PERSON_DATA_STORAGE.put(person.getId(), person);
        return true;
    }

    @Override
    public boolean deletePerson(PersonEntity person) {
        PERSON_DATA_STORAGE.remove(person.getId());
        return true;
    }

    @Override
    public PersonEntity getPersonInfo(String id) {
        return PERSON_DATA_STORAGE.get(id);
    }

    @Override
    public List<PersonEntity> listAllPerson() {
        return new ArrayList<PersonEntity>(PERSON_DATA_STORAGE.values());
    }
}