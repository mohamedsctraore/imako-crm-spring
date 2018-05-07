package ci.imako.imakocrmtest.services.impl;

import ci.imako.imakocrmtest.domain.Commande;
import ci.imako.imakocrmtest.exceptions.ResourceNotFoundException;
import ci.imako.imakocrmtest.repositories.CommandeRepository;
import ci.imako.imakocrmtest.services.CommandeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommandeServiceImpl implements CommandeService {
    private static final Logger log = LoggerFactory.getLogger(CommandeServiceImpl.class);

    private CommandeRepository commandeRepository;

    public CommandeServiceImpl(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    @Override
    public List<Commande> findAll() {
        log.info("APPELLE DE LA METHODE POUR RETROUVER TOUTES LES COMMANDES");
        return commandeRepository.findAll();
    }

    @Override
    public Commande findById(Long id) {
        log.info("APPELLE DE LA METHODE POUR RETROUVER UNE COMMANDE PAR SON ID");
        Optional<Commande> commande = Optional.ofNullable(commandeRepository.findOne(id));
        if (commande.isPresent()) {
            log.debug("ID COMMANDE : " + commande.get().getId());
            log.debug("NOTE COMMANDE : " + commande.get().getNoteCommande());
            return commande.get();
        } else {
            log.error("COMMANDE NUMERO : " + id + " INEXISTANTE.");
            throw new ResourceNotFoundException("Resource Not Found");
        }
    }

    @Override
    public Commande save(Commande commande) {
        log.info("APPELLE DE LA METHODE POUR ENREGISTRER UNE COMMANDE");
        log.debug("ID COMMANDE : " + commande.getId());
        return commandeRepository.save(commande);
    }

    @Override
    public void delete(Commande commande) {
        log.info("APPELLE DE LA METHODE POUR SUPPRIMER UN OBJET COMMANDE");
        commandeRepository.delete(commande);
    }

    @Override
    public void deleteById(Long id) {
        log.info("APPELLE DE LA METHODE POUR SUPPRIMER UN OBJET COMMANDE PAR SON ID");
        log.debug("ID COMMANDE A SUPPRIMER " + id);
        Optional<Commande> commande = Optional.ofNullable(commandeRepository.findOne(id));
        if (commande.isPresent()) {
            log.debug("ID COMMANDE : " + commande.get().getId());
            commandeRepository.delete(id);
            log.info("SUPPRESSION COMMANDE NUMERO " + id);
        } else {
            log.error("LA COMMANDE NUMERO " + id + " EST INEXISTANTE");
            throw new ResourceNotFoundException("Resource Not Found");
        }
    }

    @Override
    public List<Commande> findCommandesById(Long id) {
        log.info("APPELLE DE LA METHODE QUI RETOURNE LES COMMANDES SELON LE CONTACT");
        List<Commande> commandeList = new ArrayList<>();
        for (Commande commande : this.findAll()) {
            if (commande.getContact().getId() == id) {
                commandeList.add(commande);
                log.debug("ID COMMANDE : " + commande.getId());
                log.debug("NOTE COMMANDE : " + commande.getNoteCommande());
            }
        }
        return commandeList;
    }
}
