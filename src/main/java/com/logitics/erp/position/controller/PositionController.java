package com.logitics.erp.position.controller;

import com.logitics.erp.position.entity.Position;
import com.logitics.erp.position.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/position")
public class PositionController {

    private final PositionService positionService;

    @GetMapping
    public List<Position> getAll() {
        return positionService.getAll();
    }
}
