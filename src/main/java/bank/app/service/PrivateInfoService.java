package bank.app.service;

import bank.app.dto.PrivateInfoDto;
import bank.app.model.entity.Address;
import bank.app.model.entity.PrivateInfo;

public interface PrivateInfoService {
    PrivateInfo savePrivateInfo(PrivateInfo privateInfo);
    PrivateInfo getPrivateInfoById(Long id);
    PrivateInfo createPrivateInfo(PrivateInfoDto privateInfoDto, Address savedAddress);
}
