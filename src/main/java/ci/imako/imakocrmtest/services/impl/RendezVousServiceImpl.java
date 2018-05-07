package ci.imako.imakocrmtest.services.impl;

import ci.imako.imakocrmtest.domain.RendezVous;
import ci.imako.imakocrmtest.exceptions.ResourceNotFoundException;
import ci.imako.imakocrmtest.repositories.RendezVousRepository;
import ci.imako.imakocrmtest.services.RendezVousService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RendezVousServiceImpl implements RendezVousService {
    private static final Logger log = LoggerFactory.getLogger(RendezVousServiceImpl.class);

    private RendezVousRepository rendezVousRepository;

    @Autowired
    public RendezVousServiceImpl(RendezVousRepository rendezVousRepository) {
        this.rendezVousRepository = rendezVousRepository;
    }

    @Override
    public List<RendezVous> findAll() {
        log.info("APPELLE DE LA METHODE POUR RETROUVER TOUS LES RENDEZ VOUS");
        log.debug("NOMBRE DE RENDEZ VOUS : " + rendezVousRepository.findAll().size());
        return rendezVousRepository.findAll();
    }

    @Override
    public RendezVous findById(Long id) {
        log.info("APPELLE DE LA METHODE POUR RETROUVER UN RENDEZ VOUS PAR SON ID");
        Optional<RendezVous> rendezVous = Optional.ofNullable(rendezVousRepository.findOne(id));
        if (rendezVous.isPresent()) {
            log.debug(rendezVous.get().getId().toString());
            return rendezVous.get();
        } else {
            log.error("LE RENDEZ VOUS AVEC ID NUMERO " + id + " N'EXISTE");
            throw new ResourceNotFoundException("Resource Not Found");
        }
    }

    @Override
    public RendezVous save(RendezVous rendezVous) {
        log.info("APPELLE DE LA METHODE POUR ENREGISTRER OU MODIFIER UN RENDEZ VOUS");
        return rendezVousRepository.save(rendezVous);
    }

    @Override
    public void delete(RendezVous rendezVous) {
        log.info("APPELLE DE LA METHODE POUR SUPPRIMER UN RENDEZ VOUS OBJET");
        log.debug("ID RENDEZ SUPPRIME : " + rendezVous.getId());
        rendezVousRepository.delete(rendezVous);
    }

    @Override
    public void deleteById(Long id) {
        log.info("APPELLE DE LA METHODE POUR SUPPRIMER UN RENDEZ VOUS PAR SON ID");

        Optional<RendezVous> rendezVous = Optional.ofNullable(rendezVousRepository.findOne(id));
        if (rendezVous.isPresent()) {
            rendezVousRepository.delete(id);
            log.debug("ID RENDEZ VOUS SUPPRIME : " + id);
        } else {
            log.error("RENDEZ VOUS NUMERO " + id + " N'EXISTE PAS.");
            throw new ResourceNotFoundException("Resource Not found");
        }
    }

    @Override
    public List<RendezVous> findByRendezVousById(Long id) {
        log.info("DEBUT DE LA METHODE QUI RECUPERE LES RENDEZ VOUS SELON LE CONTACT");
        List<RendezVous> rendezVousList = new ArrayList<>();
        for (RendezVous rendezVous : this.findAll()) {
            if (rendezVous.getContact().getId() == id) {
                rendezVousList.add(rendezVous);
                log.debug("ID RENDEZ VOUS : " + rendezVous.getId());
                log.debug("DATE RENDEZ VOUS : " + rendezVous.getDateRendezVous());
            }
        }
        return rendezVousList;
    }
}
