package bank.app.service.impl;

import bank.app.model.entity.PrivateInfo;
import bank.app.repository.PrivateInfoRepository;
import bank.app.service.PrivateInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivateInfoServiceImpl implements PrivateInfoService {
    private final PrivateInfoRepository privateInfoRepository;

    @Autowired
    public PrivateInfoServiceImpl(PrivateInfoRepository privateInfoRepository) {
        this.privateInfoRepository = privateInfoRepository;
    }

    @Override
    public PrivateInfo savePrivateInfo(PrivateInfo privateInfo) {
        return privateInfoRepository.save(privateInfo);
    }

    @Override
    public PrivateInfo getPrivateInfoById(Long id) {
        return privateInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PrivateInfo not found with id: " + id));
    }
}
