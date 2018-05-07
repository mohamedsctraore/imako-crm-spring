package ci.imako.imakocrmtest.services;

import ci.imako.imakocrmtest.domain.Commande;

import java.util.List;

public interface CommandeService extends AbstractService<Commande, Long> {
    List<Commande> findCommandesById(Long id);
}
