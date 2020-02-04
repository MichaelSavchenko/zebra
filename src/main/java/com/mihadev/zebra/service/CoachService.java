package com.mihadev.zebra.service;

import com.mihadev.zebra.dto.CoachDto;
import com.mihadev.zebra.entity.Coach;
import com.mihadev.zebra.repository.CoachRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.mihadev.zebra.utils.CollectionUtils.toList;

@Service
public class CoachService {

    private final CoachRepository coachRepository;

    public CoachService(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    public List<Coach> getAll() {
        return toList(coachRepository.findAll());
    }

    public Coach get(int coachId) {
        return coachRepository.findById(coachId).orElseThrow(RuntimeException::new);
    }

    public Coach create(CoachDto dto) {
        Coach coach = fromDto(dto);
        coachRepository.save(coach);
        return coach;
    }

    public Coach update(CoachDto dto) {
        Coach coach = fromDto(dto);
        coachRepository.save(coach);
        return coach;
    }

    private Coach fromDto(CoachDto dto) {
        Coach coach = new Coach();
        if (Objects.nonNull(dto.getId())) {
            coach.setId(dto.getId());
        }
        coach.setFirstName(dto.getFirstName());
        coach.setLastName(dto.getLastName());
        coach.setPhone(dto.getPhone());
        coach.setActive(dto.isActive());
        coach.setNotes(dto.getNotes());

        return coach;
    }
}
