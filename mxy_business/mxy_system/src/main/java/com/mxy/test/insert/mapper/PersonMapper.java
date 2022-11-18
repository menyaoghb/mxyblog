package com.mxy.test.insert.mapper;

import com.mxy.test.insert.entity.Person;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author mengyao
 */
@Mapper
public interface PersonMapper {
    int insertList(List<Person> persons);
}
