package com.mxy.system.demo.insert.mapper;

import com.mxy.system.demo.insert.entity.Person;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 孟耀
 */
@Mapper
public interface PersonMapper {
    int insertList(List<Person> persons);
}
