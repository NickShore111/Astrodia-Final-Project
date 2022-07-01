package org.nicholasshore.astrodia.services;

import org.nicholasshore.astrodia.models.Port;
import org.nicholasshore.astrodia.repositories.PortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PortService {
    private final PortRepository portRepository;

    @Autowired
    public PortService(PortRepository portRepository) {
        this.portRepository = portRepository;
    }

    public List<Port> findAll() { return portRepository.findAll(); }

    public void saveOrUpdate(Port liner) {
        portRepository.save(liner);
    }

    public Optional<Port> findById(String id) { return portRepository.findById(id); }
}
