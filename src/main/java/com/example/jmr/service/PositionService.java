package com.example.jmr.service;

import com.example.jmr.entity.Position;
import com.example.jmr.repository.PositionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class PositionService {
    @Autowired
    private PositionRepository positionRepository;

    /*---------------岗位信息（Position）----------------
    -------检索：管理员
    -------更新：管理员
    -------创建：管理员
    -------删除：管理员
    --------------------------------------------------*/
    public Position addPosition(Position position){
        positionRepository.save(position);
        return position;
    }
    public Position getPosition(String name){
        return positionRepository.getPositionByP_name(name).orElse(null);
    }
}
