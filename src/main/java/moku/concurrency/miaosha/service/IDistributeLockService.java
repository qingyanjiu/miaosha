package moku.concurrency.miaosha.service;

public interface IDistributeLockService {

    String getLock(String resourceId, int expireSecond);

    boolean releaseLock(String resourceId, String lockIdentifier);
}
