package fr.kevin.llps.todo.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class IdGeneratorService {

    public Integer generateId() {
        Random random = new Random();

        return random.nextInt();
    }

}
