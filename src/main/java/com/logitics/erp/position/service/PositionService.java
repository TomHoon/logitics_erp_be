package com.logitics.erp.position.service;

import com.logitics.erp.position.entity.Position;
import com.logitics.erp.position.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;

    public List<Position> getAll() {
        return positionRepository.findAll();
    }
}
