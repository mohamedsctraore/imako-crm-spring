package ci.imako.imakocrmtest.services.impl;

import ci.imako.imakocrmtest.domain.Commande;
import ci.imako.imakocrmtest.services.CommandeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommandeServiceImpl implements CommandeService {

    @Override
    public List<Commande> findAll() {
        return null;
    }

    @Override
    public Commande findById(Long id) {
        return null;
    }

    @Override
    public Commande save(Commande commande) {
        return null;
    }

    @Override
    public void update(Commande commande) {

    }

    @Override
    public void delete(Commande commande) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
