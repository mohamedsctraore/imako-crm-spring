package ci.imako.imakocrmtest.services;

import ci.imako.imakocrmtest.domain.RendezVous;

import java.util.List;

public interface RendezVousService extends AbstractService<RendezVous, Long> {
    List<RendezVous> findByRendezVousById(Long id);
}
