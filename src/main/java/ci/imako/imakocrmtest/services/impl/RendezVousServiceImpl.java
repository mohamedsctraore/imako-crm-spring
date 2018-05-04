package ci.imako.imakocrmtest.services.impl;

import ci.imako.imakocrmtest.domain.RendezVous;
import ci.imako.imakocrmtest.services.RendezVousService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RendezVousServiceImpl implements RendezVousService {

    @Override
    public List<RendezVous> findAll() {
        return null;
    }

    @Override
    public RendezVous findById(Long id) {
        return null;
    }

    @Override
    public RendezVous save(RendezVous rendezVous) {
        return null;
    }

    @Override
    public void update(RendezVous rendezVous) {

    }

    @Override
    public void delete(RendezVous rendezVous) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
