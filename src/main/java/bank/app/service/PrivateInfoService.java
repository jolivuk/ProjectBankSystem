package bank.app.service;

import bank.app.dto.PrivateInfoRequestDto;
import bank.app.model.entity.PrivateInfo;
import bank.app.model.entity.User;

public interface PrivateInfoService {
    PrivateInfo createPrivateInfo(PrivateInfoRequestDto privateInfoRequestDto, User user);
}
