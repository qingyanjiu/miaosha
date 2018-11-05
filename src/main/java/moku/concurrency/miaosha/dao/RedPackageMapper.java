package moku.concurrency.miaosha.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface RedPackageMapper {
    List<Map> getAll();

    void add(Map params);
}
