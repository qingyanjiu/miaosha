package moku.concurrency.miaosha.service;


import java.util.List;
import java.util.Map;

public interface IRedPackageService {
    List<Map> getAll();

    void add(Map params) throws Exception;
}
