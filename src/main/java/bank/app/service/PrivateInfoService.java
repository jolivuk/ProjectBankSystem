package bank.app.service;

import bank.app.dto.PrivateInfoRequestDto;
import bank.app.dto.PrivateInfoResponseDto;
import bank.app.model.entity.Address;
import bank.app.model.entity.PrivateInfo;
import bank.app.model.entity.User;

public interface PrivateInfoService {
    PrivateInfo savePrivateInfo(PrivateInfo privateInfo);
    PrivateInfo createPrivateInfo(PrivateInfoRequestDto privateInfoRequestDto, User user);
}
