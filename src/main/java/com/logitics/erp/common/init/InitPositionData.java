package com.logitics.erp.common.init;

import com.logitics.erp.position.entity.Position;
import com.logitics.erp.position.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Order(value = 2)
public class InitPositionData implements CommandLineRunner {

    private final PositionRepository positionRepository;

    @Override
    public void run(String... args) throws Exception {

        if (positionRepository.count() > 1) {
            return;
        }

        List<Position> positions = List.of(
                new Position("사원", 1),
                new Position("주임", 2),
                new Position("대리", 3),
                new Position("선임", 4),
                new Position("과장", 5),
                new Position("차장", 6),
                new Position("부장", 7),
                new Position("실장", 8),
                new Position("센터장", 9),
                new Position("이사", 10),
                new Position("상무", 11),
                new Position("전무", 12),
                new Position("부사장", 13),
                new Position("사장", 14),
                new Position("대표이사", 15)
        );

        positionRepository.saveAll(positions);
    }
}
