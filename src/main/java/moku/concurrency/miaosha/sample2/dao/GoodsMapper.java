package moku.concurrency.miaosha.sample2.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface GoodsMapper {
    boolean sell(Map params);
}
