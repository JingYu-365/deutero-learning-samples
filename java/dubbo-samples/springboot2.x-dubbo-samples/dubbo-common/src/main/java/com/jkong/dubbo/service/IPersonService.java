package com.jkong.dubbo.service;

import com.jkong.dubbo.entity.PersonEntity;

import java.util.List;

/**
 * @author JKong
 * @version v1.0
 * @description Person
 * @date 2019/8/1 11:35.
 */
public interface IPersonService {
    /**
     * save person info, if this person data is not existed;
     * if this person data is existed, will update this person info.
     *
     * @param person person info data
     * @return Whether the storage is successful
     * true：success
     * false：failed
     */
    boolean saveOrUpdatePerson(PersonEntity person);

    /**
     * delete person info
     *
     * @param person person needed to be deleted
     * @return Whether the delete is successful
     * true：success
     * false：failed
     */
    boolean deletePerson(PersonEntity person);

    /**
     * get person info by person id
     *
     * @param id person id
     * @return person info，if the person is not existed, will return null.
     */
    PersonEntity getPersonInfo(String id);

    /**
     * list all person infos
     *
     * @return all person infos, if no data, will return empty list.
     */
    List<PersonEntity> listAllPerson();
}