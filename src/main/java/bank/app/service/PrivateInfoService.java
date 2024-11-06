package bank.app.service;

import bank.app.model.entity.PrivateInfo;

public interface PrivateInfoService {
    PrivateInfo savePrivateInfo(PrivateInfo privateInfo);
    PrivateInfo getPrivateInfoById(Long id);
}
