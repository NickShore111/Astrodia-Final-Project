package com.perscholas.astrodia.services;

import com.perscholas.astrodia.models.Port;
import com.perscholas.astrodia.repositories.PortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortService {
    private final PortRepository portRepository;

    @Autowired
    public PortService(PortRepository portRepository) {
        this.portRepository = portRepository;
    }

    List<Port> findAll() { return portRepository.findAll(); }

    public void saveOrUpdate(Port liner) {
        portRepository.save(liner);
    }
}
